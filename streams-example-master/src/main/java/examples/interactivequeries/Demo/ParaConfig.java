package examples.interactivequeries.Demo;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;

import java.util.Collections;
import java.util.Map;

//kafka stream配置项放在这里
public class ParaConfig {
    public static final String TOPIC1 = "blood-pressure";
    public static final String TOPIC2 = "heart-rate";
    public static final String TOPIC3 = "body-temperature";
    public static final String TOPIC4 = "step-count";

    public static final String bootstrapServers = "192.168.222.226:9092";
    public static final String schemaRegistryUrl = "http://192.168.222.226:8081";
    public static final String zk = "192.168.222.229:2181";

    public static final Map<String, String> serdeConfig = Collections.singletonMap(
            AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

}
