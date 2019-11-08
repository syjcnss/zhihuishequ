package com.ovu.lido.bean;

import java.util.List;

public class HomePageInfo {
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private String token;
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
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

    public static class DataBean{
        /*广告banner*/
        private List<ADInfoDataBean> adInfo;
        /*社区活动banner*/
        private List<ActivitiesDataBean> activities;
        /*社区服务类型列表*/
        private List<CommunityServiceDataBean> communityService;
        /*特供商品列表*/
        private List<SpecialProductsDataBean> specialProducts;
        /*管家*/
        private HousekeeperDataBean housekeeper;
        /*小区头条公告*/
        private List<NoticeListDataBean> noticeList;

        public List<ADInfoDataBean> getAdInfo() {
            return adInfo;
        }

        public void setAdInfo(List<ADInfoDataBean> adInfo) {
            this.adInfo = adInfo;
        }

        public List<ActivitiesDataBean> getActivities() {
            return activities;
        }

        public void setActivities(List<ActivitiesDataBean> activities) {
            this.activities = activities;
        }

        public List<CommunityServiceDataBean> getCommunityService() {
            return communityService;
        }

        public void setCommunityService(List<CommunityServiceDataBean> communityService) {
            this.communityService = communityService;
        }

        public List<SpecialProductsDataBean> getSpecialProducts() {
            return specialProducts;
        }

        public void setSpecialProducts(List<SpecialProductsDataBean> specialProducts) {
            this.specialProducts = specialProducts;
        }

        public HousekeeperDataBean getHousekeeper() {
            return housekeeper;
        }

        public void setHousekeeper(HousekeeperDataBean housekeeper) {
            this.housekeeper = housekeeper;
        }

        public List<NoticeListDataBean> getNoticeList() {
            return noticeList;
        }

        public void setNoticeList(List<NoticeListDataBean> noticeList) {
            this.noticeList = noticeList;
        }

        public static class ADInfoDataBean {
            private int ad_content_id;
            private String img;
            private String ad_type;
            private String ad_group_layout;
            private String ad_name;
            private String link;
            private int id;
            private int link_state;
            private int ad_group_id;

            public int getAd_content_id() {
                return ad_content_id;
            }

            public void setAd_content_id(int ad_content_id) {
                this.ad_content_id = ad_content_id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getAd_type() {
                return ad_type;
            }

            public void setAd_type(String ad_type) {
                this.ad_type = ad_type;
            }

            public String getAd_group_layout() {
                return ad_group_layout;
            }

            public void setAd_group_layout(String ad_group_layout) {
                this.ad_group_layout = ad_group_layout;
            }

            public String getAd_name() {
                return ad_name;
            }

            public void setAd_name(String ad_name) {
                this.ad_name = ad_name;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLink_state() {
                return link_state;
            }

            public void setLink_state(int link_state) {
                this.link_state = link_state;
            }

            public int getAd_group_id() {
                return ad_group_id;
            }

            public void setAd_group_id(int ad_group_id) {
                this.ad_group_id = ad_group_id;
            }
        }

        public static class ActivitiesDataBean {
            private int id;
            private String activity_name;
            private int activity_scope;
            private int community_id;
            private Object creator_id;
            private int create_type;
            private int activity_type_id;
            private String sponsor;
            private String activity_img;
            private String address;
            private String begin_time;
            private String end_time;
            private String enroll_end_time;
            private String introduce;
            private String tel;
            private int enroll_limit;
            private String activity_content;
            private int state;
            private int del;
            private Object type_name;
            private int enrollCount;
            private int likeCount;
            private int commentCount;
            private Object creator_name;
            private boolean is_like;
            private boolean is_enroll;
            private boolean is_end;
            private Object activityComments;

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

            public int getActivity_scope() {
                return activity_scope;
            }

            public void setActivity_scope(int activity_scope) {
                this.activity_scope = activity_scope;
            }

            public int getCommunity_id() {
                return community_id;
            }

            public void setCommunity_id(int community_id) {
                this.community_id = community_id;
            }

            public Object getCreator_id() {
                return creator_id;
            }

            public void setCreator_id(Object creator_id) {
                this.creator_id = creator_id;
            }

            public int getCreate_type() {
                return create_type;
            }

            public void setCreate_type(int create_type) {
                this.create_type = create_type;
            }

            public int getActivity_type_id() {
                return activity_type_id;
            }

            public void setActivity_type_id(int activity_type_id) {
                this.activity_type_id = activity_type_id;
            }

            public String getSponsor() {
                return sponsor;
            }

            public void setSponsor(String sponsor) {
                this.sponsor = sponsor;
            }

            public String getActivity_img() {
                return activity_img;
            }

            public void setActivity_img(String activity_img) {
                this.activity_img = activity_img;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
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

            public String getEnroll_end_time() {
                return enroll_end_time;
            }

            public void setEnroll_end_time(String enroll_end_time) {
                this.enroll_end_time = enroll_end_time;
            }

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
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

            public String getActivity_content() {
                return activity_content;
            }

            public void setActivity_content(String activity_content) {
                this.activity_content = activity_content;
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

            public Object getType_name() {
                return type_name;
            }

            public void setType_name(Object type_name) {
                this.type_name = type_name;
            }

            public int getEnrollCount() {
                return enrollCount;
            }

            public void setEnrollCount(int enrollCount) {
                this.enrollCount = enrollCount;
            }

            public int getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(int likeCount) {
                this.likeCount = likeCount;
            }

            public int getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(int commentCount) {
                this.commentCount = commentCount;
            }

            public Object getCreator_name() {
                return creator_name;
            }

            public void setCreator_name(Object creator_name) {
                this.creator_name = creator_name;
            }

            public boolean isIs_like() {
                return is_like;
            }

            public void setIs_like(boolean is_like) {
                this.is_like = is_like;
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

            public Object getActivityComments() {
                return activityComments;
            }

            public void setActivityComments(Object activityComments) {
                this.activityComments = activityComments;
            }
        }

        public static class CommunityServiceDataBean {
            private String typecode;
            private Object typepid;
            private String ID;
            private String typegroupid;
            private String create_date;
            private String typename;
            private String create_name;

            public String getTypecode() {
                return typecode;
            }

            public void setTypecode(String typecode) {
                this.typecode = typecode;
            }

            public Object getTypepid() {
                return typepid;
            }

            public void setTypepid(Object typepid) {
                this.typepid = typepid;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getTypegroupid() {
                return typegroupid;
            }

            public void setTypegroupid(String typegroupid) {
                this.typegroupid = typegroupid;
            }

            public String getCreate_date() {
                return create_date;
            }

            public void setCreate_date(String create_date) {
                this.create_date = create_date;
            }

            public String getTypename() {
                return typename;
            }

            public void setTypename(String typename) {
                this.typename = typename;
            }

            public String getCreate_name() {
                return create_name;
            }

            public void setCreate_name(String create_name) {
                this.create_name = create_name;
            }
        }

        public static class SpecialProductsDataBean {
            private String id;
            private String name;//商品名称
            private double cost_price;//成本价
            private double price;//售价
            private String thumbnail;//缩略图
            private String picture;//详情图
            private String description;//描述

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getCost_price() {
                return cost_price;
            }

            public void setCost_price(double cost_price) {
                this.cost_price = cost_price;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }

        public static class HousekeeperDataBean {
            private int comment_count;
            private int agree_count;
            private String isBoo;
            private String housekeeper_tel;
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
            private  int id;
            private  String housekeeper_pic;
            private String satisfaction_count;
            private int boo_count;

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
        }

        public static class NoticeListDataBean {
            private String title;
            private String intro;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }
        }
    }
}
