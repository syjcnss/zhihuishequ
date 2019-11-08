package com.ovu.lido.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.PayResultBean;
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

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

public class PayResultActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.status_iv)
    ImageView status_iv;
    @BindView(R.id.status_tv)
    TextView status_tv;
    @BindView(R.id.status_msg_tv)
    TextView status_msg_tv;
    @BindView(R.id.check_order_tv)
    TextView check_order_tv;
    private int orderStatus;
    private String orderId;
    private String orderProductId;
    private String mBusinessType;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void loadData() {
        super.loadData();
        orderStatus = getIntent().getIntExtra("order_status", 0);
        orderId = getIntent().getStringExtra("order_id");
        orderProductId = getIntent().getStringExtra("order_product_id");
        mBusinessType = getIntent().getStringExtra("business_type");
        if (TextUtils.equals(mBusinessType, "10")) {//商品购买 则可以查看订单
            check_order_tv.setVisibility(View.VISIBLE);
        }

        if (orderStatus == 1) {
            status_iv.setImageResource(R.drawable.pay_success_icon);
            status_tv.setText("付款成功");
            status_msg_tv.setText("订单号：" + orderId);
        } else {
            status_iv.setImageResource(R.drawable.pay_fail_icon);
            status_tv.setText("支付失败");
            status_msg_tv.setText("失败原因：第三方平台请求超时");
        }
    }

    @Override
    protected void setListener() {
        findViewById(R.id.back_iv).setOnClickListener(this);
    }

    @OnClick({R.id.back_iv, R.id.back_home_tv, R.id.check_order_tv})
    public void onClick(View view) {
        RefreshEvent.JDPayResult jdPayResult = new RefreshEvent.JDPayResult();

        switch (view.getId()) {
            case R.id.back_iv:
                EventBus.getDefault().post(new RefreshEvent(RefreshConstant.PRODUCT_BUY_SUCCESS));//维修服务、特供商品支付通知
                jdPayResult.setResultCode(1);
                EventBus.getDefault().post(jdPayResult);//京东商品支付通知
                finish();
                break;
            case R.id.back_home_tv:
                EventBus.getDefault().post(new RefreshEvent(RefreshConstant.PRODUCT_BUY_SUCCESS));
                jdPayResult.setResultCode(2);//2、支付结果返回首页
                EventBus.getDefault().post(jdPayResult);//京东商品支付通知
                finish();
                break;
            case R.id.check_order_tv:
                //查看订单
                Intent intent = new Intent();
                intent.setClass(mContext, OrderDetailActivity.class);
                intent.putExtra("mOrderProductId", orderProductId);
                startActivity(intent);
                EventBus.getDefault().post(new RefreshEvent(RefreshConstant.PRODUCT_BUY_SUCCESS));
                finish();
                break;
        }
    }

}
