package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ovu.lido.R;
import com.ovu.lido.bean.GridAdministratorInfo;
import com.ovu.lido.widgets.CircleImageView;

import java.util.List;

public class GridKeeperLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<GridAdministratorInfo.ListBean> listBeans;

    private OnPhoneBtnClickListener onPhoneBtnClickListener;

    public void setOnPhoneBtnClickListener(OnPhoneBtnClickListener onPhoneBtnClickListener) {
        this.onPhoneBtnClickListener = onPhoneBtnClickListener;
    }

    public interface OnPhoneBtnClickListener{
        void onClick(View view, int position);
    }

    public GridKeeperLvAdapter(Context mContext, List<GridAdministratorInfo.ListBean> listBeans) {
        this.mContext = mContext;
        this.listBeans = listBeans;
    }

    @Override
    public int getCount() {
        return listBeans == null ? 0 : listBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return listBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        HeadLinesLvViewHolder holder = null;
        if (view == null){
            holder = new HeadLinesLvViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.grid_keeper_lv_item_layout,parent,false);
            holder.gridkeeper_pic_civ = view.findViewById(R.id.gridkeeper_pic_civ);
            holder.gridkeeper_name_tv = view.findViewById(R.id.gridkeeper_name_tv);
            holder.gridkeeper_tel_tv = view.findViewById(R.id.gridkeeper_tel_tv);
            holder.phone_icon = view.findViewById(R.id.phone_icon);

            holder.gridkeeper_intro_tv = view.findViewById(R.id.gridkeeper_intro_tv);
            view.setTag(holder);
        }else {
            holder = (HeadLinesLvViewHolder) view.getTag();
        }
        GridAdministratorInfo.ListBean info = listBeans.get(position);
        Glide.with(mContext).load(info.getGridkeeper_pic()).into(holder.gridkeeper_pic_civ);
        holder.gridkeeper_name_tv.setText(info.getGridkeeper_name());
        holder.gridkeeper_tel_tv.setText(info.getGridkeeper_tel());
        holder.gridkeeper_intro_tv.setText(info.getGridkeeper_intro());
        holder.phone_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPhoneBtnClickListener.onClick(v,position);
            }
        });
        return view;
    }

    class HeadLinesLvViewHolder{
        CircleImageView gridkeeper_pic_civ;
        TextView gridkeeper_name_tv;
        TextView gridkeeper_tel_tv;
        ImageView phone_icon;
        TextView gridkeeper_intro_tv;
    }

}
