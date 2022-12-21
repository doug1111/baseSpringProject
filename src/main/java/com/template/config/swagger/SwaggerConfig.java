package com.template.config.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger文档配置
 *
 * @author Doug Liu
 * @since 2022-06-10
 */
@EnableOpenApi
@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket createUser() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.template.app.controller.user"))
                //.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(getGlobalRequestParameters()).groupName("需要token认证的接口");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档API")
                .description("部分简单返回的接口，返回字段没写备注，可以try it out看一下请求结果，或者问后端开发人员."
                        + "<br/>国际化请求方式：每次请求需要带参数?lang=zh_CN或?lang=en_US（例如：https://test.test.com/user/login?lang=zh_CN）"
                        + "<br/> 需要登录验证的接口，统一在header中传入token,token值在登录注册时候又返回")
                .contact(new Contact("Doug Liu", "", ""))
                .version("v1")
                .build();
    }

    //生成全局通用参数
    private List<RequestParameter> getGlobalRequestParameters() {
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameterBuilder()
                .name("token")
                .description("登录校验token")
                .required(true)
                .in(ParameterType.HEADER)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .build());
        return parameters;
    }
}