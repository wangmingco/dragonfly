package co.wangming.dragonfly.agent.plugin.springtx;

import co.wangming.dragonfly.agent.core.advise.TraceMethodAdvise;
import co.wangming.dragonfly.agent.core.transform.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionDefinition;

import java.lang.reflect.Method;

public class SpringTXTraceAdvise extends TraceMethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringTXTraceAdvise.class);

    public SpringTXTraceAdvise(Transformer transformer) {
        super(transformer);
    }

    @Override
    public String name() {
        return SpringTXTraceAdvise.class.getCanonicalName();
    }

    @Override
    public Object beforeExec(Class clazz, Method method, Object[] allArguments) {

        TransactionDefinition definition = (TransactionDefinition) allArguments[1];

        String methodName = method.getName();
        if ("doBegin".equals(methodName)) {

            LOGGER.info("事务开始 {}", definition.getName());

        } else if ("doCleanupAfterCompletion".equals(methodName)) {
            LOGGER.info("事务结束 {}", definition.getName());

        } else {
            LOGGER.info("未识别的方法名称 {}", methodName);
        }

        return super.beforeExec(clazz, method, allArguments);
    }

    @Override
    public Object afterExec(Class clazz, Method method, Object[] allArguments) {
        return super.afterExec(clazz, method, allArguments);
    }
}
