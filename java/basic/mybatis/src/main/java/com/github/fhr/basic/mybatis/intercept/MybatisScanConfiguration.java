package com.github.fhr.basic.mybatis.intercept;

import com.github.fhr.basic.mybatis.intercept.core.LogInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Fan Huaran
 * created on 2019/2/20
 * @description
 */
@Configuration
@MapperScan("com.github.fhr.basic.mybatis.intercept.dao")
public class MybatisScanConfiguration {
    @Bean
    public Interceptor getInterceptor(){
        return new LogInterceptor();
    }
}

