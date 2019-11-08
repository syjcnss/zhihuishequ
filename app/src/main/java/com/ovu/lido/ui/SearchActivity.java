package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.base.CommonAdapter;
import com.ovu.lido.base.ViewHolder;
import com.ovu.lido.bean.GroupByBean;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

public class SearchActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.lv_key)
    ListView lv_key;

    private CommonAdapter<GroupByBean.DataBean> mCommonAdapter;
    private ArrayList<GroupByBean.DataBean> mList = new ArrayList<>();
    private Context mContext;
    private Dialog mDialog;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_serach;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected void initView() {
        mDialog = LoadProgressDialog.createLoadingDialog(this);

        mCommonAdapter = new CommonAdapter<GroupByBean.DataBean>(this, mList, R.layout.lv_group_buy_item) {
            @Override
            protected void convert(ViewHolder viewHolder, GroupByBean.DataBean item, final int position) {
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.drawable.group_buy_image_error)
                        .error(R.drawable.group_buy_image_error);
                Glide.with(mContext)
                        .load(item.getCommodity_img())
                        .apply(requestOptions)
                        .into((ImageView) viewHolder.getView(R.id.iv_content));
                TextView textView = viewHolder.getView(R.id.tv_own_price);
                textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.setText(R.id.tv_name, item.getG_p_name());
                viewHolder.setText(R.id.tv_price, "¥" + AppUtil.TwoPoint(item.getG_p_price()));
                viewHolder.setText(R.id.tv_own_price, "¥" + AppUtil.TwoPoint(item.getOriginal_price()));
                if (item.isIs_end()) {
                    viewHolder.getView(R.id.tv_join).setSelected(false);
                    viewHolder.setText(R.id.tv_join, "已结束");
                    viewHolder.getView(R.id.tv_join).setEnabled(false);
                } else {
                    viewHolder.getView(R.id.tv_join).setEnabled(true);
                    if (item.isIs_enroll()) {
                        viewHolder.getView(R.id.tv_join).setSelected(false);
                        viewHolder.setText(R.id.tv_join, "已参团");
                    } else {
                        if (item.getEnrollCount() >= item.getEnroll_limit()) {
                            viewHolder.getView(R.id.tv_join).setSelected(false);
                            viewHolder.setText(R.id.tv_join, "人数已满");
                            viewHolder.getView(R.id.tv_join).setEnabled(false);
                        } else {
                            viewHolder.getView(R.id.tv_join).setEnabled(true);
                            viewHolder.setText(R.id.tv_join, "参团");
                            viewHolder.getView(R.id.tv_join).setSelected(true);
                        }
                    }
                }
                viewHolder.getView(R.id.tv_join).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, OrderDetailedActivity.class);
                        intent.putExtra("product", mList.get(position));
                        startActivity(intent);
                    }
                });

            }
        };
        lv_key.setAdapter(mCommonAdapter);
    }

    @Override
    protected void setListener() {
        findViewById(R.id.tv_cancel).setOnClickListener(this);
        findViewById(R.id.iv_search).setOnClickListener(this);
        lv_key.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, ProductDetailedActivity.class);
                intent.putExtra("groupById", mList.get(position).getId() + "");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.iv_search:
                searchKey();
                break;
        }
    }


    private void searchKey() {
        String key = et_search.getText().toString().trim();
        if (TextUtils.isEmpty(key)) {
            return;
        }
        mDialog.show();
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map.put("token", AppPreference.I().getString("token", ""));
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map.put("gpname", key);
        map1.put("data", map);
        OkHttpUtils.post()
                .url(HttpAddress.GROUP_BUY_KEY)
                .addParams("params", GsonUtil.ToGson(map1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(Tag, response);
                        LoadProgressDialog.closeDialog(mDialog);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(mContext);
                            return;
                        }
                        GroupByBean bean = GsonUtil.GsonToBean(response, GroupByBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            mList.clear();
                            mList.addAll(bean.getData());
                            mCommonAdapter.setDatas(mList);
                        }
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LoadProgressDialog.closeDialog(mDialog);
    }
}
