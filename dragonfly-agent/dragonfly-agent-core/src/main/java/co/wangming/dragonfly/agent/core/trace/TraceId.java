package co.wangming.dragonfly.agent.core.trace;

public class TraceId {

    public static String create() {
        return System.currentTimeMillis() + "";
    }
}
