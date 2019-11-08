package com.ovu.lido.bean;

public class SureChangeBean {


    /**
     * serialNo :
     * timestamp : 20180525104757
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data : {"identity":2,"certification":1,"resident_count":104,"community_name":"丽岛物业","token":"01e15872e3b644f092e7d31317599183","resident_id":211249,"community_id":34}
     * totalNum :
     * point : 0
     */

    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private DataBean data;
    private String totalNum;
    private int point;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * identity : 2
         * certification : 1
         * resident_count : 104
         * community_name : 丽岛物业
         * token : 01e15872e3b644f092e7d31317599183
         * resident_id : 211249
         * community_id : 34
         */

        private int identity;
        private int certification;
        private int resident_count;
        private String community_name;
        private String token;
        private int resident_id;
        private int community_id;

        public int getIdentity() {
            return identity;
        }

        public void setIdentity(int identity) {
            this.identity = identity;
        }

        public int getCertification() {
            return certification;
        }

        public void setCertification(int certification) {
            this.certification = certification;
        }

        public int getResident_count() {
            return resident_count;
        }

        public void setResident_count(int resident_count) {
            this.resident_count = resident_count;
        }

        public String getCommunity_name() {
            return community_name;
        }

        public void setCommunity_name(String community_name) {
            this.community_name = community_name;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getResident_id() {
            return resident_id;
        }

        public void setResident_id(int resident_id) {
            this.resident_id = resident_id;
        }

        public int getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(int community_id) {
            this.community_id = community_id;
        }
    }
}
