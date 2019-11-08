package com.ovu.lido.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ovu.lido.R;
import com.ovu.lido.ui.EnterMethodActivity;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.ImmersionBar;
import com.ovu.lido.util.TaskHelper;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {


    protected Activity mActivity;
    public Context mContext;
    protected View mRootView;
    public final String TAG = getClass().getSimpleName();

    protected ImmersionBar mImmersionBar;
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutId(), container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        if (isImmersionBarEnabled())
            initImmersionBar();
        initView();
        loadData();
        setListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mImmersionBar != null)
            mImmersionBar.init();
    }

    /**
     * Sets layout id.
     *
     * @return the layout id
     */
    protected abstract int setLayoutId();

    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(true).navigationBarWithKitkatEnable(false).init();
    }


    /**
     * 初始化数据
     */
    protected void loadData() {

    }

    /**
     * view与数据绑定
     */
    protected void initView() {

    }

    /**
     * 设置监听
     */
    protected void setListener() {

    }

    /**
     * 权限检查方法，false代表没有该权限，ture代表有该权限
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 权限请求方法
     */
    public void requestPermission(int code, String... permissions) {
        requestPermissions(permissions, code);
    }


    /**
     * 找到activity的控件
     *
     * @param <T> the type parameter
     * @param id  the id
     * @return the t
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findActivityViewById(@IdRes int id) {
        return (T) mActivity.findViewById(id);
    }

    /**
     * 弹出提示 -- 短
     *
     * @param str 提示文字
     */
    public void showShortToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String str) {
        Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
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
     * 获取状态码
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
            Log.i(TAG, "errorCode: " + errorMsg);
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
