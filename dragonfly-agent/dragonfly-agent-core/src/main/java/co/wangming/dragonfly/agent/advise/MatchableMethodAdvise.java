package co.wangming.dragonfly.agent.advise;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public abstract class MatchableMethodAdvise implements MethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchableMethodAdvise.class);

    private ElementMatcher.Junction<MethodDescription> methodMatcher;

    public MatchableMethodAdvise(ElementMatcher.Junction<MethodDescription> matcher) {
        methodMatcher = matcher;
    }

    public boolean matches(Method clazz) {
        return methodMatcher.matches(new MethodDescription.ForLoadedMethod(clazz));

    }

}
