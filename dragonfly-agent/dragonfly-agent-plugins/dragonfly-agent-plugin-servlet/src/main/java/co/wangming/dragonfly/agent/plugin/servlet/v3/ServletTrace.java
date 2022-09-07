package co.wangming.dragonfly.agent.plugin.servlet.v3;

import co.wangming.dragonfly.agent.plugin.servlet.ServletTraceTransformer;

public abstract class ServletTrace extends ServletTraceTransformer {

    public String packageName() {
        return "javax.servlet.http.";
    }

}
