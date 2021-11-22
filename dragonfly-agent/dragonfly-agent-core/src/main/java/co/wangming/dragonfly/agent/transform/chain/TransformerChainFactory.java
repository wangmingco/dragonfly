package co.wangming.dragonfly.agent.transform.chain;

import net.bytebuddy.agent.builder.AgentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransformerChainFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformerChainFactory.class);

    public static TransformerChain buildDefault() throws Exception {
        TransformerChain chain = new DefaultTransformerChain();
        chain.build();
        return chain;
    }

    public static AgentBuilder buildDefault(AgentBuilder.Default builder) throws Exception {
        TransformerChain chain = buildDefault();
        return chain.invoke(builder);
    }
}
