package com.ovu.lido.bean;

import java.util.List;

public class NeiBean {
    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private String data;
    private String totalNum;
    private int point;
    private String total_count;
    private String resident_id;
    private String nick_name;
    private String human_name;
    private String sex;
    private String profession;
    private String interest;
    private String icon_url;
    private List<InfoListBean> info_list;

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

    public void setPoint(int point) {
        this.point = point;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public List<InfoListBean> getInfo_list() {
        return info_list;
    }

    public void setInfo_list(List<InfoListBean> info_list) {
        this.info_list = info_list;
    }

    public static class InfoListBean {
        private String id;
        private String info_type_id;
        private String resident_id;
        private String nick_name;
        private String icon_url;
        private String title;
        private String content;
        private String create_time;
        private String imgs;
        private String mobile_no;
        private String response_count;
        private String view_count;
        private String agree_count;
        private String collection_count;
        private String is_agree;
        private String is_collection;
        private String info_typename;
        private String info_type_name;
        private String read_status;
        private List<?> agreeNames;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInfo_type_id() {
            return info_type_id;
        }

        public void setInfo_type_id(String info_type_id) {
            this.info_type_id = info_type_id;
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

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getMobile_no() {
            return mobile_no;
        }

        public void setMobile_no(String mobile_no) {
            this.mobile_no = mobile_no;
        }

        public String getResponse_count() {
            return response_count;
        }

        public void setResponse_count(String response_count) {
            this.response_count = response_count;
        }

        public String getView_count() {
            return view_count;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }

        public String getAgree_count() {
            return agree_count;
        }

        public void setAgree_count(String agree_count) {
            this.agree_count = agree_count;
        }

        public String getCollection_count() {
            return collection_count;
        }

        public void setCollection_count(String collection_count) {
            this.collection_count = collection_count;
        }

        public String getIs_agree() {
            return is_agree;
        }

        public void setIs_agree(String is_agree) {
            this.is_agree = is_agree;
        }

        public String getIs_collection() {
            return is_collection;
        }

        public void setIs_collection(String is_collection) {
            this.is_collection = is_collection;
        }

        public String getInfo_typename() {
            return info_typename;
        }

        public void setInfo_typename(String info_typename) {
            this.info_typename = info_typename;
        }

        public String getInfo_type_name() {
            return info_type_name;
        }

        public void setInfo_type_name(String info_type_name) {
            this.info_type_name = info_type_name;
        }

        public String getRead_status() {
            return read_status;
        }

        public void setRead_status(String read_status) {
            this.read_status = read_status;
        }

        public List<?> getAgreeNames() {
            return agreeNames;
        }

        public void setAgreeNames(List<?> agreeNames) {
            this.agreeNames = agreeNames;
        }
    }
}
