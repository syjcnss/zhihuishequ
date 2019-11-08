package com.ovu.lido.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.wechat.WXConstants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private static final String TAG = "wangw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WXConstants.APP_ID);
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {
        Log.i(TAG, "onReq");
    }

    @Override
    public void onResp(BaseResp resp) {


        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Log.i(TAG, resp.errStr + ";code=" + String.valueOf(resp.errCode));

            if (resp.errCode == 0) {
                // 支付成功
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        RefreshEvent.WXPayResult event = new RefreshEvent.WXPayResult();
                        event.setErrorCode(0);
                        EventBus.getDefault().post(event);
                    }
                },500);
            } else if (resp.errCode == -2) {
                // -2用户取消
                Log.i(TAG, "用户取消支付");
            } else {
                // 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
                String msg = "errCode=" + resp.errCode + " & errStr=" + resp.errStr;
                Toast.makeText(this, "调用微信支付失败" + msg, Toast.LENGTH_SHORT).show();
                ;
            }



            finish();

        }
    }

}
