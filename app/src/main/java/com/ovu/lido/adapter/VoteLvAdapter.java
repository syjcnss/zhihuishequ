package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.VoteInfo;

import java.util.List;

public class VoteLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<VoteInfo.VoteListBean> voteListBeans;

    public VoteLvAdapter(Context mContext, List<VoteInfo.VoteListBean> voteListBeans) {
        this.mContext = mContext;
        this.voteListBeans = voteListBeans;
    }

    @Override
    public int getCount() {
        return voteListBeans == null ? 0 : voteListBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return voteListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        VoteLvViewHolder holder = null;
        if (view == null){
            holder = new VoteLvViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.vote_lv_item_layout,parent,false);
            holder.vote_title_tv = view.findViewById(R.id.vote_title_tv);
            holder.create_time_tv = view.findViewById(R.id.create_time_tv);
            holder.tv_voted = view.findViewById(R.id.tv_voted);
            view.setTag(holder);
        }else {
            holder = (VoteLvViewHolder) view.getTag();
        }
        VoteInfo.VoteListBean listBean = voteListBeans.get(position);
        holder.vote_title_tv.setText(listBean.getVote_title());
        holder.create_time_tv.setText(listBean.getCreate_TIME());
        holder.tv_voted.setVisibility("1".equals(listBean.getVote_ever())? View.VISIBLE: View.GONE);
        return view;
    }

    class VoteLvViewHolder{
        TextView vote_title_tv;
        TextView create_time_tv;
        TextView tv_voted;
    }
}
