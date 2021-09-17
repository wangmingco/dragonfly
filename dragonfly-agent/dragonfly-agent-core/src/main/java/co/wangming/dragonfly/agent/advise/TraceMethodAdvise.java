package co.wangming.dragonfly.agent.advise;

import co.wangming.dragonfly.agent.util.TraceId;
import co.wangming.dragonfly.lib.zipkin.ZipkinReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class TraceMethodAdvise extends AbstractMethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceMethodAdvise.class);

    public TraceMethodAdvise() {
        super();
    }

    @Override
    public String name() {
        return "TraceMethodAdvise";
    }

    @Override
    public Object beforeExec(Class clazz, Method method, Object thisObj, Object[] allArguments) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("beforeExec: {}#{}", clazz.getName(), method.getName());
        }

        try {
            String traceId = getTraceId();
            String parentId = getParentId();
            ZipkinReporter.DEFAULE.sendCS(traceId, parentId, "", null);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    @Override
    public Object exceptionOnExec(Class clazz, Method method, Object thisObj, Object[] allArguments) {
        return null;
    }

    @Override
    public Object afterExec(Class clazz, Method method, Object thisObj, Object[] allArguments) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("afterExec: {}#{}", clazz.getName(), method.getName());
        }
        try {
            String traceId = getTraceId();
            String parentId = getParentId();
            ZipkinReporter.DEFAULE.sendCR(traceId, parentId, "", null);
        } catch (Exception e) {
            LOGGER.error("", e);
        }

        return null;
    }

    private String getParentId() {
        String id = TraceId.currentId();
        if (id == null) {
            id = TraceId.nextId();
            TraceId.setCurrentId(id);
        }
        return id;
    }

    private String getTraceId() {
        String id = TraceId.currentId();
        if (id == null) {
            id = TraceId.nextId();
            TraceId.setCurrentId(id);
        }
        return id;
    }
}
