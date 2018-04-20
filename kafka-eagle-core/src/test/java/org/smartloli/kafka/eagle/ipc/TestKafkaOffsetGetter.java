/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartloli.kafka.eagle.ipc;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.protocol.types.Schema;
import org.apache.kafka.common.protocol.types.Struct;
import org.apache.kafka.common.protocol.types.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartloli.kafka.eagle.common.protocol.offsets.KeyAndValueSchemasInfo;
import org.smartloli.kafka.eagle.common.protocol.offsets.MessageValueStructAndVersionInfo;
import org.smartloli.kafka.eagle.common.util.KConstants;
import org.smartloli.kafka.eagle.common.util.SystemConfigUtils;
import org.smartloli.kafka.eagle.core.factory.KafkaFactory;
import org.smartloli.kafka.eagle.core.factory.KafkaService;

import kafka.common.OffsetAndMetadata;
import kafka.common.OffsetMetadata;
import kafka.common.Topic;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.coordinator.GroupTopicPartition;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

/**
 * New offset storage formats: kafka
 * 
 * @author smartloli.
 *
 *         Created by Jan 3, 2017
 */
public class TestKafkaOffsetGetter extends Thread {

	private final static Logger LOG = LoggerFactory.getLogger(TestKafkaOffsetGetter.class);

	/** Consumer offsets in kafka topic. */
	private final static String CONSUMER_OFFSET_TOPIC = KConstants.Kafka.CONSUMER_OFFSET_TOPIC;

	/** Multi cluster information. */
	public static Map<String, Map<GroupTopicPartition, OffsetAndMetadata>> multiKafkaConsumerOffsets = new ConcurrentHashMap<>();

	/** Kafka brokers */
	private KafkaService kafkaService = new KafkaFactory().create();

	/** ============================ Start Filter ========================= */
	private static Schema OFFSET_COMMIT_KEY_SCHEMA_V0 = new Schema(new Field("group", Type.STRING), new Field("topic", Type.STRING), new Field("partition", Type.INT32));
	private static Field KEY_GROUP_FIELD = OFFSET_COMMIT_KEY_SCHEMA_V0.get("group");
	private static Field KEY_TOPIC_FIELD = OFFSET_COMMIT_KEY_SCHEMA_V0.get("topic");
	private static Field KEY_PARTITION_FIELD = OFFSET_COMMIT_KEY_SCHEMA_V0.get("partition");

	private static Schema OFFSET_COMMIT_VALUE_SCHEMA_V0 = new Schema(new Field("offset", Type.INT64), new Field("metadata", Type.STRING, "Associated metadata.", ""),
			new Field("timestamp", Type.INT64));

	private static Schema OFFSET_COMMIT_VALUE_SCHEMA_V1 = new Schema(new Field("offset", Type.INT64), new Field("metadata", Type.STRING, "Associated metadata.", ""),
			new Field("commit_timestamp", Type.INT64), new Field("expire_timestamp", Type.INT64));

	private static Field VALUE_OFFSET_FIELD_V0 = OFFSET_COMMIT_VALUE_SCHEMA_V0.get("offset");
	private static Field VALUE_METADATA_FIELD_V0 = OFFSET_COMMIT_VALUE_SCHEMA_V0.get("metadata");
	private static Field VALUE_TIMESTAMP_FIELD_V0 = OFFSET_COMMIT_VALUE_SCHEMA_V0.get("timestamp");

	private static Field VALUE_OFFSET_FIELD_V1 = OFFSET_COMMIT_VALUE_SCHEMA_V1.get("offset");
	private static Field VALUE_METADATA_FIELD_V1 = OFFSET_COMMIT_VALUE_SCHEMA_V1.get("metadata");
	private static Field VALUE_COMMIT_TIMESTAMP_FIELD_V1 = OFFSET_COMMIT_VALUE_SCHEMA_V1.get("commit_timestamp");
	/** ============================ End Filter ========================= */

