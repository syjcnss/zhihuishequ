package com.ovu.lido;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.fragment.MallFragment;
import com.ovu.lido.fragment.HomeFragment;
import com.ovu.lido.fragment.MyFragment;
import com.ovu.lido.fragment.NeighborFragment;
import com.ovu.lido.fragment.StoreFragment;
import com.ovu.lido.ui.NewsReportRepairActivity;
import com.ovu.lido.ui.SatisfactionSurveyActivity;
import com.ovu.lido.util.AppPreference;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.ExampleUtil;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.UpdateManager;
import com.ovu.lido.widgets.ConfirmDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;

    private HomeFragment mHomeFragment;
    private MallFragment mMallFragment;
    private NeighborFragment mNeighborFragment;
    private MyFragment mMyFragment;
    private FragmentManager fragmentManager;
    private int tab_index = -1;

    public static boolean isForeground = false;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    Toast.makeText(mContext,showMsg.toString(),Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e){
            }
        }
    }

    private void addShortcut() {
//        if (Build.VERSION.SDK_INT >= 25) {
//            List<ShortcutInfo> infos = new ArrayList<>();
//            // 按下返回按钮跳转的activity
//            Intent intent1 = new Intent(this, MainActivity.class);
//            intent1.setAction(Intent.ACTION_VIEW);
//
//            // 目标activity
//            Intent intent2 = new Intent(this, NewsReportRepairActivity.class);
//            intent2.setAction("android.media.action.IMAGE_CAPTURE");
//
//            Intent[] intents = new Intent[2];
//            intents[0] = intent1;
//            intents[1] = intent2;
//
//            ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(this, "publish-2")
//                    .setShortLabel("智能开门")
//                    .setLongLabel("智能开门")
//                    .setIcon(Icon.createWithResource(this, R.drawable.zhinengkaimen))
//                    .setIntents(intents)
//                    .build();
//            infos.add(shortcutInfo);
//
//            ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
//            //这样就可以通过长按图标显示出快捷方式了
//            shortcutManager.setDynamicShortcuts(infos);
//
//        }
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        registerMessageReceiver();  // used for receive msg
        fragmentManager = getSupportFragmentManager();
        List<Fragment> historyFragments = fragmentManager.getFragments();
        if (historyFragments != null) {
            FragmentTransaction historyTransaction = fragmentManager.beginTransaction();
            Log.i(TAG, "historyFragments size is:" + historyFragments.size());
            for (Fragment f : historyFragments) {
                if (f != null && f.isAdded()) {
                    historyTransaction.hide(f);
                }
            }
            historyTransaction.commitAllowingStateLoss();
        }
        setTab(0);
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }



    @Override
    protected void setListener() {
        super.setListener();
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.home_rb:
                setTab(0);
                break;
            case R.id.mall_rb:
                setTab(1);
                break;
            case R.id.neighbor_rb:
                setTab(2);
                break;
            case R.id.my_rb:
                setTab(3);
                break;
        }
    }


    /**
     * 切换到对应的tab
     *
     * @param index 0-4
     */
    public void setTab(int index) {
        if (index == tab_index) {
            Log.i(TAG, "点击当前");
            return;
        }
        tab_index = index;
        for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) mRadioGroup.getChildAt(i);
            radioButton.setChecked(i == index);
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (mHomeFragment != null) {
            fragmentTransaction.hide(mHomeFragment);
        }
        if (mMallFragment != null) {
            fragmentTransaction.hide(mMallFragment);
        }
        if (mNeighborFragment != null) {
            fragmentTransaction.hide(mNeighborFragment);
        }
        if (mMyFragment != null) {
            fragmentTransaction.hide(mMyFragment);
        }
        if (0 == index) {
            if (mHomeFragment == null) {
                mHomeFragment = new HomeFragment();
                fragmentTransaction.add(R.id.frame_layout, mHomeFragment);
            } else {
                // 主页已经加载
            }
            fragmentTransaction.show(mHomeFragment);
        } else if (1 == index) {
            // 商城
            if (mMallFragment == null) {
                mMallFragment = new MallFragment();
                fragmentTransaction.add(R.id.frame_layout, mMallFragment);
            } else {
                // 主页已经加载

            }
            fragmentTransaction.show(mMallFragment);
        } else if (2 == index) {
            // 邻里
            if (mNeighborFragment == null) {
                mNeighborFragment = new NeighborFragment();
                fragmentTransaction.add(R.id.frame_layout, mNeighborFragment);
            } else {
                // 主页已经加载
//                mNeighborFragment.refreshData();
            }
            fragmentTransaction.show(mNeighborFragment);
        } else {
            // 我的
            if (mMyFragment == null) {
                mMyFragment = new MyFragment();
                fragmentTransaction.add(R.id.frame_layout, mMyFragment);
            } else {
//                mMyFragment.getData();
            }
            fragmentTransaction.show(mMyFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        //判断用户是否点击了“返回键”
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            //与上次点击返回键时刻作差
//            if ((System.currentTimeMillis() - mExitTime) > 2000) {
//                //大于2000ms则认为是误操作，使用Toast进行提示
//                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//                //并记录下本次点击“返回键”的时刻，以便下次进行判断
//                mExitTime = System.currentTimeMillis();
//            } else {
//                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
//                System.exit(0);
//            }
//            return true;
//        }
        //返回键返回桌面，不退出应用
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void loadData() {

    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new UpdateManager(mContext).getUpdateMsg();
        } else {
            requestPermission(Constant.STORAGE_PERMISSION, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case Constant.STORAGE_PERMISSION:
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // 权限请求成功
                        new UpdateManager(mContext).getUpdateMsg();
                    } else {
                        Logger.e(TAG, permissions.toString());
                        // 权限请求失败
                        if (shouldShowRequestPermissionRationale(permissions[0])) {// 权限申请失败，判断是否需要弹窗解释原因
                            ConfirmDialog dialog = new ConfirmDialog(this, new ConfirmDialog.OnConfirmEvent() {
                                @Override
                                public void onCancel() {
                                }

                                @Override
                                public void onConfirm() {
                                    requestPermission(Constant.STORAGE_PERMISSION, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                }
                            });
                            dialog.show();
                            dialog.setContentText("存储空间：缓存数据/应用配置等文件");
                            dialog.setTitleText("\"i丽岛\"需要获得以下权限，才可正常使用");
                            dialog.setOkText("下一步");
                            dialog.setCancelVisible(View.GONE);
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);
                        } else {
                            ConfirmDialog dialog = new ConfirmDialog(this, new ConfirmDialog.OnConfirmEvent() {
                                @Override
                                public void onCancel() {
                                    finish();
                                }

                                @Override
                                public void onConfirm() {
                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                                    intent.setData(uri);
                                    //跳转到设置界面调用的是 startActivityForResult()
                                    startActivityForResult(intent, Constant.STORAGE_PERMISSION);
                                }

                            });
                            dialog.show();
                            dialog.setContentText("获取权限后\n应用不会读取您的个人信息\n请在 权限管理 中设置开启");
                            dialog.setTitleText("\"i丽岛\"需要获得以下权限，才可正常使用");
                            dialog.setOkText("前往设置");
                            dialog.setCancelText("退出i丽岛");
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);
                        }
                    }
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         *  从 权限设置 返回：
         * 1、没有必要对 resultCode 进行判断，因为用户只能通过返回键才能回到我们的 App 中，所以 resultCode 总是为 RESULT_CANCEL
         * 2、还需要对权限进行判断，因为用户有可能没有授权就返回了！
         */
        switch (requestCode) {
            case Constant.STORAGE_PERMISSION:
                if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    new UpdateManager(mContext).getUpdateMsg();

                } else {
                    requestPermission(Constant.STORAGE_PERMISSION, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }
}
