package co.wangming.dragonfly.agent.plugin.jdbc.mysql;

import co.wangming.dragonfly.agent.core.adaptor.Adaptor;
import co.wangming.dragonfly.agent.core.adaptor.MethodAdviseAdaptor;
import co.wangming.dragonfly.agent.core.advise.CatchExceptionAdvise;
import co.wangming.dragonfly.agent.core.transform.transformer.AbstractAdviseTransformer;

public abstract class MysqlTraceTransformer extends AbstractAdviseTransformer {

    public abstract String packageName();

    @Override
    public Adaptor adaptor() {
        return new MethodAdviseAdaptor(this, new MysqlTraceAdvise(this), new CatchExceptionAdvise(this));
    }

}
