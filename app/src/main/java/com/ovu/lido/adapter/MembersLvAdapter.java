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
import com.ovu.lido.bean.MembersInfo;
import com.ovu.lido.widgets.CircleImageView;

import java.util.List;

public class MembersLvAdapter extends BaseAdapter {
    private Context mContex;
    private List<MembersInfo.DataBean> membersInfos;

    public MembersLvAdapter(Context mContex, List<MembersInfo.DataBean> membersInfos) {
        this.mContex = mContex;
        this.membersInfos = membersInfos;
    }

    public PhoneBtnClickListener phoneBtnClickListener;

    public void setPhoneBtnClickListener(PhoneBtnClickListener phoneBtnClickListener) {
        this.phoneBtnClickListener = phoneBtnClickListener;
    }

    @Override
    public int getCount() {
        return membersInfos == null ? 0 : membersInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return membersInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        MembersLvViewHolder holder;
        if (view == null){
            holder = new MembersLvViewHolder();
            view = LayoutInflater.from(mContex).inflate(R.layout.members_lv_item,parent,false);
            holder.icon_civ = view.findViewById(R.id.icon_civ);
            holder.name_tv = view.findViewById(R.id.name_tv);
            holder.identity_tv = view.findViewById(R.id.identity_tv);
            holder.phone_num_tv = view.findViewById(R.id.phone_num_tv);
            holder.phone_icon_iv = view.findViewById(R.id.phone_icon_iv);
            holder.description_tv = view.findViewById(R.id.description_tv);
            view.setTag(holder);
        }else {
            holder = (MembersLvViewHolder) view.getTag();
        }
        MembersInfo.DataBean dataBean = membersInfos.get(position);
        Glide.with(mContex).load(dataBean.getHead_img()).into(holder.icon_civ);
        holder.name_tv.setText(dataBean.getUsername());
        holder.identity_tv.setText(dataBean.getPosition());
        holder.phone_num_tv.setText(dataBean.getMobile());
        holder.phone_icon_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneBtnClickListener.onBtnClick(v,position);
            }
        });
        holder.description_tv.setText(dataBean.getInfo());

        return view;
    }

    class MembersLvViewHolder {
        CircleImageView icon_civ;
        TextView name_tv;
        TextView identity_tv;
        TextView phone_num_tv;
        ImageView phone_icon_iv;
        TextView description_tv;
    }

    public interface PhoneBtnClickListener{
        void onBtnClick (View view, int position);
    }
}
