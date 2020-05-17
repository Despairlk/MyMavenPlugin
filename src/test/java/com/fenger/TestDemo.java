package com.fenger;

import com.fenger.mypluginprovide.module.ReadYamlFileModule;
import com.fenger.mypluginprovide.bean.InterfaceBean;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestDemo {

    @Test
    public void test(){

        try {
            Class<?> aClass = Class.forName("java.lang.String");
            String typeName = aClass.getTypeName();
            System.out.println(typeName);
            System.out.println(aClass.getName());
            System.out.println(aClass.getSimpleName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test2() {
//        String fileName = this.getClass().getClassLoader().getResource("test.yaml").getPath();
//        System.out.println(fileName);
//        String[] split = fileName.split("/target/");
//        String basePath = "";
//        if(split.length>0){
//            basePath = split[0];
//        }
//        String fileFullPath = basePath+"/src/main/java/com/fenger/mypluginprovide/controller/Ctx.java";
        String fileFullPath = "D:/server/apache-maven-3.6.0/Repositories/com/fenger/MyMavenPlugin/1.1/MyMavenPlugin-1.1.jar!/test.yaml/src/main/java/com/fenger/mypluginprovide/controller/userController.java";
//        File file = new File(fileFullPath);
//        try {
//            boolean newFile = file.createNewFile();
//            if(newFile){
//                System.out.println("创建文件成功");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }
}
