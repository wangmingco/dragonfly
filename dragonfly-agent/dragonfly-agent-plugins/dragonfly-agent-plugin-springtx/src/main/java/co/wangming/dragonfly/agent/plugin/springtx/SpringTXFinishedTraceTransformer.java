package co.wangming.dragonfly.agent.plugin.springtx;

import co.wangming.dragonfly.agent.transform.transformer.Transform;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.named;

@Transform
public class SpringTXFinishedTraceTransformer extends SpringTXTraceTransformer{

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        return named(className());
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return named("doCleanupAfterCompletion");
    }

}
