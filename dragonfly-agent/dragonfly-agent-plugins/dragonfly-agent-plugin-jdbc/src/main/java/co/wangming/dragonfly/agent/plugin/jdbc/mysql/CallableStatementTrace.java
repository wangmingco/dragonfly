package co.wangming.dragonfly.agent.plugin.jdbc.mysql;

import co.wangming.dragonfly.agent.bytebuddy.Matcher;
import co.wangming.dragonfly.agent.interceptor.advise.TraceMethodAdvise;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class CallableStatementTrace extends TraceMethodAdvise {

    @Override
    protected Matcher buildClassMatcher() {
        return Matcher.of(named("com.mysql.jdbc.CallableStatement"));
    }
}
