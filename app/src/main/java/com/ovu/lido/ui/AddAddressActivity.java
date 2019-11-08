package com.ovu.lido.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.AddressPickTask;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;

/**
 * 添加收货地址
 */
public class AddAddressActivity extends BaseActivity {
    @BindView(R.id.action_bar_ll)
    LinearLayout action_bar_ll;
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.top_title)
    TextView top_title;
    @BindView(R.id.contact_name_et)
    EditText contact_name_et;
    @BindView(R.id.contact_phone_et)
    EditText contact_phone_et;
    @BindView(R.id.area_tv)
    TextView area_tv;
    @BindView(R.id.detail_address_et)
    EditText detail_address_et;
    @BindView(R.id.is_default_cb)
    CheckBox is_default_cb;

    private boolean isDefault = false;
    private String mAddressId;
    //省
    private String mProvinceName;
    private String mProvinceAreaId;
    //市
    private String mCityName;
    private String mCityAreaId;
    //县
    private String mCountyName;
    private String mCountyAreaId;

    private String mTitleName;//标题
    private String mContactName;//联系人
    private String mContactPhone;//联系电话
    private String mDetailAddress;//详细地址


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_ll).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_address;
    }


    @Override
    protected void loadData() {
        super.loadData();
        mAddressId = getIntent().getStringExtra("address_id");
        isDefault = getIntent().getBooleanExtra("is_default", false);
        mContactName = getIntent().getStringExtra("contact_name");
        mContactPhone = getIntent().getStringExtra("contact_phone");
        mProvinceAreaId = getIntent().getStringExtra("province_code");
        mProvinceName = getIntent().getStringExtra("province_name");
        mCityAreaId = getIntent().getStringExtra("city_code");
        mCityName = getIntent().getStringExtra("city_name");
        mCountyAreaId = getIntent().getStringExtra("district_code");
        mCountyName = getIntent().getStringExtra("district_name");
        mDetailAddress = getIntent().getStringExtra("detail");
        mTitleName = getIntent().getStringExtra("title_name");
        initLayout();
    }

    /**
     * 初始化布局视图
     */
    private void initLayout() {
        if (!TextUtils.isEmpty(mTitleName)) {
            top_title.setText(mTitleName);
        }
        if (!TextUtils.isEmpty(mContactName)) {
            contact_name_et.setText(mContactName);
        }
        if (!TextUtils.isEmpty(mContactPhone)) {
            contact_phone_et.setText(mContactPhone);
        }
        if (!TextUtils.isEmpty(mProvinceName) && !TextUtils.isEmpty(mCityName) && !TextUtils.isEmpty(mCountyName)) {
            area_tv.setText(new StringBuffer().append(mProvinceName).append("\t").append(mCityName).append("\t").append(mCountyName));
        }
        if (!TextUtils.isEmpty(mDetailAddress)) {
            detail_address_et.setText(mDetailAddress);
        }
        is_default_cb.setChecked(isDefault);
    }

    @Override
    protected void setListener() {
        super.setListener();
        is_default_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDefault = isChecked;
            }
        });
    }

    @OnClick({R.id.back_iv, R.id.area_tv, R.id.tv_save})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.area_tv://地址选择
                onAddressPicker();
                break;
            case R.id.tv_save:
                addAddress();
                break;
        }
    }


    /**
     * 省份地区选择器
     */
    public void onAddressPicker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                showToast("数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    showToast(province.getAreaName() + city.getAreaName());
                } else {
                    area_tv.setText(province.getAreaName() + " " + city.getAreaName() + " " + county.getAreaName());
                    //省
                    mProvinceName = province.getAreaName();
                    mProvinceAreaId = province.getAreaId();
                    //市
                    mCityName = city.getAreaName();
                    mCityAreaId = city.getAreaId();
                    //县
                    mCountyName = county.getAreaName();
                    mCountyAreaId = county.getAreaId();
                }
            }
        });
        task.execute(TextUtils.isEmpty(mProvinceName) ? "" : mProvinceName, TextUtils.isEmpty(mCityName) ? "" : mCityName, TextUtils.isEmpty(mCountyName) ? "" : mCountyName);
    }


    /**
     * 添加或者修改收货地址
     */
    public void addAddress() {
        mContactName = contact_name_et.getText().toString();
        mContactPhone = contact_phone_et.getText().toString();
        String area = area_tv.getText().toString();
        mDetailAddress = detail_address_et.getText().toString();
        if (TextUtils.isEmpty(mContactName)) {
            showToast("收货人不能为空");
            return;
        }
        if (TextUtils.isEmpty(mContactPhone)) {
            showToast("联系电话不能为空");
            return;
        }
        if (TextUtils.isEmpty(area)) {
            showToast("所在地区不能为空");
            return;
        }
        if (TextUtils.isEmpty(mDetailAddress)) {
            showToast("详细地址不能为空");
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("id", mAddressId);
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map.put("is_default", isDefault ? "1" : "0");//是否默认地址 0.否1.是
        map.put("contact_name", mContactName);//联系人
        map.put("contact_phone", mContactPhone);//联系电话
        map.put("province_code", mProvinceAreaId);//t_c_zone.zone_code
        map.put("province_name", mProvinceName);//省
        map.put("city_code", mCityAreaId);//t_c_zone.zone_code
        map.put("city_name", mCityName);//市
        map.put("district_code", mCountyAreaId);//t_c_zone.zone_code
        map.put("district_name", mCountyName);//区
        map.put("detail", mDetailAddress);//详细地址
        map.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(map);
        OkGo.<String>post(Constant.SAVE_ADDRESS)
                .params("params", params)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "onSuccess: " + response.body());

                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            EventBus.getDefault().post(new RefreshEvent(1000));
                            finish();
                        } else {
                            showToast(errorMsg);
                        }
                    }
                });

    }

}
