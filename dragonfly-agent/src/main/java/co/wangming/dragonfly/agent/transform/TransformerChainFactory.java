package co.wangming.dragonfly.agent.transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransformerChainFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformerChainFactory.class);

    public static TransformerChain buildDefault() throws Exception {
        TransformerChain chain = new DefaultTransformerChain();
        chain.build();
        return chain;
    }
}
