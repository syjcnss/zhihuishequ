package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;

public class VolunteerEventDetailLvAdapter extends BaseAdapter {
    private Context mContext;
    private String[] urls;

    public VolunteerEventDetailLvAdapter(Context mContext, String[] urls) {
        this.mContext = mContext;
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.length;
    }

    @Override
    public Object getItem(int position) {
        return urls[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        MyViewHolder holder;
        if (view == null){
            holder = new MyViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.event_detail_lv_item,parent,false);
            holder.event_detail_item_iv = view.findViewById(R.id.event_detail_item_iv);
            view.setTag(holder);
        }else {
            holder = (MyViewHolder) view.getTag();
        }
        Glide.with(mContext)
                .load(urls[position])
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.activity_image_error)
                        .error(R.drawable.activity_image_error))
                .into(holder.event_detail_item_iv);
        return view;
    }

    class MyViewHolder{
        ImageView event_detail_item_iv;
    }
}
