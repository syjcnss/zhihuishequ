package com.ovu.lido.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;

import butterknife.BindView;

public class RechargeActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.et_money)
    EditText et_money;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_recharge;
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).init();
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void setListener() {
        findViewById(R.id.tv_next).setOnClickListener(this);
        findViewById(R.id.back_iv).setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_next:
                String amount = et_money.getText().toString();
                if (TextUtils.isEmpty(amount)) {
                    showToast("充值金额不能为空");
                    return;
                }
                Intent intent = new Intent(this, PaymentMethodActivity.class);
                intent.putExtra("needPayAmount", Double.parseDouble(amount));
                intent.putExtra("business_type", "5");
                startActivity(intent);
                break;
        }
    }
}

