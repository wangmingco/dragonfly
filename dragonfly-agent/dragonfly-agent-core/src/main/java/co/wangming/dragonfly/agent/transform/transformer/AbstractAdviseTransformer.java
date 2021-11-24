package co.wangming.dragonfly.agent.transform.transformer;

import co.wangming.dragonfly.agent.transform.adaptor.Adaptor;
import co.wangming.dragonfly.agent.transform.interceptor.MethodAdviseInterceptor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.utility.JavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAdviseTransformer implements Transformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAdviseTransformer.class);

    @Override
    public AgentBuilder addTransform(AgentBuilder builder) {

        final Adaptor adaptor = adaptor();
        final ElementMatcher.Junction<TypeDescription> typeMatcher = typeConstraints();
        final ElementMatcher.Junction<MethodDescription> methodMatcher = methodConstraints();

        return builder.type(typeMatcher)
                .transform(new AgentTransformer(adaptor, methodMatcher))
                .asTerminalTransformation();
    }

    public static class AgentTransformer implements AgentBuilder.Transformer {

        private Adaptor adaptor;
        private ElementMatcher.Junction<MethodDescription> methodMatcher;

        AgentTransformer(Adaptor adaptor, ElementMatcher.Junction<MethodDescription> methodMatcher) {
            this.adaptor = adaptor;
            this.methodMatcher = methodMatcher;
        }

        @Override
        public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {

            try {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("transform class: [{} - {}] 类型:{}", adaptor.name(), methodMatcher.getClass(), typeDescription.getName());
                }

                return builder.method(methodMatcher).intercept(
                        MethodDelegation.withDefaultConfiguration().to(new MethodAdviseInterceptor(adaptor))
                );
            } catch (Exception e) {
                LOGGER.error("添加代理方法异常", e);
                // TODO
                return builder;
            }
        }
    }

    public abstract ElementMatcher.Junction<TypeDescription> typeConstraints();

    public abstract ElementMatcher.Junction<MethodDescription> methodConstraints();

    public abstract Adaptor adaptor();
}
