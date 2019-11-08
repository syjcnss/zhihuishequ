package com.ovu.lido.bean;

import java.io.Serializable;
import java.util.List;

public class MyGroupBuyBean implements Serializable {


    /**
     * timestamp : 20180515145556
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : [{"original_price":10,"commodity_describe":"益达口香糖，多种口味，随机发货","amount":0.01,"gid":724,"create_time":"2018-04-16 09:29:24","g_p_name":"益达口香糖","bussiness_type":"3","count":1,"address_id":334,"g_p_price":0.01,"g_p_id":67,"order_code":"341804160929175451","community_id":34,"commodity_id":15,"advance_pay_amount":null,"pay_type":0,"id":724,"pay_id":"341804160929177338","resident_id":211249,"commodity_img":"http://120.27.196.188/community/info_img_path/img/201706/20170630111633_523.png","status":1,"commodity_name":"益达口香糖"}]
     * point : 0
     */

    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private Object hash;
    private Object token;
    private int point;
    private List<DataBean> data;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getHash() {
        return hash;
    }

    public void setHash(Object hash) {
        this.hash = hash;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * original_price : 10
         * commodity_describe : 益达口香糖，多种口味，随机发货
         * amount : 0.01
         * gid : 724
         * create_time : 2018-04-16 09:29:24
         * g_p_name : 益达口香糖
         * bussiness_type : 3
         * count : 1
         * address_id : 334
         * g_p_price : 0.01
         * g_p_id : 67
         * order_code : 341804160929175451
         * community_id : 34
         * commodity_id : 15
         * advance_pay_amount : null
         * pay_type : 0
         * id : 724
         * pay_id : 341804160929177338
         * resident_id : 211249
         * commodity_img : http://120.27.196.188/community/info_img_path/img/201706/20170630111633_523.png
         * status : 1
         * commodity_name : 益达口香糖
         */

        private int original_price;
        private String commodity_describe;
        private double amount;
        private int gid;
        private String create_time;
        private String g_p_name;
        private String bussiness_type;
        private int count;
        private int address_id;
        private double g_p_price;
        private int g_p_id;
        private String order_code;
        private int community_id;
        private int commodity_id;
        private Object advance_pay_amount;
        private int pay_type;
        private int id;
        private String pay_id;
        private int resident_id;
        private String commodity_img;
        private int status;
        private String commodity_name;

        public int getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(int original_price) {
            this.original_price = original_price;
        }

        public String getCommodity_describe() {
            return commodity_describe;
        }

        public void setCommodity_describe(String commodity_describe) {
            this.commodity_describe = commodity_describe;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getG_p_name() {
            return g_p_name;
        }

        public void setG_p_name(String g_p_name) {
            this.g_p_name = g_p_name;
        }

        public String getBussiness_type() {
            return bussiness_type;
        }

        public void setBussiness_type(String bussiness_type) {
            this.bussiness_type = bussiness_type;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public double getG_p_price() {
            return g_p_price;
        }

        public void setG_p_price(double g_p_price) {
            this.g_p_price = g_p_price;
        }

        public int getG_p_id() {
            return g_p_id;
        }

        public void setG_p_id(int g_p_id) {
            this.g_p_id = g_p_id;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public int getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(int community_id) {
            this.community_id = community_id;
        }

        public int getCommodity_id() {
            return commodity_id;
        }

        public void setCommodity_id(int commodity_id) {
            this.commodity_id = commodity_id;
        }

        public Object getAdvance_pay_amount() {
            return advance_pay_amount;
        }

        public void setAdvance_pay_amount(Object advance_pay_amount) {
            this.advance_pay_amount = advance_pay_amount;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPay_id() {
            return pay_id;
        }

        public void setPay_id(String pay_id) {
            this.pay_id = pay_id;
        }

        public int getResident_id() {
            return resident_id;
        }

        public void setResident_id(int resident_id) {
            this.resident_id = resident_id;
        }

        public String getCommodity_img() {
            return commodity_img;
        }

        public void setCommodity_img(String commodity_img) {
            this.commodity_img = commodity_img;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCommodity_name() {
            return commodity_name;
        }

        public void setCommodity_name(String commodity_name) {
            this.commodity_name = commodity_name;
        }
    }
}
