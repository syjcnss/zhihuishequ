package com.ovu.lido.ui;

import android.app.Dialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.JSONUtil;
import com.ovu.lido.util.RefreshConstant;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 家庭情况--紧急联系人 编辑
 */
public class EditEmergencyContactsActivity extends BaseActivity {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.contacts_ll)
    LinearLayout contacts_ll;
    @BindView(R.id.add_btn)
    Button add_btn;
    private List<FamilySituationInfo.DataBean.EmergencyContactsBean> contactsInfoList = new ArrayList<>();
    private List<FamilySituationInfo.DataBean.EmergencyContactsBean> emergencyContacts = new ArrayList<>();
    private Dialog mDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_edit_emergency_contacts;
    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
    }

    @Override
    protected void loadData() {
        super.loadData();
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
                            List<FamilySituationInfo.DataBean.EmergencyContactsBean> emergencyContacts = situationInfo.getData().getEmergencyContacts();
                            contactsInfoList.addAll(emergencyContacts);

                            if (contactsInfoList.size() <= 0) {
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

    @Override
    protected void setListener() {
        super.setListener();

    }

    @OnClick({R.id.back_iv, R.id.save_tv, R.id.add_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.save_tv:
                if (isAvailable()) {//是完整的
                    saveEmergencyContactsInfo(emergencyContacts.toString());
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
        FamilySituationInfo.DataBean.EmergencyContactsBean contactsBean = new FamilySituationInfo.DataBean.EmergencyContactsBean();
        contactsBean.setContact_name("");
        contactsBean.setContact_tel("");
        contactsInfoList.add(contactsBean);
    }

    /**
     * 保存紧急联系人信息
     */
    private void saveEmergencyContactsInfo(String emergencyContacts) {
        OkGo.<String>post(Constant.SAVE_EMERGENCY_CONTACT)
                .params("token", AppPreference.I().getString("token", ""))
                .params("resident_id", AppPreference.I().getString("resident_id", ""))
                .params("emergencyContacts", emergencyContacts)
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
     * @return 是否完善
     */
    private boolean isAvailable() {
        emergencyContacts.clear();
        for (int i = 0; i < contactsInfoList.size(); i++) {
            FamilySituationInfo.DataBean.EmergencyContactsBean bean = contactsInfoList.get(i);
            boolean isAllEmpty = TextUtils.isEmpty(bean.getContact_name()) && TextUtils.isEmpty(bean.getContact_tel());
            boolean isAllNotEmpty = !TextUtils.isEmpty(bean.getContact_name()) && !TextUtils.isEmpty(bean.getContact_tel());
            if (isAllNotEmpty) {
                emergencyContacts.add(bean);
            }
            if (!(isAllEmpty || isAllNotEmpty)) {
                Toast.makeText(mContext, "请填写第" + (i + 1) + "个联系人的全部信息", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    /**
     * 添加联系人布局
     */
    private void refreshLayout() {
        contacts_ll.removeAllViews();
        for (int i = 0; i < contactsInfoList.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.emergency_contacts_edit_item, contacts_ll, false);
            EditText contact_name_et = view.findViewById(R.id.contact_name_et);
            EditText contact_tel_et = view.findViewById(R.id.contact_tel_et);
            contact_name_et.setInputType(InputType.TYPE_CLASS_TEXT);
            contact_tel_et.setInputType(InputType.TYPE_CLASS_NUMBER);

            final FamilySituationInfo.DataBean.EmergencyContactsBean bean = contactsInfoList.get(i);
            contact_name_et.setText(bean.getContact_name());//联系人名字
            contact_tel_et.setText(bean.getContact_tel());//联系人电话

            contact_name_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    bean.setContact_name(s.toString());
                }
            });
            contact_tel_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    bean.setContact_tel(s.toString());
                }
            });

            contacts_ll.addView(view);
        }

        add_btn.setVisibility(contacts_ll.getChildCount() >= 2 ? View.GONE : View.VISIBLE);
    }


}
