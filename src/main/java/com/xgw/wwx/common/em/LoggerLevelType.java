package com.xgw.wwx.common.em;

public enum LoggerLevelType {

    INFO("INFO", "普通"),

    ALERT("ALERT", "告警"),;

    private String level;

    private String desc;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    LoggerLevelType(String level, String desc) {
        this.level = level;
        this.desc = desc;
    }
}
