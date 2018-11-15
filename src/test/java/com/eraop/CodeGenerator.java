package com.eraop;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class CodeGenerator {
    /**
     * 标记是否结束程序
     */
    private static boolean flag = true;


    public static void main(String[] args) {
        generatorCode();
    }

    private static void generatorCode() {
        while (flag) {
            String moduleName = scanner("模块名称 ------- 输入exit结束");
            if ("exit".equals(moduleName.toLowerCase())) {
                flag = false;
            } else {
                generatorModule(moduleName);

            }
        }
    }

    private static void generatorModule(String moduleName) {

        //循环生成表
        while (flag) {
            String tableName = scanner("表名，多个表之间用空格分割 ------- 输入exit结束,输入b返回模块");
            if ("exit".equals(tableName.toLowerCase())) {
                flag = false;
            } else if ("b".equals(tableName.toLowerCase())) {
                generatorCode();
            } else {
                String[] tableNames = tableName.split(" ");
                generatorTables(moduleName, tableNames);
            }
        }


    }

    private static void generatorTables(String moduleName, String... tableName) {
        //获取基础配置
        ResourceBundle rb = ResourceBundle.getBundle("mybatis-plus");
        String dbUrl = rb.getString("datasource.url");
        String dbUsername = rb.getString("datasource.username");
        String dbPassword = rb.getString("datasource.password");
        String dbDriver = rb.getString("datasource.driver-class-name");
        String author = rb.getString("author");
        String parent = rb.getString("parent");
        String superEntityClass = rb.getString("super-entity-class");
        boolean restController = Boolean.parseBoolean(rb.getString("rest-controller"));
        boolean lombok = Boolean.parseBoolean(rb.getString("lombok"));
        String superControllerClass = rb.getString("super-controller-class");
        String projectPath = rb.getString("output-dir");

        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/java");
        gc.setAuthor(author);
        gc.setOpen(true);
        gc.setSwagger2(false);
        gc.setMapperName("%sDao");
        gc.setServiceName("%sService"); //"I%sService"
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(dbUrl);
        // dsc.setSchemaName("public");
        dsc.setDriverName(dbDriver);
        dsc.setUsername(dbUsername);
        dsc.setPassword(dbPassword);
        mpg.setDataSource(dsc);

        // 包名策略配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent(parent)
                .setMapper("dao")
                .setEntity("model");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义Mapper文件生成路径
                return projectPath + "/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        mpg.setTemplate(tc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        if (StringUtils.isNotEmpty(superEntityClass)) {
            strategy.setSuperEntityClass(superEntityClass);
            strategy.setEntityLombokModel(true);
        }
        strategy.setRestControllerStyle(restController);
        if (StringUtils.isNotEmpty(superControllerClass)) {
            strategy.setSuperControllerClass(superControllerClass);
        }
        strategy.setInclude(tableName);
        // strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        strategy.setEntityLombokModel(lombok);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}
