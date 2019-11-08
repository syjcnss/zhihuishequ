package com.ovu.lido.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4 0004.
 * <p>
 * 通用Adapter
 */

public abstract class CommonAdapter<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected  final int mItemLayoutId;


    public CommonAdapter(Context context, List<T> data, int itemLayoutId) {
        mInflater= LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = data;
        this.mItemLayoutId=itemLayoutId;
    }

    public void setDatas(List<T> mData){
        this.mDatas=mData;
        notifyDataSetChanged();
    }


    public int getCount() {

        return mDatas.size();
    }

    public T getItem(int position) {

        return mDatas.get(position);
    }

    public long getItemId(int position) {


        return position;
    }


    public View getView(int position, View conterView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, conterView, parent);
        convert(viewHolder, getItem(position),position);
        return viewHolder.getConvertView();

    }

    protected abstract void convert(ViewHolder viewHolder, T item, int position);

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {

        return ViewHolder.get(mContext,convertView,parent,mItemLayoutId,position);

    }



}
