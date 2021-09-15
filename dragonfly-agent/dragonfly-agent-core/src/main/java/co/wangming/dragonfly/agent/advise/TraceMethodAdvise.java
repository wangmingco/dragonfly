package co.wangming.dragonfly.agent.advise;

import co.wangming.dragonfly.lib.zipkin.ZipkinReporter;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class TraceMethodAdvise extends AbstractMethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceMethodAdvise.class);

    public TraceMethodAdvise(ElementMatcher.Junction<MethodDescription> matcher) {
        super(matcher);
    }

    @Override
    public Object beforeExec(Class clazz, Method method, Object thisObj, Object[] allArguments) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("进入 TraceMethodAdvise#beforeExec");
        }

        ZipkinReporter.DEFAULE.sendCS("", "", "", null);
        return null;
    }

    @Override
    public Object exceptionOnExec(Class clazz, Method method, Object thisObj, Object[] allArguments) {
        return null;
    }

    @Override
    public Object afterExec(Class clazz, Method method, Object thisObj, Object[] allArguments) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("进入 TraceMethodAdvise#afterExec");
        }
        ZipkinReporter.DEFAULE.sendCR("", "", "", null);
        return null;
    }

}
