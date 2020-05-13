package com.fenger.mypluginprovide.bean;


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
}
