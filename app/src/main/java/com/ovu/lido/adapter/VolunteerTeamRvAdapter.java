package com.ovu.lido.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.adapter.holder.VolunteerTeamRvViewHolder;
import com.ovu.lido.bean.VolunteerTeamInfo;

import java.util.List;

public class VolunteerTeamRvAdapter extends RecyclerView.Adapter<VolunteerTeamRvViewHolder> {
    private Context mContext;
    private List<VolunteerTeamInfo.InfosBean> volunteerTeamInfos;

    public VolunteerTeamRvAdapter(Context mContext, List<VolunteerTeamInfo.InfosBean> volunteerTeamInfos) {
        this.mContext = mContext;
        this.volunteerTeamInfos = volunteerTeamInfos;
    }

    @NonNull
    @Override
    public VolunteerTeamRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sevice_team_rv_item, parent, false);
        return new VolunteerTeamRvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VolunteerTeamRvViewHolder holder, int position) {
        VolunteerTeamInfo.InfosBean bean = volunteerTeamInfos.get(position);
        Glide.with(mContext)
                .load(bean.getUrl())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_error)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_error))
                .into(holder.pic_iv);
        holder.service_member_name_tv.setText(bean.getName());
    }

    @Override
    public int getItemCount() {
        return volunteerTeamInfos == null ? 0 : volunteerTeamInfos.size();
    }
}
