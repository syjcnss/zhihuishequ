package com.ovu.lido.bean;

import java.util.List;

public class IntimateStewardInfo {


    /**
     * serialNo :
     * timestamp : 20180531092013
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data : {"comment_count":14,"agree_count":4,"isBoo":"0","housekeeper_tel":"15072310946","employee_no":"","basicallySatisfaction_count":"0","housekeeper_intro":"1．您的安心小管家，对小区环境、安全的巡视，保障您的干净的生活环境和安全的居住条件\n2.您的舒心小家人，收集您的投诉与建议及时作出改进；对责任区域内公区的基础水电进行检修、微型疏通、添砖加瓦、防漏防渗等 \n3.您的专心小助理，为您的入伙，装修流程提供一站式服务\n4.您的贴心小棉袄，帮助您进行账单查询，更便捷和及时的缴纳物业费、水费、电费等费用\n5.您的爱心小伙伴，组织责任区域内的文化活动的开展，提高您的精神生活质量\n","dissatisfied_count":"1","housekeeper_name":"刘姮","reply_count":0,"list":[{"timestamp":"","hash":"","resident_id":"214027","token":"","resident":"","id":237,"housekeeper_id":2,"comment_content":"哈哈哈","comment_time":"2018-05-30 19:43:00","nick_name":"ldwy6018817","icon_url":"http://47.92.144.236:8080/img/20180530210017123145.png","reply":[],"status":"","human_name":"","mobile_no":"","comment_time2":""}],"workTime":"08:30 - 17:30","housekeeper_id":2,"community_id":34,"isAgree":"1","percentageSatisfaction":"100%","agreeType":"1","id":237,"housekeeper_pic":"http://120.27.196.188/community/info_img_path//img/201602/20160201134347_122.jpg","satisfaction_count":"1","boo_count":2}
     * totalNum :
     * point : 0
     * list :
     */

    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private DataBean data;
    private String totalNum;
    private int point;
    private String list;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public static class DataBean {
        /**
         * comment_count : 14
         * agree_count : 4
         * isBoo : 0
         * housekeeper_tel : 15072310946
         * employee_no :
         * basicallySatisfaction_count : 0
         * housekeeper_intro : 1．您的安心小管家，对小区环境、安全的巡视，保障您的干净的生活环境和安全的居住条件
         2.您的舒心小家人，收集您的投诉与建议及时作出改进；对责任区域内公区的基础水电进行检修、微型疏通、添砖加瓦、防漏防渗等
         3.您的专心小助理，为您的入伙，装修流程提供一站式服务
         4.您的贴心小棉袄，帮助您进行账单查询，更便捷和及时的缴纳物业费、水费、电费等费用
         5.您的爱心小伙伴，组织责任区域内的文化活动的开展，提高您的精神生活质量

         * dissatisfied_count : 1
         * housekeeper_name : 刘姮
         * reply_count : 0
         * list : [{"timestamp":"","hash":"","resident_id":"214027","token":"","resident":"","id":237,"housekeeper_id":2,"comment_content":"哈哈哈","comment_time":"2018-05-30 19:43:00","nick_name":"ldwy6018817","icon_url":"http://47.92.144.236:8080/img/20180530210017123145.png","reply":[],"status":"","human_name":"","mobile_no":"","comment_time2":""}]
         * workTime : 08:30 - 17:30
         * housekeeper_id : 2
         * community_id : 34
         * isAgree : 1
         * percentageSatisfaction : 100%
         * agreeType : 1
         * id : 237
         * housekeeper_pic : http://120.27.196.188/community/info_img_path//img/201602/20160201134347_122.jpg
         * satisfaction_count : 1
         * boo_count : 2
         */

        private int comment_count;
        private int agree_count;
        private String isBoo;
        private String housekeeper_tel;
        private String employee_no;
        private String basicallySatisfaction_count;
        private String housekeeper_intro;
        private String dissatisfied_count;
        private String housekeeper_name;
        private int reply_count;
        private String workTime;
        private int housekeeper_id;
        private int community_id;
        private String isAgree;
        private String percentageSatisfaction;
        private String agreeType;
        private int id;
        private String housekeeper_pic;
        private String satisfaction_count;
        private int boo_count;
        private List<ListBean> list;

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getAgree_count() {
            return agree_count;
        }

        public void setAgree_count(int agree_count) {
            this.agree_count = agree_count;
        }

        public String getIsBoo() {
            return isBoo;
        }

        public void setIsBoo(String isBoo) {
            this.isBoo = isBoo;
        }

        public String getHousekeeper_tel() {
            return housekeeper_tel;
        }

        public void setHousekeeper_tel(String housekeeper_tel) {
            this.housekeeper_tel = housekeeper_tel;
        }

        public String getEmployee_no() {
            return employee_no;
        }

        public void setEmployee_no(String employee_no) {
            this.employee_no = employee_no;
        }

        public String getBasicallySatisfaction_count() {
            return basicallySatisfaction_count;
        }

        public void setBasicallySatisfaction_count(String basicallySatisfaction_count) {
            this.basicallySatisfaction_count = basicallySatisfaction_count;
        }

        public String getHousekeeper_intro() {
            return housekeeper_intro;
        }

        public void setHousekeeper_intro(String housekeeper_intro) {
            this.housekeeper_intro = housekeeper_intro;
        }

        public String getDissatisfied_count() {
            return dissatisfied_count;
        }

        public void setDissatisfied_count(String dissatisfied_count) {
            this.dissatisfied_count = dissatisfied_count;
        }

        public String getHousekeeper_name() {
            return housekeeper_name;
        }

        public void setHousekeeper_name(String housekeeper_name) {
            this.housekeeper_name = housekeeper_name;
        }

        public int getReply_count() {
            return reply_count;
        }

        public void setReply_count(int reply_count) {
            this.reply_count = reply_count;
        }

        public String getWorkTime() {
            return workTime;
        }

        public void setWorkTime(String workTime) {
            this.workTime = workTime;
        }

        public int getHousekeeper_id() {
            return housekeeper_id;
        }

        public void setHousekeeper_id(int housekeeper_id) {
            this.housekeeper_id = housekeeper_id;
        }

        public int getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(int community_id) {
            this.community_id = community_id;
        }

        public String getIsAgree() {
            return isAgree;
        }

        public void setIsAgree(String isAgree) {
            this.isAgree = isAgree;
        }

        public String getPercentageSatisfaction() {
            return percentageSatisfaction;
        }

        public void setPercentageSatisfaction(String percentageSatisfaction) {
            this.percentageSatisfaction = percentageSatisfaction;
        }

        public String getAgreeType() {
            return agreeType;
        }

        public void setAgreeType(String agreeType) {
            this.agreeType = agreeType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getHousekeeper_pic() {
            return housekeeper_pic;
        }

        public void setHousekeeper_pic(String housekeeper_pic) {
            this.housekeeper_pic = housekeeper_pic;
        }

        public String getSatisfaction_count() {
            return satisfaction_count;
        }

        public void setSatisfaction_count(String satisfaction_count) {
            this.satisfaction_count = satisfaction_count;
        }

        public int getBoo_count() {
            return boo_count;
        }

        public void setBoo_count(int boo_count) {
            this.boo_count = boo_count;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * timestamp :
             * hash :
             * resident_id : 214027
             * token :
             * resident :
             * id : 237
             * housekeeper_id : 2
             * comment_content : 哈哈哈
             * comment_time : 2018-05-30 19:43:00
             * nick_name : ldwy6018817
             * icon_url : http://47.92.144.236:8080/img/20180530210017123145.png
             * reply : []
             * status :
             * human_name :
             * mobile_no :
             * comment_time2 :
             */

            private String timestamp;
            private String hash;
            private String resident_id;
            private String token;
            private String resident;
            private int id;
            private int housekeeper_id;
            private String comment_content;
            private String comment_time;
            private String nick_name;
            private String icon_url;
            private String status;
            private String human_name;
            private String mobile_no;
            private String comment_time2;
            private List<?> reply;

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }

            public String getHash() {
                return hash;
            }

            public void setHash(String hash) {
                this.hash = hash;
            }

            public String getResident_id() {
                return resident_id;
            }

            public void setResident_id(String resident_id) {
                this.resident_id = resident_id;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }

            public String getResident() {
                return resident;
            }

            public void setResident(String resident) {
                this.resident = resident;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getHousekeeper_id() {
                return housekeeper_id;
            }

            public void setHousekeeper_id(int housekeeper_id) {
                this.housekeeper_id = housekeeper_id;
            }

            public String getComment_content() {
                return comment_content;
            }

            public void setComment_content(String comment_content) {
                this.comment_content = comment_content;
            }

            public String getComment_time() {
                return comment_time;
            }

            public void setComment_time(String comment_time) {
                this.comment_time = comment_time;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getHuman_name() {
                return human_name;
            }

            public void setHuman_name(String human_name) {
                this.human_name = human_name;
            }

            public String getMobile_no() {
                return mobile_no;
            }

            public void setMobile_no(String mobile_no) {
                this.mobile_no = mobile_no;
            }

            public String getComment_time2() {
                return comment_time2;
            }

            public void setComment_time2(String comment_time2) {
                this.comment_time2 = comment_time2;
            }

            public List<?> getReply() {
                return reply;
            }

            public void setReply(List<?> reply) {
                this.reply = reply;
            }
        }
    }
}
