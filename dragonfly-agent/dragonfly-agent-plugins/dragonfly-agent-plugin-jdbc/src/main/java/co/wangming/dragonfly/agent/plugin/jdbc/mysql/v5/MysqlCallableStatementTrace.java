package co.wangming.dragonfly.agent.plugin.jdbc.mysql.v5;

import co.wangming.dragonfly.agent.transform.transformer.TraceTransformer;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.named;

//@Transform
public class MysqlCallableStatementTrace extends TraceTransformer {

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        return named("com.mysql.jdbc.CallableStatement");
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return named("execute")
                .or(named("executeQuery"))
                .or(named("executeUpdate"));
    }
}
