package com.ovu.lido.bean;

import java.util.List;

public class MembersInfo {

    /**
     * timestamp : 20180512171920
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : [{"id":6,"communtiy_id":34,"username":"刘姮","mobile":"15072310946","info":"123456","head_img":"/img/201606/20160624150201_273.jpg","position":"党员","community_NAME":null},{"id":8,"communtiy_id":34,"username":"刘花","mobile":"13035111489","info":"2015年1月加入中国共产党，加入期间爱岗敬业，维护党的荣誉。","head_img":"/img/201612/20161215144510_124.jpg","position":"居委会","community_NAME":null},{"id":9,"communtiy_id":34,"username":"老干妈","mobile":"18612121212","info":"老干妈","head_img":"/img/201707/20170705152239_371.png","position":"党支部书记","community_NAME":null}]
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
         * id : 6
         * communtiy_id : 34
         * username : 刘姮
         * mobile : 15072310946
         * info : 123456
         * head_img : /img/201606/20160624150201_273.jpg
         * position : 党员
         * community_NAME : null
         */

        private int id;
        private int communtiy_id;
        private String username;
        private String mobile;
        private String info;
        private String head_img;
        private String position;
        private Object community_NAME;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCommuntiy_id() {
            return communtiy_id;
        }

        public void setCommuntiy_id(int communtiy_id) {
            this.communtiy_id = communtiy_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public Object getCommunity_NAME() {
            return community_NAME;
        }

        public void setCommunity_NAME(Object community_NAME) {
            this.community_NAME = community_NAME;
        }
    }
}
