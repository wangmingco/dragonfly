package co.wangming.dragonfly.agent.interceptor.trace;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.utility.JavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import static net.bytebuddy.matcher.ElementMatchers.named;

@TraceComponent()
public class TraceInterceptor implements AgentBuilder.Transformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceInterceptor.class);

    @RuntimeType
    public static Object intercept(@Origin Class clazz, @Origin Method method, @This Object thisObj, @AllArguments Object[] allArguments, @SuperCall Callable callable) throws Exception {

        // TODO add span
        try {
            return callable.call();
        } finally {
            // TODO add span
        }
    }

    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {

        try {
            return builder.method(named("")).intercept(MethodDelegation.withDefaultConfiguration().to(TraceInterceptor.class));
        } catch (Exception e) {
            LOGGER.error("添加代理方法异常", e);
            return builder;
        }
    }
}
