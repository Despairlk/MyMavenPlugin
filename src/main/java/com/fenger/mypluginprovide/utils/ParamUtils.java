package com.fenger.mypluginprovide.utils;

import org.apache.commons.lang3.StringUtils;

public class ParamUtils {

    public static String transToString(Object obj){
        if(null == obj){
            return "";
        }
        if("null".equalsIgnoreCase(obj.toString())){
            return "";
        }
        return obj.toString();
    }
}
