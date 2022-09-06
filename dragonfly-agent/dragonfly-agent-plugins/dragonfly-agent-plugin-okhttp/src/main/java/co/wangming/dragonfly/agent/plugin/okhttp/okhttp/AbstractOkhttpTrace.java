package co.wangming.dragonfly.agent.plugin.okhttp.okhttp;

import co.wangming.dragonfly.agent.plugin.okhttp.HttpClientTraceTransformer;

public abstract class AbstractOkhttpTrace extends HttpClientTraceTransformer {

    public String packageName() {
        return "okhttp3.";
    }

}
