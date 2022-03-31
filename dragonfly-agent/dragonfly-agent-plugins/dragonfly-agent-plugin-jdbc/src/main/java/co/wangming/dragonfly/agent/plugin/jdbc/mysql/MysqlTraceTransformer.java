package co.wangming.dragonfly.agent.plugin.jdbc.mysql;

import co.wangming.dragonfly.agent.adaptor.Adaptor;
import co.wangming.dragonfly.agent.adaptor.MethodAdviseAdaptor;
import co.wangming.dragonfly.agent.transform.transformer.AbstractAdviseTransformer;

public abstract class MysqlTraceTransformer extends AbstractAdviseTransformer {

    public abstract String packageName();

    @Override
    public Adaptor adaptor() {
        return new MethodAdviseAdaptor(this, new MysqlTraceAdvise());
    }

}
