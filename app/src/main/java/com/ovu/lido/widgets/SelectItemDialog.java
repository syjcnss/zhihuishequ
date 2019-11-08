package com.ovu.lido.widgets;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.base.CommonAdapter;
import com.ovu.lido.base.ViewHolder;
import com.ovu.lido.bean.ItemEntity;
public class SelectItemDialog<T extends ItemEntity> implements OnItemClickListener {

	Context mContext;
	View referView;
	PopWindow popWindow;
	SelectTypeAdapter adapter;
	List<T> mList = new ArrayList<T>();
	SelectListener mListener;

	public SelectItemDialog(Context ctx, View v, List<T> list, SelectListener listener) {
		this.mContext = ctx;
		this.referView = v;
		this.mList = list;
		this.mListener = listener;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mListener.loadData(mList.get(position));
		popWindow.dismiss();
	}

	public void showFilterWindow() {
		View contentView = LayoutInflater.from(mContext).inflate(R.layout.widget_select_dialog, null);
		ListView pop_list_view = (ListView) contentView.findViewById(R.id.gridView);
		adapter = new SelectTypeAdapter(mContext, R.layout.widget_item_dialog, mList);
		Button submit = (Button) contentView.findViewById(R.id.submit);
		submit.setVisibility(View.GONE);
		pop_list_view.setAdapter(adapter);
		pop_list_view.setOnItemClickListener(this);
		popWindow = new PopWindow(contentView);
		popWindow.showAtLocation(referView, Gravity.CENTER, 0, 0);
		popWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				popWindow = null;
			}
		});
	}

	private static class PopWindow extends PopupWindow {

		public PopWindow(View view) {

			super(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
			setBackgroundDrawable(view.getContext().getResources().getDrawable(R.color.translucence_6));

		}
	}

	class SelectTypeAdapter extends CommonAdapter<T> {

		public SelectTypeAdapter(Context context, int layoutId, List<T> datas) {
			super(context, datas, layoutId);
		}

		@Override
		protected void convert(ViewHolder holder, T item, int position) {
			TextView tv = holder.getView(R.id.ban_txt);
			tv.setText(item.getItem_name());
			ImageView iv = holder.getView(R.id.tag);
			iv.setVisibility(View.GONE);
		}
	}

	public interface SelectListener {
		void loadData(Object t);
	}

}
