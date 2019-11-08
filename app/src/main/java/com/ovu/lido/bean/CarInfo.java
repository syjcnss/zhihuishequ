package com.ovu.lido.bean;

import java.io.Serializable;

public class CarInfo implements Serializable {
    private String carName; // 车主姓名
    private String carNumber; // 牌照号码
    private String carKind; // 车辆种类
    private String carModel; // 车辆型号
    private String carBrand; // 车辆品牌
    private String carColor; // 车辆颜色

    public CarInfo(String carName, String carNumber, String carKind, String carModel, String carBrand, String carColor) {
        this.carName = carName;
        this.carNumber = carNumber;
        this.carKind = carKind;
        this.carModel = carModel;
        this.carBrand = carBrand;
        this.carColor = carColor;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carType) {
        this.carModel = carType;
    }

    public String getCarKind() {
        return carKind;
    }

    public void setCarKind(String carKind) {
        this.carKind = carKind;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }
}
