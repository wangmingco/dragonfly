package co.wangming.dragonfly.agent.plugin.jdbc.mysql;

import co.wangming.dragonfly.agent.transform.transformer.TraceTransformer;

public abstract class MysqlTraceTransformer extends TraceTransformer {

    public abstract String packageName();
}
