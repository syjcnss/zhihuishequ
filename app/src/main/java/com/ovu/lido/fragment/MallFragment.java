package com.ovu.lido.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseFragment;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.ViewHelper;

import org.w3c.dom.Text;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商城
 */
public class MallFragment extends BaseFragment {
    @BindView(R.id.title_bar_ll)
    LinearLayout title_bar_ll;
    @BindView(R.id.community_tv)
    TextView community_tv;
    @BindView(R.id.store_tv)
    TextView store_tv;

    private FragmentManager fragmentManager;
    private CommunityFragment mCommunityFragment;
    private StoreFragment mStoreFragment;
    private int tab_index = -1;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(title_bar_ll).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_mall;
    }

    @Override
    protected void initView() {
        super.initView();
        fragmentManager = getChildFragmentManager();

        setTab(0);
    }

    @Override
    protected void setListener() {
        super.setListener();

    }

    @OnClick({R.id.community_tv,R.id.store_tv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.community_tv:
                setTab(0);
                break;
            case R.id.store_tv:
                setTab(1);
                break;
        }
    }

    private void setTab(int index) {
        if (index == tab_index) {
            Log.i(TAG, "点击当前");
            return;
        }
        tab_index = index;
        for (int i = 0; i < title_bar_ll.getChildCount(); i++) {
            title_bar_ll.getChildAt(i).setSelected(i == index);
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (mCommunityFragment != null) {
            fragmentTransaction.hide(mCommunityFragment);
        }
        if (mStoreFragment != null) {
            fragmentTransaction.hide(mStoreFragment);
        }

        if (0 == index) {// 社区服务
            if (mCommunityFragment == null) {
                mCommunityFragment = new CommunityFragment();
                fragmentTransaction.add(R.id.frame_layout, mCommunityFragment);
            } else {
                // 主页已经加载
            }
            fragmentTransaction.show(mCommunityFragment);
        } else if (1 == index) {//附近商店
            if (mStoreFragment == null) {
                mStoreFragment = new StoreFragment();
                fragmentTransaction.add(R.id.frame_layout, mStoreFragment);
            } else {
                // 主页已经加载
            }
            fragmentTransaction.show(mStoreFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

}
