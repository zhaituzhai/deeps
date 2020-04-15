package org.mybatis.internal.example.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * @author zhaojm
 * @date 2020-04-15 17:37
 */

/**
 * Mybatis规定插件必须编写Annotation注解，是必须，而不是可选。
 * -@Intercepts注解：装载一个@Signature列表，一个@Signature其实就是一个需要拦截的方法封装。
 * 那么，一个拦截器要拦截多个方法，自然就是一个@Signature列表。
 * type = Executor.class, method = "query", args =
 * { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }
 * 解释：要拦截Executor接口内的query()方法，参数类型为args列表。
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "close", args = { boolean.class })
})
public class MyBatisInterceptor implements Interceptor {

    private Integer value;

    /**
     * 执行拦截内容的地方，比如想收点保护费。由plugin()方法触发，interceptor.plugin(target)足以证明。
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("hello, I'm The Interceptor");
        return invocation.proceed();
    }

    /**
     * 决定是否触发intercept()方法。
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        System.out.println(value);
        // Plugin类是插件的核心类，用于给target创建一个JDK的动态代理对象，触发intercept()方法
        // 使用JDK的动态代理，给target对象创建一个delegate代理对象，以此来实现方法拦截和增强功能，它会回调intercept()方法。
        return Plugin.wrap(target, this);
    }

    /**
     * 给自定义的拦截器传递xml配置的属性参数。
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        value = Integer.valueOf((String) properties.get("value"));
    }
}
