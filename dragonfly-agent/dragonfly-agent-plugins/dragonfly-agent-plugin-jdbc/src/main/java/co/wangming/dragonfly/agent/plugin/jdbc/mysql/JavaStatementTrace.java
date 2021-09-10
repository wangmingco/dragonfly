package co.wangming.dragonfly.agent.plugin.jdbc.mysql;

import co.wangming.dragonfly.agent.bytebuddy.TypeMatcher;
import co.wangming.dragonfly.agent.interceptor.advise.TraceMethodAdvise;
import co.wangming.dragonfly.agent.interceptor.advise.component.ClassAdviseComponent;

import java.sql.Statement;

import static net.bytebuddy.matcher.ElementMatchers.isSubTypeOf;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;

@ClassAdviseComponent
public class JavaStatementTrace extends TraceMethodAdvise {

    @Override
    protected TypeMatcher buildMatcher() {
        return TypeMatcher.of(isSubTypeOf(Statement.class))
                .method(nameStartsWith("execute"))
                .getTypeMatcher();
    }
}
