package com.ovu.lido.bean;

public class HeadLinesInfo {
    private String title;
    private String time;
    private String content;

    public HeadLinesInfo(String title, String time, String content) {
        this.title = title;
        this.time = time;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
