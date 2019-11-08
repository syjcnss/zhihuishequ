package com.ovu.lido.bean;

import java.util.List;

public class GridAdministratorInfo {

    /**
     * serialNo :
     * timestamp : 20180512161457
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data :
     * totalNum :
     * point : 0
     * list : [{"gridkeeper_id":"","gridkeeper_name":"李xiao好","gridkeeper_tel":"18624567581","gridkeeper_intro":"区优秀党员干部，劳动模范代表，曾受全国人大邀请。","gridkeeper_pic":"http://120.27.196.188/community/info_img_path//img/201606/20160613150613_502.png","community_id":34,"communityName":""}]
     */

    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private String data;
    private String totalNum;
    private int point;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * gridkeeper_id :
         * gridkeeper_name : 李xiao好
         * gridkeeper_tel : 18624567581
         * gridkeeper_intro : 区优秀党员干部，劳动模范代表，曾受全国人大邀请。
         * gridkeeper_pic : http://120.27.196.188/community/info_img_path//img/201606/20160613150613_502.png
         * community_id : 34
         * communityName :
         */

        private String gridkeeper_id;
        private String gridkeeper_name;
        private String gridkeeper_tel;
        private String gridkeeper_intro;
        private String gridkeeper_pic;
        private int community_id;
        private String communityName;

        public String getGridkeeper_id() {
            return gridkeeper_id;
        }

        public void setGridkeeper_id(String gridkeeper_id) {
            this.gridkeeper_id = gridkeeper_id;
        }

        public String getGridkeeper_name() {
            return gridkeeper_name;
        }

        public void setGridkeeper_name(String gridkeeper_name) {
            this.gridkeeper_name = gridkeeper_name;
        }

        public String getGridkeeper_tel() {
            return gridkeeper_tel;
        }

        public void setGridkeeper_tel(String gridkeeper_tel) {
            this.gridkeeper_tel = gridkeeper_tel;
        }

        public String getGridkeeper_intro() {
            return gridkeeper_intro;
        }

        public void setGridkeeper_intro(String gridkeeper_intro) {
            this.gridkeeper_intro = gridkeeper_intro;
        }

        public String getGridkeeper_pic() {
            return gridkeeper_pic;
        }

        public void setGridkeeper_pic(String gridkeeper_pic) {
            this.gridkeeper_pic = gridkeeper_pic;
        }

        public int getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(int community_id) {
            this.community_id = community_id;
        }

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }
    }
}
