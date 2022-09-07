package co.wangming.dragonfly.agent.plugin.thread;

import co.wangming.dragonfly.agent.advise.TraceMethodAdvise;
import co.wangming.dragonfly.agent.plugin.thread.warpper.CallableWrapper;
import co.wangming.dragonfly.agent.plugin.thread.warpper.ForkJoinTaskWrapper;
import co.wangming.dragonfly.agent.plugin.thread.warpper.RunnableWrapper;
import co.wangming.dragonfly.agent.trace.TraceContext;
import co.wangming.dragonfly.agent.trace.TraceContextHolder;
import co.wangming.dragonfly.agent.transform.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinTask;

public class ThreadTraceAdvise extends TraceMethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadTraceAdvise.class);

    public ThreadTraceAdvise(Transformer transformer) {
        super(transformer);
    }

    @Override
    public String name() {
        return ThreadTraceAdvise.class.getSimpleName();
    }

    @Override
    public Object beforeExec(Class clazz, Method method, Object[] allArguments) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("追踪到线程替换:{}#{}", clazz.getCanonicalName(), method.getName());
        }

        Object task = allArguments[0];
        if (task instanceof CallableWrapper
                || task instanceof RunnableWrapper
                || task instanceof ForkJoinTaskWrapper) {
            // Nothing To Do
        } else {
            TraceContext traceContext = TraceContextHolder.DEFAULT.getOrCreateTraceContext();
            if (task instanceof Runnable) {
                task = new RunnableWrapper((Runnable) task, traceContext);
            } else if (task instanceof Callable) {
                task = new CallableWrapper((Callable) task, traceContext);
            } else if (task instanceof ForkJoinTaskWrapper) {
                task = new ForkJoinTaskWrapper((ForkJoinTask) task, traceContext);
            }
            allArguments[0] = task;
        }

        return null;
    }

    @Override
    public Object afterExec(Class clazz, Method method, Object[] allArguments) {
        return null;
    }
}
