package com.ovu.lido.bean;

import java.util.List;

public class JoinQuestionListInfo {

    /**
     * timestamp : 20180524173826
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : [{"id":55,"content":"速度 快点处理问题","createTime":1527152905000,"parkId":"0292b000b2f744d982d7926e35b87b57","checkTypeName":"土建类","domainId":"14bdbea59d2c4b0a96594fb94382901e","creatorId":"1c30c4832dce4b0fa625a61d5685d49a","isHandle":1,"modifyTime":1527153036000},{"id":54,"content":"验房测试PAng","createTime":1527152477000,"parkId":"0292b000b2f744d982d7926e35b87b57","checkTypeName":"土建类","domainId":"14bdbea59d2c4b0a96594fb94382901e","creatorId":"1c30c4832dce4b0fa625a61d5685d49a","isHandle":1,"modifyTime":1527152503000},{"id":53,"content":"验房测试PAng","createTime":1527152418000,"parkId":"0292b000b2f744d982d7926e35b87b57","checkTypeName":"土建类","domainId":"14bdbea59d2c4b0a96594fb94382901e","creatorId":"1c30c4832dce4b0fa625a61d5685d49a","isHandle":0,"modifyTime":1527152502000},{"id":52,"content":"验房测试PAng","createTime":1527152379000,"parkId":"0292b000b2f744d982d7926e35b87b57","checkTypeName":"土建类","domainId":"14bdbea59d2c4b0a96594fb94382901e","creatorId":"1c30c4832dce4b0fa625a61d5685d49a","isHandle":0,"modifyTime":1527152501000},{"id":51,"content":"验房测试PAng","createTime":1527152198000,"parkId":"0292b000b2f744d982d7926e35b87b57","checkTypeName":"土建类","domainId":"14bdbea59d2c4b0a96594fb94382901e","creatorId":"1c30c4832dce4b0fa625a61d5685d49a","isHandle":0,"modifyTime":1527152349000},{"id":49,"content":"验房测试PAng","createTime":1527148382000,"parkId":"0292b000b2f744d982d7926e35b87b57","checkTypeName":"土建类","domainId":"14bdbea59d2c4b0a96594fb94382901e","creatorId":"1c30c4832dce4b0fa625a61d5685d49a","isHandle":0,"modifyTime":1527152347000}]
     * point : 0
     */

    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private Object hash;
    private Object token;
    private int point;
    private List<DataBean> data;

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
         * id : 55
         * content : 速度 快点处理问题
         * createTime : 1527152905000
         * parkId : 0292b000b2f744d982d7926e35b87b57
         * checkTypeName : 土建类
         * domainId : 14bdbea59d2c4b0a96594fb94382901e
         * creatorId : 1c30c4832dce4b0fa625a61d5685d49a
         * isHandle : 1
         * modifyTime : 1527153036000
         */

        private int id;
        private String content;
        private String createTime;
        private String parkId;
        private String checkTypeName;
        private String domainId;
        private String creatorId;
        private int isHandle;
        private String modifyTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getCheckTypeName() {
            return checkTypeName;
        }

        public void setCheckTypeName(String checkTypeName) {
            this.checkTypeName = checkTypeName;
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

        public int getIsHandle() {
            return isHandle;
        }

        public void setIsHandle(int isHandle) {
            this.isHandle = isHandle;
        }

        public String getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(String modifyTime) {
            this.modifyTime = modifyTime;
        }
    }
}
