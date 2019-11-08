package com.ovu.lido.bean;

import java.util.List;

public class ServiceTeamInfo {


    /**
     * serialNo :
     * timestamp : 20180522182221
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data :
     * totalNum :
     * point : 0
     * info_list : [{"id":2,"name":"李四","depart":"人力资源部","position":"网格管理员","url":"http://172.16.205.6:8210/img/20180508/2.jpg","communityId":"","createDate":"","createName":"","updateName":"","updateDate":"","content":"cs","count0":"","count1":"","count2":"","percentageSatisfactions":"100.0%","type":"1","icon_url":"","imgs":""},{"id":"","name":"张三","depart":"人力资源部","position":"网格管理员","url":"http://172.16.205.6:8210/img/20180508/3.jpg","communityId":"","createDate":"","createName":"","updateName":"","updateDate":"","content":"cs","count0":"","count1":"","count2":"","percentageSatisfactions":"100.0%","type":"","icon_url":"","imgs":""}]
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
    private String total_count;
    private List<InfoListBean> info_list;

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

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public List<InfoListBean> getInfo_list() {
        return info_list;
    }

    public void setInfo_list(List<InfoListBean> info_list) {
        this.info_list = info_list;
    }

    public static class InfoListBean {
        /**
         * id : 2
         * name : 李四
         * depart : 人力资源部
         * position : 网格管理员
         * url : http://172.16.205.6:8210/img/20180508/2.jpg
         * communityId :
         * createDate :
         * createName :
         * updateName :
         * updateDate :
         * content : cs
         * count0 :
         * count1 :
         * count2 :
         * percentageSatisfactions : 100.0%
         * type : 1
         * icon_url :
         * imgs :
         */

        private int id;
        private String name;
        private String depart;
        private String position;
        private String url;
        private String communityId;
        private String createDate;
        private String createName;
        private String updateName;
        private String updateDate;
        private String content;
        private String count0;
        private String count1;
        private String count2;
        private String percentageSatisfactions;
        private String type;
        private String icon_url;
        private String imgs;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDepart() {
            return depart;
        }

        public void setDepart(String depart) {
            this.depart = depart;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCommunityId() {
            return communityId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getUpdateName() {
            return updateName;
        }

        public void setUpdateName(String updateName) {
            this.updateName = updateName;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCount0() {
            return count0;
        }

        public void setCount0(String count0) {
            this.count0 = count0;
        }

        public String getCount1() {
            return count1;
        }

        public void setCount1(String count1) {
            this.count1 = count1;
        }

        public String getCount2() {
            return count2;
        }

        public void setCount2(String count2) {
            this.count2 = count2;
        }

        public String getPercentageSatisfactions() {
            return percentageSatisfactions;
        }

        public void setPercentageSatisfactions(String percentageSatisfactions) {
            this.percentageSatisfactions = percentageSatisfactions;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
