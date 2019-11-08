package com.ovu.lido.bean;

import java.util.List;

public class CommunityServiceProductInfo {
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private Object hash;
    private Object token;
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
             * thumbnail : http://localhost:8210/img/20190520/20190520170607.jpg
             * category_name : 土木维修
             * module_type : 2
             * price : 4.0
             * name : 老干妈味脉动
             * category_type : 0
             * description : <p>超好喝!<img src="http://127.0.0.1/estate/plug-in/ueditor/jsp/imgs/32641558343190968.png" title="客户入驻流程.png"/></p>
             * id : 5a583787-fc06-44d8-a860-cfa1dec2ea81
             * picture : http://localhost:8210/img/20190520/20190520170610.jpg
             * operator_type : 0
             */

            private String thumbnail;
            private String category_name;
            private int module_type;
            private double price;
            private String name;
            private int category_type;
            private String description;
            private String id;
            private String picture;
            private int operator_type;

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }

            public int getModule_type() {
                return module_type;
            }

            public void setModule_type(int module_type) {
                this.module_type = module_type;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCategory_type() {
                return category_type;
            }

            public void setCategory_type(int category_type) {
                this.category_type = category_type;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public int getOperator_type() {
                return operator_type;
            }

            public void setOperator_type(int operator_type) {
                this.operator_type = operator_type;
            }
        }
    }
}
