package com.ovu.lido.bean;

import java.util.List;

public class ResidentCarListInfo {

    private String timestamp;
    private String errorCode;
    private String errorMsg;
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
         * car_type : 车辆型号
         * plate_number : 车牌号
         */

        private String car_type;
        private String plate_number;

        public String getCar_type() {
            return car_type;
        }

        public void setCar_type(String car_type) {
            this.car_type = car_type;
        }

        public String getPlate_number() {
            return plate_number;
        }

        public void setPlate_number(String plate_number) {
            this.plate_number = plate_number;
        }

        @Override
        public String toString() {
            return "{\"car_type\":\""
                    + car_type
                    + "\",\"plate_number\":\""
                    + plate_number + "\"}";
        }
    }
}
