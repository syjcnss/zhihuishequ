package com.ovu.lido.util;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ovu.lido.R;
import com.ovu.lido.widgets.ConfirmDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateManager {
    // 外存sdcard存放路径
    private static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/" + "LidoApk" + "/";
    // 下载应用存放全路径
    private static final String FILE_NAME = "Lido.apk";
    // 准备安装新版本应用标记
    private static final int INSTALL_TOKEN = 1;
    //Log日志打印标签
    private static final String TAG = "Update_log";

    private Context context;
    //获取版本数据的地址
    private String version_path = Constant.CHECK_VERSION_UPDATE;
    //获取新版APK的默认地址
    private String apk_path = "http://s.whlido.com/img/ilidao_V3.0_20190328.apk";
    // 下载应用的进度条
    private ProgressDialog progressDialog;
    //新版本号和描述语言
    private int update_versionCode;//更新版本号
    private String update_describe;//更新描述
    private int force_update;//1：强制更新
    private String updata_title;//更新标题
    private boolean isShowToast = false;//是否显示提示内容，默认不显示

    public UpdateManager(Context context) {
        this.context = context;
    }

    /**
     * 获取当前版本号
     */
    private int getCurrentVersion() {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);

            Logger.e(TAG, "当前版本名和版本号" + info.versionName + "--" + info.versionCode);

            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();

            Logger.e(TAG, "获取当前版本号出错");
            return 0;
        }
    }


    public UpdateManager setShowToast(boolean isShow) {
        isShowToast = isShow;
        return this;
    }

    /**
     * 从服务器获得更新信息
     */
    public void getUpdateMsg() {
        class mAsyncTask extends AsyncTask<String, Integer, String> {
            @Override
            protected String doInBackground(String... params) {

                HttpURLConnection connection = null;
                try {
                    URL url_version = new URL(params[0]);
                    connection = (HttpURLConnection) url_version.openConnection();
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    InputStream in = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                    Log.e(TAG, "bufferReader读到的数据--" + reader);

                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    return response.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                return null;
            }


            @Override
            protected void onPostExecute(String s) {             //回到主线程，更新UI

                Log.e(TAG, "异步消息处理反馈--" + s);
                if (TextUtils.isEmpty(s)) return;
                try {
                    JSONObject object = new JSONObject(s);
                    if (TextUtils.equals(object.optString("errorCode"), ErrorCode.RESPONSE_CODE_OK)) {

                        JSONObject updateObj = object.optJSONObject("app_list").optJSONObject("1_1");
                        update_versionCode = updateObj.optInt("version_code");
                        apk_path = updateObj.optString("app_url");
                        update_describe = updateObj.optString("content");
                        force_update = updateObj.optInt("force_update");

                        String version_name = context.getString(R.string.app_name) + updateObj.optString("version_name");
                        if (force_update == 1) {//强制更新
                            updata_title = String.format(context.getResources().getString(R.string.force_update_title), version_name);
                        } else {
                            updata_title = version_name + context.getResources().getString(R.string.update_title);
                        }

                        Logger.e(TAG, "新版本号--" + update_versionCode);
                        Logger.e(TAG, "新版本描述--\n" + update_describe);

                        if (update_versionCode > getCurrentVersion()) {

                            Log.e(TAG, "提示更新！");
                            showNoticeDialog();
                        } else {
                            Log.e(TAG, "已是最新版本！");
                            if (isShowToast) {
                                Toast.makeText(context,"当前已是最新版本",Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(context, object.optString("errorMsg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        new mAsyncTask().execute(version_path);

    }


    /**
     * 显示提示更新对话框
     */
    private void showNoticeDialog() {

        ConfirmDialog dialog = new ConfirmDialog(context, new ConfirmDialog.OnConfirmEvent() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onConfirm() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (context.getPackageManager().canRequestPackageInstalls()){
                        showDownloadDialog();
                    }else {
                        ConfirmDialog confirmDialog = new ConfirmDialog(context, new ConfirmDialog.OnConfirmEvent() {
                            @Override
                            public void onCancel() {

                            }

                            @Override
                            public void onConfirm() {
                                Uri packageURI = Uri.parse("package:" + context.getPackageName());
                                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                                context.startActivity(intent);
                            }
                        });

                        confirmDialog.show();
                        confirmDialog.setContentText("需要打开允许来自此来源，请去设置中开启此权限");
                        confirmDialog.setTitleText("安装权限");
                        confirmDialog.setCancelVisible(View.GONE);
                        confirmDialog.setOkText("设置");
                        confirmDialog.setCancelVisible(force_update == 1 ? View.GONE : View.VISIBLE);
                        confirmDialog.setCancelable(false);
                        confirmDialog.setCanceledOnTouchOutside(false);

                    }

                }else {
                    showDownloadDialog();
                }

            }
        });
        dialog.show();
        dialog.setContentText(update_describe);
        dialog.setTitleText(updata_title);
        dialog.setCancelText("取消");
        dialog.setOkText("立即更新");
        dialog.setCancelVisible(force_update == 1 ? View.GONE : View.VISIBLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }


    /**
     * 显示下载进度对话框
     */
    public void showDownloadDialog() {


        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("正在下载...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);

        new downloadAsyncTask().execute();

    }


    /**
     * 下载新版本应用
     */
    private class downloadAsyncTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected void onPreExecute() {

            Log.e(TAG, "执行至--onPreExecute");
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {

            Log.e(TAG, "执行至--doInBackground");

            URL url;
            HttpURLConnection connection = null;
            InputStream in = null;
            FileOutputStream out = null;
            try {
                url = new URL(apk_path);
                connection = (HttpURLConnection) url.openConnection();

                in = connection.getInputStream();
                long fileLength = connection.getContentLength();
                File file_path = new File(FILE_PATH);
                if (!file_path.exists()) {
                    file_path.mkdir();
                }

                out = new FileOutputStream(new File(file_path, FILE_NAME));//为指定的文件路径创建文件输出流
                byte[] buffer = new byte[1024 * 1024];
                int len = 0;
                long readLength = 0;

                Log.e(TAG, "执行至--readLength = 0");

                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);//从buffer的第0位开始读取len长度的字节到输出流
                    readLength += len;
                    int curProgress = (int) (((float) readLength / fileLength) * 100);

                    Log.e(TAG, "当前下载进度：" + curProgress);
                    publishProgress(curProgress);

                    if (readLength >= fileLength) {
                        Log.e(TAG, "执行至--readLength >= fileLength");
                        break;
                    }
                }
                out.flush();
                return INSTALL_TOKEN;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            Log.e(TAG, "异步更新进度接收到的值：" + values[0]);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {

            progressDialog.dismiss();//关闭进度条

            // 延时发送，避免出现解析包错误的bug
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //安装应用
                    installApp();

                }
            }, 1000);
        }
    }

    private Handler handler = new Handler();

    /**
     * 安装新版本应用
     */
    private void installApp() {
        /**
         * 注意：
         * appFile的路径后半段要与AndroidManifest中注册的resource保持一致
         */
        File appFile = new File(FILE_PATH, FILE_NAME);
        if (!appFile.exists()) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//android7.0及以上
            File file = new File(appFile.toString());
            /**
             * context ：上下文
             * authority ：文件提供者  （注意：要与AndroidManifest中注册的authorities保持一致）
             * file：文件
             */
            Uri uriForFile = FileProvider.getUriForFile(context, "com.ovu.lido.fileProvider", file);
            // 跳转到新版本应用安装页面
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Logger.e(TAG, "安装路径" + uriForFile);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
            context.startActivity(intent);
        } else {//android7.0以下，采用普通的方式就可以了
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse("file://" + appFile.toString()), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
