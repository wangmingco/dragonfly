package co.wangming.dragonfly.agent.transform.transformer;

import co.wangming.dragonfly.agent.advise.AbstractMethodAdvise;
import co.wangming.dragonfly.agent.advise.CatchExceptionMethodAdvise;
import co.wangming.dragonfly.agent.transform.Transform;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.bytebuddy.matcher.ElementMatchers.any;

@Transform
public class CatchExceptionTransformer extends MethodAdviseTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatchExceptionTransformer.class);

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("为 CatchExceptionMethodAdvise 匹配所有类型");
        }
        return any();
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("为 CatchExceptionMethodAdvise 匹配所有方法");
        }
        return any();
    }

    @Override
    public AbstractMethodAdvise advise(ElementMatcher.Junction<MethodDescription> matcher) {
        return new CatchExceptionMethodAdvise(matcher);
    }


}
