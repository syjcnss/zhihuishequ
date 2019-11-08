package com.ovu.lido.bean;

public class MyGroupBuyDetailBean {


    /**
     * timestamp : 20180517174723
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : {"ADDRESS_ID":369,"g_p_name":"益达口香糖","count":1,"original_price":10,"order_code":"341804160929175451","commodity_name":"益达口香糖","amount":0.01,"id":724,"RESIDENT_ID":211249,"ADDRESS_DETAIL":"jjjjjjj","pay_type":0,"g_p_price":0.01,"pay_id":"341804160929177338","ADDRESS_REMARK":null,"g_p_id":67,"IS_DEFAULT":"0","ZONE_CODE1":"admin","status":1,"ZONE_CODE3":null,"bussiness_type":"3","ZONE_CODE2":null,"community_id":34,"commodity_id":15,"SHIPPER":"admin","MOBILE_NO":"17786453075","advance_pay_amount":null,"create_time":"2018-04-16 09:29:24","commodity_img":"http://120.27.196.188/community/info_img_path/img/201706/20170630111633_523.png","commodity_describe":"益达口香糖，多种口味，随机发货"}
     * point : 0
     */

    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private Object hash;
    private Object token;
    private DataBean data;
    private int point;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public static class DataBean {
        /**
         * ADDRESS_ID : 369
         * g_p_name : 益达口香糖
         * count : 1
         * original_price : 10
         * order_code : 341804160929175451
         * commodity_name : 益达口香糖
         * amount : 0.01
         * id : 724
         * RESIDENT_ID : 211249
         * ADDRESS_DETAIL : jjjjjjj
         * pay_type : 0
         * g_p_price : 0.01
         * pay_id : 341804160929177338
         * ADDRESS_REMARK : null
         * g_p_id : 67
         * IS_DEFAULT : 0
         * ZONE_CODE1 : admin
         * status : 1
         * ZONE_CODE3 : null
         * bussiness_type : 3
         * ZONE_CODE2 : null
         * community_id : 34
         * commodity_id : 15
         * SHIPPER : admin
         * MOBILE_NO : 17786453075
         * advance_pay_amount : null
         * create_time : 2018-04-16 09:29:24
         * commodity_img : http://120.27.196.188/community/info_img_path/img/201706/20170630111633_523.png
         * commodity_describe : 益达口香糖，多种口味，随机发货
         */

        private int ADDRESS_ID;
        private String g_p_name;
        private int count;
        private int original_price;
        private String order_code;
        private String commodity_name;
        private double amount;
        private int id;
        private int RESIDENT_ID;
        private String ADDRESS_DETAIL;
        private int pay_type;
        private double g_p_price;
        private String pay_id;
        private Object ADDRESS_REMARK;
        private int g_p_id;
        private String IS_DEFAULT;
        private String ZONE_CODE1;
        private int status;
        private Object ZONE_CODE3;
        private String bussiness_type;
        private Object ZONE_CODE2;
        private int community_id;
        private int commodity_id;
        private String SHIPPER;
        private String MOBILE_NO;
        private Object advance_pay_amount;
        private String create_time;
        private String commodity_img;
        private String commodity_describe;

        public int getADDRESS_ID() {
            return ADDRESS_ID;
        }

        public void setADDRESS_ID(int ADDRESS_ID) {
            this.ADDRESS_ID = ADDRESS_ID;
        }

        public String getG_p_name() {
            return g_p_name;
        }

        public void setG_p_name(String g_p_name) {
            this.g_p_name = g_p_name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(int original_price) {
            this.original_price = original_price;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getCommodity_name() {
            return commodity_name;
        }

        public void setCommodity_name(String commodity_name) {
            this.commodity_name = commodity_name;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRESIDENT_ID() {
            return RESIDENT_ID;
        }

        public void setRESIDENT_ID(int RESIDENT_ID) {
            this.RESIDENT_ID = RESIDENT_ID;
        }

        public String getADDRESS_DETAIL() {
            return ADDRESS_DETAIL;
        }

        public void setADDRESS_DETAIL(String ADDRESS_DETAIL) {
            this.ADDRESS_DETAIL = ADDRESS_DETAIL;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public double getG_p_price() {
            return g_p_price;
        }

        public void setG_p_price(double g_p_price) {
            this.g_p_price = g_p_price;
        }

        public String getPay_id() {
            return pay_id;
        }

        public void setPay_id(String pay_id) {
            this.pay_id = pay_id;
        }

        public Object getADDRESS_REMARK() {
            return ADDRESS_REMARK;
        }

        public void setADDRESS_REMARK(Object ADDRESS_REMARK) {
            this.ADDRESS_REMARK = ADDRESS_REMARK;
        }

        public int getG_p_id() {
            return g_p_id;
        }

        public void setG_p_id(int g_p_id) {
            this.g_p_id = g_p_id;
        }

        public String getIS_DEFAULT() {
            return IS_DEFAULT;
        }

        public void setIS_DEFAULT(String IS_DEFAULT) {
            this.IS_DEFAULT = IS_DEFAULT;
        }

        public String getZONE_CODE1() {
            return ZONE_CODE1;
        }

        public void setZONE_CODE1(String ZONE_CODE1) {
            this.ZONE_CODE1 = ZONE_CODE1;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getZONE_CODE3() {
            return ZONE_CODE3;
        }

        public void setZONE_CODE3(Object ZONE_CODE3) {
            this.ZONE_CODE3 = ZONE_CODE3;
        }

        public String getBussiness_type() {
            return bussiness_type;
        }

        public void setBussiness_type(String bussiness_type) {
            this.bussiness_type = bussiness_type;
        }

        public Object getZONE_CODE2() {
            return ZONE_CODE2;
        }

        public void setZONE_CODE2(Object ZONE_CODE2) {
            this.ZONE_CODE2 = ZONE_CODE2;
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

        public String getSHIPPER() {
            return SHIPPER;
        }

        public void setSHIPPER(String SHIPPER) {
            this.SHIPPER = SHIPPER;
        }

        public String getMOBILE_NO() {
            return MOBILE_NO;
        }

        public void setMOBILE_NO(String MOBILE_NO) {
            this.MOBILE_NO = MOBILE_NO;
        }

        public Object getAdvance_pay_amount() {
            return advance_pay_amount;
        }

        public void setAdvance_pay_amount(Object advance_pay_amount) {
            this.advance_pay_amount = advance_pay_amount;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCommodity_img() {
            return commodity_img;
        }

        public void setCommodity_img(String commodity_img) {
            this.commodity_img = commodity_img;
        }

        public String getCommodity_describe() {
            return commodity_describe;
        }

        public void setCommodity_describe(String commodity_describe) {
            this.commodity_describe = commodity_describe;
        }
    }
}
