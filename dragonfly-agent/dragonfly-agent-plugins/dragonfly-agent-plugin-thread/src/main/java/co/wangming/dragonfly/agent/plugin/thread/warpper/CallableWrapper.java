package co.wangming.dragonfly.agent.plugin.thread.warpper;

import co.wangming.dragonfly.agent.plugin.thread.ThreadTraceAdvise;
import co.wangming.dragonfly.agent.trace.TraceContext;
import co.wangming.dragonfly.agent.trace.TraceContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

public class CallableWrapper<T> implements Callable<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallableWrapper.class);

    private Callable<T> target;
    private TraceContext traceContext;

    public CallableWrapper(Callable target, TraceContext traceContext) {
        this.target = target;
        this.traceContext = traceContext;
    }

    @Override
    public T call() throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("进入Callable代理");
        }

        TraceContext oldTraceContext = TraceContextHolder.DEFAULT.getTraceContext();

        try {
            TraceContextHolder.DEFAULT.setTraceContext(traceContext);
            return target.call();
        } finally {
            TraceContextHolder.DEFAULT.setTraceContext(oldTraceContext);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("离开Callable代理");
            }

        }
    }

    public Callable<T> getTarget() {
        return target;
    }
}
