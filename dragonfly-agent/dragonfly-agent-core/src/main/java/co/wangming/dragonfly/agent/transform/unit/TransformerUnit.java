package co.wangming.dragonfly.agent.transform.unit;

import net.bytebuddy.agent.builder.AgentBuilder;

public interface TransformerUnit {

    public AgentBuilder addTransform(AgentBuilder builder);
}
