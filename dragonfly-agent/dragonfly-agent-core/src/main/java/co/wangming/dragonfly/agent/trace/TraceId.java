package co.wangming.dragonfly.agent.trace;

public class TraceId {

    public static String create() {
        return System.currentTimeMillis() + "";
    }
}
