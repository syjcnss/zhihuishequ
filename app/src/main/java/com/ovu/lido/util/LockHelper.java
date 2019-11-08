package com.ovu.lido.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.annotations.SerializedName;
import com.ovu.lido.MainActivity;
import com.ovu.lido.R;
import com.ovu.lido.adapter.CommonAdapter;
import com.ovu.lido.adapter.holder.ViewHolder;
import com.ovu.lido.help.ConfirmDialog;
import com.ovu.lido.help.StringUtil;
import com.ovu.lido.widgets.LockListDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.reformer.rfBleService.BleDevContext;
import cn.com.reformer.rfBleService.BleService;
import cn.com.reformer.rfBleService.OnCompletedListener;
import cn.com.reformer.rfBleService.OnPasswordWriteListener;

/**
 * 开门工具类
 * 
 * @author sqq
 *
 */
public class LockHelper {

	private Activity mActivity;
	private BleService mService;
	private BleService.RfBleKey rfBleKey;
	private BluetoothAdapter mBluetoothAdapter;
	private List<Lock> scanDeviceList;// app扫描到的设备列表
	private List<Lock> authDeviceList;// 有权限的设备列表
	private LockListDialog lockListDialog;
	private boolean isBleSupported;// 检测设备是否支持蓝牙4.0
	private static final int BLE_OPEN_TIME_OUT = 15 * 1000;// 蓝牙开门超时时间
	private static final int TIME_OUT_MSG_CODE = 3;
	private Map<String, Boolean> timeOutMap;//
	private Map<String, Lock> lockMap;// 存储蓝牙mac和lock对象对应关系
	private static final String AP_PREFIX = "ESP";// 门禁设备前缀
	//
	public static final String PRE_DOOR_TYPE = "open_door_type";// 1wifi/2ble
	public static final int OPEN_TYPE_WIFI = 1;
	public static final int OPEN_TYPE_BLE = 2;
	//
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			String showText = null;
			switch (msg.what) {
				case 1:// 开门
					String ble_mac = String.valueOf(msg.obj);
					Lock msgLock = lockMap.get(ble_mac);
					if (0 == msg.arg1) {
						showText = "开门成功";
						ToastUtil.show(mActivity, showText);
//						uploadBleUnlock(ble_mac);
						if (lockListDialog != null) {
							lockListDialog.dismiss();
						}
						LogUtil.i("LockHelper", "ble开门成功");
						mHandler.removeMessages(TIME_OUT_MSG_CODE, msgLock);

						break;
					} else if (1 == msg.arg1) {
						showText = "开门密码错误，请重试";
						ToastUtil.show(mActivity, showText);
					} else if (2 == msg.arg1) {
						if (mHandler.hasMessages(TIME_OUT_MSG_CODE, msgLock)) {
							break;
						} else {
							showText = "蓝牙异常断开，请重试";
							ToastUtil.show(mActivity, showText);
						}
					} else if (3 == msg.arg1) {
						if (mHandler.hasMessages(TIME_OUT_MSG_CODE, msgLock)) {
							break;
						} else {
							showText = "开门超时，请重试";
							ToastUtil.show(mActivity, showText);
						}
					} else if (10 == msg.arg1) {
						// wifi开门成功
						showText = "开门成功";
						if (lockListDialog != null) {
							lockListDialog.dismiss();
						}
						ToastUtil.show(mActivity, showText);
						LogUtil.i("LockHelper", "wifi开门成功");
					} else {
						showText = "开门失败，请重试";
						ToastUtil.show(mActivity, showText);
					}
					for (Lock l : authDeviceList) {
						if (TextUtils.equals(ble_mac, l.ble_mac)) {
							l.opening = false;
						}
					}
					lockListDialog.notifyDataSetChanged();
					ConfirmDialog dialog = new ConfirmDialog(mActivity, null);
					if (mActivity instanceof Activity) {
						Activity activity = mActivity;
						if (!activity.isFinishing()) {
							dialog.show();
							dialog.setCancelVisible(View.GONE);
							dialog.setContentText(showText);
						}
					}
					break;
				case 2:// 设置密码
					if (0 == msg.arg1) {
						showText = "设置成功";
					} else if (1 == msg.arg1) {
						showText = "设置失败";
					}
					break;
				case TIME_OUT_MSG_CODE:// 蓝牙开门超时

					if (lockListDialog != null) {
						lockListDialog.dismiss();
					}
					ToastUtil.show(mActivity, "开门失败，请重试");
					// Lock lock = (Lock) msg.obj;
					// openDoorByWifi(lock);
					// timeOutMap.put(lock.ble_mac, true);
					// ToastUtil.show(mActivity, "执行wifi开门");
					LogUtil.i("LockHelper", "ble开门超时");
					break;
			}
		}
	};

	private final ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder rawBinder) {
			mService = ((BleService.LocalBinder) rawBinder).getService();
			rfBleKey = mService.getRfBleKey();
			LogUtil.e("LockHelper", "mService.getRfBleKey() success");
			rfBleKey.init(null);
			rfBleKey.setOnCompletedListener(new OnCompletedListener() {
				@Override
				public void OnCompleted(byte[] bytes, int i) {
					// ！！！！！！非UI线程！！！！！！！！！！
					String formatMacString = formatByteToMac(bytes);
					if (StringUtil.isEmpty(formatMacString)) {
						return;
					}
					String mac = formatMacString.toUpperCase();// 被操作门的ble_mac
					LogUtil.i("LockHelper", "开门结果回调：" + mac);
					Boolean timeOutFlag = timeOutMap.get(mac);
					if (timeOutFlag != null && timeOutFlag) {
						LogUtil.w("LockHelper", "蓝牙开门超时");
					} else {
						Message msg = mHandler.obtainMessage(1);
						msg.arg1 = i;
						msg.obj = mac;
						msg.sendToTarget();
					}
				}
			});

			rfBleKey.setOnPasswordWriteListener(new OnPasswordWriteListener() {
				@Override
				public void OnPasswordWrite(byte[] bytes, int i) {
					Message msg = mHandler.obtainMessage(2);
					msg.arg1 = i;
					msg.sendToTarget();
				}
			});
		}

		@Override
		public void onServiceDisconnected(ComponentName classname) {
			mService = null;
			LogUtil.i("LockHelper", "onServiceDisconnected");
		}
	};
	//
	private WifiManager mWifiManager;
	private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.getAction())) {
				scanDeviceList.clear();
				List<ScanResult> scanResults = mWifiManager.getScanResults();
				for (ScanResult sr : scanResults) {
					if (sr.SSID == null) {
						continue;
					}
					if (!sr.SSID.startsWith(AP_PREFIX)) {
						continue;
					}
					Lock lock = new Lock();
					lock.name = sr.SSID;
					lock.wifi_mac = sr.BSSID;
					scanDeviceList.add(lock);
				}
				LogUtil.i("LockHelper", "热点扫描结束");
				if (lockListDialog != null && lockListDialog.isShowing()) {
					getAuthDoorList();
				} else {
					LogUtil.w("LockHelper", "lock list dialog is not show");
				}
			}
		}
	};

	/**
	 * 开门方式变更
	 */
	public void setOpenDoorType() {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
		int type = sharedPreferences.getInt(PRE_DOOR_TYPE, OPEN_TYPE_BLE);
		LogUtil.w("LockHelper", "open door type changed:" + type);
		if (type == OPEN_TYPE_WIFI) {
			// wifi优先
			if (isMyServiceRunning(BleService.class)) {
				mActivity.unbindService(mServiceConnection);
			}
			if (rfBleKey != null) {
				rfBleKey.resetBluetoothState();//
			}
		} else {
			// 蓝牙优先
			if (isMyServiceRunning(BleService.class)) {
				if (mActivity instanceof MainActivity || mActivity instanceof MainActivity)
					mActivity.unbindService(mServiceConnection);
			}
			Intent bindIntent = new Intent(mActivity.getApplicationContext(), BleService.class);
			mActivity.bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
		}
	}

	public LockHelper(Activity act) {
		mActivity = act;
		isBleSupported = act.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
		scanDeviceList = new ArrayList<>();
		authDeviceList = new ArrayList<>();
		mWifiManager = (WifiManager) act.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(act);
		boolean bleOn = sharedPreferences.getInt(PRE_DOOR_TYPE, OPEN_TYPE_BLE) == OPEN_TYPE_BLE;// 蓝牙开门是否打开
		LogUtil.e("lanya", isBleSupported + "====111===" + bleOn);

		act.registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

		lockListDialog = new LockListDialog(mActivity, authDeviceList, new PrepareUnlockListener() {

			@Override
			public void unlock(Lock lock) {

				if (isBleSupported && rfBleKey != null) {
					if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
						if (0 == rfBleKey.openDoor(stringToBytes(lock.ble_mac), 5, lock.device_key)) {
							LogUtil.i("LockHelper", "执行蓝牙开门");
							Message msg = mHandler.obtainMessage(TIME_OUT_MSG_CODE);
							msg.obj = lock;
							mHandler.sendMessageDelayed(msg, BLE_OPEN_TIME_OUT);
							timeOutMap.put(lock.ble_mac, false);
							lockMap.put(lock.ble_mac, lock);
						} else {
//							openDoorByWifi(lock);
						}
					} else {
//						openDoorByWifi(lock);
					}
				} else {
//					openDoorByWifi(lock);
				}

				// if (isBleSupported && rfBleKey != null) {
				// if (0 == rfBleKey.openDoor(stringToBytes(lock.ble_mac), 5,
				// lock.device_key)) {
				// LogUtil.i("LockHelper", "执行蓝牙开门");
				// Message msg = mHandler.obtainMessage(TIME_OUT_MSG_CODE);
				// msg.obj = lock;
				// mHandler.sendMessageDelayed(msg, BLE_OPEN_TIME_OUT);
				// timeOutMap.put(lock.ble_mac, false);
				// lockMap.put(lock.ble_mac, lock);
				// } else {
				// openDoorByWifi(lock);
				// }
				// } else {
				// openDoorByWifi(lock);
				// }
			}
		});
	}

	public void showLockListDialog() {
		if (timeOutMap == null) {
			timeOutMap = new HashMap<>();
		}
		if (lockMap == null) {
			lockMap = new HashMap<>();
		} else {
			lockMap.clear();
		}
		// authDeviceList.clear();
		mHandler.removeCallbacksAndMessages(null);
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
		boolean bleOn = sharedPreferences.getInt(PRE_DOOR_TYPE, OPEN_TYPE_BLE) == OPEN_TYPE_BLE;// 蓝牙开门是否打开

		if (isBleSupported && bleOn) {
			if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {

				lockListDialog.show();
				lockListDialog.notifyDataSetChanged();
				lockListDialog.scanDevice();

				Intent bindIntent = new Intent(mActivity.getApplicationContext(), BleService.class);
				mActivity.bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

				authDeviceList.clear();
				if (rfBleKey == null) {
					new Thread() {
						public void run() {
							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							mActivity.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									getBLEDeviceList();
								}
							});
						}
					}.start();
					// if (!bleDeviceThread.isAlive()) {
					// bleDeviceThread.start();
					// }
				} else {
					getBLEDeviceList();
				}
			} else {

				ConfirmDialog dialog = new ConfirmDialog(mActivity, new ConfirmDialog.OnConfirmListener() {

					@Override
					public void onConfirm() {

						lockListDialog.show();
						lockListDialog.notifyDataSetChanged();
						lockListDialog.scanDevice();

						Intent bindIntent = new Intent(mActivity.getApplicationContext(), BleService.class);
						mActivity.bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

						if (mBluetoothAdapter.disable()) {
							mBluetoothAdapter.enable();
						}

						authDeviceList.clear();
						if (rfBleKey == null) {
							new Thread() {
								public void run() {
									try {
										Thread.sleep(3000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									mActivity.runOnUiThread(new Runnable() {

										@Override
										public void run() {
											getBLEDeviceList();
										}
									});
								}
							}.start();

							// if (!bleDeviceThread.isAlive()) {
							// bleDeviceThread.start();
							// }
						} else {
							getBLEDeviceList();
						}
					}

					@Override
					public void onCancel() {

						ToastUtil.show(mActivity, "请打开蓝牙开门");
						lockListDialog.noDeviceFound();

						/*
						 * if (rfBleKey != null) {
						 * rfBleKey.resetBluetoothState();// }
						 *
						 * if (isMyServiceRunning(BleService.class)) { if
						 * (mActivity instanceof MainAct) {
						 * mActivity.unbindService(mServiceConnection); } }
						 *
						 * if (mBluetoothAdapter.isEnabled()) {
						 * mBluetoothAdapter.disable(); }
						 *
						 * lockListDialog.show();
						 * lockListDialog.notifyDataSetChanged();
						 * lockListDialog.scanDevice();
						 *
						 * if (mWifiManager != null &&
						 * mWifiManager.isWifiEnabled()) { // 扫描wifi热点
						 * mWifiManager.startScan(); } else {
						 * ToastUtil.show(mActivity, "请打开wifi或蓝牙开门");
						 * lockListDialog.noDeviceFound(); return; }
						 */
					}
				});

				dialog.show();
				dialog.setContentText("是否打开蓝牙？");
			}

		} else {

			ToastUtil.show(mActivity, "设备蓝牙异常");
			lockListDialog.noDeviceFound();

			/*
			 * if (mWifiManager != null && mWifiManager.isWifiEnabled()) { //
			 * 扫描wifi热点
			 *
			 * lockListDialog.show(); lockListDialog.notifyDataSetChanged();
			 * lockListDialog.scanDevice();
			 *
			 * mWifiManager.startScan(); } else { ToastUtil.show(mActivity,
			 * "请打开wifi开门"); lockListDialog.noDeviceFound(); return; }
			 */
		}
	}

	/**
	 * 获取附近有限制的门列表
	 */
	private void getAuthDoorList() {
//		System.out.println("+++++++++++++++++++++++++++++++++++");
//
//		Map<String, Object> map = new HashMap<>();
//		JSONArray ja = new JSONArray();
//		for (int i = 0; i < scanDeviceList.size(); i++) {
//			Lock lock = scanDeviceList.get(i);
//			JSONObject jo = new JSONObject();
//			JSONUtil.putJsonObject(jo, "device_blue_mac", lock.ble_mac);
//			JSONUtil.putJsonObject(jo, "device_wifi_mac", lock.wifi_mac);
//			JSONUtil.putJsonObject(ja, i, jo);
//
//			System.out.println("+++++deviceBlueMac++++++++" + lock.ble_mac);
//			System.out.println("+++++deviceWifiMac++++++++" + lock.wifi_mac);
//		}
//		map.put("deviceList", ja);
//
//		RequestParams params = RequestParamsBuilder.begin().addToken(true).add("appType", "1", true).addUserInfo(true)
//				.end2(map);
//		HttpUtil.post(Constant.GET_AUTH_DEVICE_LIST, params, new BusinessResponseHandler(mActivity, false) {
//			@Override
//			protected void onBusinessSuccess(JSONObject response, Object extra) {
//				super.onBusinessSuccess(response, extra);
//
//				Type listType = new TypeToken<List<Lock>>() {
//				}.getType();
//				List<Lock> list = new Gson().fromJson(response.optString("data"), listType);
//				if (list == null || list.isEmpty()) {
//					lockListDialog.noDeviceFound();
//					return;
//				}
//				authDeviceList.clear();
//
//				// for (int i = 0; i < 1; i++) {
//				// Lock lock1 = new Lock();
//				// lock1.name = "门" + (i + 1);
//				// lock1.ble_mac = "dsdasdad";
//				// lock1.open_tag = "1";
//				// authDeviceList.add(lock1);
//				// }
//
//				for (Lock lock : list) {
//					if (TextUtils.equals(lock.open_tag, "1")) {
//						authDeviceList.add(lock);
//					}
//				}
//				if (authDeviceList.isEmpty()) {
//					lockListDialog.noDeviceFound();
//				} else {
//					lockListDialog.hasDeviceFound();
//					lockListDialog.notifyDataSetChanged();
//				}
//
//			}
//
//			@Override
//			protected void onBusinessFail(JSONObject response, Object extra) {
//				super.onBusinessFail(response, extra);
//				lockListDialog.noDeviceFound();
//			}
//
//		});

	}

	public void onPause() {
		if (rfBleKey != null) {
			rfBleKey.free();
		}
	}

	public void onResume() {
		if (rfBleKey != null) {
			rfBleKey.init(null);
		}
	}

	public void onDestroy() {
		if (rfBleKey != null) {
			rfBleKey.resetBluetoothState();//
		}
		if (isMyServiceRunning(BleService.class)) {
			LogUtil.i("LockHelper", "unbindService");
			mActivity.unbindService(mServiceConnection);
		}
		mActivity.unregisterReceiver(wifiReceiver);
	}

	private void getBLEDeviceList() {
		if (rfBleKey == null) {
			LogUtil.w("LockHelper", "rfBleKey is null");
			return;
		}
		// scan device list
		@SuppressWarnings("unchecked")
        ArrayList<BleDevContext> lst = rfBleKey.getDiscoveredDevices();
		scanDeviceList.clear();
		for (BleDevContext dev : lst) {
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(formatByteToMac(dev.mac));
			Lock lock = new Lock();
			lock.ble_mac = stringBuffer.toString().toUpperCase();
			lock.name = lock.ble_mac;
			scanDeviceList.add(lock);
		}
		LogUtil.i("LockHelper", "蓝牙设备扫描结束" + scanDeviceList.toString());
		getAuthDoorList();
	}

