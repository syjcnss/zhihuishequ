package com.ovu.lido.eventbus;

/**
 * Created by Administrator on 2017/11/13.
 */

public class RefreshEvent {
    private final int pos;


    public RefreshEvent(int i) {
        this.pos = i;
    }

    public int getPos() {
        return pos;
    }

    public static final class WXPayResult{
        public int errorCode;

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }
    }

    public static final class JDPayResult {
        public int resultCode;//0、未支付返回  1、支付结果返回上一级  2、支付结果返回首页

        public int getResultCode() {
            return resultCode;
        }

        public void setResultCode(int resultCode) {
            this.resultCode = resultCode;
        }
    }

}
