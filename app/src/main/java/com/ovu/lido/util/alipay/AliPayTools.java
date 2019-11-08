package com.ovu.lido.util.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.ovu.lido.bean.CommonBean;
import com.ovu.lido.eventbus.RefreshEvent;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.GsonUtil;
import com.ovu.lido.util.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Call;

/**
 * @author vondear
 */
public class AliPayTools {

    private static OnSuccessAndErrorListener sOnSuccessAndErrorListener;

    // 商户PID
    public static final String APPID = "2088221705661473";
    // 商户收款账号
    public static final String SELLER = "ovu-ldwy@ovuni.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMDZcYYS44P3WbSdx/ncbxmCxMJvTBcdfwrIkMRMb4YjybAg6engGWwVPRZUFUmrhrX0J9m6N/W3NOnl5A0TfP1hakF4nI5BEwMudwKSnrBSbN30Z6e917xA7REI+I8Vm12cANGAWDIay7LwqANXt+YYg6FCnTfePHyizPrqvZUVAgMBAAECgYACLvQQFoqo5dYIK3kmJQ7E9SwRH313DYhwsNCiyFLC7AjOi7DaSwU+qgblB9Kt4NlHmhoRZwBXMdnRhB7O3xedKb127XTZ40fLfbYF0Y124pSv4pxdw6OJgPYmbsv+Qq79Hha7/c5X+wvs/yWRJgDfyaKsz1fNJsgPeYcvhy8IwQJBAPQbf9PuBktARAovYCBU4OXCPI6nKhbwibC1Y6q0kppf+Hkp7zFoxze/MsOimALrMDY28+DLKgOhfPMsxpA/CnkCQQDKPqfLZk3z3NSz+SI/eUVnSz73QsbLBotnoBSLqQ4wRzGzy9DfCQWpzxLIFTAjHCK7f5XqPCXNxE/fAoOUQzh9AkAKV/P1rftUSvfXGqTPGemhsrZQMSMmb7DV7Qm5HQetRO3JbI53IIJ4iUCEa6pXTVHwqilxWAqCtuANidMsH4+5AkEAhkuApEG0taAH5dIi+b8PZP3EO/AtjRc1boQP4IVLlKdRy7AlbFTTW+TS9eWL52SBc7vO0pFKEQ6wcsd6k/LnCQJAWioGFP+tFmPlgsiN7oQuVwWcXj+42+EeyHM/CRT2+lJDXEHf20zO4hibYKBRUITNcDwqsJaksPxZOz35UmdNhQ==";

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    Logger.i("支付宝结果","获取到的值3"+resultStatus);
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。

                        sOnSuccessAndErrorListener.onSuccess(resultStatus);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        sOnSuccessAndErrorListener.onError(resultStatus);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 支付宝支付业务
     *
     * @param out_trade_no 订单号
     * @param name         商品的标题
     * @param detail       商品的详情
     * @param money        商品金额
     * @param listener     支付结果回掉监听
     */
    public static void aliPay(final Activity activity, String out_trade_no, String name, String detail, String money, OnSuccessAndErrorListener listener) {
        sOnSuccessAndErrorListener = listener;
        // 订单
        final String orderInfo = getOrderInfo(out_trade_no, name, detail, money);
        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, false);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                Logger.i("支付宝结果", "获取到的值2" + msg);
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * create the order info. 创建订单信息
     */
    private static String getOrderInfo(String out_trade_no, String name, String detail, String money) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + APPID + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + out_trade_no + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + name + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + detail + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + money + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + Constant.ALIPAY_NOTIFY_URL + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private static String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private static String getSignType() {
        return "sign_type=\"RSA\"";
    }



}
