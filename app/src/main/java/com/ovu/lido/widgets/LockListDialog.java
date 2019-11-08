package com.ovu.lido.widgets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ovu.lido.R;
import com.ovu.lido.adapter.LockListAdapter;
import com.ovu.lido.bean.LockInfo;
import com.ovu.lido.bean.OpenDoorRedPaket;
import com.ovu.lido.bean.UpLoadInfo;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.LockHelper;
import com.ovu.lido.util.ToastUtil;
import com.ovu.lido.util.ViewHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;


public class LockListDialog extends Dialog {
    private Context mContext;
    private List<LockHelper.Lock> mList;
    // private ListView lv_lock_list;
    private GridView lv_lock_grid;
    private LockHelper.LockAdapter2 mAdapter;
    private LockHelper.PrepareUnlockListener mListener;
    private ProgressBar pb_lock_loading;
    private TextView tv_no_doors;

    public LockListDialog(Context context, List<LockHelper.Lock> list, LockHelper.PrepareUnlockListener listener) {
        super(context);
        mContext = context;
        mList = list;
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_lock_list);
        Window window = getWindow();
        window.getAttributes().gravity = Gravity.CENTER;
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        int screenH = dm.heightPixels;
        window.setLayout((int) (screenW * 0.9), screenH * 2 / 5);
        window.setBackgroundDrawableResource(R.color.transparent);
        mAdapter = new LockHelper.LockAdapter2(mContext, R.layout.lock_grid_item, mList, mListener);
        pb_lock_loading = (ProgressBar) findViewById(R.id.pb_lock_loading);
        tv_no_doors = (TextView) findViewById(R.id.tv_no_doors);

        lv_lock_grid = (GridView) findViewById(R.id.lv_lock_grid);
        lv_lock_grid.setAdapter(mAdapter);
    }

    public void notifyDataSetChanged() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     *
     */
    public void scanDevice() {
        if (!isShowing()) {
            return;
        }
        // lv_lock_list.setVisibility(View.GONE);
        lv_lock_grid.setVisibility(View.GONE);
        pb_lock_loading.setVisibility(View.VISIBLE);
        tv_no_doors.setVisibility(View.GONE);
    }

    /**
     * 未找到可打开的门
     */
    public void noDeviceFound() {
        if (!isShowing()) {
            return;
        }
        // lv_lock_list.setVisibility(View.GONE);
        lv_lock_grid.setVisibility(View.GONE);
        pb_lock_loading.setVisibility(View.GONE);
        tv_no_doors.setVisibility(View.VISIBLE);
    }

    /**
     * 已找到可打开的门
     */
    public void hasDeviceFound() {
        if (!isShowing()) {
            return;
        }
        // lv_lock_list.setVisibility(View.VISIBLE);
        lv_lock_grid.setVisibility(View.VISIBLE);
        pb_lock_loading.setVisibility(View.GONE);
        tv_no_doors.setVisibility(View.GONE);
    }

}
