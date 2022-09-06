package co.wangming.dragonfly.agent.transform.transformer;

import co.wangming.dragonfly.agent.adaptor.Adaptor;
import co.wangming.dragonfly.agent.adaptor.MethodAdviseAdaptor;
import co.wangming.dragonfly.agent.advise.CatchExceptionAdvise;
import co.wangming.dragonfly.agent.common.Config;
import co.wangming.dragonfly.agent.util.Constant;
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
public class OnlyCatchAdviseTransformer extends AbstractAdviseTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OnlyCatchAdviseTransformer.class);

    @Override
    public Adaptor adaptor() {
        return new MethodAdviseAdaptor(this, new CatchExceptionAdvise());
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
