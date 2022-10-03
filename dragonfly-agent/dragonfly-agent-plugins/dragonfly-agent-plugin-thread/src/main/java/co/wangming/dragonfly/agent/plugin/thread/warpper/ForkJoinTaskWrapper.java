package co.wangming.dragonfly.agent.plugin.thread.warpper;

import co.wangming.dragonfly.agent.core.trace.TraceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ForkJoinTask;

public class ForkJoinTaskWrapper<T> extends ForkJoinTask<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForkJoinTaskWrapper.class);

    private ForkJoinTask<T> target;
    private TraceContext traceContext;

    public ForkJoinTaskWrapper(ForkJoinTask target, TraceContext traceContext) {
        this.target = target;
        this.traceContext = traceContext;
    }

    @Override
    public T getRawResult() {
        return null;
    }

    @Override
    protected void setRawResult(T value) {

    }

    @Override
    protected boolean exec() {
        return false;
    }
}
