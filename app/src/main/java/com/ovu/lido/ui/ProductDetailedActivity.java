package com.ovu.lido.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.GroupDetailBean;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 商品详情
 */
public class ProductDetailedActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.iv_product)
    ImageView product_img;
    @BindView(R.id.tv_price)
    TextView price;
    @BindView(R.id.tv_own_price)
    TextView own_price;
    @BindView(R.id.tv_name)
    TextView name;
    @BindView(R.id.tv_content)
    TextView content;
    @BindView(R.id.tv_apply_count)
    TextView apply_count;
    @BindView(R.id.tv_join)
    TextView join;
    @BindView(R.id.tv_remain_time)
    TextView reamin_time;
    @BindView(R.id.wb_product)
    WebView mWebView;
    private String groupById;
    private GroupDetailBean mBean;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            remainTime();
            mHandler.sendEmptyMessageDelayed(0, 1000);
        }
    };
    private String mEndTime;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_product_detailed;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        groupById = intent.getStringExtra("groupById");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.getSettings().setSupportZoom(false);
    }

    @Override
    protected void loadData() {
        getData();
    }


    private void getData() {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map.put("token", AppPreference.I().getString("token",""));
        map.put("resident_id", AppPreference.I().getString("resident_id",""));
        map.put("g_p_id", groupById);
        map1.put("data", map);
        OkHttpUtils.post()
                .url(HttpAddress.GET_GROUP_DETAILS)
                .addParams("params", GsonUtil.ToGson(map1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(mContext);
                            return;
                        }
                        GroupDetailBean bean = GsonUtil.GsonToBean(response, GroupDetailBean.class);
                        mBean = bean;
                        if (bean.getErrorCode().equals("0000")) {
                            RequestOptions requestOptions = new RequestOptions()
                                    .placeholder(R.drawable.group_buy_image_error)
                                    .error(R.drawable.group_buy_image_error);
                            Glide.with(mContext)
                                    .load(bean.getData().getCommodity_img())
                                    .apply(requestOptions)
                                    .into(product_img);
                            name.setText(bean.getData().getG_p_name());
                            price.setText("¥" + AppUtil.TwoPoint(bean.getData().getG_p_price()));
                            own_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                            own_price.setText("¥" + AppUtil.TwoPoint(bean.getData().getOriginal_price()));
                            apply_count.setText(bean.getData().getEnrollCount() + "/" + bean.getData().getEnroll_limit());
                            mWebView.loadData(bean.getData().getContent(), "text/html; charset=UTF-8", null);
                            mEndTime = bean.getData().getEnd_time();
                            mHandler.sendEmptyMessage(0);
                            if (bean.getData().isIs_end()) {
                                join.setSelected(false);
                                join.setText("已结束");
                                join.setEnabled(false);
                            } else {
                                if (bean.getData().isIs_enroll()) {
                                    join.setSelected(false);
                                    join.setText("已参团");
                                    join.setEnabled(false);
                                } else {
                                    if (bean.getData().getEnrollCount() >= bean.getData().getEnroll_limit()) {
                                        join.setSelected(false);
                                        join.setText("人数已满");
                                        join.setEnabled(false);
                                    } else {
                                        join.setText("参团");
                                        join.setSelected(true);
                                    }
                                }
                            }
                        }
                    }
                });


    }

    @Override
    protected void setListener() {
        iv_back.setOnClickListener(this);
        join.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_join:
                if (mBean != null) {
                    if (mBean.getData().isIs_end()) {
                        showToast("团购已结束");
                        return;
                    }
                    if (mBean.getData().isIs_enroll()) {
                        showToast("您已参团");
                        return;
                    }
                    if (mBean.getData().getEnrollCount() >= mBean.getData().getEnroll_limit()) {
                        showToast("您已参团");
                        return;
                    }
                }
                Intent intent = new Intent(this, OrderDetailedActivity.class);
                intent.putExtra("productBean", (Serializable) mBean);
                startActivity(intent);
                break;
        }
    }


    public void remainTime() {
        String hours;
        String mins;
        String seconds;
        long endTime = TimeUtils.string2Millis(mEndTime) / 1000;
        long nowTime = System.currentTimeMillis() / 1000;
        long diff = endTime - nowTime;
        if (diff > 0) {
            long hour = diff / 3600;
            long mintue = (diff - (3600 * hour)) / 60;
            long second = diff - (hour * 3600 + mintue * 60);
            if (hour >= 10) {
                hours = hour + "";
            } else {
                hours = "0" + hour;
            }
            if (mintue >= 10) {
                mins = mintue + "";
            } else {
                mins = "0" + mintue;
            }
            if (second >= 10) {
                seconds = second + "";
            } else {
                seconds = "0" + second;
            }
            reamin_time.setText("距离结束时间:" + hours + ":" + mins + ":" + seconds);
        } else {
            reamin_time.setText("距离结束时间:00:00:00");
            mHandler.removeMessages(0);

        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(0);
    }
}
