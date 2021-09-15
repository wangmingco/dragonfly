package co.wangming.dragonfly.agent.transform.transformer;

import co.wangming.dragonfly.agent.advise.AbstractMethodAdvise;
import co.wangming.dragonfly.agent.advise.CatchExceptionMethodAdvise;
import co.wangming.dragonfly.agent.transform.Transform;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.bytebuddy.matcher.ElementMatchers.any;

@Transform
public class CatchExceptionTransformer extends MethodAdviseTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatchExceptionTransformer.class);

    @Override
    public AgentBuilder.Identified.Narrowable typeConstraints(AgentBuilder.Identified.Narrowable builder) {
        LOGGER.debug("向 AgentBuilder 添加 MethodAdviseTransformer");

//        return builder.and(named("").or(not(nameStartsWith("com.mysql"))));
        return builder.and(any());
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return any();
    }

    @Override
    public AbstractMethodAdvise advise(ElementMatcher.Junction<MethodDescription> matcher) {
        return new CatchExceptionMethodAdvise(matcher);
    }


}
