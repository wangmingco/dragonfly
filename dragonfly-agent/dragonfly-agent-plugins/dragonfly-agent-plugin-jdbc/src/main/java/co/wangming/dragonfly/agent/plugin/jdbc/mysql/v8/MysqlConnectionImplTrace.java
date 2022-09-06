package co.wangming.dragonfly.agent.plugin.jdbc.mysql.v8;

import co.wangming.dragonfly.agent.transform.transformer.Transform;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import static net.bytebuddy.matcher.ElementMatchers.named;

@Transform
public class MysqlConnectionImplTrace extends MysqlV8TraceTransformer {

    @Override
    public ElementMatcher.Junction<TypeDescription> typeConstraints() {
        return named(packageName() + "ConnectionImpl");
    }

    @Override
    public ElementMatcher.Junction<MethodDescription> methodConstraints() {
        return named("createNewIO")
                .or(named("executeQuery"))
                ;
    }
}
