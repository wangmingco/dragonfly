package co.wangming.dragonfly.agent.plugin.jdbc.mysql;

import co.wangming.dragonfly.agent.advise.TraceMethodAdvise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class MysqlTraceAdvise extends TraceMethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlTraceAdvise.class);

    @Override
    public String name() {
        return "MysqlTraceAdvise";
    }

    @Override
    public Object beforeExec(Class clazz, Method method, Object thisObj, Object[] allArguments) {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.info("追踪到Mysql资源");
        }
        return super.beforeExec(clazz, method, thisObj, allArguments);
    }

    @Override
    public Object afterExec(Class clazz, Method method, Object thisObj, Object[] allArguments) {
        return super.afterExec(clazz, method, thisObj, allArguments);
    }
}
