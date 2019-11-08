package com.ovu.lido.bean;

public class OpenDoorRedPaket {

    /**
     * timestamp : 20180604172630
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : {"sponsor":"3131","sponsor_img":"3131,3131,http://127.0.0.1:8210/img/20180531/20180531180628.jpg","redpackage":130.63,"type":0}
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
         * sponsor : 3131
         * sponsor_img : 3131,3131,http://127.0.0.1:8210/img/20180531/20180531180628.jpg
         * redpackage : 130.63
         * type : 0
         */

        private String sponsor;
        private String sponsor_img;
        private double redpackage;
        private int type;

        public String getSponsor() {
            return sponsor;
        }

        public void setSponsor(String sponsor) {
            this.sponsor = sponsor;
        }

        public String getSponsor_img() {
            return sponsor_img;
        }

        public void setSponsor_img(String sponsor_img) {
            this.sponsor_img = sponsor_img;
        }

        public double getRedpackage() {
            return redpackage;
        }

        public void setRedpackage(double redpackage) {
            this.redpackage = redpackage;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
