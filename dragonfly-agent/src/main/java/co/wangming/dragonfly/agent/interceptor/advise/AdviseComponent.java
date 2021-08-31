package co.wangming.dragonfly.agent.interceptor.advise;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AdviseComponent {

    int order = -1;

}
