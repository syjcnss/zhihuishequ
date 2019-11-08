package com.ovu.lido.bean;

public class ProductDetailInfo {

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
         * order_service_time : {"server_time":"2019-05-28 16:54:31","service_time_end":"18:00","service_time_start":"08:00"}
         * order_address : {"address":"武汉市洪山区珞狮南路497号","mobile_no":"15666666666","name":"ldwy4774103"}
         */

        private OrderServiceTimeBean order_service_time;
        private OrderAddressBean order_address;

        public OrderServiceTimeBean getOrder_service_time() {
            return order_service_time;
        }

        public void setOrder_service_time(OrderServiceTimeBean order_service_time) {
            this.order_service_time = order_service_time;
        }

        public OrderAddressBean getOrder_address() {
            return order_address;
        }

        public void setOrder_address(OrderAddressBean order_address) {
            this.order_address = order_address;
        }

        public static class OrderServiceTimeBean {
            /**
             * server_time : 2019-05-28 16:54:31
             * service_time_end : 18:00
             * service_time_start : 08:00
             */
            private String service_add_hours;
            private String server_time;//服务器时间
            private String service_time_end;//上门结束时间
            private String service_time_start;//上门开始时间

            public String getService_add_hours() {
                return service_add_hours;
            }

            public void setService_add_hours(String service_add_hours) {
                this.service_add_hours = service_add_hours;
            }

            public String getServer_time() {
                return server_time;
            }

            public void setServer_time(String server_time) {
                this.server_time = server_time;
            }

            public String getService_time_end() {
                return service_time_end;
            }

            public void setService_time_end(String service_time_end) {
                this.service_time_end = service_time_end;
            }

            public String getService_time_start() {
                return service_time_start;
            }

            public void setService_time_start(String service_time_start) {
                this.service_time_start = service_time_start;
            }
        }

        public static class OrderAddressBean {
            /**
             * address : 武汉市洪山区珞狮南路497号
             * mobile_no : 15666666666
             * name : ldwy4774103
             */

            private String address;//地址
            private String mobile_no;//联系电话
            private String name;

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
