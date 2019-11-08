package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.WorkingScheduleLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.WorkListInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;
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
 * 在线社区--办事流程
 */
public class WorkingScheduleActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.working_schedule_lv)
    ListView working_schedule_lv;

    private List<WorkListInfo.DataBean> workListInfos = new ArrayList<>();
    private WorkingScheduleLvAdapter mWorkingScheduleLvAdapter;
    private Dialog mDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_working_schedule;
    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
        mWorkingScheduleLvAdapter = new WorkingScheduleLvAdapter(mContext, workListInfos);
        working_schedule_lv.setAdapter(mWorkingScheduleLvAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        working_schedule_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, WorkingScheduleDetailActivity.class);
                intent.putExtra("work_id",workListInfos.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        mDialog.show();
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id",""));
        String params = ViewHelper.getParams(paramsMap);
        OkHttpUtils.post()
                .url(Constant.QUERY_WORK_LIST)
                .addParams("params",params)
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
                        Log.i(TAG, "办事流程列表数据: " + response);
                        LoadProgressDialog.closeDialog(mDialog);

                        WorkListInfo info = GsonUtil.GsonToBean(response, WorkListInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            workListInfos.addAll(info.getData());
                            mWorkingScheduleLvAdapter.notifyDataSetChanged();
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
