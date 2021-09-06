package co.wangming.dragonfly.lib.zipkin;

import zipkin2.Span;
import zipkin2.codec.SpanBytesEncoder;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.kafka.KafkaSender;
import zipkin2.reporter.okhttp3.OkHttpSender;

import java.util.Map;

public enum ZipkinReporter {

    DEFAULE("KAFKA", "127.0.0.1:9411"),
    HTTP("HTTP", "http://127.0.0.1:9411/api/v2/spans"),
    KAFKA("KAFKA", "127.0.0.1:9411");

    ZipkinReporter(String type, String IP) {
        Sender sender;
        switch (type) {
            case "KAFKA": {
                sender = KafkaSender.create(IP);
                break;
            }
            case "HTTP": {
                sender = OkHttpSender.create(IP);
                break;
            }
            default: {
                return;
            }
        }
        asyncReporter = AsyncReporter.create(sender);
    }

    private AsyncReporter asyncReporter;

    public void sendCS(String traceId, String parentTraceId, String name, Map data) {
        send(buildSpan("cs", traceId, parentTraceId, name, data));
    }

    public void sendCR(String traceId, String parentTraceId, String name, Map data) {
        send(buildSpan("cr", traceId, parentTraceId, name, data));
    }

    public void sendSS(String traceId, String parentTraceId, String name, Map data) {
        send(buildSpan("ss", traceId, parentTraceId, name, data));
    }

    public void sendSR(String traceId, String parentTraceId, String name, Map data) {
        send(buildSpan("sr", traceId, parentTraceId, name, data));
    }

    private void send(Span span) {
        asyncReporter.report(SpanBytesEncoder.JSON_V2.encode(span));
    }

    private Span buildSpan(String type, String traceId, String parentTraceId, String name, Map data) {

        long now = System.currentTimeMillis();

        Span.Builder builder = Span.newBuilder()
                .id(now)
                .traceId(traceId)
                .parentId(parentTraceId)
                .name(name)
                .timestamp(now)
//                .duration(1431)
                .kind(Span.Kind.SERVER)
//                .localEndpoint(Endpoint.newBuilder().ip("192.168.99.1").port(3306).serviceName("backend").build())
//                .remoteEndpoint(Endpoint.newBuilder().ip("172.19.0.2").port(58648).build())
                .addAnnotation(System.currentTimeMillis(), type);
        if (data == null || data.size() == 0) {
            return builder.build();
        }

        for (Object o : data.keySet()) {
            Object v = data.get(o);
            builder.putTag(o.toString(), v == null ? "" : v.toString());
        }
        return builder.build();
    }
}
