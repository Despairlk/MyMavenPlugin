package com.fenger.mypluginprovide.commonenum;

import org.apache.commons.lang3.StringUtils;

public enum  RequestMethod {
    GET("get"),
    HEAD("head"),
    POST("postt"),
    PUT("put"),
    PATCH("patch"),
    DELETE("delete"),
    OPTIONS("options"),
    TRACE("trace");
    private String name;
    RequestMethod(String name){
        this.name = name;
    }
    public static RequestMethod getInstance(String name) {
        if(StringUtils.isBlank(name)){
            return GET;
        }
        switch (name) {
            case "get":
                return GET;
            case "head":
                return HEAD;
            case "post":
                return POST;
            case "put":
                return PUT;
            case "patch":
                return PATCH;
            case "delete":
                return DELETE;
            case "options":
                return OPTIONS;
            case "trace":
                return TRACE;
            default:
                return GET;
        }
    }
}
