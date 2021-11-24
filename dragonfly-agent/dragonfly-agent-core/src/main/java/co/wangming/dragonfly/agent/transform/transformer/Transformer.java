package co.wangming.dragonfly.agent.transform.transformer;

import co.wangming.dragonfly.agent.transform.chain.TransformerChain;
import net.bytebuddy.agent.builder.AgentBuilder;

/**
 * 扩展组件顶级接口, 所有的扩展组件都必须实现该接口.
 * <p>
 * {@link TransformerChain} 实现类会调用 {@link Transformer#addTransform(AgentBuilder)} 方法,
 * 向 {@link AgentBuilder} 添加 {@link AgentBuilder.Transformer}.
 *
 * @author: wangming
 * @date: 2021/11/24
 */
public interface Transformer {

    AgentBuilder addTransform(AgentBuilder builder);
}
