package co.wangming.dragonfly.agent.transform.interceptor;

import co.wangming.dragonfly.agent.advise.AbstractMethodAdvise;
import net.bytebuddy.implementation.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class MethodAdviseInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodAdviseInterceptor.class);

    private AbstractMethodAdvise advise;

    public MethodAdviseInterceptor(AbstractMethodAdvise advise) {
        this.advise = advise;
    }

    @RuntimeType
    public Object intercept(@Origin Class clazz,
                            @Origin Method method,
                            @This Object thisObj,
                            @AllArguments Object[] allArguments,
                            @SuperCall Callable callable) throws Exception {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("进入Interceptor: [{}] 目标:{}#{}", advise.name(), clazz.getName(), method.getName());
        }

        Object result = advise.intercept(clazz, method, thisObj, allArguments, callable);

        return result;
    }

}
