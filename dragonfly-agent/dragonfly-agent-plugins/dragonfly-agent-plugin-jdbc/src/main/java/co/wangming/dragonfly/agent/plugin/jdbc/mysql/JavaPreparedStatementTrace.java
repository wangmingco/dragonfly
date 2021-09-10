package co.wangming.dragonfly.agent.plugin.jdbc.mysql;

import co.wangming.dragonfly.agent.bytebuddy.TypeMatcher;
import co.wangming.dragonfly.agent.interceptor.advise.TraceMethodAdvise;
import co.wangming.dragonfly.agent.interceptor.advise.component.ClassAdviseComponent;

import java.sql.PreparedStatement;

import static net.bytebuddy.matcher.ElementMatchers.isSubTypeOf;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;

@ClassAdviseComponent
public class JavaPreparedStatementTrace extends TraceMethodAdvise {

    @Override
    protected TypeMatcher buildMatcher() {
        return TypeMatcher.of(isSubTypeOf(PreparedStatement.class))
                .method(nameStartsWith("execute"))
                .getTypeMatcher();
    }
}
