package co.wangming.dragonfly.agent.advise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author: wangming
 * @date: 2021/11/24
 */
public class CatchExceptionAdvise extends AbstractMethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatchExceptionAdvise.class);

    @Override
    public String name() {
        return "CatchExceptionAdvise";
    }

    @Override
    public Object exceptionOnExec(Class clazz, Method method, Object thisObj, Object[] allArguments, Throwable e) {
        // TODO 异常捕获后发送到服务器
        LOGGER.error("捕获到异常:{}", clazz.getName() + "#" + method.getName(), e);
        return null;
    }

}
