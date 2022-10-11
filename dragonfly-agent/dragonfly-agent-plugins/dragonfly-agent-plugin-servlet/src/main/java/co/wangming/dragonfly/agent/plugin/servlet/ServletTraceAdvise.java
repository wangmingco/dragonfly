package co.wangming.dragonfly.agent.plugin.servlet;

import co.wangming.dragonfly.agent.core.advise.TraceMethodAdvise;
import co.wangming.dragonfly.agent.core.trace.TraceContextHolder;
import co.wangming.dragonfly.agent.core.transform.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * TODO 添加链路追踪功能。
 *
 * 从HttpServletRequest 中拿到链路追踪header，
 * 将该请求发送到ziplink里，然后设置到 {@link TraceContextHolder}
 *
 */
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

        LOGGER.info("追踪到Servlet资源\n[\n" +
                "   class:" + clazz.getCanonicalName() + "\n" +
                "   method:" + method.getName() + "\n" +
                "   request argument:" + allArguments[0] + "\n" +
                "   response argument:" + allArguments[1] + "\n" +
                "]");

        return super.beforeExec(clazz, method, allArguments);
    }

    @Override
    public Object afterExec(Class clazz, Method method, Object[] allArguments) {
        return super.afterExec(clazz, method, allArguments);
    }
}
