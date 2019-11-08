package com.ovu.lido.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.ovu.lido.ui.EnterMethodActivity;
import com.ovu.lido.widgets.CommonAction;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;


public class AppUtil {

    private static final String TAG = "AppUtil";


    /**
     * 获取状态栏高度
     *
     * @return
     */
    public static int getStatusBarHeight(Activity context) {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param fontScale （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(float spValue, float fontScale) {
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getAPILevel(Context context) {
        int version = Build.VERSION.SDK_INT;
        System.out.println("api version is " + Build.VERSION.SDK_INT);
        return version;
    }

    /**
     * 获取当前版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            code = pi.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 获取当前版本名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        String name = "";
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            name = pi.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 将bitmap转换为base64字节数组
     */
    public static byte[] bitmap2Base64(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            // 先将bitmap转换为普通的字节数组
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            byte[] buffer = out.toByteArray();
            // 将普通字节数组转换为base64数组
            byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
            return encode;
        } catch (Exception e) {
            Log.w(TAG, e.getMessage(), e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.w(TAG, "OutputStream close failed");
            }
        }
        return null;
    }

    /**
     * 将base64字节数组转换为bitmap
     */
    public static Bitmap base642Bitmap(byte[] base64) {
        // 将base64字节数组转换为普通的字节数组
        byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
        // 用BitmapFactory创建bitmap
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static double change(double a) {
        return a * Math.PI / 180;
    }

    public static double changeAngle(double a) {
        return a * 180 / Math.PI;
    }


    public static boolean isToken(String res) {
        JSONObject jsonObject = null;
        String a = "";
        try {
            jsonObject = new JSONObject(res);
            a = jsonObject.optString("errorCode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (!TextUtils.isEmpty(a)) && a.equals("9989");
    }

    public static void toLogin(Context context) {
        Intent intent = new Intent((Activity) context, EnterMethodActivity.class);
        context.startActivity(intent);
        AppPreference.I().putString("password", "");
        CommonAction.getInstance().OutSign();
    }


    /**
     * 保留两位小数
     *
     * @param money
     * @return
     */
    public static String TwoPoint(double money) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(money);
    }


    public static InputStream mergeStream(Context context, List<String> pathList) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream result = new DataOutputStream(baos);
        try {
            result.writeInt(pathList.size());
            for (String str : pathList) {
                byte[] buffer = readFully(context, str);
                result.writeInt(buffer.length);
                result.write(buffer);
            }
        } catch (IOException e) {
            Logger.w(TAG, e.getMessage());
        }
        return new ByteArrayInputStream(baos.toByteArray());
    }


    public static byte[] readFully(Context context, String path) {
        File file = new File(path);
        int length = (int) file.length();
        byte[] contentBytes = new byte[length];
        int index = 0;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            while (index != length) {
                index += is.read(contentBytes, index, length - index);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    Logger.i(TAG, "close input stream");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return contentBytes;
    }


    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    public static boolean checkedVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true;
        }
        return false;
    }


}
