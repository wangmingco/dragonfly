package co.wangming.dragonfly.agent.transform.chain;

import co.wangming.dragonfly.agent.listener.DefaultListener;
import co.wangming.dragonfly.agent.transform.transformer.CatchTransformer;
import co.wangming.dragonfly.agent.transform.transformer.Transform;
import co.wangming.dragonfly.agent.transform.transformer.Transformer;
import co.wangming.dragonfly.agent.util.ClassUtil;
import co.wangming.dragonfly.agent.util.Constant;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;
import static net.bytebuddy.matcher.ElementMatchers.not;

/**
 * {@link TransformerChain} 默认实现
 *
 * @author: wangming
 * @date: 2021/11/24
 */
public class DefaultTransformerChain implements TransformerChain {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTransformerChain.class);

    protected List<Transformer> chain;

    @Override
    public void build() throws InstantiationException, IllegalAccessException {
        chain = new ArrayList<>();

        Set<Class<?>> subTypes = ClassUtil.getTypesAnnotatedWith(Transform.class);
        subTypes.add(CatchTransformer.class);

        if (LOGGER.isDebugEnabled()) {
            String names = subTypes.stream().map(it -> it.getName()).collect(Collectors.joining("\n    "));
            LOGGER.debug("构建Transformer列表:\n[\n    {}\n]", names);
        }

        for (Class subType : subTypes) {
            Transformer instance = (Transformer) subType.newInstance();
            chain.add(instance);
        }
    }

    @Override
    public AgentBuilder invoke(AgentBuilder.Default builder) {

        AgentBuilder agentBuilder = builder
                .with(new DefaultListener())
                ;

        for (String name : Constant.ignoreNameStartWith()) {
            agentBuilder = agentBuilder.ignore(nameStartsWith(name));
        }

        for (Transformer transformer : chain) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("构建Transformer的类型: {}", transformer.getClass().getName());
            }
            AgentBuilder wrapperBuilder = transformer.addTransform(agentBuilder);
            if (wrapperBuilder != null) {
                agentBuilder = wrapperBuilder;
            } else {
                LOGGER.error("构建Transformer失败,返回的AgentBuilder为空. 类型: {}", transformer.getClass().getName());
            }
        }

        return agentBuilder;
    }
}
