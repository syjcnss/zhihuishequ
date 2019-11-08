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

import com.ovu.lido.R;
import com.ovu.lido.adapter.CommonAdapter;
import com.ovu.lido.adapter.holder.ViewHolder;
import com.ovu.lido.bean.DoorInfo;

import java.util.ArrayList;
import java.util.List;

public class SelectDoorDialog implements OnItemClickListener {
    public static final String TAG = "wangw";
    private Context mContext;
    private View referView;
    private PopWindow popWindow;
    private SelectDoorAdapter adapter;
    private List<DoorInfo.DataBean> mList;

    private RefreshData refreshData;

    public void setRefreshData(RefreshData refreshData) {
        this.refreshData = refreshData;
    }

    public interface RefreshData {
        void loadData(List<DoorInfo.DataBean> entity);
    }

    public SelectDoorDialog(Context ctx, View v, List<DoorInfo.DataBean> doorList) {
        this.mContext = ctx;
        this.referView = v;
        this.mList = doorList;
    }



    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        for (int i = 0; i < mList.size(); i++) {
            if (i == arg2) {
                mList.get(i).setSelect(!mList.get(i).isSelect());
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void showFilterWindow() {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.widget_select_dialog, null);
        ListView pop_list_view = (ListView) contentView.findViewById(R.id.gridView);
        adapter = new SelectDoorAdapter(mContext, R.layout.widget_item_dialog, mList);
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
                ArrayList<DoorInfo.DataBean> list = new ArrayList<>();
                for (int i = 0; i < mList.size(); i++) {
                    if (mList.get(i).isSelect()) {
                        list.add(mList.get(i));
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
            setBackgroundDrawable(view.getContext().getResources().getDrawable(R.color.translucence_6));

        }
    }

    class SelectDoorAdapter extends CommonAdapter<DoorInfo.DataBean> {

        public SelectDoorAdapter(Context context, int layoutId, List<DoorInfo.DataBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        public void convert(ViewHolder holder, DoorInfo.DataBean t) {
            TextView tv = holder.getView(R.id.ban_txt);
            tv.setText(t.getName());
            ImageView iv = holder.getView(R.id.tag);
            if (t.isSelect()) {
                iv.setVisibility(View.VISIBLE);
                tv.setSelected(true);
            } else {
                iv.setVisibility(View.GONE);
                tv.setSelected(false);

            }
        }
    }

}
