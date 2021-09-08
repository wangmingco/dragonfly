package co.wangming.dragonfly.agent.bytebuddy;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDefinition;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

public class TypeMatcher<T extends TypeDescription> implements ElementMatcher.Junction<T> {

    public static TypeMatcher of(ElementMatcher<TypeDefinition> matcher) {
        return new TypeMatcher(matcher);
    }

    private Junction<T> typeSortMatcher;

    public TypeMatcher(ElementMatcher<T> matcher) {
        typeSortMatcher = new JunctionMapper(matcher);
    }

    @Override
    public <U extends T> Junction<U> and(ElementMatcher<? super U> other) {
        Junction newMatcher = typeSortMatcher.and(other);
        this.typeSortMatcher = newMatcher;
        return newMatcher;
    }

    @Override
    public <U extends T> Junction<U> or(ElementMatcher<? super U> other) {
        Junction newMatcher = typeSortMatcher.or(other);
        this.typeSortMatcher = newMatcher;
        return newMatcher;
    }

    @Override
    public boolean matches(T target) {
        return this.typeSortMatcher.matches(target);
    }

    public static class JunctionMapper<T extends TypeDescription> extends ElementMatcher.Junction.AbstractBase<T> {

        private ElementMatcher<T> matcher;

        private JunctionMapper(ElementMatcher<T> matcher) {
            this.matcher = matcher;
        }

        @Override
        public boolean matches(T target) {
            return matcher.matches(target);
        }
    }

    private MethodMatcher methodMatcher;

    public MethodMatcher method(ElementMatcher<MethodDescription> matcher) {
        methodMatcher = new MethodMatcher(matcher);
        return methodMatcher;
    }

    public boolean matches(MethodDescription target) {
        return this.methodMatcher.matches(target);
    }

}
