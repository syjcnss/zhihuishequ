package com.ovu.lido.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.LoginInfo;
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

public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.edit_phone)
    EditText edit_phone;
    @BindView(R.id.btn_send)
    TextView btn_send;
    @BindView(R.id.edit_code)
    EditText edit_code;


    private String mobileNo;
    private String captcha;
    private MyCountDownTimer myCountDownTimer;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @OnClick({R.id.back_iv,R.id.btn_send, R.id.next_step_btn})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_send:
                if (validatePhone()){

                    getCaptcha(mobileNo);
                }
                break;
            case R.id.next_step_btn:
//                startActivity(new Intent(mContext, AuthenticationActivity.class));
                doNextStep();
                break;
        }
    }

    /**
     * 处理下一步按钮
     */
    private void doNextStep() {
        mobileNo = edit_phone.getText().toString().trim();
        captcha = edit_code.getText().toString().trim();
        if (!validatePhone()) {
            return;
        }
        if (TextUtils.isEmpty(captcha)) {
            showShortToast(getString(R.string.verification_code_empty));
            return;
        }
        userRegister();
    }

    /**
     * 获取验证返回结果
     */
    private void userRegister() {
        OkHttpUtils.post()
                .url(Constant.REGIST_NEXT_URL)
                .addParams("mobile_no",mobileNo)
                .addParams("captcha",captcha)
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
                        LoginInfo info = GsonUtil.GsonToBean(response, LoginInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            Intent intent = new Intent(mContext, AuthenticationActivity.class);
                            intent.putExtra("phoneNum", String.valueOf(mobileNo));
                            startActivity(intent);
                        }else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 获取验证码
     * @param mobileNo
     */
    private void getCaptcha(String mobileNo) {
        String captcha_type = "01";
        OkHttpUtils.get()
                .url(Constant.GET_CAPTCHA_URL)
                .addParams("mobile_no",mobileNo)
                .addParams("captcha_type",captcha_type)
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
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            if (myCountDownTimer != null){
                                myCountDownTimer.cancel();
                            }
                            myCountDownTimer = new MyCountDownTimer(60000,1000,btn_send);
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
     * @return
     */
    private boolean validatePhone() {
        mobileNo = edit_phone.getText().toString().trim();
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
