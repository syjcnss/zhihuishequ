package com.ovu.lido.bean;

import java.util.List;

public class LockInfo {

    /**
     * timestamp : null
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * point : 0
     * data : [{"id":"2c9abe5663aff7610163be5571a000bd","name":"Pdemo","device_wifi_mac":"","device_blue_mac":"3242544E7741377341","community_id":null,"ref_id":null,"ref_name":null,"is_qr_device":null,"device_qr_mac":null,"device_real_wifi_mac":"","device_key":null,"create_by":null,"create_time":null,"update_by":null,"update_time":null,"remark":null,"del_flag":"0","open_tag":"1","qr_create_time":null}]
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
         * id : 2c9abe5663aff7610163be5571a000bd
         * name : Pdemo
         * device_wifi_mac :
         * device_blue_mac : 3242544E7741377341
         * community_id : null
         * ref_id : null
         * ref_name : null
         * is_qr_device : null
         * device_qr_mac : null
         * device_real_wifi_mac :
         * device_key : null
         * create_by : null
         * create_time : null
         * update_by : null
         * update_time : null
         * remark : null
         * del_flag : 0
         * open_tag : 1
         * qr_create_time : null
         */

        private String id;
        private String name;
        private String device_wifi_mac;
        private String device_blue_mac;
        private Object community_id;
        private Object ref_id;
        private Object ref_name;
        private Object is_qr_device;
        private Object device_qr_mac;
        private String device_real_wifi_mac;
        private String device_key;
        private Object create_by;
        private Object create_time;
        private Object update_by;
        private Object update_time;
        private Object remark;
        private String del_flag;
        private String open_tag;
        private Object qr_create_time;
        private boolean opening;// 正在执行开门操作

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", device_wifi_mac='" + device_wifi_mac + '\'' +
                    ", device_blue_mac='" + device_blue_mac + '\'' +
                    ", community_id=" + community_id +
                    ", ref_id=" + ref_id +
                    ", ref_name=" + ref_name +
                    ", is_qr_device=" + is_qr_device +
                    ", device_qr_mac=" + device_qr_mac +
                    ", device_real_wifi_mac='" + device_real_wifi_mac + '\'' +
                    ", device_key='" + device_key + '\'' +
                    ", create_by=" + create_by +
                    ", create_time=" + create_time +
                    ", update_by=" + update_by +
                    ", update_time=" + update_time +
                    ", remark=" + remark +
                    ", del_flag='" + del_flag + '\'' +
                    ", open_tag='" + open_tag + '\'' +
                    ", qr_create_time=" + qr_create_time +
                    ", opening=" + opening +
                    '}';
        }

        public boolean isOpening() {
            return opening;
        }

        public void setOpening(boolean opening) {
            this.opening = opening;
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

        public String getDevice_wifi_mac() {
            return device_wifi_mac;
        }

        public void setDevice_wifi_mac(String device_wifi_mac) {
            this.device_wifi_mac = device_wifi_mac;
        }

        public String getDevice_blue_mac() {
            return device_blue_mac;
        }

        public void setDevice_blue_mac(String device_blue_mac) {
            this.device_blue_mac = device_blue_mac;
        }

        public Object getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(Object community_id) {
            this.community_id = community_id;
        }

        public Object getRef_id() {
            return ref_id;
        }

        public void setRef_id(Object ref_id) {
            this.ref_id = ref_id;
        }

        public Object getRef_name() {
            return ref_name;
        }

        public void setRef_name(Object ref_name) {
            this.ref_name = ref_name;
        }

        public Object getIs_qr_device() {
            return is_qr_device;
        }

        public void setIs_qr_device(Object is_qr_device) {
            this.is_qr_device = is_qr_device;
        }

        public Object getDevice_qr_mac() {
            return device_qr_mac;
        }

        public void setDevice_qr_mac(Object device_qr_mac) {
            this.device_qr_mac = device_qr_mac;
        }

        public String getDevice_real_wifi_mac() {
            return device_real_wifi_mac;
        }

        public void setDevice_real_wifi_mac(String device_real_wifi_mac) {
            this.device_real_wifi_mac = device_real_wifi_mac;
        }

        public String getDevice_key() {
            return device_key;
        }

        public void setDevice_key(String device_key) {
            this.device_key = device_key;
        }

        public Object getCreate_by() {
            return create_by;
        }

        public void setCreate_by(Object create_by) {
            this.create_by = create_by;
        }

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public Object getUpdate_by() {
            return update_by;
        }

        public void setUpdate_by(Object update_by) {
            this.update_by = update_by;
        }

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getDel_flag() {
            return del_flag;
        }

        public void setDel_flag(String del_flag) {
            this.del_flag = del_flag;
        }

        public String getOpen_tag() {
            return open_tag;
        }

        public void setOpen_tag(String open_tag) {
            this.open_tag = open_tag;
        }

        public Object getQr_create_time() {
            return qr_create_time;
        }

        public void setQr_create_time(Object qr_create_time) {
            this.qr_create_time = qr_create_time;
        }
    }
}
