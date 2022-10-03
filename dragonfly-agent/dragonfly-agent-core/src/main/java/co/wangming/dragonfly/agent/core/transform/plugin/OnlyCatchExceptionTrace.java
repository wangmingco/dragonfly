package co.wangming.dragonfly.agent.core.transform.plugin;

import co.wangming.dragonfly.agent.core.transform.transformer.AbstractAdviseTransformer;
import co.wangming.dragonfly.agent.core.adaptor.Adaptor;
import co.wangming.dragonfly.agent.core.adaptor.MethodAdviseAdaptor;
import co.wangming.dragonfly.agent.core.advise.CatchExceptionAdvise;
import co.wangming.dragonfly.agent.core.common.Config;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * @author: wangming
 * @date: 2021/11/24
 */
public class OnlyCatchExceptionTrace extends AbstractAdviseTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OnlyCatchExceptionTrace.class);

    @Override
    public Adaptor adaptor() {
        return new MethodAdviseAdaptor(this, new CatchExceptionAdvise(this));
    }

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        return nameStartsWith(Config.commonCatchPackageName).and(not(nameContains("$auxiliary$")));
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return any();
    }

}
