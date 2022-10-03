package co.wangming.dragonfly.agent.core.transform.mock;

import co.wangming.dragonfly.agent.core.transform.chain.TransformerChain;
import co.wangming.dragonfly.agent.core.listener.DefaultListener;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.any;

public class MockTransformerChain implements TransformerChain {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockTransformerChain.class);

    protected AgentBuilder agentBuilder;

    public MockTransformerChain() {
        agentBuilder = new AgentBuilder
                .Default()
                .with(new DefaultListener());
    }

    @Override
    public TransformerChain scanTransformers() {
        return this;
    }

    @Override
    public TransformerChain registerTransformers() {
        agentBuilder = agentBuilder
                .type(any())
                .transform((builder, typeDescription, classLoader, module) -> {
                    LOGGER.info("构建方法代理: " + typeDescription.getName());
                            return builder
                                    .method(any())
                                    .intercept(MethodDelegation.to(new MockDelegation()));

                        }
                );
        return this;
    }

    @Override
    public TransformerChain installOn(Instrumentation instrumentation) {
        agentBuilder.installOn(instrumentation);
        return this;
    }

}
