package co.wangming.dragonfly.agent.transform.adaptor;

import co.wangming.dragonfly.agent.advise.MethodAdvise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * {@link Adaptor} 的 method advise 实现
 *
 * @author: wangming
 * @date: 2021/11/24
 */
public class MethodAdviseAdaptor implements Adaptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodAdviseAdaptor.class);

    private List<MethodAdvise> advises;

    public MethodAdviseAdaptor(MethodAdvise... advises) {
        this.advises = Arrays.asList(advises);
    }

    @Override
    public final Object dispatch(Class clazz, Method method, Object thisObj, Object[] allArguments, Callable callable) throws Exception {

        for (MethodAdvise advise : advises) {
            try {
                advise.beforeExec(clazz, method, thisObj, allArguments);
            } catch (Exception e) {
                LOGGER.error("[方法增强] beforeExec {}#{} 发生异常", clazz.getName(), method.getName(), e);
            }
        }

        try {
            return callable.call();
        } catch (Throwable e) {
            for (MethodAdvise advise : advises) {
                try {
                    advise.exceptionOnExec(clazz, method, thisObj, allArguments, e);
                } catch (Exception e1) {
                    LOGGER.error("[方法增强] {}#{} exceptionOnExec 发生异常", clazz.getName(), method.getName(), e);
                }
            }
            throw e;
        } finally {
            for (MethodAdvise advise : advises) {
                try {
                    advise.afterExec(clazz, method, thisObj, allArguments);
                } catch (Exception e1) {
                    LOGGER.error("[方法增强] afterExec {}#{} 发生异常", clazz.getName(), method.getName(), e1);
                }
            }

        }
    }

    @Override
    public String name() {
        return advises.stream().map(advise -> advise.name()).collect(Collectors.joining(" - "));
    }
}
