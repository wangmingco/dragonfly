package co.wangming.dragonfly.agent.plugin.jdbc.mysql;

import co.wangming.dragonfly.agent.transform.Transform;
import co.wangming.dragonfly.agent.transform.transformer.TraceTransformer;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import java.sql.Statement;

import static net.bytebuddy.matcher.ElementMatchers.isSubTypeOf;
import static net.bytebuddy.matcher.ElementMatchers.nameStartsWith;

@Transform
public class JavaStatementTrace extends TraceTransformer {

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        return isSubTypeOf(Statement.class);
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return nameStartsWith("execute");
    }
}
