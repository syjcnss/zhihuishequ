package com.ovu.lido.ui;

import android.view.View;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ActivitiesActivity extends BaseActivity {

    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f);
        mImmersionBar.titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_activities;
    }

    @OnClick({R.id.back_iv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
        }
    }

}
