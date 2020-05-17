package com.fenger.mypluginprovide.bean;


import com.fenger.mypluginprovide.commonenum.RequestParam;
import lombok.Data;

@Data
public class ParamerBean {
    /*类型*/
    private Class<?> tClass;
    /*类型名*/
    private String type;
    /*是否必填*/
    private boolean required;
    /*默认值*/
    private Object defaultvalue;
    /*请求参数类型*/
    private RequestParam requestParamType;
    /*参数名*/
    private String paramName;
}
