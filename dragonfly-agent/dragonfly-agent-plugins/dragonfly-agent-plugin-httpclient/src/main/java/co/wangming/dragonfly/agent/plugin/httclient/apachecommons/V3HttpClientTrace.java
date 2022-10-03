package co.wangming.dragonfly.agent.plugin.httclient.apachecommons;

import co.wangming.dragonfly.agent.core.transform.transformer.Transform;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.named;

@Transform
public class V3HttpClientTrace extends AbstractHttpClientTrace {

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        return named(packageName() + "HttpClient");
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return named("executeMethod");
    }
}
