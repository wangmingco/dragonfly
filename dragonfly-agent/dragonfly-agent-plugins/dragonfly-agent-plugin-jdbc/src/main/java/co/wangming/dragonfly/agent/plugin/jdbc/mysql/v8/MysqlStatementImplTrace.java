package co.wangming.dragonfly.agent.plugin.jdbc.mysql.v8;

import co.wangming.dragonfly.agent.transform.transformer.Transform;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.named;

@Transform
public class MysqlStatementImplTrace extends MysqlV8TraceTransformer {

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        return named(packageName() + "StatementImpl");
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return named("execute")
                .or(named("executeQuery"))
                .or(named("executeUpdate"))
                .or(named("executeLargeUpdate"))
                .or(named("executeBatchInternal"))
                .or(named("executeUpdateInternal"))
                .or(named("executeBatch"));
    }
}
