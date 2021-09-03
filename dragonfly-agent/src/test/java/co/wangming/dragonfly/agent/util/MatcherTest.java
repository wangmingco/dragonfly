package co.wangming.dragonfly.agent.util;

import net.bytebuddy.description.type.TypeDescription;
import org.junit.Assert;
import org.junit.Test;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class MatcherTest {

    @Test
    public void testMatched() {

        Matcher.Ignored ignored = Matcher.build()
                .type(named("java.lang.String"))
                .and(not(nameStartsWith("sun.")));

        TypeDescription.ForLoadedType forLoadedType = new TypeDescription.ForLoadedType(String.class);
        boolean result = ignored.matches(forLoadedType, String.class.getClassLoader(), null, null, null);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testNotMatch() {

        Matcher.Ignored ignored = Matcher.build()
                .type(named("java.lang.Integer"))
                .and(not(nameStartsWith("sun.")));

        TypeDescription.ForLoadedType forLoadedType = new TypeDescription.ForLoadedType(String.class);
        boolean result = ignored.matches(forLoadedType, String.class.getClassLoader(), null, null, null);
        Assert.assertEquals(false, result);
    }

}
