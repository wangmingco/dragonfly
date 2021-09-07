package co.wangming.dragonfly.agent.bytebuddy;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.MethodSortMatcher;
import net.bytebuddy.matcher.TypeSortMatcher;

public class TypeMatcher implements ElementMatcher.Junction<TypeDescription> {

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

    public void method(ElementMatcher<? super DynamicType.Builder.MethodDefinition> matcher) {
        methodMatcher = MethodSortMatcher.of(MethodSortMatcher.Sort.METHOD);
    }

    private ElementMatcher.Junction<MethodDescription> methodMatcher;

    public boolean matches(MethodDescription target) {
        if (methodMatcher == null) {
            return true;
        }
        return methodMatcher.matches(target);
    }
}
