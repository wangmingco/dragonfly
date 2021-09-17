package co.wangming.dragonfly.agent.advise;

import java.lang.reflect.Method;

public interface MethodAdvise {

    String name();

    Object beforeExec(Class clazz, Method method, Object thisObj, Object[] allArguments);

    Object exceptionOnExec(Class clazz, Method method, Object thisObj, Object[] allArguments);

    Object afterExec(Class clazz, Method method, Object thisObj, Object[] allArguments);

}
