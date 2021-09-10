package co.wangming.dragonfly.agent.bytebuddy;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

public class MethodMatcher<T extends MethodDescription> implements ElementMatcher.Junction<T> {

    private Junction<T> methodMatcher;
    private TypeMatcher<TypeDescription> typeMatcher;

    public MethodMatcher(ElementMatcher<MethodDescription> matcher, TypeMatcher<TypeDescription> typeMatcher) {
        this.typeMatcher = typeMatcher;
        this.methodMatcher = new InnerMethodMatcher(matcher);
    }

    @Override
    public <U extends T> MethodMatcher<U> and(ElementMatcher<? super U> other) {
        Junction newMethodMatcher = methodMatcher.and(other);
        return new MethodMatcher(newMethodMatcher, this.typeMatcher);
    }

    @Override
    public <U extends T> MethodMatcher<U> or(ElementMatcher<? super U> other) {
        Junction newMethodMatcher = methodMatcher.or(other);
        return new MethodMatcher(newMethodMatcher, this.typeMatcher);
    }

    @Override
    public boolean matches(T target) {
        return methodMatcher.matches(target);
    }

    public static class InnerMethodMatcher<T extends MethodDescription> extends ElementMatcher.Junction.AbstractBase<T> {

        private ElementMatcher<T> matcher;

        private InnerMethodMatcher(ElementMatcher<T> matcher) {
            this.matcher = matcher;
        }

        @Override
        public boolean matches(T target) {
            return matcher.matches(target);
        }
    }

    public TypeMatcher<TypeDescription> getTypeMatcher() {
        typeMatcher.setMethodMatcher(this);
        return typeMatcher;
    }
}
