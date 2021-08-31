package co.wangming.dragonfly.agent.interceptor.trace;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TraceComponent {

    String className = "";
}
