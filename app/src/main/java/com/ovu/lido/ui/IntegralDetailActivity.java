package com.ovu.lido.ui;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.IntegralDetailLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.IntegralDetailInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 积分明细
 */
public class IntegralDetailActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.integral_lv)
    ListView integral_lv;
    @BindView(R.id.integral_srl)
    SmartRefreshLayout integral_srl;
    private IntegralDetailLvAdapter mAdapter;
    private Dialog mDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_integral_detail;
    }

    @Override
    protected void initView() {
        super.initView();

        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
        mDialog.show();
        integral_srl.setRefreshFooter(new BallPulseFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
        integral_srl.setEnableRefresh(false);
        mAdapter = new IntegralDetailLvAdapter(mContext, inteHistoryInfos);
        integral_lv.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        integral_srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadData();
            }
        });
    }

    @OnClick(R.id.back_iv)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
        }
    }

    private int currentPage = 0;
    private int pageNo = 100;
    private int currentLoadSize = 0;
    private List<IntegralDetailInfo.DataBean.ListBean> inteHistoryInfos = new ArrayList<>();

    @Override
    protected void loadData() {
        super.loadData();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        paramsMap.put("pageSize", String.valueOf(pageNo));
        paramsMap.put("pageNo", String.valueOf(currentPage));
        String params = ViewHelper.getParams(paramsMap);
        OkHttpUtils.post()
                .url(Constant.GET_USER_HISTORY_INTE_GRATION)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                        integral_srl.finishLoadMore();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "积分明细数据: " + response);
                        LoadProgressDialog.closeDialog(mDialog);
                        IntegralDetailInfo info = GsonUtil.GsonToBean(response, IntegralDetailInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            int total_count = info.getData().getTotal_count();
                            List<IntegralDetailInfo.DataBean.ListBean> list = info.getData().getList();
                            currentLoadSize = list == null ? 0 : list.size();
                            if (list != null && currentLoadSize > 0) {
                                inteHistoryInfos.addAll(list);
                                mAdapter.notifyDataSetChanged();
                            }
                            Log.i(TAG, "=========size=========: " + currentLoadSize + "/" + total_count);
                            if (currentLoadSize < pageNo) {
                                integral_srl.setNoMoreData(true);
                                integral_srl.finishLoadMore();
                            } else {
                                // 还有未加载的数据
                                currentPage++;
                                integral_srl.setNoMoreData(false);
                                integral_srl.finishLoadMore();
                            }

                        } else {
                            integral_srl.finishLoadMore();
                            showShortToast(info.getErrorMsg());
                        }

                    }
                });
    }
}
