package com.ovu.lido.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
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
import com.ovu.lido.help.ConfirmDialog;
import com.ovu.lido.widgets.LockRewardDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.reformer.rfBleService.BleDevContext;
import cn.com.reformer.rfBleService.BleService;
import cn.com.reformer.rfBleService.OnCompletedListener;
import cn.com.reformer.rfBleService.OnPasswordWriteListener;
import okhttp3.Call;

import static com.blankj.utilcode.util.ServiceUtils.unbindService;

public class LockManager {
    public static final String TAG = "LockHelper";
    private Context mContext;
    private boolean isBleSupported;//是否支持蓝牙
    private BluetoothAdapter mBluetoothAdapter;//蓝牙适配器
    private List<LockInfo.DataBean> scanDeviceList = new ArrayList<>();// app扫描到的设备列表
    private List<LockInfo.DataBean> authDeviceList = new ArrayList<>();// 有权限的设备列表
    private LockListDialog lockListDialog;//可开的门列表对话框

    private static final int BLE_OPEN_TIME_OUT = 15 * 1000;// 蓝牙开门超时时间
    public static final int OPEN_DOOR_CODE = 1;//开门
    private static final int TIME_OUT_MSG_CODE = 2;//超时

    private Map<String, Boolean> timeOutMap = new HashMap<>();//开门超时集合


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            String showText = null;
            switch (msg.what) {
                case OPEN_DOOR_CODE:// 开门
                    removeMessages(TIME_OUT_MSG_CODE);
                    String ble_mac = String.valueOf(msg.obj);
                    if (0 == msg.arg1) {
                        showText = "开门成功";
                        ToastUtil.show(mContext, showText);
                        uploadBleUnlock(ble_mac);
                        if (lockListDialog != null) {
                            lockListDialog.dismiss();
                        }
                        LogUtil.i(TAG, "ble开门成功");
                        break;
                    } else if (1 == msg.arg1) {
                        showText = "开门密码错误，请重试";
                        ToastUtil.show(mContext, showText);
                    } else if (2 == msg.arg1) {

                        showText = "蓝牙异常断开，请重试";
                        ToastUtil.show(mContext, showText);
                    } else if (3 == msg.arg1) {
                        showText = "开门超时，请重试";
                        ToastUtil.show(mContext, showText);
                    } else {
                        showText = "开门失败，请重试";
                        ToastUtil.show(mContext, showText);
                    }
                    for (LockInfo.DataBean l : authDeviceList) {
                        if (TextUtils.equals(ble_mac, l.getDevice_blue_mac())) {
                            l.setOpening(false);
                        }
                    }
                    lockListDialog.notifyDataSetChanged();
                    ConfirmDialog dialog = new ConfirmDialog(mContext, null);
                    if (mContext instanceof Activity) {
                        Activity activity = (Activity) mContext;
                        if (!activity.isFinishing()) {
                            dialog.show();
                            dialog.setCancelVisible(View.GONE);
                            dialog.setContentText(showText);
                        }
                    }
                    break;
                case TIME_OUT_MSG_CODE:// 蓝牙开门超时

                    if (lockListDialog != null) {
                        lockListDialog.dismiss();
                    }
                    ToastUtil.show(mContext, "开门失败，请重试");
                    LogUtil.i(TAG, "ble开门超时");
                    break;
                case 199:
                    doBleService();
                    break;
            }
        }
    };
    private boolean isConnected = false;


    public LockManager(Context mContext) {
        this.mContext = mContext;
        initBle();
    }


    private void initBle() {
        //检测是否支持蓝牙功能
        isBleSupported = mContext.getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
        //创建蓝牙适配器

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        lockListDialog = new LockListDialog(mContext);
    }

    public void showLockListDialog() {
        //支持蓝牙
        if (isBleSupported) {
            //绑定服务
            //蓝牙已经开启
            if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
                Intent bindIntent = new Intent(mContext, BleService.class);
                mContext.bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
                isConnected = true;
                //开启蓝牙服务
                doBleService();
            } else {
                //显示打开蓝牙对话框
                ConfirmDialog dialog = new ConfirmDialog(mContext, new ConfirmDialog.OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        if (mBluetoothAdapter.disable()) {
                            mBluetoothAdapter.enable();
                        }
                    }

                    @Override
                    public void onCancel() {
                        ToastUtil.show(mContext, "请打开蓝牙开门");
                    }
                });
                dialog.show();
                dialog.setContentText("是否打开蓝牙？");
            }
            lockListDialog.scanDevice();
        } else {
            //不支持蓝牙
            ToastUtil.show(mContext, "设备蓝牙异常");
            lockListDialog.noDeviceFound();

        }
    }

    private void doBleService() {
        //显示加载对话框
        lockListDialog.show();
        //获取扫描结果
        if (rfBleKey == null) {
            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ((Activity) mContext).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            getBLEDeviceList();
                        }
                    });
                }
            }.start();
        } else {
            getBLEDeviceList();
        }
    }

    private void getBLEDeviceList() {
        if (rfBleKey == null) {
            Log.w(TAG, "rfBleKey is null");
            return;
        }
        // scan device list
        @SuppressWarnings("unchecked")
        ArrayList<BleDevContext> lst = rfBleKey.getDiscoveredDevices();
        scanDeviceList.clear();
        for (BleDevContext dev : lst) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(formatByteToMac(dev.mac));
            LockInfo.DataBean lock = new LockInfo.DataBean();
            lock.setDevice_blue_mac(stringBuffer.toString().toUpperCase());
            lock.setName(lock.getName());
            scanDeviceList.add(lock);
        }
        Log.i(TAG, "蓝牙设备扫描结束" + scanDeviceList.toString());
        if (Network.isAvailable(mContext)) {//有网络，从服务器取可开门列表数据
            getAuthDoorList();
        }else {//没有网络，从缓存中取
            String response = AppPreference.I().getString("auth_device_list", "");
            Log.i(TAG, "有权限门禁数据-内存获取: " + response);
            //解析数据
            parseAuthDeviceData(response);

        }
    }

    /**
     * @param bytes
     * @return
     */
    private String formatByteToMac(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(bytePadLeft(Integer.toHexString(bytes[i]), 2));
        }
        return sb.toString();
    }

    private static String bytePadLeft(String str, int len) {
        if (str.length() > 2)
            str = str.substring(str.length() - 2);
        String pad = "0000000000000000";
        return len > str.length() && len <= 16 && len >= 0 ? pad.substring(0, len - str.length()) + str : str;
    }

    /**
     * 获取附近有限制的门列表
     */
    private void getAuthDoorList() {
        System.out.println("+++++++++++++++++++++++++++++++++++");

        Map<String, Object> map = new HashMap<>();
        List<Map<String, String>> mapList = new ArrayList<>();
        for (int i = 0; i < scanDeviceList.size(); i++) {
            Map<String, String> param = new HashMap<>();
            LockInfo.DataBean lock = scanDeviceList.get(i);
            param.put("name", lock.getName());
            param.put("device_blue_mac", lock.getDevice_blue_mac());
            mapList.add(param);
            Log.i(TAG, "+++++deviceBlueMac++++++++" + lock.getDevice_blue_mac());

        }
        map.put("deviceList", mapList);
        map.put("token", AppPreference.I().getString("token", ""));
        map.put("appType", "1");
        Map<String, Object> params = new HashMap<>();
        params.put("data", map);
        Log.i(TAG, "params: " + params.toString());
        OkHttpUtils.post()
                .url(Constant.GET_AUTH_DEVICE_LIST)
                .addParams("params", GsonUtil.ToGson(params))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "有权限门禁数据: " + response);
                        AppPreference.I().putString("auth_device_list",response);//缓存请求到有权限门禁数据
                        //解析数据
                        parseAuthDeviceData(response);
                    }
                });

    }

    /**
     * 解析有权限设备数据
     * @param response
     */
    private void parseAuthDeviceData(String response) {
        try {
            JSONObject object = new JSONObject(response);
            String errorCode = object.optString("errorCode");
            if (errorCode.equals(Constant.RESULT_OK)){
//                authDeviceList.clear();
                LockInfo info = GsonUtil.GsonToBean(response, LockInfo.class);
                List<LockInfo.DataBean> data = info.getData();
                for (LockInfo.DataBean d :
                        data) {
                    if (d.getOpen_tag().equals("1")) {
                        authDeviceList.add(d);
                    }
                }

                if (authDeviceList.isEmpty()) {
                    lockListDialog.noDeviceFound();
                } else {
                    lockListDialog.hasDeviceFound();
                    lockListDialog.notifyDataSetChanged();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private BleService mService;
    private BleService.RfBleKey rfBleKey;
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            mService = ((BleService.LocalBinder) rawBinder).getService();
            rfBleKey = mService.getRfBleKey();
            Log.e(TAG, "mService.getRfBleKey() success");
            rfBleKey.init(null);
            rfBleKey.setOnCompletedListener(new OnCompletedListener() {
                @Override
                public void OnCompleted(byte[] bytes, int i) {
                    // ！！！！！！非UI线程！！！！！！！！！！
                    String formatMacString = formatByteToMac(bytes);
                    if (TextUtils.isEmpty(formatMacString)) {
                        return;
                    }
                    String mac = formatMacString.toUpperCase();// 被操作门的ble_mac
                    Log.i(TAG, "开门结果回调：" + mac);
                    Boolean timeOutFlag = timeOutMap.get(mac);
                    if (timeOutFlag != null && timeOutFlag) {
                        Log.w(TAG, "蓝牙开门超时");
                    } else {
                        Message msg = mHandler.obtainMessage(OPEN_DOOR_CODE);
                        msg.arg1 = i;
                        msg.obj = mac;
                        msg.sendToTarget();
                    }
                }
            });

            rfBleKey.setOnPasswordWriteListener(new OnPasswordWriteListener() {
                @Override
                public void OnPasswordWrite(byte[] bytes, int i) {

                }
            });

            rfBleKey.setOnBleDevListChangeListener(new BleService.OnBleDevListChangeListener() {
                @Override
                public void onNewBleDev(BleDevContext bleDevContext) {
                    //发现新设备
                }

                @Override
                public void onUpdateBleDev(BleDevContext bleDevContext) {
                    //设备更新
                }
            });
        }


        @Override
        public void onServiceDisconnected(ComponentName classname) {
            mService = null;
            Log.i(TAG, "onServiceDisconnected");
        }
    };


    private class LockListDialog extends Dialog {
        private Context mContext;
        private GridView lv_lock_grid;
        private LockListAdapter listAdapter;
        private ProgressBar pb_lock_loading;
        private TextView tv_no_doors;

        public LockListDialog(Context mContext) {
            super(mContext);
            this.mContext = mContext;

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_lock_list);
            Window window = getWindow();
            window.getAttributes().gravity = Gravity.CENTER;
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenW = dm.widthPixels;
            int screenH = dm.heightPixels;
            window.setLayout((int) (screenW * 0.9), screenH * 2 / 5);
            window.setBackgroundDrawableResource(R.color.transparent);

            initView();
            setListener();
        }

        private void initView() {
            pb_lock_loading = findViewById(R.id.pb_lock_loading);
            tv_no_doors = findViewById(R.id.tv_no_doors);
            lv_lock_grid = findViewById(R.id.lv_lock_grid);
            listAdapter = new LockListAdapter(mContext, authDeviceList);
            lv_lock_grid.setAdapter(listAdapter);
        }

        private void setListener() {
            listAdapter.setmPrepareUnlockListener(new LockListAdapter.PrepareUnlockListener() {
                @Override
                public void unlock(View view, int position) {
                    authDeviceList.get(position).setOpening(true);
                    listAdapter.notifyDataSetChanged();
                    if (isBleSupported && rfBleKey != null) {
                        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
                            String device_blue_mac = authDeviceList.get(position).getDevice_blue_mac();
                            String device_key = authDeviceList.get(position).getDevice_key();
                            if (0 == rfBleKey.openDoor(stringToBytes(device_blue_mac), 5, device_key)) {

                                Log.i(TAG, "执行蓝牙开门");
                                Message msg = mHandler.obtainMessage(TIME_OUT_MSG_CODE);
                                msg.obj = device_blue_mac;
                                mHandler.sendMessageDelayed(msg, BLE_OPEN_TIME_OUT);
                                timeOutMap.put(device_blue_mac, false);
                            }
                        }
                    }

                }
            });
        }

        private byte[] stringToBytes(String outStr) {
            if (outStr.length() != 18)
                return null;
            int len = outStr.length() / 2;
            byte[] mac = new byte[len];
            for (int i = 0; i < len; i++) {
                String s = outStr.substring(i * 2, i * 2 + 2);
                if (Integer.valueOf(s, 16) > 0x7F) {
                    mac[i] = (byte) (Integer.valueOf(s, 16) - 0xFF - 1);
                } else {
                    mac[i] = Byte.valueOf(s, 16);
                }
            }
            return mac;
        }

        public void notifyDataSetChanged() {
            if (listAdapter != null) {
                listAdapter.notifyDataSetChanged();
            }
        }

        /**
         *
         */
        public void scanDevice() {
            if (!isShowing()) {
                return;
            }
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
            lv_lock_grid.setVisibility(View.VISIBLE);
            pb_lock_loading.setVisibility(View.GONE);
            tv_no_doors.setVisibility(View.GONE);
        }
    }


    /**
     * 门打开之后提交开门信息
     *
     * @param ble_mac 开锁的mac地址
     */
    private void uploadBleUnlock(String ble_mac) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        paramsMap.put("token", AppPreference.I().getString("token", ""));
        paramsMap.put("appType", "1");
        paramsMap.put("device_blue_mac", ble_mac);
        String params = ViewHelper.getParams(paramsMap);
        OkHttpUtils.post()
                .url(Constant.SAVE_RECORD)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "提交开门记录数据: " + response);
                        UpLoadInfo info = GsonUtil.GsonToBean(response, UpLoadInfo.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            openDoorRedPaket();
                        } else {
                            ToastUtil.show(mContext, info.getErrorMsg());
                        }
                    }
                });
    }

    /**
     * 开门红包
     */
    private void openDoorRedPaket() {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("resident_id", AppPreference.I().getString("resident_id", ""));
        paramsMap.put("token", AppPreference.I().getString("token", ""));
        String params = ViewHelper.getParams(paramsMap);
        OkHttpUtils.post()
                .url(Constant.OPEN_DOOR_RED_PAKET)
                .addParams("params", params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError: " + e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "开门红包数据: " + response);
                        OpenDoorRedPaket info = GsonUtil.GsonToBean(response, OpenDoorRedPaket.class);
                        if (info.getErrorCode().equals(Constant.RESULT_OK)) {
                            OpenDoorRedPaket.DataBean data = info.getData();
                            int type = data.getType();
                            //0 正常返回有红包，2已发红包，1余额不够 3没有红包发
                            switch (type) {
                                case 0:
                                    // 抢到红包
                                    double redpackage = data.getRedpackage();
                                    String sponsor = data.getSponsor();
                                    String sponsor_img = data.getSponsor_img();
                                    LockRewardDialog dialog = new LockRewardDialog(mContext
                                            , ViewHelper.getDisplayPrice(redpackage)
                                            , sponsor
                                            , type
                                            , sponsor_img);
                                    dialog.show();
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                            }
                        }
                    }
                });
    }

    public void onDestroy() {
        rfBleKey.free();
        if (isConnected) {
            mContext.unbindService(mServiceConnection);
            isConnected = false;
        }
    }


}
