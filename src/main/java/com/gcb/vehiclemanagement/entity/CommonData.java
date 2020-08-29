package com.gcb.vehiclemanagement.entity;

public class CommonData {
    private String dataType;
    private String data;

    public CommonData() {
        super();
    }

    public CommonData(String dataType, String data) {
        this.dataType = dataType;
        this.data = data;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonData{" +
                "dataType='" + dataType + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
