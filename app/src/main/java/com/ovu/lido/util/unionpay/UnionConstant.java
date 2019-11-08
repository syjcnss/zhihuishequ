package com.ovu.lido.util.unionpay;

public class UnionConstant {
//    public static final String APPID = "0027000186";//测试环境
    public static final String APPID = "0027630273";//生产环境
//    public static final String H5Url = "http://121.15.180.66:801/netpayment/BaseHttp.dll?H5PayJsonSDK";//测试环境
    public static final String H5Url = "https://netpay.cmbchina.com/netpayment/BaseHttp.dll?H5PayJsonSDK ";//生产环境
//    public static final String CMBJumpUrl = "cmbmobilebank://CMBLS/FunctionJump?action=gofuncid&funcid=200013" +
//            "&serverid=CMBEUserPay&requesttype=post&cmb_app_trans_parms_start=here";//测试环境
    public static final String CMBJumpUrl = "cmbmobilebank://CMBLS/FunctionJump?action=gofuncid&funcid=200013" +
            "&serverid=CMBEUserPay&requesttype=post&cmb_app_trans_parms_start=here";//生产环境
}
