package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.BillPayInfo;
import com.ovu.lido.bean.UpLoadInfo;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.RefreshConstant;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 线上缴费 -- 余额缴费
 */
public class BillPayActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.account_balance_tv)
    TextView account_balance_tv;//账户余额
    @BindView(R.id.invoice_type_tv)
    TextView invoice_type_tv;//发票类型
    @BindView(R.id.need_pay_tv)
    TextView need_pay_tv;//应缴金额
    @BindView(R.id.use_balance_et)
    EditText use_balance_et;
    @BindView(R.id.real_pay_tv)
    TextView real_pay_tv;//实缴金额
    private double mNeedPayAmount;
    private Serializable mIdList;
    private double mRealPayAmount;
    private double balance;
    private double use_advance_pay_amount;
    private String mInvoiceTitle;
    private int mInvoiceType;
    private Dialog loadingDialog;


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f)
                .titleBar(action_bar_rl).init();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_bill_pay;
    }

    @Override
    protected void initView() {
        super.initView();
        loadingDialog = LoadProgressDialog.createLoadingDialog(mContext);
        mNeedPayAmount = getIntent().getDoubleExtra("needPayAmount",0);//应缴金额
//        mIdList = getIntent().getStringExtra("id_list");
       mIdList = getIntent().getSerializableExtra("id_list");
        Log.i(TAG, "id_list:------------------------ " + mIdList);
        mRealPayAmount = mNeedPayAmount;
        Log.i(TAG, "mNeedPayAmount: " + mNeedPayAmount + "\t id_list: " + mIdList);
        need_pay_tv.setText(ViewHelper.getDisplayPrice(mNeedPayAmount));
        real_pay_tv.setText(ViewHelper.getDisplayPrice(mRealPayAmount));
    }

    @Override
    protected void setListener() {
        super.setListener();
        use_balance_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                }

                if (!StringUtils.isEmpty(s.toString().trim())) {
                    Double use_balance = Double.parseDouble(s.toString().trim());
                    double d = mNeedPayAmount;
                    if (balance > d) {
                        if (use_balance > d) {
                            s = d + "";
                            use_balance_et.setText(s + "");
                            use_balance_et.setSelection(s.length());
                        }
                    } else {
                        if (use_balance > balance) {
                            s = balance + "";
                            use_balance_et.setText(s + "");
                            use_balance_et.setSelection(s.length());
                        }
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                String use_balance_text = use_balance_et.getText().toString().trim();
                if (!StringUtils.isEmpty(use_balance_text)) {
                    use_advance_pay_amount = Double.parseDouble(use_balance_text);
                    if (use_advance_pay_amount > mNeedPayAmount) {
                        use_advance_pay_amount = mNeedPayAmount;
                        use_balance_et.setText(String.valueOf(use_advance_pay_amount));
                    }
                } else {
                    use_advance_pay_amount = 0;
                }

                mRealPayAmount = mNeedPayAmount - use_advance_pay_amount;
                real_pay_tv.setText(ViewHelper.getDisplayPrice(mRealPayAmount));
            }
        });
    }

    @OnClick({R.id.back_iv,R.id.invoice_type_tv,R.id.btn_pay})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.invoice_type_tv:
                // 发票
                Intent intent = new Intent(this, InvoiceTypeActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_pay:
                String invoice_type = invoice_type_tv.getText().toString().trim();
                if (StringUtils.isEmpty(invoice_type)) {
                    showShortToast("请选择发票类型");
                    return;
                }
                if (mRealPayAmount == 0) {
                    // 全部抵扣完
                    allDeductions();
                } else {
                    // 部分抵扣或不抵扣
                    partialDeduction();

                }
                break;
        }
    }

    /**
     * 部分抵扣或不抵扣
     */
    private void partialDeduction() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id_list", mIdList);
        map.put("use_advance_pay_amount", ViewHelper.getDisplayPrice(use_advance_pay_amount));
        map.put("amount", ViewHelper.getDisplayPrice(mRealPayAmount));
        map.put("invoice_title", mInvoiceTitle);//发票抬头
        map.put("invoice_type", String.valueOf(mInvoiceType));//发票类型

        Intent intent = new Intent(mContext, PaymentMethodActivity.class);
        intent.putExtra("needPayAmount", Double.valueOf(ViewHelper.getDisplayPrice(mRealPayAmount)));
        intent.putExtra("business_type", "4");
        intent.putExtra("business_data", map);
        startActivity(intent);
    }

    /**
     * 全部抵扣
     */
    private void allDeductions() {
        loadingDialog.show();
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("resident_id", AppPreference.I().getString("resident_id",""));
        paramMap.put("invoice_type", String.valueOf(mInvoiceType));
        paramMap.put("invoice_title",mInvoiceTitle);
        paramMap.put("use_advance_pay_amount", String.valueOf(use_advance_pay_amount));
        String params = ViewHelper.getParams(paramMap);
        Log.i(TAG, "params: " + params);
        OkHttpUtils.post()
                .url(Constant.ALL_DEDUCTIONS_URL)
                .addParams("params",params)
                .addParams("id_list", GsonUtil.ToGson(mIdList))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (mContext == null || isFinishing()) return;
                        LoadProgressDialog.closeDialog(loadingDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "jsonStr: " + response);
                        LoadProgressDialog.closeDialog(loadingDialog);
                        UpLoadInfo info = GsonUtil.GsonToBean(response, UpLoadInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            showShortToast("缴费成功");
                            EventBus.getDefault().post(new RefreshEvent(RefreshConstant.WALLET_PAY_SUCCESS));
                            finish();
                        }else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    @Override
    protected void loadData() {
        super.loadData();
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("resident_id", AppPreference.I().getString("resident_id",""));
        String params = ViewHelper.getParams(paramMap);
        OkHttpUtils.post()
                .url(Constant.QUERY_NED_PAY_URL)
                .addParams("params",params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        call.cancel();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "jsonStr: " + response);
                        BillPayInfo info = GsonUtil.GsonToBean(response, BillPayInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)){
                            balance = info.getData().getBalance();//应缴金额
                            account_balance_tv.setText(ViewHelper.getDisplayPrice(balance));
                        }else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    mInvoiceType = data.getIntExtra("invoice_type", 0);
                    mInvoiceTitle = data.getStringExtra("invoice_title");
                    Log.i(TAG, "mInvoiceType: " + mInvoiceType + "\t mInvoiceTitle: " + mInvoiceTitle);
                    switch (mInvoiceType) {
                        case 0:
                            invoice_type_tv.setText("不开发票");
                            break;
                        case 1:
                            invoice_type_tv.setText("个人发票");
                            break;
                        case 2:
                            invoice_type_tv.setText("单位发票");
                            break;

                        default:
                            break;
                    }
                    break;
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
