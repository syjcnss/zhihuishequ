package com.ovu.lido.bean;

import java.util.List;

public class WalletHistoryBean {


    /**
     * timestamp : 20180528181000
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : [{"change_amount":3434.12,"create_time":"2017-11-25 20:28:03","change_type":0,"incident":"自助缴费！"},{"change_amount":416.13,"create_time":"2017-09-16 17:03:41","change_type":0,"incident":"自助缴费！"},{"change_amount":0.33,"create_time":"2017-09-15 00:21:29","change_type":1,"incident":"开门红包！"},{"change_amount":3.49,"create_time":"2017-09-15 00:19:29","change_type":1,"incident":"开门红包！"}]
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
         * change_amount : 3434.12
         * create_time : 2017-11-25 20:28:03
         * change_type : 0
         * incident : 自助缴费！
         */

        private double change_amount;
        private String create_time;
        private int change_type;
        private String incident;

        public double getChange_amount() {
            return change_amount;
        }

        public void setChange_amount(double change_amount) {
            this.change_amount = change_amount;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getChange_type() {
            return change_type;
        }

        public void setChange_type(int change_type) {
            this.change_type = change_type;
        }

        public String getIncident() {
            return incident;
        }

        public void setIncident(String incident) {
            this.incident = incident;
        }
    }
}
