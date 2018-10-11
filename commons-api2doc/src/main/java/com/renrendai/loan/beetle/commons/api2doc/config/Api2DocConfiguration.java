package com.renrendai.loan.beetle.commons.api2doc.config;

import com.renrendai.loan.beetle.commons.api2doc.meta.ApiMetaService;
import com.renrendai.loan.beetle.commons.api2doc.codewriter.RetrofitCodeWriter;
import com.renrendai.loan.beetle.commons.api2doc.controller.Api2DocController;
import com.renrendai.loan.beetle.commons.api2doc.impl.Api2DocCollector;
import com.renrendai.loan.beetle.commons.api2doc.meta.ApiMetaService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 可以通过配置 beetle.api2doc.enabled 来
 * 启用或禁用文档服务。
 */
@ConditionalOnExpression("${api2doc.enabled:true}")
@ComponentScan(basePackageClasses = {
        Api2DocController.class,
        Api2DocCollector.class,
        RetrofitCodeWriter.class,
        ApiMetaService.class
})
@Configuration
public class Api2DocConfiguration {

}