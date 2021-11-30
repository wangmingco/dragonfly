package co.wangming.dragonfly.agent.transform.transformer;

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
public class DefaultTransformer extends TraceAdviseTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTransformer.class);

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        return not(nameContains("$auxiliary$")).
                and(not(nameStartsWith("co.wangming.dragonfly."))).
                and(not(nameStartsWith("java."))).
                and(not(nameStartsWith("sun."))).
                and(not(nameStartsWith("jdk."))).
                and(not(nameStartsWith("com.sun."))).
                and(not(nameStartsWith("net.bytebuddy."))).
                and(not(nameStartsWith("ch.qos.logback."))).
                and(not(nameStartsWith("com.intellij."))).
                and(not(nameStartsWith("org.jetbrains.")));
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return any().and(not(nameContains("$auxiliary$")));
    }

}
