package com.ovu.lido.bean;

import java.util.List;

public class ChilAccountBean {


    /**
     * serialNo :
     * timestamp : 20180517165120
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data : [{"nick_name":"ldwy2724284","identity":2,"MOBILE_NO":"17786483075","child_state":1,"child_id":213794,"resident_id":211249}]
     * totalNum :
     * point : 0
     */

    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private String totalNum;
    private int point;
    private List<DataBean> data;

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
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
         * nick_name : ldwy2724284
         * identity : 2
         * MOBILE_NO : 17786483075
         * child_state : 1
         * child_id : 213794
         * resident_id : 211249
         */

        private String nick_name;
        private int identity;
        private String MOBILE_NO;
        private int child_state;
        private int child_id;
        private int resident_id;

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public int getIdentity() {
            return identity;
        }

        public void setIdentity(int identity) {
            this.identity = identity;
        }

        public String getMOBILE_NO() {
            return MOBILE_NO;
        }

        public void setMOBILE_NO(String MOBILE_NO) {
            this.MOBILE_NO = MOBILE_NO;
        }

        public int getChild_state() {
            return child_state;
        }

        public void setChild_state(int child_state) {
            this.child_state = child_state;
        }

        public int getChild_id() {
            return child_id;
        }

        public void setChild_id(int child_id) {
            this.child_id = child_id;
        }

        public int getResident_id() {
            return resident_id;
        }

        public void setResident_id(int resident_id) {
            this.resident_id = resident_id;
        }
    }
}
