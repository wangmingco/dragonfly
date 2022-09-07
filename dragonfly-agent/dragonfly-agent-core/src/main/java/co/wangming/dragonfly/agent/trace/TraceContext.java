package co.wangming.dragonfly.agent.trace;

public class TraceContext {

    private String pid;
    private String traceId;
    private String tag;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public static TraceContext build() {
        TraceContext traceContext = new TraceContext();

        return traceContext;
    }
}
