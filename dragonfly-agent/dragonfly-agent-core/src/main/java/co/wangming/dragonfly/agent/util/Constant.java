package co.wangming.dragonfly.agent.util;

import java.util.ArrayList;
import java.util.List;


public class Constant {

    private static final List<String> SKIP_PACKAGE = new ArrayList() {{
        add("co.wangming.dragonfly.agent.");
        add("java.");
        add("sun.");
        add("jdk.");
        add("com.sun.");
        add("net.bytebuddy.");
        add("com.intellij.");
        add("org.jetbrains.");
    }};

    public static List<String> skipPackages() {
        return SKIP_PACKAGE;
    }

    public static String dragonflyPackageName() {
        return "co.wangming.dragonfly.agent";
    }
}
