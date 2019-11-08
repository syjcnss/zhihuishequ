package com.ovu.lido.bean;

import java.util.List;

public class AwardsRecordInfo {

    /**
     * timestamp : 20180605094332
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : [{"awards_name":"200积分","phone":"18702788789"},{"awards_name":"200积分","phone":"18572838816"},{"awards_name":"200积分","phone":"17182715555"},{"awards_name":"200积分","phone":"13071285860"},{"awards_name":"200积分","phone":"13886115896"},{"awards_name":"200积分","phone":"15327105576"},{"awards_name":"200积分","phone":"15337157191"},{"awards_name":"200积分","phone":"15327105576"},{"awards_name":"200积分","phone":"18907182529"},{"awards_name":"200积分","phone":"13212711150"}]
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
         * awards_name : 200积分
         * phone : 18702788789
         */

        private String awards_name;
        private String phone;

        public String getAwards_name() {
            return awards_name;
        }

        public void setAwards_name(String awards_name) {
            this.awards_name = awards_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
