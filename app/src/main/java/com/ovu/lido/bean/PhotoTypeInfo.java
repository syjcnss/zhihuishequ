package com.ovu.lido.bean;

import java.util.List;

public class PhotoTypeInfo {

    /**
     * serialNo :
     * timestamp : 20190625152128
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data : {"start":0,"pageSize":100,"pageTotal":1,"totalCount":4,"data":[{"fileName":"测试域下添加","lastUpdateId":"","createTime":"","creatorId":"","id":26,"fileDescribe":"343","parkId":"","lastUpdateTime":""},{"fileName":"其他文件test","lastUpdateId":"","createTime":"","creatorId":"","id":25,"fileDescribe":"文件描述文件描述文件描述文件描述文件描述","parkId":"","lastUpdateTime":""},{"fileName":"证件文件A","lastUpdateId":"","createTime":"","creatorId":"","id":24,"fileDescribe":"证件文件A证件文件A","parkId":"","lastUpdateTime":""},{"fileName":"身份证test1","lastUpdateId":"","createTime":"","creatorId":"","id":22,"fileDescribe":"文件描述","parkId":"","lastUpdateTime":""}],"pageIndex":0}
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
         * start : 0
         * pageSize : 100
         * pageTotal : 1
         * totalCount : 4
         * data : [{"fileName":"测试域下添加","lastUpdateId":"","createTime":"","creatorId":"","id":26,"fileDescribe":"343","parkId":"","lastUpdateTime":""},{"fileName":"其他文件test","lastUpdateId":"","createTime":"","creatorId":"","id":25,"fileDescribe":"文件描述文件描述文件描述文件描述文件描述","parkId":"","lastUpdateTime":""},{"fileName":"证件文件A","lastUpdateId":"","createTime":"","creatorId":"","id":24,"fileDescribe":"证件文件A证件文件A","parkId":"","lastUpdateTime":""},{"fileName":"身份证test1","lastUpdateId":"","createTime":"","creatorId":"","id":22,"fileDescribe":"文件描述","parkId":"","lastUpdateTime":""}]
         * pageIndex : 0
         */

        private int start;
        private int pageSize;
        private int pageTotal;
        private int totalCount;
        private int pageIndex;
        private List<DataBean> data;

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

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
             * fileName : 测试域下添加
             * lastUpdateId :
             * createTime :
             * creatorId :
             * id : 26
             * fileDescribe : 343
             * parkId :
             * lastUpdateTime :
             */

            private String fileName;
            private String lastUpdateId;
            private String createTime;
            private String creatorId;
            private int id;
            private String fileDescribe;
            private String parkId;
            private String lastUpdateTime;
            private String certificate_id;

            public String getCertificate_id() {
                return certificate_id;
            }

            public void setCertificate_id(String certificate_id) {
                this.certificate_id = certificate_id;
            }

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
            }

            public String getLastUpdateId() {
                return lastUpdateId;
            }

            public void setLastUpdateId(String lastUpdateId) {
                this.lastUpdateId = lastUpdateId;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCreatorId() {
                return creatorId;
            }

            public void setCreatorId(String creatorId) {
                this.creatorId = creatorId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFileDescribe() {
                return fileDescribe;
            }

            public void setFileDescribe(String fileDescribe) {
                this.fileDescribe = fileDescribe;
            }

            public String getParkId() {
                return parkId;
            }

            public void setParkId(String parkId) {
                this.parkId = parkId;
            }

            public String getLastUpdateTime() {
                return lastUpdateTime;
            }

            public void setLastUpdateTime(String lastUpdateTime) {
                this.lastUpdateTime = lastUpdateTime;
            }
        }
    }
}
