package co.wangming.dragonfly.agent.transform;

import co.wangming.dragonfly.agent.transform.unit.TransformerUnit;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class TransformerChain {

    protected List<TransformerUnit> chain = new ArrayList<>();

    public abstract void build() throws InstantiationException, IllegalAccessException;

    public abstract AgentBuilder invoke(AgentBuilder.Default builder);
}
