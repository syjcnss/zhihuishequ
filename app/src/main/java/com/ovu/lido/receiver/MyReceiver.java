package com.ovu.lido.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.ovu.lido.MainActivity;
import com.ovu.lido.ui.MessageDetailActivity;
import com.ovu.lido.util.ExampleUtil;
import com.ovu.lido.util.Logger;
import com.ovu.lido.widgets.CommonAction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {

        private static final String TAG = "JIGUANG-Example";

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                Bundle bundle = intent.getExtras();
                Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

                if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                    String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                    Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                    //send the Registration Id to your server...

                } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                    Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                    processCustomMessage(context, bundle);

                } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                    Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                    int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                    Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

                } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                    Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");

                    //打开自定义的Activity
                    openNotification(context,bundle);
//
//                    Intent i = new Intent(context, AnnouncementDetailsActivity.class);
//                    i.putExtras(bundle);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(i);

                } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                    Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                    //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

                } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                    boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                    Logger.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
                } else {
                    Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
                }
            } catch (Exception e){

            }

        }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String business_id = "";
        String business_type = "";
        Log.i(TAG, "发送过来的消息" + extras);
        try {
            JSONObject object = new JSONObject(extras).optJSONObject("customParams");
            business_id = object.optString("businessId");
            business_type = object.optString("businessType");
            //判断app进程是否存活
            if (CommonAction.getInstance().getAllActivitites().size() > 0) {
                switch (business_type) {
                    case "1":  // 小区头条
                        if (TextUtils.isEmpty(business_id)) {
                            return;
                        }
                        Intent intent = new Intent(context, MessageDetailActivity.class);
                        intent.putExtra("message_id", Integer.valueOf(business_id));
                        context.startActivity(intent);
                        break;
                    default:
                        context.startActivity(new Intent(context, MainActivity.class));
                        break;

                }
            }

        } catch (Exception e) {
            Logger.w(TAG, "Unexpected: extras is not a valid json" + e);
        }

    }

    // 打印所有的 intent extra 数据
        private static String printBundle(Bundle bundle) {
            StringBuilder sb = new StringBuilder();
            for (String key : bundle.keySet()) {
                if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                    sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
                }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                    sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
                } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                    if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                        Logger.i(TAG, "This message has no Extra data");
                        continue;
                    }

                    try {
                        JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                        Iterator<String> it =  json.keys();

                        while (it.hasNext()) {
                            String myKey = it.next();
                            sb.append("\nkey:" + key + ", value: [" +
                                    myKey + " - " +json.optString(myKey) + "]");
                        }
                    } catch (JSONException e) {
                        Logger.e(TAG, "Get message extra JSON error!");
                    }

                } else {
                    sb.append("\nkey:" + key + ", value:" + bundle.get(key));
                }
            }
            return sb.toString();
        }

        //send msg to MainActivity
        private void processCustomMessage(Context context, Bundle bundle) {
            if (MainActivity.isForeground) {
                String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
                Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
                msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
                if (!ExampleUtil.isEmpty(extras)) {
                    try {
                        JSONObject extraJson = new JSONObject(extras);
                        if (extraJson.length() > 0) {
                            msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
                        }
                    } catch (JSONException e) {

                    }

                }
                LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
            }
        }
}
