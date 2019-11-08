package com.ovu.lido.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.ovu.lido.R;
import com.ovu.lido.base.BaseActivity;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.ViewHelper;
import com.ovu.lido.widgets.LoadProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class CommonWebActivity extends BaseActivity {
    @BindView(R.id.action_bar_rl)
    RelativeLayout action_bar_rl;
    @BindView(R.id.top_title)
    TextView top_title;
    @BindView(R.id.web_view)
    WebView webView;
    private Dialog mDialog;

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true, 0.2f)
                .titleBar(action_bar_rl).init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_common_web;
    }

    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);

        mDialog = LoadProgressDialog.createLoadingDialog(mContext);
        WebSettings webSettings = webView.getSettings();
        // 支持javascript
        webSettings.setJavaScriptEnabled(true);//启用js(打开交互权限)
        webSettings.setBlockNetworkImage(false);//解决图片不显示
        /**
         * android 从Lollipop（5.0）开始webview 默认不允许混合模式，https中不能加载http资源，需要设置开启
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(false);
        // 扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        // 自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebViewClient(mWebViewClientBase);
        webView.addJavascriptInterface(new JsonObject(), "android");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.d(TAG, "onJsAlert:" + message);
                return super.onJsAlert(view, url, message, result);
            }
        });
    }

    private class JsonObject {
        /**
         * 跳转至app收银台的函数
         *
         * @param params: ○ businessType 业务类型
         *                ○ amount 订单金额
         *                ○ orderId 订单id
         */
        @JavascriptInterface
        public void appCheckoutCounter(String params) {
            try {
                JSONObject js = new JSONObject(params);
                String businessType = js.optString("businessType");
                double amount = js.optDouble("amount");
                String orderId = js.optString("orderId");
                Map<String, Object> map = new HashMap<>();
                map.put("order_id", orderId);
                Log.i(TAG, "appCheckoutCounter: " + "businessType:" + businessType + "\t amount:" + amount + "\t orderId:" + orderId + "\t map:" + map.toString());
                startActivity(new Intent(mContext, PaymentMethodActivity.class)
                        .putExtra("business_type", businessType)
                        .putExtra("needPayAmount", ViewHelper.getDisplayPrice2(amount))
                        .putExtra("map", (Serializable) map));

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }

    @Override
    protected void loadData() {
        super.loadData();
        String title = getIntent().getStringExtra("title");
        String url = getIntent().getStringExtra("url");
        top_title.setText(title);
        webView.loadUrl(url);
    }


    private WebViewClientBase mWebViewClientBase = new WebViewClientBase();

    private class WebViewClientBase extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            mDialog.show();
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("ansen", "拦截url:" + url);
            if (url.equals("http://www.google.com/")) {
                Toast.makeText(mContext, "国内不能访问google,拦截该url", Toast.LENGTH_LONG).show();
                return true;//表示我已经处理过了
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            if (mContext == null || isFinishing()) {
                return;
            }
            LoadProgressDialog.closeDialog(mDialog);
            super.onPageFinished(view, url);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent.JDPayResult jdPayResult) {
        int resultCode = jdPayResult.getResultCode();//0、未执行支付，直接返回    1、支付完成返回
        switch (resultCode) {
            case 0:
                onBackPressed();
                break;
            case 1:
                webView.loadUrl("javascript:payCallback()");
                break;
            case 2:
                finish();
                break;
        }


    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.back_iv)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_iv:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
