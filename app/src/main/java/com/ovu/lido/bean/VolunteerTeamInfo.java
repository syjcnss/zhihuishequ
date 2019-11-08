package com.ovu.lido.bean;

import java.util.List;

public class VolunteerTeamInfo {

    /**
     * serialNo :
     * timestamp : 20180528144201
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data :
     * totalNum :
     * point : 0
     * info_list :
     * infos : [{"id":1,"name":"李思","url":"http://172.16.205.6:8082/20180508/3.jpg","content":"老人福利服务,妇女福利服务,青少年福利服务,儿童福利服务,家庭福利服务,社区福利服务,医院福利服务,司法矫正服务,学校福利服务,社会公益活动服务,会展活动服务,其它","community_id":34,"create_time":"2018-05-28 14:14:14","create_by":"","update_by":"","update_time":"","imgs":"","icon_url":""},{"id":2,"name":"王国栋","url":"http://172.16.205.6:8082/20180508/2.jpg","content":"社区福利服务","community_id":34,"create_time":"2018-05-28 14:14:14","create_by":"","update_by":"","update_time":"","imgs":"","icon_url":""}]
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
    private String info_list;
    private String total_count;
    private List<InfosBean> infos;

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

    public String getInfo_list() {
        return info_list;
    }

    public void setInfo_list(String info_list) {
        this.info_list = info_list;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public List<InfosBean> getInfos() {
        return infos;
    }

    public void setInfos(List<InfosBean> infos) {
        this.infos = infos;
    }

    public static class InfosBean {
        /**
         * id : 1
         * name : 李思
         * url : http://172.16.205.6:8082/20180508/3.jpg
         * content : 老人福利服务,妇女福利服务,青少年福利服务,儿童福利服务,家庭福利服务,社区福利服务,医院福利服务,司法矫正服务,学校福利服务,社会公益活动服务,会展活动服务,其它
         * community_id : 34
         * create_time : 2018-05-28 14:14:14
         * create_by :
         * update_by :
         * update_time :
         * imgs :
         * icon_url :
         */

        private int id;
        private String name;
        private String url;
        private String content;
        private int community_id;
        private String create_time;
        private String create_by;
        private String update_by;
        private String update_time;
        private String imgs;
        private String icon_url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getCreate_by() {
            return create_by;
        }

        public void setCreate_by(String create_by) {
            this.create_by = create_by;
        }

        public String getUpdate_by() {
            return update_by;
        }

        public void setUpdate_by(String update_by) {
            this.update_by = update_by;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
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