//	private void openDoorByWifi(final Lock lock) {
//		RequestParams params = RequestParamsBuilder.begin().addToken(true).add("appType", "1", true)
//				.add("device_wifi_mac", lock.device_real_wifi_mac, true).addUserInfo(true).end(true);
//		HttpUtil.post(Constant.OPEN_DOOR, params, new BusinessResponseHandler(mActivity, false) {
//			@Override
//			protected void onBusinessSuccess(JSONObject response, Object extra) {
//				super.onBusinessSuccess(response, extra);
//				JSONObject jo = response.optJSONObject("data");
//				Message msg = mHandler.obtainMessage(1);
//				msg.arg1 = 11;
//				if (jo != null && 0 == jo.optInt("result")) {
//					msg.arg1 = 10;
//				}
//				msg.obj = lock.ble_mac;
//				msg.sendToTarget();
//			}
//
//			@Override
//			protected void onBusinessFail(JSONObject response, Object extra) {
//				super.onBusinessFail(response, extra);
//				Message msg = mHandler.obtainMessage(1);
//				msg.arg1 = 11;
//				msg.obj = lock.ble_mac;
//				msg.sendToTarget();
//			}
//
//		});
//
//	}

	/**
	 * 蓝牙开门成功后
	 *
	 * @param ble_mac
	 */
