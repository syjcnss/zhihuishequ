package com.ovu.lido.bean;

import java.util.List;

public class ActivityBean {

    /**
     * timestamp : 20180510140639
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : [{"id":106,"activity_name":"庆祝香港回归20周年","activity_scope":2,"community_id":34,"creator_id":441,"create_type":1,"activity_type_id":6,"sponsor":"武汉丽岛物业管理有限公司","activity_img":"/img/201706/20170630113607_497.png","address":"创意天地文化广场","begin_time":"2017-07-01 09:00:00","end_time":"2017-07-01 16:00:00","enroll_end_time":"2017-06-30 23:00:00","introduce":"庆祝香港回归20周年大型互动文艺演出","tel":"4000273999","enroll_limit":200,"activity_content":"一、文艺演出；\n二、现场观众互动游戏；\n三、盛大宴会；\n四、创意市集。","state":2,"del":1,"type_name":null,"enrollCount":0,"likeCount":2,"commentCount":3,"creator_name":null,"is_like":false,"is_enroll":false,"is_end":false,"activityComments":null},{"id":105,"activity_name":"test","activity_scope":2,"community_id":34,"creator_id":441,"create_type":1,"activity_type_id":1,"sponsor":"丽岛物业","activity_img":"","address":"丽岛物业","begin_time":"2017-06-21 16:03:16","end_time":"2017-06-30 16:03:18","enroll_end_time":"2017-06-30 16:03:21","introduce":"test","tel":"15071796932","enroll_limit":100,"activity_content":"大家一起来测试新系统","state":2,"del":1,"type_name":null,"enrollCount":6,"likeCount":0,"commentCount":0,"creator_name":null,"is_like":false,"is_enroll":false,"is_end":false,"activityComments":null},{"id":71,"activity_name":"亲子运动会","activity_scope":2,"community_id":34,"creator_id":425,"create_type":1,"activity_type_id":2,"sponsor":"丽岛物业","activity_img":"/img/201704/20170419110537_443.jpg","address":"小区东广场","begin_time":"2017-05-06 11:03:03","end_time":"2017-06-30 11:24:02","enroll_end_time":"2017-06-30 11:24:09","introduce":"一起来玩吧","tel":"02787399165","enroll_limit":50,"activity_content":"详情咨询物业！","state":2,"del":1,"type_name":null,"enrollCount":4,"likeCount":1,"commentCount":2,"creator_name":null,"is_like":false,"is_enroll":false,"is_end":false,"activityComments":null}]
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
         * id : 106
         * activity_name : 庆祝香港回归20周年
         * activity_scope : 2
         * community_id : 34
         * creator_id : 441
         * create_type : 1
         * activity_type_id : 6
         * sponsor : 武汉丽岛物业管理有限公司
         * activity_img : /img/201706/20170630113607_497.png
         * address : 创意天地文化广场
         * begin_time : 2017-07-01 09:00:00
         * end_time : 2017-07-01 16:00:00
         * enroll_end_time : 2017-06-30 23:00:00
         * introduce : 庆祝香港回归20周年大型互动文艺演出
         * tel : 4000273999
         * enroll_limit : 200
         * activity_content : 一、文艺演出；
         二、现场观众互动游戏；
         三、盛大宴会；
         四、创意市集。
         * state : 2
         * del : 1
         * type_name : null
         * enrollCount : 0
         * likeCount : 2
         * commentCount : 3
         * creator_name : null
         * is_like : false
         * is_enroll : false
         * is_end : false
         * activityComments : null
         */

        private int id;
        private String activity_name;
        private int activity_scope;
        private int community_id;
        private int creator_id;
        private int create_type;
        private int activity_type_id;
        private String sponsor;
        private String activity_img;
        private String address;
        private String begin_time;
        private String end_time;
        private String enroll_end_time;
        private String introduce;
        private String tel;
        private int enroll_limit;
        private String activity_content;
        private int state;
        private int del;
        private Object type_name;
        private int enrollCount;
        private int likeCount;
        private int commentCount;
        private Object creator_name;
        private boolean is_like;
        private boolean is_enroll;
        private boolean is_end;
        private Object activityComments;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(String activity_name) {
            this.activity_name = activity_name;
        }

        public int getActivity_scope() {
            return activity_scope;
        }

        public void setActivity_scope(int activity_scope) {
            this.activity_scope = activity_scope;
        }

        public int getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(int community_id) {
            this.community_id = community_id;
        }

        public int getCreator_id() {
            return creator_id;
        }

        public void setCreator_id(int creator_id) {
            this.creator_id = creator_id;
        }

        public int getCreate_type() {
            return create_type;
        }

        public void setCreate_type(int create_type) {
            this.create_type = create_type;
        }

        public int getActivity_type_id() {
            return activity_type_id;
        }

        public void setActivity_type_id(int activity_type_id) {
            this.activity_type_id = activity_type_id;
        }

        public String getSponsor() {
            return sponsor;
        }

        public void setSponsor(String sponsor) {
            this.sponsor = sponsor;
        }

        public String getActivity_img() {
            return activity_img;
        }

        public void setActivity_img(String activity_img) {
            this.activity_img = activity_img;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBegin_time() {
            return begin_time;
        }

        public void setBegin_time(String begin_time) {
            this.begin_time = begin_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getEnroll_end_time() {
            return enroll_end_time;
        }

        public void setEnroll_end_time(String enroll_end_time) {
            this.enroll_end_time = enroll_end_time;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getEnroll_limit() {
            return enroll_limit;
        }

        public void setEnroll_limit(int enroll_limit) {
            this.enroll_limit = enroll_limit;
        }

        public String getActivity_content() {
            return activity_content;
        }

        public void setActivity_content(String activity_content) {
            this.activity_content = activity_content;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getDel() {
            return del;
        }

        public void setDel(int del) {
            this.del = del;
        }

        public Object getType_name() {
            return type_name;
        }

        public void setType_name(Object type_name) {
            this.type_name = type_name;
        }

        public int getEnrollCount() {
            return enrollCount;
        }

        public void setEnrollCount(int enrollCount) {
            this.enrollCount = enrollCount;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public void setLikeCount(int likeCount) {
            this.likeCount = likeCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public Object getCreator_name() {
            return creator_name;
        }

        public void setCreator_name(Object creator_name) {
            this.creator_name = creator_name;
        }

        public boolean isIs_like() {
            return is_like;
        }

        public void setIs_like(boolean is_like) {
            this.is_like = is_like;
        }

        public boolean isIs_enroll() {
            return is_enroll;
        }

        public void setIs_enroll(boolean is_enroll) {
            this.is_enroll = is_enroll;
        }

        public boolean isIs_end() {
            return is_end;
        }

        public void setIs_end(boolean is_end) {
            this.is_end = is_end;
        }

        public Object getActivityComments() {
            return activityComments;
        }

        public void setActivityComments(Object activityComments) {
            this.activityComments = activityComments;
        }
    }
}
