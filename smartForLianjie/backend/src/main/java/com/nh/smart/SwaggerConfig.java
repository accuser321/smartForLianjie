package com.nh.smart;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 配置类
 * 在与spring boot 集成时，放在与application.java 同级的目录下
 * 通过@Configuration注解，让spring来加载该配置
 * 通过@EnableSwagger2注解来启动Swagger2
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // 定义分隔符
    private static final String splitor = ";";

    // 配置swagger可以扫面多个路径
    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }


    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(splitor)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }


    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }


    /**
     * 创建改API的基本信息（这些基本信息会展示在文档页面中）
     * 访问地址： http://项目实际地址/swagger-ui.html
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("积分商城测试API")
                .description("如需修改请联系开发人员")
                .contact("wmb")
                .version("1.0")
                .build();
    }


    /**
     * 创建API应用
     * appinfo()增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例，用来控制那些接口暴露给Swagger来展现
     * 本例采用置顶扫描的包路径来定义指定要建立API的目录
     */
    @Bean
    public Docket init() {
      return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .groupName("公共模块")
        .select()
        .apis(basePackage("com.nh.smart.controller.security.base"))
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any()).build();
    }

    @Bean
  public Docket material() {
    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .groupName("业务员客户信息与素材模块")
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.nh.smart.controller.material"))
      .apis(RequestHandlerSelectors.any())
      .paths(PathSelectors.any()).build();
  }

  @Bean
  public Docket record() {
    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .groupName("渠道业务员行为记录")
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.nh.smart.controller.record"))
      .apis(RequestHandlerSelectors.any())
      .paths(PathSelectors.any()).build();
  }


}
