package com.ovu.lido.ui;

import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.CommonBean;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 意见反馈
 */
public class FeedBackActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.et_content)
    TextView content;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void setListener() {
        iv_back.setOnClickListener(this);
        findViewById(R.id.tv_send_msg).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_send_msg:
                submitAdvice();
                break;
        }
    }

    private void submitAdvice() {
        String advice = content.getText().toString();
        if (TextUtils.isEmpty(advice)) {
            showToast("建议不能为空");
            return;
        }
        String version = "";
        try {
            version = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.SIGNATURE_MATCH).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        OkHttpUtils.post()
                .url(HttpAddress.USER_FEEDBACK)
                .addParams("token", AppPreference.I().getString("token",""))
                .addParams("resident_id",  AppPreference.I().getString("resident_id",""))
                .addParams("content", advice)
                .addParams("version", version)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, "请求信息:" + response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(FeedBackActivity.this);
                            return;
                        }
                        CommonBean bean = GsonUtil.GsonToBean(response, CommonBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            showToast("反馈成功");
                            finish();
                        }
                    }
                });

    }
}
