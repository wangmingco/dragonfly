package co.wangming.dragonfly.agent.transform.transformer;

import co.wangming.dragonfly.agent.advise.AbstractMethodAdvise;
import co.wangming.dragonfly.agent.advise.TraceMethodAdvise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TraceTransformer extends MethodAdviseTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceTransformer.class);

    @Override
    public AbstractMethodAdvise advise() {
        return new TraceMethodAdvise();
    }

}
