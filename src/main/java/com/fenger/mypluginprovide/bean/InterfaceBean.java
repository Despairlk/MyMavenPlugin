package com.fenger.mypluginprovide.bean;

import com.fenger.mypluginprovide.commonenum.RequestMethod;
import lombok.Data;

import java.util.List;

/**
 * 要生成的接口的构成
 */
@Data
public class InterfaceBean {
    /*请求路径*/
    private String url;
    /*请求类型*/
    private RequestMethod requestType;
    /*参数列表*/
    private List<ParamerBean> paramerBeans;
    /*返回值类型*/
    private Class responseClass;
    /*所属controller*/
    private String tag;
    /*生成方法名*/
    private String methodName;
}
