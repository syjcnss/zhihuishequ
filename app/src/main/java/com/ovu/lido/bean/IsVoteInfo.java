package com.ovu.lido.bean;

public class IsVoteInfo {

    /**
     * timestamp : 20180601151248
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : {"isEmpty":"1","isSend":"1"}
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
         * isEmpty : 1
         * isSend : 1
         */

        private String isEmpty;
        private String isSend;

        public String getIsEmpty() {
            return isEmpty;
        }

        public void setIsEmpty(String isEmpty) {
            this.isEmpty = isEmpty;
        }

        public String getIsSend() {
            return isSend;
        }

        public void setIsSend(String isSend) {
            this.isSend = isSend;
        }
    }
}
