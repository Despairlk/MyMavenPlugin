package com.fenger.mypluginprovide.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ControllerBean {
    private String controllerName;
    private List<InterfaceBean> interfaceBeans = new ArrayList<>();
}
