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
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.bean.HomePageInfo;

import java.util.List;

public class SpecialProductRvAdapter extends RecyclerView.Adapter<SpecialProductRvAdapter.SpecialProductRvViewHolder> {
    private Context mContext;
    private List<HomePageInfo.DataBean.SpecialProductsDataBean> specialProductsDataBeans;

    public SpecialProductRvAdapter(Context mContext, List<HomePageInfo.DataBean.SpecialProductsDataBean> specialProductsDataBeans) {
        this.mContext = mContext;
        this.specialProductsDataBeans = specialProductsDataBeans;
    }

    @NonNull
    @Override
    public SpecialProductRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.special_products_rv_item, parent, false);
        return new SpecialProductRvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialProductRvViewHolder holder, int position) {
        HomePageInfo.DataBean.SpecialProductsDataBean bean = specialProductsDataBeans.get(position);
        holder.products_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(v);
            }
        });
        Glide.with(mContext)
                .load(bean.getThumbnail())
                .apply(new RequestOptions().placeholder(R.drawable.image_error).error(R.drawable.image_error).dontAnimate().centerCrop())
                .into(holder.products_iv);
        holder.products_name_tv.setText(bean.getName());
        holder.products_price_tv.setText(String.valueOf(bean.getPrice()) + "元");

    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (specialProductsDataBeans == null ){
            itemCount = 0;
        }else if (specialProductsDataBeans.size() <= 3){
            itemCount = specialProductsDataBeans.size();
        }else {
            itemCount = 3;
        }
        return itemCount;
    }

    class SpecialProductRvViewHolder extends RecyclerView.ViewHolder{
        LinearLayout products_layout;
        ImageView products_iv;
        TextView products_name_tv;
        TextView products_price_tv;
        public SpecialProductRvViewHolder(View itemView) {
            super(itemView);
            products_layout = itemView.findViewById(R.id.products_layout);
            products_iv = itemView.findViewById(R.id.products_iv);
            products_name_tv = itemView.findViewById(R.id.products_name_tv);
            products_price_tv = itemView.findViewById(R.id.products_price_tv);
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
