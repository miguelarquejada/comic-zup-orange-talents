package com.marb.zupcomics.model.comic;


public class ComicDataWrapper {
    private Integer code;
    private String status;
    private ComicDataContainer data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ComicDataContainer getData() {
        return data;
    }

    public void setData(ComicDataContainer data) {
        this.data = data;
    }
}
