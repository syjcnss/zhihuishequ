package com.ovu.lido.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.util.Constant;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 进入方式  注册/登录
 */
public class EnterMethodActivity extends BaseActivity {
    @BindView(R.id.privacy_tv)
    TextView privacy_tv;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_enter_method;
    }

    @Override
    protected void initView() {
        super.initView();
        privacy_tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//设置下划线
        privacy_tv.getPaint().setAntiAlias(true);//抗锯齿
    }

    @Override
    protected void loadData() {
        super.loadData();

    }

    @Override
    protected void setListener() {
        super.setListener();

    }

    @OnClick({R.id.register_tv, R.id.login_tv, R.id.privacy_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_tv://注册
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.login_tv://登录
                startActivity(new Intent(mContext, LoginActivity.class));
                break;
            case R.id.privacy_tv://隐私政策
                startActivity(new Intent(mContext,CommonWebActivity.class).putExtra("url",Constant.PRIVACY_URL));
                break;
        }
    }
}
