package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.PayHistoryInfo;

import java.util.List;

public class HistoryLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<PayHistoryInfo.DataBean> payHistoryInfos;

    public HistoryLvAdapter(Context mContext, List<PayHistoryInfo.DataBean> stringList) {
        this.mContext = mContext;
        this.payHistoryInfos = stringList;
    }

    @Override
    public int getCount() {
        return payHistoryInfos == null ? 0 : payHistoryInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return payHistoryInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.history_list_item,parent,false);
            holder = new ViewHolder();
            holder.title_tv = view.findViewById(R.id.title_tv);
            holder.time_tv = view.findViewById(R.id.time_tv);
            holder.money_tv = view.findViewById(R.id.money_tv);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        PayHistoryInfo.DataBean dataBean = payHistoryInfos.get(position);
        holder.title_tv.setText(dataBean.getItem_name());
        holder.time_tv.setText(dataBean.getCreate_date());
        holder.money_tv.setText(String.valueOf(dataBean.getAmount()));
        return view;
    }

    class ViewHolder {
        TextView title_tv;
        TextView time_tv;
        TextView money_tv;
    }
}
