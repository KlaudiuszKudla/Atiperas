package com.example.atipera.entity;

public enum Code {

    USER_NOT_EXIST_WITH_NAME("404"),
    UNACCEPTABLE_FORMAT("406");
    public final String label;
    private Code(String label){
        this.label = label;
    }
}
