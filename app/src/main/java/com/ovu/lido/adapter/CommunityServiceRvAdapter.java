package com.ovu.lido.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.bean.CommunityServiceProductInfo;
import com.ovu.lido.ui.ProductDetailActivity;
import com.ovu.lido.util.ToastUtil;

import java.util.List;

public class CommunityServiceRvAdapter extends RecyclerView.Adapter<CommunityServiceRvAdapter.CommunityServiceRvViewHolder> {
    private Context mContext;
    private List<CommunityServiceProductInfo.DataBeanX.DataBean> productInfoList;

    public CommunityServiceRvAdapter(Context mContext, List<CommunityServiceProductInfo.DataBeanX.DataBean> productInfoList) {
        this.mContext = mContext;
        this.productInfoList = productInfoList;
    }

    @NonNull
    @Override
    public CommunityServiceRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.community_service_rv_item, parent, false);
        return new CommunityServiceRvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommunityServiceRvViewHolder holder, int position) {
        CommunityServiceProductInfo.DataBeanX.DataBean info = productInfoList.get(position);
        Glide.with(mContext).load(info.getThumbnail()).apply(new RequestOptions().error(R.drawable.image_error).placeholder(R.drawable.image_error)).into(holder.thumbnail_iv);
        holder.name_tv.setText(info.getName());
        holder.price_tv.setText(new StringBuffer().append("￥").append(info.getPrice()));
        holder.typename_tv.setText(info.getOperator_type() == 0 ? "自营" : "第三方");
        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productInfoList == null ? 0 : productInfoList.size();
    }

    class CommunityServiceRvViewHolder extends RecyclerView.ViewHolder{
        CardView item_layout;
        ImageView thumbnail_iv;
        TextView name_tv;
        TextView price_tv;
        TextView typename_tv;
        public CommunityServiceRvViewHolder(View itemView) {
            super(itemView);
            item_layout = itemView.findViewById(R.id.item_layout);
            thumbnail_iv = itemView.findViewById(R.id.thumbnail_iv);
            name_tv = itemView.findViewById(R.id.name_tv);
            price_tv = itemView.findViewById(R.id.price_tv);
            typename_tv = itemView.findViewById(R.id.typename_tv);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
