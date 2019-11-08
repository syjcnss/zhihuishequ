package com.ovu.lido.ui;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.FamilySituationInfo;
import com.ovu.lido.bean.ResidentCarListInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.JSONUtil;
import com.ovu.lido.widgets.LoadProgressDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 业主车辆情况 编辑
 */
public class EditResidentCarActivity extends BaseActivity {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.cars_ll)
    LinearLayout cars_ll;
    @BindView(R.id.add_btn)
    Button add_btn;

    private Dialog mDialog;
    private List<ResidentCarListInfo.DataBean> carList = new ArrayList<>();
    private List<ResidentCarListInfo.DataBean> carArray = new ArrayList<>();

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_edit_resident_car;
    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
    }

    @Override
    protected void loadData() {
        super.loadData();
        OkGo.<String>post(Constant.QUERY_RESIDENT_CAR_LIST)
                .params("token", AppPreference.I().getString("token", ""))
                .params("resident_id", AppPreference.I().getString("resident_id", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadProgressDialog.closeDialog(mDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            ResidentCarListInfo residentCarListInfo = new Gson().fromJson(response.body(), ResidentCarListInfo.class);
                            List<ResidentCarListInfo.DataBean> cars = residentCarListInfo.getData();
                            carList.addAll(cars);

                            if (carList.size() <= 0) {
                                addDefaultData();
                            }
                            refreshLayout();

                        } else if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else {
                            showToast(errorMsg);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (!isFinishing()) LoadProgressDialog.closeDialog(mDialog);
                    }
                });

    }

    private void addDefaultData() {
        ResidentCarListInfo.DataBean carInfo = new ResidentCarListInfo.DataBean();
        carInfo.setCar_type("");
        carInfo.setPlate_number("");
        carList.add(carInfo);
    }

    @OnClick({R.id.back_iv, R.id.save_tv, R.id.add_btn})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.save_tv:
                if (isAvailable()) {//是完整的
                    saveCarsInfo(carArray.toString());
                }
                break;
            case R.id.add_btn:
                addDefaultData();
                refreshLayout();
                break;
        }
    }

    /**
     * 保存紧急联系人信息
     */
    private void saveCarsInfo(String cars) {
        OkGo.<String>post(Constant.SAVE_CAR_INFO)
                .params("token", AppPreference.I().getString("token", ""))
                .params("resident_id", AppPreference.I().getString("resident_id", ""))
                .params("carArray", cars)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject resp = JSONUtil.toJsonObject(response.body());
                        String errorCode = resp.optString("errorCode");
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            finish();
                        } else if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else {
                            Toast.makeText(mContext, resp.optString("errorMsg"), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 判断用户输入信息的完善性
     * @return 是否完善
     */
    private boolean isAvailable() {
        carArray.clear();
        for (int i = 0; i < carList.size(); i++) {
            ResidentCarListInfo.DataBean bean = carList.get(i);
            boolean isAllEmpty = TextUtils.isEmpty(bean.getCar_type()) && TextUtils.isEmpty(bean.getPlate_number());
            boolean isAllNotEmpty = !TextUtils.isEmpty(bean.getCar_type()) && !TextUtils.isEmpty(bean.getPlate_number());
            if (isAllNotEmpty) {
                carArray.add(bean);
            }
            if (!(isAllEmpty || isAllNotEmpty)) {
                Toast.makeText(mContext, "请填写第" + (i + 1) + "个车辆的全部信息", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    /**
     * 添加车辆布局
     */
    private void refreshLayout() {
        cars_ll.removeAllViews();
        for (int i = 0; i < carList.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.resident_car_edit_item, cars_ll, false);
            EditText car_type_et = view.findViewById(R.id.contact_name_et);
            EditText car_number_et = view.findViewById(R.id.contact_tel_et);

            final ResidentCarListInfo.DataBean bean = carList.get(i);

            car_type_et.setText(bean.getCar_type());
            car_number_et.setText(bean.getPlate_number());

            car_type_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    bean.setCar_type(s.toString());
                }
            });
            car_number_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    bean.setPlate_number(s.toString());
                }
            });

            cars_ll.addView(view);
        }

        add_btn.setVisibility(cars_ll.getChildCount() >= 3 ? View.GONE : View.VISIBLE);
    }
}
