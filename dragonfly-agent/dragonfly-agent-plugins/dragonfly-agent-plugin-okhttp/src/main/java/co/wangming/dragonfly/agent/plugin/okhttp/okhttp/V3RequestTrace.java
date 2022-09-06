package co.wangming.dragonfly.agent.plugin.okhttp.okhttp;

import co.wangming.dragonfly.agent.transform.transformer.Transform;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.isConstructor;
import static net.bytebuddy.matcher.ElementMatchers.named;

@Transform
public class V3RequestTrace extends AbstractOkhttpTrace {

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        return named(packageName() + "Request");
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return isConstructor();
    }
}
