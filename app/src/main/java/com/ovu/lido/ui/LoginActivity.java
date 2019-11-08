package com.ovu.lido.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.MainActivity;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.LoginInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.JPushHelper;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.Network;
import com.ovu.lido.util.TagAliasOperatorHelper;
import com.ovu.lido.util.TaskHelper;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

import static com.ovu.lido.util.TagAliasOperatorHelper.sequence;

/**
 * 登陆
 */
public class LoginActivity extends BaseActivity implements OnClickListener {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.phone_et)
    EditText phone_et;
    @BindView(R.id.password_et)
    EditText password_et;

    private Dialog mDialog;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).statusBarDarkFont(true, 0.2f)
                .keyboardEnable(true).init();
    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
    }

    @Override
    protected void setListener() {
        super.setListener();

    }

    @Override
    protected void loadData() {
        super.loadData();

    }

    @OnClick({R.id.back_iv, R.id.forget_password_tv, R.id.login_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.login_btn://登陆
                attemptLogin();
                break;
            case R.id.forget_password_tv://忘记密码
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;

        }
    }

    /**
     * 尝试登录或注册登录表单指定的帐户。
     * 如果出现表单错误（无效的电子邮件，缺少字段等），
     * 将显示错误，并且不会进行实际的登录尝试。
     */
    private void attemptLogin() {
        // 在登录尝试时存储值。
        String phone = phone_et.getText().toString().trim();
        String password = password_et.getText().toString();

        // 检查一个有效的电子邮件地址。
        if (TextUtils.isEmpty(phone)) {
            showShortToast("请输入手机号");
        } else if (!ViewHelper.isMobileNO(phone)) {
            showShortToast("此号码无效");
        }else if (TextUtils.isEmpty(password)) {
            showShortToast("请输入密码");
        } else if (!isPasswordValid(password)) {
            showShortToast("该密码太短");
        }else {
            // 执行用户登录尝试
            LoginAttempt(phone, password);

        }
    }

    /**
     * 尝试登陆
     * @param phone
     * @param password
     */
    private void LoginAttempt(final String phone, final String password) {
        mDialog.show();
        OkGo.<String>post(Constant.LOGIN_URL)
                .params("mobile_no", phone)
                .params("password", password)
                .params("login_type", "1")
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                        if (!Network.isAvailable(mContext)){
                            showToast("网络不可用");
                        }
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "尝试登陆-onSuccess: " + response.body());
                        LoadProgressDialog.closeDialog(mDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            Type type = new TypeToken<LoginInfo>() {
                            }.getType();
                            LoginInfo info = new Gson().fromJson(response.body(),type);
                            AppPreference.I().putString("phoneNum", phone);
                            AppPreference.I().putString("password", password);
                            AppPreference.I().putString("token", info.getToken());
                            AppPreference.I().putString("community_id", info.getCommunity_id());
                            AppPreference.I().putString("community_name",info.getCommunity_name());
                            AppPreference.I().putString("resident_id", info.getResident_id());

                            //设置JPush别名
                            JPushHelper.setAlias(mContext,info.getResident_id());

                            Intent intent = new Intent(mContext, MainActivity.class);
                            TaskHelper.finishAffinity((Activity) mContext, intent);
                        } else {
                            showShortToast(errorMsg);
                        }
                    }
                });
    }

    private boolean isPasswordValid(String password) {
        //用你自己的逻辑代替这个
        return password.length() > 4;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LoadProgressDialog.closeDialog(mDialog);

    }

}

