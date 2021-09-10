package co.wangming.dragonfly.agent.bytebuddy;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class MethodMatcherTest {

    @Test
    public void testNamed() throws Exception {
        TypeMatcher<TypeDescription> matcher = TypeMatcher.of(named("java.lang.Integer"))
                .method(named("toBinaryString"))
                .getTypeMatcher();

        Method toBinaryString = Integer.class.getMethod("toBinaryString", int.class);
        MethodDescription.ForLoadedMethod md = new MethodDescription.ForLoadedMethod(toBinaryString);
        TypeDescription.ForLoadedType td = new TypeDescription.ForLoadedType(Integer.class);

        boolean result = matcher.matches(td, md);

        Assert.assertEquals(true, result);
    }

    @Test
    public void testNameStartsWith() throws Exception {
        TypeMatcher<TypeDescription> matcher = TypeMatcher.of(named("java.lang.Integer"))
                .method(nameStartsWith("toBinary"))
                .getTypeMatcher();

        Method toBinaryString = Integer.class.getMethod("toBinaryString", int.class);
        MethodDescription.ForLoadedMethod md = new MethodDescription.ForLoadedMethod(toBinaryString);
        TypeDescription.ForLoadedType td = new TypeDescription.ForLoadedType(Integer.class);

        boolean result = matcher.matches(td, md);

        Assert.assertEquals(true, result);
    }

    @Test
    public void testOr() throws Exception {
        TypeMatcher<TypeDescription> matcher = TypeMatcher.of(named("java.lang.Integer"))
                .method(nameStartsWith("toBinary"))
                .or(named("toUnsignedString"))
                .getTypeMatcher();

        TypeDescription.ForLoadedType td = new TypeDescription.ForLoadedType(Integer.class);

        Method toBinaryString = Integer.class.getMethod("toBinaryString", int.class);
        MethodDescription.ForLoadedMethod toBinaryStringMD = new MethodDescription.ForLoadedMethod(toBinaryString);
        Assert.assertEquals(true, matcher.matches(td, toBinaryStringMD));

        Method toUnsignedString = Integer.class.getMethod("toUnsignedString", int.class);
        MethodDescription.ForLoadedMethod toUnsignedStringMD = new MethodDescription.ForLoadedMethod(toBinaryString);
        Assert.assertEquals(true, matcher.matches(td, toUnsignedStringMD));
    }
}
