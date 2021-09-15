package co.wangming.dragonfly.agent.plugin.jdbc.mysql.v5;

import co.wangming.dragonfly.agent.plugin.jdbc.mysql.MysqlTraceTransformer;

public abstract class MysqlV5TraceTransformer extends MysqlTraceTransformer {

    @Override
    public String packageName() {
        return "com.mysql.jdbc.";
    }
}
