package com.fenger.mypluginprovide.module;

import com.fenger.mypluginprovide.bean.ControllerBean;
import com.fenger.mypluginprovide.bean.InterfaceBean;
import com.fenger.mypluginprovide.bean.ParamerBean;
import com.fenger.mypluginprovide.commonenum.RequestMethod;
import com.fenger.mypluginprovide.commonenum.RequestParam;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;

public class JavaFileModule {

    public static void main (String[] args){
        new JavaFileModule().initYamlToControllerFile("D:\\workspace\\addresswebapp\\src\\main\\resources\\test.yaml","com.fenger.addresswebapp");
    }

    public void initYamlToControllerFile(String path,String packageName) {
        ReadYamlFileModule readYamlFileModule = new ReadYamlFileModule();

        List<ControllerBean> interfaceBeanList = readYamlFileModule.initInterFaceBeans(path);
        for (ControllerBean e :interfaceBeanList) {
            String tag = e.getControllerName();
            System.out.println(String.format("controllerName:%s", tag));
            System.out.println(String.format("path:%s", path));
            String outDir = transOutDir(path, packageName);
            System.out.println(String.format("outDir:%s", outDir));
            File file = initFile(outDir, tag,"controller");
            if(null != file) {
                writeFileCode(file,e,packageName);
            }
        }
    }

    private void writeFileCode(File file, ControllerBean e,String packageName) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(String.format("package %s.controller;\n",packageName));
            bufferedWriter.newLine();
            bufferedWriter.newLine();

            bufferedWriter.write("import org.springframework.web.bind.annotation.RestController;\n");
            bufferedWriter.write("import org.springframework.beans.factory.annotation.Autowired;\n" );
            bufferedWriter.write("import org.springframework.web.bind.annotation.*;\n");
            bufferedWriter.newLine();
            bufferedWriter.write("@RestController\n");
            bufferedWriter.write(String.format("public class %sController {\n",StringUtils.capitalize(e.getControllerName())));
            List<InterfaceBean> interfaceBeans = e.getInterfaceBeans();
            for (InterfaceBean interfaceBean : interfaceBeans) {
                String methodName = interfaceBean.getMethodName();
                List<ParamerBean> paramerBeans = interfaceBean.getParamerBeans();
                RequestMethod requestType = interfaceBean.getRequestType();
                String url = interfaceBean.getUrl();
                Class responseClass = interfaceBean.getResponseClass();
                String requestAnno = getRequestAnnoType(requestType);
                String format = String.format(requestAnno, url);
                bufferedWriter.write("    "+format+"\n");
                bufferedWriter.write(getMethodHeader(methodName, responseClass,paramerBeans));
                bufferedWriter.write(String.format("    return new %s();\n",interfaceBean.getResponseClass().getName()));
                bufferedWriter.write(String.format("    }\n"));
            }
            bufferedWriter.write(String.format("}"));
            bufferedWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private String getMethodHeader(String methodName, Class responseClass, List<ParamerBean> paramerBeans) {
        String heade = "    public " + responseClass.getName() + " "+methodName + "(%s) {" + "\n";
        StringBuffer paramListStr = new StringBuffer();
        boolean isFrist = true;
        for (ParamerBean paramerBean : paramerBeans) {
            RequestParam requestParamType = paramerBean.getRequestParamType();
            switch (requestParamType) {
                case BODY:
                    if (isFrist) {
                        paramListStr.append(String.format("@RequestBody %s %s", paramerBean.getType(), paramerBean.getParamName()));
                    }else {
                        paramListStr.append(",\n          ").append(String.format("@RequestBody %s %s", paramerBean.getType(), paramerBean.getParamName()));
                    }
                    break;
                case QUERY:
                    if (isFrist) {
                        paramListStr.append(getQueryListStr(paramerBean));
                    }else {
                       paramListStr.append(",\n          ").append(getQueryListStr(paramerBean));
                    }
                    break;
                case FILE:
                    if (isFrist) {
                        paramListStr.append(getQueryListStr(paramerBean));
                    }else {
                        paramListStr.append(",\n          ").append(getQueryListStr(paramerBean));
                    }
                    break;
                default:
            }
            isFrist = false;
        }
        heade = String.format(heade,paramListStr.toString());
        return heade;
    }

    private String getQueryListStr(ParamerBean paramerBean) {
        if(null != paramerBean.getDefaultvalue() && StringUtils.isNotBlank(paramerBean.getDefaultvalue().toString())){
            return String.format("@RequestParam(name = \"%s\",required = %s,defaultValue = \"%s\") %s %s",paramerBean.getParamName(),paramerBean.isRequired(),paramerBean.getDefaultvalue(),paramerBean.getType(),paramerBean.getParamName());
        }else {
            return String.format("@RequestParam(name = \"%s\",required = %s) %s %s",paramerBean.getParamName(),paramerBean.isRequired(),paramerBean.getType(),paramerBean.getParamName());
        }
    }

    private String getRequestAnnoType(RequestMethod requestType) {

        switch (requestType){
            case GET:
                return "@RequestMapping(value = \"%s\", method = RequestMethod.GET)";
            case HEAD:
                return "@RequestMapping(value = \"%s\", method = RequestMethod.HEAD)";
            case POST:
                return "@RequestMapping(value = \"%s\", method = RequestMethod.POST)";
            case PUT:
                return "@RequestMapping(value = \"%s\", method = RequestMethod.PUT)";
            case PATCH:
                return "@RequestMapping(value = \"%s\", method = RequestMethod.PATCH)";
            case DELETE:
                return "@RequestMapping(value = \"%s\", method = RequestMethod.DELETE)";
            case OPTIONS:
                return "@RequestMapping(value = \"%s\", method = RequestMethod.OPTIONS)";
            case TRACE:
                return "@RequestMapping(value = \"%s\", method = RequestMethod.TRACE)";
            default:
                return "@RequestMapping(value = \"%s\", method = RequestMethod.GET)";
        }
    }

    private String transOutDir(String path,String packageName) {
        String[] split = path.split("\\\\main\\\\");
        if(split.length>0){
            return split[0]+"\\main\\java\\"+packageName.replaceAll("\\.", Matcher.quoteReplacement(File.separator))+"\\";
        }
        return "";
    }



    private File initFile(String path, String tag,String modelName) {
        String fileName = StringUtils.capitalize(tag) + "Controller.java";
        System.out.println(fileName);
        String fileFullPath = path +modelName+"/"+ fileName;
        System.out.println(String.format("fileFullPath:%s", fileFullPath));
        File file = new File(fileFullPath);
        try {
            File parentFile = file.getParentFile();
            if (null != parentFile && !parentFile.exists()) {
                boolean mkdir = parentFile.mkdirs();
                if (mkdir) {
                    System.out.println("创建目录成功");
                }
            }
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                if (newFile) {
                    System.out.println("创建文件成功");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }
}
