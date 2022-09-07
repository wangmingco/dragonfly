package co.wangming.dragonfly.agent.plugin.servlet;

import co.wangming.dragonfly.agent.adaptor.Adaptor;
import co.wangming.dragonfly.agent.adaptor.MethodAdviseAdaptor;
import co.wangming.dragonfly.agent.advise.CatchExceptionAdvise;
import co.wangming.dragonfly.agent.transform.transformer.AbstractAdviseTransformer;

public abstract class ServletTraceTransformer extends AbstractAdviseTransformer {

    public abstract String packageName();

    @Override
    public Adaptor adaptor() {
        return new MethodAdviseAdaptor(this, new ServletTraceAdvise(this), new CatchExceptionAdvise(this));
    }

}
