package co.wangming.dragonfly.agent.util;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.MethodSortMatcher;
import net.bytebuddy.matcher.TypeSortMatcher;
import org.junit.Test;

import static net.bytebuddy.matcher.ElementMatchers.any;

public class MatcherTest {

//    @Test
//    public void testMatched() {
//
//        Matcher.Ignored ignored = Matcher.build()
//                .type(named("java.lang.String"))
//                .and(not(nameStartsWith("sun.")));
//
//        TypeDescription.ForLoadedType forLoadedType = new TypeDescription.ForLoadedType(String.class);
//        boolean result = ignored.matches(forLoadedType, String.class.getClassLoader(), null, null, null);
//        Assert.assertEquals(true, result);
//    }
//
//    @Test
//    public void testNotMatch() {
//
//        Matcher.Ignored ignored = Matcher.build()
//                .type(named("java.lang.Integer"))
//                .and(not(nameStartsWith("sun.")));
//
//        TypeDescription.ForLoadedType forLoadedType = new TypeDescription.ForLoadedType(String.class);
//        boolean result = ignored.matches(forLoadedType, String.class.getClassLoader(), null, null, null);
//        Assert.assertEquals(false, result);
//    }

    @Test
    public void test() {
        ElementMatcher.Junction<MethodDescription> a = MethodSortMatcher.of(MethodSortMatcher.Sort.METHOD);
        TypeSortMatcher b = new TypeSortMatcher(any());

    }

}
