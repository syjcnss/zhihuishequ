package com.ovu.lido.bean;

import java.util.List;

public class VolunteerEventInfo {

    /**
     * serialNo :
     * timestamp : 20180528144855
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data :
     * totalNum :
     * point : 0
     * info_list : [{"id":1,"activity_name":"社区志愿者活动","activity_url":"http://172.16.205.6:8082/20180508/11.png","activity_content":"传承红色基因 打造优化营商环境 服务民生的先锋队\u2014\u2014青浦爱融共产党员服务队纪实","community_id":34,"create_time":"2018-04-25 17:24:12","create_user":"","update_user":"","update_time":"","urls":"dd","imgs":"","icon_url":""}]
     * infos :
     * total_count :
     */

    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private String data;
    private String totalNum;
    private int point;
    private String infos;
    private String total_count;
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

    public void setPoint(int point) {
        this.point = point;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
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
        /**
         * id : 1
         * activity_name : 社区志愿者活动
         * activity_url : http://172.16.205.6:8082/20180508/11.png
         * activity_content : 传承红色基因 打造优化营商环境 服务民生的先锋队——青浦爱融共产党员服务队纪实
         * community_id : 34
         * create_time : 2018-04-25 17:24:12
         * create_user :
         * update_user :
         * update_time :
         * urls : dd
         * imgs :
         * icon_url :
         */

        private int id;
        private String activity_name;
        private String activity_url;
        private String activity_content;
        private int community_id;
        private String create_time;
        private String create_user;
        private String update_user;
        private String update_time;
        private String urls;
        private String imgs;
        private String icon_url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(String activity_name) {
            this.activity_name = activity_name;
        }

        public String getActivity_url() {
            return activity_url;
        }

        public void setActivity_url(String activity_url) {
            this.activity_url = activity_url;
        }

        public String getActivity_content() {
            return activity_content;
        }

        public void setActivity_content(String activity_content) {
            this.activity_content = activity_content;
        }

        public int getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(int community_id) {
            this.community_id = community_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCreate_user() {
            return create_user;
        }

        public void setCreate_user(String create_user) {
            this.create_user = create_user;
        }

        public String getUpdate_user() {
            return update_user;
        }

        public void setUpdate_user(String update_user) {
            this.update_user = update_user;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getUrls() {
            return urls;
        }

        public void setUrls(String urls) {
            this.urls = urls;
        }

        public String getImgs() {
            return imgs;
        }

        public void setImgs(String imgs) {
            this.imgs = imgs;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }
    }
}
