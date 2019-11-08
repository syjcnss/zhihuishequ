package com.ovu.lido.bean;

import java.util.List;

public class ProgressRateInfo {

    /**
     * serialNo :
     * timestamp : 20180719143434
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data : {"pageSize":1,"data":[{"workStatus":1,"parkName":"丽岛花园","worktypeId":"737944c48eb84f20a905b305b35f0a5c","importantLevel":1,"description":"重新发不 -- 2018-07-19  15:00-16:00","reportPicture":"/home/data/app/img/20180719111923564924.png","parkId":"1796967f42fe4f14b0eb49807c5fc250","worktypeName":"工程","workDate":"2018-07-19 11:19:23","worktunitName":"重新发不 -- 2018-07-19  15:00-16:00201807191119","id":"P20180719000004","eventAddr":"null","reportTime":"2018-07-19 11:19:23"}],"pageTotal":15,"totalCount":15,"pageIndex":0}
     * totalNum :
     * point : 0
     */

    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private DataBeanX data;
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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
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

    public static class DataBeanX {
        /**
         * pageSize : 1
         * data : [{"workStatus":1,"parkName":"丽岛花园","worktypeId":"737944c48eb84f20a905b305b35f0a5c","importantLevel":1,"description":"重新发不 -- 2018-07-19  15:00-16:00","reportPicture":"/home/data/app/img/20180719111923564924.png","parkId":"1796967f42fe4f14b0eb49807c5fc250","worktypeName":"工程","workDate":"2018-07-19 11:19:23","worktunitName":"重新发不 -- 2018-07-19  15:00-16:00201807191119","id":"P20180719000004","eventAddr":"null","reportTime":"2018-07-19 11:19:23"}]
         * pageTotal : 15
         * totalCount : 15
         * pageIndex : 0
         */

        private int pageSize;
        private int pageTotal;
        private int totalCount;
        private int pageIndex;
        private List<DataBean> data;

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageTotal() {
            return pageTotal;
        }

        public void setPageTotal(int pageTotal) {
            this.pageTotal = pageTotal;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * workStatus : 1
             * parkName : 丽岛花园
             * worktypeId : 737944c48eb84f20a905b305b35f0a5c
             * importantLevel : 1
             * description : 重新发不 -- 2018-07-19  15:00-16:00
             * reportPicture : /home/data/app/img/20180719111923564924.png
             * parkId : 1796967f42fe4f14b0eb49807c5fc250
             * worktypeName : 工程
             * workDate : 2018-07-19 11:19:23
             * worktunitName : 重新发不 -- 2018-07-19  15:00-16:00201807191119
             * id : P20180719000004
             * eventAddr : null
             * reportTime : 2018-07-19 11:19:23
             */

            private int workStatus;
            private String parkName;
            private String worktypeId;
            private int importantLevel;
            private String description;
            private String reportPicture;
            private String parkId;
            private String worktypeName;
            private String workDate;
            private String worktunitName;
            private String id;
            private String eventAddr;
            private String reportTime;
            private String finishTime;

            public String getFinishTime() {
                return finishTime;
            }

            public void setFinishTime(String finishTime) {
                this.finishTime = finishTime;
            }

            public int getWorkStatus() {
                return workStatus;
            }

            public void setWorkStatus(int workStatus) {
                this.workStatus = workStatus;
            }

            public String getParkName() {
                return parkName;
            }

            public void setParkName(String parkName) {
                this.parkName = parkName;
            }

            public String getWorktypeId() {
                return worktypeId;
            }

            public void setWorktypeId(String worktypeId) {
                this.worktypeId = worktypeId;
            }

            public int getImportantLevel() {
                return importantLevel;
            }

            public void setImportantLevel(int importantLevel) {
                this.importantLevel = importantLevel;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getReportPicture() {
                return reportPicture;
            }

            public void setReportPicture(String reportPicture) {
                this.reportPicture = reportPicture;
            }

            public String getParkId() {
                return parkId;
            }

            public void setParkId(String parkId) {
                this.parkId = parkId;
            }

            public String getWorktypeName() {
                return worktypeName;
            }

            public void setWorktypeName(String worktypeName) {
                this.worktypeName = worktypeName;
            }

            public String getWorkDate() {
                return workDate;
            }

            public void setWorkDate(String workDate) {
                this.workDate = workDate;
            }

            public String getWorktunitName() {
                return worktunitName;
            }

            public void setWorktunitName(String worktunitName) {
                this.worktunitName = worktunitName;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getEventAddr() {
                return eventAddr;
            }

            public void setEventAddr(String eventAddr) {
                this.eventAddr = eventAddr;
            }

            public String getReportTime() {
                return reportTime;
            }

            public void setReportTime(String reportTime) {
                this.reportTime = reportTime;
            }
        }
    }
}
