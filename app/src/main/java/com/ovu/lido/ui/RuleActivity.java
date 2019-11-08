package com.ovu.lido.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.fragment.EventRuleFragment;
import com.ovu.lido.fragment.IntegralRuleFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class RuleActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.rule_rg)
    RadioGroup mRadioGroup;

    private FragmentManager mSupportFragmentManager;
    private Fragment mCurrntShowFragment;
    private EventRuleFragment mEventRuleFragment;
    private IntegralRuleFragment mIntegralRuleFragment;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true,0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_rule;
    }

    @Override
    protected void initView() {
        super.initView();
        mSupportFragmentManager = getSupportFragmentManager();
        loadFragment();
        mRadioGroup.check(R.id.integral_rule_rb);
    }

    private void loadFragment() {
        mIntegralRuleFragment = new IntegralRuleFragment();
        mEventRuleFragment = new EventRuleFragment();
        ctrlFragment(mIntegralRuleFragment);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.integral_rule_rb://积分规则
                        ctrlFragment(mIntegralRuleFragment);
                        break;
                    case R.id.event_rule_rb://活动规则
                        ctrlFragment(mEventRuleFragment);
                        break;
                }
            }
        });
    }

    @OnClick(R.id.back_iv)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }

    /**
     * @param fragment 当前要显示的fragment
     */
    private void ctrlFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mSupportFragmentManager.beginTransaction();

        if (null != mCurrntShowFragment) {
            fragmentTransaction.hide(mCurrntShowFragment);
        }

        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.rule_content, fragment);

        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.commit();
        mCurrntShowFragment = fragment;
    }


}
