package com.fenger.mypluginprovide.utils;


import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {
    public static void main(String[] args){
        String path = "D:\\workspace\\MavenPluginCusumeDemo\\src\\main\\java\\com\\fenger";
        File file = new File(path);
        if(!file.exists()) {
            boolean mkdirs = file.mkdirs();
        }
    }
    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader;
        reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    public static Map<String,Object> readYamlContent(String fileName) {
        Yaml yaml = new Yaml();
        File file = new File(fileName);
        Map<String, Object> ret = null;
        try {
            ret = (Map<String, Object>) yaml.load(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(ret);
        return ret;
    }

}
