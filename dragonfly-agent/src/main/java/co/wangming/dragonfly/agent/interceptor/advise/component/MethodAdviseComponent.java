package co.wangming.dragonfly.agent.interceptor.advise.component;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MethodAdviseComponent {

}
