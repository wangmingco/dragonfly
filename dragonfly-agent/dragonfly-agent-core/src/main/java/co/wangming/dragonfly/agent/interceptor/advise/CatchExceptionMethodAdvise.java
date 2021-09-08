package co.wangming.dragonfly.agent.interceptor.advise;

import co.wangming.dragonfly.agent.bytebuddy.TypeMatcher;
import co.wangming.dragonfly.agent.interceptor.advise.component.ClassAdviseComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

import static net.bytebuddy.matcher.ElementMatchers.any;

@ClassAdviseComponent()
public class CatchExceptionMethodAdvise extends MatchableMethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatchExceptionMethodAdvise.class);

    @Override
    protected TypeMatcher buildClassMatcher() {
        return TypeMatcher.of(any());
    }

    @Override
    public Object beforeExec(Class clazz, Method method, Object thisObj, Object[] allArguments, AdviseContext adviseContext) {
        return null;
    }

    @Override
    public Object exceptionOnExec(Class clazz, Method method, Object thisObj, Object[] allArguments, AdviseContext adviseContext) {
        // TODO 异常捕获后发送到服务器
        return null;
    }

    @Override
    public Object afterExec(Class clazz, Method method, Object thisObj, Object[] allArguments, AdviseContext adviseContext) {
        return null;
    }

}
