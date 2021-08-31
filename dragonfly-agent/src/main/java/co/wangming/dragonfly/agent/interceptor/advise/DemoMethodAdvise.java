package co.wangming.dragonfly.agent.interceptor.advise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

@AdviseComponent
public class DemoMethodAdvise extends MethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoMethodAdvise.class);

    @Override
    public Object beforeExec(Class clazz, Method method, Object thisObj, Object[] allArguments, AdviseContext adviseContext) {

        LOGGER.debug("进入方法之前:{}", method.getName());

        return super.beforeExec(clazz, method, thisObj, allArguments, adviseContext);
    }

    @Override
    public Object exceptionOnExec(Class clazz, Method method, Object thisObj, Object[] allArguments, AdviseContext adviseContext) {

        LOGGER.debug("方法遇到异常:{}", method.getName());

        return super.exceptionOnExec(clazz, method, thisObj, allArguments, adviseContext);
    }

    @Override
    public Object afterExec(Class clazz, Method method, Object thisObj, Object[] allArguments, AdviseContext adviseContext) {

        LOGGER.debug("进入方法之后:{}", method.getName());

        return super.afterExec(clazz, method, thisObj, allArguments, adviseContext);
    }
}
