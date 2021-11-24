package co.wangming.dragonfly.agent.transform.transformer;

import co.wangming.dragonfly.agent.advise.TraceMethodAdvise;
import co.wangming.dragonfly.agent.transform.adaptor.Adaptor;
import co.wangming.dragonfly.agent.transform.adaptor.MethodAdviseAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TraceAdviseTransformer extends AbstractAdviseTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceAdviseTransformer.class);

    @Override
    public Adaptor adaptor() {
        return new MethodAdviseAdaptor(new TraceMethodAdvise());
    }

}
