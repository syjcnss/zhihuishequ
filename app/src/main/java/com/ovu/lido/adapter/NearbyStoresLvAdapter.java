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
import com.ovu.lido.bean.NearbyStoresInfo;
import com.ovu.lido.util.ViewHelper;

import java.util.List;

public class NearbyStoresLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<NearbyStoresInfo.DataBean> storesInfos;

    public NearbyStoresLvAdapter(Context mContext, List<NearbyStoresInfo.DataBean> storesInfos) {
        this.mContext = mContext;
        this.storesInfos = storesInfos;
    }

    @Override
    public int getCount() {
        return storesInfos == null ? 0 : storesInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return storesInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        NearbyStoresViewHolder holder;
        if (view == null){
            holder = new NearbyStoresViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.nearby_stores_lv_item,parent,false);
            holder.name_tv = view.findViewById(R.id.name_tv);
            holder.address_tv = view.findViewById(R.id.address_tv);
            holder.tel_tv = view.findViewById(R.id.tel_tv);
            holder.advertising_iv = view.findViewById(R.id.advertising_iv);
            view.setTag(holder);
        }else {
            holder = (NearbyStoresViewHolder) view.getTag();
        }
        final NearbyStoresInfo.DataBean dataBean = storesInfos.get(position);
        holder.name_tv.setText(dataBean.getTitle());
        holder.address_tv.setText(dataBean.getPure_address());
        holder.tel_tv.setText(dataBean.getTel());
        holder.tel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewHelper.toDialView(mContext, dataBean.getTel());
            }
        });
        Glide.with(mContext)
                .load(dataBean.getImg())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.activity_image_error)
                        .error(R.drawable.activity_image_error)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.advertising_iv);
        return view;
    }

    class NearbyStoresViewHolder{
        TextView name_tv;
        TextView address_tv;
        TextView tel_tv;
        ImageView advertising_iv;
    }
}
