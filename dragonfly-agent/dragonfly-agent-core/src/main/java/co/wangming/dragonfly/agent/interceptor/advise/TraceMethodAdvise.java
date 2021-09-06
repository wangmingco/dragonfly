package co.wangming.dragonfly.agent.interceptor.advise;

import co.wangming.dragonfly.lib.zipkin.ZipkinReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public abstract class TraceMethodAdvise extends MatchableMethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceMethodAdvise.class);

    @Override
    public Object beforeExec(Class clazz, Method method, Object thisObj, Object[] allArguments, AdviseContext adviseContext) {
        ZipkinReporter.DEFAULE.sendCS("", "", "", null);
        return null;
    }

    @Override
    public Object exceptionOnExec(Class clazz, Method method, Object thisObj, Object[] allArguments, AdviseContext adviseContext) {
        return null;
    }

    @Override
    public Object afterExec(Class clazz, Method method, Object thisObj, Object[] allArguments, AdviseContext adviseContext) {
        ZipkinReporter.DEFAULE.sendCR("", "", "", null);
        return null;
    }

}
