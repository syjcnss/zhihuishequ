package com.ovu.lido.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.FamilySituationInfo;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.JSONUtil;
import com.ovu.lido.util.RefreshConstant;
import com.ovu.lido.widgets.LoadProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.addapp.pickers.picker.DatePicker;
import cn.addapp.pickers.picker.DateTimePicker;

public class EditKinsmanActivity extends BaseActivity {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.kinsman_ll)
    LinearLayout kinsman_ll;
    @BindView(R.id.add_btn)
    Button add_btn;

    private Dialog mDialog;
    private List<FamilySituationInfo.DataBean.KinsmanListBean> kinsmanListBeans = new ArrayList<>();
    private List<FamilySituationInfo.DataBean.KinsmanListBean> kinsmanInfoList = new ArrayList<>();

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_edit_kinsman;
    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = LoadProgressDialog.createLoadingDialog(this);
    }

    @Override
    protected void loadData() {
        super.loadData();
        mDialog.show();
        kinsmanListBeans.clear();
        OkGo.<String>post(Constant.QUERY_RESIDENT_CONTACTS)
                .params("token", AppPreference.I().getString("token", ""))
                .params("resident_id", AppPreference.I().getString("resident_id", ""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LoadProgressDialog.closeDialog(mDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            FamilySituationInfo situationInfo = new Gson().fromJson(response.body(), FamilySituationInfo.class);
                            List<FamilySituationInfo.DataBean.KinsmanListBean> kinsmanList = situationInfo.getData().getKinsmanList();
                            kinsmanListBeans.addAll(kinsmanList);

                            if (kinsmanListBeans.size() <= 0) {
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

    @OnClick({R.id.back_iv, R.id.save_tv, R.id.add_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.save_tv:
                if (isAvailable()) {
                    saveKinsmanInfo(kinsmanInfoList.toString());
                }
                break;
            case R.id.add_btn:
                addDefaultData();
                refreshLayout();
                break;
        }
    }

    /**
     * 添加默认数据
     */
    private void addDefaultData() {
        FamilySituationInfo.DataBean.KinsmanListBean kinsman = new FamilySituationInfo.DataBean.KinsmanListBean();
        kinsman.setKinsman_name("");
        kinsman.setKinsman_relationship("");
        kinsman.setBirth_date("");
        kinsman.setIdentification_number("");
        kinsman.setWork_unit("");
        kinsman.setLink_tel("");
        kinsmanListBeans.add(kinsman);
    }

    /**
     * 保存主要成员信息
     */
    private void saveKinsmanInfo(String kinsmanList) {
        OkGo.<String>post(Constant.SAVE_KINS_MAN)
                .params("token", AppPreference.I().getString("token", ""))
                .params("resident_id", AppPreference.I().getString("resident_id", ""))
                .params("kinsmanList", kinsmanList)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject resp = JSONUtil.toJsonObject(response.body());
                        String errorCode = resp.optString("errorCode");
                        if (errorCode.equals(Constant.RESULT_OK)) {
                            EventBus.getDefault().post(new RefreshEvent(RefreshConstant.EDIT_PERSONAL_INFO_SUCCESS));
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
     *
     * @return 是否完善
     */
    private boolean isAvailable() {
        kinsmanInfoList.clear();
        for (int i = 0; i < kinsmanListBeans.size(); i++) {
            FamilySituationInfo.DataBean.KinsmanListBean bean = kinsmanListBeans.get(i);
            boolean isAllEmpty = TextUtils.isEmpty(bean.getKinsman_name()) && TextUtils.isEmpty(bean.getKinsman_relationship()) && TextUtils.isEmpty(bean.getBirth_date())
                    && TextUtils.isEmpty(bean.getIdentification_number()) && TextUtils.isEmpty(bean.getWork_unit()) && TextUtils.isEmpty(bean.getLink_tel());
            boolean isAllNotEmpty = !TextUtils.isEmpty(bean.getKinsman_name()) && !TextUtils.isEmpty(bean.getKinsman_relationship()) && !TextUtils.isEmpty(bean.getBirth_date())
                    && !TextUtils.isEmpty(bean.getIdentification_number()) && !TextUtils.isEmpty(bean.getWork_unit()) && !TextUtils.isEmpty(bean.getLink_tel());
            if (isAllNotEmpty) {
                kinsmanInfoList.add(bean);
            }
            if (!(isAllEmpty || isAllNotEmpty)) {
                Toast.makeText(mContext, "请填写第" + (i + 1) + "个成员的全部信息", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    /**
     * 更新视图
     */
    private void refreshLayout() {
        kinsman_ll.removeAllViews();
        for (int i = 0; i < kinsmanListBeans.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.kinsman_item, kinsman_ll, false);
            LinearLayout item_ll = view.findViewById(R.id.item_ll);

            EditText kinsman_name_et = view.findViewById(R.id.kinsman_name_et);//姓名
            EditText kinsman_relationship_et = view.findViewById(R.id.kinsman_relationship_et);//与业主关系
            TextView birth_date_tv = view.findViewById(R.id.birth_date_tv);//出生日期
            EditText identification_number_et = view.findViewById(R.id.identification_number_et);//证件号码
            EditText work_unit_et = view.findViewById(R.id.work_unit_et);//工作单位
            EditText link_tel_et = view.findViewById(R.id.link_tel_et);//联系电话

            final FamilySituationInfo.DataBean.KinsmanListBean bean = kinsmanListBeans.get(i);

            kinsman_name_et.setText(bean.getKinsman_name());
            kinsman_relationship_et.setText(bean.getKinsman_relationship());
            birth_date_tv.setText(bean.getBirth_date());
            birth_date_tv.setTag(bean);
            identification_number_et.setText(bean.getIdentification_number());
            work_unit_et.setText(bean.getWork_unit());
            link_tel_et.setText(bean.getLink_tel());

            kinsman_name_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    bean.setKinsman_name(s.toString());
                }
            });
            kinsman_relationship_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    bean.setKinsman_relationship(s.toString());
                }
            });
            birth_date_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //选择生日（格式：yyyy-MM-dd）
                    onYearMonthDayPicker(v);
                }
            });
            identification_number_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    bean.setIdentification_number(s.toString());
                }
            });
            work_unit_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    bean.setWork_unit(s.toString());
                }
            });
            link_tel_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    bean.setLink_tel(s.toString());
                }
            });

            if (i == 0) {
                item_ll.setPadding(AppUtil.dip2px(this, 15), AppUtil.dip2px(this, 12), AppUtil.dip2px(this, 15), 0);
            } else {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) item_ll.getLayoutParams();
                lp.setMargins(0, AppUtil.dip2px(this, 10), 0, 0);
                item_ll.setLayoutParams(lp);
            }
            kinsman_ll.addView(view);
        }
        add_btn.setVisibility(kinsman_ll.getChildCount() >= 3 ? View.INVISIBLE : View.VISIBLE);

    }

    /**
     * 生日选择器
     * @param v
     */
    private void onYearMonthDayPicker(final View v) {
        final DatePicker picker = new DatePicker(this);
        picker.setCanLoop(false);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(1919, 1, 1);
        picker.setRangeEnd(2119, 12, 31);
        picker.setSelectedItem(2019, 9, 16);
        picker.setWeightEnable(true);
        picker.setLineColor(Color.BLACK);
        picker.setSelectedTextColor(Color.BLACK);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                String birthDate = year + "-" + month + "-" + day;
                FamilySituationInfo.DataBean.KinsmanListBean bean = (FamilySituationInfo.DataBean.KinsmanListBean) v.getTag();
                ((TextView) v).setText(birthDate);
                bean.setBirth_date(birthDate);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }


}
