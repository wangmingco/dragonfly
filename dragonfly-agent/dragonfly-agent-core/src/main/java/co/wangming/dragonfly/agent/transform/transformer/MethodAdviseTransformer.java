package co.wangming.dragonfly.agent.transform.transformer;

import co.wangming.dragonfly.agent.advise.AbstractMethodAdvise;
import co.wangming.dragonfly.agent.util.Constant;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.*;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.utility.JavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;
import static net.bytebuddy.matcher.ElementMatchers.not;

public abstract class MethodAdviseTransformer implements Transformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodAdviseTransformer.class);

    public AgentBuilder addTransform(AgentBuilder builder) {

        ElementMatcher.Junction<TypeDescription> typeMatcher = typeConstraints();

        return builder
                .type(typeMatcher)
                .and(not(nameStartsWith(Constant.getPackageName())))
                .and(not(nameStartsWith("java.")))
                .and(not(nameStartsWith("sun.")))
                .and(not(nameStartsWith("jdk.")))
                .and(not(nameStartsWith("com.sun.")))
                .and(not(nameStartsWith("net.bytebuddy.")))
                .transform(new AgentTransformer());
    }

    public class AgentTransformer implements AgentBuilder.Transformer {

        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("添加代理方法: {}", typeDescription.getName());
            }

            try {
                ElementMatcher.Junction<MethodDescription> matcher = methodConstraints();
                return builder.method(matcher).intercept(MethodDelegation.withDefaultConfiguration().to(new MethodAdviseInterceptor()));
            } catch (Exception e) {
                LOGGER.error("添加代理方法异常", e);
                // TODO
                return builder;
            }
        }
    }


    public class MethodAdviseInterceptor {

        @RuntimeType
        public Object intercept(@Origin Class clazz, @Origin Method method, @This Object thisObj, @AllArguments Object[] allArguments, @SuperCall Callable callable) throws Exception {

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("{}#{} 开始执行intercept", clazz.getName(), method.getName());
            }

            AbstractMethodAdvise advise = advise();

            Object result = advise.intercept(clazz, method, thisObj, allArguments, callable);

            return result;
        }
    }

    public abstract ElementMatcher.Junction<TypeDescription> typeConstraints();

    public abstract ElementMatcher.Junction<MethodDescription> methodConstraints();

    public abstract AbstractMethodAdvise advise();
}
