package co.wangming.dragonfly.agent.interceptor.advise;

import java.lang.reflect.Method;

public interface MethodAdvise {

    Object beforeExec(Class clazz, Method method, Object thisObj, Object[] allArguments, AdviseContext adviseContext);

    Object exceptionOnExec(Class clazz, Method method, Object thisObj, Object[] allArguments, AdviseContext adviseContext);

    Object afterExec(Class clazz, Method method, Object thisObj, Object[] allArguments, AdviseContext adviseContext);

}
