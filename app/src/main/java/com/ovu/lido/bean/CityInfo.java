package com.ovu.lido.bean;

public class CityInfo implements ItemEntity {
    private String zone_code; //地区编码
    private String zone_name; //地区名称
    private String parent_code; //父地区编码
    private String zone_level; //地区级别 1省，2市，3区

    public CityInfo(String zone_code, String zone_name, String parent_code, String zone_level) {
        this.zone_code = zone_code;
        this.zone_name = zone_name;
        this.parent_code = parent_code;
        this.zone_level = zone_level;
    }

    public String getZone_code() {
        return zone_code;
    }

    public void setZone_code(String zone_code) {
        this.zone_code = zone_code;
    }

    public String getZone_name() {
        return zone_name;
    }

    public void setZone_name(String zone_name) {
        this.zone_name = zone_name;
    }

    public String getParent_code() {
        return parent_code;
    }

    public void setParent_code(String parent_code) {
        this.parent_code = parent_code;
    }

    public String getZone_level() {
        return zone_level;
    }

    public void setZone_level(String zone_level) {
        this.zone_level = zone_level;
    }

    @Override
    public String getItem_name() {
        return zone_name;
    }
}
