package com.ovu.lido.adapter.holder;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 通用ViewHolder (1)简化代码(2)增强可读性
 * 
 * @author sqq
 *
 */
public class ViewHolder {

	private SparseArray<View> mViews;
	private int mPosition;

	private View mConvertView;

	public ViewHolder(Context context, int position, int layoutId, ViewGroup parent) {
		mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		mPosition = position;
		mConvertView.setTag(this);
	}

	public static ViewHolder get(Context context, int position, int layoutId, ViewGroup parent, View convertView) {
		if (convertView == null) {
			return new ViewHolder(context, position, layoutId, parent);
		} else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.mPosition = position;
			return holder;
		}
	}

	public int getPosition() {
		return mPosition;
	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 通过ViewId获取控件
	 * 
	 * @param viewId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	public ViewHolder setText(int viewId, String text) {
		TextView tv = getView(viewId);
		tv.setText(text);
		return this;
	}

	public ViewHolder setDrawable(int viewId, int resId) {
		ImageView iv = getView(viewId);
		iv.setImageResource(resId);
		return this;
	}

	public ViewHolder setVisibility(int viewId, int visibility) {
		View view = getView(viewId);
		view.setVisibility(visibility);
		return this;
	}

}
