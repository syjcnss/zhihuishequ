package com.ovu.lido.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ovu.lido.R;

public class WorkOrderDetailRvViewHolder extends RecyclerView.ViewHolder {
    public ImageView pic_iv;
    public WorkOrderDetailRvViewHolder(View itemView) {
        super(itemView);
        pic_iv = itemView.findViewById(R.id.pic_iv);
    }
}
