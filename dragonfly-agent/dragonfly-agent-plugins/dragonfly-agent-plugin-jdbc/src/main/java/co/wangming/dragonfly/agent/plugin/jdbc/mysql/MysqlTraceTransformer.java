package co.wangming.dragonfly.agent.plugin.jdbc.mysql;

import co.wangming.dragonfly.agent.transform.adaptor.Adaptor;
import co.wangming.dragonfly.agent.transform.adaptor.MethodAdviseAdaptor;
import co.wangming.dragonfly.agent.transform.transformer.TraceAdviseTransformer;

public abstract class MysqlTraceTransformer extends TraceAdviseTransformer {

    public abstract String packageName();

    @Override
    public Adaptor adaptor() {
        return new MethodAdviseAdaptor(new MysqlTraceAdvise());
    }

}
