package com.ovu.lido.bean;

public class BeginAwardsInfo {

    /**
     * timestamp : 20180605151602
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : {"awards_id":5,"lottery_number":1,"pointIncrement":5}
     * point : 5
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
         * awards_id : 5
         * lottery_number : 1
         * pointIncrement : 5
         */

        private int awards_id;
        private int lottery_number;
        private int pointIncrement;

        public int getAwards_id() {
            return awards_id;
        }

        public void setAwards_id(int awards_id) {
            this.awards_id = awards_id;
        }

        public int getLottery_number() {
            return lottery_number;
        }

        public void setLottery_number(int lottery_number) {
            this.lottery_number = lottery_number;
        }

        public int getPointIncrement() {
            return pointIncrement;
        }

        public void setPointIncrement(int pointIncrement) {
            this.pointIncrement = pointIncrement;
        }
    }
}
