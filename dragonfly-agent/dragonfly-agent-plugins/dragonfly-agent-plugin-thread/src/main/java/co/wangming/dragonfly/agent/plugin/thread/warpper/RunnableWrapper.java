package co.wangming.dragonfly.agent.plugin.thread.warpper;

import co.wangming.dragonfly.agent.core.trace.TraceContext;
import co.wangming.dragonfly.agent.core.trace.TraceContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RunnableWrapper implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunnableWrapper.class);

    private Runnable target;
    private TraceContext traceContext;

    public RunnableWrapper(Runnable target, TraceContext traceContext) {
        this.target = target;
        this.traceContext = traceContext;
    }

    @Override
    public void run() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("进入Runnable代理");
        }

        TraceContext oldTraceContext = TraceContextHolder.DEFAULT.getTraceContext();

        try {
            TraceContextHolder.DEFAULT.setTraceContext(traceContext);
            target.run();
        } finally {
            TraceContextHolder.DEFAULT.setTraceContext(oldTraceContext);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("离开Runnable代理");
            }
        }
    }

    public Runnable getTarget() {
        return target;
    }
}
