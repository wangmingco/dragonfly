package co.wangming.dragonfly.agent.advise;

import java.lang.reflect.Method;

/**
 * @author: wangming
 * @date: 2021/11/24
 */
public interface MethodAdvise {

    String name();

    Object beforeExec(Class clazz, Method method, Object thisObj, Object[] allArguments);

    Object exceptionOnExec(Class clazz, Method method, Object thisObj, Object[] allArguments, Throwable e);

    Object afterExec(Class clazz, Method method, Object thisObj, Object[] allArguments);

}
