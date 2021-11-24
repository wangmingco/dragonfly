package co.wangming.dragonfly.agent.transform.chain;

import net.bytebuddy.agent.builder.AgentBuilder;

public interface TransformerChain {

    void build() throws InstantiationException, IllegalAccessException;

    AgentBuilder invoke(AgentBuilder.Default builder);

}
