package com.ovu.lido.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ovu.lido.R;

public class CommunityRvAdapter extends RecyclerView.Adapter<CommunityRvAdapter.CommunityRvViewHolder> {
    private Context mContext;
    private Integer[] resIds;
    private String[] names;

    public CommunityRvAdapter(Context mContext, Integer[] resIds, String[] names) {
        this.mContext = mContext;
        this.resIds = resIds;
        this.names = names;
    }

    @NonNull
    @Override
    public CommunityRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.community_rv_item, parent, false);
        return new CommunityRvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityRvViewHolder holder, int position) {
        holder.image_icon.setImageResource(resIds[position]);
        holder.name_tv.setText(names[position]);
        holder.item_ll.setOnClickListener(new View.OnClickListener() {
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

    public class CommunityRvViewHolder extends RecyclerView.ViewHolder{
        LinearLayout item_ll;
        ImageView image_icon;
        TextView name_tv;
        public CommunityRvViewHolder(View itemView) {
            super(itemView);
            item_ll = itemView.findViewById(R.id.item_ll);
            image_icon = itemView.findViewById(R.id.image_icon);
            name_tv = itemView.findViewById(R.id.name_tv);
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
