package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.DecorationHistoryInfo;

import java.util.List;

public class DecorationHistoryLvAdater extends BaseAdapter {
    private Context mContext;
    private List<DecorationHistoryInfo.DataBean> historyInfos;

    public DecorationHistoryLvAdater(Context mContext, List<DecorationHistoryInfo.DataBean> historyInfos) {
        this.mContext = mContext;
        this.historyInfos = historyInfos;
    }

    @Override
    public int getCount() {
        return historyInfos == null ? 0 : historyInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return historyInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        DecorationHistoryLvViewHolder holder;
        if (view == null){
            holder = new DecorationHistoryLvViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.decoration_history_lv_item,parent,false);
            holder.title_tv = view.findViewById(R.id.title_tv);
            holder.time_tv = view.findViewById(R.id.time_tv);
            holder.status_tv = view.findViewById(R.id.status_tv);
            view.setTag(holder);
        }else {
            holder = (DecorationHistoryLvViewHolder) view.getTag();
        }
        DecorationHistoryInfo.DataBean bean = historyInfos.get(position);
        holder.title_tv.setText(bean.getDecorationName());
        holder.time_tv.setText(bean.getCreateTime());

        int applyStatus = bean.getApplyStatus();
        //1.审核通过  2.未通过  3.审核中
        switch (applyStatus){
            case 1:
                holder.status_tv.setText("审核通过");
                holder.status_tv.setTextColor(mContext.getResources().getColor(R.color.bg_blue));
                break;
            case 2:
                holder.status_tv.setText("未通过");
                holder.status_tv.setTextColor(mContext.getResources().getColor(R.color.bg_red));
                break;
            case 3:
                holder.status_tv.setText("待审核");
                holder.status_tv.setTextColor(mContext.getResources().getColor(R.color.text_green));
                break;

        }

        return view;
    }

    class DecorationHistoryLvViewHolder{
        TextView title_tv;
        TextView time_tv;
        TextView status_tv;

    }
}
