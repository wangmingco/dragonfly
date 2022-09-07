package co.wangming.dragonfly.agent.plugin.servlet.v3;

import co.wangming.dragonfly.agent.transform.transformer.Transform;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.*;

@Transform
public class HttpServletTrace extends ServletTrace {

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        return named(packageName() + "HttpServlet");
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return isMethod()
                .and(takesArguments(2))
                .and(takesArgument(0, named("javax.servlet.http.HttpServletRequest")))
                .and(takesArgument(1, named("javax.servlet.http.HttpServletResponse")))
                .and(nameStartsWith("do"));
    }
}
