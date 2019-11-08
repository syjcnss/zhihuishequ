package com.ovu.lido.ui;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.StringUtils;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class InvoiceTypeActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;

    @BindView(R.id.radio_group)
    RadioGroup radio_group;
    @BindView(R.id.invoice_title_et)
    EditText invoice_title_et;
    @BindView(R.id.tax_id_et)
    EditText tax_id_et;
    @BindView(R.id.invoice_title_ll)
    LinearLayout invoice_title_ll;
    @BindView(R.id.tax_id_ll)
    LinearLayout tax_id_ll;
    private int invoice_type = 0; //默认不开发票

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_invoice_type;
    }

    @Override
    protected void initView() {
        super.initView();
        radio_group.check(R.id.no_invoice_rb);
        setLayoutVisible();
    }

    @Override
    protected void setListener() {
        super.setListener();
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.no_invoice_rb:
                        invoice_type = 0;
                        break;
                    case R.id.personal_invoice_rb:
                        invoice_type = 1;
                        break;
                    case R.id.unit_invoice_rb:
                        invoice_type = 2;
                        break;
                }
                setLayoutVisible();
            }
        });
    }

    private void setLayoutVisible() {
        switch (invoice_type) {
            case 0:
                tax_id_ll.setVisibility(View.GONE);
                invoice_title_ll.setVisibility(View.GONE);
                break;
            case 1:
                invoice_title_ll.setVisibility(View.VISIBLE);
                tax_id_ll.setVisibility(View.GONE);
                break;
            case 2:
                tax_id_ll.setVisibility(View.VISIBLE);
                invoice_title_ll.setVisibility(View.VISIBLE);
                break;
        }

    }

    @OnClick({R.id.enter_tv, R.id.back_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.enter_tv:
                String title = invoice_title_et.getText().toString().trim();
                String tax_id = tax_id_et.getText().toString().trim();


                if (invoice_type == 0) {
                    Intent intent = new Intent();
                    intent.putExtra("invoice_type", invoice_type);
                    intent.putExtra("invoice_title", "");
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (invoice_type == 1){

                    if (StringUtils.isEmpty(title)) {
                        showShortToast("请填写发票抬头");
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("invoice_type", invoice_type);
                        intent.putExtra("invoice_title", title);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }else if (invoice_type == 2){

                    if (StringUtils.isEmpty(title)) {
                        showShortToast("请填写发票抬头");
                    }else if (StringUtils.isEmpty(tax_id)){
                        showShortToast("请填写单位税号");
                    }else {
                        Intent intent = new Intent();
                        intent.putExtra("invoice_type", invoice_type);
                        intent.putExtra("invoice_title", title+tax_id);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
                break;
        }
    }
}
