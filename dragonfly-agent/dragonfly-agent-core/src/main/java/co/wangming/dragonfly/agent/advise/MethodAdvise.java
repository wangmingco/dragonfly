package co.wangming.dragonfly.agent.advise;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public interface MethodAdvise {

    String name();

    Object intercept(Class clazz, Method method, Object thisObj, Object[] allArguments, Callable callable) throws Exception;

    Object beforeExec(Class clazz, Method method, Object thisObj, Object[] allArguments);

    Object exceptionOnExec(Class clazz, Method method, Object thisObj, Object[] allArguments, Throwable e);

    Object afterExec(Class clazz, Method method, Object thisObj, Object[] allArguments);

}
