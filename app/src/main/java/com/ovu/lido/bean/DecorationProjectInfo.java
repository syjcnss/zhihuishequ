package com.ovu.lido.bean;

import java.util.List;

public class DecorationProjectInfo {
    /**
     * serialNo :
     * timestamp : 20180523165837
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data : [{"creatorId":"","id":49,"createTime":"","projectDesc":"dd","parkId":"","projectName":"dd","lastUpdateTime":"","lastUpdateId":""},{"creatorId":"","id":48,"createTime":"","projectDesc":"tttt","parkId":"","projectName":"ttt","lastUpdateTime":"","lastUpdateId":""},{"creatorId":"","id":47,"createTime":"","projectDesc":"eeeee\ne\ne\ne\ne","parkId":"","projectName":"eee","lastUpdateTime":"","lastUpdateId":""},{"creatorId":"","id":45,"createTime":"","projectDesc":"水电燃气改造","parkId":"","projectName":"水电燃气改造2018","lastUpdateTime":"","lastUpdateId":""}]
     * totalNum :
     * point : 0
     */

    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private String totalNum;
    private int point;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * creatorId :
         * id : 49
         * createTime :
         * projectDesc : dd
         * parkId :
         * projectName : dd
         * lastUpdateTime :
         * lastUpdateId :
         */

        private boolean select;
        private String creatorId;
        private int id;
        private String createTime;
        private String projectDesc;
        private String parkId;
        private String projectName;
        private String lastUpdateTime;
        private String lastUpdateId;

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
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

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getProjectDesc() {
            return projectDesc;
        }

        public void setProjectDesc(String projectDesc) {
            this.projectDesc = projectDesc;
        }

        public String getParkId() {
            return parkId;
        }

        public void setParkId(String parkId) {
            this.parkId = parkId;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(String lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public String getLastUpdateId() {
            return lastUpdateId;
        }

        public void setLastUpdateId(String lastUpdateId) {
            this.lastUpdateId = lastUpdateId;
        }
    }


//    /**
//     * serialNo :
//     * timestamp : 20180507101617
//     * errorCode : 0000
//     * errorMsg : 正确返回
//     * hash :
//     * data : [{"type_name":"铝合金门窗安装","type_id":22},{"type_name":"广告招牌安装","type_id":23},{"type_name":"供暖工程","type_id":24},{"type_name":"消防工程","type_id":25},{"type_name":"电焊作业","type_id":26},{"type_name":"墙面批灰粉刷","type_id":27},{"type_name":"油漆","type_id":28},{"type_name":"木工制作","type_id":29},{"type_name":"白蚁防治","type_id":30},{"type_name":"防水工程","type_id":31},{"type_name":"楼板加建","type_id":32},{"type_name":"空调安装","type_id":33},{"type_name":"水电安装","type_id":34},{"type_name":"智能化改动","type_id":35},{"type_name":"卫生间改动","type_id":36},{"type_name":"厨房改动","type_id":37},{"type_name":"拆墙","type_id":38}]
//     * totalNum :
//     * point : 0
//     */
//
//    private String serialNo;
//    private String timestamp;
//    private String errorCode;
//    private String errorMsg;
//    private String hash;
//    private String totalNum;
//    private int point;
//    private List<DataBean> data;
//
//    public String getSerialNo() {
//        return serialNo;
//    }
//
//    public void setSerialNo(String serialNo) {
//        this.serialNo = serialNo;
//    }
//
//    public String getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(String timestamp) {
//        this.timestamp = timestamp;
//    }
//
//    public String getErrorCode() {
//        return errorCode;
//    }
//
//    public void setErrorCode(String errorCode) {
//        this.errorCode = errorCode;
//    }
//
//    public String getErrorMsg() {
//        return errorMsg;
//    }
//
//    public void setErrorMsg(String errorMsg) {
//        this.errorMsg = errorMsg;
//    }
//
//    public String getHash() {
//        return hash;
//    }
//
//    public void setHash(String hash) {
//        this.hash = hash;
//    }
//
//    public String getTotalNum() {
//        return totalNum;
//    }
//
//    public void setTotalNum(String totalNum) {
//        this.totalNum = totalNum;
//    }
//
//    public int getPoint() {
//        return point;
//    }
//
//    public void setPoint(int point) {
//        this.point = point;
//    }
//
//    public List<DataBean> getData() {
//        return data;
//    }
//
//    public void setData(List<DataBean> data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
//        /**
//         * type_name : 铝合金门窗安装
//         * type_id : 22
//         */
//        private boolean select;
//        private String type_name;
//        private int type_id;
//
//        public boolean isSelect() {
//            return select;
//        }
//
//        public void setSelect(boolean select) {
//            this.select = select;
//        }
//
//        public String getType_name() {
//            return type_name;
//        }
//
//        public void setType_name(String type_name) {
//            this.type_name = type_name;
//        }
//
//        public int getType_id() {
//            return type_id;
//        }
//
//        public void setType_id(int type_id) {
//            this.type_id = type_id;
//        }
//    }
}
