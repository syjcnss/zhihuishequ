package com.ovu.lido.bean;

public class WorkDetailInfo {

    /**
     * timestamp : 20180529191119
     * errorCode : 0000
     * errorMsg : 正确返回
     * hash : null
     * token : null
     * data : <p>第一条 依据《中华人民共和国民法通则》及法律、行政法规、规章的有关规定，并依据合伙协议，按照平等、自愿、公平、诚实信用的原则，经新合伙人与原合伙人全体协商一致，订立本协议。 &nbsp;第二条 本合伙为普通个人合伙，是根据合伙协议自愿组成的个体工商户。全体合伙人愿意遵守国家有关的法律、法规、规章，依法纳税，守法经营。第三条 合伙字号（个体工商户字号。</p>
     * point : 0
     */

    private String timestamp;
    private String errorCode;
    private String errorMsg;
    private Object hash;
    private Object token;
    private String data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
