package com.ovu.lido.bean;

import java.util.List;

public class MyResonseBean {


    /**
     * timestamp : 20180510105216
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : {"total_count":4,"list":[{"agreeNames":[{"nick_name":"ldwy844101","resident_id":211249}],"id":"8294","info_type_id":"02","resident_id":"211249","nick_name":"ldwy844101","icon_url":null,"title":"橘子","content":"买橘子","create_time":"2018-01-27 11:04:49.0","imgs":"http://172.16.205.9/community-rest//20180127/2018012711044800193.jpg?7d13e28487ed470dad455641867feea6","mobile_no":null,"response_count":"1","view_count":"22","agree_count":"1","collection_count":"0","is_agree":"1","is_collection":null,"info_typename":2,"info_type_name":null,"read_status":"0"},{"agreeNames":[],"id":"6208","info_type_id":"02","resident_id":"203762","nick_name":"剁椒","icon_url":"http://123.207.114.182/community/resident_icon_path/203762.jpg?d824a9a3f136497db83e6f35d164cb65","title":"。。","content":"下雨了吃鱼，吃完鱼不下雨","create_time":"2017-09-27 16:16:27.0","imgs":"http://172.16.205.9/community-rest//20170927/2017092716162600129.jpg?f33d769ca7fd4cc1bb8cf6b1d1286abc","mobile_no":null,"response_count":"1","view_count":"11","agree_count":"0","collection_count":"0","is_agree":"0","is_collection":null,"info_typename":1,"info_type_name":null,"read_status":"0"},{"agreeNames":[],"id":"4949","info_type_id":"02","resident_id":"194039","nick_name":"开局一只狗装备全靠捡","icon_url":"http://123.207.114.182/community/resident_icon_path/194039.jpg?b9893552892a4744aac6b713c3b9a355","title":"11","content":"11","create_time":"2017-07-14 17:06:12.0","imgs":null,"mobile_no":null,"response_count":"2","view_count":"5","agree_count":"0","collection_count":"0","is_agree":"0","is_collection":null,"info_typename":2,"info_type_name":null,"read_status":"0"},{"agreeNames":[{"nick_name":"哆啦咪梦","resident_id":4454},{"nick_name":"开局一只狗装备全靠捡","resident_id":194039},{"nick_name":"樊皓天","resident_id":194109},{"nick_name":"小明zZ","resident_id":198835},{"nick_name":"ldwy8624962","resident_id":204543},{"nick_name":"陈嘻嘻","resident_id":204837},{"nick_name":"ldwy844101","resident_id":211249},{"nick_name":"ldwy064686","resident_id":211273},{"nick_name":"ldwy1254988","resident_id":212293}],"id":"4766","info_type_id":"02","resident_id":"204837","nick_name":"陈嘻嘻","icon_url":"http://123.207.114.182/community/resident_icon_path/204837.jpg?aa21a91389f8494da560671eb8ac819b","title":"一只水笔","content":"好写的笔","create_time":"2017-07-07 10:29:02.0","imgs":"http://172.16.205.9/community-rest//20170707/2017070710290200001.jpg?03fe666cecb54fdb9ddfa934ee15a433","mobile_no":null,"response_count":"4","view_count":"100","agree_count":"9","collection_count":"0","is_agree":"1","is_collection":null,"info_typename":2,"info_type_name":null,"read_status":"0"}]}
     * point : 0
     */

    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private Object hash;
    private Object token;
    private DataBean data;
    private int point;

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

