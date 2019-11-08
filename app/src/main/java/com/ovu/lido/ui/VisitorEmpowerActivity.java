package com.ovu.lido.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.Contacts;
import com.ovu.lido.bean.DoorInfo;
import com.ovu.lido.bean.UpLoadInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.SelectDoorDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 访客授权
 */
public class VisitorEmpowerActivity extends BaseActivity {
    private static final int READ_CODE = 1235;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.phone_et)
    EditText phone_et;
    @BindView(R.id.select_text)
    TextView select_text;

    private List<DoorInfo.DataBean> doorList = new ArrayList<>();
    private SelectDoorDialog dialog;
    private StringBuffer ids;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_visitor_empower;
    }

    @Override
    protected void initView() {
        super.initView();
        dialog = new SelectDoorDialog(mContext, name_tv, doorList);
    }

    @Override
    protected void loadData() {
        super.loadData();
        getDoorList();
    }

    private void getDoorList() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        String params = ViewHelper.getParams(paramsMap);
        OkHttpUtils.post()
                .url(Constant.GET_DOOR_LIST)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "访客授权--门禁列表数据: " + response);
                        DoorInfo info = GsonUtil.GsonToBean(response, DoorInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            doorList.clear();
                            doorList.addAll(info.getData());
                        } else {
                            ToastUtil.show(mContext, info.getErrorMsg());
                        }
                    }
                });
    }

    @OnClick({R.id.back_iv, R.id.submit_btn, R.id.select_address, R.id.access_list_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.select_address://电话本
                ActivityCompat.requestPermissions(VisitorEmpowerActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, READ_CODE);

                break;
            case R.id.access_list_tv://授权门禁
                ViewHelper.hideSoftInput(this);
                dialog.showFilterWindow();
                dialog.setRefreshData(new SelectDoorDialog.RefreshData() {

                    @Override
                    public void loadData(List<DoorInfo.DataBean> entity) {
                        Log.i(TAG, entity.size() + "");
                        select_text.setText("");
                        ids = new StringBuffer();
                        for (int i = 0; i < entity.size(); i++) {
                            if (i == 0) {
                                ids.append(entity.get(i).getId());
                                select_text.append(entity.get(i).getName());
                            } else {
                                ids.append(",");
                                ids.append(entity.get(i).getId());
                                select_text.append("/" + entity.get(i).getName());
                            }
                        }

                        Log.i(TAG, "ids is ----------" + ids);
                    }
                });
                break;
            case R.id.submit_btn:
                doSubmit();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case READ_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, 1);
                } else {
                    showToast("请开启权限");
                }
                break;

        }
    }


    private void doSubmit() {
        String name = name_tv.getText().toString().trim();
        String phone = phone_et.getText().toString().trim();
        String selectTxt = select_text.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showShortToast("请填写访客姓名");
        } else if (TextUtils.isEmpty(phone)) {
            showShortToast("请填写访客电话");
        } else if (TextUtils.isEmpty(selectTxt)) {
            showShortToast("请选择授权门禁");
        } else {
            inviteGuest(name, phone);
        }
    }

    /**
     * 请求邀请数据
     */
    private void inviteGuest(String name, String phone) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        paramsMap.put("appType", "1");
        paramsMap.put("phone", phone);
        paramsMap.put("name", name);
        paramsMap.put("token", AppPreference.I().getString("token", ""));
        paramsMap.put("ids", ids.toString());
        String params = ViewHelper.getParams(paramsMap);
        OkHttpUtils.post()
                .url(Constant.INVITE_GUEST)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "邀请数据: " + response);
                        String errorCode = getErrorCode(response);
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else {
                            UpLoadInfo info = GsonUtil.GsonToBean(response, UpLoadInfo.class);
                            if (errorCode.equals(Constant.RESULT_OK)) {
                                showShortToast("开门通行码已发送至访客手机");
                                finish();
                            } else {
                                showShortToast(info.getErrorMsg());
                            }
                        }

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Uri contactData = data.getData();
                    Contacts contacts = ViewHelper.getContactPhone2(this, contactData);
                    String[] tels = contacts.getTel();
                    String names = contacts.getName();
                    if (tels == null || tels.length < 0) {
                        Log.i(TAG, "select contact is null");
                        return;
                    }
                    String tempTel = tels[0];
                    if (TextUtils.isEmpty(tempTel)) {
                        Log.i(TAG, "tempTel is null");
                        return;
                    }
                    // 如果是以+86开头，去掉+86
                    String TEL_PREFIX = "+86";
                    if (tempTel.startsWith(TEL_PREFIX)) {
                        tempTel = tempTel.substring(TEL_PREFIX.length());
                    }
                    // 去掉所有的空格和分隔符-
                    String showTel = tempTel.replaceAll("\\s*", "").replaceAll("-*",
                            "");

                    if (!ViewHelper.isMobileNO(showTel)) {
                        showShortToast("请选择正确的手机号码");
                        return;
                    } else {
                        phone_et.setText(showTel);
                        if (!TextUtils.isEmpty(names)) {
                            name_tv.setText(names);
                        }
                    }

                    break;
            }
        }
    }
}
