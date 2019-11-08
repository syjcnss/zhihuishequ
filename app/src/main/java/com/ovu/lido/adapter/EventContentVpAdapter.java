package com.ovu.lido.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.bean.CommunityActivityInfo;
import com.ovu.lido.bean.HomePageInfo;
import com.ovu.lido.ui.ActivityDetailedActivity;

import java.util.List;

public class EventContentVpAdapter extends PagerAdapter {
    private Context mContext;
    private List<HomePageInfo.DataBean.ActivitiesDataBean> dataBeans;

    public EventContentVpAdapter(Context mContext, List<HomePageInfo.DataBean.ActivitiesDataBean> stringList) {
        this.mContext = mContext;
        this.dataBeans = stringList;
    }

    @Override
    public int getCount() {
        return dataBeans == null ? 0 : dataBeans.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.event_vp_item, container, false);

        CardView event_content_cv = view.findViewById(R.id.event_content_cv);
        event_content_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActivityDetailedActivity.class);
                intent.putExtra("activityId", dataBeans.get(position).getId() + "");
                mContext.startActivity(intent);
            }
        });
        ImageView event_content_pic_iv = view.findViewById(R.id.event_content_pic_iv);
        TextView event_content_title_tv = view.findViewById(R.id.event_content_title_tv);
        TextView people_num_tv = view.findViewById(R.id.people_num_tv);
        TextView join_tv = view.findViewById(R.id.join_tv);

        event_content_cv.setRadius(20);
        event_content_cv.setCardElevation(8);
        HomePageInfo.DataBean.ActivitiesDataBean data = dataBeans.get(position);
        Glide.with(mContext)
                .load(data.getActivity_img())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.activity_image_error)
                        .error(R.drawable.activity_image_error)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(event_content_pic_iv);
        event_content_title_tv.setText(data.getActivity_name());
        //如果参加过 或 已经结束
        if (data.isIs_end()){
            join_tv.setText("已结束");
        }else {
            if (data.isIs_enroll()) {
                join_tv.setText("已参加");
                join_tv.setEnabled(false);
            } else {
                join_tv.setText("去参加");
                join_tv.setEnabled(true);
            }
        }
        people_num_tv.setText(data.getEnrollCount() + "/" + data.getEnroll_limit());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
