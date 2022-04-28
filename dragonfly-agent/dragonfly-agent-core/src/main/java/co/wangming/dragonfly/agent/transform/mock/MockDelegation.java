package co.wangming.dragonfly.agent.transform.mock;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Byte Buddy遵循一个最接近原则：
 * <p>
 * intercept(int)因为参数类型不匹配，直接Pass，另外两个方法参数都匹配，但是 intercept(String)类型更加接近，因此会委托给它
 */
public class MockDelegation {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockDelegation.class);

    @RuntimeType
    public Object intercept(@Origin Class clazz, @Origin Method method,
                            @AllArguments Object[] allArguments, @SuperCall Callable callable) throws Exception {

        LOGGER.info("[Mock] 进入实例方法代理 : \n[\n   className : {}\n   methodName: {}\n]", clazz.getName(), method.getName());

        return callable.call();
    }
}
