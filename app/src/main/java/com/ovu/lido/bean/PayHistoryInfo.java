package com.ovu.lido.bean;

import java.util.List;

public class PayHistoryInfo {

    /**
     * timestamp : 20180509202059
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : [{"amount":0.1,"begin_time":"2018-03-24","create_date":"2018-05-05 14:52:25","item_name":"水费","end_time":"2018-03-28","house_code":165245,"charge_item_code":66}]
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

    public static class DataBean {
        /**
         * amount : 0.1
         * begin_time : 2018-03-24
         * create_date : 2018-05-05 14:52:25
         * item_name : 水费
         * end_time : 2018-03-28
         * house_code : 165245
         * charge_item_code : 66
         */

        private double amount;
        private String begin_time;
        private String create_date;
        private String item_name;
        private String end_time;
        private int house_code;
        private int charge_item_code;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getBegin_time() {
            return begin_time;
        }

        public void setBegin_time(String begin_time) {
            this.begin_time = begin_time;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public int getHouse_code() {
            return house_code;
        }

        public void setHouse_code(int house_code) {
            this.house_code = house_code;
        }

        public int getCharge_item_code() {
            return charge_item_code;
        }

        public void setCharge_item_code(int charge_item_code) {
            this.charge_item_code = charge_item_code;
        }
    }
}
