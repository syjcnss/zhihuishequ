package com.ovu.lido.bean;

import java.util.List;

public class ConveniencePhoneInfo {

    /**
     * serialNo :
     * timestamp : 20180508163002
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data : [{"img_path":"info_type_img_path","id":4,"phone":"027-88888888","title":"物业管理处"},{"img_path":"info_type_img_path","id":6,"phone":"027-55555555","title":"下水道疏通"},{"img_path":"info_type_img_path/201707/20170706135323_357.jpg","id":136,"phone":"027-66666666","title":"绿化"},{"img_path":"info_type_img_path","id":137,"phone":"027-87654321","title":"业委会"},{"img_path":"info_type_img_path/201707/20170706135313_90.jpg","id":243,"phone":"027-12345678","title":"社区民警"},{"img_path":"info_type_img_path/201707/20170706135253_576.jpg","id":244,"phone":"027-43218765","title":"居委会"}]
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
         * img_path : info_type_img_path
         * id : 4
         * phone : 027-88888888
         * title : 物业管理处
         */

        private String img_path;
        private int id;
        private String phone;
        private String title;

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
