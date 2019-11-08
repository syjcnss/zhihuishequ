package com.ovu.lido.bean;

import java.util.List;

public class VoteDetailInfo {

    /**
     * serialNo :
     * timestamp : 20180512111258
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data :
     * totalNum :
     * point : 0
     * vote_id : 34
     * vote_title : 2015中国十大品牌女性候选人网络投票
     * vote_content : 2015中国十大品牌女性候选人网络投票
     * vote_type : 2
     * start_time :
     * end_time :
     * vote_ever : 0
     * vote_option :
     * option_list : [{"option_index":"A","option_content":"柴静---著名记者和主持人"},{"option_index":"B","option_content":"李静-乐蜂网创始人"},{"option_index":"C","option_content":"杜红-新浪CEO联合总裁"},{"option_index":"D","option_content":"陶华碧-贵阳南明老干妈风味食品董事长"},{"option_index":"E","option_content":"陆允娟-北京众生平安健康产业集团董事长"},{"option_index":"F","option_content":"王伟杨-河南省宋河酒业股份有限公司总裁"},{"option_index":"G","option_content":"周袁红-阿姨来了信息科技创始人兼CEO"}]
     */

    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private String data;
    private String totalNum;
    private int point;
    private String vote_id;
    private String vote_title;
    private String vote_content;
    private String vote_type;
    private String start_time;
    private String end_time;
    private String vote_ever;
    private String vote_option;
    private List<OptionListBean> option_list;

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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getVote_ever() {
        return vote_ever;
    }

    public void setVote_ever(String vote_ever) {
        this.vote_ever = vote_ever;
    }

    public String getVote_option() {
        return vote_option;
    }

    public void setVote_option(String vote_option) {
        this.vote_option = vote_option;
    }

    public List<OptionListBean> getOption_list() {
        return option_list;
    }

    public void setOption_list(List<OptionListBean> option_list) {
        this.option_list = option_list;
    }

    public static class OptionListBean {
        /**
         * option_index : A
         * option_content : 柴静---著名记者和主持人
         */

        private String option_index;
        private String option_content;

        public String getOption_index() {
            return option_index;
        }

        public void setOption_index(String option_index) {
            this.option_index = option_index;
        }

        public String getOption_content() {
            return option_content;
        }

        public void setOption_content(String option_content) {
            this.option_content = option_content;
        }
    }
}
