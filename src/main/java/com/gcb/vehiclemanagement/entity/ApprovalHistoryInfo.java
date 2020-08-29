package com.gcb.vehiclemanagement.entity;

import java.sql.Timestamp;

public class ApprovalHistoryInfo {
    private String applyId;
    private long approverId;
    private String suggestion;
    private Timestamp approvalTime;
    private String type;
    private String status;

    private ApprovalHistoryInfo() {
        super();
    }

    public ApprovalHistoryInfo(String applyId, long approverId, String suggestion, Timestamp approvalTime,
                               String type, String status) {
        this.applyId = applyId;
        this.approverId = approverId;
        this.suggestion = suggestion;
        this.approvalTime = approvalTime;
        this.type = type;
        this.status = status;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public void setApproverId(long approverId) {
        this.approverId = approverId;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public void setApprovalTime(Timestamp approvalTime) {
        this.approvalTime = approvalTime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplyId() {
        return applyId;
    }

    public long getApproverId() {
        return approverId;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public Timestamp getApprovalTime() {
        return approvalTime;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ApprovalHistoryInfo{" +
                "applyId='" + applyId + '\'' +
                ", approverId=" + approverId +
                ", suggestion='" + suggestion + '\'' +
                ", approvalTime=" + approvalTime +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
