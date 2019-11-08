package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.R;
import com.ovu.lido.adapter.ReceiveAddressLvAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.ReceiveAddressInfo;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.ConfirmDialog;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class ReceiveAddressActivity extends BaseActivity {
    @BindView(R.id.action_bar_ll)
    LinearLayout action_bar_ll;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refresh_layout;
    @BindView(R.id.receive_address_lv)
    ListView receive_address_lv;

    TextView contact_name_tv;
    TextView contact_phone_tv;
    TextView address_tv;
    CheckBox is_default_cb;

    private int mPageNo = 0;
    private Dialog mDialog;
    private int mTotalCount;
    private ReceiveAddressLvAdapter mAdapter;
    private List<ReceiveAddressInfo.Data.ThirdAddressPageBean.DataBean> thirdAddressList = new ArrayList<>();
    private int mAddressType = -1;//0.仅自营 1.自营+三方 2.仅三方
    private boolean mCanSelected = false;
    private View mView;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_ll).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_receive_address;
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);

        mAdapter = new ReceiveAddressLvAdapter(mContext, R.layout.receive_address_rv_item, thirdAddressList);
        initHeader();
        receive_address_lv.setAdapter(mAdapter);
    }

    /**
     * 初始化ListView头部
     */
    private void initHeader() {
        mView = LayoutInflater.from(mContext).inflate(R.layout.receive_address_rv_item, null);
        contact_name_tv = mView.findViewById(R.id.contact_name_tv);
        contact_phone_tv = mView.findViewById(R.id.contact_phone_tv);
        address_tv = mView.findViewById(R.id.address_tv);
        is_default_cb = mView.findViewById(R.id.is_default_cb);
        is_default_cb.setText("自营默认地址");
        mView.findViewById(R.id.delete_tv).setVisibility(View.GONE);
        mView.findViewById(R.id.edit_tv).setVisibility(View.GONE);

        receive_address_lv.addHeaderView(mView);
    }

    @Override
    protected void loadData() {
        super.loadData();
        mAddressType = getIntent().getIntExtra("mAddressType", -1);
        mCanSelected = getIntent().getBooleanExtra("canSelected", false);
        mDialog.show();
        getAddressData();
    }

    @Override
    protected void setListener() {
        super.setListener();
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mPageNo = 0;
                getAddressData();
            }
        });
        refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                if (mAdapter.getCount() < mTotalCount) {
                    mPageNo++;
                    getAddressData();
                } else {
                    Toast.makeText(mContext, "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                    refreshLayout.finishLoadMoreWithNoMoreData();//设置之后，将不会再触发加载事件
                }
            }
        });


        mAdapter.setOnChildClickListener(new ReceiveAddressLvAdapter.OnChildClickListener() {
            @Override
            public void onDeleteClick(final String addressId) {
                ConfirmDialog confirmDialog = new ConfirmDialog(mContext, new ConfirmDialog.OnConfirmEvent() {
                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onConfirm() {
                        deleteAddress(addressId);
                    }
                });
                confirmDialog.show();
                confirmDialog.setContentText("确定要删除该地址吗？");
                confirmDialog.setOkText("确定");
            }

            @Override
            public void onEditClick(ReceiveAddressInfo.Data.ThirdAddressPageBean.DataBean bean) {
                Intent intent = new Intent(mContext, AddAddressActivity.class);
                intent.putExtra("address_id", bean.getId());//resident_receive_address.id 新增时为空或者无此字段
                intent.putExtra("is_default", bean.getIs_default() == 1);//是否默认地址 0.否1.是
                intent.putExtra("contact_name", bean.getContact_name());//联系人
                intent.putExtra("contact_phone", bean.getContact_phone()); //联系电话
                intent.putExtra("province_code", bean.getProvince_code());//t_c_zone.zone_code
                intent.putExtra("province_name", bean.getProvince_name());//省
                intent.putExtra("city_code", bean.getCity_code());//t_c_zone.zone_code
                intent.putExtra("city_name", bean.getCity_name());//市
                intent.putExtra("district_code", bean.getDistrict_code());//t_c_zone.zone_code
                intent.putExtra("district_name", bean.getDistrict_name());//区
                intent.putExtra("detail", bean.getDetail());//详细地址
                intent.putExtra("title_name", "编辑收货地址");
                startActivity(intent);
            }

            @Override
            public void onCheckedChanged(boolean isChecked, ReceiveAddressInfo.Data.ThirdAddressPageBean.DataBean bean) {
                if (isChecked) {
                    bean.setIs_default(1);
                    for (int i = 0; i < thirdAddressList.size(); i++) {
                        String id = thirdAddressList.get(i).getId();
                        String id1 = bean.getId();
                        if (!TextUtils.equals(id, id1)) {
                            thirdAddressList.get(i).setIs_default(0);
                        }
                    }
                } else {
                    bean.setIs_default(0);
                }
                addAddress(bean);
            }

            @Override
            public void onItemClickListener(ReceiveAddressInfo.Data.ThirdAddressPageBean.DataBean bean) {
                if (mCanSelected) {
                    Intent intent = new Intent();
                    intent.putExtra("contact_name", bean.getContact_name());//联系人
                    intent.putExtra("contact_phone", bean.getContact_phone()); //联系电话
                    intent.putExtra("address", bean.getProvince_name() + bean.getCity_name() + bean.getDistrict_name() + bean.getDetail());//详细地址
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    /**
     * 添加或者修改收货地址
     */
    public void addAddress(ReceiveAddressInfo.Data.ThirdAddressPageBean.DataBean bean) {
        mDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("id", bean.getId());
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map.put("is_default", String.valueOf(bean.getIs_default()));//是否默认地址 0.否1.是
        map.put("contact_name", bean.getContact_name());//联系人
        map.put("contact_phone", bean.getContact_phone());//联系电话
        map.put("province_code", bean.getProvince_code());//t_c_zone.zone_code
        map.put("province_name", bean.getProvince_name());//省
        map.put("city_code", bean.getCity_code());//t_c_zone.zone_code
        map.put("city_name", bean.getCity_name());//市
        map.put("district_code", bean.getDistrict_code());//t_c_zone.zone_code
        map.put("district_name", bean.getDistrict_name());//区
        map.put("detail", bean.getDetail());//详细地址
        map.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(map);
        OkGo.<String>post(Constant.SAVE_ADDRESS)
                .params("params", params)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mContext == null && isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "onSuccess: " + response.body());
                        LoadProgressDialog.closeDialog(mDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            mAdapter.notifyDataSetChanged();
                        } else {
                            showToast(errorMsg);
                        }
                    }
                });

    }

    /**
     * 删除地址
     */
    private void deleteAddress(String addressId) {
        mDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("address_id", addressId);
        map.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(map);
        OkGo.<String>post(Constant.DELETE_ADDRESS)
                .params("params", params)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "onSuccess ：" + response.body());
                        LoadProgressDialog.closeDialog(mDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            refresh_layout.autoRefresh();
                        } else {
                            showToast(errorMsg);
                        }
                    }
                });
    }

    @OnClick({R.id.back_iv, R.id.add_address_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.add_address_tv:
                startActivity(new Intent(mContext, AddAddressActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        int position = event.getPos();
        if (position == 1000) {
            refresh_layout.autoRefresh();
        }
    }

    /**
     * 获取地址数据
     */
    private void getAddressData() {

        Map<String, String> map = new HashMap<>();
        map.put("page_no", String.valueOf(mPageNo));
        map.put("page_size", "20");
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map.put("address_type", String.valueOf(mAddressType));//0.仅自营 1.自营+三方 2.仅三方
        map.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(map);
        OkGo.<String>post(Constant.MY_RECEIVE_ADDRESS)
                .params("params", params)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);

                        refresh_layout.finishRefresh(true);
                        refresh_layout.finishLoadMore(true);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "地址数据-onSuccess ：" + response.body());
                        LoadProgressDialog.closeDialog(mDialog);
                        refresh_layout.finishRefresh(true);
                        refresh_layout.finishLoadMore(true);

                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            Type type = new TypeToken<ReceiveAddressInfo>() {
                            }.getType();
                            ReceiveAddressInfo addressInfo = new Gson().fromJson(response.body(), type);
                            //自营地址
                            setSelfAddress(addressInfo);
                            //第三方地址
                            setThirdAddress(addressInfo);
                        } else {
                            showToast(errorMsg);
                        }
                    }
                });
    }

    /**
     * 设置第三方地址
     *
     * @param addressInfo
     */
    private void setThirdAddress(ReceiveAddressInfo addressInfo) {
        ReceiveAddressInfo.Data.ThirdAddressPageBean third_address_page = addressInfo.getData().getThird_address_page();
        if (third_address_page != null) {
            List<ReceiveAddressInfo.Data.ThirdAddressPageBean.DataBean> third_address = third_address_page.getData();
            mTotalCount = third_address_page.getTotalCount();
            if (mPageNo == 0) {
                thirdAddressList.clear();
            }
            thirdAddressList.addAll(third_address);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 设置自营地址
     *
     * @param addressInfo
     */
    private void setSelfAddress(ReceiveAddressInfo addressInfo) {
        final ReceiveAddressInfo.Data.SelfAddressBean self_address = addressInfo.getData().getSelf_address();
        if (self_address != null) {
            contact_name_tv.setText(self_address.getName());
            contact_phone_tv.setText(self_address.getMobile_no());
            address_tv.setText(self_address.getAddress());
            is_default_cb.setChecked(true);
            is_default_cb.setClickable(false);
            //TODO
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCanSelected) {
                        Intent intent = new Intent();
                        intent.putExtra("contact_name", self_address.getName());//联系人
                        intent.putExtra("contact_phone", self_address.getMobile_no()); //联系电话
                        intent.putExtra("address", self_address.getAddress());//详细地址
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LoadProgressDialog.closeDialog(mDialog);
        EventBus.getDefault().unregister(this);
    }
}
