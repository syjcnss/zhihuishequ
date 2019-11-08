package com.ovu.lido.ui;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.WorkDetailInfo;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 在线社区 -- 办事流程 -- 详情
 */
public class WorkingScheduleDetailActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.work_detail_wv)
    WebView work_detail_wv;
    private Dialog mDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_working_schedule_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
        work_detail_wv.getSettings().setJavaScriptEnabled(true);
        work_detail_wv.getSettings().setDefaultTextEncodingName("utf-8");
        work_detail_wv.getSettings().setSupportZoom(false);
    }

    @Override
    protected void loadData() {
        super.loadData();
        mDialog.show();
        int work_id = getIntent().getIntExtra("work_id", -1);
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("work_id", String.valueOf(work_id));
        String params = ViewHelper.getParams(paramsMap);
        OkHttpUtils.post()
                .url(Constant.QUERU_WORK_DETAIL)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (mContext == null || isFinishing()){
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "办事流程详情数据: " + response);
                        LoadProgressDialog.closeDialog(mDialog);

                        WorkDetailInfo info = GsonUtil.GsonToBean(response, WorkDetailInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            work_detail_wv.loadData(info.getData(),"text/html; charset=UTF-8",null);
                        }else {
                            showShortToast(info.getErrorMsg());
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
