package co.wangming.dragonfly.agent.plugin.thread;

import co.wangming.dragonfly.agent.adaptor.Adaptor;
import co.wangming.dragonfly.agent.adaptor.MethodAdviseAdaptor;
import co.wangming.dragonfly.agent.advise.CatchExceptionAdvise;
import co.wangming.dragonfly.agent.transform.transformer.AbstractAdviseTransformer;
import co.wangming.dragonfly.agent.transform.transformer.Transform;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import java.util.concurrent.*;

import static net.bytebuddy.matcher.ElementMatchers.*;


@Transform
public class ThreadTraceTransformer extends AbstractAdviseTransformer {

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        ElementMatcher.Junction<TypeDescription> matchedClass =
                hasSuperType(named(ExecutorService.class.getCanonicalName()))
                .or(hasSuperType(named(CompletionService.class.getCanonicalName())));

        return any()
                .and(matchedClass)
                .and(not(isInterface()))
                .and(not(isAbstract()))
                ;
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return isMethod()
                .and(named("submit")
                        .or(named("execute"))
                        .or(named("schedule"))
                        .or(named("scheduleAtFixedRate"))
                        .or(named("scheduleWithFixedDelay"))
                        .or(named("invoke"))
                )
                .and(takesArgument(0, Runnable.class)
                        .or(takesArgument(0, Callable.class))
                        .or(takesArgument(0, ForkJoinTask.class))
                )
                ;
    }

    @Override
    public Adaptor adaptor() {
        return new MethodAdviseAdaptor(this, new ThreadTraceAdvise(this), new CatchExceptionAdvise(this));
    }

}
