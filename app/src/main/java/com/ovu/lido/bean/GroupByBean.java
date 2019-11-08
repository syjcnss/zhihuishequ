package com.ovu.lido.bean;

import java.io.Serializable;
import java.util.List;

public class GroupByBean implements Serializable {


    /**
     * timestamp : 20180510180121
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : [{"id":68,"creator_id":null,"g_p_name":"卫龙辣条","sponsor":null,"commodity_name":"卫龙辣条","begin_time":"2017-06-30 11:21:57.0","end_time":"2018-06-30 11:21:58.0","create_time":null,"content":"<p>卫龙辣条，小时候的纯正味道<\/p>","tel":"18012341234","enroll_limit":10000,"state":2,"del":1,"community_id":null,"enrollCount":0,"is_enroll":false,"is_end":false,"original_price":0,"g_p_price":0,"commodity_img":"http://120.27.196.188/community/info_img_pathnull","commodity_id":16},{"id":72,"creator_id":null,"g_p_name":"老干妈","sponsor":null,"commodity_name":"老干妈","begin_time":"2017-07-05 14:53:45.0","end_time":"2018-07-05 14:53:46.0","create_time":null,"content":"<p>风味豆豉<\/p><p>油制辣椒<\/p><p>中国名牌<\/p>","tel":"18612121212","enroll_limit":10000,"state":2,"del":1,"community_id":null,"enrollCount":0,"is_enroll":false,"is_end":false,"original_price":0,"g_p_price":0,"commodity_img":"http://120.27.196.188/community/info_img_pathnull","commodity_id":17},{"id":97,"creator_id":null,"g_p_name":"丽岛业主独享，因特指纹锁大促销！","sponsor":null,"commodity_name":"因特指纹锁-超越系列","begin_time":"2017-07-20 14:16:38.0","end_time":"2017-07-20 17:16:39.0","create_time":null,"content":"<p><img src=\"http://120.27.196.188/72081500531476314.png\" _src=\"http://120.27.196.188/72081500531476314.png\" style=\"\"/><\/p><p><img src=\"http://120.27.196.188/81701500531483365.gif\" _src=\"http://120.27.196.188/81701500531483365.gif\" style=\"\"/><\/p><p><img src=\"http://120.27.196.188/88051500531486596.png\" _src=\"http://120.27.196.188/88051500531486596.png\" style=\"\"/><\/p><p><img src=\"http://120.27.196.188/88481500531495509.png\" _src=\"http://120.27.196.188/88481500531495509.png\" style=\"\"/><\/p><p><img src=\"http://120.27.196.188/24971500531500746.png\" _src=\"http://120.27.196.188/24971500531500746.png\" style=\"\"/><\/p><p><img src=\"http://120.27.196.188/73101500531509944.png\" _src=\"http://120.27.196.188/73101500531509944.png\" style=\"\"/><\/p><p><img src=\"http://120.27.196.188/93531500531522208.png\" _src=\"http://120.27.196.188/93531500531522208.png\" style=\"\"/><\/p><p><img src=\"http://120.27.196.188/13751500531524989.jpg\" _src=\"http://120.27.196.188/13751500531524989.jpg\" style=\"\"/><\/p><p><img src=\"http://120.27.196.188/84391500531587575.jpg\" _src=\"http://120.27.196.188/84391500531587575.jpg\" style=\"\"/><\/p><p><img src=\"http://120.27.196.188/3521500531590615.png\" _src=\"http://120.27.196.188/3521500531590615.png\" style=\"\"/><\/p><p><br/><\/p>","tel":"13477078876","enroll_limit":100,"state":2,"del":1,"community_id":null,"enrollCount":0,"is_enroll":false,"is_end":false,"original_price":0,"g_p_price":0,"commodity_img":"http://120.27.196.188/community/info_img_pathnull","commodity_id":25},{"id":113,"creator_id":null,"g_p_name":"因特指纹锁-超越系列","sponsor":null,"commodity_name":"因特指纹锁-超越系列2","begin_time":"2017-08-02 00:00:00.0","end_time":"2017-08-31 23:59:59.0","create_time":null,"content":"<p>\n    <img src=\"http://120.27.196.188/13611501590719273.png\" _src=\"http://120.27.196.188/13611501590719273.png\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/65461501590726175.gif\" _src=\"http://120.27.196.188/65461501590726175.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/64711501590731261.png\" _src=\"http://120.27.196.188/64711501590731261.png\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/10561501590734891.png\" _src=\"http://120.27.196.188/10561501590734891.png\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/47711501590737310.png\" _src=\"http://120.27.196.188/47711501590737310.png\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/75201501590742439.png\" _src=\"http://120.27.196.188/75201501590742439.png\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/46321501590744703.png\" _src=\"http://120.27.196.188/46321501590744703.png\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/96911501590746138.jpg\" _src=\"http://120.27.196.188/96911501590746138.jpg\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/14881501590759237.jpg\" _src=\"http://120.27.196.188/14881501590759237.jpg\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/63971501590762424.png\" _src=\"http://120.27.196.188/63971501590762424.png\" width = \"100%\"/>\n<\/p>\n<p>\n    <br/>\n<\/p>","tel":"13477078876","enroll_limit":100,"state":2,"del":1,"community_id":null,"enrollCount":0,"is_enroll":false,"is_end":false,"original_price":0,"g_p_price":0,"commodity_img":"http://120.27.196.188/community/info_img_pathnull","commodity_id":26},{"id":115,"creator_id":null,"g_p_name":"因特指纹锁-印象系列","sponsor":null,"commodity_name":"因特指纹锁-印象系列","begin_time":"2017-08-02 00:00:00.0","end_time":"2017-08-31 23:59:59.0","create_time":null,"content":"<p>\n    <img src=\"http://120.27.196.188/61561501592696182.gif\" _src=\"http://120.27.196.188/61561501592696182.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/76551501592697708.gif\" _src=\"http://120.27.196.188/76551501592697708.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/10981501592698807.gif\" _src=\"http://120.27.196.188/10981501592698807.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/39351501592699935.gif\" _src=\"http://120.27.196.188/39351501592699935.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/29501501592701095.gif\" _src=\"http://120.27.196.188/29501501592701095.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/95231501592702301.gif\" _src=\"http://120.27.196.188/95231501592702301.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/88211501592704323.gif\" _src=\"http://120.27.196.188/88211501592704323.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/40751501592705895.gif\" _src=\"http://120.27.196.188/40751501592705895.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/39951501592707254.gif\" _src=\"http://120.27.196.188/39951501592707254.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/65841501592708454.gif\" _src=\"http://120.27.196.188/65841501592708454.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/30361501592709910.gif\" _src=\"http://120.27.196.188/30361501592709910.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/12141501592711220.gif\" _src=\"http://120.27.196.188/12141501592711220.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/53931501592713443.gif\" _src=\"http://120.27.196.188/53931501592713443.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/48131501592714635.gif\" _src=\"http://120.27.196.188/48131501592714635.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/99271501592716173.gif\" _src=\"http://120.27.196.188/99271501592716173.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <br/>\n<\/p>","tel":"13477078876","enroll_limit":100,"state":2,"del":1,"community_id":null,"enrollCount":0,"is_enroll":false,"is_end":false,"original_price":0,"g_p_price":0,"commodity_img":"http://120.27.196.188/community/info_img_pathnull","commodity_id":41},{"id":116,"creator_id":null,"g_p_name":"因特指纹锁-英弛系列","sponsor":null,"commodity_name":"因特指纹锁-英弛系列","begin_time":"2017-08-02 00:00:00.0","end_time":"2017-08-31 23:59:59.0","create_time":null,"content":"<p>\n    <img src=\"http://120.27.196.188/24651501593099115.gif\" _src=\"http://120.27.196.188/24651501593099115.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/76591501593100477.gif\" _src=\"http://120.27.196.188/76591501593100477.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/63851501593101835.gif\" _src=\"http://120.27.196.188/63851501593101835.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/95461501593103224.gif\" _src=\"http://120.27.196.188/95461501593103224.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/57151501593105054.gif\" _src=\"http://120.27.196.188/57151501593105054.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/76191501593106312.gif\" _src=\"http://120.27.196.188/76191501593106312.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/40661501593107715.gif\" _src=\"http://120.27.196.188/40661501593107715.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/83661501593109835.gif\" _src=\"http://120.27.196.188/83661501593109835.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/64991501593111054.gif\" _src=\"http://120.27.196.188/64991501593111054.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/58411501593112254.gif\" _src=\"http://120.27.196.188/58411501593112254.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <img src=\"http://120.27.196.188/10931501593114044.gif\" _src=\"http://120.27.196.188/10931501593114044.gif\" width = \"100%\"/>\n<\/p>\n<p>\n    <br/>\n<\/p>","tel":"13477078876","enroll_limit":100,"state":2,"del":1,"community_id":null,"enrollCount":0,"is_enroll":false,"is_end":false,"original_price":0,"g_p_price":0,"commodity_img":"http://120.27.196.188/community/info_img_pathnull","commodity_id":42},{"id":119,"creator_id":null,"g_p_name":"测试","sponsor":null,"commodity_name":"益达口香糖","begin_time":"2017-08-11 09:21:38.0","end_time":"2017-11-02 09:21:39.0","create_time":null,"content":"","tel":"15623917223","enroll_limit":1,"state":2,"del":1,"community_id":null,"enrollCount":0,"is_enroll":false,"is_end":false,"original_price":0,"g_p_price":0,"commodity_img":"http://120.27.196.188/community/info_img_pathnull","commodity_id":15},{"id":126,"creator_id":null,"g_p_name":"码尚慧3、4、5、6年级","sponsor":null,"commodity_name":"码尚慧3-6年级","begin_time":"2017-08-28 16:28:30.0","end_time":"2018-08-28 16:28:31.0","create_time":null,"content":"<p><img src=\"http://120.27.196.188/87651503909035345.png\" _src=\"http://120.27.196.188/87651503909035345.png\"/><\/p><p><img src=\"http://120.27.196.188/99341503909049584.png\" _src=\"http://120.27.196.188/99341503909049584.png\"/><\/p><p>&nbsp; &nbsp;<img src=\"http://120.27.196.188/64101503909067281.png\" _src=\"http://120.27.196.188/64101503909067281.png\"/><\/p><p><img src=\"http://120.27.196.188/45571503909106223.png\" _src=\"http://120.27.196.188/45571503909106223.png\"/><\/p><p><img src=\"http://120.27.196.188/37911503909120545.png\" _src=\"http://120.27.196.188/37911503909120545.png\"/><\/p><p><img src=\"http://120.27.196.188/77191503909131975.png\" _src=\"http://120.27.196.188/77191503909131975.png\"/><\/p><p><br/><\/p><p><img src=\"http://120.27.196.188/25171503909141749.png\" _src=\"http://120.27.196.188/25171503909141749.png\"/><\/p><p><img src=\"http://120.27.196.188/96541503909153687.png\" _src=\"http://120.27.196.188/96541503909153687.png\"/><\/p><p><\/p><p><br/><\/p><p><br/><\/p>","tel":"13477078876","enroll_limit":100,"state":2,"del":1,"community_id":null,"enrollCount":0,"is_enroll":false,"is_end":false,"original_price":0,"g_p_price":0,"commodity_img":"http://120.27.196.188/community/info_img_pathnull","commodity_id":50},{"id":127,"creator_id":null,"g_p_name":"码尚慧7、8、9年级","sponsor":null,"commodity_name":"码尚慧7-9年级","begin_time":"2017-08-28 16:51:46.0","end_time":"2018-08-28 16:51:48.0","create_time":null,"content":"<p><img src=\"http://120.27.196.188/44181503911356206.png\" _src=\"http://120.27.196.188/44181503911356206.png\"/><\/p><p><img src=\"http://120.27.196.188/30331503911371557.png\" _src=\"http://120.27.196.188/30331503911371557.png\"/><\/p><p><img src=\"http://120.27.196.188/96891503911389010.png\" _src=\"http://120.27.196.188/96891503911389010.png\"/><\/p><p><img src=\"http://120.27.196.188/65221503911401653.png\" _src=\"http://120.27.196.188/65221503911401653.png\"/><\/p><p><img src=\"http://120.27.196.188/48081503911410621.png\" _src=\"http://120.27.196.188/48081503911410621.png\"/><\/p><p><img src=\"http://120.27.196.188/22561503911421467.png\" _src=\"http://120.27.196.188/22561503911421467.png\"/><\/p><p><br/><\/p><p><img src=\"http://120.27.196.188/56911503911434491.png\" _src=\"http://120.27.196.188/56911503911434491.png\"/><\/p><p><img src=\"http://120.27.196.188/2941503911444470.png\" _src=\"http://120.27.196.188/2941503911444470.png\"/><\/p><p><br/><\/p><p><br/><\/p><p><br/><\/p>","tel":"13477078876","enroll_limit":100,"state":2,"del":1,"community_id":null,"enrollCount":0,"is_enroll":false,"is_end":false,"original_price":0,"g_p_price":0,"commodity_img":"http://120.27.196.188/community/info_img_pathnull","commodity_id":51},{"id":133,"creator_id":null,"g_p_name":"测试","sponsor":null,"commodity_name":"益达口香糖","begin_time":"2017-08-30 09:28:38.0","end_time":"2017-10-18 09:28:39.0","create_time":null,"content":"","tel":"15623917223","enroll_limit":1,"state":2,"del":1,"community_id":null,"enrollCount":0,"is_enroll":false,"is_end":false,"original_price":0,"g_p_price":0,"commodity_img":"http://120.27.196.188/community/info_img_pathnull","commodity_id":15},{"id":134,"creator_id":null,"g_p_name":"test","sponsor":null,"commodity_name":"益达口香糖","begin_time":"2017-08-31 12:40:41.0","end_time":"2017-11-10 12:40:42.0","create_time":null,"content":"","tel":"15623917223","enroll_limit":1,"state":2,"del":1,"community_id":null,"enrollCount":0,"is_enroll":false,"is_end":false,"original_price":0,"g_p_price":0,"commodity_img":"http://120.27.196.188/community/info_img_pathnull","commodity_id":15},{"id":135,"creator_id":null,"g_p_name":"11","sponsor":null,"commodity_name":"益达口香糖","begin_time":"2017-08-31 12:42:02.0","end_time":"2017-11-30 12:42:03.0","create_time":null,"content":"","tel":"15623917223","enroll_limit":1,"state":2,"del":1,"community_id":null,"enrollCount":0,"is_enroll":false,"is_end":false,"original_price":0,"g_p_price":0,"commodity_img":"http://120.27.196.188/community/info_img_pathnull","commodity_id":15},{"id":136,"creator_id":null,"g_p_name":"222","sponsor":null,"commodity_name":"卫龙辣条","begin_time":"2017-08-31 12:49:13.0","end_time":"2017-11-16 12:49:13.0","create_time":null,"content":"","tel":"15623917223","enroll_limit":1,"state":2,"del":1,"community_id":null,"enrollCount":0,"is_enroll":false,"is_end":false,"original_price":0,"g_p_price":0,"commodity_img":"http://120.27.196.188/community/info_img_pathnull","commodity_id":16},{"id":156,"creator_id":null,"g_p_name":"紫缘臻品月礼盒","sponsor":null,"commodity_name":"紫缘臻品月礼盒","begin_time":"2017-09-21 20:58:11.0","end_time":"2017-10-04 20:00:00.0","create_time":null,"content":"","tel":"13477078876","enroll_limit":100,"state":2,"del":1,"community_id":null,"enrollCount":0,"is_enroll":false,"is_end":false,"original_price":0,"g_p_price":0,"commodity_img":"http://120.27.196.188/community/info_img_pathnull","commodity_id":74}]
     * point : 0
     */

    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private Object hash;
    private Object token;
    private int point;
    private List<DataBean> data;

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

