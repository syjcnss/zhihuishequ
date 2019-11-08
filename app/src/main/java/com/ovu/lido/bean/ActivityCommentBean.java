package com.ovu.lido.bean;

import java.util.List;

public class ActivityCommentBean {


    /**
     * timestamp : 20180510174118
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : [{"id":61,"activity_id":106,"user_id":211249,"community_id":34,"comment":"啊啊啊啊啊啊","comment_time":"2018-04-16 12:47:08","scope":"2","activity_name":null,"user_name":"ldwy844101"},{"id":57,"activity_id":106,"user_id":205043,"community_id":34,"comment":"啦啦","comment_time":"2017-07-17 10:22:37","scope":"2","activity_name":null,"user_name":null},{"id":56,"activity_id":106,"user_id":4454,"community_id":34,"comment":"嘿嘿","comment_time":"2017-07-05 11:43:58","scope":"2","activity_name":null,"user_name":"哆啦咪梦"}]
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
         * id : 61
         * activity_id : 106
         * user_id : 211249
         * community_id : 34
         * comment : 啊啊啊啊啊啊
         * comment_time : 2018-04-16 12:47:08
         * scope : 2
         * activity_name : null
         * user_name : ldwy844101
         */

        private int id;
        private int activity_id;
        private int user_id;
        private int community_id;
        private String comment;
        private String comment_time;
        private String scope;
        private Object activity_name;
        private String user_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(int activity_id) {
            this.activity_id = activity_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(int community_id) {
            this.community_id = community_id;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getComment_time() {
            return comment_time;
        }

        public void setComment_time(String comment_time) {
            this.comment_time = comment_time;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public Object getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(Object activity_name) {
            this.activity_name = activity_name;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}
