package co.wangming.dragonfly.agent.plugin.okhttp;

import co.wangming.dragonfly.agent.core.transform.transformer.Transform;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.isConstructor;
import static net.bytebuddy.matcher.ElementMatchers.named;

@Transform
public class OkHttpV3RequestTrace extends OkHttpClientTraceTransformer {

    public String packageName() {
        return "okhttp3.";
    }

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        return named(packageName() + "Request");
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return isConstructor();
    }
}
