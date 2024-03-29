package com.template;


import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.io.IOException;
import java.sql.Types;
import java.util.*;


/**
 * MySQL代码生成器，生成entity，mapper，service，controller
 *
 * @author Doug Liu
 * @since 2022-06-10
 */
public class CodeGenerator {

    /**
     * 读取控制台内容，获取数据库表名
     * @param tip 提示信息
     * @return String[]
     */
    public static String[] getTables(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt.split(",");
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) throws IOException {
        /*
         *  特别注意：生成的时间类型均为：“LocalDateTime”格式，需要假如以下注解方可正常使用
         *  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
         *  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
         *  @JsonSerialize(using = LocalDateTimeSerializer.class)
         */
        //数据库连接
        String url = "jdbc:mysql://localhost:3306/template_db?cacheServerConfiguration=true&useLocalSessionState=true&autoReconnect=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";//数据库url
        String username = "root";//账号
        String password = "Dmbz1234.";//密码
        //全局配置参数
        String author = "Doug Liu";//作者
        /*
         * 这里必须做修改改成自己项目的地址的位置
         */
        File file = new File("");
        String filePath = file.getCanonicalPath();
//        String filePath = System.getProperty("code_path");
        String outputDir = filePath + "/src/main/java";//指定Controller,Service,Mapper输出目录
        String xmlOutputDir = filePath + "/src/main/resources/mapper";//指定xml输出目录
        //包配置参数
        String parent = "com.template.app";//父包名
//        String moduleName = "app";//父包模块名
        String entity = "entity";//Entity 实体类包名
        String mapper = "mapper";//Mapper 包名
        String mapperXml = "mapper";//Mapper XML 包名
        String service = "service";//Service 包名
        String serviceImpl = "service.impl";//Service Impl 包名
        String controller = "controller";//Controller 包名
        //要生成的数据库表
        String[] tables = getTables("表名，多个英文逗号分割");

        DataSourceConfig.Builder dataSourceConfig = new DataSourceConfig.Builder(url, username, password);
        dataSourceConfig.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
            if (typeCode == Types.TINYINT) {
                if (metaInfo.getLength() > 1) {
                    return DbColumnType.INTEGER;
                }
                return DbColumnType.BOOLEAN;
            }
            return typeRegistry.getColumnType(metaInfo);
        });
        //开始生成
        FastAutoGenerator.create(dataSourceConfig)
                //全局配置
                .globalConfig(builder -> {
                    builder.author(author)
                            .outputDir(outputDir)
                            .enableSwagger()//开启swagger
                            .disableOpenDir()//设置生成完毕后不打开生成代码所在的目录,注释掉则变为打开
                            .dateType(DateType.ONLY_DATE)
                            .commentDate("yyyy-MM-dd");//注释日期
                })
                //包配置
                .packageConfig(builder -> {
                    builder.parent(parent)
//                            .moduleName(moduleName)//父包模块名 可以注释掉
                            .entity(entity)
                            .mapper(mapper)
                            .xml(mapperXml)
                            .service(service)
                            .serviceImpl(serviceImpl)
                            .controller(controller)
                            .pathInfo(Collections.singletonMap(OutputFile.xml, xmlOutputDir));//设置Mapper.xml的生成位置
                })
                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
                            //开启生成实体类
                            .entityBuilder()
                            .enableLombok()//开启 lombok 模型
//                            .enableTableFieldAnnotation()//开启生成实体时生成字段注解
                            .superClass("com.template.app.entity.base.BaseEntity")
                            .addSuperEntityColumns("id", "create_time", "update_time", "delete_flag")//写于父类中的公共字段
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .disableSerialVersionUID()
                            .enableChainModel()
                            .enableFileOverride()
                            //开启生成mapper
                            .mapperBuilder()
                            .enableBaseResultMap()//启用 BaseResultMap 生成
                            .enableBaseColumnList()
                            .enableFileOverride()
                            .superClass(BaseMapper.class)//设置父类
                            .formatMapperFileName("%sMapper")//格式化 mapper 文件名称
                            .formatXmlFileName("%sMapper")//格式化 xml 实现类文件名称
                            //开启生成service及impl
                            .serviceBuilder()
                            .formatServiceFileName("I%sService")//格式化 service 接口文件名称
                            .formatServiceImplFileName("%sServiceImpl")//格式化 service 实现类文件名称
                            .enableFileOverride()
                            //开启生成controller
                            .controllerBuilder()
                            // 映射路径使用连字符格式，而不是驼峰
//                            .enableHyphenStyle()
                            .enableFileOverride()
                            .formatFileName("%sController")//格式化文件名称
                            .enableRestStyle();
                })
//                .templateEngine(new VelocityTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
//                .templateConfig(builder -> builder.controller(""))//关闭生成controller
                .execute();
    }

}