package com.ovu.lido.ui;

import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.AddressBean;
import com.ovu.lido.bean.BalanceBean;
import com.ovu.lido.bean.CommonBean;
import com.ovu.lido.bean.DefaultAddressBean;
import com.ovu.lido.bean.GroupByBean;
import com.ovu.lido.bean.GroupDetailBean;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.RefreshConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 订单详情
 */
public class OrderDetailedActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.reduce)
    ImageView mReduce;
    @BindView(R.id.iv_img)
    ImageView iv_img;
    @BindView(R.id.tv_receiver)
    TextView receiver;
    @BindView(R.id.tv_address)
    TextView address;
    @BindView(R.id.tv_phone)
    TextView phone;
    @BindView(R.id.name)//团购名称
            TextView name;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_price)
    TextView price;
    @BindView(R.id.tv_count)
    TextView tv_count;
    @BindView(R.id.tv_account_money)
    TextView account;
    @BindView(R.id.tv_use_money)
    EditText et_money;
    @BindView(R.id.tv_total)
    TextView total_price;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.tv_set_address)
    TextView set_address;
    @BindView(R.id.et_count)
    EditText mEt_count;
    private int count = 1;
    private static final int MAX_COUNT = 999;
    private static final int CHANGE_ADDRESS_RECODE = 66;
    private GroupDetailBean mDataBean;
    private GroupByBean.DataBean mBean;
    private String addressId = "-1";
    private double group_price = 0.0;
    private double remaimMoney = 0.0;
    private Handler mHandler = new Handler();
    Runnable mRunnable;
    private double payMoney = 0.0f;
    private int g_p_id;
    private int commodity_id;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_order_detailed;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mDataBean = (GroupDetailBean) intent.getSerializableExtra("productBean");
        mBean = (GroupByBean.DataBean) intent.getSerializableExtra("product");
        setData();
    }

    @Override
    protected void loadData() {
        super.loadData();
        getAddress();
    }

    private void setData() {
        if (mDataBean != null) {
            name.setText(mDataBean.getData().getG_p_name());
            tv_name.setText(mDataBean.getData().getCommodity_name());
            RequestOptions requestOptions = new RequestOptions()
                    .error(R.drawable.activity);
            Glide.with(mContext)
                    .load(mDataBean.getData().getCommodity_img())
                    .apply(requestOptions)
                    .into(iv_img);
            price.setText("¥" + AppUtil.TwoPoint(mDataBean.getData().getG_p_price()));
            group_price = mDataBean.getData().getG_p_price();
            total_price.setText("¥" + AppUtil.TwoPoint(mDataBean.getData().getG_p_price()));
            payMoney = mDataBean.getData().getG_p_price();
            g_p_id = mDataBean.getData().getId();
            commodity_id = mDataBean.getData().getCommodity_id();
        }
        if (mBean != null) {
            name.setText(mBean.getG_p_name());
            tv_name.setText(mBean.getCommodity_name());
            RequestOptions requestOptions = new RequestOptions()
                    .error(R.drawable.activity);
            Glide.with(mContext)
                    .load(mBean.getCommodity_img())
                    .apply(requestOptions)
                    .into(iv_img);
            price.setText("¥" + AppUtil.TwoPoint(mBean.getG_p_price()));
            group_price = mBean.getG_p_price();
            total_price.setText("¥" + AppUtil.TwoPoint(mBean.getG_p_price()));
            payMoney = mBean.getG_p_price();
            g_p_id = mBean.getId();
            commodity_id = mBean.getCommodity_id();
        }
    }


    /**
     * 获取默认收货地址
     */
    private void getAddress() {
        OkHttpUtils.post()
                .url(HttpAddress.QUERY_DEFAULT_ADDRESS)
                .addParams("token", AppPreference.I().getString("token", ""))
                .addParams("resident_id", AppPreference.I().getString("resident_id", ""))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(mContext);
                            return;
                        }
                        DefaultAddressBean bean = GsonUtil.GsonToBean(response, DefaultAddressBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            receiver.setText("收货人:" + bean.getShipper());
                            phone.setText(bean.getMobile_no());
                            address.setText("收货地址:" + bean.getAddress_detail());

                            receiver.setVisibility(View.VISIBLE);
                            phone.setVisibility(View.VISIBLE);
                            address.setVisibility(View.VISIBLE);
                            set_address.setVisibility(View.GONE);
                            addressId = bean.getAddress_id();

                        } else if (bean.getErrorCode().equals("0064")) {
                            receiver.setVisibility(View.GONE);
                            phone.setVisibility(View.GONE);
                            address.setVisibility(View.GONE);
                            set_address.setVisibility(View.VISIBLE);

                        } else {
                            showToast("获取信息失败");
                        }
                    }
                });
    }


    /**
     * 获取钱包余额
     */
    private void getBalance() {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        map.put("token", AppPreference.I().getString("token", ""));
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map1.put("data", map);
        OkHttpUtils.post()
                .url(HttpAddress.GET_BALANCE)
                .addParams("params", GsonUtil.ToGson(map1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(mContext);
                            return;
                        }
                        BalanceBean bean = GsonUtil.GsonToBean(response, BalanceBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            remaimMoney = bean.getData().getBalance();
                            account.setText(AppUtil.TwoPoint(bean.getData().getBalance()));
                        }
                    }
                });

    }


    @Override
    protected void setListener() {
        iv_back.setOnClickListener(this);
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.reduce).setOnClickListener(this);
        findViewById(R.id.rl_address).setOnClickListener(this);
        findViewById(R.id.tv_pay).setOnClickListener(this);

        mEt_count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(mEt_count.getText().toString()) || "0".equals(mEt_count.getText().toString()))
                    mEt_count.setText(1 + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                int posDot = temp.indexOf(".");
                if (posDot > 0) {
                    if (temp.length() - posDot - 1 > 2) {
                        s.delete(posDot + 3, posDot + 4);
                    }
                }

                if (s.length() > 0 && Double.parseDouble(et_money.getText().toString()) > remaimMoney) {
                    if (mRunnable == null) {
                        mRunnable = new Runnable() {
                            @Override
                            public void run() {

                                if ((group_price * count - remaimMoney) < 0) {
                                    et_money.setText(AppUtil.TwoPoint(group_price * count));
                                    payMoney = 0.0;
                                    total_price.setText("¥0.00");
                                } else {
                                    et_money.setText(AppUtil.TwoPoint(remaimMoney));
                                    payMoney = group_price * count - remaimMoney;
                                    total_price.setText("¥" + AppUtil.TwoPoint((group_price * count - remaimMoney)));
                                }
                            }
                        };
                    }
                    mHandler.postDelayed(mRunnable, 500);
                } else if (s.length() > 0 && Double.parseDouble(et_money.getText().toString()) >= 0 && Double.parseDouble(et_money.getText().toString()) <= remaimMoney) {
                    if (((group_price * count) - Double.parseDouble(et_money.getText().toString())) < 0) {
                        et_money.setText(AppUtil.TwoPoint(group_price * count));
                        total_price.setText("¥0.00");
                        payMoney = 0.0;
                    } else {
                        payMoney = ((group_price * count) - Double.parseDouble(et_money.getText().toString()));
                        total_price.setText("¥" + AppUtil.TwoPoint(((group_price * count) - Double.parseDouble(et_money.getText().toString()))));
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.add:
                addCount();
                break;
            case R.id.reduce:
                reduceCount();
                break;
            case R.id.rl_address:
                Intent intent = new Intent(this, MyAddressActivity.class);
                intent.putExtra("addressId", addressId);
                startActivityForResult(intent, CHANGE_ADDRESS_RECODE);
                break;
            case R.id.tv_pay:
                if (payMoney == 0) {
                    payAllMoney();
                } else {
                    paySomeMoney();
                }
                break;
        }
    }

    /**
     * 部分抵扣或者不抵扣
     */
    private void paySomeMoney() {
        if (addressId.equals("-1")) {
            showToast("请设置收货地址");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("g_p_id", g_p_id + "");
        map.put("commodity_id", commodity_id + "");
        map.put("count", count + "");
        map.put("address_id", addressId);
        map.put("advance_pay_amount", et_money.getText().toString() + "");
        map.put("token", AppPreference.I().getString("token", ""));

        Intent intent = new Intent(this, PaymentMethodActivity.class);
        intent.putExtra("needPayAmount", payMoney);
        intent.putExtra("business_type", "3");
        intent.putExtra("map", (Serializable) map);
        startActivity(intent);

//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("count", buy_count + "");
//        map.put("advance_pay_amount", use_balance + "");
//        map.put("g_p_id", groupBuy.getId());
//        map.put("commodity_id", groupBuy.getCommodity_id());
//        map.put("address_id", address_id);
//        	.add("amount", recharge, true).add("pay_type", payType, true)
//                .add("bussiness_type", bussinessType, true).end(map);
    }

    private void payAllMoney() {
        if (addressId.equals("-1")) {
            showToast("请设置收货地址");
            return;
        }
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map.put("g_p_id", g_p_id + "");
        map.put("commodity_id", commodity_id + "");
        map.put("count", count + "");
        map.put("address_id", addressId);
        map.put("advance_pay_amount", count * group_price + "");
        map.put("token", AppPreference.I().getString("token", ""));
        map.put("resident_id", AppPreference.I().getString("resident_id", ""));
        map1.put("data", map);
        OkHttpUtils.post()
                .url(HttpAddress.GROUP_PURCHASE_ORDER)
                .addParams("params", GsonUtil.ToGson(map1))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Logger.i(TAG, response);
                        if (AppUtil.isToken(response)) {
                            AppUtil.toLogin(mContext);
                            return;
                        }
                        CommonBean bean = GsonUtil.GsonToBean(response, CommonBean.class);
                        if (bean.getErrorCode().equals("0000")) {
                            showToast("付款成功");
                            startActivity(new Intent(OrderDetailedActivity.this, MyOrderActivity.class));
                            EventBus.getDefault().post(new RefreshEvent(RefreshConstant.GROUP_BUY_LIST));
                            ActivityUtils.finishActivity(ProductDetailedActivity.class);
                            finish();
                        } else {
                            showToast(bean.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 增加数量
     */
    private void addCount() {
        if (TextUtils.isEmpty(mEt_count.getText().toString().trim())) {
            return;
        }
        count = Integer.parseInt(mEt_count.getText().toString());
        count++;
        mReduce.setEnabled(true);
        mEt_count.setText(count + "");
        tv_count.setText("x" + count);
        String money = et_money.getText().toString();
        if (TextUtils.isEmpty(money)) {
            money = "0";
        }
        if (count * group_price >= Double.parseDouble(money)) {
            payMoney = count * group_price - Double.parseDouble(money);
        } else {
            et_money.setText("" + count * group_price);
            payMoney = 0.0f;
        }
        total_price.setText("¥" + AppUtil.TwoPoint(payMoney));
    }

    /**
     * 减少数量
     */
    private void reduceCount() {
        if (TextUtils.isEmpty(mEt_count.getText().toString().trim())) {
            return;
        }
        count = Integer.parseInt(mEt_count.getText().toString());
        count--;
        if (count == 1) {
            mReduce.setEnabled(false);
        }
        mEt_count.setText(count + "");
        tv_count.setText("x" + count);
        String money = et_money.getText().toString();
        if (TextUtils.isEmpty(money)) {
            money = "0";
        }
        if (count * group_price >= Double.parseDouble(money)) {
            payMoney = count * group_price - Double.parseDouble(money);
        } else {
            et_money.setText("" + count * group_price);
            payMoney = 0.0f;
        }
        total_price.setText("¥" + AppUtil.TwoPoint(payMoney));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CHANGE_ADDRESS_RECODE) {
            if (data != null) {
                String isExist = data.getStringExtra("isExist");
                AddressBean.AddressListBean bean = (AddressBean.AddressListBean) data.getSerializableExtra("address");
                if (isExist.equals("1") && bean != null) {
                    receiver.setText("收货人:" + bean.getShipper());
//                    Log.i(TAG, "收货人:" + bean.getShipper());
                    phone.setText(bean.getMobile_no());
                    address.setText("收货地址:" + bean.getAddress_detail());
//                    Log.i(TAG, "收货地址:" + bean.getAddress_detail());
                    addressId = bean.getAddress_id();
                    receiver.setVisibility(View.VISIBLE);
                    phone.setVisibility(View.VISIBLE);
                    address.setVisibility(View.VISIBLE);
                    set_address.setVisibility(View.GONE);
                } else {
                    addressId = "-1";
                    receiver.setVisibility(View.GONE);
                    phone.setVisibility(View.GONE);
                    address.setVisibility(View.GONE);
                    set_address.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getAddress();
        getBalance();
    }
}
