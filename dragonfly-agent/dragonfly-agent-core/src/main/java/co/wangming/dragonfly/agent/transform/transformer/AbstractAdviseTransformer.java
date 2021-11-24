package co.wangming.dragonfly.agent.transform.transformer;

import co.wangming.dragonfly.agent.transform.adaptor.Adaptor;
import co.wangming.dragonfly.agent.transform.interceptor.Interceptor;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.utility.JavaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link Transformer} 的实现类.
 * <p>
 * 该类抽象了三个方法:
 * {@link AbstractAdviseTransformer#typeConstraints()}
 * {@link AbstractAdviseTransformer#methodConstraints()}
 * {@link AbstractAdviseTransformer#adaptor()}
 * <p>
 * {@link AbstractAdviseTransformer#methodConstraints()} 是 {@link AbstractAdviseTransformer#typeConstraints()} 约束的方法信息 。
 * {@link AbstractAdviseTransformer#adaptor()} 是连接Transformer与Advise之间的连接器。
 *
 * @author: wangming
 * @date: 2021/11/24
 */
public abstract class AbstractAdviseTransformer implements Transformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAdviseTransformer.class);

    /*
     * 拿到子类的类型约束，方法约束以及连接目标Advise的连接器，然后构造出AgentTransformer，将其添加到 AgentBuilder上。
     * 需要注意的是，扩展约束要添加在DefaultTransformer之前，将DefaultTransformer放在最后。
     */
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
                    LOGGER.debug("transform class \n[\n    Adaptor -> {}, \n    methodMatcher -> {}, \n    类型 -> {} \n]",
                            adaptor.name(), methodMatcher.getClass().getName(), typeDescription.getName());
                }

                return builder.method(methodMatcher).intercept(
                        MethodDelegation.withDefaultConfiguration().to(new Interceptor(adaptor))
                );
            } catch (Exception e) {
                LOGGER.error("添加代理方法异常\n[\n    Adaptor -> {}, \n    methodMatcher -> {}, \n    类型 -> {} \n]",
                        adaptor.name(), methodMatcher.getClass().getName(), typeDescription.getName(), e);
                return builder;
            }
        }
    }

    /**
     * 类型约束
     *
     * @return
     */
    public abstract ElementMatcher.Junction<TypeDescription> typeConstraints();

    /**
     * 方法约束
     *
     * @return
     */
    public abstract ElementMatcher.Junction<MethodDescription> methodConstraints();

    /**
     * 目标Advise的连接器
     *
     * @return
     */
    public abstract Adaptor adaptor();
}
