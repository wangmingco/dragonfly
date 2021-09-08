package co.wangming.dragonfly.agent.bytebuddy;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.isMethod;

public class MethodMatcher implements ElementMatcher.Junction<MethodDescription> {

    private ElementMatcher.Junction<MethodDescription> methodMatcher;

    public MethodMatcher(ElementMatcher<MethodDescription> matcher) {
        methodMatcher = isMethod().and(matcher);
    }

    @Override
    public Junction<MethodDescription> and(ElementMatcher other) {
        Junction<MethodDescription> newMethodMatcher = methodMatcher.and(other);
        methodMatcher = newMethodMatcher;
        return this.methodMatcher;
    }

    @Override
    public Junction<MethodDescription> or(ElementMatcher other) {
        Junction<MethodDescription> newMethodMatcher = methodMatcher.and(other);
        methodMatcher = newMethodMatcher;
        return this.methodMatcher;
    }

    @Override
    public boolean matches(MethodDescription target) {
        return methodMatcher.matches(target);
    }

}
