package com.ovu.lido.bean;

import java.util.List;

public class ReceiveAddressInfo {

    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private String token;
    private Data data;
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

    public String  getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public static class Data {

        private ThirdAddressPageBean third_address_page;// //三方地址分页
        private SelfAddressBean self_address;//自营地址

        public ThirdAddressPageBean getThird_address_page() {
            return third_address_page;
        }

        public void setThird_address_page(ThirdAddressPageBean third_address_page) {
            this.third_address_page = third_address_page;
        }

        public SelfAddressBean getSelf_address() {
            return self_address;
        }

        public void setSelf_address(SelfAddressBean self_address) {
            this.self_address = self_address;
        }

        public static class ThirdAddressPageBean {

            private int pageSize;
            private int pageIndex;
            private int pageTotal;
            private int totalCount;
            private List<DataBean> data;

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getPageIndex() {
                return pageIndex;
            }

            public void setPageIndex(int pageIndex) {
                this.pageIndex = pageIndex;
            }

            public int getPageTotal() {
                return pageTotal;
            }

            public void setPageTotal(int pageTotal) {
                this.pageTotal = pageTotal;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean {

                private String contact_name;//联系人
                private String contact_phone;//联系电话
                private String city_code;//t_c_zone.zone_code
                private String province_code;//t_c_zone.zone_code
                private int is_default;//是否三方默认地址 0.否1.是
                private String province_name;//省
                private String district_name;//区
                private String city_name;//市
                private String district_code;//t_c_zone.zone_code
                private String id;//resident_receive_address.id
                private String detail;//详细地址

                public String getContact_name() {
                    return contact_name;
                }

                public void setContact_name(String contact_name) {
                    this.contact_name = contact_name;
                }

                public String getContact_phone() {
                    return contact_phone;
                }

                public void setContact_phone(String contact_phone) {
                    this.contact_phone = contact_phone;
                }

                public String getCity_code() {
                    return city_code;
                }

                public void setCity_code(String city_code) {
                    this.city_code = city_code;
                }

                public String getProvince_code() {
                    return province_code;
                }

                public void setProvince_code(String province_code) {
                    this.province_code = province_code;
                }

                public int getIs_default() {
                    return is_default;
                }

                public void setIs_default(int is_default) {
                    this.is_default = is_default;
                }

                public String getProvince_name() {
                    return province_name;
                }

                public void setProvince_name(String province_name) {
                    this.province_name = province_name;
                }

                public String getDistrict_name() {
                    return district_name;
                }

                public void setDistrict_name(String district_name) {
                    this.district_name = district_name;
                }

                public String getCity_name() {
                    return city_name;
                }

                public void setCity_name(String city_name) {
                    this.city_name = city_name;
                }

                public String getDistrict_code() {
                    return district_code;
                }

                public void setDistrict_code(String district_code) {
                    this.district_code = district_code;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }
            }
        }

        public static class SelfAddressBean {

            private String address;//地址
            private String mobile_no;//手机号
            private String name;//名称

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getMobile_no() {
                return mobile_no;
            }

            public void setMobile_no(String mobile_no) {
                this.mobile_no = mobile_no;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
