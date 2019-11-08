package com.ovu.lido.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.bean.LockInfo;

import java.util.List;

public class LockListAdapter extends BaseAdapter {
    private Context mContext;
    private List<LockInfo.DataBean> lockInfos;
    private PrepareUnlockListener mPrepareUnlockListener;

    public void setmPrepareUnlockListener(PrepareUnlockListener mPrepareUnlockListener) {
        this.mPrepareUnlockListener = mPrepareUnlockListener;
    }

    public LockListAdapter(Context mContext, List<LockInfo.DataBean> lockInfos) {
        this.mContext = mContext;
        this.lockInfos = lockInfos;
    }

    @Override
    public int getCount() {
        return lockInfos == null ? 0 : lockInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return lockInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LockLvAdapter holder;
        if (view == null){
            holder = new LockLvAdapter();
            view = LayoutInflater.from(mContext).inflate(R.layout.lock_grid_item,parent,false);
            holder.item_name = view.findViewById(R.id.item_name);
            holder.item_image = view.findViewById(R.id.item_image);
            holder.pb_lock_opening = view.findViewById(R.id.pb_lock_opening);
            view.setTag(holder);
        }else {
            holder = (LockLvAdapter) view.getTag();
        }
        holder.item_name.setText(lockInfos.get(position).getName());
        holder.item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrepareUnlockListener.unlock(v,position);
            }
        });
        if (lockInfos.get(position).isOpening()){
            holder.pb_lock_opening.setVisibility(View.VISIBLE);
            holder.item_image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.btn_clicked));
        }else {
            holder.pb_lock_opening.setVisibility(View.INVISIBLE);
            holder.item_image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.btn_not_clicked));
        }
        return view;
    }

    class LockLvAdapter{
        TextView item_name;
        ImageView item_image;
        ProgressBar pb_lock_opening;
    }

    public interface PrepareUnlockListener{
        void unlock(View view, int position);
    }
}
