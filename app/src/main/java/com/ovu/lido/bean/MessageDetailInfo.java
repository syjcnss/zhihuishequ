package com.ovu.lido.bean;

public class MessageDetailInfo {

    /**
     * timestamp : 20180528153027
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : 77e78f6c54674c348e59ca33f9ab7fc0
     * data : {"id":86,"resident_id":214023,"message_content":"您已成功创建工单，请耐心等待物业人员接单。","message_time":"2018-05-26 14:49:35","message_type":2,"message_status":1,"message_rank":1}
     * point : 0
     */

    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private Object hash;
    private String token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
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
         * id : 86
         * resident_id : 214023
         * message_content : 您已成功创建工单，请耐心等待物业人员接单。
         * message_time : 2018-05-26 14:49:35
         * message_type : 2
         * message_status : 1
         * message_rank : 1
         */

        private int id;
        private int resident_id;
        private String message_content;
        private String message_time;
        private int message_type;
        private int message_status;
        private int message_rank;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getResident_id() {
            return resident_id;
        }

        public void setResident_id(int resident_id) {
            this.resident_id = resident_id;
        }

        public String getMessage_content() {
            return message_content;
        }

        public void setMessage_content(String message_content) {
            this.message_content = message_content;
        }

        public String getMessage_time() {
            return message_time;
        }

        public void setMessage_time(String message_time) {
            this.message_time = message_time;
        }

        public int getMessage_type() {
            return message_type;
        }

        public void setMessage_type(int message_type) {
            this.message_type = message_type;
        }

        public int getMessage_status() {
            return message_status;
        }

        public void setMessage_status(int message_status) {
            this.message_status = message_status;
        }

        public int getMessage_rank() {
            return message_rank;
        }

        public void setMessage_rank(int message_rank) {
            this.message_rank = message_rank;
        }
    }
}
