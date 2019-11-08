package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.WorkListInfo;

import java.util.List;

public class WorkingScheduleLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<WorkListInfo.DataBean> stringList;

    public WorkingScheduleLvAdapter(Context mContext, List<WorkListInfo.DataBean> stringList) {
        this.mContext = mContext;
        this.stringList = stringList;
    }

    @Override
    public int getCount() {
        return stringList == null ? 0 : stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.working_schedule_lv_item,parent,false);
            holder.name_tv = view.findViewById(R.id.name_tv);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name_tv.setText(stringList.get(position).getWork_title());
        return view;
    }

    class ViewHolder {
        TextView name_tv;
    }
}
