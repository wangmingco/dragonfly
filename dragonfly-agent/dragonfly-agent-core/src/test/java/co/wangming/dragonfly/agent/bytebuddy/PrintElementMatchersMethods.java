package co.wangming.dragonfly.agent.bytebuddy;

import net.bytebuddy.matcher.ElementMatchers;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class PrintElementMatchersMethods {

    public static void main(String[] args) {
        Set<String> set = Arrays.stream(ElementMatchers.class.getMethods()).map(it -> it.getName()).collect(Collectors.toSet());
        for (String s : set) {
            String name = s.substring(0, 1).toUpperCase() + s.substring(1);
            System.out.println(" @Test\n" +
                    "    public void test" + name + "() {\n" +
                    "\n" +
                    "    }");
            System.out.println();
        }

    }
}