//	private void uploadBleUnlock(String ble_mac) {
//
//		RequestParams params = RequestParamsBuilder.begin().addToken(true).add("appType", "1", true)
//				.add("device_blue_mac", ble_mac, true).addUserInfo(true).end(true);
//		HttpUtil.post(Constant.SUBMIT_ENTRANCE_RECORD, params, new BusinessResponseHandler(mActivity, false) {
//			@Override
//			protected void onBusinessSuccess(JSONObject response, Object extra) {
//				super.onBusinessSuccess(response, extra);
//
//				openDoorRedpaket();
//			}
//
//		});
//
//		// Map<String, Object> map = new HashMap<>();
//		// map.put("deviceBlueMac", ble_mac);
//		// AjaxParams params = new AjaxParams();
//		// params.put("params", RequestParamsUtil.getHttpParamsV2(map));
//		// new CommonHttp(mActivity, new HttpResultHandlerImpl() {
//		//
//		// @Override
//		// public void onBusinessSuccess(JSONObject resp, Bundle extras) {
//		//
//		// }
//		// }).configMethod(Method.POST).showDialog(false).showToast(false).sendRequest(Constant.UPLOAD_BLE_UNLOCK,
//		// params);
//	}

//	private void openDoorRedpaket() {
//
//		RequestParams params = RequestParamsBuilder.begin().addToken(true).addUserInfo(true).end(true);
//		HttpUtil.post(Constant.OPEN_DOOR_REDPAKET, params, new BusinessResponseHandler(mActivity, false) {
//			@Override
//			protected void onBusinessSuccess(JSONObject response, Object extra) {
//				super.onBusinessSuccess(response, extra);
//
//				JSONObject json = response.optJSONObject("data");
//				if (json != null) {
//					int type = json.optInt("type");
//
//					switch (type) {
//						case 0:
//							// 抢到红包
//							LockRewardDialog dialog = new LockRewardDialog(mActivity, json.optString("redpackage"), json
//									.optString("sponsor"), type, json.optString("sponsor_img"));
//							dialog.show();
//
//							break;
//						case 1:
//							// 红包派完了
//
//							break;
//						case 2:
//							// 抢过了
//
//							break;
//						case 3:
//							// 木有红包活动
//
//							break;
//
//						default:
//							break;
//					}
//
//				}
//
//			}
//
//		});
//	}

	/**
	 *
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

	private static byte[] stringToBytes(String outStr) {
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

	/**
	 * 判断服务是否在运行
	 *
	 * @param serviceClass
	 * @return
	 */
	private boolean isMyServiceRunning(Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) mActivity.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	public static class Lock {

		public String name;
		@SerializedName("device_blue_mac")
		public String ble_mac;
		@SerializedName("device_wifi_mac")
		public String wifi_mac;
		public String open_tag;// 1 可以打开，0不可以
		public boolean opening;// 正在执行开门操作
		public String device_real_wifi_mac;// wifi开门使用的mac
		public String device_key;
	}

