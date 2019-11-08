package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.FaultTypeInfo;

import java.util.List;

public class FaultTypeLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<FaultTypeInfo.DataBeanX.DataBean> faultTypeInfos;

    public FaultTypeLvAdapter(Context mContext, List<FaultTypeInfo.DataBeanX.DataBean> faultTypeInfos) {
        this.mContext = mContext;
        this.faultTypeInfos = faultTypeInfos;
    }

    @Override
    public int getCount() {
        return faultTypeInfos == null ? 0 : faultTypeInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return faultTypeInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        FaultTypeViewHolder holder;
        if (view == null){
            holder = new FaultTypeViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.fault_type_lv_item,parent,false);
            holder.fault_name_tv = view.findViewById(R.id.fault_name_tv);
            view.setTag(holder);
        }else {
            holder = (FaultTypeViewHolder) view.getTag();
        }
        holder.fault_name_tv.setText(faultTypeInfos.get(position).getText());
        return view;
    }

    class FaultTypeViewHolder{
        TextView fault_name_tv;
    }
}
