package co.wangming.dragonfly.agent.transform.mock;

import co.wangming.dragonfly.agent.listener.DefaultListener;
import co.wangming.dragonfly.agent.transform.chain.TransformerChain;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.any;

public class MockTransformerChain implements TransformerChain {

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
                            System.out.println("构建方法代理: " + typeDescription.getName());
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
