package com.fenger.mypluginprovide;

import com.fenger.mypluginprovide.utils.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.List;

@Mojo(name = "fengerMavenPlugin",defaultPhase = LifecyclePhase.INSTALL)
public class MyMojo extends AbstractMojo {
    @Parameter
    private String name;
    @Parameter
    private List<String> list;
    @Parameter
    private String path;

    /**
     * 单例
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println(name);
        System.out.println(list);
        initYamlToController(path);
    }

    private void initYamlToController(String path) {
        String out = FileUtils.readFileContent(path);
        System.out.println(out);
    }
    public static void main(String[] args){
        String path = "test.yaml";

    }
}
