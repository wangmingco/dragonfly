package co.wangming.dragonfly.agent.util;

public class TraceId {

    private static final ThreadLocal<String> ID_HOLDER = new ThreadLocal<>();

    public static String currentId() {
        return ID_HOLDER.get();
    }

    public static void setCurrentId(String id) {
        ID_HOLDER.set(id);
    }

    public static String nextId() {
        return System.currentTimeMillis() + "";
    }

}
