package com.ovu.lido.bean;

import java.util.List;

public class AwardsInfo {

    /**
     * timestamp : 20180605110044
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : {"lottery_number":1,"list":[{"id":2,"awards_img":"http://120.27.196.188/supermarket/supermarket_img_path/img/201708/20170830120034_82.jpg","awards_name":"即"},{"id":9,"awards_img":"http://120.27.196.188/supermarket/supermarket_img_path/img/201707/20170718114311_435.png","awards_name":"请"},{"id":3,"awards_img":"http://120.27.196.188/supermarket/supermarket_img_path","awards_name":"200积分"},{"id":4,"awards_img":"http://120.27.196.188/supermarket/supermarket_img_path/img/201707/20170718114256_300.png","awards_name":"上"},{"id":5,"awards_img":"http://120.27.196.188/supermarket/supermarket_img_path","awards_name":"谢谢参与"},{"id":10,"awards_img":"http://120.27.196.188/supermarket/supermarket_img_path/img/201707/20170718114315_104.png","awards_name":"期"},{"id":15,"awards_img":"http://120.27.196.188/supermarket/supermarket_img_path/img/201707/20170718114320_311.png","awards_name":"待"},{"id":8,"awards_img":"http://120.27.196.188/supermarket/supermarket_img_path/img/201707/20170718114307_35.png","awards_name":"敬"}]}
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
         * lottery_number : 1
         * list : [{"id":2,"awards_img":"http://120.27.196.188/supermarket/supermarket_img_path/img/201708/20170830120034_82.jpg","awards_name":"即"},{"id":9,"awards_img":"http://120.27.196.188/supermarket/supermarket_img_path/img/201707/20170718114311_435.png","awards_name":"请"},{"id":3,"awards_img":"http://120.27.196.188/supermarket/supermarket_img_path","awards_name":"200积分"},{"id":4,"awards_img":"http://120.27.196.188/supermarket/supermarket_img_path/img/201707/20170718114256_300.png","awards_name":"上"},{"id":5,"awards_img":"http://120.27.196.188/supermarket/supermarket_img_path","awards_name":"谢谢参与"},{"id":10,"awards_img":"http://120.27.196.188/supermarket/supermarket_img_path/img/201707/20170718114315_104.png","awards_name":"期"},{"id":15,"awards_img":"http://120.27.196.188/supermarket/supermarket_img_path/img/201707/20170718114320_311.png","awards_name":"待"},{"id":8,"awards_img":"http://120.27.196.188/supermarket/supermarket_img_path/img/201707/20170718114307_35.png","awards_name":"敬"}]
         */

        private int lottery_number;
        private List<ListBean> list;

        public int getLottery_number() {
            return lottery_number;
        }

        public void setLottery_number(int lottery_number) {
            this.lottery_number = lottery_number;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 2
             * awards_img : http://120.27.196.188/supermarket/supermarket_img_path/img/201708/20170830120034_82.jpg
             * awards_name : 即
             */

            private int id;
            private String awards_img;
            private String awards_name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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
