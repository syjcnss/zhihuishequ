package com.ovu.lido.fragment;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseFragment;
import com.ovu.lido.ui.MyActivity;
import com.ovu.lido.ui.MyOrderActivity;
import com.ovu.lido.ui.SearchActivity;

import butterknife.BindView;

/**
 * 活动
 */
public class EventHomeFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.title_rl)
    RelativeLayout title_tv;
    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.rb_activity)
    RadioButton mRb_activity;
    @BindView(R.id.iv_search)
    ImageView mIv_search;
    @BindView(R.id.iv_activity)
    ImageView mIv_activity;
    @BindView(R.id.iv_shop)
    ImageView mIv_shop;
    private FragmentManager fragmentManager;
    private ActivityFragment mActivityFragment;
    private GroupBuyFragment mGroupBuyFragment;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_event_home;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_tv)
                .statusBarDarkFont(true,0.2f).init();
    }

    @Override
    protected void initView() {
        mRb_activity.setChecked(true);
        showFrgment(0);
        changeUI(0);
    }

    @Override
    protected void setListener() {
        findActivityViewById(R.id.rb_activity).setOnClickListener(this);
        findActivityViewById(R.id.rb_groupBuy).setOnClickListener(this);
        findActivityViewById(R.id.iv_shop).setOnClickListener(this);
        findActivityViewById(R.id.iv_activity).setOnClickListener(this);
        mIv_shop.setOnClickListener(this);
        mIv_search.setOnClickListener(this);
        mIv_activity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_activity:
                showFrgment(0);
                changeUI(0);
                break;
            case R.id.rb_groupBuy:
                showFrgment(1);
                changeUI(1);
                break;
            case R.id.iv_search:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.iv_shop:
                startActivity(new Intent(getActivity(), MyOrderActivity.class));
                break;
            case R.id.iv_activity:
                startActivity(new Intent(getActivity(), MyActivity.class));
                break;
        }
    }

    private void changeUI(int i) {
        if (i == 0) {
            mIv_activity.setVisibility(View.VISIBLE);
            mIv_search.setVisibility(View.GONE);
            mIv_shop.setVisibility(View.GONE);
        } else {
            mIv_activity.setVisibility(View.GONE);
            mIv_search.setVisibility(View.VISIBLE);
            mIv_shop.setVisibility(View.VISIBLE);
        }
    }


    private void showFrgment(int i) {

        fragmentManager = getFragmentManager();
        mFragmentTransaction = fragmentManager.beginTransaction();
        hideAllFragment(mFragmentTransaction);
        switch (i) {
            case 0:
                if (mActivityFragment == null) {
                    mActivityFragment = new ActivityFragment();
                    mFragmentTransaction.add(R.id.fl_activity, mActivityFragment);
                } else {
                    mFragmentTransaction.show(mActivityFragment);
                    mActivityFragment.getData();
                }
                break;
            case 1:
                if (mGroupBuyFragment == null) {
                    mGroupBuyFragment = new GroupBuyFragment();
                    mFragmentTransaction.add(R.id.fl_activity, mGroupBuyFragment);

                } else {
                    mFragmentTransaction.show(mGroupBuyFragment);
                    mGroupBuyFragment.getData();
                }
                break;
        }
        mFragmentTransaction.commit();
    }

    private void hideAllFragment(FragmentTransaction mFragmentTransaction) {
        if (mActivityFragment != null) {
            mFragmentTransaction.hide(mActivityFragment);
        }
        if (mGroupBuyFragment != null) {
            mFragmentTransaction.hide(mGroupBuyFragment);
        }

    }


    /**
     * 刷新数据
     */
    public void refreshData() {
        if (mActivityFragment != null) {
            mActivityFragment.loadData();
        }
        if (mGroupBuyFragment != null) {
            mGroupBuyFragment.loadData();
        }
    }
}
