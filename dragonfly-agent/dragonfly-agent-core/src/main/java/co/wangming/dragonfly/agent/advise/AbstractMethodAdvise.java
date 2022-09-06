package co.wangming.dragonfly.agent.advise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author: wangming
 * @date: 2021/11/24
 */
public abstract class AbstractMethodAdvise implements MethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMethodAdvise.class);

    @Override
    public Object beforeExec(Class clazz, Method method, Object[] allArguments) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[方法增强] 执行前:{} - {}", clazz.getCanonicalName(), method.getName());
        }
        return null;
    }

    @Override
    public Object exceptionOnExec(Class clazz, Method method, Object[] allArguments, Throwable e) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[方法增强] 执行发生异常:{} - {}", clazz.getCanonicalName(), method.getName());
        }
        return null;
    }

    @Override
    public Object afterExec(Class clazz, Method method, Object[] allArguments) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[方法增强] 执行后:{} - {}", clazz.getCanonicalName(), method.getName());
        }
        return null;
    }
}
