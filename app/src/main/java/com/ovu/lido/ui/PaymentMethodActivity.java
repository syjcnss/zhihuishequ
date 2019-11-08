package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.CreateOrderInfo;
import com.ovu.lido.bean.GroupBuyOrderBean;
import com.ovu.lido.bean.PayResultBean;
import com.ovu.lido.bean.WechatModel;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.RefreshConstant;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.util.alipay.AliPayTools;
import com.ovu.lido.util.alipay.OnSuccessAndErrorListener;
import com.ovu.lido.util.unionpay.UnionConstant;
import com.ovu.lido.util.wechat.WechatPayTools;
import com.ovu.lido.widgets.CommonAction;
import com.ovu.lido.widgets.LoadProgressDialog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cmbapi.CMBApi;
import cmbapi.CMBApiFactory;
import cmbapi.CMBEventHandler;
import cmbapi.CMBRequest;
import cmbapi.CMBResponse;

/**
 * 支付方式
 */
public class PaymentMethodActivity extends BaseActivity implements View.OnClickListener, CMBEventHandler {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.need_pay_tv)
    TextView need_pay_tv;
    @BindView(R.id.wechat_ll)
    LinearLayout wechat_ll;
    @BindView(R.id.wechat_cb)
    CheckBox wechat_cb;
    @BindView(R.id.alipay_ll)
    LinearLayout alipay_ll;
    @BindView(R.id.alipay_cb)
    CheckBox alipay_cb;
    @BindView(R.id.unionpay_ll)
    LinearLayout unionpay_ll;
    @BindView(R.id.unionpay_cb)
    CheckBox unionpay_cb;
    @BindView(R.id.pay_immediately_btn)
    Button pay_immediately_btn;

    private double mNeedPayAmount;
    private String payType = "WX";
    private String mBusinessType;// 0充值 1缴费 3团购 4充值缴费 10购买社区商品
    private HashMap<String, String> mBusinessData;
    private String order_id;
    private HashMap<String, String> map;
    private String mSubject;
    private int payMode = 0;
    private CMBApi cmbApi;

    private Handler handler = new Handler();
    private Dialog loadingDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl)
                .statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_payment_method;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = LoadProgressDialog.createLoadingDialog(mContext);
        EventBus.getDefault().register(this);
        cmbApi = CMBApiFactory.createCMBAPI(this, UnionConstant.APPID);
        cmbApi.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        cmbApi.handleIntent(intent, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cmbApi.handleIntent(data, this);
    }

    /**
     * 招商银行支付结果回调
     * @param cmbResponse
     */
    @Override
    public void onResp(CMBResponse cmbResponse) {
        //mRespCode 错误码：0处理成功，-1普通错误， -2 结果未知
        //mRespMsg 返回的业务数据
        if (cmbResponse.mRespCode == 0) {
            Logger.i(TAG, "调用成功.str: " + cmbResponse.mRespMsg);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    queryPayResult();
                }
            },500);
        } else {
            Toast.makeText(this, "调用失败", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void initView() {
        super.initView();
        map = (HashMap<String, String>) getIntent().getSerializableExtra("map");
        mBusinessType = getIntent().getStringExtra("business_type");
        mBusinessData = (HashMap<String, String>) getIntent().getSerializableExtra("business_data");
        mNeedPayAmount = getIntent().getDoubleExtra("needPayAmount", 0);
        Log.i(TAG, "mNeedPayAmount: " + mNeedPayAmount + "\t mBusinessType: " + mBusinessType + "\t mBusinessData: " + mBusinessData);
        need_pay_tv.setText("￥" + AppUtil.TwoPoint(mNeedPayAmount));
        wechat_cb.setChecked(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                if (mBusinessType.equals("11")){
                    RefreshEvent.JDPayResult jdPayResult = new RefreshEvent.JDPayResult();
                    jdPayResult.setResultCode(0);
                    EventBus.getDefault().post(jdPayResult);//京东商品支付通知
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.wechat_ll,R.id.alipay_ll, R.id.unionpay_ll,R.id.pay_immediately_btn,R.id.back_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wechat_ll:
                setCheckGroup(true, false, false);
                payType = "WX";
                break;
            case R.id.alipay_ll:
                setCheckGroup(false, true, false);
                payType = "ALIPAY";
                break;
            case R.id.unionpay_ll:
                setCheckGroup(false, false, true);
                payType = "UNIONPAY";
                break;
            case R.id.pay_immediately_btn:
                doSubmit();
//                Intent intent = new Intent(mContext, PayResultActivity.class);
//                intent.putExtra("order_status", 1);
//                intent.putExtra("order_id", "123456");
//                intent.putExtra("business_type",mBusinessType);
//                startActivity(intent);
                break;
            case R.id.back_iv:
                RefreshEvent.JDPayResult jdPayResult = new RefreshEvent.JDPayResult();
                jdPayResult.setResultCode(0);
                EventBus.getDefault().post(jdPayResult);//京东商品支付通知
                finish();
                break;

        }
    }

    private void doSubmit() {
        if (TextUtils.equals("4", mBusinessType)) {
            mSubject = "缴费";
        }else if (TextUtils.equals("10", mBusinessType)) {
            mSubject = "购买社区商品";
        }else if (TextUtils.equals("5",mBusinessType)){
            mSubject = "钱包充值";
        }else if (TextUtils.equals("11",mBusinessType)){
            mSubject = "购买京东商品";
        }
        switch (payType) {
            case "ALIPAY":
                payMode = 1;
                if (mBusinessType.equals("4")) {
                    AppPreference.I().putInt("payMode", payMode);
                    payFree();
                }else if (mBusinessType.equals("10")) {
                    productBuyOrder();
                }else if (mBusinessType.equals("5")) {
                    topUp();
                }else if (mBusinessType.equals("11")) {
                    productBuyOrder();
                }
                break;
            case "WX":
                payMode = 0;
                if (mBusinessType.equals("4")) {
                    AppPreference.I().putInt("payMode", payMode);
                    payFree();
                }else if (mBusinessType.equals("10")) {
                    productBuyOrder();
                }else if (mBusinessType.equals("11")) {
                    productBuyOrder();
                }
                break;
            case "UNIONPAY":
                payMode = 3;
                if (mBusinessType.equals("4")) {
                    AppPreference.I().putInt("payMode", payMode);
                    payFree();
                }else if (mBusinessType.equals("10")) {
                    productBuyOrder();
                }else if (mBusinessType.equals("11")) {
                    productBuyOrder();
                }
                break;
        }


    }

    /**
     * 钱包充值
     */
    private void topUp() {
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> map1 = new HashMap<>();
        HashMap<String, Object> map2 = new HashMap<>();
        map.put("token", AppPreference.I().getString("token", ""));
        map1.put("bussiness_data", map);
        map1.put("amount", mNeedPayAmount + "");
        map1.put("bussiness_type", mBusinessType);
        map1.put("resident_id", AppPreference.I().getString("resident_id", ""));
        String params = ViewHelper.getParamsV2(map1);
        OkGo.<String>post(Constant.PAYMENT_CREATE_ORDER)
                .params("params", params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "钱包充值订单id-onSuccess: " + response.body());
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else if (errorCode.equals(Constant.RESULT_OK)){
                            Type type = new TypeToken<GroupBuyOrderBean>() {
                            }.getType();
                            GroupBuyOrderBean info = new Gson().fromJson(response.body(), type);
                            order_id = info.getData().getOrder_id();
                            getPayId(order_id);
                        }else {
                            showToast(errorMsg);
                        }

                    }
                });

    }

    /**
     * 购买社区商品获取订单号
     */
    private void productBuyOrder() {
        loadingDialog.show();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        paramMap.put("amount", mNeedPayAmount + "");
        paramMap.put("business_type", mBusinessType);//业务类型 10购买社区商品 11购买京东商品
        paramMap.put("business_data", map);
        paramMap.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParamsV2(paramMap);
        OkGo.<String>post(Constant.CREATE_ORDER_ID)
                .params("params", params)
                .execute(new com.lzy.okgo.callback.StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mContext == null || isFinishing()){
                            return;
                        }
                        LoadProgressDialog.closeDialog(loadingDialog);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "订单编号-onSuccess: " + response.body());
                        LoadProgressDialog.closeDialog(loadingDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            try {
                                JSONObject object = new JSONObject(response.body());
                                order_id = object.optJSONObject("data").optString("order_id");
                                getPayIdV2(order_id);
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
     * 缴费获取订单号
     */
    private void payFree() {
        loadingDialog.show();
        HashMap<String, Object> param = new HashMap<>();
        param.put("resident_id", AppPreference.I().getString("resident_id", ""));
        param.put("amount", ViewHelper.getDisplayPrice(mNeedPayAmount));
        param.put("bussiness_type", mBusinessType);
        param.put("bussiness_data", mBusinessData);
        String params = ViewHelper.getParamsV2(param);
        OkGo.<String>post(Constant.GET_ORDER_ID_URL)
                .params("params", params)
                .execute(new com.lzy.okgo.callback.StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else if (errorCode.equals(Constant.RESULT_OK)){
                            Type type = new TypeToken<CreateOrderInfo>() {
                            }.getType();
                            CreateOrderInfo info = new Gson().fromJson(response.body(),type);
                            order_id = info.getData().getOrder_id();
                            getPayId(order_id);
                        }else {
                            showToast(errorMsg);
                        }
                    }
                });
    }

    /**
     * 获取 缴费 支付id
     * @param order_id
     */
    private void getPayId(String order_id) {
        loadingDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("token", AppPreference.I().getString("token", ""));
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map.put("order_id", order_id);
        map.put("pay_type",String.valueOf(payMode));//支付方式
        String params = ViewHelper.getParams(map);
        OkGo.<String>post(HttpAddress.GET_PAY_ID)
                .params("params", params)
                .execute(new com.lzy.okgo.callback.StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mContext == null || isFinishing()){
                            return;
                        }
                        LoadProgressDialog.closeDialog(loadingDialog);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "缴费支付id - onSuccess: ");
                        LoadProgressDialog.closeDialog(loadingDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)){
                            startLoginActivity();
                        }else if (errorCode.equals(Constant.RESULT_OK)){
                            try {
                                JSONObject jsonObject = new JSONObject(response.body());

                                JSONObject data = jsonObject.optJSONObject("data");
                                String payId = data.optString("pay_id");
                                if (TextUtils.isEmpty(payId)) {
                                    return;
                                }
                                switch (payType) {
                                    case "WX":
                                        wechatPay(mSubject, payId, mNeedPayAmount);
                                        break;
                                    case "ALIPAY":
                                        alipay(payId, mSubject, mNeedPayAmount + "");
                                        break;
                                    case "UNIONPAY":
                                        unionpay(data.optString("jsonRequestData"));
                                    default:
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else {
                            showToast(errorMsg);
                        }
                    }
                });


    }

    /**
     * 获取 社区商品 支付id
     *
     * @param order_id
     */
    private void getPayIdV2(String order_id) {
        loadingDialog.show();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        paramMap.put("order_id", order_id);
        paramMap.put("pay_type", String.valueOf(payMode));//支付方式
        paramMap.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(paramMap);
        OkGo.<String>post(Constant.GET_PAY_ID)
                .params("params", params)
                .execute(new com.lzy.okgo.callback.StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mContext == null || isFinishing()){
                            return;
                        }
                        LoadProgressDialog.closeDialog(loadingDialog);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.i(TAG, "支付ID-onSuccess: " + response.body());
                        LoadProgressDialog.closeDialog(loadingDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body());
                                JSONObject data = jsonObject.optJSONObject("data");
                                String payId = data.optString("pay_id");
                                if (TextUtils.isEmpty(payId)) {
                                    return;
                                }
                                switch (payType) {
                                    case "WX":
                                        wechatPay(mSubject, payId, mNeedPayAmount);
                                        break;
                                    case "ALIPAY":
                                        alipay(payId, mSubject, mNeedPayAmount + "");
                                        break;
                                    case "UNIONPAY":
                                        unionpay(data.optString("jsonRequestData"));
                                        break;
                                    default:
                                        break;
                                }

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
     * 查询订单状态（支付结果）
     */
    private void queryPayResult() {
        loadingDialog.show();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("token", AppPreference.I().getString("token", ""));
        paramMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        paramMap.put("order_id", order_id);
        String params = ViewHelper.getParams(paramMap);
        OkGo.<String>post(Constant.QUERY_PAY_RESULT)
                .params("params", params)
                .execute(new com.lzy.okgo.callback.StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if (mContext == null || isFinishing()){
                            return;
                        }
                        LoadProgressDialog.closeDialog(loadingDialog);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.i(TAG, "订单状态-onSuccess: " + response.body());
                        LoadProgressDialog.closeDialog(loadingDialog);

                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            //发送通知
                            RefreshEvent.JDPayResult jdPayResult = new RefreshEvent.JDPayResult();
                            jdPayResult.setResultCode(0);
                            EventBus.getDefault().post(jdPayResult);//京东商品支付通知

                            //跳转到支付结果页面
                            Type type = new TypeToken<PayResultBean>() {
                            }.getType();
                            PayResultBean info = new Gson().fromJson(response.body(), type);
                            int order_status = info.getData().getOrder_status();//订单状态  1：支付成功  0：支付失败
                            String order_code = info.getData().getOrder_code();//订单号
                            Intent intent = new Intent(mContext, PayResultActivity.class);
                            intent.putExtra("order_status", order_status);
                            intent.putExtra("order_id", order_code);
                            intent.putExtra("business_type",mBusinessType);
                            if (TextUtils.equals(mBusinessType,"10")) {
                                intent.putExtra("order_product_id", map.get("order_product_id"));
                            }
                            startActivity(intent);


                        }else {
                            showToast(errorMsg);
                        }
                    }
                });
    }


    private void unionpay(String jsonRequestData) {
        Logger.i(TAG, "是否已经安装招行app: " + cmbApi.isCMBAppInstalled());
        CMBRequest request = getCMBRequest(jsonRequestData);
        //mCMBJumpUrl需要以http://或者https://开头，参数错误会抛异常:IllegalArgumentException
        try {
            cmbApi.sendReq(request);
        } catch (IllegalArgumentException e) {
            showShortToast(e.toString());
        }
    }

    private CMBRequest getCMBRequest(String jsonRequestData) {
        CMBRequest request = new CMBRequest();
        //业务功能类型，SDK透传 (默认上送pay)
        request.mMethod = "pay";
        // h5Url与CMBJumpUrl均需要赋值，app没有安装时在商户APP打开H5页面
        request.mH5Url = UnionConstant.H5Url;
        //h5Url与CMBJumpUrl均需要赋值，app已经安装时要跳转到的招商银行APP具体功能的url
        request.mCMBJumpUrl = UnionConstant.CMBJumpUrl;
        //支付、协议、领券等业务功能等请求参数，具体内容由业务功能给出具体内容，SDK透传，会将该字段信息Post给对应功能页面
        request.mRequestData = "jsonRequestData=" + jsonRequestData;
        return request;
    }

    private void wechatPay(String name, String out_trade_no, double amount) {
        WechatPayTools.wechatPayUnifyOrder(mContext,
                new WechatModel(out_trade_no,
                        (int) (Double.parseDouble(ViewHelper.getDisplayPrice(amount)) * 100) + "",
                        name,
                        name),
                new OnSuccessAndErrorListener() {
                    @Override
                    public void onSuccess(String s) {
                        Log.i(TAG, "onSuccess: " + s);
                    }

                    @Override
                    public void onError(String s) {
                        Log.i(TAG, "onError: " + s);
                    }
                });

    }

    /**
     * 支付宝支付
     */
    private void alipay(String out_trade_no, String subject, String total_amount) {
        AliPayTools.aliPay(this, out_trade_no, subject, subject, total_amount,
                new OnSuccessAndErrorListener() {//支付宝支付结果回调
                    @Override
                    public void onSuccess(String s) {
                        Log.i(TAG, "onSuccess: " + s);
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                queryPayResult();
                            }
                        },500);
                    }

                    @Override
                    public void onError(String s) {
                        Log.i(TAG, "onError: " + s);
                    }
                });

    }

    private void setCheckGroup(Boolean isWechat, Boolean isAlipay, Boolean isUnionpay) {
        wechat_cb.setChecked(isWechat);
        alipay_cb.setChecked(isAlipay);
        unionpay_cb.setChecked(isUnionpay);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        int position = event.getPos();
        if (position == RefreshConstant.PRODUCT_BUY_SUCCESS) {
            finish();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent.JDPayResult jdPayResult) {
        int resultCode = jdPayResult.getResultCode();//0、未执行支付，直接返回    1、支付完成返回  2、
        switch (resultCode) {
            case 1:
            case 2:
                finish();
                break;
        }


    }

    /**
     * 微信支付结果回调
     * @param result
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent.WXPayResult result) {
        int code = result.getErrorCode();
        if (code == 0) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    queryPayResult();
                }
            },500);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
