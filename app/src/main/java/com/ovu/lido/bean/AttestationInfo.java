package com.ovu.lido.bean;

public class AttestationInfo {

    /**
     * timestamp : 20180511141039
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : {"nick_name":"ldwy7400177","identity":1,"resident_count":120,"community_name":"丽岛物业","token":"d06a856d5e5b4756a41592ece081e883","resident_id":214021,"community_id":34}
     * point : 50
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
         * nick_name : ldwy7400177
         * identity : 1
         * resident_count : 120
         * community_name : 丽岛物业
         * token : d06a856d5e5b4756a41592ece081e883
         * resident_id : 214021
         * community_id : 34
         */

        private String nick_name;
        private int identity;
        private int resident_count;
        private String community_name;
        private String token;
        private int resident_id;
        private int community_id;

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
