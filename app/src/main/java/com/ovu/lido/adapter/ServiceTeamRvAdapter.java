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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.bean.ServiceTeamInfo;

import java.util.List;

public class ServiceTeamRvAdapter extends RecyclerView.Adapter<ServiceTeamRvAdapter.ServiceTeamRvAdapterViewHolder> {
    private List<ServiceTeamInfo.InfoListBean> list;
    private Context mContext;

    public ServiceTeamRvAdapter(Context mContext, List<ServiceTeamInfo.InfoListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ServiceTeamRvAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sevice_team_rv_item, parent, false);
        return new ServiceTeamRvAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceTeamRvAdapterViewHolder holder, final int position) {
        holder.service_member_name_tv.setText(list.get(position).getName());
        Glide.with(mContext)
                .load(list.get(position).getUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.activity_image_error)
                        .error(R.drawable.activity_image_error)
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(holder.pic_iv);
        holder.content_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ServiceTeamRvAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView service_member_name_tv;
        ImageView pic_iv;
        LinearLayout content_ll;
        public ServiceTeamRvAdapterViewHolder(View itemView) {
            super(itemView);
            service_member_name_tv= itemView.findViewById(R.id.service_member_name_tv);
            pic_iv = itemView.findViewById(R.id.pic_iv);
            content_ll = itemView.findViewById(R.id.content_ll);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

}
