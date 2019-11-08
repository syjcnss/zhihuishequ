package com.ovu.lido.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.adapter.PaymentAdapter;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.PaymentInfo;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.RefreshConstant;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.CustomExpandableListView;
import com.ovu.lido.widgets.LoadProgressDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 线上缴费--首页
 */
public class OnlinePaymentActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.history_tv)
    TextView history_tv;
    @BindView(R.id.total_arrears_tv)
    TextView total_arrears_tv;
    @BindView(R.id.cost_classification_list)
    CustomExpandableListView cost_classification_list;
    @BindView(R.id.btn_select_all)
    CheckBox btn_select_all;
    @BindView(R.id.tv_pay_amount)
    TextView tv_pay_amount;

    private List<PaymentInfo.DataBean.ListItemBean> paymentList = new ArrayList<>();
    private PaymentAdapter mPaymentAdapter;
    private double need_pay_amount;
    private Map<String, String> paramMap;
    private Dialog mDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f);
        mImmersionBar.titleBar(action_bar_rl).init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_online_payment;
    }

    @Override
    protected void initView() {
        super.initView();
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
        mPaymentAdapter = new PaymentAdapter(mContext, paymentList);
        cost_classification_list.setAdapter(mPaymentAdapter);

        btn_select_all.setChecked(true);
    }

    @Override
    protected void setListener() {
        super.setListener();
        cost_classification_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                PaymentInfo.DataBean.ListItemBean.BillListBean billListBean = paymentList.get(groupPosition).getBillList().get(childPosition);

                if (billListBean.isIs_select()) {
                    billListBean.setIs_select(false);
                } else {
                    billListBean.setIs_select(true);
                }
                calculatedAmount();

                btn_select_all.setChecked(dealAllParentIsChecked());

                mPaymentAdapter.notifyDataSetChanged();
                return false;
            }
        });

    }


    private boolean dealAllParentIsChecked() {
        for (int i = 0; i < paymentList.size(); i++) {
            List<PaymentInfo.DataBean.ListItemBean.BillListBean> billList = paymentList.get(i).getBillList();
            for (int j = 0; j < billList.size(); j++) {
                boolean is_select = billList.get(j).isIs_select();
                if (!is_select) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void loadData() {
        super.loadData();
        refreshData();

    }

    private void refreshData() {
        mDialog.show();
        paymentList.clear();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        String params = ViewHelper.getParams(paramMap);

        OkHttpUtils.post()
                .url(Constant.PAYMENT_LIST_URL)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                        if (mContext == null || isFinishing()){
                            return;
                        }
                        LoadProgressDialog.closeDialog(mDialog);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "缴费列表数据: " + response);
                        LoadProgressDialog.closeDialog(mDialog);
                        PaymentInfo info = GsonUtil.GsonToBean(response, PaymentInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            paymentList.clear();
                            PaymentInfo.DataBean data = info.getData();
                            total_arrears_tv.setText(ViewHelper.getDisplayPrice(data.getSumAmount()));
                            paymentList.addAll(data.getListItem());
                            mPaymentAdapter.notifyDataSetChanged();
                            handler.sendEmptyMessage(0);
                        } else {
                            showShortToast(info.getErrorMsg());
                        }
                    }
                });
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    btn_select_all.setChecked(true);
                    setupAllChecked(true);
                    if (paymentList == null) {
//                        tv_pay_amount.setText("合计￥0.00元");
                        return;
                    }

                    for (int i = 0; i < paymentList.size(); i++) {
                        cost_classification_list.expandGroup(i);
                    }
                    break;
            }
        }
    };

    @OnClick({R.id.back_iv, R.id.pay_btn, R.id.history_tv, R.id.btn_select_all})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv://返回
                finish();
                break;
            case R.id.pay_btn://立即付款
                String needPayAmount = ViewHelper.getDisplayPrice(need_pay_amount);
                Log.i(TAG, "onClick: " + needPayAmount);
                List<HashMap<String, String>> list = new ArrayList<>();
                List<Integer> typeIds = new ArrayList<>();//已选中条目type_id集合
                List<Integer> billIds = new ArrayList<>();//已选中条目id集合
                List<PaymentInfo.DataBean.ListItemBean.BillListBean> bList = new ArrayList<>();//已选中条目集合
                List<Integer> numList = new ArrayList<>();
                for (int i = 0; i < paymentList.size(); i++) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    int type_id = paymentList.get(i).getType_id();//大类id

                    StringBuffer sb = new StringBuffer();
                    List<PaymentInfo.DataBean.ListItemBean.BillListBean> billList = paymentList.get(i).getBillList();
                    for (int j = 0; j < billList.size(); j++) {
                        PaymentInfo.DataBean.ListItemBean.BillListBean bean = billList.get(j);
                        if (bean.isIs_select()) {
                            bList = billList;
                            typeIds.add(type_id);
                            billIds.add(bean.getId());
                            numList.add(bean.getNum());
                            sb.append(bean.getRecord_id());
                            sb.append(",");
                        }

                    }
                    if (sb.length() > 0) {
                        hashMap.put(type_id + "", sb.substring(0, sb.length() - 1));
                        list.add(hashMap);
                    }
                    Log.i(TAG, "list: " + list.toString());
                }
                Log.i(TAG, "---------------------------typeIds: " + typeIds.toString() + "\n billIds: " + billIds.toString());

                if (typeIds.size() > 0) {
                    boolean rule = isRule(typeIds, billIds);
                    if (rule) {
                        if (bList.size() < 3) {
                            if (typeIds.size()<bList.size()){
                                showShortToast("请缴纳所有");
                            }else {
                                startActivity(new Intent(mContext, BillPayActivity.class).putExtra("needPayAmount", Double.valueOf(needPayAmount)).putExtra("id_list", (Serializable) list));
                            }
                        } else {
                            if (billIds.size() >= 3) {

                                boolean inARow = isInARow(numList);
                                Log.i(TAG, "inARow: " + inARow);
                                if (inARow){
                                    startActivity(new Intent(mContext, BillPayActivity.class).putExtra("needPayAmount", Double.valueOf(needPayAmount)).putExtra("id_list", (Serializable) list));
                                }else {
                                    showShortToast("不能跨月份缴费");
                                }

                            } else {
                                showShortToast("请至少缴纳3个月");
                            }
                        }


                    } else {
                        showShortToast("请选择相同类别费用提交");
                    }

                } else {
                    showShortToast("请先选择费用单");
                }
                break;
            case R.id.history_tv://历史
                startActivity(new Intent(mContext, PayHistoryActivity.class));
                break;
            case R.id.btn_select_all://全选
                boolean checked = btn_select_all.isChecked();
                setupAllChecked(checked);
                break;
        }
    }

    /**
     * 是否是从第一个欠费月并连续月份缴费
     * @param numList
     * @return
     */
    private boolean isInARow(List<Integer> numList){
        if (numList.get(0) != 1){
            return false;
        }
        for (int i = 0; i < numList.size() - 1; ++i) {
            if (numList.get(i) + 1 != numList.get(i+1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否符合缴费规则
     *
     * @param typeIds 大类id
     * @param billIds 小类id
     * @return
     */
    private boolean isRule(List<Integer> typeIds, List<Integer> billIds) {
        int typeId = typeIds.get(0);
        for (int id : typeIds) {
            if (typeId != id) {
                return false;
            }
        }
        int billId = billIds.get(0);
        for (int id : billIds) {
            if (billId != id) {
                return false;
            }
        }
        return true;
    }

    /**
     * 供全选按钮调用
     *
     * @param checked 是否全选
     */
    private void setupAllChecked(boolean checked) {
        if (paymentList != null && !paymentList.isEmpty()) {
            for (int i = 0; i < paymentList.size(); i++) {
                paymentList.get(i).setIs_select(checked);
                List<PaymentInfo.DataBean.ListItemBean.BillListBean> billList = paymentList.get(i).getBillList();
                for (int j = 0; j < billList.size(); j++) {
                    billList.get(j).setIs_select(checked);
                }
            }

            calculatedAmount();
        }else {
            tv_pay_amount.setText("合计￥0.00元");
        }

        mPaymentAdapter.notifyDataSetChanged();
    }

    /**
     * 计算总金额
     */
    private void calculatedAmount() {
        need_pay_amount = 0.00;
        for (int i = 0; i < paymentList.size(); i++) {
            for (int j = 0; j < paymentList.get(i).getBillList().size(); j++) {
                if (paymentList.get(i).getBillList().get(j).isIs_select()) {
                    double childMoney = paymentList.get(i).getBillList().get(j).getAmount();
                    need_pay_amount += childMoney;
                }
            }
        }

        tv_pay_amount.setText("合计￥" + ViewHelper.getDisplayPrice(need_pay_amount) + "元");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        int position = event.getPos();
        if (position == RefreshConstant.PRODUCT_BUY_SUCCESS) {
            finish();
        }else if (position == RefreshConstant.WALLET_PAY_SUCCESS){
            refreshData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
