package co.wangming.dragonfly.agent.transform.transformer;

import net.bytebuddy.agent.builder.AgentBuilder;

public interface Transformer {

    AgentBuilder addTransform(AgentBuilder builder);
}
