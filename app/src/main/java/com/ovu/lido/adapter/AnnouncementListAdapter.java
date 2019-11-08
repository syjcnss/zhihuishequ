package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.AnnouncementInfo;

import java.util.List;

public class AnnouncementListAdapter extends BaseAdapter {
    private Context mContext;
    private List<AnnouncementInfo.InfoListBean> infoListBeans;

    public AnnouncementListAdapter(Context mContext, List<AnnouncementInfo.InfoListBean> infoListBeans) {
        this.mContext = mContext;
        this.infoListBeans = infoListBeans;
    }

    @Override
    public int getCount() {
        return infoListBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return infoListBeans.get(position);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.message_list_item,parent,false);
            holder = new ViewHolder();
            holder.message_title = view.findViewById(R.id.message_title);
            holder.message_time = view.findViewById(R.id.message_time);
            holder.message_content = view.findViewById(R.id.message_content);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.message_title.setText(infoListBeans.get(position).getTitle());
        holder.message_time.setText(infoListBeans.get(position).getCreate_time());
        holder.message_content.setText(infoListBeans.get(position).getContent1());

        return view;
    }

    class ViewHolder {
        TextView message_title;
        TextView message_time;
        TextView message_content;
    }
}
