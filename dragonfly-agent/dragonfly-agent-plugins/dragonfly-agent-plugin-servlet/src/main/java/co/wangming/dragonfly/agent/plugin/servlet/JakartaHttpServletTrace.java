package co.wangming.dragonfly.agent.plugin.servlet;

import co.wangming.dragonfly.agent.core.transform.transformer.Transform;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.*;

@Transform
public class JakartaHttpServletTrace extends ServletTraceTransformer {

    public String packageName() {
        return "jakarta.servlet.http.";
    }

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        return named(packageName() + "HttpServlet");
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return isMethod()
                .and(takesArguments(2))
                .and(takesArgument(0, named(packageName() + "HttpServletRequest")))
                .and(takesArgument(1, named(packageName() + "HttpServletResponse")))
                .and(nameStartsWith("do"));
    }
}
