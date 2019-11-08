package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.bean.MyPrizeInfo;

import java.util.List;

public class MyPrizeLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyPrizeInfo.DataBean.ListBean> prizeInfos;

    public MyPrizeLvAdapter(Context mContext, List<MyPrizeInfo.DataBean.ListBean> prizeInfos) {
        this.mContext = mContext;
        this.prizeInfos = prizeInfos;
    }

    @Override
    public int getCount() {
        return prizeInfos == null ? 0 : prizeInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return prizeInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        PrizeViewHolder holder;
        if (view == null){
            holder = new PrizeViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.my_prize_lv_item,parent,false);
            holder.item_icon = view.findViewById(R.id.item_icon);
            holder.item_name = view.findViewById(R.id.item_name);
            holder.item_time = view.findViewById(R.id.item_time);
            holder.item_type = view.findViewById(R.id.item_type);
            view.setTag(holder);
        }else {
            holder = (PrizeViewHolder) view.getTag();
        }
        MyPrizeInfo.DataBean.ListBean bean = prizeInfos.get(position);
        Glide.with(mContext)
                .load(bean.getAwards_img())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_error)
                        .error(R.drawable.ic_error))
                .into(holder.item_icon);
        holder.item_name.setText(bean.getAwards_name());
        holder.item_time.setText(bean.getAwards_time());

        switch (bean.getIs_expiry()){
            case 0:
                holder.item_type.setText("未领取");
                holder.item_name.setTextColor(mContext.getResources().getColor(R.color.title_text));
                holder.item_time.setTextColor(mContext.getResources().getColor(R.color.content_text));
                holder.item_type.setTextColor(mContext.getResources().getColor(R.color.action_bar_color));
                break;
            case 1:
                holder.item_type.setText("已领取");
                holder.item_name.setTextColor(mContext.getResources().getColor(R.color.title_text));
                holder.item_time.setTextColor(mContext.getResources().getColor(R.color.content_text));
                holder.item_type.setTextColor(mContext.getResources().getColor(R.color.action_bar_color));
                break;
            case 2:
                holder.item_type.setText("已过期");
                holder.item_name.setTextColor(mContext.getResources().getColor(R.color.text_bbb));
                holder.item_time.setTextColor(mContext.getResources().getColor(R.color.text_bbb));
                holder.item_type.setTextColor(mContext.getResources().getColor(R.color.text_bbb));
                break;
        }

        return view;
    }

    class PrizeViewHolder{
        ImageView item_icon;
        TextView item_name;
        TextView item_time;
        TextView item_type;
    }
}
