package com.ovu.lido.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.base.MyViewPager;
import com.ovu.lido.util.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 展现大图
 */

public class ShowBigPicActivity extends BaseActivity {
    @BindView(R.id.action_bar)
    View action_bar;
    @BindView(R.id.vp_showbig_img)
    MyViewPager mVp_showbig_img;

    private List<PhotoView> mList = new ArrayList<>();
    private ArrayList<String> mPath = new ArrayList<>();
    private Intent mIntent;
    private String Tag = "ShowBigPicActivity";

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_show_big;
    }

    @Override
    protected void loadData() {
        super.loadData();
        mIntent = getIntent();
        mPath = mIntent.getStringArrayListExtra("imageList");
        Log.i(TAG, "大图地址: " + mPath.toString());
        if (mPath != null) {
            for (int i = 0; i < mPath.size(); i++) {
                PhotoView image = new PhotoView(this);
                image.enable();
                image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                mList.add(image);
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.big_image_error)
                        .error(R.drawable.big_image_error);
                Glide.with(this)
                        .load(mPath.get(i))
                        .apply(options)
                        .into(image);
            }
            mVp_showbig_img.setAdapter(mImageAdapter);
            if (mIntent.getStringExtra("position") != null) {
                int position = Integer.parseInt(mIntent.getStringExtra("position"));
                mVp_showbig_img.setCurrentItem(position);
            }
        }

    }


    private PagerAdapter mImageAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            Logger.i(Tag, "控件大小" + mList.size());
            return mPath == null ? 0 : mPath.size();

        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mList.get(position));
            mList.get(position).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            return mList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
