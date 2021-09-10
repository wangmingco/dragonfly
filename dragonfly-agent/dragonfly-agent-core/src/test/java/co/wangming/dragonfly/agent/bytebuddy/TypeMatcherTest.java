package co.wangming.dragonfly.agent.bytebuddy;

import net.bytebuddy.description.type.TypeDescription;
import org.junit.Assert;
import org.junit.Test;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class TypeMatcherTest {

    @Test
    public void testAnd() {

        TypeMatcher<TypeDescription> matcher = TypeMatcher.of(named("java.lang.String"))
                .and(not(nameStartsWith("sun.")));

        Assert.assertEquals(true, matcher.matches(new TypeDescription.ForLoadedType(String.class)));
        Assert.assertEquals(false, matcher.matches(new TypeDescription.ForLoadedType(Integer.class)));
    }

    @Test
    public void testOr() {

        TypeMatcher<TypeDescription> matcher = TypeMatcher.of(named("java.lang.Integer"))
                .or(named("java.lang.String"));

        Assert.assertEquals(true, matcher.matches(new TypeDescription.ForLoadedType(String.class)));
        Assert.assertEquals(true, matcher.matches(new TypeDescription.ForLoadedType(Integer.class)));
        Assert.assertEquals(false, matcher.matches(new TypeDescription.ForLoadedType(Double.class)));
    }

}
