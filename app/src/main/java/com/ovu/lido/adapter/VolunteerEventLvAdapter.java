package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.bean.VolunteerEventInfo;

import java.util.List;

public class VolunteerEventLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<VolunteerEventInfo.InfoListBean> volunteerEventInfos;

    public VolunteerEventLvAdapter(Context mContext, List<VolunteerEventInfo.InfoListBean> volunteerEventInfos) {
        this.mContext = mContext;
        this.volunteerEventInfos = volunteerEventInfos;
    }

    @Override
    public int getCount() {
        return volunteerEventInfos == null ? 0 : volunteerEventInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return volunteerEventInfos.get(position);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.volunteer_event_lv_item,parent,false);
            holder = new ViewHolder();
            holder.event_title_tv = view.findViewById(R.id.event_title_tv);
            holder.event_time_tv = view.findViewById(R.id.event_time_tv);
            holder.event_icon_iv = view.findViewById(R.id.event_icon_iv);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        VolunteerEventInfo.InfoListBean bean = volunteerEventInfos.get(position);
        holder.event_title_tv.setText(bean.getActivity_content());
        holder.event_time_tv.setText(bean.getCreate_time());
        String activity_url = bean.getActivity_url();
        String[] urls = activity_url.split(",");
        Glide.with(mContext)
                .load(urls[0])
                .apply(new RequestOptions()
                        .error(R.drawable.ic_error)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_error))
                .into(holder.event_icon_iv );
        return view;
    }

    class ViewHolder {
        TextView event_title_tv;
        TextView event_time_tv;
        ImageView event_icon_iv;
    }
}
