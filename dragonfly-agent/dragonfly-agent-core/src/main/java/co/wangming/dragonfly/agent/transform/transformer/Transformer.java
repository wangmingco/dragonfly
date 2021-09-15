package co.wangming.dragonfly.agent.transform.transformer;

import net.bytebuddy.agent.builder.AgentBuilder;

public interface Transformer {

    public AgentBuilder addTransform(AgentBuilder builder);
}
