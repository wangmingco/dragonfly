package co.wangming.dragonfly.agent.interceptor.empty;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class Emptynterceptor {

    @RuntimeType
    public static Object intercept(@Origin Class clazz, @Origin Method method, @This Object thisObj, @AllArguments Object[] allArguments, @SuperCall Callable callable) throws Exception {
        return callable.call();
    }
}
