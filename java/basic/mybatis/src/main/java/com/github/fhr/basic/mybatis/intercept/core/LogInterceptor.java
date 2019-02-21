package com.github.fhr.basic.mybatis.intercept.core;


import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author Fan Huaran
 * created on 2019/2/20
 * @description 将拦截器作用在Executor.update方法上，所有写（增删改）都通过此方法进行
 * 参数是：MappedStatement + Object
 */
@Intercepts({@Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class})})
public class LogInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // Executor executor = (Executor)invocation.getTarget();
        // Method method = invocation.getMethod();

        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object param = args[1];

        String fullMethodSignature = mappedStatement.getId();

        String[] items = fullMethodSignature.split("\\.");
        String action = items[items.length - 2] + "." + items[items.length - 1];

        String sql = mappedStatement.getSqlSource()
                .getBoundSql(param)
                .getSql();

        // StatementType statementType = mappedStatement.getStatementType();
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

        logger.info("action:{},param:{},sql:{},commandType:{}", action, param, sql, sqlCommandType);

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
