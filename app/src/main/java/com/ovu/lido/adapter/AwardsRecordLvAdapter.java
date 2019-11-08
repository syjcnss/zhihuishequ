package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ovu.lido.R;

import java.util.List;

public class AwardsRecordLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> recordInfos;

    public AwardsRecordLvAdapter(Context mContext, List<String> recordInfos) {
        this.mContext = mContext;
        this.recordInfos = recordInfos;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Object getItem(int position) {
        return recordInfos.get(position % recordInfos.size());
    }

    @Override
    public long getItemId(int position) {
        return position % recordInfos.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 这里是一个缓存的机制，不多说。
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.winning_record_item, parent,false);
            holder.content_tv = convertView.findViewById(R.id.content_tv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        String phone = recordInfos.get(position % recordInfos.size()).getPhone();
//        String q = "";
//        if (phone.length() == 11) {
//            q = phone.replace(phone.substring(3, phone.length() - 4), "****");
//        }
//        String awards_name = recordInfos.get(position % recordInfos.size()).getAwards_name();
        holder.content_tv.setText(recordInfos.get(position % recordInfos.size()));// 循环读取数据

        return convertView;
    }

    static class ViewHolder {
        public TextView content_tv;

    }
}
