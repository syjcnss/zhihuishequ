package com.ovu.lido.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.FaultTypeLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.FaultTypeInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 故障类型
 */
public class FaultTypeActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.type_list)
    ListView type_list;

    private List<FaultTypeInfo.DataBeanX.DataBean> faultTypeInfos = new ArrayList<>();
    private FaultTypeLvAdapter mFaultTypeLvAdapter;
    private int service_type;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_fault_type;
    }

    @Override
    protected void initView() {
        super.initView();
        service_type = getIntent().getIntExtra("service_type", 0);
        mFaultTypeLvAdapter = new FaultTypeLvAdapter(mContext, faultTypeInfos);
        type_list.setAdapter(mFaultTypeLvAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        type_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("fault_name",faultTypeInfos.get(position).getText());
                intent.putExtra("fault_id",faultTypeInfos.get(position).getId());

                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        OkHttpUtils.post()
                .url(Constant.GET_FAULT_TYPE)
                .addParams("service_type", String.valueOf(service_type))
                .addParams("resident_id", AppPreference.I().getString("resident_id",""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "故障类型数据: " + response);
                        FaultTypeInfo info = GsonUtil.GsonToBean(response, FaultTypeInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            faultTypeInfos.addAll(info.getData().getData());
                            mFaultTypeLvAdapter.notifyDataSetChanged();
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
