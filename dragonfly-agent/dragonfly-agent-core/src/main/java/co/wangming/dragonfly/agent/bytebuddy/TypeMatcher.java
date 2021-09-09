package co.wangming.dragonfly.agent.bytebuddy;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

public class TypeMatcher<T extends TypeDescription> implements ElementMatcher.Junction<T> {

    private Junction<T> typeSortMatcher;
    private MethodMatcher methodMatcher;

    public TypeMatcher(ElementMatcher<T> matcher) {
        typeSortMatcher = new InnerTypeMatcher(matcher);
    }

    public static <T extends TypeDescription> TypeMatcher<T> of(ElementMatcher.Junction<T> matcher) {
        return new TypeMatcher(matcher);
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

    public MethodMatcher method(ElementMatcher<MethodDescription> matcher) {
        methodMatcher = new MethodMatcher(matcher);
        return methodMatcher;
    }

    public boolean matches(MethodDescription target) {
        return this.methodMatcher.matches(target);
    }

    public static class InnerTypeMatcher<T extends TypeDescription> extends ElementMatcher.Junction.AbstractBase<T> {

        private ElementMatcher<T> matcher;

        private InnerTypeMatcher(ElementMatcher<T> matcher) {
            this.matcher = matcher;
        }

        @Override
        public boolean matches(T target) {
            return matcher.matches(target);
        }
    }

}
