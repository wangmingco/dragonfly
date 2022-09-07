package co.wangming.dragonfly.agent.util;

import java.util.Arrays;
import java.util.List;

public class Constant {

    public static String dragonflyPackageName() {
        return "co.wangming.dragonfly.";
    }

    public static List<String> ignoreNameStartWith() {
        return Arrays.asList(dragonflyPackageName()
//                , "java."
//                , "javax."
                , "jdk."
                , "sun."
                , "com.sun."
                , "net.bytebuddy."
                , "ch.qos.logback."
                , "com.intellij."
                , "org.jetbrains."
                , "org.apache."
//              , "com.mysql.cj."  // TODO 栈溢出 ???
        );
    }
}
