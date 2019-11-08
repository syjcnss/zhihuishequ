package com.ovu.lido.bean;

import java.util.List;

public class SpVoteListInfo {
    /**
     * serialNo :
     * timestamp : 20180601143922
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data :
     * totalNum :
     * point : 0
     * info_list : [{"id":1,"topic_id":1,"title":"对小区环境满意吗满意","vote_type":"2","start":"2018-05-30 00:00:00","end":"2018-08-01 00:00:00","itemContent":"小区卫生满意度","community_id":34,"created":"2018-05-31 00:00:00","modified":"","username":"","votenum":"","hitnum":"","score":100,"status":"1","vote_id":1,"multi":"0","maxcheck":2,"topicdetail":[{"topicdetailId":1,"content":"满意"},{"topicdetailId":6,"content":"不满意"}],"imgs":"","icon_url":""}]
     * info_historylist :
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
    private String info_historylist;
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

    public String getInfo_historylist() {
        return info_historylist;
    }

    public void setInfo_historylist(String info_historylist) {
        this.info_historylist = info_historylist;
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
         * topic_id : 1
         * title : 对小区环境满意吗满意
         * vote_type : 2
         * start : 2018-05-30 00:00:00
         * end : 2018-08-01 00:00:00
         * itemContent : 小区卫生满意度
         * community_id : 34
         * created : 2018-05-31 00:00:00
         * modified :
         * username :
         * votenum :
         * hitnum :
         * score : 100
         * status : 1
         * vote_id : 1
         * multi : 0
         * maxcheck : 2
         * topicdetail : [{"topicdetailId":1,"content":"满意"},{"topicdetailId":6,"content":"不满意"}]
         * imgs :
         * icon_url :
         */

