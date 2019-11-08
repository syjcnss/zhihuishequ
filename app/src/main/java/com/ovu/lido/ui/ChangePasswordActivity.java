package com.ovu.lido.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.CommonBean;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.ovu.lido.widgets.CommonAction;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 修改密码
 */
public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.et_now_pass)
    EditText mEt_now_pass;
    @BindView(R.id.et_new_pass)
    EditText mEt_new_pass;
    @BindView(R.id.et_new_pass_again)
    EditText mEt_new_pass_again;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_change_password;
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).statusBarDarkFont(true,0.2f).init();
    }

    @OnClick({R.id.back_iv, R.id.save_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.save_btn:
                changePassWord();
                break;
        }
    }

    private void changePassWord() {
        String pass = mEt_now_pass.getText().toString();
        String newpass = mEt_new_pass.getText().toString();
        String passAgain = mEt_new_pass_again.getText().toString();
        if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(newpass) || TextUtils.isEmpty(passAgain)) {
            showToast("密码不能为空");
            return;
        }
        String password = AppPreference.I().getString("password", "");
        if (!pass.equals(password)) {
            showToast("原始密码错误");
            return;
        }
        if (!newpass.equals(passAgain)) {
            showToast("新密码不一致");
            return;
        }
        if (newpass.length() < 6) {
            showToast("密码最少6位");
            return;
        }
        OkHttpUtils.post()
                .url(HttpAddress.MODIFY_PASSWORD)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .addParams("modify_type", "1")
                .addParams("password", pass)
                .addParams("new_password", newpass)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, "请求信息:" + response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(ChangePasswordActivity.this);
                            return;
                        }
                        CommonBean bean = GsonUtil.GsonToBean(response, CommonBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            showToast("修改成功");
                            startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                            AppPreference.I().putString("password", "");
                            CommonAction.getInstance().OutSign();
                        }
                    }
                });

    }
}
