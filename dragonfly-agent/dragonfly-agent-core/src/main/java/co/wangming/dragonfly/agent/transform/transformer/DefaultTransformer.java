package co.wangming.dragonfly.agent.transform.transformer;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.bytebuddy.matcher.ElementMatchers.any;

public class DefaultTransformer extends TraceAdviseTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTransformer.class);

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        return any();
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return any();
    }

}
