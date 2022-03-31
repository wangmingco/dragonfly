package co.wangming.dragonfly.agent.interceptor;

import co.wangming.dragonfly.agent.adaptor.Adaptor;
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
public class InstanceInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstanceInterceptor.class);

    private Adaptor adaptor;

    public InstanceInterceptor(Adaptor adaptor) {
        this.adaptor = adaptor;
    }

    /**
     * 在ByteBuddy 的文档中，对于 {@link This} 是用于访问对象的字段。
     * 在本类中直接访问 {@link This} 对象的方法会直接产生栈溢出，因此将它移除掉
     *
     * @param clazz
     * @param method
     * @param allArguments
     * @param callable
     * @return
     * @throws Exception
     */
    @RuntimeType
    public Object intercept(@Origin Class clazz,
                            @Origin Method method,
                            @AllArguments Object[] allArguments,
                            @SuperCall Callable callable) throws Exception {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("进入Interceptor---> \n[\n    Adaptor: {}\n    目标类: {}\n    目标方法: {}\n]", adaptor.name(), clazz.getName(), method.getName());
        }

        final Object result = adaptor.dispatch(clazz, method, allArguments, callable);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("离开Interceptor---> \n[\n    Adaptor: {}\n    目标类: {}\n    目标方法: {}\n]", adaptor.name(), clazz.getName(), method.getName());
        }

        return result;
    }

}
