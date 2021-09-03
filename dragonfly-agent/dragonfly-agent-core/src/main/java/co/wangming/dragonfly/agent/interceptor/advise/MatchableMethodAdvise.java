package co.wangming.dragonfly.agent.interceptor.advise;

import co.wangming.dragonfly.agent.util.Matcher;
import net.bytebuddy.description.type.TypeDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MatchableMethodAdvise implements MethodAdvise {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchableMethodAdvise.class);

    private Matcher.Matchable matchable;

    public MatchableMethodAdvise() {
        matchable = buildMatchable();
    }

    protected abstract Matcher.Matchable buildMatchable();

    public boolean matches(Class clazz) {
        TypeDescription.ForLoadedType forLoadedType = new TypeDescription.ForLoadedType(clazz);
        return matchable.matches(forLoadedType, clazz.getClassLoader(), null, null, null);

    }

}
