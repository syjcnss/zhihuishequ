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
import com.ovu.lido.bean.ConveniencePhoneInfo;

import java.util.List;

public class PhoneLvAdapter extends BaseAdapter {
    private Context mContext;
    private List<ConveniencePhoneInfo.DataBean> phoneInfos;

    public OnPhoneBtnClickListener clickListener;

    public void setOnClickListener(OnPhoneBtnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public PhoneLvAdapter(Context mContext, List<ConveniencePhoneInfo.DataBean> phoneInfos) {
        this.mContext = mContext;
        this.phoneInfos = phoneInfos;
    }

    @Override
    public int getCount() {
        return phoneInfos == null ? 0 : phoneInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return phoneInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.phone_lv_item, parent, false);
            holder = new ViewHolder();
            holder.icon_iv = view.findViewById(R.id.icon_iv);
            holder.name_tv = view.findViewById(R.id.name_tv);
            holder.phone_num_tv = view.findViewById(R.id.phone_num_tv);
            holder.phone_icon = view.findViewById(R.id.phone_icon);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Glide.with(mContext)
                .load(phoneInfos.get(position).getImg_path())
                .into(holder.icon_iv);
        holder.phone_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.click(v,position);
            }
        });
        holder.name_tv.setText(phoneInfos.get(position).getTitle());
        holder.phone_num_tv.setText(phoneInfos.get(position).getPhone());
        return view;
    }

    class ViewHolder {
        ImageView icon_iv;
        TextView name_tv;
        TextView phone_num_tv;
        ImageView phone_icon;
    }

    public interface OnPhoneBtnClickListener{
        void click(View view, int position);
    }
}
