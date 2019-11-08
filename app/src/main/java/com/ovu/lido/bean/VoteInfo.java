package com.ovu.lido.bean;

import java.util.List;

public class VoteInfo {

    /**
     * serialNo :
     * timestamp : 20180512102608
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data :
     * totalNum :
     * point : 0
     * vote_list : [{"vote_id":"47","vote_title":"测试投票","vote_content":"测试","vote_type":"1","vote_ever":"","create_TIME":"2016-04-05 02:12:00"},{"vote_id":"49","vote_title":"环境满意度","vote_content":"两个选项，满意和不满意","vote_type":"2","vote_ever":"","create_TIME":"2016-04-05 02:12:00"},{"vote_id":"50","vote_title":"123","vote_content":"456","vote_type":"2","vote_ever":"","create_TIME":"2016-04-05 02:12:00"},{"vote_id":"51","vote_title":"开不开空调","vote_content":"投票哟","vote_type":"2","vote_ever":"","create_TIME":"2016-04-05 02:12:00"},{"vote_id":"52","vote_title":"黄鹤楼是谁的诗","vote_content":"投票有奖","vote_type":"1","vote_ever":"","create_TIME":"2016-04-05 02:12:00"}]
     */

    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private String data;
    private String totalNum;
    private int point;
    private List<VoteListBean> vote_list;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
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

    public List<VoteListBean> getVote_list() {
        return vote_list;
    }

    public void setVote_list(List<VoteListBean> vote_list) {
        this.vote_list = vote_list;
    }

    public static class VoteListBean {
        /**
         * vote_id : 47
         * vote_title : 测试投票
         * vote_content : 测试
         * vote_type : 1
         * vote_ever :
         * create_TIME : 2016-04-05 02:12:00
         */

        private String vote_id;
        private String vote_title;
        private String vote_content;
        private String vote_type;
        private String vote_ever;
        private String create_TIME;

        public String getVote_id() {
            return vote_id;
        }

        public void setVote_id(String vote_id) {
            this.vote_id = vote_id;
        }

        public String getVote_title() {
            return vote_title;
        }

        public void setVote_title(String vote_title) {
            this.vote_title = vote_title;
        }

        public String getVote_content() {
            return vote_content;
        }

        public void setVote_content(String vote_content) {
            this.vote_content = vote_content;
        }

        public String getVote_type() {
            return vote_type;
        }

        public void setVote_type(String vote_type) {
            this.vote_type = vote_type;
        }

        public String getVote_ever() {
            return vote_ever;
        }

        public void setVote_ever(String vote_ever) {
            this.vote_ever = vote_ever;
        }

        public String getCreate_TIME() {
            return create_TIME;
        }

        public void setCreate_TIME(String create_TIME) {
            this.create_TIME = create_TIME;
        }
    }
}
