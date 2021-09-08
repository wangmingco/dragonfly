package co.wangming.dragonfly.agent.util;

import co.wangming.dragonfly.agent.bytebuddy.TypeMatcher;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.junit.Assert;
import org.junit.Test;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class MatcherTest {

    @Test
    public void testMatched() {

        ElementMatcher.Junction matcher = TypeMatcher.of(named("java.lang.String"))
                .and(not(nameStartsWith("sun.")));

        TypeDescription.ForLoadedType forLoadedType = new TypeDescription.ForLoadedType(String.class);
        boolean result = matcher.matches(forLoadedType);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testNotMatch() {

        ElementMatcher.Junction matcher = TypeMatcher.of(named("java.lang.Integer"))
                .and(not(nameStartsWith("sun.")));

        TypeDescription.ForLoadedType forLoadedType = new TypeDescription.ForLoadedType(String.class);
        boolean result = matcher.matches(forLoadedType);
        Assert.assertEquals(false, result);
    }

}
