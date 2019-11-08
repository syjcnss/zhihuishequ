package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.AnnouncementListInfo;

import java.util.List;

public class HeadLinesLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<AnnouncementListInfo.InfoListBean> headLinesInfos;

    public HeadLinesLvAdapter(Context mContext, List<AnnouncementListInfo.InfoListBean> headLinesInfos) {
        this.mContext = mContext;
        this.headLinesInfos = headLinesInfos;
    }

    @Override
    public int getCount() {
        return headLinesInfos == null ? 0 : headLinesInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return headLinesInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        HeadLinesLvViewHolder holder = null;
        if (view == null){
            holder = new HeadLinesLvViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.head_lines_lv_item,parent,false);
            holder.title_tv = view.findViewById(R.id.title_tv);
            holder.time_tv = view.findViewById(R.id.time_tv);
            holder.content_tv = view.findViewById(R.id.content_tv);
            view.setTag(holder);
        }else {
            holder = (HeadLinesLvViewHolder) view.getTag();
        }
        AnnouncementListInfo.InfoListBean info = headLinesInfos.get(position);
        holder.title_tv.setText(info.getTitle());
        holder.time_tv.setText(info.getCreate_time());
        holder.content_tv.setText(info.getContent1());
        return view;
    }

    class HeadLinesLvViewHolder{
        TextView title_tv;
        TextView time_tv;
        TextView content_tv;
    }
}
