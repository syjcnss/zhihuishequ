package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.tools.DateUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.ProductDetailInfo;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.RefreshConstant;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.ConfirmDialog;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.scwang.smartrefresh.layout.util.DesignUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.addapp.pickers.common.LineConfig;
import cn.addapp.pickers.picker.DatePicker;
import cn.addapp.pickers.picker.DateTimePicker;

public class ProductDetailActivity extends BaseActivity {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.back_iv)
    ImageView back_iv;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.picture_iv)
    ImageView picture_iv;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.price_tv)
    TextView price_tv;
    @BindView(R.id.service_time_tv)
    TextView service_time_tv;

    @BindView(R.id.reduce_btn)
    Button reduce_btn;
    @BindView(R.id.number_et)
    EditText number_et;
    @BindView(R.id.add_btn)
    Button add_btn;
    @BindView(R.id.select_num_ll)
    LinearLayout select_num_ll;
    @BindView(R.id.select_time_ll)
    LinearLayout select_time_ll;
    @BindView(R.id.describe_wv)
    WebView describe_wv;

    @BindView(R.id.total_price_tv)
    TextView total_price_tv;
    @BindView(R.id.confirm_tv)
    TextView confirm_tv;


    private int scroll_y;
    private int mNum = 1;
    private String mPictureUrl;
    private String mName;
    private double mPrice;
    private String mDescription;
    private String mServiceTime;//上门时间
    private String mProductId;//商品id
    private String mContactName;//联系人
    private String mContactPhone;//联系电话
    private String mReceiveAddress;//地址收货地址
    private String mModuleType;//板块分类(1：维修服务  2:特供商品)
    private Dialog loadingDialog;
    private String mServerTime;//服务器时间
    private String mServiceTimeStart;//上门开始时间
    private String mServiceTimeEnd;//上门结束时间
    private String mServiceAddHours;//缓冲时间
    private String[] startTimeStr;
    private String[] endTimeStr;
    private DateTimePicker picker;


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_product_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        loadingDialog = LoadProgressDialog.createLoadingDialog(mContext);
        scroll_y = AppUtil.dip2px(mContext, 320);
        WebSettings settings = describe_wv.getSettings();
        settings.setDisplayZoomControls(false); //隐藏webview缩放按钮
        settings.setJavaScriptEnabled(true);//支持js
        describe_wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 得到 URL 可以传给应用中的某个 WebView 页面加载显示
                return true;
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        loadingDialog.show();
        mModuleType = getIntent().getStringExtra("module_type");
        mPictureUrl = getIntent().getStringExtra("picture");
        mName = getIntent().getStringExtra("name");
        mPrice = getIntent().getDoubleExtra("price", 0.0);
        mDescription = getIntent().getStringExtra("description");
        mProductId = getIntent().getStringExtra("product_id");
        Glide.with(mContext).load(mPictureUrl).apply(new RequestOptions().placeholder(R.drawable.image_error).error(R.drawable.image_error)).into(picture_iv);
        name_tv.setText(mName);
        price_tv.setText(String.valueOf(mPrice));
        if (!TextUtils.isEmpty(mDescription)) {
            describe_wv.loadDataWithBaseURL(null, mDescription, "text/html", "UTF-8", null);
        }
        total_price_tv.setText(String.valueOf(mNum * mPrice));
        if (TextUtils.equals(mModuleType, "1")) {//社区服务
            select_time_ll.setVisibility(View.VISIBLE);
            add_btn.setEnabled(false);
            reduce_btn.setEnabled(false);
            number_et.setFocusable(false);
        } else {//特殊商品
            select_time_ll.setVisibility(View.GONE);
            add_btn.setEnabled(true);
            reduce_btn.setEnabled(true);
            number_et.setFocusable(true);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * 1、防止用户停止在此页面时间过长，获取的服务器时间过时
         * 2、防止用户在下一页（订单详情页）选择地址时更改默认地址后返回此页面，从服务器获取的默认地址没有及时更新
         * 3、所以在这里请求数据，获取（或者刷新）服务器时间和默认地址
         */
        requestData();
    }

    /**
     * 获取order_service_time 和 order_address 数据
     */
    private void requestData() {
        Map<String, String> map = new HashMap<>();
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map.put("product_id", mProductId);
        map.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(map);
        OkGo.<String>post(Constant.GENERATE_ORDER)
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
                        Logger.i(TAG, "生成订单详情-onSuccess: " + response.body());
                        LoadProgressDialog.closeDialog(loadingDialog);
                        String errorMsg = getErrorMsg(response.body());
                        String errorCode = getErrorCode(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            Type type = new TypeToken<ProductDetailInfo>() {
                            }.getType();
                            ProductDetailInfo info = new Gson().fromJson(response.body(), type);
                            getOrderServiceTime(info);
                            getOrderAddress(info);
                        } else {
                            showToast(errorMsg);
                        }
                    }
                });
    }

    private void getOrderAddress(ProductDetailInfo info) {
        ProductDetailInfo.DataBean.OrderAddressBean order_address = info.getData().getOrder_address();
        if (order_address != null) {
            mReceiveAddress = order_address.getAddress();//地址
            mContactPhone = order_address.getMobile_no();//联系电话
            mContactName = order_address.getName();//联系人
        }
    }

    /**
     * 获取服务时间
     */
    private void getOrderServiceTime(ProductDetailInfo info) {
        ProductDetailInfo.DataBean.OrderServiceTimeBean order_service_time = info.getData().getOrder_service_time();
        if (order_service_time != null) {
            mServerTime = order_service_time.getServer_time();//服务器时间
            mServiceTimeStart = order_service_time.getService_time_start();//上门开始时间
            mServiceTimeEnd = order_service_time.getService_time_end(); //上门结束时间
            mServiceAddHours = order_service_time.getService_add_hours();//缓冲时间
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.i(TAG, "onScrollChange: scrollY:" + scrollY + "\t oldScrollY:" + oldScrollY);
                if (scrollY <= scroll_y) {
                    float alpha = (float) scrollY / scroll_y;
                    title_bar_ll.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                            , ContextCompat.getColor(mContext, R.color.white), alpha));
                    back_iv.setImageResource(R.drawable.back_white);
                    mImmersionBar.statusBarDarkFont(false).init();
                } else {
                    title_bar_ll.setBackgroundColor(ColorUtils.blendARGB(Color.TRANSPARENT
                            , ContextCompat.getColor(mContext, R.color.white), 1));
                    back_iv.setImageResource(R.drawable.back_img);
                    mImmersionBar.statusBarDarkFont(true, 0.2f).init();
                }
            }
        });

        number_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    return;
                }
                if (mNum <= 1) {
                    reduce_btn.setEnabled(false);
                } else {
                    reduce_btn.setEnabled(true);
                }
                if (mNum >= 99) {
                    add_btn.setEnabled(false);
                } else {
                    add_btn.setEnabled(true);
                }
                mNum = Integer.valueOf(s.toString());
                if (mNum < 1) {
                    number_et.setText(String.valueOf(1));
                    return;
                }

                if (mNum > 99) {
                    number_et.setText(String.valueOf(99));
                    return;
                }
                total_price_tv.setText(String.valueOf(mNum * mPrice));
            }
        });
    }

    @OnClick({R.id.back_iv, R.id.reduce_btn, R.id.add_btn, R.id.select_time_ll, R.id.confirm_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.reduce_btn:
                if (mNum > 1) {
                    mNum--;
                    number_et.setText(String.valueOf(mNum));
                }
                break;
            case R.id.add_btn:
                if (mNum < 99) {
                    mNum++;
                    number_et.setText(String.valueOf(mNum));
                }
                break;
            case R.id.select_time_ll:
                ConfirmDialog confirmDialog = new ConfirmDialog(mContext, new ConfirmDialog.OnConfirmEvent() {
                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onConfirm() {
                        getServiceTime();

                    }
                });
                confirmDialog.show();
                confirmDialog.setContentText("1.工作时间为 8:00~18:00，节假日无休；\n2.若预定时间超过当日下班时间，则默认上门为次日早上8:00；");
                confirmDialog.setOkText("确定");
                confirmDialog.setOkTextColor(getResources().getColor(R.color.color_37A2AC));
                confirmDialog.setTitleText("温馨提示");
                confirmDialog.setCancelVisible(View.GONE);
                break;
            case R.id.confirm_tv:
                //如果是特殊商品并且时间为空 return
                if (TextUtils.isEmpty(mServiceTime) && TextUtils.equals(mModuleType, "1")) {
                    showToast("请选择上门时间");
                    return;
                }

                createOrder();
                break;

        }
    }

    /**
     * 获取上门时间
     */
    private void getServiceTime() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(mServerTime));
            int startYear = calendar.get(Calendar.YEAR);
            int startMonth = calendar.get(Calendar.MONTH) + 1;
            int startDay = calendar.get(Calendar.DAY_OF_MONTH);
            startTimeStr = mServiceTimeStart.split(":");
            endTimeStr = mServiceTimeEnd.split(":");
            if (startTimeStr.length < 2 || endTimeStr.length < 2) {
                showToast("返回时间数据格式不正确");
                return;
            }
            picker = new DateTimePicker(this, DatePicker.HOUR_24);
            picker.setTitleText("请选择上门时间");
            picker.setLabel("年", "月", "日", ":", null);
            picker.setDateRangeStart(startYear, startMonth, startDay);
            picker.setDateRangeEnd(startYear + 25, 12, 31);
            picker.setTimeRangeStart(Integer.parseInt(startTimeStr[0]), Integer.parseInt(startTimeStr[1]));
            picker.setTimeRangeEnd(Integer.parseInt(endTimeStr[0]), Integer.parseInt(endTimeStr[1]));
            picker.setCanLoop(false);//不禁用循环
            picker.setCanLinkage(true);//是否联动
            picker.setWeightEnable(true);//开启权重（横向平分）
            picker.setWheelModeEnable(true);
            picker.setSelectedTextColor(getResources().getColor(R.color.black));
            LineConfig config = new LineConfig();
            config.setColor(getResources().getColor(R.color.list_line));//线颜色
            config.setAlpha(120);//线透明度
            config.setVisible(true);//线不显示 默认显示
            picker.setLineConfig(config);
            picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                private String pick_time = "";
                private SimpleDateFormat df2;
                private SimpleDateFormat df3;

                @Override
                public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                    pick_time = year + "-" + month + "-" + day + " " + hour + ":" + minute;
                    String start_time = year + "-" + month + "-" + day + " " + mServiceTimeStart;//上班时间
                    String end_time = year + "-" + month + "-" + day + " " + mServiceTimeEnd;//下班时间

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                    df3 = new SimpleDateFormat("yyyy-MM-dd HH", Locale.getDefault());

                    try {
                        Date dt1 = df.parse(pick_time);
                        Date dt2 = df.parse(mServerTime);
                        //选择的时间
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(df2.parse(pick_time));
                        //下班时间
                        Calendar cal2 = Calendar.getInstance();
                        cal2.setTime(df2.parse(end_time));

                        if (dt1.getTime() > dt2.getTime()) {//选择了今天以后的日期
                            Log.i(TAG, "选择的日期 > 服务器日期");
                            if (cal.getTime().after(cal2.getTime())){//先择的时间 > 下班时间
                                mServiceTime = end_time;
                            }else {//先择的时间 <= 下班时间
                                mServiceTime = pick_time;
                            }
                        } else if (dt1.getTime() < dt2.getTime()) {//选择了今天以前的日期
                            Log.i(TAG, "选择的日期 < 服务器日期");
                            mServiceTime = "";
                            showShortToast("请选择正确的日期");
                        } else {//选择了今天的日期
                            Log.i(TAG, "选择的日期 == 服务器日期");
                            //服务器时间 小时取整
                            Calendar cal1 = Calendar.getInstance();
                            cal1.setTime(df3.parse(mServerTime));
                            cal1.add(Calendar.HOUR_OF_DAY, Integer.parseInt(mServiceAddHours));
                            if (cal1.getTime().after(cal2.getTime())){//服务器时间+3 > 下班时间
                                cal.add(Calendar.DAY_OF_MONTH, 1);//今天+1
                                cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTimeStr[0]));
                                cal.set(Calendar.MINUTE, Integer.parseInt(startTimeStr[1]));
                                mServiceTime = df2.format(cal.getTime());
                            }else {//服务器时间+3 <= 下班时间
                                if (cal.getTime().before(cal1.getTime())) {//选择的时间 < 服务器时间+3
                                    showToast("可预约上门时间为当前时间+3h，请重新选择");
                                    mServiceTime = "";
                                }else {//选择的时间 >= 服务器时间+3
                                    if (cal.getTime().after(cal2.getTime())){//先择的时间 > 下班时间
                                        mServiceTime = end_time;
                                    }else {//先择的时间 <= 下班时间
                                        mServiceTime = pick_time;
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, "onDateTimePicked: " + mServiceTime);
                    service_time_tv.setText(mServiceTime);
                }
            });
            picker.show();
        } catch (ParseException e) {
            e.printStackTrace();

        }


    }

    /**
     * 创建订单
     */
    private void createOrder() {
        loadingDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));//用户id
        map.put("amount", String.valueOf(mNum * mPrice));//订单金额
        map.put("product_price",String.valueOf(mPrice));//单价
        map.put("service_time", mServiceTime);//上门时间
        map.put("product_id", mProductId);//商品id
        map.put("product_name", mName);//商品名称
        map.put("product_num", String.valueOf(mNum));//商品数量
        map.put("contact_name", mContactName);//联系人
        map.put("contact_phone", mContactPhone);//联系电话
        map.put("receive_address", mReceiveAddress);//收货地址
        map.put("remark", "");//备注
        map.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(map);
        OkGo.<String>post(Constant.CREATE_ORDER)
                .params("params", params)
                .execute(new com.lzy.okgo.callback.StringCallback() {
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
                        Logger.i(TAG, "订单编号-onSuccess: " + response.body());
                        LoadProgressDialog.closeDialog(loadingDialog);
                        String errorCode = getErrorCode(response.body());
                        String errorMsg = getErrorMsg(response.body());
                        if (errorCode.equals(Constant.TOKEN_ERROR)) {
                            startLoginActivity();
                        } else if (errorCode.equals(Constant.RESULT_OK)) {
                            try {
                                JSONObject object = new JSONObject(response.body());
                                String order_product_id = object.optString("data");
                                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                                intent.putExtra("mOrderProductId", order_product_id);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        int position = event.getPos();
        if (position == RefreshConstant.PRODUCT_BUY_SUCCESS){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadProgressDialog.closeDialog(loadingDialog);
        EventBus.getDefault().unregister(this);
    }
}
