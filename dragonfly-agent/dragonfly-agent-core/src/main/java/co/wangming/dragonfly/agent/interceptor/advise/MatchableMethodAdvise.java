package co.wangming.dragonfly.agent.interceptor.advise;

import co.wangming.dragonfly.agent.bytebuddy.TypeMatcher;
import net.bytebuddy.description.type.TypeDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MatchableMethodAdvise implements MethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchableMethodAdvise.class);

    private TypeMatcher matcher;

    public MatchableMethodAdvise() {
        matcher = buildClassMatcher();
    }

    protected abstract TypeMatcher buildClassMatcher();

    public boolean matches(Class clazz) {
        return matcher.matches(new TypeDescription.ForLoadedType(clazz));

    }

}
