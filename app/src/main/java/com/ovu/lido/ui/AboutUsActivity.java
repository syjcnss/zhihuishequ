package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.bean.VersionBean;
import com.ovu.lido.util.AppUtil;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.HttpAddress;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.UpdateManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 关于我们
 */
public class AboutUsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.back_iv)
    ImageView iv_back;
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.tv_version)
    TextView tv_version;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.titleBar(action_bar_rl).statusBarDarkFont(true, 0.2f).init();
    }

    @Override
    protected void initView() {
        tv_version.setText("i丽岛V" + AppUtils.getAppVersionName());
    }

    @OnClick({R.id.back_iv,R.id.ll_aggrement, R.id.ll_update, R.id.ll_menu_intro})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.ll_aggrement:
                startActivity(new Intent(AboutUsActivity.this, AgreementActivity.class));
                break;
            case R.id.ll_update:
                new UpdateManager(mContext).setShowToast(true).getUpdateMsg();
                break;
            case R.id.ll_menu_intro:
                startActivity(new Intent(AboutUsActivity.this, FunctionActivity.class));
                break;
        }
    }

}
