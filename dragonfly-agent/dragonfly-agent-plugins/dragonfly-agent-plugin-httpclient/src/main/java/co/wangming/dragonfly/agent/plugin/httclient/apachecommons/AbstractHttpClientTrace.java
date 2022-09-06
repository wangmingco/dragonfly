package co.wangming.dragonfly.agent.plugin.httclient.apachecommons;

import co.wangming.dragonfly.agent.plugin.httclient.HttpClientTraceTransformer;

public abstract class AbstractHttpClientTrace extends HttpClientTraceTransformer {

    public String packageName() {
        return "org.apache.commons.httpclient.";
    }

}
