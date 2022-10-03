package co.wangming.dragonfly.agent.core.transform.transformer;

import java.lang.annotation.*;

/**
 * 扩展组件使用该注解
 *
 * @author: wangming
 * @date: 2021/11/24
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Transform {
}
