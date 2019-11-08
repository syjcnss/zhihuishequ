package com.ovu.lido.bean;

import java.util.List;

public class BuildingInfo {

    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private String data;
    private String totalNum;
    private int point;
    private List<BuildingListBean> building_list;

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

    public List<BuildingListBean> getBuilding_list() {
        return building_list;
    }

    public void setBuilding_list(List<BuildingListBean> building_list) {
        this.building_list = building_list;
    }

    public static class BuildingListBean implements ItemEntity {
        /**
         * building_no : xiyou
         * building_name : xiyou
         * unit_nos : [{"unit_no":"001","room_nos":["西柚 xiyou-001-0401","西柚 xiyou-001-0606","西柚 xiyou-001-222208","西柚 xiyou-001-282805"]}]
         */

        private String building_no;
        private String building_name;
        private List<UnitNosBean> unit_nos;

        public String getBuilding_no() {
            return building_no;
        }

        public void setBuilding_no(String building_no) {
            this.building_no = building_no;
        }

        public String getBuilding_name() {
            return building_name;
        }

        public void setBuilding_name(String building_name) {
            this.building_name = building_name;
        }

        public List<UnitNosBean> getUnit_nos() {
            return unit_nos;
        }

        public void setUnit_nos(List<UnitNosBean> unit_nos) {
            this.unit_nos = unit_nos;
        }

        @Override
        public String getItem_name() {
            return building_name;
        }

        public static class UnitNosBean implements ItemEntity {
            /**
             * unit_no : 001
             * room_nos : ["西柚 xiyou-001-0401","西柚 xiyou-001-0606","西柚 xiyou-001-222208","西柚 xiyou-001-282805"]
             */

            private String unit_no;
            private List<String> room_nos;

            public String getUnit_no() {
                return unit_no;
            }

            public void setUnit_no(String unit_no) {
                this.unit_no = unit_no;
            }

            public List<String> getRoom_nos() {
                return room_nos;
            }

            public void setRoom_nos(List<String> room_nos) {
                this.room_nos = room_nos;
            }

            @Override
            public String getItem_name() {
                return unit_no;
            }
        }
    }
}
