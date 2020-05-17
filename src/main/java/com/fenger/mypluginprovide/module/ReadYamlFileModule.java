package com.fenger.mypluginprovide.module;

import com.fenger.mypluginprovide.bean.ControllerBean;
import com.fenger.mypluginprovide.bean.InterfaceBean;
import com.fenger.mypluginprovide.bean.ParamerBean;
import com.fenger.mypluginprovide.commonenum.RequestMethod;
import com.fenger.mypluginprovide.commonenum.RequestParam;
import com.fenger.mypluginprovide.utils.FileUtils;
import com.fenger.mypluginprovide.utils.ParamUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReadYamlFileModule {
    public static final Logger log = LoggerFactory.getLogger(ReadYamlFileModule.class);

    public List<ControllerBean> initInterFaceBeans(String path){
        System.out.println(String.format("initInterFaceBeans->param:%s",path));
//        log.info(String.format("initInterFaceBeans->param:%s",path));
        Map<String, Object> map = FileUtils.readYamlContent(path);
        List<InterfaceBean> interfaceBeans = getInterfaceBeans(map);
        List<ControllerBean> controllerBeans = new ArrayList<>();
        Map<String, List<InterfaceBean>> collect =
                interfaceBeans.stream().collect(Collectors.groupingBy(InterfaceBean::getTag));
        for (Map.Entry<String, List<InterfaceBean>> entry : collect.entrySet()) {
            String tag = entry.getKey();
            List<InterfaceBean> value = entry.getValue();
            ControllerBean controllerBean = new ControllerBean();
            controllerBean.setControllerName(tag);
            controllerBean.getInterfaceBeans().addAll(value);
            controllerBeans.add(controllerBean);
        }
        return controllerBeans;
    }
    public static void main(String[] args){
        ReadYamlFileModule re = new ReadYamlFileModule();
        re.initInterFaceBeans("D:\\workspace\\MavenPluginCusumeDemo\\src\\main\\resources\\test.yaml");
    }
    public List<InterfaceBean> getInterfaceBeans(Map<String, Object> map) {
        List<InterfaceBean> interfaceBeanList = new ArrayList<InterfaceBean>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(StringUtils.equals(key,"path")){
                Map<String,Object> it = (Map<String, Object>) value;
                for (Map.Entry<String, Object> objectEntry : it.entrySet()) {
                    String url  = objectEntry.getKey();
                    Map<String,Object> obj = (Map<String, Object>) objectEntry.getValue();
                    for (Map.Entry<String, Object> requestTypeEntity : obj.entrySet()) {
                        transInterFaceBean(interfaceBeanList, url, requestTypeEntity);
                    }

                }
            }
        }
        return interfaceBeanList;
    }

    private void transInterFaceBean(List<InterfaceBean> interfaceBeanList, String url, Map.Entry<String, Object> requestTypeEntity) {
        InterfaceBean item = new InterfaceBean();
        String requestType = requestTypeEntity.getKey();
        Map<String,Object> obj = (Map<String, Object>) requestTypeEntity.getValue();
        item.setRequestType(RequestMethod.getInstance(requestType));
        item.setUrl(url);
        item.setMethodName(obj.get("methodName").toString());
        item.setTag(obj.get("tag").toString());
        item.setParamerBeans(transParamerBeans(obj));
        item.setResponseClass(getResponseClass(obj));
        interfaceBeanList.add(item);
    }

    private Class getResponseClass(Map<String, Object> obj) {
        Map<String,Object> map = (Map<String, Object>) obj.get("response");
        Object response = map.get("responseType");
        Class clazz = Object.class;
        try {
            clazz =  Class.forName(response.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    private List<ParamerBean> transParamerBeans(Map<String, Object> obj) {
        Object parameters = obj.get("parameters");
        List<Map<String,Object>> list = (List<Map<String,Object>>)parameters;
        List<ParamerBean> paramerBeans = new ArrayList<>();
        for (Map<String, Object> entity : list) {
            Object parameter = entity.get("parameter");
            Map<String, Object> item = (Map<String, Object>) parameter;
            Object paramerName = item.get("paramerName");
            Object in = item.get("in");
            Object paramerType = item.get("paramerType");
            Object required = item.get("required");
            Object defaultStr = item.get("default");
            ParamerBean paramerBean = new ParamerBean();
            paramerBean.setRequestParamType(RequestParam.getInstance(ParamUtils.transToString(in)));
            paramerBean.setRequired(StringUtils.isNotBlank(ParamUtils.transToString(required)));
            paramerBean.setTClass(getClassForName(paramerType));
            paramerBean.setType(ParamUtils.transToString(paramerType));
            paramerBean.setDefaultvalue(defaultStr);
            paramerBean.setParamName(ParamUtils.transToString(paramerName));
            paramerBeans.add(paramerBean);
        }
        return paramerBeans;
    }

    private Class<?> getClassForName(Object paremerType) {
        Class<?> string = Object.class;
        try {
            string = Class.forName(paremerType.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            return string;
        }
    }
}
