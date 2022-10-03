package co.wangming.dragonfly.agent.core.transform.chain;

import co.wangming.dragonfly.agent.core.transform.plugin.OnlyCatchExceptionTrace;
import co.wangming.dragonfly.agent.core.transform.transformer.Transform;
import co.wangming.dragonfly.agent.core.transform.transformer.Transformer;
import co.wangming.dragonfly.agent.core.listener.DefaultListener;
import co.wangming.dragonfly.agent.core.util.ClassUtil;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;
import java.util.*;
import java.util.stream.Collectors;

/**
 * {@link TransformerChain} 默认实现
 *
 * @author: wangming
 * @date: 2021/11/24
 */
public class DefaultTransformerChain implements TransformerChain {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTransformerChain.class);

    protected List<Transformer> chain = new ArrayList<>();
    ;
    protected AgentBuilder agentBuilder;

    public DefaultTransformerChain() {
        agentBuilder = new AgentBuilder
                .Default()
                .with(new DefaultListener());
    }

    @Override
    public TransformerChain scanTransformers() throws InstantiationException, IllegalAccessException {

        Set<Class<?>> subTypes = ClassUtil.getTypesAnnotatedWith(Transform.class);
        List<Class<?>> sortedSubTypes = subTypes.stream().sorted(Comparator.comparing(Class::getCanonicalName)).collect(Collectors.toList());
        sortedSubTypes.add(OnlyCatchExceptionTrace.class);

        if (LOGGER.isDebugEnabled()) {
            String names = sortedSubTypes.stream().map(it -> it.getName()).collect(Collectors.joining("\n    "));
            LOGGER.debug("构建Transformer列表:\n[\n    {}\n]", names);
        }

        for (Class subType : sortedSubTypes) {
            Transformer instance = (Transformer) subType.newInstance();
            chain.add(instance);
        }

        return this;
    }

    @Override
    public TransformerChain registerTransformers() {

        for (Transformer transformer : chain) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("构建Transformer的类型: {}", transformer.getClass().getName());
            }
            AgentBuilder wrapperBuilder = transformer.addTransform(agentBuilder);
            if (wrapperBuilder != null) {
                agentBuilder = wrapperBuilder;
            } else {
                LOGGER.error("构建Transformer失败,返回的AgentBuilder为空. 类型: {}", transformer.getClass().getName());
            }
        }

        // 现在是严格匹配因此不再需要ignore
//        for (String name : Constant.ignoreNameStartWith()) {
//            agentBuilder = agentBuilder.ignore(nameStartsWith(name));
//        }

        return this;
    }

    @Override
    public TransformerChain installOn(Instrumentation instrumentation) {
        agentBuilder.installOn(instrumentation);
        return this;
    }
}
