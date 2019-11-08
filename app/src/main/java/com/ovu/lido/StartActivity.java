package com.ovu.lido;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.LoginInfo;
import com.ovu.lido.ui.EnterMethodActivity;
import com.ovu.lido.ui.GuideActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.JPushHelper;
import com.ovu.lido.util.Network;
import com.ovu.lido.util.TagAliasOperatorHelper;
import com.ovu.lido.widgets.ConfirmDialog;
import com.sdu.didi.openapi.DIOpenSDK;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

import static com.ovu.lido.util.TagAliasOperatorHelper.ACTION_SET;
import static com.ovu.lido.util.TagAliasOperatorHelper.sequence;

public class StartActivity extends BaseActivity {

    private String phoneNum;
    private String password;
    private String from;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            toNextPage();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initView() {
        super.initView();
        // 友盟统计
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);// 禁止默认的页面统计方式

        // 注册滴滴
        DIOpenSDK.registerApp(this, Constant.DIDI_appid, Constant.DIDI_secret);

        //判断是否需要动态申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//需要

            if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION)) {//拥有存储权限

                //延时进入下一页
                handler.sendEmptyMessageDelayed(0,1000);

            } else {//没有存储权限

                //申请存储权限和位置权限
                requestPermission(Constant.STORAGE_PERMISSION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }else {//不需要

            handler.sendEmptyMessageDelayed(0,1000);
        }

    }

    @Override
    protected void loadData() {
        super.loadData();

    }

    private void toNextPage() {
        boolean isFirstOpen = AppPreference.I().getBoolean("isFirstOpen", true);
        phoneNum = AppPreference.I().getString("phoneNum", "");
        password = AppPreference.I().getString("password", "");

        if (isFirstOpen) {
            startActivity(new Intent(mContext, GuideActivity.class));
            finish();
        } else {
            if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(password)) {
                startActivity(new Intent(mContext, EnterMethodActivity.class));
                finish();
            } else {
                LoginAttempt();
            }

        }
    }

    /**
     * 尝试登陆
     */
    private void LoginAttempt() {
        OkHttpUtils.post()
                .url(Constant.LOGIN_URL)
                .addParams("mobile_no", phoneNum)
                .addParams("password", password)
                .addParams("login_type", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (isFinishing() || mContext == null) {
                            return;
                        }
                        if (!Network.isAvailable(mContext)){
                            showToast("网络不可用");
                        }
                        startActivity(new Intent(mContext, EnterMethodActivity.class));
                        finish();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "尝试记住密码登陆数据: " + response);
                        String errorCode = getErrorCode(response);
                        String errorMsg = getErrorMsg(response);
                        if (errorCode.equals(Constant.RESULT_OK)) {


                            LoginInfo info = GsonUtil.GsonToBean(response, LoginInfo.class);
                            AppPreference.I().putString("phoneNum", phoneNum);
                            AppPreference.I().putString("password", password);
                            AppPreference.I().putString("token", info.getToken());
                            AppPreference.I().putString("community_name", info.getCommunity_name());
                            AppPreference.I().putString("community_id", info.getCommunity_id());
                            AppPreference.I().putString("resident_id", info.getResident_id());
                            //设置JPush别名
//                            TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
//                            tagAliasBean.alias = info.getResident_id() + "dev";
//                            tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
//                            tagAliasBean.isAliasAction = true;
//                            TagAliasOperatorHelper.getInstance().handleAction(getApplicationContext(),sequence,tagAliasBean);
                            JPushHelper.setAlias(mContext,info.getResident_id());

                            if (!TextUtils.isEmpty(from) && from.equals("receiver")) {
                                startActivity(new Intent(mContext, MainActivity.class).putExtra("from", from));
                            } else {
                                startActivity(new Intent(mContext, MainActivity.class));
                            }
                            finish();
                        } else {
                            showShortToast(errorMsg);
                            startActivity(new Intent(mContext, EnterMethodActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.STORAGE_PERMISSION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED ) {//存储权限
                        // 权限请求成功
                        toNextPage();
                    } else {
                        // 权限请求失败
                        if (shouldShowRequestPermissionRationale(permissions[0])) {// 权限申请失败，判断是否需要弹窗解释原因
                            ConfirmDialog dialog = new ConfirmDialog(this, new ConfirmDialog.OnConfirmEvent() {
                                @Override
                                public void onCancel() {
                                }

                                @Override
                                public void onConfirm() {
                                    //申请存储和位置权限
                                    requestPermission(Constant.STORAGE_PERMISSION, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION);
                                }
                            });
                            dialog.show();
                            dialog.setContentText("存储空间：缓存数据/应用配置等文件");
                            dialog.setTitleText("\"i丽岛\"需要获得以下权限，才可正常使用");
                            dialog.setOkText("下一步");
                            dialog.setCancelVisible(View.GONE);
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);
                        } else {// 权限申请失败，并且勾选不再提示，弹窗解释原因并引导进入设置界面设置权限
                            ConfirmDialog dialog = new ConfirmDialog(this, new ConfirmDialog.OnConfirmEvent() {
                                @Override
                                public void onCancel() {
                                    finish();
                                }

                                @Override
                                public void onConfirm() {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    //跳转到设置界面调用的是 startActivityForResult()
                                    startActivityForResult(intent, Constant.STORAGE_PERMISSION);
                                }

                            });
                            dialog.show();
                            dialog.setContentText("获取权限后\n应用不会读取您的个人信息\n请在 权限管理 中设置开启");
                            dialog.setTitleText("\"i丽岛\"需要获得以下权限，才可正常使用");
                            dialog.setOkText("前往设置");
                            dialog.setCancelText("退出i丽岛");
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);
                        }
                    }

                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         *  从 权限设置 返回：
         * 1、没有必要对 resultCode 进行判断，因为用户只能通过返回键才能回到我们的 App 中，所以 resultCode 总是为 RESULT_CANCEL
         * 2、还需要对权限进行判断，因为用户有可能没有授权就返回了！
         */
        switch (requestCode) {
            case Constant.STORAGE_PERMISSION:
                if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//权限申请成功
                    //进入下一页
                    toNextPage();

                } else {//权限申请失败
                    //申请存储和位置权限
                    requestPermission(Constant.STORAGE_PERMISSION, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION);
                }
                break;
        }
    }
}
