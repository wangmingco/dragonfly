package co.wangming.dragonfly.agent.transform.chain;

import co.wangming.dragonfly.agent.transform.transformer.Transform;
import co.wangming.dragonfly.agent.transform.transformer.Transformer;
import net.bytebuddy.agent.builder.AgentBuilder;

/**
 * Transformer链抽象接口
 *
 * @author: wangming
 * @date: 2021/11/24
 */
public interface TransformerChain {

    /**
     * 扫描插件中所有被 {@link Transform} 注解的组件(该组件必须实现 {@link Transformer} 接口)，
     * 然后将其添加到transformer chain中，等待被 {@link TransformerChain#invoke(AgentBuilder.Default)} 调用。
     *
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    void build() throws InstantiationException, IllegalAccessException;

    /**
     * 调用 {@link Transformer#addTransform(AgentBuilder)} 方法, 调用 {@link AgentBuilder.Default} 添加 transformer
     *
     * @param builder
     * @return
     */
    AgentBuilder invoke(AgentBuilder.Default builder);

}
