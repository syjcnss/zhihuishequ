package com.ovu.lido.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ovu.lido.R;

public class VolunteerTeamRvViewHolder extends RecyclerView.ViewHolder {
    public ImageView pic_iv;
    public TextView service_member_name_tv;
    public VolunteerTeamRvViewHolder(View itemView) {
        super(itemView);
        pic_iv = itemView.findViewById(R.id.pic_iv);
        service_member_name_tv = itemView.findViewById(R.id.service_member_name_tv);
    }
}