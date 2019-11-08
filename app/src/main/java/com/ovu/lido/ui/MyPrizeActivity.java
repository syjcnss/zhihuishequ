package com.ovu.lido.ui;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.MyPrizeLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.MyPrizeInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class MyPrizeActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.my_prize_lv)
    ListView my_prize_lv;
    @BindView(R.id.my_prize_srl)
    SmartRefreshLayout my_prize_srl;

    private MyPrizeLvAdapter mMyPrizeLvAdapter;
    private Dialog mDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_prize;
    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
        mDialog.show();
        mMyPrizeLvAdapter = new MyPrizeLvAdapter(mContext, myPrizeInfos);
        my_prize_lv.setAdapter(mMyPrizeLvAdapter);
    }

    private int currentPage = 0;
    private int pageNo = 100;
    private int currentLoadSize = 0;
    private List<MyPrizeInfo.DataBean.ListBean> myPrizeInfos = new ArrayList<>();

    @Override
    protected void loadData() {
        super.loadData();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        paramsMap.put("pageSize", String.valueOf(pageNo));
        paramsMap.put("pageNo", String.valueOf(currentPage));
        String params = ViewHelper.getParams(paramsMap);
        OkHttpUtils.post()
                .url(Constant.GET_USER_AWARDS_RECORD)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                        my_prize_srl.finishLoadMore();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "积分明细数据: " + response);
                        LoadProgressDialog.closeDialog(mDialog);
                        MyPrizeInfo info = GsonUtil.GsonToBean(response, MyPrizeInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            int total_count = info.getData().getTotal_count();
                            List<MyPrizeInfo.DataBean.ListBean> list = info.getData().getList();
                            currentLoadSize = list == null ? 0 : list.size();
                            if (list != null && currentLoadSize > 0) {
                                myPrizeInfos.addAll(list);
                                mMyPrizeLvAdapter.notifyDataSetChanged();
                            }
                            Log.i(TAG, "=========size=========: " + currentLoadSize + "/" + total_count);
                            if (currentLoadSize < total_count) {
                                my_prize_srl.setNoMoreData(true);
                            } else {
                                // 还有未加载的数据
                                currentPage++;
                                my_prize_srl.setNoMoreData(false);
                            }
                        } else {
                            showShortToast(info.getErrorMsg());
                        }
                        my_prize_srl.finishLoadMore();

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
