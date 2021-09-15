package co.wangming.dragonfly.agent.plugin.jdbc.mysql.v8;

import co.wangming.dragonfly.agent.plugin.jdbc.mysql.MysqlTraceTransformer;

public abstract class MysqlV8TraceTransformer extends MysqlTraceTransformer {

    @Override
    public String packageName() {
        return "com.mysql.cj.jdbc.";
    }
}
