package com.fenger.mypluginprovide;

import com.fenger.mypluginprovide.bean.ControllerBean;
import com.fenger.mypluginprovide.module.JavaFileModule;
import com.fenger.mypluginprovide.module.ReadYamlFileModule;
import com.fenger.mypluginprovide.bean.InterfaceBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mojo(name = "fengerMavenPlugin",defaultPhase = LifecyclePhase.INSTALL)
public class MyMojo extends AbstractMojo {
    @Parameter
    private String name;
    @Parameter
    private List<String> list;
    @Parameter
    private String path;
    @Parameter
    private String packageName;
    /**
     * 单例
     */
    public void execute()  {
        System.out.println(name);
        System.out.println(list);
        initYamlToController(path);
    }
    private void initYamlToController(String path) {
        System.out.println("initYamlToController()");
        new JavaFileModule().initYamlToControllerFile(path,packageName);

    }
}
