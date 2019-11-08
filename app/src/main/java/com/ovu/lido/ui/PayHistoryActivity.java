package com.ovu.lido.ui;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.HistoryLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.PayHistoryInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ViewHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class PayHistoryActivity extends BaseActivity {

    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.history_list)
    ListView history_list;


    private List<PayHistoryInfo.DataBean> payHistoryInfos = new ArrayList<>();
    private HistoryLvAdapter mAdapter;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f);
        mImmersionBar.titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_pay_history;
    }

    @Override
    protected void initView() {
        super.initView();
        mAdapter = new HistoryLvAdapter(mContext, payHistoryInfos);
        history_list.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @Override
    protected void loadData() {
        super.loadData();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        String params = ViewHelper.getParams(paramMap);
        OkHttpUtils.post()
                .url(Constant.PAYMENT_HISTORY_URL)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "jsonStr: " + response);
                        if (isTokenError(response)) {
                            startLoginActivity();
                        } else {
                            PayHistoryInfo info = GsonUtil.GsonToBean(response, PayHistoryInfo.class);
                            if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                                payHistoryInfos.addAll(info.getData());
                                mAdapter.notifyDataSetChanged();
                            } else {
                                showShortToast(info.getErrorMsg());
                            }
                        }
                    }
                });

    }

    @OnClick(R.id.back_iv)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }

}
