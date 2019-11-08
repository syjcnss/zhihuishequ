package com.ovu.lido.bean;

import java.io.Serializable;

public class GroupDetailBean implements Serializable {


    /**
     * timestamp : 20180511100659
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : {"id":67,"creator_id":null,"g_p_name":"益达口香糖","sponsor":null,"commodity_name":"益达口香糖","begin_time":"2017-06-30 11:16:47","end_time":"2018-06-30 11:16:48","create_time":"2017-06-30 11:17:19","content":"  <!DOCTYPE html><html lang='zh-cn'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'><meta name='viewport' content='width=device-width, initial-scale=1.0' /><\/head><body><p>给，你的益达\u2026\u2026<\/p><p>不，是你的益达\u2026\u2026<\/p><\/body>","tel":"18012341234","enroll_limit":10000,"state":2,"del":1,"community_id":34,"enrollCount":11,"is_enroll":true,"is_end":false,"original_price":10,"g_p_price":0.01,"commodity_img":"http://120.27.196.188/community/info_img_path/img/201706/20170630111633_523.png","commodity_id":15}
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

    public static class DataBean implements Serializable {
        /**
         * id : 67
         * creator_id : null
         * g_p_name : 益达口香糖
         * sponsor : null
         * commodity_name : 益达口香糖
         * begin_time : 2017-06-30 11:16:47
         * end_time : 2018-06-30 11:16:48
         * create_time : 2017-06-30 11:17:19
         * content :   <!DOCTYPE html><html lang='zh-cn'><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'><meta name='viewport' content='width=device-width, initial-scale=1.0' /></head><body><p>给，你的益达……</p><p>不，是你的益达……</p></body>
         * tel : 18012341234
         * enroll_limit : 10000
         * state : 2
         * del : 1
         * community_id : 34
         * enrollCount : 11
         * is_enroll : true
         * is_end : false
         * original_price : 10
         * g_p_price : 0.01
         * commodity_img : http://120.27.196.188/community/info_img_path/img/201706/20170630111633_523.png
         * commodity_id : 15
         */

        private int id;
        private Object creator_id;
        private String g_p_name;
        private Object sponsor;
        private String commodity_name;
        private String begin_time;
        private String end_time;
        private String create_time;
        private String content;
        private String tel;
        private int enroll_limit;
        private int state;
        private int del;
        private int community_id;
        private int enrollCount;
        private boolean is_enroll;
        private boolean is_end;
        private double original_price;
        private double g_p_price;
        private String commodity_img;
        private int commodity_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getCreator_id() {
            return creator_id;
        }

        public void setCreator_id(Object creator_id) {
            this.creator_id = creator_id;
        }

        public String getG_p_name() {
            return g_p_name;
        }

        public void setG_p_name(String g_p_name) {
            this.g_p_name = g_p_name;
        }

        public Object getSponsor() {
            return sponsor;
        }

        public void setSponsor(Object sponsor) {
            this.sponsor = sponsor;
        }

        public String getCommodity_name() {
            return commodity_name;
        }

        public void setCommodity_name(String commodity_name) {
            this.commodity_name = commodity_name;
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public int getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(int community_id) {
            this.community_id = community_id;
        }

        public int getEnrollCount() {
            return enrollCount;
        }

        public void setEnrollCount(int enrollCount) {
            this.enrollCount = enrollCount;
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

        public double getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(double original_price) {
            this.original_price = original_price;
        }

        public double getG_p_price() {
            return g_p_price;
        }

        public void setG_p_price(double g_p_price) {
            this.g_p_price = g_p_price;
        }

        public String getCommodity_img() {
            return commodity_img;
        }

        public void setCommodity_img(String commodity_img) {
            this.commodity_img = commodity_img;
        }

        public int getCommodity_id() {
            return commodity_id;
        }

        public void setCommodity_id(int commodity_id) {
            this.commodity_id = commodity_id;
        }
    }
}
