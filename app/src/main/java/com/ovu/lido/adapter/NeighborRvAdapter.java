package com.ovu.lido.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ovu.lido.R;
import com.ovu.lido.bean.NeiBean;
import com.ovu.lido.ui.ShowBigPicActivity;
import com.ovu.lido.util.GlideCircleTransform;
import com.ovu.lido.util.ViewHelper;

import java.util.ArrayList;
import java.util.List;

public class NeighborRvAdapter extends RecyclerView.Adapter<NeighborRvAdapter.NeighborRvViewHolder> {
    private Context mContext;
    private List<NeiBean.InfoListBean> infoListBeans;

    public NeighborRvAdapter(Context mContext, List<NeiBean.InfoListBean> infoListBeans) {
        this.mContext = mContext;
        this.infoListBeans = infoListBeans;
    }

    @NonNull
    @Override
    public NeighborRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.neighbor_rv_item, parent, false);
        return new NeighborRvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NeighborRvViewHolder holder, int position) {
        NeiBean.InfoListBean info = infoListBeans.get(position);
        holder.typename_tv.setText(TextUtils.equals(info.getInfo_typename(),"2") ? "二手市场" : "议事大厅");//类型 （2：二手市场 4：议事大厅）
        holder.title_tv.setText(info.getTitle());//标题
        holder.content_tv.setText(info.getContent());//内容
        //头像
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_icon)
                .error(R.drawable.default_icon)
                .transform(new GlideCircleTransform(mContext));
        Glide.with(mContext)
                .load(info.getIcon_url())
                .apply(options)
                .into(holder.icon_iv);
        holder.icon_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChildClickListener.onIconClick(holder.getAdapterPosition());
            }
        });

        holder.nick_name_tv.setText(info.getNick_name());//昵称
        holder.create_time_tv.setText(ViewHelper.getDisplayData(info.getCreate_time()));//时间
        holder.response_count_tv.setText(info.getResponse_count());//评论
        holder.dianzan_icon.setSelected(TextUtils.equals(info.getIs_agree(),"1"));
        holder.agree_count_tv.setText(info.getAgree_count());//点赞

        holder.dianzan_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChildClickListener.onPraiseClick(holder.getAdapterPosition());
            }
        });
        holder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChildClickListener.onItemClick(v);
            }
        });



        final ArrayList<String> imgUrls = new ArrayList<>();
        if (!TextUtils.isEmpty(info.getImgs())) {
            imgUrls.clear();
            final String[] imgs = info.getImgs().split(",");
            for (int i = 0; i < imgs.length; i++) {
                imgUrls.add(imgs[i]);
            }
            if (imgUrls.size() == 1){
                holder.images_rv.setVisibility(View.GONE);
                holder.image_iv.setVisibility(View.VISIBLE);
                Glide.with(mContext)
                        .load(info.getImgs())
                        .apply(new RequestOptions().placeholder(R.drawable.image_error).error(R.drawable.image_error))
                        .into(holder.image_iv);
                holder.image_iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ShowBigPicActivity.class);
                        intent.putStringArrayListExtra("imageList", imgUrls);
                        intent.putExtra("position", holder.getAdapterPosition() + "");
                        mContext.startActivity(intent);
                    }
                });
            }else {
                holder.images_rv.setVisibility(View.VISIBLE);
                holder.image_iv.setVisibility(View.GONE);
                ImagesRvAdapter adapter = new ImagesRvAdapter(mContext, imgUrls);
                GridLayoutManager manager = new GridLayoutManager(mContext, 3);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                holder.images_rv.setLayoutManager(manager);
                holder.images_rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }else {
            holder.images_rv.setVisibility(View.GONE);
            holder.image_iv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return infoListBeans == null ? 0 : infoListBeans.size();
    }

    public class NeighborRvViewHolder extends RecyclerView.ViewHolder{
        TextView typename_tv;
        TextView title_tv;
        TextView content_tv;
        ImageView icon_iv;
        TextView nick_name_tv;
        TextView create_time_tv;
        TextView response_count_tv;
        TextView agree_count_tv;
        RelativeLayout item_layout;
        RecyclerView images_rv;
        ImageView image_iv;
        LinearLayout dianzan_ll;
        ImageView dianzan_icon;
        public NeighborRvViewHolder(View itemView) {
            super(itemView);
            typename_tv = itemView.findViewById(R.id.typename_tv);
            title_tv = itemView.findViewById(R.id.title_tv);
            content_tv = itemView.findViewById(R.id.content_tv);
            icon_iv = itemView.findViewById(R.id.icon_iv);
            nick_name_tv = itemView.findViewById(R.id.nick_name_tv);
            create_time_tv= itemView.findViewById(R.id.create_time_tv);
            response_count_tv = itemView.findViewById(R.id.response_count_tv);
            agree_count_tv = itemView.findViewById(R.id.agree_count_tv);
            item_layout = itemView.findViewById(R.id.item_layout);
            images_rv = itemView.findViewById(R.id.images_rv);
            image_iv = itemView.findViewById(R.id.image_iv);
            dianzan_ll = itemView.findViewById(R.id.dianzan_ll);
            dianzan_icon = itemView.findViewById(R.id.dianzan_icon);
        }
    }

    public interface OnChildClickListener{
        void onPraiseClick(int position);
        void onItemClick(View view);
        void onIconClick(int position);
    }

    private OnChildClickListener onChildClickListener;

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }
}
