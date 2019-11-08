package com.ovu.lido.bean;

import java.util.List;

public class WorkOrderDetailInfo {

    /**
     * serialNo :
     * timestamp : 20180526142758
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data : {"neighborhood_id":34,"Content":"您已成功创建工单，请耐心等待物业人员接单","DataList":{"eventAddr":"null","reportPicture":"D:/java/javaSource/img/2018052610385500430.png,D:/java/javaSource/img/20180526103855010536.png","ownerName":"656","parkId":"0292b000b2f744d982d7926e35b87b57","worktypeId":"8e3c12ea6e93424a912cb7cb764d7508","ownerId":"1c30c4832dce4b0fa625a61d5685d49a","reportTime":"2018-05-26 10:38:51","id":"P20180526000007","parkName":"项目1","description":"维保详情。。。。。。。。。","workStatus":1,"worktypeName":"设施设备巡检","worktunitName":"维保详情。。。。。。。。。201805261038","importantLevel":1},"wotreeList":[{"time":"2018-05-25","content":"您已成功创建工单，请耐心等待物业人员接单","employee_name":""}]}
     * totalNum :
     * point : 0
     */

    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private DataBean data;
    private String totalNum;
    private int point;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        /**
         * neighborhood_id : 34
         * Content : 您已成功创建工单，请耐心等待物业人员接单
         * DataList : {"eventAddr":"null","reportPicture":"D:/java/javaSource/img/2018052610385500430.png,D:/java/javaSource/img/20180526103855010536.png","ownerName":"656","parkId":"0292b000b2f744d982d7926e35b87b57","worktypeId":"8e3c12ea6e93424a912cb7cb764d7508","ownerId":"1c30c4832dce4b0fa625a61d5685d49a","reportTime":"2018-05-26 10:38:51","id":"P20180526000007","parkName":"项目1","description":"维保详情。。。。。。。。。","workStatus":1,"worktypeName":"设施设备巡检","worktunitName":"维保详情。。。。。。。。。201805261038","importantLevel":1}
         * wotreeList : [{"time":"2018-05-25","content":"您已成功创建工单，请耐心等待物业人员接单","employee_name":""}]
         */

        private int neighborhood_id;
        private String Content;
        private DataListBean DataList;
        private List<WotreeListBean> wotreeList;

        public int getNeighborhood_id() {
            return neighborhood_id;
        }

        public void setNeighborhood_id(int neighborhood_id) {
            this.neighborhood_id = neighborhood_id;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public DataListBean getDataList() {
            return DataList;
        }

        public void setDataList(DataListBean DataList) {
            this.DataList = DataList;
        }

        public List<WotreeListBean> getWotreeList() {
            return wotreeList;
        }

        public void setWotreeList(List<WotreeListBean> wotreeList) {
            this.wotreeList = wotreeList;
        }

        public static class DataListBean {
            /**
             * eventAddr : null
             * reportPicture : D:/java/javaSource/img/2018052610385500430.png,D:/java/javaSource/img/20180526103855010536.png
             * ownerName : 656
             * parkId : 0292b000b2f744d982d7926e35b87b57
             * worktypeId : 8e3c12ea6e93424a912cb7cb764d7508
             * ownerId : 1c30c4832dce4b0fa625a61d5685d49a
             * reportTime : 2018-05-26 10:38:51
             * id : P20180526000007
             * parkName : 项目1
             * description : 维保详情。。。。。。。。。
             * workStatus : 1
             * worktypeName : 设施设备巡检
             * worktunitName : 维保详情。。。。。。。。。201805261038
             * importantLevel : 1
             */

            private String eventAddr;
            private String reportPicture;
            private String ownerName;
            private String parkId;
            private String worktypeId;
            private String ownerId;
            private String reportTime;
            private String id;
            private String parkName;
            private String description;
            private int workStatus;
            private String worktypeName;
            private String worktunitName;
            private int importantLevel;
            private String finishPeson; //执行人
            private String finishComment;//执行人备注
            private String finishPhoto;//完成时上传的图片

            public String getFinishPeson() {
                return finishPeson;
            }

            public void setFinishPeson(String finishPeson) {
                this.finishPeson = finishPeson;
            }

            public String getFinishComment() {
                return finishComment;
            }

            public void setFinishComment(String finishComment) {
                this.finishComment = finishComment;
            }

            public String getFinishPhoto() {
                return finishPhoto;
            }

            public void setFinishPhoto(String finishPhoto) {
                this.finishPhoto = finishPhoto;
            }

            public String getEventAddr() {
                return eventAddr;
            }

            public void setEventAddr(String eventAddr) {
                this.eventAddr = eventAddr;
            }

            public String getReportPicture() {
                return reportPicture;
            }

            public void setReportPicture(String reportPicture) {
                this.reportPicture = reportPicture;
            }

            public String getOwnerName() {
                return ownerName;
            }

            public void setOwnerName(String ownerName) {
                this.ownerName = ownerName;
            }

            public String getParkId() {
                return parkId;
            }

            public void setParkId(String parkId) {
                this.parkId = parkId;
            }

            public String getWorktypeId() {
                return worktypeId;
            }

            public void setWorktypeId(String worktypeId) {
                this.worktypeId = worktypeId;
            }

            public String getOwnerId() {
                return ownerId;
            }

            public void setOwnerId(String ownerId) {
                this.ownerId = ownerId;
            }

            public String getReportTime() {
                return reportTime;
            }

            public void setReportTime(String reportTime) {
                this.reportTime = reportTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getParkName() {
                return parkName;
            }

            public void setParkName(String parkName) {
                this.parkName = parkName;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getWorkStatus() {
                return workStatus;
            }

            public void setWorkStatus(int workStatus) {
                this.workStatus = workStatus;
            }

            public String getWorktypeName() {
                return worktypeName;
            }

            public void setWorktypeName(String worktypeName) {
                this.worktypeName = worktypeName;
            }

            public String getWorktunitName() {
                return worktunitName;
            }

            public void setWorktunitName(String worktunitName) {
                this.worktunitName = worktunitName;
            }

            public int getImportantLevel() {
                return importantLevel;
            }

            public void setImportantLevel(int importantLevel) {
                this.importantLevel = importantLevel;
            }
        }

        public static class WotreeListBean {
            /**
             * time : 2018-05-25
             * content : 您已成功创建工单，请耐心等待物业人员接单
             * employee_name :
             */

            private String time;
            private String content;
            private String employee_name;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getEmployee_name() {
                return employee_name;
            }

            public void setEmployee_name(String employee_name) {
                this.employee_name = employee_name;
            }
        }
    }
}
