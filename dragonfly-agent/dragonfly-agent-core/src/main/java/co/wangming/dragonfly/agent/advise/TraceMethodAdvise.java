package co.wangming.dragonfly.agent.advise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author: wangming
 * @date: 2021/11/24
 */
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
    public Object beforeExec(Class clazz, Method method, Object[] allArguments) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("beforeExec: {}#{} {}", clazz.getName(), method.getName(), Arrays.asList(allArguments));
        }

//        Trace.sendCR();
        return null;
    }

    @Override
    public Object afterExec(Class clazz, Method method, Object[] allArguments) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("afterExec: {}#{} {}", clazz.getName(), method.getName(), Arrays.asList(allArguments));
        }

//        Trace.sendCS();
        return null;
    }


}
