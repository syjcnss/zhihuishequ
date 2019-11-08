package com.ovu.lido.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.ovu.lido.R;
import com.ovu.lido.util.LockHelper;
import com.ovu.lido.util.LogUtil;
import com.ovu.lido.util.ToastUtil;

public class OpenDoorDialog extends Dialog implements View.OnClickListener {

	public static final String OpenDoor = "open_door_type_chang";

	private Context mContext;
	private SharedPreferences sdf;
	private int type;

	public OpenDoorDialog(Context context) {
		super(context);
		mContext = context;
		sdf = PreferenceManager.getDefaultSharedPreferences(mContext);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_open_door);
		Window window = getWindow();
		window.getAttributes().gravity = Gravity.CENTER;
		DisplayMetrics dm = new DisplayMetrics();
		((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		int screenH = dm.heightPixels;
		window.setLayout((int) (screenW * 0.9), screenH * 2 / 5);
		window.setBackgroundDrawableResource(R.color.transparent);
		findViewById(R.id.ll_door_wifi).setOnClickListener(this);
		findViewById(R.id.ll_door_ble).setOnClickListener(this);
		ImageView iv_door_wifi = (ImageView) findViewById(R.id.iv_door_wifi);
		ImageView iv_door_ble = (ImageView) findViewById(R.id.iv_door_ble);
		type = sdf.getInt(LockHelper.PRE_DOOR_TYPE, LockHelper.OPEN_TYPE_WIFI);
		if (type == LockHelper.OPEN_TYPE_WIFI) {
			iv_door_wifi.setVisibility(View.VISIBLE);
			iv_door_ble.setVisibility(View.GONE);
		} else {
			iv_door_wifi.setVisibility(View.GONE);
			iv_door_ble.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		dismiss();
		Editor editor = sdf.edit();
		switch (v.getId()) {
		case R.id.ll_door_wifi:
			if (type == LockHelper.OPEN_TYPE_WIFI) {
				LogUtil.i("OpenDoorDialog", "开门方式wifi不变");
			} else {
				editor.putInt(LockHelper.PRE_DOOR_TYPE, LockHelper.OPEN_TYPE_WIFI).commit();
				Intent intent = new Intent(OpenDoor);
				mContext.sendBroadcast(intent);
			}
			break;
		case R.id.ll_door_ble:
			if (type == LockHelper.OPEN_TYPE_BLE) {
				LogUtil.i("OpenDoorDialog", "开门方式蓝牙不变");
			} else {
				boolean isBleSupported = mContext.getPackageManager().hasSystemFeature(
						PackageManager.FEATURE_BLUETOOTH_LE);
				if (isBleSupported) {
					editor.putInt(LockHelper.PRE_DOOR_TYPE, LockHelper.OPEN_TYPE_BLE).commit();
					Intent intent = new Intent(OpenDoor);
					mContext.sendBroadcast(intent);
				} else {
					ToastUtil.show(mContext, "设备不支持蓝牙4.0");
				}
			}
			break;
		default:
			break;
		}
	}
}
