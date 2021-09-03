package co.wangming.dragonfly.agent.interceptor;

import co.wangming.dragonfly.agent.interceptor.advise.AdviseContext;
import co.wangming.dragonfly.agent.interceptor.advise.MatchableMethodAdvise;
import co.wangming.dragonfly.agent.interceptor.advise.MethodAdvise;
import co.wangming.dragonfly.agent.interceptor.advise.component.ClassAdviseComponent;
import co.wangming.dragonfly.agent.util.ClassUtil;
import net.bytebuddy.implementation.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class MethodAdviseInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodAdviseInterceptor.class);

    private static List<MethodAdviseWrapper> adviseList;

    static {
        init();
    }

    private static void init() {
        LOGGER.debug("构建 MethodAdviseInterceptor");

        Set<Class<?>> classes = ClassUtil.getTypesAnnotatedWith(ClassAdviseComponent.class);
        if (classes == null || classes.size() == 0) {
            LOGGER.warn("没有找到 AdviseComponent ");
            return;
        }

        String names = classes.stream().map(it -> it.getName()).collect(Collectors.joining(","));
        LOGGER.debug("找到 AdviseComponent 列表:{}", names);

        List<MethodAdviseWrapper> methodAdviseList = new ArrayList<>();

        for (Class<?> aClass : classes) {
            ClassAdviseComponent classAdviseComponent = aClass.getAnnotation(ClassAdviseComponent.class);
            try {
                MethodAdviseWrapper methodAdviseWrapper = new MethodAdviseWrapper();
                methodAdviseWrapper.methodAdvise = (MethodAdvise) aClass.newInstance();
                methodAdviseWrapper.order = classAdviseComponent.order();
                methodAdviseList.add(methodAdviseWrapper);
            } catch (InstantiationException e) {
                LOGGER.error("{} 实例化异常", aClass.getName(), e);
                break;
            } catch (IllegalAccessException e) {
                LOGGER.error("{} 实例化异常", aClass.getName(), e);
                break;
            }
        }

        Collections.sort(methodAdviseList, Comparator.comparing(MethodAdviseWrapper::order));

        names = methodAdviseList.stream().map(it -> it.methodAdvise.getClass().getName()).collect(Collectors.joining(","));
        LOGGER.debug("排序AdviseComponent列表:{}", names);

        adviseList = methodAdviseList;
    }

    public static class MethodAdviseWrapper {
        MethodAdvise methodAdvise;
        int order;

        public int order() {
            return order;
        }

        public MethodAdvise getMethodAdvise() {
            return methodAdvise;
        }
    }

    @RuntimeType
    public static Object intercept(@Origin Class clazz, @Origin Method method, @This Object thisObj, @AllArguments Object[] allArguments, @SuperCall Callable callable) throws Exception {

        LOGGER.debug("{}#{} 开始执行intercept", clazz.getName(), method.getName());

        if (adviseList == null || adviseList.size() == 0) {
            LOGGER.warn("adviseList 为空");
            return callable.call();
        }

        final Map<String, AdviseContext> contextMap = new HashMap<>();

        for (MethodAdviseWrapper methodAdviseWrapper : adviseList) {

            if (mactchClass(clazz, methodAdviseWrapper.methodAdvise)) {
                continue;
            }

            AdviseContext adviseContext = AdviseContext.enterBefore();
            try {
                methodAdviseWrapper.methodAdvise.beforeExec(clazz, method, thisObj, allArguments, adviseContext);
                contextMap.put(methodAdviseWrapper.methodAdvise.getClass().getCanonicalName(), adviseContext);
            } catch (Exception e) {
                LOGGER.error("方法增强 {} beforeExec 发生异常", methodAdviseWrapper.methodAdvise.toString(), e);
            } finally {
                adviseContext.exitBefore();
            }
        }

        try {
            return callable.call();
        } catch (Exception e) {
            for (MethodAdviseWrapper methodAdviseWrapper : adviseList) {

                if (mactchClass(clazz, methodAdviseWrapper.methodAdvise)) {
                    continue;
                }

                AdviseContext context = contextMap.get(methodAdviseWrapper.methodAdvise.getClass().getCanonicalName());
                context.enterException();
                try {
                    methodAdviseWrapper.methodAdvise.exceptionOnExec(clazz, method, thisObj, allArguments, context);
                } catch (Exception e1) {
                    LOGGER.error("方法增强 {} exceptionOnExec 发生异常", methodAdviseWrapper.methodAdvise.toString(), e);
                } finally {
                    context.exitException();
                }
            }
            throw e;
        } finally {
            for (MethodAdviseWrapper methodAdviseWrapper : adviseList) {

                if (mactchClass(clazz, methodAdviseWrapper.methodAdvise)) {
                    continue;
                }

                AdviseContext context = contextMap.get(methodAdviseWrapper.methodAdvise.getClass().getCanonicalName());
                context.enterAfter();
                try {
                    methodAdviseWrapper.methodAdvise.afterExec(clazz, method, thisObj, allArguments, context);
                } catch (Exception e1) {
                    LOGGER.error("方法增强 {} afterExec 发生异常", methodAdviseWrapper.methodAdvise.toString(), e1);
                } finally {
                    context.exitAfter();
                }
            }
            LOGGER.debug("{}#{} intercept执行完成", clazz.getName(), method.getName());
        }

    }

    private static boolean mactchClass(Class clazz, MethodAdvise methodAdvise) {
        if (methodAdvise instanceof MatchableMethodAdvise &&
                !((MatchableMethodAdvise) methodAdvise).matches(clazz)) {
            return true;
        }
        return false;
    }


}
