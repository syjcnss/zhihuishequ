package com.ovu.lido.ui;

import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.MainActivity;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.AttestationInfo;
import com.ovu.lido.bean.BuildingInfo;
import com.ovu.lido.bean.CertificationInfo;
import com.ovu.lido.bean.CityInfo;
import com.ovu.lido.bean.CommunityInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.JPushHelper;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.TaskHelper;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.ChangeTextViewSpace;
import com.ovu.lido.widgets.ListSelectorDialog;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AuthenticationActivity extends BaseActivity implements View.OnClickListener, ListSelectorDialog.OnAddressSelectedListener {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.city_tv)
    TextView city_tv;//城市名称
    @BindView(R.id.community_tv)
    TextView community_tv;//小区名称
    @BindView(R.id.building_tv)
    TextView building_tv;//楼栋名称
    @BindView(R.id.unit_tv)
    TextView unit_tv;//单元名称
    @BindView(R.id.room_tv)
    TextView room_tv;//房间名称
    @BindView(R.id.phone_start)
    ChangeTextViewSpace phone_start;
    @BindView(R.id.phone_layout)
    LinearLayout phone_layout;
    @BindView(R.id.phone_end)
    ChangeTextViewSpace phone_end;
    @BindView(R.id.btn_submit)
    TextView btn_submit;

    private ListSelectorDialog mAddressDialog;
    private int mCode = 0;
    private String zoneCode;
    private String communityId;
    private String buildingNo;
    private String unitNo;
    private String roomName;
    private String checkMobileNo;
    private String phoneNum;//注册方式添加房产传入的手机号

    @Override
    protected int setLayoutId() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected void initView() {
        super.initView();
        mAddressDialog = new ListSelectorDialog(mContext);
        mAddressDialog.setOnAddressSelectedListener(this);

    }

    @Override
    protected void loadData() {
        super.loadData();
        mCode = getIntent().getIntExtra("code", 0);
        phoneNum = getIntent().getStringExtra("phoneNum");
        if (TextUtils.isEmpty(phoneNum)) {
            phoneNum = AppPreference.I().getString("phoneNum", "");
            btn_submit.setText("添加");
        }
        hintCertificationTelView();
    }

    @Override
    protected void setListener() {
        super.setListener();

    }

    /**
     * 地址选择监听
     *
     * @param cityInfo
     * @param communityInfo
     * @param buildingInfo
     * @param unitInfo
     * @param roomName
     */
    @Override
    public void onAddressSelected(CityInfo cityInfo, CommunityInfo.CommunityListBean communityInfo, BuildingInfo.BuildingListBean buildingInfo, BuildingInfo.BuildingListBean.UnitNosBean unitInfo, String roomName) {
        hintCertificationTelView();
        String cityName = cityInfo.getItem_name();
        city_tv.setText(cityName);
        zoneCode = cityInfo.getZone_code();
        //社区
        String communityName = "";
        communityId = "";
        if (communityInfo != null) {
            communityName = communityInfo.getItem_name();
            communityId = communityInfo.getId();
        }
        community_tv.setText(communityName);
        //楼栋
        String buildingName = "";
        buildingNo = "";
        if (buildingInfo != null) {
            buildingName = buildingInfo.getItem_name();
            buildingNo = buildingInfo.getBuilding_no();
        }
        building_tv.setText(buildingName);
        //单元
        String unitName = "";
        unitNo = "";
        if (unitInfo != null) {
            unitName = unitInfo.getItem_name();
            unitNo = unitInfo.getUnit_no();
        }
        unit_tv.setText(unitName);
        //房间
        this.roomName = "";
        if (!TextUtils.isEmpty(roomName)) {
            this.roomName = roomName;
            room_tv.setText(this.roomName);
            queryCertificationInfo();
        }
        room_tv.setText(this.roomName);
    }

    @OnClick({R.id.back_iv, R.id.city_tv, R.id.community_tv, R.id.building_tv, R.id.unit_tv, R.id.room_tv, R.id.btn_submit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.city_tv://城市
                if (mAddressDialog == null) {
                    mAddressDialog = new ListSelectorDialog(mContext);
                    mAddressDialog.setOnAddressSelectedListener(this);
                }
                mAddressDialog.show();
                break;
            case R.id.community_tv://小区
                if (validate(1)) {
                    if (mAddressDialog == null) {
                        mAddressDialog = new ListSelectorDialog(mContext);
                        mAddressDialog.setOnAddressSelectedListener(this);
                    }
                    mAddressDialog.show();
                }
                break;
            case R.id.building_tv:
                if (validate(2)) {
                    if (mAddressDialog == null) {
                        mAddressDialog = new ListSelectorDialog(mContext);
                        mAddressDialog.setOnAddressSelectedListener(this);
                    }
                    mAddressDialog.show();
                }
                break;
            case R.id.unit_tv:
                if (validate(3)) {
                    if (mAddressDialog == null) {
                        mAddressDialog = new ListSelectorDialog(mContext);
                        mAddressDialog.setOnAddressSelectedListener(this);
                    }
                    mAddressDialog.show();
                }
                break;
            case R.id.room_tv:
                if (validate(4)) {
                    if (mAddressDialog == null) {
                        mAddressDialog = new ListSelectorDialog(mContext);
                        mAddressDialog.setOnAddressSelectedListener(this);
                    }
                    mAddressDialog.show();
                }
                break;
            case R.id.btn_submit:
                if (validate(5)) {
                    doSubmit();
                }
                break;

        }
    }


    /**
     * 处理登陆按钮
     */
    private void doSubmit() {
        if (validatePhone()) {
            StringBuffer sb = new StringBuffer(phone_start.getText().toString());
            for (int i = 0; i < phone_layout.getChildCount(); i++) {
                EditText editText = (EditText) phone_layout.getChildAt(i);
                sb.append(editText.getText().toString().trim());
            }
            sb.append(phone_end.getText().toString());
            //注册认证
            if (mCode == 0) {
                registerToAttestation(sb.toString());
            } else {
                addHouse(sb.toString());
            }
        }
    }

    /**
     * 增加房产
     *
     * @param mobile_no
     */
    private void addHouse(String mobile_no) {
        String resident_id = AppPreference.I().getString("resident_id", "");
        Map<String, String> map = new HashMap<>();
        map.put("building_no", buildingNo);
        map.put("unit_no", unitNo);
        map.put("room_no", roomName);
        map.put("community_id", communityId);
        map.put("phone_no", mobile_no); //业主手机号
        map.put("mobile_no", phoneNum); //登陆时输入的手机号
        map.put("resident_id", resident_id);
        map.put("type", "2");
        String params = ViewHelper.getParams(map);

        OkGo.<String>post(Constant.REGISTER_TO_ATTESTATION_URL)
                .params("params", params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "增加房产-onSuccess: " + response.body());
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            finish();
                        } else {
                            showToast(errorMsg);
                        }
                    }
                });
    }


    /**
     * 注册认证登陆
     *
     * @param mobile_no
     */
    private void registerToAttestation(String mobile_no) {
        Map<String, String> map = new HashMap<>();
        map.put("building_no", buildingNo);
        map.put("unit_no", unitNo);
        map.put("room_no", roomName);
        map.put("community_id", communityId);
        map.put("phone_no", mobile_no); //业主手机号
        map.put("mobile_no", phoneNum); //验证时手机号
        map.put("type", "1");
        String params = ViewHelper.getParams(map);
        OkGo.<String>post(Constant.REGISTER_TO_ATTESTATION_URL)
                .params("params", params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "注册认证-onSuccess: " + response.body());
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            Type type = new TypeToken<AttestationInfo>() {
                            }.getType();
                            AttestationInfo info = new Gson().fromJson(response.body(), type);
                            AppPreference.I().putString("phoneNum", phoneNum);
                            AppPreference.I().putString("password", "123456");
                            AppPreference.I().putString("token", info.getData().getToken());
                            AppPreference.I().putString("community_id", String.valueOf(info.getData().getCommunity_id()));
                            AppPreference.I().putString("community_name", info.getData().getCommunity_name());
                            AppPreference.I().putString("resident_id", String.valueOf(info.getData().getResident_id()));
                            //设置别名
                            JPushHelper.setAlias(mContext,String.valueOf(info.getData().getResident_id()));

                            startMainActivity();
                        } else {
                            showToast(errorMsg);
                        }
                    }
                });
    }

    /**
     * 判断用户填写的手机号和预留的手机号是否一致
     *
     * @return
     */
    private boolean validatePhone() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < phone_layout.getChildCount(); i++) {
            EditText editText = (EditText) phone_layout.getChildAt(i);
            sb.append(editText.getText().toString().trim());
        }

        if (!TextUtils.equals(checkMobileNo, sb)) {
            showShortToast("业主手机号不匹配");
            return false;
        }
        return true;
    }

    /**
     * 是否是当前步骤
     *
     * @param step
     * @return
     */
    private boolean validate(int step) {
        if (step >= 1) {
            if (StringUtils.isEmpty(zoneCode)) {
                showShortToast("请先选择城市");
                return false;
            }
        }
        if (step >= 2) {
            if (StringUtils.isEmpty(communityId)) {
                showShortToast("请先选择小区");
                return false;
            }
        }
        if (step >= 3) {
            if (StringUtils.isEmpty(buildingNo)) {
                showShortToast("请先选择楼栋");
                return false;
            }
        }
        if (step >= 4) {
            if (StringUtils.isEmpty(unitNo)) {
                showShortToast("请先选择单元");
                return false;
            }
        }
        if (step >= 5) {
            if (StringUtils.isEmpty(roomName)) {
                showShortToast("请先选择房间号");
                return false;
            }
        }
        return true;

    }

    /**
     * 隐藏认证电话内容布局
     */
    private void hintCertificationTelView() {
        checkMobileNo = "";
        phone_start.setText("");
        phone_end.setText("");
        phone_layout.removeAllViews();
    }


    /**
     * 查询认证信息
     */
    private void queryCertificationInfo() {
        OkGo.<String>post(Constant.QUERY_CERTIFICATION_URL)
                .params("room_no", roomName)
                .params("community_id", communityId)
                .params("building_no", buildingNo)
                .params("unit_no", unitNo)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "认证信息-onSuccess: " + response.body());
                        String errorCode = getErrorCode(response.body());
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            Type type = new TypeToken<CertificationInfo>() {
                            }.getType();
                            CertificationInfo info = new Gson().fromJson(response.body(), type);
                            String mobile_no = info.getMobile_no();
                            if (TextUtils.isEmpty(mobile_no) || mobile_no.length() != 11) {
                                showToast("预留手机号格式不正确");
                            } else {
                                phone_start.setSpacing(15.0f);
                                phone_start.setText(mobile_no.substring(0, 3));
                                checkMobileNo = mobile_no.substring(3, 7);
                                phone_end.setSpacing(15.0f);
                                phone_end.setText(mobile_no.substring(7,11));
                                showCertificationTelView();
                            }

                        }
                    }
                });
    }

    /**
     * 显示认证手机号
     */
    private void showCertificationTelView() {
        phone_layout.removeAllViews();
        final int length = checkMobileNo.length();
        for (int i = 0; i < length; i++) {
            final int index = i;
            EditText betweenTelEt = (EditText) LayoutInflater.from(mContext).inflate(R.layout.certification_tel_item, phone_layout, false);
            phone_layout.addView(betweenTelEt);
            betweenTelEt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 1 && index < length - 1) {
                        EditText childAt = (EditText) phone_layout.getChildAt(index + 1);
                        childAt.requestFocus();
                    }
                }
            });

            betweenTelEt.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        for (int j = 0; j < length; j++) {
                            EditText childAt = (EditText) phone_layout.getChildAt(j);
                            childAt.setText(null);
                            if (j == 0) {
                                childAt.requestFocus();
                            }
                        }
                    }
                    return false;
                }
            });
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent();
        intent.setClass(mContext, MainActivity.class);
        TaskHelper.finishAffinity((Activity) mContext, intent);
    }

}
