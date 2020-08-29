package com.gcb.vehiclemanagement.entity.format;

public class UserDeptInfoFormat {
    private long userId;
    private String userName;
    private String deptName;
    private String loginName;
    private String driverBirthday;
    private String startDrivingDate;
    private String drivingType;
    private String driverPhoto;
    private String driverStatus;
    private String role;
    private String relation;

    public UserDeptInfoFormat(long userId, String userName, String deptName, String loginName, String driverBirthday, String startDrivingDate, String drivingType, String driverPhoto, String driverStatus, String role, String relation) {
        this.userId = userId;
        this.userName = userName;
        this.deptName = deptName;
        this.loginName = loginName;
        this.driverBirthday = driverBirthday;
        this.startDrivingDate = startDrivingDate;
        this.drivingType = drivingType;
        this.driverPhoto = driverPhoto;
        this.driverStatus = driverStatus;
        this.role = role;
        this.relation = relation;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getDriverBirthday() {
        return driverBirthday;
    }

    public void setDriverBirthday(String driverBirthday) {
        this.driverBirthday = driverBirthday;
    }

    public String getStartDrivingDate() {
        return startDrivingDate;
    }

    public void setStartDrivingDate(String startDrivingDate) {
        this.startDrivingDate = startDrivingDate;
    }

    public String getDrivingType() {
        return drivingType;
    }

    public void setDrivingType(String drivingType) {
        this.drivingType = drivingType;
    }

    public String getDriverPhoto() {
        return driverPhoto;
    }

    public void setDriverPhoto(String driverPhoto) {
        this.driverPhoto = driverPhoto;
    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