	/** Kafka offset memory in schema. */
	@SuppressWarnings("serial")
	private static Map<Integer, KeyAndValueSchemasInfo> OFFSET_SCHEMAS = new HashMap<Integer, KeyAndValueSchemasInfo>() {
		{
			KeyAndValueSchemasInfo ks0 = new KeyAndValueSchemasInfo();
			ks0.setKeySchema(OFFSET_COMMIT_KEY_SCHEMA_V0);
			ks0.setValueSchema(OFFSET_COMMIT_VALUE_SCHEMA_V0);
			put(0, ks0);

			KeyAndValueSchemasInfo ks1 = new KeyAndValueSchemasInfo();
			ks1.setKeySchema(OFFSET_COMMIT_KEY_SCHEMA_V0);
			ks1.setValueSchema(OFFSET_COMMIT_VALUE_SCHEMA_V1);
			put(1, ks1);
		}
	};

	/** Listening offset thread method. */
	private static synchronized void startOffsetListener(String clusterAlias, ConsumerConnector consumerConnector) {
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(CONSUMER_OFFSET_TOPIC, new Integer(1));
		KafkaStream<byte[], byte[]> offsetMsgStream = consumerConnector.createMessageStreams(topicCountMap).get(CONSUMER_OFFSET_TOPIC).get(0);

		ConsumerIterator<byte[], byte[]> it = offsetMsgStream.iterator();
		while (true) {
			MessageAndMetadata<byte[], byte[]> offsetMsg = it.next();
			if (ByteBuffer.wrap(offsetMsg.key()).getShort() < 2) {
				try {
					GroupTopicPartition commitKey = readMessageKey(ByteBuffer.wrap(offsetMsg.key()));
					if (offsetMsg.message() == null) {
						continue;
					}
					OffsetAndMetadata commitValue = readMessageValue(ByteBuffer.wrap(offsetMsg.message()));
					if (multiKafkaConsumerOffsets.containsKey(clusterAlias)) {
						multiKafkaConsumerOffsets.get(clusterAlias).put(commitKey, commitValue);
					} else {
						Map<GroupTopicPartition, OffsetAndMetadata> kafkaConsumerOffsets = new ConcurrentHashMap<>();
						kafkaConsumerOffsets.put(commitKey, commitValue);
						multiKafkaConsumerOffsets.put(clusterAlias, kafkaConsumerOffsets);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static synchronized void startOffsetSASLListener(String clusterAlias, KafkaConsumer<String, String> consumer) {
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(100);
			for (ConsumerRecord<String, String> offsetMsg : records) {
				if (ByteBuffer.wrap(offsetMsg.key().getBytes()).getShort() < 2) {
					try {
						GroupTopicPartition commitKey = readMessageKey(ByteBuffer.wrap(offsetMsg.key().getBytes()));
						if (offsetMsg.value().getBytes() == null) {
							continue;
						}
						OffsetAndMetadata commitValue = readMessageValue(ByteBuffer.wrap(offsetMsg.value().getBytes()));
						if (multiKafkaConsumerOffsets.containsKey(clusterAlias)) {
							multiKafkaConsumerOffsets.get(clusterAlias).put(commitKey, commitValue);
						} else {
							Map<GroupTopicPartition, OffsetAndMetadata> kafkaConsumerOffsets = new ConcurrentHashMap<>();
							kafkaConsumerOffsets.put(commitKey, commitValue);
							multiKafkaConsumerOffsets.put(clusterAlias, kafkaConsumerOffsets);
						}
						System.out.println(multiKafkaConsumerOffsets.toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/** Get instance K&V schema. */
	private static KeyAndValueSchemasInfo schemaFor(int version) {
		return OFFSET_SCHEMAS.get(version);
	}

	/** Analysis of Kafka data in topic in buffer. */
	private static GroupTopicPartition readMessageKey(ByteBuffer buffer) {
		short version = buffer.getShort();
		Schema keySchema = schemaFor(version).getKeySchema();
		Struct key = (Struct) keySchema.read(buffer);
		String group = key.getString(KEY_GROUP_FIELD);
		String topic = key.getString(KEY_TOPIC_FIELD);
		int partition = key.getInt(KEY_PARTITION_FIELD);
		return new GroupTopicPartition(group, new TopicPartition(topic, partition));
	}

	/** Analysis of buffer data in metadata in Kafka. */
	private static OffsetAndMetadata readMessageValue(ByteBuffer buffer) {
		MessageValueStructAndVersionInfo structAndVersion = readMessageValueStruct(buffer);
		if (structAndVersion.getValue() == null) {
			return null;
		} else {
			if (structAndVersion.getVersion() == 0) {
				long offset = structAndVersion.getValue().getLong(VALUE_OFFSET_FIELD_V0);
				String metadata = structAndVersion.getValue().getString(VALUE_METADATA_FIELD_V0);
				long timestamp = structAndVersion.getValue().getLong(VALUE_TIMESTAMP_FIELD_V0);
				return new OffsetAndMetadata(new OffsetMetadata(offset, metadata), timestamp, timestamp);
			} else if (structAndVersion.getVersion() == 1) {
				long offset = structAndVersion.getValue().getLong(VALUE_OFFSET_FIELD_V1);
				String metadata = structAndVersion.getValue().getString(VALUE_METADATA_FIELD_V1);
				long commitTimestamp = structAndVersion.getValue().getLong(VALUE_COMMIT_TIMESTAMP_FIELD_V1);
				return new OffsetAndMetadata(new OffsetMetadata(offset, metadata), commitTimestamp, commitTimestamp);
			} else {
				throw new IllegalStateException("Unknown offset message version: " + structAndVersion.getVersion());
			}
		}
	}

	/** Analysis of struct data structure in metadata in Kafka. */
	private static MessageValueStructAndVersionInfo readMessageValueStruct(ByteBuffer buffer) {
		MessageValueStructAndVersionInfo mvs = new MessageValueStructAndVersionInfo();
		if (buffer == null) {
			mvs.setValue(null);
			mvs.setVersion(Short.valueOf("-1"));
		} else {
			short version = buffer.getShort();
			Schema valueSchema = schemaFor(version).getValueSchema();
			Struct value = (Struct) valueSchema.read(buffer);
			mvs.setValue(value);
			mvs.setVersion(version);
		}
		return mvs;
	}

	static {
		LOG.info("Initialize KafkaOffsetGetter clazz.");
//		TestKafkaOffsetGetter kafka = new TestKafkaOffsetGetter();
//		kafka.start();
	}

	/** Instance KafkaOffsetGetter clazz. */
	public static void getInstance() {
		LOG.info(TestKafkaOffsetGetter.class.getName());
	}

	public static void main(String[] args) {
		getInstance();
		TestKafkaOffsetGetter kafka = new TestKafkaOffsetGetter();
		kafka.run();
	}

	/** Run method for running thread. */
	@Override
	public void run() {
		String[] clusterAliass = SystemConfigUtils.getPropertyArray("kafka.eagle.zk.cluster.alias", ",");
		for (String clusterAlias : clusterAliass) {
			Properties props = new Properties();
			if (SystemConfigUtils.getBooleanProperty("kafka.eagle.sasl.enable")) {
				props.put("group.id", "kafka.eagle.sasl.system.group");
				props.put("exclude.internal.topics", "false");
				props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, kafkaService.getKafkaBrokerServer(clusterAlias));
				props.put("enable.auto.commit", "true");
				props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
				props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
				
				props.put("security.protocol", "SASL_PLAINTEXT");
				props.put("sasl.mechanism", "PLAIN");
				
				KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
				consumer.subscribe(Arrays.asList(Topic.GroupMetadataTopicName()));
				startOffsetSASLListener(clusterAlias, consumer);
			} else {
				String zk = SystemConfigUtils.getProperty(clusterAlias + ".zk.list");
				props.put("group.id", "kafka.eagle.system.group");
				props.put("exclude.internal.topics", "false");
				props.put("zookeeper.connect", zk);
				ConsumerConnector consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
				startOffsetListener(clusterAlias, consumer);
			}
		}
	}

}