//	public static class LockAdapter extends CommonAdapter<Lock> implements OnUnlockListener {
//
//		private PrepareUnlockListener mListener;
//
//		public LockAdapter(Context context, int layoutId, List<Lock> datas, PrepareUnlockListener listener) {
//			super(context, layoutId, datas);
//			mListener = listener;
//		}
//
//		@Override
//		public void convert(ViewHolder holder, final Lock t) {
//			SlideUnlockView suv_lock_item = holder.getView(R.id.suv_lock_item);
//			suv_lock_item.setLabelText(t.name);
//			suv_lock_item.setTag(t);
//			suv_lock_item.setOnUnlockListener(this);
//			ProgressBar pb_lock_opening = holder.getView(R.id.pb_lock_opening);
//			if (t.opening) {
//				pb_lock_opening.setVisibility(View.VISIBLE);
//			} else {
//				pb_lock_opening.setVisibility(View.GONE);
//				suv_lock_item.reset();
//			}
//		}
//
//		@Override
//		public void onUnlock(View view) {
//			if (mListener != null) {
//				Lock lock = (Lock) view.getTag();
//				mListener.unlock(lock);
//				lock.opening = true;
//				notifyDataSetChanged();
//			}
//		}
//
//	}

	public static class LockAdapter2 extends CommonAdapter<Lock> {

		private PrepareUnlockListener mListener;
		private Context mContext;

		public LockAdapter2(Context context, int layoutId, List<Lock> datas, PrepareUnlockListener listener) {
			super(context, layoutId, datas);
			mListener = listener;
			mContext = context;
		}

		@Override
		public void convert(ViewHolder holder, final Lock t) {

			holder.setText(R.id.item_name, t.name);
			ImageView item_image = holder.getView(R.id.item_image);
			item_image.setTag(t);
			item_image.setOnClickListener(mClick);

			ProgressBar pb_lock_opening = holder.getView(R.id.pb_lock_opening);
			if (t.opening) {
				pb_lock_opening.setVisibility(View.VISIBLE);
				item_image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.btn_clicked));
			} else {
				pb_lock_opening.setVisibility(View.INVISIBLE);
				item_image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.btn_not_clicked));
			}
		}

		private OnClickListener mClick = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mListener != null) {
					Lock lock = (Lock) v.getTag();
					mListener.unlock(lock);
					lock.opening = true;
					notifyDataSetChanged();
				}
			}
		};

	}

	/**
	 * 滑动监听
	 *
	 * @author sqq
	 *
	 */
	public interface PrepareUnlockListener {
		/**
		 * 执行开门操作
		 *
		 */
		void unlock(Lock lock);
	}
}
