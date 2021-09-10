package co.wangming.dragonfly.agent.plugin.jdbc.mysql.v5;

import co.wangming.dragonfly.agent.bytebuddy.TypeMatcher;
import co.wangming.dragonfly.agent.interceptor.advise.TraceMethodAdvise;
import co.wangming.dragonfly.agent.interceptor.advise.component.ClassAdviseComponent;

import static net.bytebuddy.matcher.ElementMatchers.named;

@ClassAdviseComponent()
public class MysqlPreparedStatementTrace extends TraceMethodAdvise {

    @Override
    protected TypeMatcher buildMatcher() {
        return TypeMatcher.of(named("com.mysql.jdbc.PreparedStatement"))
                .method(named("execute"))
                .or(named("executeQuery"))
                .or(named("executeUpdate"))
                .or(named("executeLargeUpdate"))
                .getTypeMatcher();
    }

}
