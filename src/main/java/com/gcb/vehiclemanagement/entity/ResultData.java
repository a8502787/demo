package com.gcb.vehiclemanagement.entity;

import java.util.Map;

public class ResultData {
    private Integer statusCode;
    private String statusRes;
    private Map<String,Object> info;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusRes() {
        return statusRes;
    }

    public void setStatusRes(String statusRes) {
        this.statusRes = statusRes;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }
}
