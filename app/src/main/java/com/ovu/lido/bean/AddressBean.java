package com.ovu.lido.bean;

import java.io.Serializable;
import java.util.List;

public class AddressBean implements Serializable {


    /**
     * serialNo :
     * timestamp : 20180511173959
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data :
     * totalNum :
     * point : 0
     * address_list : [{"address_id":"334","shipper":"卫细","mobile_no":"15623730598","zone_code":"420000","address_detail":"光谷步行街","is_default":"1"}]
     */

    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private String data;
    private String totalNum;
    private int point;
    private List<AddressListBean> address_list;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
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

    public List<AddressListBean> getAddress_list() {
        return address_list;
    }

    public void setAddress_list(List<AddressListBean> address_list) {
        this.address_list = address_list;
    }

    public static class AddressListBean implements Serializable {
        /**
         * address_id : 334
         * shipper : 卫细
         * mobile_no : 15623730598
         * zone_code : 420000
         * address_detail : 光谷步行街
         * is_default : 1
         */

        private String address_id;
        private String shipper;
        private String mobile_no;
        private String zone_code;
        private String address_detail;
        private String is_default;

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getShipper() {
            return shipper;
        }

        public void setShipper(String shipper) {
            this.shipper = shipper;
        }

        public String getMobile_no() {
            return mobile_no;
        }

        public void setMobile_no(String mobile_no) {
            this.mobile_no = mobile_no;
        }

        public String getZone_code() {
            return zone_code;
        }

        public void setZone_code(String zone_code) {
            this.zone_code = zone_code;
        }

        public String getAddress_detail() {
            return address_detail;
        }

        public void setAddress_detail(String address_detail) {
            this.address_detail = address_detail;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }
    }
}
