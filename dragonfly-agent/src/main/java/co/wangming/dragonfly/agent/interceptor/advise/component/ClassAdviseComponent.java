package co.wangming.dragonfly.agent.interceptor.advise.component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ClassAdviseComponent {

    int order() default -1;

}
