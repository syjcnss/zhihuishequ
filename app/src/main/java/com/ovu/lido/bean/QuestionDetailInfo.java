package com.ovu.lido.bean;

public class QuestionDetailInfo {

    /**
     * timestamp : 20180525090959
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : {"id":43,"result":"sddddddddd","createTime":1527158980000,"parkId":"0292b000b2f744d982d7926e35b87b57","checkroomId":56,"domainId":"14bdbea59d2c4b0a96594fb94382901e","creatorId":"3c12996534dc4c0abfaac17cffd031d7","modifyTime":1527158980000}
     * point : 0
     */

    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private Object hash;
    private Object token;
    private DataBean data;
    private int point;

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

    public Object getHash() {
        return hash;
    }

    public void setHash(Object hash) {
        this.hash = hash;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public static class DataBean {
        /**
         * id : 43
         * result : sddddddddd
         * createTime : 1527158980000
         * parkId : 0292b000b2f744d982d7926e35b87b57
         * checkroomId : 56
         * domainId : 14bdbea59d2c4b0a96594fb94382901e
         * creatorId : 3c12996534dc4c0abfaac17cffd031d7
         * modifyTime : 1527158980000
         */

        private int id;
        private String result;
        private String createTime;
        private String parkId;
        private int checkroomId;
        private String domainId;
        private String creatorId;
        private String modifyTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getParkId() {
            return parkId;
        }

        public void setParkId(String parkId) {
            this.parkId = parkId;
        }

        public int getCheckroomId() {
            return checkroomId;
        }

        public void setCheckroomId(int checkroomId) {
            this.checkroomId = checkroomId;
        }

        public String getDomainId() {
            return domainId;
        }

        public void setDomainId(String domainId) {
            this.domainId = domainId;
        }

        public String getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(String creatorId) {
            this.creatorId = creatorId;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }
    }
}
