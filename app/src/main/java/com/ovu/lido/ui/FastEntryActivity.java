package com.ovu.lido.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ovu.lido.R;
import com.ovu.lido.adapter.FastEntryLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.IsJoinOrCheckInfo;
import com.ovu.lido.bean.IsSubmitInfo;
import com.ovu.lido.bean.UpLoadInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.LockManager;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.ConfirmDialog;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

public class FastEntryActivity extends BaseActivity {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.back_iv)
    ImageView back_iv;
    @BindView(R.id.fast_entry_lv)
    ListView fast_entry_lv;

    private Integer[] resIds = {R.drawable.zhinengkaimen, R.drawable.baoshibaoxiu, R.drawable.zaixianjiaofei,
            R.drawable.bianmindianhua, R.drawable.fangkeyaoqing, R.drawable.manyidudiaocha,
            R.drawable.zhuangxiuguanli, R.drawable.ruhuoguanli, R.drawable.toupiao,
            R.drawable.wanggeyuan, R.drawable.shequdangyuan, R.drawable.tongzhigonggao,
            R.drawable.shequliuyan, R.drawable.banshiliucheng, R.drawable.zhiyuanzhefuwu};
    private String[] names = {"智能开门", "报事报修", "在线缴费", "便民电话", "访客邀请", "满意度调查",
            "装修管理", "入伙管理", "投票", "网格员", "社区党员", "通知公告", "社区留言", "办事流程", "志愿者服务"};
    private LockManager lockManager;
    private Dialog mDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_fast_entry;
    }

    @Override
    protected void initView() {
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
        FastEntryLvAdapter fastEntryLvAdapter = new FastEntryLvAdapter(mContext, resIds, names);
        fast_entry_lv.setAdapter(fastEntryLvAdapter);
    }

    @Override
    protected void setListener() {
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fast_entry_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://智能开门
                        openDoor();
                        break;
                    case 1://报事报修
                        startActivity(new Intent(mContext, NewsReportRepairActivity.class));
                        break;
                    case 2://在线缴费
                        startActivity(new Intent(mContext, OnlinePaymentActivity.class));
                        break;
                    case 3://便民电话
                        startActivity(new Intent(mContext, ConveniencePhoneActivity.class));
                        break;
                    case 4://访客邀请
                        startActivity(new Intent(mContext, VisitorEmpowerActivity.class));
                        break;
                    case 5://满意度调查
                        startActivity(new Intent(mContext, SatisfactionSurveyActivity.class));
                        break;
                    case 6://装修管理
                        isSubmit();
                        break;
                    case 7://入伙管理
                        isJoin();
                        break;
                    case 8://投票
                        startActivity(new Intent(mContext,VoteActivity.class));
                        break;
                    case 9://网格员
                        startActivity(new Intent(mContext,GridAdministratorActivity.class));
                        break;
                    case 10://社区党员
                        startActivity(new Intent(mContext,CommunityMembersActivity.class));
                        break;
                    case 11://通知公告
                        startActivity(new Intent(mContext,AnnouncementActivity.class));
                        break;
                    case 12://社区留言
                        startActivity(new Intent(mContext,LeaveCommentsActivity.class));
                        break;
                    case 13://办事流程
                        startActivity(new Intent(mContext,WorkingScheduleActivity.class));
                        break;
                    case 14://志愿者服务
                        startActivity(new Intent(mContext,VolunteerServiceActivity.class));
                        break;
                }
            }
        });

    }

    /**
     * 判断是否已经入伙或验房
     */
    private void isJoin() {
        mDialog.show();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        String params = ViewHelper.getParams(paramsMap);
        OkGo.<String>post(Constant.ISJOIN_OR_ISCHECK)
                .params("params", params)
                .execute(new com.lzy.okgo.callback.StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadProgressDialog.closeDialog(mDialog);


                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            Type type = new TypeToken<IsJoinOrCheckInfo>() {
                            }.getType();
                            IsJoinOrCheckInfo info = new Gson().fromJson(response.body(), type);
                            int is_join = info.getData().getIs_join();
                            int is_check = info.getData().getIs_check();
                            String line = Constant.REQ_URL_PRE + info.getData().getLine();

                            Intent intent = new Intent(mContext, OccupationActivity.class);
                            intent.putExtra("is_join", is_join);
                            intent.putExtra("is_check", is_check);
                            intent.putExtra("line", line);
                            startActivity(intent);

                        } else {
                            ToastUtil.show(mContext, errorMsg);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e(TAG, "onError: " + response.body());
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }
                });
    }

    /**
     * 判断是否已经申请
     */
    private void isSubmit() {
        mDialog.show();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        String params = ViewHelper.getParams(paramsMap);
        OkGo.<String>post(Constant.IS_SUBMIT_DECORATION)
                .params("params", params)
                .execute(new com.lzy.okgo.callback.StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadProgressDialog.closeDialog(mDialog);

                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            Type type = new TypeToken<IsSubmitInfo>() {
                            }.getType();
                            IsSubmitInfo info = new Gson().fromJson(response.body(), type);
                            String status = info.getData().getStatus();//1.提交过  0.没有提交
                            if (TextUtils.equals(status, "1")) {
                                startActivity(new Intent(mContext, DecorationHistoryActivity.class));
                            } else if (TextUtils.equals(status, "0")) {
                                startActivity(new Intent(mContext, DecorationActivity.class));
                            }
                        } else {
                            showShortToast(errorMsg);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.e(TAG, "onError: " + response.body());
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }
                });
    }


    /**
     * 智能开门
     */
    private void openDoor() {
        if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            lockManager = new LockManager(mContext);
            lockManager.showLockListDialog();
        } else {
            requestPermission(Constant.LOCATION_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.LOCATION_PERMISSION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//存储权限
                        // 权限请求成功
                        if (lockManager == null) {
                            lockManager = new LockManager(mContext);
                        }
                        lockManager.showLockListDialog();
                    } else {
                        // 权限请求失败
                        if (shouldShowRequestPermissionRationale(permissions[0])) {// 权限申请失败，判断是否需要弹窗解释原因
                            ConfirmDialog dialog = new ConfirmDialog(mContext, new ConfirmDialog.OnConfirmEvent() {
                                @Override
                                public void onCancel() {
                                }

                                @Override
                                public void onConfirm() {
                                    //申请存储和位置权限
                                    requestPermission(Constant.LOCATION_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION);
                                }
                            });
                            dialog.show();
                            dialog.setContentText("位置权限：用于蓝牙开门");
                            dialog.setTitleText("\"i丽岛\"需要获得以下权限，开门功能才可正常使用");
                            dialog.setOkText("下一步");
                            dialog.setCancelVisible(View.GONE);
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);
                        } else {// 权限申请失败，并且勾选不再提示，弹窗解释原因并引导进入设置界面设置权限
                            ConfirmDialog dialog = new ConfirmDialog(mContext, new ConfirmDialog.OnConfirmEvent() {
                                @Override
                                public void onCancel() {
                                    Toast.makeText(mContext, "未取得您的位置信息使用权限，此功能无法使用。请前往应用权限设置打开权限", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onConfirm() {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                                    intent.setData(uri);
                                    //跳转到设置界面调用的是 startActivityForResult()
                                    startActivityForResult(intent, Constant.LOCATION_PERMISSION);
                                }

                            });
                            dialog.show();
                            dialog.setContentText("获取权限后\n应用不会读取您的个人信息\n请在 权限管理 中设置开启");
                            dialog.setTitleText("\"i丽岛\"需要获得以下权限，开门功能才可正常使用");
                            dialog.setOkText("前往设置");
                            dialog.setCancelText("退出");
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);
                        }
                    }

                }

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         *  从 权限设置 返回：
         * 1、没有必要对 resultCode 进行判断，因为用户只能通过返回键才能回到我们的 App 中，所以 resultCode 总是为 RESULT_CANCEL
         * 2、还需要对权限进行判断，因为用户有可能没有授权就返回了！
         */
        switch (requestCode) {
            case Constant.LOCATION_PERMISSION:
                if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {//权限申请成功
                    //进入下一页
                    if (lockManager == null) {
                        lockManager = new LockManager(mContext);
                    }
                    lockManager.showLockListDialog();
                } else {//权限申请失败
                    //申请存储和位置权限
                    requestPermission(Constant.LOCATION_PERMISSION, Manifest.permission.ACCESS_FINE_LOCATION);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (lockManager != null) {
            lockManager.onDestroy();
        }
        super.onDestroy();
    }
}
