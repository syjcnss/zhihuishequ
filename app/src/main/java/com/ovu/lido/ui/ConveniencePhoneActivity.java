package com.ovu.lido.ui;

import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.adapter.PhoneLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.ConveniencePhoneInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 贴心管家 -- 便民电话
 */
public class ConveniencePhoneActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;

    @BindView(R.id.phone_lv)
    ListView phone_lv;
    private PhoneLvAdapter mPhoneLvAdapter;
    private List<ConveniencePhoneInfo.DataBean> phoneInfos = new ArrayList<>();
    private Dialog mDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f);
        mImmersionBar.titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_convenience_phone;
    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
        mPhoneLvAdapter = new PhoneLvAdapter(mContext, phoneInfos);
        phone_lv.setAdapter(mPhoneLvAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mPhoneLvAdapter.setOnClickListener(new PhoneLvAdapter.OnPhoneBtnClickListener() {
            @Override
            public void click(View view, int position) {
                ViewHelper.toDialView(mContext, phoneInfos.get(position).getPhone());
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        mDialog.show();
        OkHttpUtils.post()
                .url(Constant.CONVENIENCE_PHONE_URL)
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
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
                        Log.i(TAG, "便民电话数据: " + response);
                        LoadProgressDialog.closeDialog(mDialog);

                        ConveniencePhoneInfo info = GsonUtil.GsonToBean(response, ConveniencePhoneInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            phoneInfos.addAll(info.getData());
                            mPhoneLvAdapter.notifyDataSetChanged();
                        } else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });

    }
    @OnClick(R.id.back_iv)
    public void onClick (View view){
        switch (view.getId()){
            case R.id.back_iv:
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
