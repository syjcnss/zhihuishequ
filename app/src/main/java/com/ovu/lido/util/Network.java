package com.ovu.lido.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.ovu.lido.base.AppActivity;

public class Network {
    /**
     * </br><b>title : </b> 网络是否可用 </br><b>description :</b>网络是否可用 </br><b>time
     * :</b> 2012-7-18 下午7:44:36
     *
     * @param context
     * @return
     */
    public static boolean isAvailable(Context context) {
        if(context==null)
            context = AppActivity.getInstance();
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        return !(networkinfo == null || !networkinfo.isAvailable());
    }

    /**
     * </br><b>title : </b> Wifi是否启用 </br><b>description :</b>Wifi是否启用
     * </br><b>time :</b> 2012-7-18 下午7:45:20
     *
     * @param c
     * @return
     */
    public static boolean isWIFIActivate(Context c) {
        return ((WifiManager) c.getSystemService(Context.WIFI_SERVICE)).isWifiEnabled();
    }

    /**
     * </br><b>title : </b> 修改WIFI状态 </br><b>description :</b>修改WIFI状态
     * </br><b>time :</b> 2012-7-18 下午7:46:13
     *
     * @param c
     * @param status
     */
    public static void changeWIFIStatus(Context c, boolean status) {
        ((WifiManager) c.getSystemService(Context.WIFI_SERVICE)).setWifiEnabled(status);
    }

    /**
     * </br><b>description :</b>提示打开网络设置 </br><b>time :</b> 2012-7-18 下午7:46:13
     *
     */
    public static void showNetworkSetting(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    /**
     * 判断wifi是否已连接
     *
     * @param inContext
     * @return
     */
    public static boolean isWiFiConnected(Context inContext) {
        WifiManager mWifiManager = (WifiManager) inContext.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        int ipAddress = (wifiInfo == null) ? 0 : wifiInfo.getIpAddress();
        if (mWifiManager.isWifiEnabled() && ipAddress != 0) {
            if (ipAddress == 0) {
                LogUtil.i("Network", "wifi is on, but not connected");
            } else {
                LogUtil.i("Network", "wifi is on and connected");
            }
            return true;
        } else {
            LogUtil.i("Network", "wifi is off");
            return false;
        }
    }
}
