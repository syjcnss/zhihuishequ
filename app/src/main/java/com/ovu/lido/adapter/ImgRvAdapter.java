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
import com.ovu.lido.ui.ShowBigPicActivity;
import com.ovu.lido.util.Logger;

import java.util.ArrayList;

public class ImgRvAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<String> mList;


    public ImgRvAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(ArrayList<String> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.lv_img_item, parent, false);
        return new ImgViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ImgViewHolde item = (ImgViewHolde) holder;
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.image_error)
                .error(R.drawable.image_error);
        Glide.with(mContext).load(mList.get(position))
                .apply(options)
                .into(item.mImageView);


        for (int i = 0; i <mList.size() ; i++) {
            Logger.i("ImageAdapter", "图片连接:" + mList.get(i));
        }

        item.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowBigPicActivity.class);
                intent.putStringArrayListExtra("imageList", mList);
                intent.putExtra("position", position + "");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    class ImgViewHolde extends RecyclerView.ViewHolder {
        ImageView mImageView;

        public ImgViewHolde(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_content_img);
        }
    }
}
