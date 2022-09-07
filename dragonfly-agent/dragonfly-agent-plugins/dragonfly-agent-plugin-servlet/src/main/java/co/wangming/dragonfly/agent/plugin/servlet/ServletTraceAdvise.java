package co.wangming.dragonfly.agent.plugin.servlet;

import co.wangming.dragonfly.agent.advise.TraceMethodAdvise;
import co.wangming.dragonfly.agent.transform.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class ServletTraceAdvise extends TraceMethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServletTraceAdvise.class);

    public ServletTraceAdvise(Transformer transformer) {
        super(transformer);
    }

    @Override
    public String name() {
        return ServletTraceAdvise.class.getSimpleName();
    }

    @Override
    public Object beforeExec(Class clazz, Method method, Object[] allArguments) {

        System.out.println("-------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>");
        LOGGER.info("追踪到Servlet资源");
        return super.beforeExec(clazz, method, allArguments);
    }

    @Override
    public Object afterExec(Class clazz, Method method, Object[] allArguments) {
        return super.afterExec(clazz, method, allArguments);
    }
}
