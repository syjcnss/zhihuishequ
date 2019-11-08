package com.ovu.lido.bean;

import java.util.List;

public class MyPrizeInfo {

    /**
     * timestamp : 20180607091751
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : {"total_count":3,"list":[{"is_expiry":0,"awards_time":"2018-06-05","awards_img":"http://120.27.196.188/supermarket/supermarket_img_path","awards_name":"200积分"},{"is_expiry":0,"awards_time":"2018-06-05","awards_img":"http://120.27.196.188/supermarket/supermarket_img_path","awards_name":"200积分"},{"is_expiry":0,"awards_time":"2018-06-05","awards_img":"http://120.27.196.188/supermarket/supermarket_img_path","awards_name":"200积分"}]}
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
         * total_count : 3
         * list : [{"is_expiry":0,"awards_time":"2018-06-05","awards_img":"http://120.27.196.188/supermarket/supermarket_img_path","awards_name":"200积分"},{"is_expiry":0,"awards_time":"2018-06-05","awards_img":"http://120.27.196.188/supermarket/supermarket_img_path","awards_name":"200积分"},{"is_expiry":0,"awards_time":"2018-06-05","awards_img":"http://120.27.196.188/supermarket/supermarket_img_path","awards_name":"200积分"}]
         */

        private int total_count;
        private List<ListBean> list;

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * is_expiry : 0
             * awards_time : 2018-06-05
             * awards_img : http://120.27.196.188/supermarket/supermarket_img_path
             * awards_name : 200积分
             */

            private int is_expiry;
            private String awards_time;
            private String awards_img;
            private String awards_name;

            public int getIs_expiry() {
                return is_expiry;
            }

            public void setIs_expiry(int is_expiry) {
                this.is_expiry = is_expiry;
            }

            public String getAwards_time() {
                return awards_time;
            }

            public void setAwards_time(String awards_time) {
                this.awards_time = awards_time;
            }

            public String getAwards_img() {
                return awards_img;
            }

            public void setAwards_img(String awards_img) {
                this.awards_img = awards_img;
            }

            public String getAwards_name() {
                return awards_name;
            }

            public void setAwards_name(String awards_name) {
                this.awards_name = awards_name;
            }
        }
    }
}
