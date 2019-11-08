package com.ovu.lido.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.MyGroupBuyDetailBean;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 我的订单详情
 */
public class GroupBuyDetailedActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    private String groupId;
    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.tv_address)
    TextView address;
    @BindView(R.id.tv_receiver)
    TextView receiver;
    @BindView(R.id.tv_phone)
    TextView phone;
    @BindView(R.id.tv_name)
    TextView name;
    @BindView(R.id.tv_top_name)
    TextView top_name;
    @BindView(R.id.tv_price)
    TextView price;
    @BindView(R.id.tv_count)
    TextView count;
    @BindView(R.id.tv_account_money)
    TextView account;
    @BindView(R.id.tv_use_money)
    TextView use_money;
    @BindView(R.id.tv_total)
    TextView total;
    @BindView(R.id.tv_date)
    TextView date;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_group_buy_detailed;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).init();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        groupId = intent.getStringExtra("myGroupBuy");


    }

    @Override
    protected void loadData() {
        getData();
    }


    private void getData() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();

        map.put("token", AppPreference.I().getString("token", ""));
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map.put("id", groupId);
        map1.put("data", map);
        OkHttpUtils.post()
                .url(HttpAddress.MY_GROUP_DETAIL)
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
                        MyGroupBuyDetailBean bean = GsonUtil.GsonToBean(response, MyGroupBuyDetailBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            receiver.setText("收货人:" + bean.getData().getSHIPPER());
                            phone.setText(bean.getData().getMOBILE_NO());
                            address.setText("收货地址:" + bean.getData().getADDRESS_DETAIL());
                            top_name.setText(bean.getData().getG_p_name());
                            name.setText(bean.getData().getG_p_name());
                            price.setText(AppUtil.TwoPoint(bean.getData().getG_p_price()));
                            count.setText("x" + bean.getData().getCount());
                            total.setText(AppUtil.TwoPoint(bean.getData().getAmount()));
                            date.setText(bean.getData().getCreate_time());
                            RequestOptions requestOptions = new RequestOptions()
                                    .placeholder(R.drawable.group_buy_image_error)
                                    .error(R.drawable.group_buy_image_error);
                            Glide.with(GroupBuyDetailedActivity.this)
                                    .load(bean.getData().getCommodity_img())
                                    .apply(requestOptions)
                                    .into(iv_img);
                        }
                    }
                });

    }

    @Override
    protected void setListener() {
        iv_back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;

        }
    }
}
