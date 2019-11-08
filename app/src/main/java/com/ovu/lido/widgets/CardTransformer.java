package com.ovu.lido.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by wangw on 2019/5/23.
 */
public class CardTransformer implements ViewPager.PageTransformer {
    private static final float MIN_ALPHA = 1.0f;//遮罩透明度
    private static final float MIN_SCALE = 0.90f;//卡片圆角

    @Override
    public void transformPage(View page, float position) {
        if (position < -1 || position > 1) {
            page.setAlpha(MIN_ALPHA);
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        } else {
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            //不透明->半透明
            if (position < 0) {//[0,-1]
                page.setAlpha(MIN_ALPHA + (1 + position) * (1 - MIN_ALPHA));
                float scaleX = 1 + (1.0f - MIN_SCALE) * position;
                page.setScaleX(scaleX);
                page.setScaleY(scaleX);
            } else {//[1,0]
                //半透明->不透明
                page.setAlpha(MIN_ALPHA + (1 - position) * (1 - MIN_ALPHA));
                float scaleX = 1 - (1.0f - MIN_SCALE) * position;
                page.setScaleX(scaleX);
                page.setScaleY(scaleX);
            }
            page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        }
    }
}
