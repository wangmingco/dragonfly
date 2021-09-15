package co.wangming.dragonfly.agent.advise;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public abstract class AbstractMethodAdvise extends MatchableMethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMethodAdvise.class);

    public AbstractMethodAdvise(ElementMatcher.Junction<MethodDescription> matcher) {
        super(matcher);
    }

    public Object intercept(Class clazz, Method method, Object thisObj, Object[] allArguments, Callable callable) throws Exception {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("{}#{} 开始执行intercept", clazz.getName(), method.getName());
        }

        if (matches(method)) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("{}#{} 不匹配直接执行原始方法", clazz.getName(), method.getName());
            }
            return callable.call();
        }

        try {
            beforeExec(clazz, method, thisObj, allArguments);
        } catch (Exception e) {
            LOGGER.error("方法增强 {} beforeExec 发生异常", toString(), e);
        }

        try {
            return callable.call();
        } catch (Exception e) {
            try {
                exceptionOnExec(clazz, method, thisObj, allArguments);
            } catch (Exception e1) {
                LOGGER.error("方法增强 {} exceptionOnExec 发生异常", toString(), e);
            }
            throw e;
        } finally {
            try {
                afterExec(clazz, method, thisObj, allArguments);
            } catch (Exception e1) {
                LOGGER.error("方法增强 {} afterExec 发生异常", toString(), e1);
            }

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("{}#{} intercept执行完成", clazz.getName(), method.getName());
            }
        }

    }

}
