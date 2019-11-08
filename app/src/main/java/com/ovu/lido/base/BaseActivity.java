package com.ovu.lido.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.ovu.lido.R;
import com.ovu.lido.ui.EnterMethodActivity;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.ImmersionBar;
import com.ovu.lido.util.SoftHideKeyBoardUtil;
import com.ovu.lido.util.TaskHelper;
import com.ovu.lido.widgets.CommonAction;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppActivity {
    private int stackIndex;// 从0开始

    protected static final String TAG = "wangw";
    public final String Tag = getClass().getSimpleName();
    public Context mContext;
    private Unbinder unbinder;
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        CommonAction.getInstance().addActivity(this);
        stackIndex = AppActivity.getInstance().activityStackIndex++;
        AppActivity.getInstance().activityStack.put(stackIndex, this);
        mContext = this;
        //绑定控件
        unbinder = ButterKnife.bind(this);
        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar();
       SoftHideKeyBoardUtil.assistActivity(this);
        initView();
        //设置监听
        setListener();
        loadData();
    }

    protected abstract int setLayoutId();

    /**
     * 初始化数据
     */
    protected void loadData() {

    }

    /**
     * 初始化控件
     */
    protected void initView() {

    }

    /**
     * 设置监听
     */
    protected void setListener() {

    }

    /**
     * 弹出提示 -- 短
     *
     * @param str 提示文字
     */
    public void showShortToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(true).init();
    }

    /**
     * 权限检查方法，false代表没有该权限，ture代表有该权限
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 权限请求方法
     */
    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }



    /**
     * 获取状态码
     * @param response 数据源
     * @return 状态码
     */
    public String getErrorCode(String response) {
        String errorCode = "";
        //创建容器
        JSONObject rootObject = null;
        try {
            rootObject = new JSONObject(response);
            errorCode = rootObject.getString("errorCode");
            Log.i(TAG, "errorCode: " + errorCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return errorCode;
    }

    /**
     * 获取状态信息
     * @param response 数据源
     * @return 状态码
     */
    public String getErrorMsg(String response) {
        String errorMsg = "";
        //创建容器
        JSONObject rootObject = null;
        try {
            rootObject = new JSONObject(response);
            errorMsg = rootObject.getString("errorMsg");
            Log.i(TAG, "errorMsg: " + errorMsg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return errorMsg;
    }

    /**
     * 判断token是否有效
     * @param response 数据源
     * @return 是否有效
     */
    public boolean isTokenError(String response){
        String errorCode = getErrorCode(response);
        //如果状态码为TOKEN_ERROR，说明token失效
        if (errorCode.equals(Constant.TOKEN_ERROR)){
            return true;
        }
        return false;
    }

    /**
     * 关闭所有activity，并跳转至登陆页
     */
    public void startLoginActivity() {
        Intent intent = new Intent(mContext, EnterMethodActivity.class);
        TaskHelper.finishAffinity((Activity) mContext,intent);
        showShortToast(getString(R.string.token_error));
    }

}
