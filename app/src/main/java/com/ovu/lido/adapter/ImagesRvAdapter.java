package com.ovu.lido.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.bean.NeiBean;
import com.ovu.lido.ui.ShowBigPicActivity;

import java.util.ArrayList;
import java.util.List;

public class ImagesRvAdapter extends RecyclerView.Adapter<ImagesRvAdapter.ImagesRvViewHolder> {
    private Context mContext;
    private ArrayList<String> imgs;

    public ImagesRvAdapter(Context mContext, ArrayList<String> imgs) {
        this.mContext = mContext;
        this.imgs = imgs;
    }

    @NonNull
    @Override
    public ImagesRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.images_rv_item, parent, false);
        return new ImagesRvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImagesRvViewHolder holder, int position) {

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.image_error)
                .error(R.drawable.image_error);
        Glide.with(mContext).load(imgs.get(position))
                .apply(options)
                .into(holder.image_iv);
        holder.image_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowBigPicActivity.class);
                intent.putStringArrayListExtra("imageList", imgs);
                intent.putExtra("position", holder.getAdapterPosition() + "");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imgs == null ? 0 : imgs.size();
    }

    public class ImagesRvViewHolder extends RecyclerView.ViewHolder{
        ImageView image_iv;
        public ImagesRvViewHolder(View itemView) {
            super(itemView);
            image_iv = itemView.findViewById(R.id.image_iv);
        }
    }
}
