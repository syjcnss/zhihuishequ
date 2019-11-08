package com.ovu.lido.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ovu.lido.R;

public class ThirdServiceRvAdapter extends RecyclerView.Adapter<ThirdServiceRvAdapter.ThirdServiceRvViewHolder> {
    private Context mContext;
    private Integer[] resIds;

    public ThirdServiceRvAdapter(Context mContext, Integer[] resIds) {
        this.mContext = mContext;
        this.resIds = resIds;
    }

    @NonNull
    @Override
    public ThirdServiceRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.third_service_rv_item, parent, false);
        return new ThirdServiceRvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThirdServiceRvViewHolder holder, int position) {
        holder.image_icon.setImageResource(resIds[position]);
        holder.image_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resIds.length;
    }

    public class ThirdServiceRvViewHolder extends RecyclerView.ViewHolder {
        ImageView image_icon;

        public ThirdServiceRvViewHolder(View itemView) {
            super(itemView);
            image_icon = itemView.findViewById(R.id.image_icon);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
