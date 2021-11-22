package co.wangming.dragonfly.agent.util;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class ClassUtil {

    public static Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(Constant.dragonflyPackageName());
        return reflections.getTypesAnnotatedWith(annotation);
    }
}