        private int id;
        private int topic_id;
        private String title;
        private String vote_type;
        private String start;
        private String end;
        private String itemContent;
//        private int community_id;
        private String created;
        private String modified;
        private String username;
        private String votenum;
        private String hitnum;
        private int score;
        private String status;
        private int vote_id;
        private String multi;
        private int maxcheck;
        private String imgs;
        private String icon_url;
        private List<TopicdetailBean> topicdetail;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(int topic_id) {
            this.topic_id = topic_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVote_type() {
            return vote_type;
        }

        public void setVote_type(String vote_type) {
            this.vote_type = vote_type;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getItemContent() {
            return itemContent;
        }

        public void setItemContent(String itemContent) {
            this.itemContent = itemContent;
        }

//        public int getCommunity_id() {
//            return community_id;
//        }

//        public void setCommunity_id(int community_id) {
//            this.community_id = community_id;
//        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getModified() {
            return modified;
        }

        public void setModified(String modified) {
            this.modified = modified;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getVotenum() {
            return votenum;
        }

        public void setVotenum(String votenum) {
            this.votenum = votenum;
        }

        public String getHitnum() {
            return hitnum;
        }

        public void setHitnum(String hitnum) {
            this.hitnum = hitnum;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getVote_id() {
            return vote_id;
        }

        public void setVote_id(int vote_id) {
            this.vote_id = vote_id;
        }

        public String getMulti() {
            return multi;
        }

        public void setMulti(String multi) {
            this.multi = multi;
        }

        public int getMaxcheck() {
            return maxcheck;
        }

        public void setMaxcheck(int maxcheck) {
            this.maxcheck = maxcheck;
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

        public List<TopicdetailBean> getTopicdetail() {
            return topicdetail;
        }

        public void setTopicdetail(List<TopicdetailBean> topicdetail) {
            this.topicdetail = topicdetail;
        }

        public static class TopicdetailBean {
            /**
             * topicdetailId : 1
             * content : 满意
             */

            private int topicdetailId;
            private String content;

            public int getTopicdetailId() {
                return topicdetailId;
            }

            public void setTopicdetailId(int topicdetailId) {
                this.topicdetailId = topicdetailId;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }


//    /**
//     * serialNo :
//     * timestamp : 20180521171749
//     * errorCode : 0000
//     * errorMsg : 正确返回
//     * hash :
//     * data :
//     * totalNum :
//     * point : 0
//     * info_list : [{"id":1,"topic_id":1,"title":"小区环境是否满意","vote_type":"2","start":"2018-05-01 00:00:00","end":"2018-06-30 00:00:00","itemContent":"小区服务调查","community_id":34,"created":"2018-05-04 00:00:00","modified":"2018-05-04 00:00:00","username":"1","votenum":0,"hitnum":0,"score":10,"status":"1","vote_id":1,"multi":"0","maxcheck":1,"topicdetail":[{"content":"满意","topicdetailId":1},{"content":"不满意","topicdetailId":2}],"imgs":"","icon_url":""},{"id":1,"topic_id":2,"title":"小区硬件需要升级的有哪些","vote_type":"2","start":"2018-05-01 00:00:00","end":"2018-06-30 00:00:00","itemContent":"小区服务调查","community_id":34,"created":"2018-05-04 00:00:00","modified":"2018-05-04 00:00:00","username":"1","votenum":0,"hitnum":0,"score":10,"status":"1","vote_id":1,"multi":"1","maxcheck":3,"topicdetail":[{"content":"路面","topicdetailId":3},{"content":"绿化","topicdetailId":4},{"content":"体育设施","topicdetailId":5}],"imgs":"","icon_url":""},{"id":1,"topic_id":3,"title":"物业服务是否满意","vote_type":"2","start":"2018-05-01 00:00:00","end":"2018-06-30 00:00:00","itemContent":"小区服务调查","community_id":34,"created":"2018-05-04 00:00:00","modified":"2018-05-04 00:00:00","username":"1","votenum":0,"hitnum":0,"score":10,"status":"1","vote_id":1,"multi":"0","maxcheck":1,"topicdetail":[{"content":"满意","topicdetailId":6},{"content":"不满意","topicdetailId":7}],"imgs":"","icon_url":""},{"id":1,"topic_id":4,"title":"你对社区环境感觉如何","vote_type":"2","start":"2018-05-01 00:00:00","end":"2018-06-30 00:00:00","itemContent":"小区服务调查","community_id":34,"created":"2018-05-04 00:00:00","modified":"2018-05-04 00:00:00","username":"1","votenum":0,"hitnum":0,"score":50,"status":"1","vote_id":1,"multi":"2","maxcheck":"","topicdetail":[{"content":"","topicdetailId":""}],"imgs":"","icon_url":""}]
//     * info_historylist :
//     * total_count :
//     */
//
//    private String serialNo;
//    private String timestamp;
//    private String errorCode;
//    private String errorMsg;
//    private String hash;
//    private String data;
//    private String totalNum;
//    private int point;
//    private String info_historylist;
//    private String total_count;
//    private List<InfoListBean> info_list;
//
//    public String getSerialNo() {
//        return serialNo;
//    }
//
//    public void setSerialNo(String serialNo) {
//        this.serialNo = serialNo;
//    }
//
//    public String getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(String timestamp) {
//        this.timestamp = timestamp;
//    }
//
//    public String getErrorCode() {
//        return errorCode;
//    }
//
//    public void setErrorCode(String errorCode) {
//        this.errorCode = errorCode;
//    }
//
//    public String getErrorMsg() {
//        return errorMsg;
//    }
//
//    public void setErrorMsg(String errorMsg) {
//        this.errorMsg = errorMsg;
//    }
//
//    public String getHash() {
//        return hash;
//    }
//
//    public void setHash(String hash) {
//        this.hash = hash;
//    }
//
//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
//        this.data = data;
//    }
//
//    public String getTotalNum() {
//        return totalNum;
//    }
//
//    public void setTotalNum(String totalNum) {
//        this.totalNum = totalNum;
//    }
//
//    public int getPoint() {
//        return point;
//    }
//
//    public void setPoint(int point) {
//        this.point = point;
//    }
//
//    public String getInfo_historylist() {
//        return info_historylist;
//    }
//
//    public void setInfo_historylist(String info_historylist) {
//        this.info_historylist = info_historylist;
//    }
//
//    public String getTotal_count() {
//        return total_count;
//    }
//
//    public void setTotal_count(String total_count) {
//        this.total_count = total_count;
//    }
//
//    public List<InfoListBean> getInfo_list() {
//        return info_list;
//    }
//
//    public void setInfo_list(List<InfoListBean> info_list) {
//        this.info_list = info_list;
//    }
//
//    public static class InfoListBean {
//        /**
//         * id : 1
//         * topic_id : 1
//         * title : 小区环境是否满意
//         * vote_type : 2
//         * start : 2018-05-01 00:00:00
//         * end : 2018-06-30 00:00:00
//         * itemContent : 小区服务调查
//         * community_id : 34
//         * created : 2018-05-04 00:00:00
//         * modified : 2018-05-04 00:00:00
//         * username : 1
//         * votenum : 0
//         * hitnum : 0
//         * score : 10
//         * status : 1
//         * vote_id : 1
//         * multi : 0
//         * maxcheck : 1
//         * topicdetail : [{"content":"满意","topicdetailId":1},{"content":"不满意","topicdetailId":2}]
//         * imgs :
//         * icon_url :
//         */
//
//        private int id;
//        private int topic_id;
//        private String title;
//        private String vote_type;
//        private String start;
//        private String end;
//        private String itemContent;
//        private int community_id;
//        private String created;
//        private String modified;
//        private String username;
//        private int votenum;
//        private int hitnum;
//        private int score;
//        private String status;
//        private int vote_id;
//        private String multi;
//        private int maxcheck;
//        private String imgs;
//        private String icon_url;
//        private List<TopicdetailBean> topicdetail;
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public int getTopic_id() {
//            return topic_id;
//        }
//
//        public void setTopic_id(int topic_id) {
//            this.topic_id = topic_id;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getVote_type() {
//            return vote_type;
//        }
//
//        public void setVote_type(String vote_type) {
//            this.vote_type = vote_type;
//        }
//
//        public String getStart() {
//            return start;
//        }
//
//        public void setStart(String start) {
//            this.start = start;
//        }
//
//        public String getEnd() {
//            return end;
//        }
//
//        public void setEnd(String end) {
//            this.end = end;
//        }
//
//        public String getItemContent() {
//            return itemContent;
//        }
//
//        public void setItemContent(String itemContent) {
//            this.itemContent = itemContent;
//        }
//
//        public int getCommunity_id() {
//            return community_id;
//        }
//
//        public void setCommunity_id(int community_id) {
//            this.community_id = community_id;
//        }
//
//        public String getCreated() {
//            return created;
//        }
//
//        public void setCreated(String created) {
//            this.created = created;
//        }
//
//        public String getModified() {
//            return modified;
//        }
//
//        public void setModified(String modified) {
//            this.modified = modified;
//        }
//
//        public String getUsername() {
//            return username;
//        }
//
//        public void setUsername(String username) {
//            this.username = username;
//        }
//
//        public int getVotenum() {
//            return votenum;
//        }
//
//        public void setVotenum(int votenum) {
//            this.votenum = votenum;
//        }
//
//        public int getHitnum() {
//            return hitnum;
//        }
//
//        public void setHitnum(int hitnum) {
//            this.hitnum = hitnum;
//        }
//
//        public int getScore() {
//            return score;
//        }
//
//        public void setScore(int score) {
//            this.score = score;
//        }
//
//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//
//        public int getVote_id() {
//            return vote_id;
//        }
//
//        public void setVote_id(int vote_id) {
//            this.vote_id = vote_id;
//        }
//
//        public String getMulti() {
//            return multi;
//        }
//
//        public void setMulti(String multi) {
//            this.multi = multi;
//        }
//
//        public int getMaxcheck() {
//            return maxcheck;
//        }
//
//        public void setMaxcheck(int maxcheck) {
//            this.maxcheck = maxcheck;
//        }
//
//        public String getImgs() {
//            return imgs;
//        }
//
//        public void setImgs(String imgs) {
//            this.imgs = imgs;
//        }
//
//        public String getIcon_url() {
//            return icon_url;
//        }
//
//        public void setIcon_url(String icon_url) {
//            this.icon_url = icon_url;
//        }
//
//        public List<TopicdetailBean> getTopicdetail() {
//            return topicdetail;
//        }
//
//        public void setTopicdetail(List<TopicdetailBean> topicdetail) {
//            this.topicdetail = topicdetail;
//        }
//
//        public static class TopicdetailBean {
//            /**
//             * content : 满意
//             * topicdetailId : 1
//             */
//
//            private String content;
//            private int topicdetailId;
//
//            public String getContent() {
//                return content;
//            }
//
//            public void setContent(String content) {
//                this.content = content;
//            }
//
//            public int getTopicdetailId() {
//                return topicdetailId;
//            }
//
//            public void setTopicdetailId(int topicdetailId) {
//                this.topicdetailId = topicdetailId;
//            }
//        }
//    }
}
