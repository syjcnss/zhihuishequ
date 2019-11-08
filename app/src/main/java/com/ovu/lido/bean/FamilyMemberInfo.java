package com.ovu.lido.bean;

import java.io.File;
import java.io.Serializable;
import java.util.Map;

public class FamilyMemberInfo implements Serializable {
    private String familyMemberId;// 家庭成员id
    private Map<String, File> picPath; // 家庭成员照片
    private String familyMemberName; // 家庭成员姓名
    private String familyMemberSex; // 性别，0：女，1：男
    private String familyMemberMobile; // 家庭成员电话
    private String familyMemberRelation;// 与业主关系
    private String familyMemberIDCard;// 身份证
    private String familyMemberWorkUnit;// 工作单位

    public FamilyMemberInfo(Map<String, File> picPath, String familyMemberName, String familyMemberSex, String familyMemberMobile, String familyMemberRelation, String familyMemberIDCard, String familyMemberWorkUnit) {
        this.picPath = picPath;
        this.familyMemberName = familyMemberName;
        this.familyMemberSex = familyMemberSex;
        this.familyMemberMobile = familyMemberMobile;
        this.familyMemberRelation = familyMemberRelation;
        this.familyMemberIDCard = familyMemberIDCard;
        this.familyMemberWorkUnit = familyMemberWorkUnit;
    }

    public String getFamilyMemberId() {
        return familyMemberId;
    }

    public void setFamilyMemberId(String familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    public Map<String, File> getPicPath() {
        return picPath;
    }

    public void setPicPath(Map<String, File> picPath) {
        this.picPath = picPath;
    }

    public String getFamilyMemberName() {
        return familyMemberName;
    }

    public void setFamilyMemberName(String familyMemberName) {
        this.familyMemberName = familyMemberName;
    }

    public String getFamilyMemberSex() {
        return familyMemberSex;
    }

    public void setFamilyMemberSex(String familyMemberSex) {
        this.familyMemberSex = familyMemberSex;
    }

    public String getFamilyMemberMobile() {
        return familyMemberMobile;
    }

    public void setFamilyMemberMobile(String familyMemberMobile) {
        this.familyMemberMobile = familyMemberMobile;
    }

    public String getFamilyMemberRelation() {
        return familyMemberRelation;
    }

    public void setFamilyMemberRelation(String familyMemberRelation) {
        this.familyMemberRelation = familyMemberRelation;
    }

    public String getFamilyMemberIDCard() {
        return familyMemberIDCard;
    }

    public void setFamilyMemberIDCard(String familyMemberIDCard) {
        this.familyMemberIDCard = familyMemberIDCard;
    }

    public String getFamilyMemberWorkUnit() {
        return familyMemberWorkUnit;
    }

    public void setFamilyMemberWorkUnit(String familyMemberWorkUnit) {
        this.familyMemberWorkUnit = familyMemberWorkUnit;
    }
}
