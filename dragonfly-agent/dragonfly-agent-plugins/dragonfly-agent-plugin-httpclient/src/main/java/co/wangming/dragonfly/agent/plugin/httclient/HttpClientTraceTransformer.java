package co.wangming.dragonfly.agent.plugin.httclient;

import co.wangming.dragonfly.agent.adaptor.Adaptor;
import co.wangming.dragonfly.agent.adaptor.MethodAdviseAdaptor;
import co.wangming.dragonfly.agent.advise.CatchExceptionAdvise;
import co.wangming.dragonfly.agent.transform.transformer.AbstractAdviseTransformer;

public abstract class HttpClientTraceTransformer extends AbstractAdviseTransformer {

    public abstract String packageName();

    @Override
    public Adaptor adaptor() {
        return new MethodAdviseAdaptor(this, new HttpClientTraceAdvise(), new CatchExceptionAdvise());
    }

}
