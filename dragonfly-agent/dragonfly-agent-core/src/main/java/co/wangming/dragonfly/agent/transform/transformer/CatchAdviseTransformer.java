package co.wangming.dragonfly.agent.transform.transformer;

import co.wangming.dragonfly.agent.adaptor.Adaptor;
import co.wangming.dragonfly.agent.adaptor.MethodAdviseAdaptor;
import co.wangming.dragonfly.agent.advise.CatchExceptionAdvise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link Transformer} 的trace实现。
 * <p>
 * 该类主要是为 {@link AbstractAdviseTransformer#adaptor()} 返回一个trace实现。
 *
 * @author: wangming
 * @date: 2021/11/24
 */
public abstract class CatchAdviseTransformer extends AbstractAdviseTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatchAdviseTransformer.class);

    @Override
    public Adaptor adaptor() {
        return new MethodAdviseAdaptor(new CatchExceptionAdvise());
    }

}
