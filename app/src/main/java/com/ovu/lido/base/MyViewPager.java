package com.ovu.lido.base;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


/**
 * Created by Administrator on 2018/1/11.
 */

public class MyViewPager extends ViewPager {
    private float xDistance, xLast;
    private float mDown, mUp;
    private boolean noScroll = false;
    DrawerLayout mDrawerLayout;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (noScroll) {
            return false;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDown = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                mUp = ev.getX();
                if (mDown - mUp < -100 && getCurrentItem() == 0) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    /**
     * 控制Viewpager是否可滑动
     *
     * @param noScroll : true 不能滑动
     */

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        if (noScroll)
            return false;
        switch (arg0.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xLast = arg0.getX();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                float curX = arg0.getX();
                Log.i("MyviewPager", "按下:" + mDown + "滑动:" + curX);
                Log.i("MyviewPager", "状态:" + (mDown - curX));
                if (mDown - curX < -300 && getCurrentItem() == 0) {
//                    if (!noScroll) {
//                        mDrawerLayout.openDrawer(Gravity.LEFT);
//                    }
                }
                break;
        }

        return super.onTouchEvent(arg0);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }


    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.mDrawerLayout = drawerLayout;
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }


}
