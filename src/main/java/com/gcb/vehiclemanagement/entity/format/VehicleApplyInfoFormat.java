package com.gcb.vehiclemanagement.entity.format;

public class VehicleApplyInfoFormat {
    private String applyId;
    private long applicantId;
    private String applicantName;
    private String vehicleId;
    private long driverId;
    private String driverName;
    private long departmentCode;
    private String applyDepartment;
    private String vehicleUser;
    private String createTime;
    private String startTime;
    private String endTime;
    private String destination;
    private String cause;
    private String entourage;
    private int peopleNumber;
    private String contactPhone;
    private String status;
    private String confirm;
    private String finish;
    private String picUrl;

    public VehicleApplyInfoFormat(String applyId, long applicantId, String applicantName, String vehicleId, long driverId,
                                  String driverName, long departmentCode, String applyDepartment, String vehicleUser,
                                  String createTime, String startTime, String endTime, String destination, String cause,
                                  String entourage, int peopleNumber, String contactPhone, String status, String confirm, String finish,String picUrl) {
        this.applyId = applyId;
        this.applicantId = applicantId;
        this.applicantName = applicantName;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.driverName = driverName;
        this.departmentCode = departmentCode;
        this.applyDepartment = applyDepartment;
        this.vehicleUser = vehicleUser;
        this.createTime = createTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.destination = destination;
        this.cause = cause;
        this.entourage = entourage;
        this.peopleNumber = peopleNumber;
        this.contactPhone = contactPhone;
        this.status = status;
        this.confirm = confirm;
        this.finish = finish;
        this.picUrl = picUrl;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(long applicantId) {
        this.applicantId = applicantId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public long getDriverId() {
        return driverId;
    }

    public void setDriverId(long driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public long getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(long departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getApplyDepartment() {
        return applyDepartment;
    }

    public void setApplyDepartment(String applyDepartment) {
        this.applyDepartment = applyDepartment;
    }

    public String getVehicleUser() {
        return vehicleUser;
    }

    public void setVehicleUser(String vehicleUser) {
        this.vehicleUser = vehicleUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getEntourage() {
        return entourage;
    }

    public void setEntourage(String entourage) {
        this.entourage = entourage;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
