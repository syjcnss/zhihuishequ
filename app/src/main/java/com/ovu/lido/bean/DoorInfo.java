package com.ovu.lido.bean;

import java.util.List;

public class DoorInfo {

    /**
     * timestamp : null
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * point : 0
     * data : [{"id":"1","name":"锦绣江南-9栋2单元"},{"id":"2","name":"水域天际26栋1单元"},{"id":"3","name":"水域天际14栋2单元"},{"id":"4","name":"ccc"},{"id":"5","name":"PDemo"}]
     */

    private Object timestamp;
    private String errorCode;
    private String errorMsg;
    private Object hash;
    private int point;
    private List<DataBean> data;

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
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
         * id : 1
         * name : 锦绣江南-9栋2单元
         */
        private boolean select = false;
        private String id;
        private String name;

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
