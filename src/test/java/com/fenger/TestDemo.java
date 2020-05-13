package com.fenger;

import com.fenger.mypluginprovide.utils.FileUtils;
import org.junit.Test;

import java.util.Map;

public class TestDemo {

    @Test
    public void test(){
        String path = "test.yaml";
        Map<String, Object> map = FileUtils.readYamlContent(path, this.getClass());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
        }
        System.out.println(map);
    }
}
