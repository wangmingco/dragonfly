package co.wangming.dragonfly.agent.bytebuddy;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.TypeSortMatcher;

public class TypeMatcher implements ElementMatcher.Junction<TypeDescription> {

    public static TypeMatcher of(ElementMatcher<? super TypeDefinition> matcher) {
        return new TypeMatcher(matcher);
    }

    private Junction typeSortMatcher;

    public TypeMatcher(ElementMatcher<? super TypeDefinition> matcher) {
        typeSortMatcher = new TypeSortMatcher(matcher);
    }

    @Override
    public Junction and(ElementMatcher other) {
        Junction newMatcher = typeSortMatcher.and(other);
        this.typeSortMatcher = newMatcher;
        return this.typeSortMatcher;
    }

    @Override
    public Junction or(ElementMatcher other) {
        Junction newMatcher = typeSortMatcher.or(other);
        this.typeSortMatcher = newMatcher;
        return this.typeSortMatcher;
    }

    @Override
    public boolean matches(TypeDescription target) {
        return this.typeSortMatcher.matches(target);
    }

    public boolean matches(MethodDescription target) {
        return this.methodMatcher.matches(target);
    }

    private MethodMatcher methodMatcher;

    public MethodMatcher method(ElementMatcher<MethodDescription> matcher) {
        methodMatcher = new MethodMatcher(matcher);
        return methodMatcher;
    }


}
