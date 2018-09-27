package com.xgw.wwx.common.em;

public enum LoggerStatusType {

    SUCCESS(1, "成功"),

    FAILED(0, "失败"),;

    private int status;

    private String desc;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    LoggerStatusType(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
