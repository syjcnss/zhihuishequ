package com.ovu.lido.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.SparseArray;

import com.ovu.lido.base.BaseActivity;

public class TaskHelper {
    public static void finishAffinity(Activity activity, Intent intent) {
        if (Build.VERSION.SDK_INT >= 16) {
            // 当api大于等于16时，
            ActivityCompat.finishAffinity(activity);
        } else {
            SparseArray<Activity> stack = BaseActivity.getInstance().activityStack;
            int size = BaseActivity.getInstance().activityStackIndex + 1;
            for (int i = 0; i < size; i++) {
                Activity act = stack.get(i);
                if (act != null && !act.isFinishing()) {
                    // 该Activity没有被销毁
                    act.finish();
                }
            }
        }
        if (intent != null) {
            activity.startActivity(intent);
        }
    }
}
