package co.wangming.dragonfly.agent.bytebuddy;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

public class TypeMatcher<T extends TypeDescription> implements ElementMatcher.Junction<T> {

    private Junction<T> typeMatcher;
    private MethodMatcher<MethodDescription> methodMatcher;

    public TypeMatcher(ElementMatcher<T> matcher) {
        typeMatcher = new InnerTypeMatcher(matcher);
    }

    public static <T extends TypeDescription> TypeMatcher<T> of(ElementMatcher.Junction<T> matcher) {
        return new TypeMatcher(matcher);
    }

    @Override
    public <U extends T> TypeMatcher<U> and(ElementMatcher<? super U> other) {
        Junction newMatcher = typeMatcher.and(other);
        return new TypeMatcher(newMatcher);
    }

    @Override
    public <U extends T> TypeMatcher<U> or(ElementMatcher<? super U> other) {
        Junction newMatcher = typeMatcher.or(other);
        return new TypeMatcher(newMatcher);
    }

    @Override
    public boolean matches(T target) {
        return this.typeMatcher.matches(target);
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

    public MethodMatcher method(ElementMatcher<MethodDescription> matcher) {
        methodMatcher = new MethodMatcher(matcher, this);
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    private boolean matches(MethodDescription target) {
        return this.methodMatcher.matches(target);
    }

    public boolean matches(T typeDescription, MethodDescription methodDescription) {

        if (!this.typeMatcher.matches(typeDescription)) {
            return false;
        }

        return this.methodMatcher.matches(methodDescription);
    }

}
