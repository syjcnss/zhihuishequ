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
import com.luck.picture.lib.entity.LocalMedia;
import com.ovu.lido.R;
import com.ovu.lido.adapter.holder.PicRvViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.luck.picture.lib.config.PictureConfig.TYPE_CAMERA;
import static com.luck.picture.lib.config.PictureConfig.TYPE_PICTURE;

public class PicRvAdapter extends RecyclerView.Adapter<PicRvViewHolder> {
    private Context mContext;
    private List<LocalMedia> list = new ArrayList<>();
    private int selectMax;//选择图片数量
    /**
     * 图片点击监听
     */
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position, View view);
    }

    /**
     * ＋ 号点击监听
     */
    private OnAddPicClickListener onAddPicClickListener;

    public void setOnAddPicClickListener(OnAddPicClickListener onAddPicClickListener) {
        this.onAddPicClickListener = onAddPicClickListener;
    }

    public interface OnAddPicClickListener{
        void onAddPicClick();
    }


    public interface OnDeletePicClickListener {
        void onDeletePicClick(List<LocalMedia> list);
    }

    private OnDeletePicClickListener onDeletePicClickListener;

    public void setOnDeletePicClickListener(OnDeletePicClickListener onDeletePicClickListener){
        this.onDeletePicClickListener = onDeletePicClickListener;
    }

    public void setList(List<LocalMedia> list) {
        this.list = list;
    }

    public PicRvAdapter(Context mContext, int selectMax) {
        this.mContext = mContext;
        this.selectMax = selectMax;
    }

    @NonNull
    @Override
    public PicRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pic_rv_item_layout, parent, false);
        return new PicRvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PicRvViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_CAMERA) {
            holder.pic_iv.setImageResource(R.drawable.add_pictures);
            holder.ll_del.setVisibility(View.INVISIBLE);
            holder.pic_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onAddPicClickListener.onAddPicClick();
                }
            });
        }else {
            holder.ll_del.setVisibility(View.VISIBLE);
            holder.ll_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = holder.getAdapterPosition();
                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                    if (index != RecyclerView.NO_POSITION) {
                        list.remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, list.size());
                        if (null != onDeletePicClickListener){
                            onDeletePicClickListener.onDeletePicClick(list);
                        }
                    }
                }
            });
            //压缩之后的路径
            LocalMedia media = list.get(position);
            String path = "";
            if (media.isCompressed()) {
                path = media.getCompressPath();
            }
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.color.line_color)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(holder.itemView.getContext())
                    .load(path)
                    .apply(options)
                    .into(holder.pic_iv);

        }

        //itemView 的点击事件
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = holder.getAdapterPosition();
                    onItemClickListener.onItemClick(adapterPosition, v);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list.size() < selectMax){
            return list.size()+1;
        }else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    private boolean isShowAddItem(int position) {
        int size = list.size() == 0 ? 0 : list.size();
        return position == size;
    }

}
