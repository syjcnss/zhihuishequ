package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.bean.LeaveCommentsInfo;
import com.ovu.lido.widgets.CircleImageView;

import java.util.List;

public class LeaveCommentsLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<LeaveCommentsInfo.DataBean.AlllistBean> leaveCommentsInfos;

    public LeaveCommentsLvAdapter(Context mContext, List<LeaveCommentsInfo.DataBean.AlllistBean> leaveCommentsInfos) {
        this.mContext = mContext;
        this.leaveCommentsInfos = leaveCommentsInfos;
    }

    @Override
    public int getCount() {
        return leaveCommentsInfos == null ? 0 : leaveCommentsInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return leaveCommentsInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LeaveCommentsLvViewHolder holder = null;
        View view = convertView;
        if (view == null){
            holder = new LeaveCommentsLvViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.leave_comments_lv_item,parent,false);
            holder.message_to_head_image_civ = view.findViewById(R.id.message_to_head_image_civ);
            holder.message_to_tv = view.findViewById(R.id.message_to_tv);
            holder.message_from_tv = view.findViewById(R.id.message_from_tv);
            holder.message_from_head_image_civ = view.findViewById(R.id.message_from_head_image_civ);
            view.setTag(holder);
        }else{
            holder = (LeaveCommentsLvViewHolder) view.getTag();
        }
        LeaveCommentsInfo.DataBean.AlllistBean info = leaveCommentsInfos.get(position);
        int states = info.getStates();
        switch (states){
            case 1://业主留言
                holder.message_to_head_image_civ.setVisibility(View.GONE);
                holder.message_to_tv.setVisibility(View.GONE);
                holder.message_from_head_image_civ.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(info.getIconUrl())
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.default_head)
                                .error(R.drawable.default_head))
                        .into(holder.message_from_head_image_civ);
                holder.message_from_tv.setVisibility(View.VISIBLE);
                holder.message_from_tv.setText(info.getContent());
                break;
            case 2://管家回复
                holder.message_to_head_image_civ.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(info.getUrl())
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.default_head)
                                .error(R.drawable.default_head))
                        .into(holder.message_to_head_image_civ);
                holder.message_to_tv.setVisibility(View.VISIBLE);
                holder.message_to_tv.setText(info.getContent());
                holder.message_from_head_image_civ.setVisibility(View.GONE);
                holder.message_from_tv.setVisibility(View.GONE);
                break;
        }
        return view;
    }

    class LeaveCommentsLvViewHolder{
        CircleImageView message_to_head_image_civ;
        TextView message_to_tv;
        TextView message_from_tv;
        CircleImageView message_from_head_image_civ;
    }
}
