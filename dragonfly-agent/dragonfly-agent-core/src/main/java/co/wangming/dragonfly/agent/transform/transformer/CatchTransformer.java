package co.wangming.dragonfly.agent.transform.transformer;

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
public class CatchTransformer extends CatchAdviseTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatchTransformer.class);

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        ElementMatcher.Junction<TypeDescription> junction = not(nameContains("$auxiliary$"));

        for (String name : Constant.ignoreNameStartWith()) {
            junction = junction.and(not(nameStartsWith(name)));
        }

        return junction;
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return not(nameContains("$auxiliary$"));
    }

}
