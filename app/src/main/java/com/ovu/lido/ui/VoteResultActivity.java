package com.ovu.lido.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.util.Constant;
import com.ovu.lido.widgets.LoadProgressDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 投票结果
 */
public class VoteResultActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.tp_success_layout)
    LinearLayout tp_success_layout;
    @BindView(R.id.tp_webview)
    WebView tp_webview;
    private Dialog mDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_vote_result;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        super.initView();
        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
        String vote_id = getIntent().getStringExtra("vote_id");
        String type = getIntent().getStringExtra("type");
        tp_success_layout.setVisibility(TextUtils.equals(type, "02") ? View.GONE : View.VISIBLE);

        String url = Constant.VOTE_RESULT_URL + vote_id;
        Log.i(TAG, "url: " + url);
        tp_webview.getSettings().setLoadsImagesAutomatically(true);
        tp_webview.getSettings().setBuiltInZoomControls(true);
        tp_webview.getSettings().setJavaScriptEnabled(true);
        tp_webview.getSettings().setDefaultTextEncodingName("utf-8");

        tp_webview.loadUrl(url);
        tp_webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) {
                    return false;
                }
                mDialog.show();
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LoadProgressDialog.closeDialog(mDialog);
            }
        });
    }

    @OnClick(R.id.back_iv)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_iv:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (tp_webview != null) {
            tp_webview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            tp_webview.clearHistory();

            ((ViewGroup) tp_webview.getParent()).removeView(tp_webview);
            tp_webview.destroy();
            tp_webview = null;
        }
        super.onDestroy();
    }
}
