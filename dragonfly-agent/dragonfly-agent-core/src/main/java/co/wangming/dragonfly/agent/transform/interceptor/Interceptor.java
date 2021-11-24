package co.wangming.dragonfly.agent.transform.interceptor;

import co.wangming.dragonfly.agent.transform.adaptor.Adaptor;
import net.bytebuddy.implementation.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * 默认的方法拦截器，该实现不会修改做扩展性设计，扩展主要是通过 {@link Adaptor} 进行扩展.
 *
 * @author: wangming
 * @date: 2021/11/24
 */
public class Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(Interceptor.class);

    private Adaptor adaptor;

    public Interceptor(Adaptor adaptor) {
        this.adaptor = adaptor;
    }

    @RuntimeType
    public Object intercept(@Origin Class clazz,
                            @Origin Method method,
                            @This Object thisObj,
                            @AllArguments Object[] allArguments,
                            @SuperCall Callable callable) throws Exception {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("进入MethodAdviseInterceptor: [{}] 目标:{}#{}", adaptor.name(), clazz.getName(), method.getName());
        }

        final Object result = adaptor.intercept(clazz, method, thisObj, allArguments, callable);

        return result;
    }

}
