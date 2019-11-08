package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;
import com.ovu.lido.R;
import com.ovu.lido.adapter.DecorationHistoryLvAdater;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.DecorationHistoryInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 装修管理--装修历史
 */
public class DecorationHistoryActivity extends BaseActivity {

    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.decoration_history_lv)
    ListView decoration_history_lv;

    private List<DecorationHistoryInfo.DataBean> historyInfos = new ArrayList<>();
    private DecorationHistoryLvAdater mHistoryLvAdater;
    private Dialog mDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_decoration_history;
    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
        mHistoryLvAdater = new DecorationHistoryLvAdater(mContext, historyInfos);
        decoration_history_lv.setAdapter(mHistoryLvAdater);
        decoration_history_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, DecorationHistoryDetailActivity.class);
                intent.putExtra("id",historyInfos.get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        mDialog.show();
        OkHttpUtils.get()
                .url(Constant.GET_DECORATION_LIST)
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
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
                        Log.i(TAG, "装修历史数据: " + response);
                        LoadProgressDialog.closeDialog(mDialog);
                        Type type = new TypeToken<DecorationHistoryInfo>(){}.getType();
                        DecorationHistoryInfo info = GsonUtil.GsonToBean(response,type);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            if (null != info.getData()){
                                historyInfos.addAll(info.getData());
                                mHistoryLvAdater.notifyDataSetChanged();
                            }
                        }else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    @OnClick({R.id.back_iv,R.id.second_decoration_tv})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.second_decoration_tv:
                startActivity(new Intent(mContext,DecorationActivity.class));
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadProgressDialog.closeDialog(mDialog);
    }
}
