package com.ovu.lido.util.wechat;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ovu.lido.bean.WechatModel;
import com.ovu.lido.util.Constant;
import com.ovu.lido.util.Logger;
import com.ovu.lido.util.alipay.OnSuccessAndErrorListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author Vondear
 * @date 2017/4/17
 */

public class WechatPayTools {
    private static final String TAG = "wangw";

    /**
     * 商户发起生成预付单请求
     *
     * @return
     */
    public static String wechatPayUnifyOrder(final Context mContext, WechatModel wechatModel, final OnSuccessAndErrorListener onSuccessAndErrorListener) {
        String nonce_str = getRandomStringByLength(8);//随机码
        String body = wechatModel.getDetail();//商品描述
        String out_trade_no = wechatModel.getOut_trade_no();//商品订单号
        String total_fee = wechatModel.getMoney();//总金额 分
        String time_start = getCurrTime();//交易起始时间(订单生成时间非必须)
        String trade_type = "APP";//App支付
        String notify_url = Constant.WECHAT_NOTIFY_URL;//"http://" + "域名" + "/" + "项目名" + "回调地址.do";//回调函数

        SortedMap<String, String> params = new TreeMap<String, String>();
        params.put("appid", WXConstants.APP_ID);//应用APPID
        params.put("mch_id", WXConstants.MCH_ID);//商户号
        params.put("device_info", "WEB"); //设备号
        params.put("nonce_str", nonce_str);//随机字符串，不长于32位
        params.put("body", body);//商品描述
        params.put("out_trade_no", out_trade_no);//商户系统内部订单号
        params.put("total_fee", total_fee);//订单总金额，单位为分
        params.put("time_start", time_start);//订单生成时间，格式为yyyyMMddHHmmss
        params.put("notify_url", notify_url);//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
        params.put("trade_type", trade_type);//支付类型
        String sign = "";//签名(该签名本应使用微信商户平台的API证书中的密匙key,但此处使用的是微信公众号的密匙APP_SECRET)
        sign = getSign(params, WXConstants.API_KEY);
        //参数xml化
        String xmlParams = parseString2Xml(params, sign);
        Log.i(TAG, "xmlParams: " + xmlParams);
        //判断返回码
        final String[] jsonStr = {""};

        //商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易会话标识后再在APP里面调起支付
        OkGo.<String>post(WXConstants.WX_TOTAL_ORDER)
                .upString(xmlParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String s = response.body();
                        Log.d("微信统一下单", s);
                        jsonStr[0] = s;

                        Map<String, String> mapXml = null;
                        try {
                            mapXml = getMapFromXML(s);
                        } catch (ParserConfigurationException | IOException | SAXException e) {
                            e.printStackTrace();
                        }
                        Logger.i(TAG, "mapXml: " + mapXml);

                        String time = getCurrTime();

                        SortedMap<String, String> params = new TreeMap<String, String>();
                        params.put("noncestr", mapXml.get("nonce_str"));
                        params.put("sign", mapXml.get("sign"));
                        params.put("prepayid", mapXml.get("prepay_id"));
                        params.put("timestamp", time);
                        Logger.i(TAG, "params-onSuccess: " + params.toString());

//                        wechatPayApp(mContext, appid, mch_id, wx_private_key, params, OnSuccessAndErrorListener);
                        doWXPay(mContext,params,onSuccessAndErrorListener);
                    }
                });

        if (!jsonStr[0].contains("FAIL") && jsonStr[0].trim().length() > 0) {//成功
            return "success";
        } else {//失败
            return "fail";
        }
    }

    public static String parseString2Xml(Map<String, String> map, String sign) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = map.entrySet();
        Iterator iterator = es.iterator();

        while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            sb.append("<" + k + ">" + v + "</" + k + ">");
        }

        sb.append("<sign>" + sign + "</sign>");
        sb.append("</xml>");
        return sb.toString();
    }

    public static String getSign(SortedMap<String, String> params, String wx_private_key) {
        String sign = null;
        StringBuffer sb = new StringBuffer();
        Set es = params.entrySet();
        Iterator iterator = es.iterator();

        while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }

        sb.append("key=" + wx_private_key);
        sign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return sign;
    }

    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }

        return sb.toString();
    }

    public static String getCurrTime() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = outFormat.format(now);
        return s;
    }

    public static Map<String, String> getMapFromXML(String xmlString) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is = new ByteArrayInputStream(xmlString.getBytes());
        Document document = builder.parse(is);
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Map<String, String> map = new HashMap();

        for(int i = 0; i < allNodes.getLength(); ++i) {
            Node node = allNodes.item(i);
            if (node instanceof Element) {
                map.put(node.getNodeName(), node.getTextContent());
            }
        }

        return map;
    }

    public static void doWXPay(final Context mContext, SortedMap<String, String> pay_param, final OnSuccessAndErrorListener onRxHttpString) {
        WechatPay.init(mContext);
        WechatPay.getInstance().doPay(pay_param, new WechatPay.WXPayResultCallBack() {
            public void onSuccess() {
                onRxHttpString.onSuccess("微信支付成功");
            }

            public void onError(int error_code) {
                switch(error_code) {
                    case 1:
                        Toast.makeText(mContext,"未安装微信或微信版本过低", Toast.LENGTH_SHORT).show();
                        onRxHttpString.onError("未安装微信或微信版本过低");
                        break;
                    case 2:
                        Toast.makeText(mContext,"参数错误", Toast.LENGTH_SHORT).show();
                        onRxHttpString.onError("参数错误");
                        break;
                    case 3:
                        Toast.makeText(mContext,"支付失败", Toast.LENGTH_SHORT).show();
                        onRxHttpString.onError("支付失败");
                }

            }

            public void onCancel() {
                onRxHttpString.onError("支付取消");
            }
        });
    }
}
