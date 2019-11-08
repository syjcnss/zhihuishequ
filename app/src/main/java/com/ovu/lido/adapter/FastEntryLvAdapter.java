package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ovu.lido.R;

import java.util.List;

public class FastEntryLvAdapter extends BaseAdapter {
    private Context mContext;
    private Integer[] resIds;
    private String[] names;

    public FastEntryLvAdapter(Context mContext, Integer[] resIds, String[] names) {
        this.mContext = mContext;
        this.resIds = resIds;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names == null ? 0 : names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        FastEntryLvViewHolder holder = null;
        if (view == null){
            holder = new FastEntryLvViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.fast_entry_lv_item,parent,false);
            holder.icon_iv = view.findViewById(R.id.icon_iv);
            holder.name_tv = view.findViewById(R.id.name_tv);
            view.setTag(holder);
        }else {
            holder = (FastEntryLvViewHolder) view.getTag();
        }
        holder.name_tv.setText(names[position]);
        holder.icon_iv.setImageResource(resIds[position]);
        return view;
    }

    class FastEntryLvViewHolder{
        ImageView icon_iv;
        TextView name_tv;
    }
}
