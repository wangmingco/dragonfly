package co.wangming.dragonfly.agent.transform.adaptor;

import co.wangming.dragonfly.agent.transform.interceptor.Interceptor;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * transformer 与 adaptor连接器。
 * 抽象该概念主要是为了做拓展用，目前只提供了 {@link MethodAdviseAdaptor} ，实现transformer与method advise之间的连接。
 * 如果以后要实现其他功能，只要重新实现一个Adaptor就可以了。
 *
 * @author: wangming
 * @date: 2021/11/24
 */
public interface Adaptor {

    /**
     * 接受从 {@link Interceptor#intercept(Class, Method, Object, Object[], Callable)} 派发过来的方法。
     *
     * @param clazz
     * @param method
     * @param thisObj
     * @param allArguments
     * @param callable
     * @return
     * @throws Exception
     */
    Object dispatch(Class clazz, Method method, Object thisObj, Object[] allArguments, Callable callable) throws Exception;

    /**
     * 连接器名称
     *
     * @return
     */
    String name();
}
