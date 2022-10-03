package co.wangming.dragonfly.agent.plugin.jdbc.mysql.v5;

import co.wangming.dragonfly.agent.core.transform.transformer.Transform;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.named;

@Transform
public class MysqlCallableStatementTrace extends MysqlV5TraceTransformer {

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        return named(packageName() + "CallableStatement");
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return named("execute")
                .or(named("executeQuery"))
                .or(named("executeUpdate"));
    }
}
