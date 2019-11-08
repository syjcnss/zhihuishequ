package com.ovu.lido.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ovu.lido.R;

public class PicRvViewHolder extends RecyclerView.ViewHolder{
    public ImageView pic_iv;
    public LinearLayout ll_del;
    public PicRvViewHolder(View itemView) {
        super(itemView);
        pic_iv = itemView.findViewById(R.id.pic_iv);
        ll_del = itemView.findViewById(R.id.ll_del);
    }
}
