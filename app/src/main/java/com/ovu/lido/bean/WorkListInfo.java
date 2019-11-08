package com.ovu.lido.bean;

import java.util.List;

public class WorkListInfo {

    /**
     * timestamp : 20180529190304
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : [{"id":1,"work_title":"入伙协议","work_content":null,"work_time":null,"community_id":null,"community_name":null},{"id":2,"work_title":"办事办件","work_content":null,"work_time":null,"community_id":null,"community_name":null},{"id":3,"work_title":"测试流程","work_content":null,"work_time":null,"community_id":null,"community_name":null},{"id":4,"work_title":"测试","work_content":null,"work_time":null,"community_id":null,"community_name":null}]
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
         * id : 1
         * work_title : 入伙协议
         * work_content : null
         * work_time : null
         * community_id : null
         * community_name : null
         */

        private int id;
        private String work_title;
        private Object work_content;
        private Object work_time;
        private Object community_id;
        private Object community_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWork_title() {
            return work_title;
        }

        public void setWork_title(String work_title) {
            this.work_title = work_title;
        }

        public Object getWork_content() {
            return work_content;
        }

        public void setWork_content(Object work_content) {
            this.work_content = work_content;
        }

        public Object getWork_time() {
            return work_time;
        }

        public void setWork_time(Object work_time) {
            this.work_time = work_time;
        }

        public Object getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(Object community_id) {
            this.community_id = community_id;
        }

        public Object getCommunity_name() {
            return community_name;
        }

        public void setCommunity_name(Object community_name) {
            this.community_name = community_name;
        }
    }
}
