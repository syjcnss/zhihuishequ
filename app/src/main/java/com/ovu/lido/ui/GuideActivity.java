package com.ovu.lido.ui;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.BarHide;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

public class GuideActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.banner_guide_background)
    BGABanner mBackgroundBanner;
    @BindView(R.id.banner_guide_foreground)
    BGABanner mForegroundBanner;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).hideBar(BarHide.FLAG_HIDE_STATUS_BAR).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        super.initView();

    }

    @Override
    protected void setListener() {
        super.setListener();
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */
        mForegroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                AppPreference.I().putBoolean("isFirstOpen",false);
                startActivity(new Intent(GuideActivity.this, EnterMethodActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
        BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
        // 设置数据源
        mBackgroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.guide_up_1,
                R.drawable.guide_up_2,
                R.drawable.guide_up_3,
                R.drawable.guide_up_4);

        mForegroundBanner.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                R.drawable.guide_up_1,
                R.drawable.guide_up_2,
                R.drawable.guide_up_3,
                R.drawable.guide_up_4);
    }
}
