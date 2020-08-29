package com.gcb.vehiclemanagement.entity.format;

public class MaintenanceApplyInfoFormat {
    private String applyId;
    private long applicantId;
    private String applicantName;
    private String vehicleId;
    private long driverId;
    private String driverName;
    private String createTime;
    private String startTime;
    private String endTime;
    private String destination;
    private String cause;
    private String maintenanceProvider;
    private String entourage;
    private String contactPhone;
    private String status;
    private String confirm;
    private String finish;

    public MaintenanceApplyInfoFormat(String applyId, long applicantId, String applicantName, String vehicleId, long driverId,
                                      String driverName, String createTime, String startTime, String endTime, String destination,
                                      String cause, String maintenanceProvider, String entourage, String contactPhone, String status,
                                      String confirm, String finish) {
        this.applyId = applyId;
        this.applicantId = applicantId;
        this.applicantName = applicantName;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
        this.driverName = driverName;
        this.createTime = createTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.destination = destination;
        this.cause = cause;
        this.maintenanceProvider = maintenanceProvider;
        this.entourage = entourage;
        this.contactPhone = contactPhone;
        this.status = status;
        this.confirm = confirm;
        this.finish = finish;
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

    public String getMaintenanceProvider() {
        return maintenanceProvider;
    }

    public void setMaintenanceProvider(String maintenanceProvider) {
        this.maintenanceProvider = maintenanceProvider;
    }

    public String getEntourage() {
        return entourage;
    }

    public void setEntourage(String entourage) {
        this.entourage = entourage;
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
}
