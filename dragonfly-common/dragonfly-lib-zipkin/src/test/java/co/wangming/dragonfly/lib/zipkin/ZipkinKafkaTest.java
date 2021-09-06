package co.wangming.dragonfly.lib.zipkin;

import org.junit.Test;
import zipkin2.Endpoint;
import zipkin2.Span;
import zipkin2.codec.SpanBytesEncoder;
import zipkin2.reporter.kafka.KafkaSender;

import java.util.List;

import static java.util.Arrays.asList;

public class ZipkinKafkaTest {

    @Test
    public void send() throws Exception {

        try (KafkaSender sender = KafkaSender.create("127.0.0.1:9411")) {
            List<byte[]> spans = asList(SpanBytesEncoder.JSON_V2.encode(buildSpan()));
            sender.sendSpans(spans).execute();
        } catch (Exception e) {

        }

    }

    public static Span buildSpan() {
        long traceId = System.currentTimeMillis();

        return Span.newBuilder()
                .id("352bff9a74ca9ad2")
                .traceId(traceId + "")
                .parentId("6b221d5bc9e6496c")
                .name("get /api")
                .timestamp(1556604172355737l)
                .duration(1431)
                .kind(Span.Kind.SERVER)
                .localEndpoint(Endpoint.newBuilder().ip("192.168.99.1").port(3306).serviceName("backend").build())
                .remoteEndpoint(Endpoint.newBuilder().ip("172.19.0.2").port(58648).build())
                .addAnnotation(System.currentTimeMillis(), "cs")
                .build();
    }
}
