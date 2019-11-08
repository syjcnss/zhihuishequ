package com.ovu.lido.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.MyWorkOrderInfo;

import java.util.List;

public class MyWorkOrderLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyWorkOrderInfo.DataBeanX.DataBean> myWorkOrderInfos;

    public MyWorkOrderLvAdapter(Context mContext, List<MyWorkOrderInfo.DataBeanX.DataBean> myWorkOrderInfos) {
        this.mContext = mContext;
        this.myWorkOrderInfos = myWorkOrderInfos;
    }

    @Override
    public int getCount() {
        return myWorkOrderInfos == null ? 0 : myWorkOrderInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return myWorkOrderInfos.get(position);
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
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.my_work_order_lv_item,parent,false);
            holder.title_tv = view.findViewById(R.id.title_tv);
            holder.time_tv = view.findViewById(R.id.time_tv);
            holder.content_tv = view.findViewById(R.id.content_tv);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        MyWorkOrderInfo.DataBeanX.DataBean info = myWorkOrderInfos.get(position);
        holder.title_tv.setText(info.getWorktypeName());
        holder.time_tv.setText(TextUtils.isEmpty(info.getFinishTime()) ? info.getReportTime() : info.getFinishTime());
        holder.content_tv.setText(info.getDescription());
        return view;
    }

    class ViewHolder {
        TextView title_tv;
        TextView time_tv;
        TextView content_tv;
    }
}
