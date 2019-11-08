package com.ovu.lido.bean;

import java.util.List;

public class OrderInfo {

    /**
     * timestamp : 20190529142001
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : {"pageSize":5,"pageIndex":0,"pageTotal":1,"totalCount":2,"data":[{"amount":4,"thumbnail":"http://localhost:8210/img/20190520/20190520170607.jpg","product_num":1,"id":"1298c15b-0175-4184-95b5-2fc49b429873","product_name":"橘子味脉动","status":0}]}
     * point : 0
     */

    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private String token;
    private DataBeanX data;
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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public static class DataBeanX {
        /**
         * pageSize : 5
         * pageIndex : 0
         * pageTotal : 1
         * totalCount : 2
         * data : [{"amount":4,"thumbnail":"http://localhost:8210/img/20190520/20190520170607.jpg","product_num":1,"id":"1298c15b-0175-4184-95b5-2fc49b429873","product_name":"橘子味脉动","status":0}]
         */

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
            /**
             * amount : 4.0
             * thumbnail : http://localhost:8210/img/20190520/20190520170607.jpg
             * product_num : 1
             * id : 1298c15b-0175-4184-95b5-2fc49b429873
             * product_name : 橘子味脉动
             * status : 0
             */

            private double amount;
            private String thumbnail;
            private int product_num;
            private String id;
            private String product_name;
            private int status;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public int getProduct_num() {
                return product_num;
            }

            public void setProduct_num(int product_num) {
                this.product_num = product_num;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
