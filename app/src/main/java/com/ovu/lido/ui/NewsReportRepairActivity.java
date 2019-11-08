package com.ovu.lido.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.fragment.FamilyRepairsFragment;
import com.ovu.lido.fragment.PublicAreaRepairsFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 报事报修
 */
public class NewsReportRepairActivity extends BaseActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;

    @BindView(R.id.report_repair_rg)
    RadioGroup mRadioGroup;
    private FragmentManager mSupportFragmentManager;
    private Fragment mCurrntShowFragment;
    private FamilyRepairsFragment mFamilyRepairsFragment;
    private PublicAreaRepairsFragment mPublicAreaRepairsFragment;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl)
                .statusBarDarkFont(true,0.2f)
                .init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_news_report_repair;
    }

    @Override
    protected void initView() {
        super.initView();
        mSupportFragmentManager = getSupportFragmentManager();
        loadFragment();
        mRadioGroup.check(R.id.family_report_repair_rb);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mRadioGroup.setOnCheckedChangeListener(this);
    }


    private void loadFragment() {
        mFamilyRepairsFragment = new FamilyRepairsFragment();
        mPublicAreaRepairsFragment = new PublicAreaRepairsFragment();
        ctrlFragment(mFamilyRepairsFragment);
    }

    @Override
    protected void loadData() {
        super.loadData();

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.family_report_repair_rb://家庭报修
                ctrlFragment(mFamilyRepairsFragment);
                break;
            case R.id.public_report_repair_rb://公共区域报修
                ctrlFragment(mPublicAreaRepairsFragment);
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
            fragmentTransaction.add(R.id.join_content, fragment);

        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.commit();
        mCurrntShowFragment = fragment;
    }

    @OnClick({R.id.back_iv,R.id.my_work_order_tv})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.my_work_order_tv:
                startActivity(new Intent(mContext,MyWorkOrderActivity.class));
                break;
        }
    }
}
