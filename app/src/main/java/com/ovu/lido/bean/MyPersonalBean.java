package com.ovu.lido.bean;

import java.io.Serializable;
import java.util.List;

public class MyPersonalBean implements Serializable{


    /**
     * serialNo :
     * timestamp : 20180517135041
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data :
     * totalNum :
     * point : 0
     * resident_id : 211249
     * nick_name : ldwy844101
     * human_name : 卫细才
     * sex : 1
     * profession :
     * interest :
     * icon_url :
     * community_name : 丽岛物业
     * building_no : 29
     * unit_no : 2
     * room_no : 嘉泰银河湾 29-2-06601
     * identity : 1
     * hand_over_house_time : 2017-12-04
     * hand_over_house_status : 1
     * info_complexity : 50
     * extra_estates : [{"community_id":"34","community_name":"丽岛物业","building_no":"2","unit_no":"2","room_no":"一期 2-2-066001","mobile_no":"15623730597","human_name":"小卫","room_id":"162204","identity":""}]
     */

    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private String data;
    private String totalNum;
    private int point;
    private String resident_id;
    private String nick_name;
    private String human_name;
    private String sex;
    private String profession;
    private String interest;
    private String icon_url;
    private String community_name;
    private String building_no;
    private String unit_no;
    private String room_no;
    private int identity;//1.业主 2.亲属 3.租户
    private String hand_over_house_time;
    private int hand_over_house_status;
    private String info_complexity;
    private String minorities;//名族
    private String id_card_no;//身份证号码
    private String native_pace;//籍贯
    private String political_appearance;//政治面貌
    private String pet_situation;//宠物情况（0：大型犬，1：小型犬，2：猫，3：其他）
    private String data_complete_rate;//业主资料完善度（%） 判断下空
    private List<ExtraEstatesBean> extra_estates;

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

    public String getResident_id() {
        return resident_id;
    }

    public void setResident_id(String resident_id) {
        this.resident_id = resident_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getHuman_name() {
        return human_name;
    }

    public void setHuman_name(String human_name) {
        this.human_name = human_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getBuilding_no() {
        return building_no;
    }

    public void setBuilding_no(String building_no) {
        this.building_no = building_no;
    }

    public String getUnit_no() {
        return unit_no;
    }

    public void setUnit_no(String unit_no) {
        this.unit_no = unit_no;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public String getHand_over_house_time() {
        return hand_over_house_time;
    }

    public void setHand_over_house_time(String hand_over_house_time) {
        this.hand_over_house_time = hand_over_house_time;
    }

    public int getHand_over_house_status() {
        return hand_over_house_status;
    }

    public void setHand_over_house_status(int hand_over_house_status) {
        this.hand_over_house_status = hand_over_house_status;
    }

    public String getInfo_complexity() {
        return info_complexity;
    }

    public void setInfo_complexity(String info_complexity) {
        this.info_complexity = info_complexity;
    }

    public List<ExtraEstatesBean> getExtra_estates() {
        return extra_estates;
    }

    public void setExtra_estates(List<ExtraEstatesBean> extra_estates) {
        this.extra_estates = extra_estates;
    }

    public String getMinorities() {
        return minorities;
    }

    public void setMinorities(String minorities) {
        this.minorities = minorities;
    }

    public String getId_card_no() {
        return id_card_no;
    }

    public void setId_card_no(String id_card_no) {
        this.id_card_no = id_card_no;
    }

    public String getNative_pace() {
        return native_pace;
    }

    public void setNative_pace(String native_pace) {
        this.native_pace = native_pace;
    }

    public String getPolitical_appearance() {
        return political_appearance;
    }

    public void setPolitical_appearance(String political_appearance) {
        this.political_appearance = political_appearance;
    }

    public String getPet_situation() {
        return pet_situation;
    }

    public void setPet_situation(String pet_situation) {
        this.pet_situation = pet_situation;
    }

    public String getData_complete_rate() {
        return data_complete_rate;
    }

    public void setData_complete_rate(String data_complete_rate) {
        this.data_complete_rate = data_complete_rate;
    }

    public static class ExtraEstatesBean implements Serializable{
        /**
         * community_id : 34
         * community_name : 丽岛物业
         * building_no : 2
         * unit_no : 2
         * room_no : 一期 2-2-066001
         * mobile_no : 15623730597
         * human_name : 小卫
         * room_id : 162204
         * identity :
         */

        private String community_id;
        private String community_name;
        private String building_no;
        private String unit_no;
        private String room_no;
        private String mobile_no;
        private String human_name;
        private String room_id;
        private String identity;

        public String getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(String community_id) {
            this.community_id = community_id;
        }

        public String getCommunity_name() {
            return community_name;
        }

        public void setCommunity_name(String community_name) {
            this.community_name = community_name;
        }

        public String getBuilding_no() {
            return building_no;
        }

        public void setBuilding_no(String building_no) {
            this.building_no = building_no;
        }

        public String getUnit_no() {
            return unit_no;
        }

        public void setUnit_no(String unit_no) {
            this.unit_no = unit_no;
        }

        public String getRoom_no() {
            return room_no;
        }

        public void setRoom_no(String room_no) {
            this.room_no = room_no;
        }

        public String getMobile_no() {
            return mobile_no;
        }

        public void setMobile_no(String mobile_no) {
            this.mobile_no = mobile_no;
        }

        public String getHuman_name() {
            return human_name;
        }

        public void setHuman_name(String human_name) {
            this.human_name = human_name;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }
    }
}
