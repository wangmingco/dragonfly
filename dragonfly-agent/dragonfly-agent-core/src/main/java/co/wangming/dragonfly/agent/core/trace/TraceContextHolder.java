package co.wangming.dragonfly.agent.core.trace;

/**
 * TODO 完善链路追踪功能
 */
public enum TraceContextHolder {

    DEFAULT;

    private final InheritableThreadLocal<TraceContext> traceContextCache = new InheritableThreadLocal<TraceContext>();

    public void setTraceContext(TraceContext traceContext) {
        traceContextCache.set(traceContext);
    }

    public TraceContext getTraceContext() {
        return traceContextCache.get();
    }

    public TraceContext getOrCreateTraceContext() {
        TraceContext context = traceContextCache.get();
        if (context == null) {
            context = new TraceContext();
            context.setTraceId(TraceId.create());
            context.setPid(""); // TODO
            context.setTag(""); // TODO
            traceContextCache.set(context);
        }

        return context;
    }
}
