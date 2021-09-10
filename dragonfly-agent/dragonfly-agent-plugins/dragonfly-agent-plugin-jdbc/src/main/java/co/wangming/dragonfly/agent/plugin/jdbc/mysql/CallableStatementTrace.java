package co.wangming.dragonfly.agent.plugin.jdbc.mysql;

import co.wangming.dragonfly.agent.bytebuddy.TypeMatcher;
import co.wangming.dragonfly.agent.interceptor.advise.TraceMethodAdvise;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class CallableStatementTrace extends TraceMethodAdvise {

    @Override
    protected TypeMatcher buildMatcher() {
        return TypeMatcher.of(named("com.mysql.jdbc.CallableStatement"));
    }
}
