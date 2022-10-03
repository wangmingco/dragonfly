package co.wangming.dragonfly.agent.core.adaptor;

import co.wangming.dragonfly.agent.core.transform.transformer.Transformer;
import co.wangming.dragonfly.agent.core.advise.MethodAdvise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
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

    public MethodAdviseAdaptor(Transformer transformer, MethodAdvise... advises) {
        if (advises == null || advises.length == 0) {
            this.advises = new ArrayList<>();
            LOGGER.warn("为{} 添加MethodAdvise为空", transformer.getClass().getSimpleName());
        } else {
            this.advises = Arrays.asList(advises);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("为{} 添加MethodAdvise:{}", transformer.getClass().getSimpleName(), this.advises.stream().map(MethodAdvise::name).collect(Collectors.joining(", ")));
            }
        }
    }

    @Override
    public final Object dispatch(Class clazz, Method method, Object[] allArguments, Callable callable) throws Exception {

        for (MethodAdvise advise : advises) {
            try {
                advise.beforeExec(clazz, method, allArguments);
            } catch (Exception e) {
                LOGGER.error("[方法增强] beforeExec {}#{} 发生异常", clazz.getName(), method.getName(), e);
            }
        }

        try {
            return callable.call();
        } catch (Throwable e) {
            LOGGER.error("--------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", e);
            for (MethodAdvise advise : advises) {
                try {
                    advise.exceptionOnExec(clazz, method, allArguments, e);
                } catch (Exception e1) {
                    LOGGER.error("[方法增强] {}#{} exceptionOnExec 发生异常", clazz.getName(), method.getName(), e);
                }
            }
            throw e;
        } finally {
            for (MethodAdvise advise : advises) {
                try {
                    advise.afterExec(clazz, method, allArguments);
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
