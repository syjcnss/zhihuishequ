package com.ovu.lido.bean;

import java.io.Serializable;
import java.util.List;

public class PostDetailBean  implements Serializable {


    /**
     * serialNo :
     * timestamp : 20180507175321
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data :
     * totalNum :
     * point : 0
     * info_id :
     * info_type_id : 02
     * resident_id : 4454
     * nick_name : 哆啦咪梦
     * icon_url : http://123.207.114.182/community/resident_icon_path/4454.jpg?7f9288ea08f144cab7ec00f77157c6ca
     * title : 测
     * content : 测
     * create_time : 2017-06-09 09:57:12.0
     * imgs : http://123.207.114.182/community/info_img_path//20170609/2017060909571100015.jpg?24db2dc8d1e04a04a17e16f4f645bd92
     * mobile_no :
     * response_count : 3
     * view_count : 119
     * agree_count : 4
     * collection_count : 0
     * is_agree : 0
     * is_collection : 0
     * info_response_list : [{"resident_id":"194039","nick_name":"开局一只狗装备全靠捡","icon_url":"http://123.207.114.182/community/resident_icon_path/194039.jpg?b9893552892a4744aac6b713c3b9a355","title":"测","content":"该喝喝","info_id":"","quote_title":"","quote_content":"","response_time":"2017-06-13 15:47:41.0","info_type":"","info_type_name":"","info_response_id":"7986"},{"resident_id":"194039","nick_name":"开局一只狗装备全靠捡","icon_url":"http://123.207.114.182/community/resident_icon_path/194039.jpg?b9893552892a4744aac6b713c3b9a355","title":"测","content":"哈哈哈","info_id":"","quote_title":"","quote_content":"","response_time":"2017-06-13 15:46:36.0","info_type":"","info_type_name":"","info_response_id":"7983"},{"resident_id":"4454","nick_name":"哆啦咪梦","icon_url":"http://123.207.114.182/community/resident_icon_path/4454.jpg?7f9288ea08f144cab7ec00f77157c6ca","title":"详情","content":"测","info_id":"","quote_title":"","quote_content":"","response_time":"2017-06-09 09:57:43.0","info_type":"","info_type_name":"","info_response_id":"7842"}]
     * agreeNames : [{"nick_name":"开局一只狗装备全靠捡","resident_id":194039},{"nick_name":"小明zZ","resident_id":198835},{"nick_name":"","resident_id":""},{"nick_name":"ldwy064686","resident_id":211273}]
     */

    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private String data;
    private String totalNum;
    private int point;
    private String info_id;
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

    public String getInfo_typename() {
        return info_typename;
    }

    public void setInfo_typename(String info_typename) {
        this.info_typename = info_typename;
    }

    private String info_typename;
    private List<InfoResponseListBean> info_response_list;
    private List<AgreeNamesBean> agreeNames;

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

    public String getInfo_id() {
        return info_id;
    }

    public void setInfo_id(String info_id) {
        this.info_id = info_id;
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

    public List<InfoResponseListBean> getInfo_response_list() {
        return info_response_list;
    }

    public void setInfo_response_list(List<InfoResponseListBean> info_response_list) {
        this.info_response_list = info_response_list;
    }

    public List<AgreeNamesBean> getAgreeNames() {
        return agreeNames;
    }

    public void setAgreeNames(List<AgreeNamesBean> agreeNames) {
        this.agreeNames = agreeNames;
    }

    public static class InfoResponseListBean  implements Serializable {
        /**
         * resident_id : 194039
         * nick_name : 开局一只狗装备全靠捡
         * icon_url : http://123.207.114.182/community/resident_icon_path/194039.jpg?b9893552892a4744aac6b713c3b9a355
         * title : 测
         * content : 该喝喝
         * info_id :
         * quote_title :
         * quote_content :
         * response_time : 2017-06-13 15:47:41.0
         * info_type :
         * info_type_name :
         * info_response_id : 7986
         */

        private String resident_id;
        private String nick_name;
        private String icon_url;
        private String title;
        private String content;
        private String info_id;
        private String quote_title;
        private String quote_content;
        private String response_time;
        private String info_type;
        private String info_type_name;
        private String info_response_id;


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

        public String getInfo_id() {
            return info_id;
        }

        public void setInfo_id(String info_id) {
            this.info_id = info_id;
        }

        public String getQuote_title() {
            return quote_title;
        }

        public void setQuote_title(String quote_title) {
            this.quote_title = quote_title;
        }

        public String getQuote_content() {
            return quote_content;
        }

        public void setQuote_content(String quote_content) {
            this.quote_content = quote_content;
        }

        public String getResponse_time() {
            return response_time;
        }

        public void setResponse_time(String response_time) {
            this.response_time = response_time;
        }

        public String getInfo_type() {
            return info_type;
        }

        public void setInfo_type(String info_type) {
            this.info_type = info_type;
        }

        public String getInfo_type_name() {
            return info_type_name;
        }

        public void setInfo_type_name(String info_type_name) {
            this.info_type_name = info_type_name;
        }

        public String getInfo_response_id() {
            return info_response_id;
        }

        public void setInfo_response_id(String info_response_id) {
            this.info_response_id = info_response_id;
        }
    }

    public static class AgreeNamesBean  implements Serializable {
        /**
         * nick_name : 开局一只狗装备全靠捡
         * resident_id : 194039
         */

        private String nick_name;
        private int resident_id;

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public int getResident_id() {
            return resident_id;
        }

        public void setResident_id(int resident_id) {
            this.resident_id = resident_id;
        }
    }
}
