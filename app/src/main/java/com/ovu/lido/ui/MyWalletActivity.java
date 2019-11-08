package com.ovu.lido.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.base.CommonAdapter;
import com.ovu.lido.base.ViewHolder;
import com.ovu.lido.bean.BalanceBean;
import com.ovu.lido.bean.WalletHistoryBean;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 我的钱包
 */
public class MyWalletActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.lv_wallet)
    ListView mLv_wallet;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_recharge)
    TextView tv_recharge;
    private CommonAdapter<WalletHistoryBean.DataBean> mCommonAdapter;
    private ArrayList<WalletHistoryBean.DataBean> mList = new ArrayList<>();

    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_wallet;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected void initView() {
        mCommonAdapter = new CommonAdapter<WalletHistoryBean.DataBean>(this, mList, R.layout.lv_wallet_item) {
            @Override
            protected void convert(ViewHolder viewHolder, WalletHistoryBean.DataBean item, int position) {
                viewHolder.setText(R.id.tv_wallet_type, item.getIncident());
                viewHolder.setText(R.id.tv_time, item.getCreate_time());
                if (item.getChange_type() == 0) {
                    viewHolder.setText(R.id.tv_wallet_money, "-" + AppUtil.TwoPoint(item.getChange_amount()));
                } else {
                    viewHolder.setText(R.id.tv_wallet_money, "+" + AppUtil.TwoPoint(item.getChange_amount()));
                }
            }
        };
        mLv_wallet.setAdapter(mCommonAdapter);
    }


    private void getData() {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map.put("token", AppPreference.I().getString("token", ""));
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map1.put("data", map);
        OkHttpUtils.post()
                .url(HttpAddress.GET_BALANCE)
                .addParams("params", GsonUtil.ToGson(map1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(Tag, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(mContext);
                            return;
                        }
                        BalanceBean bean = GsonUtil.GsonToBean(response, BalanceBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            tv_money.setText("¥" + AppUtil.TwoPoint(bean.getData().getBalance()));
                            if (bean.getData().getIsShow() == 0) {
                                tv_recharge.setVisibility(View.GONE);
                            } else {
                                tv_recharge.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

    }

    private void getList() {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map.put("token", AppPreference.I().getString("token", ""));
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map1.put("data", map);
        OkHttpUtils.post()
                .url(HttpAddress.QUERY_HISTORY_LIST)
                .addParams("params", GsonUtil.ToGson(map1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(Tag, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(mContext);
                            return;
                        }
                        WalletHistoryBean bean = GsonUtil.GsonToBean(response, WalletHistoryBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            mList.clear();
                            mList.addAll(bean.getData());
                            mCommonAdapter.setDatas(mList);
                        } else {
                            showToast(bean.getErrorMsg());
                        }
                    }
                });

    }


    @Override
    protected void setListener() {
        iv_back.setOnClickListener(this);
        findViewById(R.id.tv_recharge).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_recharge:
                startActivity(new Intent(this, RechargeActivity.class));
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        getData();
        getList();
    }
}
