package co.wangming.dragonfly.agent.interceptor.advise;

import java.lang.reflect.Method;

public class MethodAdvise implements Advise {

    public Object beforeExec(Class clazz, Method method, Object thisObj, Object[] allArguments, AdviseContext adviseContext) {

        return null;
    }

    public Object exceptionOnExec(Class clazz, Method method, Object thisObj, Object[] allArguments, AdviseContext adviseContext) {

        return null;
    }

    public Object afterExec(Class clazz, Method method, Object thisObj, Object[] allArguments, AdviseContext adviseContext) {

        return null;
    }

}
