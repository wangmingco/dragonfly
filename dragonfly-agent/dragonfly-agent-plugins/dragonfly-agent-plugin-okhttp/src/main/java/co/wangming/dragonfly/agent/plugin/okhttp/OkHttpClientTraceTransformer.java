package co.wangming.dragonfly.agent.plugin.okhttp;

import co.wangming.dragonfly.agent.core.adaptor.Adaptor;
import co.wangming.dragonfly.agent.core.adaptor.MethodAdviseAdaptor;
import co.wangming.dragonfly.agent.core.advise.CatchExceptionAdvise;
import co.wangming.dragonfly.agent.core.transform.transformer.AbstractAdviseTransformer;

public abstract class OkHttpClientTraceTransformer extends AbstractAdviseTransformer {

    public abstract String packageName();

    @Override
    public Adaptor adaptor() {
        return new MethodAdviseAdaptor(this, new OKHttpClientTraceAdvise(this), new CatchExceptionAdvise(this));
    }

}
