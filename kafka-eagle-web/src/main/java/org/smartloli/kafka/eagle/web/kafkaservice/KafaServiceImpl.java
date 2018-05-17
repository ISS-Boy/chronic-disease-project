package org.smartloli.kafka.eagle.web.kafkaservice;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.smartloli.kafka.eagle.web.help.LimitQueue;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class KafaServiceImpl {

    private static Map<String,LimitQueue<String>> map = new ConcurrentHashMap<>();
    public static Map<String,LimitQueue<String>> getMap() {
        return map;
    }
    public static void startKafkaLalo() {
        new Input().start();
    }
    static class Input extends Thread {
        @Override
        public void run() {
            Properties props = new Properties();
            //配置kafka集群的broker地址，建议配置两个以上，以免其中一个失效，但不需要配全，集群会自动查找leader节点。
            props.put("bootstrap.servers", "192.168.222.226:9092");
            props.put("group.id", "chronic-lalo-consumer-33");
            props.put("schema.registry.url", "http://192.168.222.226:8081");
            props.put("enable.auto.commit", true);
            //配置value的序列化类
            //key的序列化类key.serializer.class可以单独配置，默认使用value的序列化类
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
            //配置partitionner选择策略，可选配置
            props.put("auto.offset.reset", "latest");
            // 消费来自atitude-longitude的数据
            KafkaConsumer<String, GenericRecord> kafkaConsumer = new KafkaConsumer(props);
            List<String> mTopics = new ArrayList<>();
            mTopics.addAll(Arrays.asList("latitude-longitude"));
            kafkaConsumer.subscribe(mTopics);
            while (true) {
                ConsumerRecords<String, GenericRecord> records = kafkaConsumer.poll(1000);
                for (ConsumerRecord<String, GenericRecord> record : records) {
                    if(!map.containsKey(record.key())){
                        LimitQueue<String> queue = new LimitQueue<String>();
                        map.put(record.key(),queue);
                        queue.offer(record.value().get(1)+","+record.value().get(2));
                    }
                    else{
                        map.get(record.key()).offer(record.value().get(1)+","+record.value().get(2));
                    }
                }
            }
        }

    }
}