    public Object getHash() {
        return hash;
    }

    public void setHash(Object hash) {
        this.hash = hash;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
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

    public static class DataBean implements Serializable {
        /**
         * id : 68
         * creator_id : null
         * g_p_name : 卫龙辣条
         * sponsor : null
         * commodity_name : 卫龙辣条
         * begin_time : 2017-06-30 11:21:57.0
         * end_time : 2018-06-30 11:21:58.0
         * create_time : null
         * content : <p>卫龙辣条，小时候的纯正味道</p>
         * tel : 18012341234
         * enroll_limit : 10000
         * state : 2
         * del : 1
         * community_id : null
         * enrollCount : 0
         * is_enroll : false
         * is_end : false
         * original_price : 0
         * g_p_price : 0
         * commodity_img : http://120.27.196.188/community/info_img_pathnull
         * commodity_id : 16
         */

        private int id;
        private Object creator_id;
        private String g_p_name;
        private Object sponsor;
        private String commodity_name;
        private String begin_time;
        private String end_time;
        private Object create_time;
        private String content;
        private String tel;
        private int enroll_limit;
        private int state;
        private int del;
        private Object community_id;
        private int enrollCount;
        private boolean is_enroll;
        private boolean is_end;
        private double original_price;
        private double g_p_price;
        private String commodity_img;
        private int commodity_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getCreator_id() {
            return creator_id;
        }

        public void setCreator_id(Object creator_id) {
            this.creator_id = creator_id;
        }

        public String getG_p_name() {
            return g_p_name;
        }

        public void setG_p_name(String g_p_name) {
            this.g_p_name = g_p_name;
        }

        public Object getSponsor() {
            return sponsor;
        }

        public void setSponsor(Object sponsor) {
            this.sponsor = sponsor;
        }

        public String getCommodity_name() {
            return commodity_name;
        }

        public void setCommodity_name(String commodity_name) {
            this.commodity_name = commodity_name;
        }

        public String getBegin_time() {
            return begin_time;
        }

        public void setBegin_time(String begin_time) {
            this.begin_time = begin_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getEnroll_limit() {
            return enroll_limit;
        }

        public void setEnroll_limit(int enroll_limit) {
            this.enroll_limit = enroll_limit;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getDel() {
            return del;
        }

        public void setDel(int del) {
            this.del = del;
        }

        public Object getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(Object community_id) {
            this.community_id = community_id;
        }

        public int getEnrollCount() {
            return enrollCount;
        }

        public void setEnrollCount(int enrollCount) {
            this.enrollCount = enrollCount;
        }

        public boolean isIs_enroll() {
            return is_enroll;
        }

        public void setIs_enroll(boolean is_enroll) {
            this.is_enroll = is_enroll;
        }

        public boolean isIs_end() {
            return is_end;
        }

        public void setIs_end(boolean is_end) {
            this.is_end = is_end;
        }

        public double getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(double original_price) {
            this.original_price = original_price;
        }

        public double getG_p_price() {
            return g_p_price;
        }

        public void setG_p_price(double g_p_price) {
            this.g_p_price = g_p_price;
        }

        public String getCommodity_img() {
            return commodity_img;
        }

        public void setCommodity_img(String commodity_img) {
            this.commodity_img = commodity_img;
        }

        public int getCommodity_id() {
            return commodity_id;
        }

        public void setCommodity_id(int commodity_id) {
            this.commodity_id = commodity_id;
        }
    }
}
