package com.ovu.lido.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.ui.ShowBigPicActivity;
import com.ovu.lido.util.Logger;

import java.util.ArrayList;

public class ImgGridAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mList;


    public ImgGridAdapter(Context context) {
        this.mContext = context;
    }

    public void setList(ArrayList<String> list) {
        this.mList = list;
        Logger.i("Neigh", "图片列表大小:" + mList.size());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
//        if (mList != null && mList.size() > 4) {
//            return 4;
//        }
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_img_item, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.image_error)
                .error(R.drawable.image_error);
        Glide.with(mContext).load(mList.get(position))
                .apply(options)
                .into(viewHolder.mImageView);
        viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShowBigPicActivity.class);
                intent.putStringArrayListExtra("imageList", mList);
                intent.putExtra("position", position + "");
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }


    class ViewHolder {
        ImageView mImageView;

        public ViewHolder(View view) {
            mImageView = view.findViewById(R.id.iv_content_img);
            view.setTag(this);
        }
    }
}
