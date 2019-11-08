package com.ovu.lido.widgets;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ovu.lido.R;
import com.ovu.lido.base.CommonAdapter;
import com.ovu.lido.base.ViewHolder;
import com.ovu.lido.bean.DecorationProjectInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class SelectDialog implements OnItemClickListener {

	Context mContext;
	View referView;
	PopWindow popWindow;
	SelectAdapter adapter;
	List<DecorationProjectInfo.DataBean> dataList = new ArrayList<>();

	public SelectDialog(Context ctx, View v) {
		this.mContext = ctx;
		this.referView = v;
		addData();
	}

	private void addData() {
		OkHttpUtils.post()
				.url(Constant.DECORATION_PROJECT_URL)
				.addParams("resident_id", AppPreference.I().getString("resident_id",""))
				.build()
				.execute(new StringCallback() {
					@Override
					public void onError(Call call, Exception e, int id) {

					}

					@Override
					public void onResponse(String response, int id) {
						DecorationProjectInfo info = GsonUtil.GsonToBean(response, DecorationProjectInfo.class);
						if (info.getErrorCode().equals(Constant.RESULT_OK)){
							dataList.addAll(info.getData());
						}else {
							Toast.makeText(mContext,info.getErrorMsg(),Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		for (int i = 0; i < dataList.size(); i++) {
			if (i == arg2) {
				dataList.get(i).setSelect(!dataList.get(i).isSelect());
			}
		}
		adapter.notifyDataSetChanged();
	}

	public void showFilterWindow() {
		View contentView = LayoutInflater.from(mContext).inflate(R.layout.widget_select_dialog, null);
		ListView pop_list_view = (ListView) contentView.findViewById(R.id.gridView);
		adapter = new SelectAdapter(mContext, dataList, R.layout.widget_item_dialog);
		Button submit = (Button) contentView.findViewById(R.id.submit);
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
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ArrayList<DecorationProjectInfo.DataBean> list = new ArrayList<>();
				for (int i = 0; i < dataList.size(); i++) {
					if (dataList.get(i).isSelect()) {
						list.add(dataList.get(i));
					}
				}
				refreshData.loadData(list);
				popWindow.dismiss();
			}
		});
	}

	private static class PopWindow extends PopupWindow {

		public PopWindow(View view) {

			super(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
			// setOutsideTouchable(false);
			setBackgroundDrawable(view.getContext().getResources().getDrawable(R.color.translucence_6));

		}
	}

	class SelectAdapter extends CommonAdapter<DecorationProjectInfo.DataBean> {

		public SelectAdapter(Context context, List<DecorationProjectInfo.DataBean> data, int itemLayoutId) {
			super(context, data, itemLayoutId);
		}

		@Override
		protected void convert(ViewHolder viewHolder, DecorationProjectInfo.DataBean item, int position) {
			TextView tv = viewHolder.getView(R.id.ban_txt);
			tv.setText(item.getProjectName());
			ImageView iv = viewHolder.getView(R.id.tag);
			if (item.isSelect()) {
				iv.setVisibility(View.VISIBLE);
				tv.setSelected(true);
			} else {
				iv.setVisibility(View.GONE);
				tv.setSelected(false);

			}
		}
	}

	RefreshData refreshData;

	public void setRefreshData(RefreshData refreshData) {
		this.refreshData = refreshData;
	}

	public interface RefreshData {
		void loadData(List<DecorationProjectInfo.DataBean> entity);
	}

}
