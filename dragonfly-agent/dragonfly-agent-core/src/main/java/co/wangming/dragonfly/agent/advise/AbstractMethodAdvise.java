package co.wangming.dragonfly.agent.advise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public abstract class AbstractMethodAdvise implements MethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMethodAdvise.class);

    public Object intercept(Class clazz, Method method, Object thisObj, Object[] allArguments, Callable callable) throws Exception {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[方法增强] 开始: {}#{} ", clazz.getName(), method.getName());
        }

        try {
            beforeExec(clazz, method, thisObj, allArguments);
        } catch (Exception e) {
            LOGGER.error("[方法增强] beforeExec {}#{} 发生异常", clazz.getName(), method.getName(), e);
        }

        try {
            return callable.call();
        } catch (Throwable e) {
            try {
                exceptionOnExec(clazz, method, thisObj, allArguments, e);
            } catch (Exception e1) {
                LOGGER.error("[方法增强] {}#{} exceptionOnExec 发生异常", clazz.getName(), method.getName(), e);
            }
            throw e;
        } finally {
            try {
                afterExec(clazz, method, thisObj, allArguments);
            } catch (Exception e1) {
                LOGGER.error("[方法增强] afterExec {}#{} 发生异常", clazz.getName(), method.getName(), e1);
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("[方法增强] 完成: {}#{}", clazz.getName(), method.getName());
            }
        }

    }

}
