package co.wangming.dragonfly.agent.core.util;

import co.wangming.dragonfly.agent.core.common.Config;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class ClassUtil {

    public static Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(Config.dragonflyPackageName());
        return reflections.getTypesAnnotatedWith(annotation);
    }
}
