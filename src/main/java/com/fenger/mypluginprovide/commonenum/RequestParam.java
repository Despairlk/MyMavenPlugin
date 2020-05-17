package com.fenger.mypluginprovide.commonenum;

import org.apache.commons.lang3.StringUtils;

public enum RequestParam {
    BODY("body"),
    QUERY("query"),
    FILE("file");
    private String name;
    RequestParam(String name){
        this.name = name;
    }
    public static RequestParam getInstance(String name) {
        if(StringUtils.isBlank(name)){
            return QUERY;
        }
        switch (name) {
            case "body":
                return BODY;
            case "query":
                return QUERY;
            case "file":
                return FILE;
            default:
                return QUERY;
        }
    }
}
