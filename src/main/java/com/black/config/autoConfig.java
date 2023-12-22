package com.black.config;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;

public class autoConfig {
    /**
     * 代码生成入口
     * 注意： 请先修改resources目录下的generator.properties文件中的配置
     */
    public static void main(String[] args) {
        doGenerator();
    }

    private static final Properties properties = new Properties();

    static {
        // 读取resources目录下的配置文件
        InputStream inputStream = autoConfig.class.getClassLoader().getResourceAsStream("generator.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 代码生成实现
     * 提示：如果不需要生成controller相关代码，设置packageConfig中的controller为""，设置templateConfig中的controller为null
     */
    private static void doGenerator() {
        System.out.println("==========================欢迎使用BlackJun自定义代码生成器==========================");
        // 建立数据库连接
        String url = properties.getProperty("database.url");
        String username = properties.getProperty("database.username");
        String password = properties.getProperty("database.password");
        DataSourceConfig dsc = new DataSourceConfig.Builder(url, username, password).build();

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator(dsc);
        // 模块名称
        String moduleName = properties.getProperty("moduleName");
        // 项目目录
        String projectPath;
        if (properties.getProperty("projectPath").equals("")){
            projectPath=System.getProperty("user.dir");
        }else{
            projectPath=properties.getProperty("projectPath");
        }
        // 作者名称
        String author = properties.getProperty("author");
        // 基础包路径
        String packagePath = properties.getProperty("packagePath");
        // 需要生成的表
        String tables = properties.getProperty("tables");

        String outputDir=projectPath + "/" + moduleName + "/src/main/java";

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig
                .Builder()
                .outputDir(outputDir)
                .author(author)
                .commentDate("yyyy-MM-dd HH:mm:ss")
                .disableOpenDir()
                .build();

        // 包配置
        PackageConfig packageConfig = new PackageConfig
                .Builder()
                .pathInfo(Collections.singletonMap(OutputFile.xml, outputDir+"/"+packagePath.replace(".","/")+"/mapper"))
                .parent(packagePath)
                .entity("entity")
                .mapper("mapper")
                .service("service")
                .serviceImpl("service.impl")
                .controller("controller")
                .build();

        // 配置模板
        String absolutePath = File.separator + "templates";
        String controllerTempPath = absolutePath + File.separator + "controller.java";
        String mapperTempPath = absolutePath + File.separator + "mapper.java";
        String mapperXmlTempPath = absolutePath + File.separator + "mapper.xml";
        String entityTempPath = absolutePath + File.separator + "entity.java";
        String serviceTempPath = absolutePath + File.separator + "service.java";
        String serviceImplTempPath = absolutePath + File.separator + "serviceImpl.java";
        TemplateConfig templateConfig = new TemplateConfig
                .Builder()
                .mapper(mapperTempPath)
                .service(serviceTempPath)
                .serviceImpl(serviceImplTempPath)
                .entity(entityTempPath)
                .xml(mapperXmlTempPath)
                .controller(controllerTempPath)
                .build();

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig
                .Builder()
                .addTablePrefix("tk_")//去除表头
                .addInclude(tables.split(","))
                .entityBuilder()
                .naming(NamingStrategy.underline_to_camel)
                .enableLombok()//开启lombok
                .superClass("BaseEntity")//基础类
                .controllerBuilder()
                .enableRestStyle()
                .build();

        mpg.global(globalConfig);
        mpg.packageInfo(packageConfig);
        mpg.template(templateConfig);
        mpg.strategy(strategyConfig);
        // 开始生成代码文件
        mpg.execute(new FreemarkerTemplateEngine());
        getPath();
    }


    /**
     * 获取当前项目本地磁盘目录
     */
    private static void getPath() {
        System.out.println("项目生成目录与->" + System.getProperty("user.dir"));
        System.out.println("==========================BlackJun自定义代码生成器生成结束==========================");
    }

}
