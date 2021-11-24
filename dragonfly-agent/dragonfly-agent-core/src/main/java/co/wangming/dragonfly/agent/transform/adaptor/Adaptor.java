package co.wangming.dragonfly.agent.transform.adaptor;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public interface Adaptor {

    Object intercept(Class clazz, Method method, Object thisObj, Object[] allArguments, Callable callable) throws Exception;

    String name();
}
