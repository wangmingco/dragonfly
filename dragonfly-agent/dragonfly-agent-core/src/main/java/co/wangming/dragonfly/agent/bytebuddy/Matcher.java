package co.wangming.dragonfly.agent.bytebuddy;

import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

public class Matcher implements ElementMatcher.Junction<TypeDescription> {

    public static Matcher of(ElementMatcher<? super TypeDefinition> matcher) {
        return new Matcher(matcher);
    }

    private Matcher(ElementMatcher<? super TypeDefinition> matcher) {
        typeMatcher = new TypeMatcher(matcher);
    }

    private TypeMatcher typeMatcher;

    @Override
    public Junction and(ElementMatcher other) {
        return typeMatcher.and(other);
    }

    @Override
    public Junction or(ElementMatcher other) {
        return typeMatcher.or(other);
    }

    @Override
    public boolean matches(TypeDescription target) {
        return this.typeMatcher.matches(target);
    }

}
