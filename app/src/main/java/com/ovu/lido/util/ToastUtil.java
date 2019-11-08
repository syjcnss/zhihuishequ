package com.ovu.lido.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	private static Toast mToast;

	public static void show(Context context, int id) {
		show(context, context.getString(id));
	}

	public static void show(Context context, String s) {
		if (context == null) {
			LogUtil.i("ToastUtil", "context is null");
			return;
		}
		if (mToast == null) {
			mToast = Toast.makeText(context, s, Toast.LENGTH_LONG);
		} else {
			mToast.setText(s);
		}
		mToast.show();
	}
}
