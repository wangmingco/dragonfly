package co.wangming.dragonfly.agent.transform.transformer;

import co.wangming.dragonfly.agent.util.Constant;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class DefaultTransformer extends TraceAdviseTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTransformer.class);

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        ElementMatcher.Junction<TypeDescription> an = any();

        ElementMatcher.Junction<TypeDescription> typeConstraints = null;
        for (String skipPackage : Constant.skipPackages()) {
            typeConstraints = an.and(not(nameStartsWith(skipPackage)));
        }
        return typeConstraints;
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return any();
    }

}
