package co.wangming.dragonfly.agent;

import co.wangming.dragonfly.agent.transform.chain.TransformerChainFactory;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

public class DragonflyAgent {

    private static final Logger LOGGER = LoggerFactory.getLogger(DragonflyAgent.class);

    public static void premain(String arguments, Instrumentation instrumentation) throws Exception {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dragonfly 启动");
        }

        AgentBuilder.Default builder = new AgentBuilder.Default();

        AgentBuilder agentBuilder = TransformerChainFactory.buildDefault(builder);

        agentBuilder.installOn(instrumentation);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Dragonfly 构建完成");
        }
    }
}
