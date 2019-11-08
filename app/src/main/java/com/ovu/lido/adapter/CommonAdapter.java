package com.ovu.lido.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ovu.lido.adapter.holder.ViewHolder;

import java.util.List;

/**
 * 通用Adapter
 * 
 * @author sqq
 *
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

	protected List<T> mDatas;
	private Context mContext;
	private int mLayoutId;

	public CommonAdapter(Context context, int layoutId, List<T> datas) {
		mContext = context;
		mLayoutId = layoutId;
		mDatas = datas;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public T getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = ViewHolder.get(mContext, position, mLayoutId, parent, convertView);
		convert(holder, getItem(position));
		return holder.getConvertView();
	}

	public abstract void convert(ViewHolder holder, T t);

}
