package co.wangming.dragonfly.agent.bytebuddy;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.isMethod;

public class MethodMatcher<T extends MethodDescription> implements ElementMatcher.Junction<T> {

    private Junction<MethodDescription> methodMatcher;

    public MethodMatcher(ElementMatcher<MethodDescription> matcher) {
        methodMatcher = isMethod().and(matcher);
    }

    @Override
    public <U extends T> Junction<U> and(ElementMatcher<? super U> other) {
        Junction newMethodMatcher = methodMatcher.and(other);
        methodMatcher = newMethodMatcher;
        return newMethodMatcher;
    }

    @Override
    public <U extends T> Junction<U> or(ElementMatcher<? super U> other) {
        Junction newMethodMatcher = methodMatcher.and(other);
        methodMatcher = newMethodMatcher;
        return newMethodMatcher;
    }

    @Override
    public boolean matches(MethodDescription target) {
        return methodMatcher.matches(target);
    }

}
