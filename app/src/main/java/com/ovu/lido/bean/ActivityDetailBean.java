package com.ovu.lido.bean;

public class ActivityDetailBean {


    /**
     * timestamp : 20180510165931
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : {"id":20,"activity_name":"水域天际社区特惠节（9月9日--9月16日）","activity_scope":2,"community_id":56,"creator_id":174,"create_type":1,"activity_type_id":8,"sponsor":"武汉丽岛物业管理有限公司","activity_img":"","address":"水域天际会所广场","begin_time":"2016-09-09 11:03:51","end_time":"2016-09-09 11:03:51","enroll_end_time":"2016-09-08 11:03:51","introduce":"丽岛物业携手百余商户，举行大型社区特惠节活动。通过业主APP支付即可享受满减，特惠商品和抽奖活动哦！","tel":"02788776177","enroll_limit":1000,"activity_content":"月儿圆圆迎中秋，优惠多多邀您来！尊敬的各位水域天际的业主/住户，本月9日-16日，丽岛物业携手百余商家举行大型社区特惠节活动啦！\n优惠一、百余店铺折扣大放送，购买商品满立减；优惠二、惊喜优惠商品买不停，比超市折扣更给力；优惠三、幸运抽奖每天有，下一个幸运人儿就是你。只要您下载注册成为\u201ci丽岛\u201d用户，通过APP完成支付。以上惊喜优惠就都是您的啦！心动不如行动，手指赶紧动起来吧！\n活动地址：小区会所广场，详情请关注小区内宣传海报，或咨询活动点工作人员。","state":2,"del":1,"type_name":null,"enrollCount":0,"likeCount":0,"commentCount":0,"creator_name":"武汉丽岛物业管理有限公司","is_like":false,"is_enroll":false,"is_end":true,"activityComments":null}
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
         * id : 20
         * activity_name : 水域天际社区特惠节（9月9日--9月16日）
         * activity_scope : 2
         * community_id : 56
         * creator_id : 174
         * create_type : 1
         * activity_type_id : 8
         * sponsor : 武汉丽岛物业管理有限公司
         * activity_img :
         * address : 水域天际会所广场
         * begin_time : 2016-09-09 11:03:51
         * end_time : 2016-09-09 11:03:51
         * enroll_end_time : 2016-09-08 11:03:51
         * introduce : 丽岛物业携手百余商户，举行大型社区特惠节活动。通过业主APP支付即可享受满减，特惠商品和抽奖活动哦！
         * tel : 02788776177
         * enroll_limit : 1000
         * activity_content : 月儿圆圆迎中秋，优惠多多邀您来！尊敬的各位水域天际的业主/住户，本月9日-16日，丽岛物业携手百余商家举行大型社区特惠节活动啦！
         优惠一、百余店铺折扣大放送，购买商品满立减；优惠二、惊喜优惠商品买不停，比超市折扣更给力；优惠三、幸运抽奖每天有，下一个幸运人儿就是你。只要您下载注册成为“i丽岛”用户，通过APP完成支付。以上惊喜优惠就都是您的啦！心动不如行动，手指赶紧动起来吧！
         活动地址：小区会所广场，详情请关注小区内宣传海报，或咨询活动点工作人员。
         * state : 2
         * del : 1
         * type_name : null
         * enrollCount : 0
         * likeCount : 0
         * commentCount : 0
         * creator_name : 武汉丽岛物业管理有限公司
         * is_like : false
         * is_enroll : false
         * is_end : true
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
        private String creator_name;
        private boolean is_like;
        private boolean is_enroll;
        private boolean is_end;
        private Object activityComments;
        private String evaluateComment;

        public String getEvaluateComment() {
            return evaluateComment;
        }

        public void setEvaluateComment(String evaluateComment) {
            this.evaluateComment = evaluateComment;
        }

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

        public String getCreator_name() {
            return creator_name;
        }

        public void setCreator_name(String creator_name) {
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
