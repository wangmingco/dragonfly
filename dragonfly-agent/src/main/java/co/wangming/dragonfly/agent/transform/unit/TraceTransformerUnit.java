package co.wangming.dragonfly.agent.transform.unit;

import co.wangming.dragonfly.agent.interceptor.trace.TraceComponent;
import co.wangming.dragonfly.agent.transform.TransformerComponent;
import co.wangming.dragonfly.agent.util.ClassUtil;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import static net.bytebuddy.matcher.ElementMatchers.named;

@TransformerComponent
public class TraceTransformerUnit implements TransformerUnit {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceTransformerUnit.class);

    @Override
    public AgentBuilder addTransform(AgentBuilder builder) {
        Set<Class<?>> traceClasses = ClassUtil.getTypesAnnotatedWith(TraceComponent.class);
        AgentBuilder newBuilder = null;
        for (Class<?> traceClass : traceClasses) {
            TraceComponent anno = traceClass.getAnnotation(TraceComponent.class);

            try {
                newBuilder = builder
                        .type(named(anno.className))
                        .transform((AgentBuilder.Transformer) traceClass.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (newBuilder == null) {
            return builder;
        } else {
            return newBuilder;
        }
    }

}
