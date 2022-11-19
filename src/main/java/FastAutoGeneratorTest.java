import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import java.util.Collections;

/**
 * @Author: yu.zhou
 * @DateTime: 2022/11/19 14:51
 */
public class FastAutoGeneratorTest {

    public static void main(String[] args) {
        //1、配置数据源
        FastAutoGenerator
                .create("jdbc:mysql://94.74.110.135/community?characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai",
                        "root", "root")
                //2、全局配置
                .globalConfig(builder -> {
                    builder.author("yu.zhou") // 设置作者名
                            .outputDir(System.getProperty("user.dir") + "/src/main/java")   //设置输出路径：项目的 java 目录下
                            .commentDate("yyyy-MM-dd hh:mm:ss")   //注释日期
                            .dateType(DateType.TIME_PACK)   //定义生成的实体类中日期的类型 TIME_PACK=LocalDateTime;ONLY_DATE=Date;
                            .fileOverride()   //覆盖之前的文件
                            .enableSwagger()   //开启 swagger 模式
                            .disableOpenDir();   //禁止打开输出目录，默认打开
                })
                //3、包配置
                .packageConfig(builder -> {
                    builder.parent("com.youqu") // 设置父包名
                            .moduleName("interest")  //设置模块包名
                            .entity("domain")   //pojo 实体类包名
                            .service("service") //Service 包名
                            .serviceImpl("service.impl") // ***ServiceImpl 包名
                            .mapper("mapper")   //Mapper 包名
                            .xml("store.mapper")  //Mapper XML 包名
                            .controller("controller") //Controller 包名
                            .other("utils") //自定义文件包名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir")
                                    + "/src/main/resources/store/mapper"));    //配置 mapper.xml 路径信息：项目的 resources 目录下
                })
                //4、策略配置
                .strategyConfig(builder -> {
                    builder.addInclude("user") // 设置需要生成的数据表名
//                            .addTablePrefix("t_", "c_") // 设置过滤表前缀

                            //4.1、Mapper策略配置
                            .mapperBuilder()
                            .superClass(BaseMapper.class)   //设置父类
                            .formatMapperFileName("%sMapper")   //格式化 mapper 文件名称
//                            .enableMapperAnnotation()       //开启 @Mapper 注解
                            .formatXmlFileName("%sXml") //格式化 Xml 文件名称
                            .enableBaseResultMap() // 生成通用查询resultMap
                            .enableBaseColumnList() //通用查询列

                            //4.2、service 策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService") //格式化 service 接口文件名称，%s进行匹配表名，如 UserService
                            .formatServiceImplFileName("%sServiceImpl") //格式化 service 实现类文件名称，%s进行匹配表名，如 UserServiceImpl

                            //4.3、实体类策略配置
                            .entityBuilder()
                            .enableLombok() //开启 Lombok
//                            .disableSerialVersionUID()  //不实现 Serializable 接口，不生产 SerialVersionUID
//                            .logicDeleteColumnName("deleted")   //逻辑删除字段名
//                            .superClass(BaseEntity.class)  //设置父类
                            .naming(NamingStrategy.underline_to_camel)  //数据库表映射到实体的命名策略：下划线转驼峰命
                            .columnNaming(NamingStrategy.underline_to_camel)    //数据库表字段映射到实体的命名策略：下划线转驼峰命
                            .addTableFills(
                                    new Column("create_time", FieldFill.INSERT),
                                    new Column("update_time", FieldFill.INSERT_UPDATE)
                            )   //添加表字段填充，"create_time"字段自动填充为插入时间，"update_time"字段自动填充为插入修改时间
                            .enableTableFieldAnnotation()       // 开启生成实体时生成字段注解

                            //4.4、Controller策略配置
                            .controllerBuilder()
                            .formatFileName("%sController") //格式化 Controller 类文件名称，%s进行匹配表名，如 UserController
                            .enableRestStyle();  //开启生成 @RestController 控制器
                })
                //5、模板
                .templateEngine(new VelocityTemplateEngine())
                /*
                        .templateEngine(new FreemarkerTemplateEngine())
                        .templateEngine(new BeetlTemplateEngine())
                        */
                //6、执行
                .execute();
    }
}
