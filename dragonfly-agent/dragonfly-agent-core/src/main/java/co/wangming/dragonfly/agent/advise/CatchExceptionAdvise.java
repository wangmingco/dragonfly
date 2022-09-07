package co.wangming.dragonfly.agent.advise;

import co.wangming.dragonfly.agent.transform.transformer.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author: wangming
 * @date: 2021/11/24
 */
public class CatchExceptionAdvise extends AbstractMethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatchExceptionAdvise.class);

    public CatchExceptionAdvise(Transformer transformer) {
        super(transformer);
    }

    @Override
    public String name() {
        return "CatchExceptionAdvise";
    }

    @Override
    public Object exceptionOnExec(Class clazz, Method method, Object[] allArguments, Throwable e) {
        // TODO 异常捕获后发送到服务器
        LOGGER.error("捕获到异常:{}", clazz.getName() + "#" + method.getName(), e);
        return null;
    }

}
