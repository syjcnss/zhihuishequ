package com.ovu.lido.bean;

import java.util.List;

public class DecorationHistoryInfo {


    /**
     * serialNo :
     * timestamp : 20180524190559
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data : [{"createTime":"2018-05-24 11:07:17","workerTel":"18972821011","itemName":"","approveDesc":"","ownerName":"庞雄俊","parkId":"","updateTime":"","ownerId":"","creatorId":"","decorationName":"45","updateId":"","certificateId":"45","ownerTel":"18972821092","roomId":"","suggest":"","applyStatus":2,"decorationCompany":"齐家装修公司","id":150,"roomName":"","domainId":"","decorationItems":"45","workerName":"张三","applyerType":"","certificateUrl":"D:/java/javaSource/img/20180524110720359415.png"},{"createTime":"2018-05-24 10:58:24","workerTel":"18972821011","itemName":"","approveDesc":"","ownerName":"庞雄俊","parkId":"","updateTime":"","ownerId":"","creatorId":"","decorationName":"48,47","updateId":"","certificateId":"48,47","ownerTel":"18972821092","roomId":"","suggest":"","applyStatus":2,"decorationCompany":"齐家装修公司","id":149,"roomName":"","domainId":"","decorationItems":"48,47","workerName":"张三","applyerType":"","certificateUrl":"D:/java/javaSource/img/2018052410582795061.png"}]
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
         * createTime : 2018-05-24 11:07:17
         * workerTel : 18972821011
         * itemName :
         * approveDesc :
         * ownerName : 庞雄俊
         * parkId :
         * updateTime :
         * ownerId :
         * creatorId :
         * decorationName : 45
         * updateId :
         * certificateId : 45
         * ownerTel : 18972821092
         * roomId :
         * suggest :
         * applyStatus : 2
         * decorationCompany : 齐家装修公司
         * id : 150
         * roomName :
         * domainId :
         * decorationItems : 45
         * workerName : 张三
         * applyerType :
         * certificateUrl : D:/java/javaSource/img/20180524110720359415.png
         */

        private String createTime;
        private String workerTel;
        private String itemName;
        private String approveDesc;
        private String ownerName;
        private String parkId;
        private String updateTime;
        private String ownerId;
        private String creatorId;
        private String decorationName;
        private String updateId;
        private String certificateId;
        private String ownerTel;
        private String roomId;
        private String suggest;
        private int applyStatus;
        private String decorationCompany;
        private int id;
        private String roomName;
        private String domainId;
        private String decorationItems;
        private String workerName;
        private String applyerType;
        private String certificateUrl;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getWorkerTel() {
            return workerTel;
        }

        public void setWorkerTel(String workerTel) {
            this.workerTel = workerTel;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getApproveDesc() {
            return approveDesc;
        }

        public void setApproveDesc(String approveDesc) {
            this.approveDesc = approveDesc;
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

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
        }

        public String getDecorationName() {
            return decorationName;
        }

        public void setDecorationName(String decorationName) {
            this.decorationName = decorationName;
        }

        public String getUpdateId() {
            return updateId;
        }

        public void setUpdateId(String updateId) {
            this.updateId = updateId;
        }

        public String getCertificateId() {
            return certificateId;
        }

        public void setCertificateId(String certificateId) {
            this.certificateId = certificateId;
        }

        public String getOwnerTel() {
            return ownerTel;
        }

        public void setOwnerTel(String ownerTel) {
            this.ownerTel = ownerTel;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getSuggest() {
            return suggest;
        }

        public void setSuggest(String suggest) {
            this.suggest = suggest;
        }

        public int getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(int applyStatus) {
            this.applyStatus = applyStatus;
        }

        public String getDecorationCompany() {
            return decorationCompany;
        }

        public void setDecorationCompany(String decorationCompany) {
            this.decorationCompany = decorationCompany;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public String getDomainId() {
            return domainId;
        }

        public void setDomainId(String domainId) {
            this.domainId = domainId;
        }

        public String getDecorationItems() {
            return decorationItems;
        }

        public void setDecorationItems(String decorationItems) {
            this.decorationItems = decorationItems;
        }

        public String getWorkerName() {
            return workerName;
        }

        public void setWorkerName(String workerName) {
            this.workerName = workerName;
        }

        public String getApplyerType() {
            return applyerType;
        }

        public void setApplyerType(String applyerType) {
            this.applyerType = applyerType;
        }

        public String getCertificateUrl() {
            return certificateUrl;
        }

        public void setCertificateUrl(String certificateUrl) {
            this.certificateUrl = certificateUrl;
        }
    }
}
