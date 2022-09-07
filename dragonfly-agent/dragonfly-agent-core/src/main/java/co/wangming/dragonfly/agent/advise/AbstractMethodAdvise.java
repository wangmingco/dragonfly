package co.wangming.dragonfly.agent.advise;

import co.wangming.dragonfly.agent.transform.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author: wangming
 * @date: 2021/11/24
 */
public abstract class AbstractMethodAdvise implements MethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMethodAdvise.class);

    private Transformer transformer;

    public AbstractMethodAdvise(Transformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public Transformer transformer() {
        return transformer;
    }

    @Override
    public Object beforeExec(Class clazz, Method method, Object[] allArguments) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[方法增强] 执行前{} 目标类方法:{}#{}", this, clazz.getCanonicalName(), method.getName());
        }
        return null;
    }

    @Override
    public Object exceptionOnExec(Class clazz, Method method, Object[] allArguments, Throwable e) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[方法增强] 执行异常{} 目标类方法:{}#{}", this, clazz.getCanonicalName(), method.getName());
        }
        return null;
    }

    @Override
    public Object afterExec(Class clazz, Method method, Object[] allArguments) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[方法增强] 执行后{} 目标类方法:{}#{}", this, clazz.getCanonicalName(), method.getName());
        }
        return null;
    }

    @Override
    public String toString() {
        return " \n[" +
                "\n    Transformer: " + transformer.getClass().getCanonicalName() +
                "\n    子类Advise: " + this.getClass().getCanonicalName() +
                "\n]\n";
    }
}
