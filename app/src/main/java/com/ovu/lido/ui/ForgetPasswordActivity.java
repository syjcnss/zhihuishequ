package com.ovu.lido.ui;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.UpLoadInfo;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.MyCountDownTimer;
import com.ovu.lido.util.Network;
import com.ovu.lido.util.ViewHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 找回密码
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.user_tel)
    EditText user_tel;
    @BindView(R.id.edit_code)
    EditText edit_code;
    @BindView(R.id.btn_send)
    TextView btn_send;
    @BindView(R.id.new_password)
    EditText new_password;
    @BindView(R.id.new_password_2)
    EditText new_password2;

    private MyCountDownTimer myCountDownTimer;
    private String mobileNo;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).statusBarDarkFont(true, 0.2f).init();
    }

    @OnClick({R.id.back_iv, R.id.btn_send, R.id.btn_ok})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_send://发送验证码
                if (validatePhone()) {
                    getCaptcha(mobileNo);
                }
                break;
            case R.id.btn_ok:
                doSubmit();
                break;
        }
    }

    /**
     * 处理确定按钮
     */
    private void doSubmit() {
        String userTel = user_tel.getText().toString().trim();
        String verificationCode = edit_code.getText().toString().trim();
        String newPassword = new_password.getText().toString().trim();
        String newPassword2 = new_password2.getText().toString().trim();
        if (TextUtils.isEmpty(userTel)){
            showToast("请输入手机号");
            return;
        }
        if (StringUtils.isEmpty(verificationCode)) {
            showToast("请输入验证码");
            return;
        }
        if (StringUtils.isEmpty(newPassword)) {
            showToast("请输入新密码");
            return;
        }
        if (newPassword.length() < 6) {
            showToast("密码最少6位");
            return;
        }
        if (StringUtils.isEmpty(newPassword2)) {
            showToast("请再次输入新密码");
            return;
        }
        if (!TextUtils.equals(newPassword, newPassword2)) {
            showToast("两次输入的密码不一致");
            return;
        }
        modifyPassword(userTel, newPassword, verificationCode);
    }

    /**
     * 修改密码
     */
    private void modifyPassword(String userTel, String newPassword, String verificationCode) {
        OkHttpUtils.post()
                .url(Constant.MODIFY_PASSWORD_URL)
                .addParams("modify_type", "3")
                .addParams("mobile_no", userTel)
                .addParams("new_password", newPassword)
                .addParams("password", verificationCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (!Network.isAvailable(mContext)){
                            showToast("网络不可用");
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "jsonStr: " + response);
                        UpLoadInfo info = GsonUtil.GsonToBean(response, UpLoadInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            showShortToast("修改密码成功");
                            finish();
                        } else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 获取验证码
     *
     * @param mobileNo
     */
    private void getCaptcha(String mobileNo) {
        String captcha_type = "04";
        OkHttpUtils.get()
                .url(Constant.GET_CAPTCHA_URL)
                .addParams("mobile_no", mobileNo)
                .addParams("captcha_type", captcha_type)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i(TAG, "onError: " + e);
                        call.cancel();
                        if (!Network.isAvailable(mContext)){
                            showToast("网络不可用");
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        UpLoadInfo info = GsonUtil.GsonToBean(response, UpLoadInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            if (myCountDownTimer != null) {
                                myCountDownTimer.cancel();
                            }
                            myCountDownTimer = new MyCountDownTimer(60000, 1000, btn_send);
                            myCountDownTimer.start();
                            showShortToast("发送成功");
                        } else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 判断手机号有效性
     *
     * @return
     */
    private boolean validatePhone() {
        mobileNo = user_tel.getText().toString().trim();
        Log.i(TAG, "validatePhone: " + mobileNo);
        if (TextUtils.isEmpty(mobileNo)) {
            showShortToast(getString(R.string.error_field_required));
            return false;
        }
        if (!ViewHelper.isMobileNO(mobileNo)) {
            showShortToast(getString(R.string.error_invalid_email));
            return false;
        }
        return true;
    }
}
