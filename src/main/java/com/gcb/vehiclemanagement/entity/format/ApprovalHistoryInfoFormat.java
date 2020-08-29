package com.gcb.vehiclemanagement.entity.format;

public class ApprovalHistoryInfoFormat {
    private String applyId;
    private long approverId;
    private String approverName;
    private String suggestion;
    private String approvalTime;
    private String type;
    private String status;

    public ApprovalHistoryInfoFormat(String applyId, long approverId, String approverName, String suggestion,
                                     String approvalTime, String type, String status) {
        this.applyId = applyId;
        this.approverId = approverId;
        this.approverName = approverName;
        this.suggestion = suggestion;
        this.approvalTime = approvalTime;
        this.type = type;
        this.status = status;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public long getApproverId() {
        return approverId;
    }

    public void setApproverId(long approverId) {
        this.approverId = approverId;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(String approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
