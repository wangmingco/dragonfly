package co.wangming.dragonfly.agent.trace;

import co.wangming.dragonfly.lib.zipkin.ZipkinReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: wangming
 * @date: 2021/12/1
 */
public class Trace {

    private static final Logger LOGGER = LoggerFactory.getLogger(Trace.class);

    public static void sendCS() {
        try {
            String traceId = getTraceId();
            String parentId = getParentId();
            ZipkinReporter.DEFAULE.sendCS(traceId, parentId, "", null);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void sendCR() {
        try {
            String traceId = getTraceId();
            String parentId = getParentId();
            ZipkinReporter.DEFAULE.sendCR(traceId, parentId, "", null);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    private static String getParentId() {
        String id = TraceId.currentId();
        if (id == null) {
            id = TraceId.nextId();
            TraceId.setCurrentId(id);
        }
        return id;
    }

    private static String getTraceId() {
        String id = TraceId.currentId();
        if (id == null) {
            id = TraceId.nextId();
            TraceId.setCurrentId(id);
        }
        return id;
    }
}
