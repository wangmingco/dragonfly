package co.wangming.dragonfly.agent.plugin.jdbc.mysql;

import co.wangming.dragonfly.agent.advise.MethodAdvise;
import co.wangming.dragonfly.agent.transform.transformer.TraceAdviseTransformer;

public abstract class MysqlTraceTransformer extends TraceAdviseTransformer {

    public abstract String packageName();

    @Override
    public MethodAdvise advise() {
        return new MysqlTraceAdvise();
    }

}
