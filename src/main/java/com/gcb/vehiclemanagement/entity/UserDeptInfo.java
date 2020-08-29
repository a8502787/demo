package com.gcb.vehiclemanagement.entity;

import java.sql.Timestamp;

public class UserDeptInfo {
        private long userId;
        private String loginName;
        private String name;
        private String deptCode;
        private String deptId;
        private String deptName;
        private Timestamp driverBirthday;
        private Timestamp startDrivingDate;
        private String drivingType;
        private String driverPhoto;
        private String driverStatus;
        private String role;
        private String relation;

        public UserDeptInfo() {
            super();
        }

    public UserDeptInfo(long userId, String loginName, String name, String deptCode, String deptId, String deptName, Timestamp driverBirthday, Timestamp startDrivingDate, String drivingType, String driverPhoto, String driverStatus, String role, String relation) {
        this.userId = userId;
        this.loginName = loginName;
        this.name = name;
        this.deptCode = deptCode;
        this.deptId = deptId;
        this.deptName = deptName;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Timestamp getDriverBirthday() {
        return driverBirthday;
    }

    public void setDriverBirthday(Timestamp driverBirthday) {
        this.driverBirthday = driverBirthday;
    }

    public Timestamp getStartDrivingDate() {
        return startDrivingDate;
    }

    public void setStartDrivingDate(Timestamp startDrivingDate) {
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

    @Override
    public String toString() {
        return "UserDeptInfo{" +
                "userId=" + userId +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", deptCode=" + deptCode +
                ", deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                ", driverBirthday=" + driverBirthday +
                ", startDrivingDate=" + startDrivingDate +
                ", drivingType='" + drivingType + '\'' +
                ", driverPhoto='" + driverPhoto + '\'' +
                ", driverStatus='" + driverStatus + '\'' +
                ", role='" + role + '\'' +
                ", relation='" + relation + '\'' +
                '}';
    }
}
