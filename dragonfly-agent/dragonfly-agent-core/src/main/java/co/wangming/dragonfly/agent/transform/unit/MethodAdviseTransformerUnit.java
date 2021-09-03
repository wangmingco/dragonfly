package co.wangming.dragonfly.agent.transform.unit;

import co.wangming.dragonfly.agent.interceptor.MethodAdviseInterceptor;
import co.wangming.dragonfly.agent.transform.TransformerComponent;
import co.wangming.dragonfly.agent.util.Constant;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.utility.JavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.bytebuddy.matcher.ElementMatchers.*;

@TransformerComponent
public class MethodAdviseTransformerUnit implements TransformerUnit {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodAdviseTransformerUnit.class);

    @Override
    public AgentBuilder addTransform(AgentBuilder builder) {
        LOGGER.debug("向 AgentBuilder 添加 MethodAdviseTransformer");

        return builder
                .type(any())
                .and(not(nameStartsWith(Constant.getPackageName())))
                .and(not(nameStartsWith("net.bytebuddy.")))
                .and(not(nameStartsWith("java.")))
                .and(not(nameStartsWith("sun.")))
                .and(not(nameStartsWith("jdk.")))
                .transform(new MethodAdviseTransformer());
    }

    public static class MethodAdviseTransformer implements AgentBuilder.Transformer {

        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
            LOGGER.debug("添加代理方法: {}", typeDescription.getName());

            try {
                return builder.method(any()).intercept(MethodDelegation.withDefaultConfiguration().to(MethodAdviseInterceptor.class));
            } catch (Exception e) {
                LOGGER.error("添加代理方法异常", e);
                // TODO
                return builder;
            }
        }
    }
}
