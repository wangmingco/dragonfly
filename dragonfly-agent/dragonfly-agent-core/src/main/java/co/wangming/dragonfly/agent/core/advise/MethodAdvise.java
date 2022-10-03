package co.wangming.dragonfly.agent.core.advise;

import co.wangming.dragonfly.agent.core.transform.transformer.Transformer;

import java.lang.reflect.Method;

/**
 * @author: wangming
 * @date: 2021/11/24
 */
public interface MethodAdvise {

    Transformer transformer();

    String name();

    Object beforeExec(Class clazz, Method method, Object[] allArguments);

    Object exceptionOnExec(Class clazz, Method method, Object[] allArguments, Throwable e);

    Object afterExec(Class clazz, Method method, Object[] allArguments);

}
