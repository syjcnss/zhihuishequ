package com.ovu.lido.bean;

import java.io.Serializable;
import java.util.List;

public class FamilySituationInfo{
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private DataBean data;
    private int point;

    @Override
    public String toString() {
        return "FamilySituationInfo{" +
                "timestamp='" + timestamp + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                ", point=" + point +
                '}';
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

    public static class DataBean{
        private List<KinsmanListBean> kinsmanList;
        private List<EmergencyContactsBean> emergencyContacts;

        public List<KinsmanListBean> getKinsmanList() {
            return kinsmanList;
        }

        public void setKinsmanList(List<KinsmanListBean> kinsmanList) {
            this.kinsmanList = kinsmanList;
        }

        public List<EmergencyContactsBean> getEmergencyContacts() {
            return emergencyContacts;
        }

        public void setEmergencyContacts(List<EmergencyContactsBean> emergencyContacts) {
            this.emergencyContacts = emergencyContacts;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "kinsmanList=" + kinsmanList +
                    ", emergencyContacts=" + emergencyContacts +
                    '}';
        }

        public static class KinsmanListBean {
            /**
             * kinsman_name : 哈哈2
             * kinsman_relationship : 儿子
             * birth_date : 2017-05-3
             * identification_number : 421021119156154
             * work_unit : 腾讯
             * link_tel : 654321
             */

            private String kinsman_name;
            private String kinsman_relationship;
            private String birth_date;
            private String identification_number;
            private String work_unit;
            private String link_tel;

            public String getKinsman_name() {
                return kinsman_name;
            }

            public void setKinsman_name(String kinsman_name) {
                this.kinsman_name = kinsman_name;
            }

            public String getKinsman_relationship() {
                return kinsman_relationship;
            }

            public void setKinsman_relationship(String kinsman_relationship) {
                this.kinsman_relationship = kinsman_relationship;
            }

            public String getBirth_date() {
                return birth_date;
            }

            public void setBirth_date(String birth_date) {
                this.birth_date = birth_date;
            }

            public String getIdentification_number() {
                return identification_number;
            }

            public void setIdentification_number(String identification_number) {
                this.identification_number = identification_number;
            }

            public String getWork_unit() {
                return work_unit;
            }

            public void setWork_unit(String work_unit) {
                this.work_unit = work_unit;
            }

            public String getLink_tel() {
                return link_tel;
            }

            public void setLink_tel(String link_tel) {
                this.link_tel = link_tel;
            }

            @Override
            public String toString() {
                return "{\"kinsman_name\":\"" +  kinsman_name + "\",\"kinsman_relationship\":\""
                        + kinsman_relationship + "\",\"birth_date\":\""
                        + birth_date + "\",\"identification_number\":\""
                        + identification_number + "\",\"work_unit\":\""
                        + work_unit + "\",\"link_tel\":\""
                        + link_tel + "\"}";
            }
        }

        public static class EmergencyContactsBean{
            /**
             * contact_name : 联系人名字
             * contact_tel : 联系人电话
             */

            private String contact_name;
            private String contact_tel;

            public String getContact_name() {
                return contact_name;
            }

            public void setContact_name(String contact_name) {
                this.contact_name = contact_name;
            }

            public String getContact_tel() {
                return contact_tel;
            }

            public void setContact_tel(String contact_tel) {
                this.contact_tel = contact_tel;
            }

            @Override
            public String toString() {
                return "{\"contact_name\":\""+contact_name+"\",\"contact_tel\":\""+contact_tel + "\"}";
            }
        }
    }
}
