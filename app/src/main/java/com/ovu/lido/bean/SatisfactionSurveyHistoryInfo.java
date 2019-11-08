package com.ovu.lido.bean;

import java.util.List;

public class SatisfactionSurveyHistoryInfo {

    /**
     * serialNo :
     * timestamp : 20180522151508
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data :
     * totalNum :
     * point : 0
     * info_list :
     * info_historylist : [{"vote_time":"2018-05-11 17:25:02","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-11 17:25:02","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-11 17:25:02","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-11 17:48:59","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-11 17:48:59","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-11 17:48:59","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-11 17:48:59","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 09:32:25","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 09:32:25","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 09:32:25","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 09:32:25","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 09:53:14","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 09:53:15","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 09:53:15","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 09:53:15","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 13:48:16","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 13:48:16","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 13:48:16","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 13:48:16","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 13:53:11","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 13:53:12","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 13:53:12","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 13:53:12","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 14:14:51","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 14:14:51","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 14:14:51","vote_time2":"2018年05月投票","icon_url":"","imgs":""},{"vote_time":"2018-05-22 14:14:51","vote_time2":"2018年05月投票","icon_url":"","imgs":""}]
     * total_count :
     */

    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private String data;
    private String totalNum;
    private int point;
    private String info_list;
    private String total_count;
    private List<InfoHistorylistBean> info_historylist;

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

    public String getInfo_list() {
        return info_list;
    }

    public void setInfo_list(String info_list) {
        this.info_list = info_list;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public List<InfoHistorylistBean> getInfo_historylist() {
        return info_historylist;
    }

    public void setInfo_historylist(List<InfoHistorylistBean> info_historylist) {
        this.info_historylist = info_historylist;
    }

    public static class InfoHistorylistBean {
        /**
         * vote_time : 2018-05-11 17:25:02
         * vote_time2 : 2018年05月投票
         * icon_url :
         * imgs :
         */

        private String vote_time;
        private String vote_time2;
        private String icon_url;
        private String imgs;

        public String getVote_time() {
            return vote_time;
        }

        public void setVote_time(String vote_time) {
            this.vote_time = vote_time;
        }

        public String getVote_time2() {
            return vote_time2;
        }

        public void setVote_time2(String vote_time2) {
            this.vote_time2 = vote_time2;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }
    }
}
