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
                .and(not(nameStartsWith("com.intellij.")))
                .and(not(nameStartsWith("org.jetbrains.")))
                .transform(new AgentTransformer())
                .asTerminalTransformation();
    }

    public class AgentTransformer implements AgentBuilder.Transformer {

        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {

            try {
                ElementMatcher.Junction<MethodDescription> methodMatcher = methodConstraints();
                AbstractMethodAdvise advise = advise();

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("构建方法代理: [{} - {}]", advise.name(), typeDescription.getName());
                }

                return builder
                        .method(methodMatcher)
                        .intercept(MethodDelegation
                                .withDefaultConfiguration()
                                .to(new MethodAdviseInterceptor(advise))
                        );
            } catch (Exception e) {
                LOGGER.error("添加代理方法异常", e);
                // TODO
                return builder;
            }
        }
    }


    public static class MethodAdviseInterceptor {

        AbstractMethodAdvise advise;

        MethodAdviseInterceptor(AbstractMethodAdvise advise) {
            this.advise = advise;
        }

        @RuntimeType
        public Object intercept(@Origin Class clazz,
                                @Origin Method method,
                                @This Object thisObj,
                                @AllArguments Object[] allArguments,
                                @SuperCall Callable callable) throws Exception {

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("进入Interceptor: [{}] 目标:{}#{}", advise.name(), clazz.getName(), method.getName());
            }

            Object result = advise.intercept(clazz, method, thisObj, allArguments, callable);

            return result;
        }
    }

    public abstract ElementMatcher.Junction<TypeDescription> typeConstraints();

    public abstract ElementMatcher.Junction<MethodDescription> methodConstraints();

    public abstract AbstractMethodAdvise advise();
}
