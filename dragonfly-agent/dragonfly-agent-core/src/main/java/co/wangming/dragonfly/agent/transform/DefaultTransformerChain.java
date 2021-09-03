package co.wangming.dragonfly.agent.transform;

import co.wangming.dragonfly.agent.transform.unit.TransformerUnit;
import co.wangming.dragonfly.agent.util.ClassUtil;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class DefaultTransformerChain extends TransformerChain {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTransformerChain.class);

    @Override
    public void build() throws InstantiationException, IllegalAccessException {
        Set<Class<?>> subTypes = ClassUtil.getTypesAnnotatedWith(TransformerComponent.class);

        String names = subTypes.stream().map(it -> it.getName()).collect(Collectors.joining(","));
        LOGGER.debug("找到 TransformComponent 列表:{}", names);

        for (Class subType : subTypes) {
            TransformerUnit instance = (TransformerUnit) subType.newInstance();
            chain.add(instance);
        }

    }

    @Override
    public AgentBuilder invoke(AgentBuilder.Default builder) {

        AgentBuilder agentBuilder = builder;
        for (TransformerUnit transformerUnit : chain) {
            AgentBuilder wrapperBuilder = transformerUnit.addTransform(agentBuilder);
            if (wrapperBuilder != null) {
                agentBuilder = wrapperBuilder;
            }
        }
        return agentBuilder;
    }
}
