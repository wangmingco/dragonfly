package co.wangming.dragonfly.agent.advise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class CatchExceptionMethodAdvise extends AbstractMethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatchExceptionMethodAdvise.class);

    private static CatchExceptionMethodAdvise advise = new CatchExceptionMethodAdvise();

    public static CatchExceptionMethodAdvise INSANCE() {
        return advise;
    }

    @Override
    public Object beforeExec(Class clazz, Method method, Object thisObj, Object[] allArguments) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("进入 CatchExceptionMethodAdvise beforeExec");
        }
        return null;
    }

    @Override
    public Object exceptionOnExec(Class clazz, Method method, Object thisObj, Object[] allArguments) {
        // TODO 异常捕获后发送到服务器
        return null;
    }

    @Override
    public Object afterExec(Class clazz, Method method, Object thisObj, Object[] allArguments) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("进入 CatchExceptionMethodAdvise afterExec");
        }
        return null;
    }

}
