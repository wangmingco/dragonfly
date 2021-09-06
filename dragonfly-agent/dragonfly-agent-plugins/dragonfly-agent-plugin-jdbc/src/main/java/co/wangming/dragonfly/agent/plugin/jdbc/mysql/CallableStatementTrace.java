package co.wangming.dragonfly.agent.plugin.jdbc.mysql;

import co.wangming.dragonfly.agent.interceptor.advise.TraceMethodAdvise;
import co.wangming.dragonfly.agent.util.Matcher;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class CallableStatementTrace extends TraceMethodAdvise {

    @Override
    protected Matcher.Matchable buildClassMatcher() {
        return Matcher.build().type(named("com.mysql.jdbc.CallableStatement"));
    }
}
