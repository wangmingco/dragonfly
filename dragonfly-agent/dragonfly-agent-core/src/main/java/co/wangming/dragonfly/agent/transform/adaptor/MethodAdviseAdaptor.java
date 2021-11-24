package co.wangming.dragonfly.agent.transform.adaptor;

import co.wangming.dragonfly.agent.advise.MethodAdvise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * {@link Adaptor} 的 method advise 实现
 *
 * @author: wangming
 * @date: 2021/11/24
 */
public class MethodAdviseAdaptor implements Adaptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodAdviseAdaptor.class);

    private MethodAdvise advise;

    public MethodAdviseAdaptor(MethodAdvise advise) {
        this.advise = advise;
    }

    @Override
    public final Object intercept(Class clazz, Method method, Object thisObj, Object[] allArguments, Callable callable) throws Exception {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[方法增强] 开始: {}#{} ", clazz.getName(), method.getName());
        }

        try {
            this.advise.beforeExec(clazz, method, thisObj, allArguments);
        } catch (Exception e) {
            LOGGER.error("[方法增强] beforeExec {}#{} 发生异常", clazz.getName(), method.getName(), e);
        }

        try {
            return callable.call();
        } catch (Throwable e) {
            try {
                this.advise.exceptionOnExec(clazz, method, thisObj, allArguments, e);
            } catch (Exception e1) {
                LOGGER.error("[方法增强] {}#{} exceptionOnExec 发生异常", clazz.getName(), method.getName(), e);
            }
            throw e;
        } finally {
            try {
                this.advise.afterExec(clazz, method, thisObj, allArguments);
            } catch (Exception e1) {
                LOGGER.error("[方法增强] afterExec {}#{} 发生异常", clazz.getName(), method.getName(), e1);
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("[方法增强] 完成: {}#{}", clazz.getName(), method.getName());
            }
        }

    }

    @Override
    public String name() {
        return advise.name();
    }
}
