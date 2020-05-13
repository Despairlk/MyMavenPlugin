package com.fenger.mypluginprovide.utils;


import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {
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

    public static Map<String,Object> readYamlContent(String fileName,Class<?> clazz ) {
        Yaml yaml = new Yaml();
        ClassLoader classLoader = clazz.getClassLoader();
        InputStream resourceAsStream = classLoader.getResourceAsStream(fileName);
        Map<String, Object> ret = (Map<String, Object>) yaml.load(resourceAsStream);
        System.out.println(ret);
        return ret;
    }

}
