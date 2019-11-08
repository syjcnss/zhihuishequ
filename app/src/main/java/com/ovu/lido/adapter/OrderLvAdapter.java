package com.ovu.lido.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.adapter.holder.ViewHolder;
import com.ovu.lido.bean.OrderInfo;
import com.ovu.lido.util.ViewHelper;

import java.util.List;

public class OrderLvAdapter extends CommonAdapter<OrderInfo.DataBeanX.DataBean> {
    private Context mContext;
    public OrderLvAdapter(Context context, int layoutId, List<OrderInfo.DataBeanX.DataBean> datas) {
        super(context, layoutId, datas);
        mContext = context;
    }

    @Override
    public void convert(ViewHolder holder, OrderInfo.DataBeanX.DataBean orderInfo) {
        ImageView thumbnail_iv = holder.getView(R.id.thumbnail_iv);
        Glide.with(mContext).load(orderInfo.getThumbnail()).apply(new RequestOptions().error(R.drawable.image_error).placeholder(R.drawable.image_error)).into(thumbnail_iv);
        holder.setText(R.id.product_name_tv, orderInfo.getProduct_name());
        holder.setText(R.id.product_num_tv, "共" + orderInfo.getProduct_num() + "件商品");
        holder.setText(R.id.amount_tv, ViewHelper.getDisplayPrice(orderInfo.getAmount()));
        TextView status_tv = holder.getView(R.id.status_tv);
        int status = orderInfo.getStatus();//0:待付款 1:待收货 2:已完成 3:已失效
        String statusStr = "";
        int color = mContext.getResources().getColor(R.color.color_ff666666);
        switch (status){
            case 0:
                statusStr = "待付款";
                color = mContext.getResources().getColor(R.color.red);
                break;
            case 1:
                statusStr = "待收货";
                break;
            case 2:
                statusStr = "已完成";
                break;
            case 3:
                statusStr = "已失效";
                break;
        }
        status_tv.setText(statusStr);
        status_tv.setTextColor(color);
    }
}
