package co.wangming.dragonfly.agent.transform.chain;

import co.wangming.dragonfly.agent.transform.mock.MockTransformerChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

/**
 * {@link TransformerChain} 构造工厂
 *
 * @author: wangming
 * @date: 2021/11/24
 */
public class TransformerChainFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformerChainFactory.class);

    public static TransformerChain buildDefault(Instrumentation instrumentation) throws Exception {

        LOGGER.info("构建 Default TransformerChain");
        return new DefaultTransformerChain()
                .scanTransformers()
                .registerTransformers()
                .installOn(instrumentation);
    }

    public static TransformerChain buildMock(Instrumentation instrumentation) throws Exception {

        LOGGER.info("构建 Mock TransformerChain");
        return new MockTransformerChain()
                .scanTransformers()
                .registerTransformers()
                .installOn(instrumentation);
    }
}
