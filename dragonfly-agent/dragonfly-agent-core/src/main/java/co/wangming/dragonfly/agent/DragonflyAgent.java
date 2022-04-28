package co.wangming.dragonfly.agent;

import co.wangming.dragonfly.agent.transform.chain.TransformerChainFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

/**
 * Agent 入口.
 * 在该入口中通过 TransformerChainFactory工厂类构建出 ByteBuddy#AgentBuilder,
 * 然后将该AgentBuilder 安装到Instrumentation 上.
 *
 * @author: wangming
 * @date: 2021/11/24
 */
public class DragonflyAgent {

    private static final Logger LOGGER = LoggerFactory.getLogger(DragonflyAgent.class);

    public static void premain(String arguments, Instrumentation instrumentation) throws Exception {

        LOGGER.info("Dragonfly 启动. 参数:{}", arguments);

        switch (arguments) {
            case "mock": {
                TransformerChainFactory.buildMock(instrumentation);
                break;
            }
            default: {
                TransformerChainFactory.buildDefault(instrumentation);
            }
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dragonfly 构建完成");
        }
    }
}
