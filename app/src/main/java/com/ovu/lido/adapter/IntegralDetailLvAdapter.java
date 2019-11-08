package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.IntegralDetailInfo;

import java.util.List;

public class IntegralDetailLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<IntegralDetailInfo.DataBean.ListBean> integralInfos;

    public IntegralDetailLvAdapter(Context mContext, List<IntegralDetailInfo.DataBean.ListBean> integralInfos) {
        this.mContext = mContext;
        this.integralInfos = integralInfos;
    }

    @Override
    public int getCount() {
        return integralInfos == null ? 0 : integralInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return integralInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        IntegralLvViewHolder holder;
        if (view == null){
            holder = new IntegralLvViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.integral_lv_item,parent,false);
            holder.title_tv = view.findViewById(R.id.title_tv);
            holder.time_tv = view.findViewById(R.id.time_tv);
            holder.fractional_number_tv = view.findViewById(R.id.fractional_number_tv);
            view.setTag(holder);
        }else {
            holder = (IntegralLvViewHolder) view.getTag();
        }

        holder.title_tv.setText(integralInfos.get(position).getIncident());
        holder.time_tv.setText(integralInfos.get(position).getPoint_change_time());

        String status = "";
        int change_status = integralInfos.get(position).getChange_status();
        switch (change_status){
            case 0:
                status = "+";
                break;
            case 1:
                status = "-";
                break;
        }
        int point_change = integralInfos.get(position).getPoint_change();
        holder.fractional_number_tv.setText(status + point_change);

        return view;
    }

    class IntegralLvViewHolder{
        TextView title_tv;
        TextView time_tv;
        TextView fractional_number_tv;
    }
}
