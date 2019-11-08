package com.ovu.lido.bean;

import java.util.List;

public class LeaveCommentsInfo {

    /**
     * timestamp : 20180509173649
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : {"alllist":[{"content":"我来测试下","states":1,"statesName":"业主留言","comment_time":"2018-05-04 13:42:17"},{"content":"有个问题请教下    ","states":1,"statesName":"业主留言","comment_time":"2018-05-04 17:51:58"},{"content":"可以","states":2,"statesName":"管家回复","comment_time":"2018-05-09 11:04:31.0"},{"content":"可以,你先试试","states":2,"statesName":"管家回复","comment_time":"2018-05-19 11:04:31.0"},{"content":"在吗","states":1,"statesName":"业主留言","comment_time":"2018-06-04 17:48:43"}],"housekeeper_id":2,"total_count":5}
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
         * alllist : [{"content":"我来测试下","states":1,"statesName":"业主留言","comment_time":"2018-05-04 13:42:17"},{"content":"有个问题请教下    ","states":1,"statesName":"业主留言","comment_time":"2018-05-04 17:51:58"},{"content":"可以","states":2,"statesName":"管家回复","comment_time":"2018-05-09 11:04:31.0"},{"content":"可以,你先试试","states":2,"statesName":"管家回复","comment_time":"2018-05-19 11:04:31.0"},{"content":"在吗","states":1,"statesName":"业主留言","comment_time":"2018-06-04 17:48:43"}]
         * housekeeper_id : 2
         * total_count : 5
         */

        private int housekeeper_id;
        private int total_count;
        private List<AlllistBean> alllist;

        public int getHousekeeper_id() {
            return housekeeper_id;
        }

        public void setHousekeeper_id(int housekeeper_id) {
            this.housekeeper_id = housekeeper_id;
        }

        public int getTotal_count() {
            return total_count;
        }

        public void setTotal_count(int total_count) {
            this.total_count = total_count;
        }

        public List<AlllistBean> getAlllist() {
            return alllist;
        }

        public void setAlllist(List<AlllistBean> alllist) {
            this.alllist = alllist;
        }

        public static class AlllistBean {
            /**
             * content : 我来测试下
             * states : 1
             * statesName : 业主留言
             * comment_time : 2018-05-04 13:42:17
             */

            private String content;
            private int states;
            private String statesName;
            private String comment_time;
            private String iconUrl;//业主图像
            private String url;//管家图像

            public AlllistBean(String content, int states) {
                this.content = content;
                this.states = states;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStates() {
                return states;
            }

            public void setStates(int states) {
                this.states = states;
            }

            public String getStatesName() {
                return statesName;
            }

            public void setStatesName(String statesName) {
                this.statesName = statesName;
            }

            public String getComment_time() {
                return comment_time;
            }

            public void setComment_time(String comment_time) {
                this.comment_time = comment_time;
            }

            public String getIconUrl() {
                return iconUrl;
            }

            public void setIconUrl(String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
