package com.ovu.lido.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.adapter.holder.WorkOrderDetailRvViewHolder;
import com.ovu.lido.ui.ShowBigPicActivity;

import java.util.ArrayList;

public class WorkOrderDetailRvAdapter extends RecyclerView.Adapter<WorkOrderDetailRvViewHolder> {
    private Context mContext;
    private String[] resIds;
    private ArrayList<String> imageList = new ArrayList<>();

    public WorkOrderDetailRvAdapter(Context mContext, String[] resIds) {
        this.mContext = mContext;
        this.resIds = resIds;
    }

    @NonNull
    @Override
    public WorkOrderDetailRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.work_order_detail_rv_item, parent, false);
        return new WorkOrderDetailRvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkOrderDetailRvViewHolder holder, final int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_error)
                .error(R.drawable.ic_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(mContext)
                .load(resIds[position])
                .apply(options)
                .into(holder.pic_iv);
        holder.pic_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowBigPicActivity.class);
//                String string = resIds.toString();
                for (int i = 0; i < resIds.length; i++) {
                    imageList.add(resIds[i]);
                }

                intent.putStringArrayListExtra("imageList", imageList);
                intent.putExtra("position", position + "");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return resIds == null ? 0 : resIds.length;
    }
}
