package com.ovu.lido.bean;

import com.google.gson.annotations.SerializedName;

public class VersionBean {


    /**
     * serialNo :
     * timestamp : 20180605095547
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash :
     * data :
     * totalNum :
     * point : 0
     * app_list : {"1_1":{"app_url":"http://120.27.196.188/community/app_download_path/201708/1_1_1.0.19_19_20170831091407.apk","version_code":"20","version_name":"3.0.1","content":"","force_update":"1"}}
     */

    private String serialNo;
    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private String hash;
    private String data;
    private String totalNum;
    private int point;
    private AppListBean app_list;

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

    public AppListBean getApp_list() {
        return app_list;
    }

    public void setApp_list(AppListBean app_list) {
        this.app_list = app_list;
    }

    public static class AppListBean {
        /**
         * 1_1 : {"app_url":"http://120.27.196.188/community/app_download_path/201708/1_1_1.0.19_19_20170831091407.apk","version_code":"20","version_name":"3.0.1","content":"","force_update":"1"}
         */

        @SerializedName("1_1")
        private _$11Bean _$1_1;

        public _$11Bean get_$1_1() {
            return _$1_1;
        }

        public void set_$1_1(_$11Bean _$1_1) {
            this._$1_1 = _$1_1;
        }

        public static class _$11Bean {
            /**
             * app_url : http://120.27.196.188/community/app_download_path/201708/1_1_1.0.19_19_20170831091407.apk
             * version_code : 20
             * version_name : 3.0.1
             * content :
             * force_update : 1
             */

            private String app_url;
            private String version_code;
            private String version_name;
            private String content;
            private String force_update;

            public String getApp_url() {
                return app_url;
            }

            public void setApp_url(String app_url) {
                this.app_url = app_url;
            }

            public String getVersion_code() {
                return version_code;
            }

            public void setVersion_code(String version_code) {
                this.version_code = version_code;
            }

            public String getVersion_name() {
                return version_name;
            }

            public void setVersion_name(String version_name) {
                this.version_name = version_name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getForce_update() {
                return force_update;
            }

            public void setForce_update(String force_update) {
                this.force_update = force_update;
            }
        }
    }
}
