package co.wangming.dragonfly.agent.transform.chain;

import co.wangming.dragonfly.agent.transform.transformer.DefaultTransformer;
import co.wangming.dragonfly.agent.transform.transformer.Transform;
import co.wangming.dragonfly.agent.transform.transformer.Transformer;
import co.wangming.dragonfly.agent.util.ClassUtil;
import co.wangming.dragonfly.agent.util.Constant;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.Collectors;

import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;

public class DefaultTransformerChain extends TransformerChain {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTransformerChain.class);

    @Override
    public void build() throws InstantiationException, IllegalAccessException {
        Set<Class<?>> subTypes = ClassUtil.getTypesAnnotatedWith(Transform.class);
        subTypes.add(DefaultTransformer.class);

        if (LOGGER.isDebugEnabled()) {
            String names = subTypes.stream().map(it -> it.getName()).collect(Collectors.joining("\n    "));
            LOGGER.debug("[TransformerChain] 找到 TransformComponent 列表:\n[\n    {}\n]", names);
        }

        for (Class subType : subTypes) {
            Transformer instance = (Transformer) subType.newInstance();
            chain.add(instance);
        }
    }

    @Override
    public AgentBuilder invoke(AgentBuilder.Default builder) {

        AgentBuilder agentBuilder = builder
                .ignore(nameStartsWith(Constant.dragonflyPackageName()))
                .ignore(nameStartsWith("java."))
                .ignore(nameStartsWith("sun."))
                .ignore(nameStartsWith("jdk."))
                .ignore(nameStartsWith("com.sun."))
                .ignore(nameStartsWith("net.bytebuddy."))
                .ignore(nameStartsWith("com.intellij."))
                .ignore(nameStartsWith("org.jetbrains."));

        for (Transformer transformer : chain) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("[TransformerChain] 构建Transformer: {}", transformer.getClass().getName());
            }
            AgentBuilder wrapperBuilder = transformer.addTransform(agentBuilder);
            if (wrapperBuilder != null) {
                agentBuilder = wrapperBuilder;
            } else {
                LOGGER.error("[TransformerChain] 返回的AgentBuilder为空");
            }
        }

        return agentBuilder;
    }
}
