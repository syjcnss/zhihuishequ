package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.ClauseInfo;

import java.util.List;

public class ClauseLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<ClauseInfo.DataBean.ListDataBean> dataBeans;

    public ClauseLvAdapter(Context mContext, List<ClauseInfo.DataBean.ListDataBean> dataBeans) {
        this.mContext = mContext;
        this.dataBeans = dataBeans;
    }

    @Override
    public int getCount() {
        return dataBeans == null ? 0 : dataBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return dataBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ClauseLvViewHolder holder;
        if (view == null){
            holder = new ClauseLvViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.clause_lv_item_layout,parent,false);
            holder.clause_name_tv = view.findViewById(R.id.clause_name_tv);
            view.setTag(holder);
        }else {
            holder = (ClauseLvViewHolder) view.getTag();
        }
        holder.clause_name_tv.setText(dataBeans.get(position).getAgreementName());
        return view;
    }

    class ClauseLvViewHolder{
        TextView clause_name_tv;
    }
}
