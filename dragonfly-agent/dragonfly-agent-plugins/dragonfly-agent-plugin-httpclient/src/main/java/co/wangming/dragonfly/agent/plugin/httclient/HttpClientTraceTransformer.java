package co.wangming.dragonfly.agent.plugin.httclient;

import co.wangming.dragonfly.agent.core.adaptor.Adaptor;
import co.wangming.dragonfly.agent.core.adaptor.MethodAdviseAdaptor;
import co.wangming.dragonfly.agent.core.advise.CatchExceptionAdvise;
import co.wangming.dragonfly.agent.core.transform.transformer.AbstractAdviseTransformer;

public abstract class HttpClientTraceTransformer extends AbstractAdviseTransformer {

    public abstract String packageName();

    @Override
    public Adaptor adaptor() {
        return new MethodAdviseAdaptor(this, new HttpClientTraceAdvise(this), new CatchExceptionAdvise(this));
    }

}
