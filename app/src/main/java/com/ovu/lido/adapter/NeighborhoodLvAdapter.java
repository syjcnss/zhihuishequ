package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.bean.NeiBean;
import com.ovu.lido.util.GlideCircleTransform;
import com.ovu.lido.widgets.CircleImageView;

import java.util.List;

public class NeighborhoodLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<NeiBean.InfoListBean> neighborhoodListInfos;

    public NeighborhoodLvAdapter(Context mContext, List<NeiBean.InfoListBean> stringList) {
        this.mContext = mContext;
        this.neighborhoodListInfos = stringList;
    }

    @Override
    public int getCount() {
        return neighborhoodListInfos == null ? 0 : neighborhoodListInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return neighborhoodListInfos.get(position);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.neighborhood_lv_item,parent,false);
            holder = new ViewHolder();
            holder.post_person_civ = view.findViewById(R.id.post_person_civ);
            holder.post_content = view.findViewById(R.id.post_content);
            holder.post_time = view.findViewById(R.id.post_time);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_head)
                .error(R.drawable.default_head)
                .transform(new GlideCircleTransform(mContext));
        Glide.with(mContext)
                .load(neighborhoodListInfos.get(position).getIcon_url())
                .apply(options)
                .into(holder.post_person_civ);
        holder.post_content.setText(neighborhoodListInfos.get(position).getContent());
        holder.post_time.setText(neighborhoodListInfos.get(position).getCreate_time());
        return view;
    }

    class ViewHolder {
        CircleImageView post_person_civ;
        TextView post_content;
        TextView post_time;
    }
}
