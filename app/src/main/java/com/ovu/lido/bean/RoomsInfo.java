package com.ovu.lido.bean;

public class RoomsInfo implements ItemEntity {
    private String room_name;

    public RoomsInfo(String room_name) {
        this.room_name = room_name;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    @Override
    public String getItem_name() {
        return room_name;
    }
}
