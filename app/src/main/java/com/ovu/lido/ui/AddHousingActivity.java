package com.ovu.lido.ui;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.util.AppUtil;

import butterknife.BindView;

/**
 * 新增房产
 */
public class AddHousingActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.ll_phone)
    LinearLayout phone_layout;
    private String checkMobileString;// 预留手机号中间4位


    @Override
    protected int setLayoutId() {
        return R.layout.activity_add_housing;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).init();
    }

    @Override
    protected void initView() {
        showCertificationTelView();
    }


    @Override
    protected void setListener() {
        iv_back.setOnClickListener(this);


    }

    private void hintCertificationTelView() {
        checkMobileString = "";
//        phone_start.setText("");
//        phone_end.setText("");
        phone_layout.removeAllViews();
    }

    private void showCertificationTelView() {
        phone_layout.removeAllViews();
        final int size = 4;
        EditText editText = null;
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < size; i++) {
            final int index = i;
            editText = (EditText) inflater.inflate(R.layout.linearlayout_tel_item, phone_layout, false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.width = 0;
            params.weight = 1;
            params.leftMargin = AppUtil.dip2px(this, 5);
            phone_layout.addView(editText, params);
            editText.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 1 && index < size - 1) {
                        EditText next = (EditText) phone_layout.getChildAt(index + 1);
                        next.requestFocus();
                    }
                }
            });
            editText.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        for (int j = 0; j < size; j++) {
                            EditText childAt = (EditText) phone_layout.getChildAt(j);
                            childAt.setText(null);
                            if (j == 0) {
                                childAt.requestFocus();
                            }
                        }
                    }
                    return false;
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
        }
    }
}
