package co.wangming.dragonfly.agent.plugin.okhttp;

import co.wangming.dragonfly.agent.advise.TraceMethodAdvise;
import co.wangming.dragonfly.agent.transform.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class HttpClientTraceAdvise extends TraceMethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientTraceAdvise.class);

    public HttpClientTraceAdvise(Transformer transformer) {
        super(transformer);
    }

    @Override
    public String name() {
        return "HttpClientTraceAdvise";
    }

    @Override
    public Object beforeExec(Class clazz, Method method, Object[] allArguments) {

        LOGGER.info("追踪到HttpClient资源");
        return super.beforeExec(clazz, method, allArguments);
    }

    @Override
    public Object afterExec(Class clazz, Method method, Object[] allArguments) {
        return super.afterExec(clazz, method, allArguments);
    }
}