    public static class DataBean {
        /**
         * total_count : 4
         * list : [{"agreeNames":[{"nick_name":"ldwy844101","resident_id":211249}],"id":"8294","info_type_id":"02","resident_id":"211249","nick_name":"ldwy844101","icon_url":null,"title":"橘子","content":"买橘子","create_time":"2018-01-27 11:04:49.0","imgs":"http://172.16.205.9/community-rest//20180127/2018012711044800193.jpg?7d13e28487ed470dad455641867feea6","mobile_no":null,"response_count":"1","view_count":"22","agree_count":"1","collection_count":"0","is_agree":"1","is_collection":null,"info_typename":2,"info_type_name":null,"read_status":"0"},{"agreeNames":[],"id":"6208","info_type_id":"02","resident_id":"203762","nick_name":"剁椒","icon_url":"http://123.207.114.182/community/resident_icon_path/203762.jpg?d824a9a3f136497db83e6f35d164cb65","title":"。。","content":"下雨了吃鱼，吃完鱼不下雨","create_time":"2017-09-27 16:16:27.0","imgs":"http://172.16.205.9/community-rest//20170927/2017092716162600129.jpg?f33d769ca7fd4cc1bb8cf6b1d1286abc","mobile_no":null,"response_count":"1","view_count":"11","agree_count":"0","collection_count":"0","is_agree":"0","is_collection":null,"info_typename":1,"info_type_name":null,"read_status":"0"},{"agreeNames":[],"id":"4949","info_type_id":"02","resident_id":"194039","nick_name":"开局一只狗装备全靠捡","icon_url":"http://123.207.114.182/community/resident_icon_path/194039.jpg?b9893552892a4744aac6b713c3b9a355","title":"11","content":"11","create_time":"2017-07-14 17:06:12.0","imgs":null,"mobile_no":null,"response_count":"2","view_count":"5","agree_count":"0","collection_count":"0","is_agree":"0","is_collection":null,"info_typename":2,"info_type_name":null,"read_status":"0"},{"agreeNames":[{"nick_name":"哆啦咪梦","resident_id":4454},{"nick_name":"开局一只狗装备全靠捡","resident_id":194039},{"nick_name":"樊皓天","resident_id":194109},{"nick_name":"小明zZ","resident_id":198835},{"nick_name":"ldwy8624962","resident_id":204543},{"nick_name":"陈嘻嘻","resident_id":204837},{"nick_name":"ldwy844101","resident_id":211249},{"nick_name":"ldwy064686","resident_id":211273},{"nick_name":"ldwy1254988","resident_id":212293}],"id":"4766","info_type_id":"02","resident_id":"204837","nick_name":"陈嘻嘻","icon_url":"http://123.207.114.182/community/resident_icon_path/204837.jpg?aa21a91389f8494da560671eb8ac819b","title":"一只水笔","content":"好写的笔","create_time":"2017-07-07 10:29:02.0","imgs":"http://172.16.205.9/community-rest//20170707/2017070710290200001.jpg?03fe666cecb54fdb9ddfa934ee15a433","mobile_no":null,"response_count":"4","view_count":"100","agree_count":"9","collection_count":"0","is_agree":"1","is_collection":null,"info_typename":2,"info_type_name":null,"read_status":"0"}]
         */

        private int total_count;
        private List<ListBean> list;

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * agreeNames : [{"nick_name":"ldwy844101","resident_id":211249}]
             * id : 8294
             * info_type_id : 02
             * resident_id : 211249
             * nick_name : ldwy844101
             * icon_url : null
             * title : 橘子
             * content : 买橘子
             * create_time : 2018-01-27 11:04:49.0
             * imgs : http://172.16.205.9/community-rest//20180127/2018012711044800193.jpg?7d13e28487ed470dad455641867feea6
             * mobile_no : null
             * response_count : 1
             * view_count : 22
             * agree_count : 1
             * collection_count : 0
             * is_agree : 1
             * is_collection : null
             * info_typename : 2
             * info_type_name : null
             * read_status : 0
             */

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
            private Object is_collection;
            private String info_typename;
            private String info_type_name;
            private String read_status;
            private List<AgreeNamesBean> agreeNames;

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

            public Object getIs_collection() {
                return is_collection;
            }

            public void setIs_collection(Object is_collection) {
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

            public List<AgreeNamesBean> getAgreeNames() {
                return agreeNames;
            }

            public void setAgreeNames(List<AgreeNamesBean> agreeNames) {
                this.agreeNames = agreeNames;
            }

            public static class AgreeNamesBean {
                /**
                 * nick_name : ldwy844101
                 * resident_id : 211249
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
    }
}
