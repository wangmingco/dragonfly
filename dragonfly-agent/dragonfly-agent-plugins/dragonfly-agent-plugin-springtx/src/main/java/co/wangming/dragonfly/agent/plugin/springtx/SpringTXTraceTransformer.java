package co.wangming.dragonfly.agent.plugin.springtx;

import co.wangming.dragonfly.agent.core.adaptor.Adaptor;
import co.wangming.dragonfly.agent.core.adaptor.MethodAdviseAdaptor;
import co.wangming.dragonfly.agent.core.advise.CatchExceptionAdvise;
import co.wangming.dragonfly.agent.core.transform.transformer.AbstractAdviseTransformer;

public abstract class SpringTXTraceTransformer extends AbstractAdviseTransformer {

    public String className() {
        return "org.springframework.jdbc.datasource.DataSourceTransactionManager";
    }

    @Override
    public Adaptor adaptor() {
        return new MethodAdviseAdaptor(this, new SpringTXTraceAdvise(this), new CatchExceptionAdvise(this));
    }

}
