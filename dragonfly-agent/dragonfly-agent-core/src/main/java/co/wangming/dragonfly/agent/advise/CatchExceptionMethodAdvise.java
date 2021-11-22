package co.wangming.dragonfly.agent.advise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public abstract class CatchExceptionMethodAdvise extends AbstractMethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatchExceptionMethodAdvise.class);

    @Override
    public Object exceptionOnExec(Class clazz, Method method, Object thisObj, Object[] allArguments, Throwable e) {
        // TODO 异常捕获后发送到服务器
        LOGGER.error("捕获到异常:{}", e);
        return null;
    }

}
