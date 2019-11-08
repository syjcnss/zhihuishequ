package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.OrderDetailInfo;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.RefreshConstant;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.ConfirmDialog;
import com.ovu.lido.widgets.LoadProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.back_iv)
    ImageView back_iv;

    @BindView(R.id.address_rl)
    RelativeLayout address_rl;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.phone_tv)
    TextView phone_tv;
    @BindView(R.id.address_tv)
    TextView address_tv;
    @BindView(R.id.right_icon)
    ImageView right_icon;

    @BindView(R.id.thumbnail_iv)
    ImageView thumbnail_iv;
    @BindView(R.id.product_name_tv)
    TextView product_name_tv;
    @BindView(R.id.label_tv)
    TextView label_tv;
    @BindView(R.id.order_no_tv)
    TextView order_no_tv;
    @BindView(R.id.product_price_tv)
    TextView product_price_tv;
    @BindView(R.id.product_num_tv)
    TextView product_num_tv;
    @BindView(R.id.the_time_tv)
    TextView the_time_tv;
    @BindView(R.id.service_time_tv)
    TextView service_time_tv;
    @BindView(R.id.the_pay_time)
    TextView the_pay_time;
    @BindView(R.id.pay_time_tv)
    TextView pay_time_tv;
    @BindView(R.id.remark_et)
    EditText remark_et;

    @BindView(R.id.amount_tv)
    TextView amount_tv;
    @BindView(R.id.to_pay_tv)
    TextView to_pay_tv;
    @BindView(R.id.status_tv)
    TextView status_tv;


    private String mOrderProductId;//订单id
    private Dialog loadingDialog;
    private int mStatus = -1;//0:待付款 1:待收货 2:已完成 3:已失效
    private int mAddressType;//0.自营 1.第三方
    private double mAmount;//订单金额
    private String mServiceTime;//上门时间
    private String mProductId;//商品id
    private String mProductName;//商品名称
    private String mProductNum;//商品数量
    private String mContactName;//联系人
    private String mContactPhone;//联系电话
    private String mReceiveAddress;//收货地址
    private String mRemark; //备注
    private String mOrderNo;//订单号
    private String mPayTime;//支付时间

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        loadingDialog = LoadProgressDialog.createLoadingDialog(mContext);
        address_rl.requestFocus();
    }

    @Override
    protected void loadData() {
        super.loadData();
        mOrderProductId = getIntent().getStringExtra("mOrderProductId");

        requestData();
    }

    private void requestData() {
        loadingDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("order_product_id", mOrderProductId);
        map.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(map);
        OkGo.<String>post(Constant.GET_ORDER_LIST_DETAIL)
                .params("params", params)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(loadingDialog);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        LoadProgressDialog.closeDialog(loadingDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            Type type = new TypeToken<OrderDetailInfo>() {
                            }.getType();
                            OrderDetailInfo detailInfo = new Gson().fromJson(response.body(), type);
                            refreshLayout(detailInfo);

                        } else {
                            showToast(errorMsg);
                        }
                    }
                });
    }

    @Override
    protected void setListener() {
        super.setListener();
        remark_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mRemark = s.toString();
            }
        });
    }

    /**
     * 刷新视图
     *
     * @param detailInfo
     */
    private void refreshLayout(OrderDetailInfo detailInfo) {
        OrderDetailInfo.DataBean data = detailInfo.getData();
        if (data == null) return;
        mOrderNo = data.getOrder_no();
        mProductId = data.getProduct_id();
        mServiceTime = data.getService_time();
        mPayTime = data.getPay_time();
        mProductName = data.getProduct_name();
        mProductNum = String.valueOf(data.getProduct_num());
        mContactName = data.getContact_name();
        mContactPhone = data.getContact_phone();
        mReceiveAddress = data.getReceive_address();
        mAmount = data.getAmount();
        mRemark = data.getRemark();

        name_tv.setText(mContactName);
        phone_tv.setText(mContactPhone);
        address_tv.setText(mReceiveAddress);
        Glide.with(mContext).load(data.getThumbnail()).apply(new RequestOptions().placeholder(R.drawable.image_error).error(R.drawable.image_error)).into(thumbnail_iv);
        product_name_tv.setText(mProductName);

        order_no_tv.setText(data.getOrder_no());
        product_price_tv.setText(new StringBuffer().append(ViewHelper.getDisplayPrice(data.getProduct_price())).append("元"));
        product_num_tv.setText(mProductNum);
        the_time_tv.setVisibility(TextUtils.isEmpty(mServiceTime) ? View.GONE : View.VISIBLE);
        service_time_tv.setText(mServiceTime);
        the_pay_time.setVisibility(TextUtils.isEmpty(mPayTime) ? View.GONE : View.VISIBLE);
        pay_time_tv.setText(mPayTime);
        remark_et.setText(TextUtils.isEmpty(mRemark) ? "" : mRemark);
        amount_tv.setText(ViewHelper.getDisplayPrice(mAmount));
        mStatus = data.getStatus();//0:待付款 1:待收货 2:已完成 3:已失效
        String statusStr = "";
        switch (mStatus) {
            case 0:
                statusStr = "去付款";
                status_tv.setVisibility(View.GONE);
                to_pay_tv.setVisibility(View.VISIBLE);
                remark_et.setFocusable(true);
                break;
            case 1:
                statusStr = "待收货";
                status_tv.setVisibility(View.GONE);
                to_pay_tv.setVisibility(View.VISIBLE);
                remark_et.setFocusable(false);
                break;
            case 2:
                statusStr = "订单已完成";
                status_tv.setVisibility(View.VISIBLE);
                to_pay_tv.setVisibility(View.GONE);
                remark_et.setFocusable(false);
                break;
            case 3:
                statusStr = "订单已失效";
                status_tv.setVisibility(View.VISIBLE);
                to_pay_tv.setVisibility(View.GONE);
                remark_et.setFocusable(false);
                break;
        }
        to_pay_tv.setText(statusStr);
        status_tv.setText(statusStr);

        mAddressType = data.getOperator_type();
        if (mAddressType == 0) {//自营商品 禁止选择地址
            right_icon.setVisibility(View.GONE);
            label_tv.setText("自营");
        } else {
            right_icon.setVisibility(View.VISIBLE);
            label_tv.setText("第三方");
        }
    }

    @OnClick({R.id.back_iv, R.id.to_pay_tv, R.id.address_rl})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.to_pay_tv:

                if (mStatus == 0) {//去付款
//                    if (mAmount > 0) {
                        createOrder();
//                    } else {
//                        freePayment();
//                    }
                } else if (mStatus == 1) {//确认收货
                    ConfirmDialog confirmDialog = new ConfirmDialog(mContext, new ConfirmDialog.OnConfirmEvent() {
                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onConfirm() {
                            confirmReceive();
                        }
                    });
                    confirmDialog.show();
                    confirmDialog.setCancelable(false);
                    confirmDialog.setCanceledOnTouchOutside(false);
                    confirmDialog.setTitleText("确认收货");
                    confirmDialog.setContentText("是否确认收货完成");
                    confirmDialog.setOkText("确认");

                }
                break;
            case R.id.address_rl://地址选择
                if (mStatus == 0) {//待付款
                    if (mAddressType == 1) {//第三方 的可以选择 自营和第三方地址
                        intent.setClass(mContext, ReceiveAddressActivity.class);
                        intent.putExtra("mAddressType", mAddressType);
                        intent.putExtra("canSelected", true);
                        startActivityForResult(intent, 100);
                    }
                }
                break;
        }
    }

    /**
     * 确认收货
     */
    private void confirmReceive() {
        loadingDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("order_product_id", mOrderProductId);
        map.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(map);
        OkGo.<String>post(Constant.CONFIRM_RECEIVE)
                .params("params", params)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(loadingDialog);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "确认收货-onSuccess: ");
                        LoadProgressDialog.closeDialog(loadingDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            requestData();
                        } else {
                            showToast(errorMsg);
                        }
                    }
                });
    }

    /**
     * 创建订单
     */
    private void createOrder() {
        loadingDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));//用户id
        map.put("amount", String.valueOf(mAmount));//订单金额
        map.put("service_time", mServiceTime);//上门时间
        map.put("product_id", mProductId);//商品id
        map.put("id", mOrderProductId);//订单id
        map.put("product_name", mProductName);//商品名称
        map.put("product_num", mProductNum); //商品数量
        map.put("contact_name", mContactName);//联系人
        map.put("contact_phone", mContactPhone);//联系电话
        map.put("receive_address", mReceiveAddress);//收货地址
        map.put("remark", mRemark);//备注
        map.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(map);
        OkGo.<String>post(Constant.CREATE_ORDER)
                .params("params", params)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mContext == null || isFinishing()) {
                            return;
                        }
                        LoadProgressDialog.closeDialog(loadingDialog);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "创建订单-onSuccess: " + response.body());
                        LoadProgressDialog.closeDialog(loadingDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            try {
                                JSONObject object = new JSONObject(response.body());
                                String order_product_id = object.optString("data");//订单id

                                Map<String, Object> map = new HashMap<>();
                                map.put("order_product_id", order_product_id);

                                Intent intent = new Intent(mContext, PaymentMethodActivity.class);
                                intent.putExtra("needPayAmount", mAmount);
                                intent.putExtra("business_type", "10");//业务类型 10购买社区商品
                                intent.putExtra("map", (Serializable) map);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            showToast(errorMsg);
                        }
                    }
                });
    }

    /**
     * 免费商品支付
     */
    private void freePayment() {
        loadingDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("order_product_id", mOrderProductId);
        map.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(map);
        OkGo.<String>post(Constant.FREE_PAYMENT)
                .params("params", params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "onSuccess: " + response.body());
                        LoadProgressDialog.closeDialog(loadingDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            Intent intent = new Intent(mContext, PayResultActivity.class);
                            intent.putExtra("order_status", 1);
                            intent.putExtra("order_id", mOrderNo);
                            intent.putExtra("business_type", "10");
                            intent.putExtra("order_product_id", mOrderProductId);
                            startActivity(intent);
                        } else {
                            showToast(errorMsg);
                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                mContactName = data.getStringExtra("contact_name");
                mContactPhone = data.getStringExtra("contact_phone");
                mReceiveAddress = data.getStringExtra("address");
                name_tv.setText(mContactName);
                phone_tv.setText(mContactPhone);
                address_tv.setText(mReceiveAddress);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        int position = event.getPos();
        if (position == RefreshConstant.PRODUCT_BUY_SUCCESS) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
