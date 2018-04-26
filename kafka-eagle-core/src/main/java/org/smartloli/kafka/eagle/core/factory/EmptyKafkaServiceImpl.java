package org.smartloli.kafka.eagle.core.factory;

import org.smartloli.kafka.eagle.common.protocol.DisplayInfo;
import org.smartloli.kafka.eagle.common.protocol.KafkaSqlInfo;
import org.smartloli.kafka.eagle.common.protocol.MetadataInfo;
import org.smartloli.kafka.eagle.common.protocol.OffsetZkInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by dujijun on 2018/4/26.
 */
public class EmptyKafkaServiceImpl implements KafkaService{
    @Override
    public boolean findTopicAndGroupExist(String clusterAlias, String topic, String group) {
        return false;
    }

    @Override
    public List<String> findTopicPartition(String clusterAlias, String topic) {
        return null;
    }

    @Override
    public Map<String, List<String>> getActiveTopic(String clusterAlias) {
        return null;
    }

    @Override
    public String getAllBrokersInfo(String clusterAlias) {
        return null;
    }

    @Override
    public String getAllPartitions(String clusterAlias) {
        return null;
    }

    @Override
    public Map<String, List<String>> getConsumers(String clusterAlias) {
        return null;
    }

    @Override
    public Map<String, List<String>> getConsumers(String clusterAlias, DisplayInfo page) {
        return null;
    }

    @Override
    public long getLogSize(List<String> hosts, String topic, int partition) {
        return 0;
    }

    @Override
    public OffsetZkInfo getOffset(String clusterAlias, String topic, String group, int partition) {
        return null;
    }

    @Override
    public String getKafkaOffset(String clusterAlias) {
        return null;
    }

    @Override
    public Map<String, Object> create(String clusterAlias, String topicName, String partitions, String replic) {
        return null;
    }

    @Override
    public Map<String, Object> delete(String clusterAlias, String topicName) {
        return null;
    }

    @Override
    public List<MetadataInfo> findLeader(String clusterAlias, String topic) {
        return null;
    }

    @Override
    public KafkaSqlInfo parseSql(String clusterAlias, String sql) {
        return null;
    }

    @Override
    public Set<String> getKafkaActiverTopics(String clusterAlias, String group) {
        return null;
    }

    @Override
    public String getKafkaConsumer(String clusterAlias) {
        return null;
    }

    @Override
    public String getKafkaActiverSize(String clusterAlias, String group) {
        return null;
    }

    @Override
    public String getKafkaBrokerServer(String clusterAlias) {
        return null;
    }

    @Override
    public int getKafkaConsumerGroups(String clusterAlias) {
        return 0;
    }

    @Override
    public Set<String> getKafkaConsumerTopic(String clusterAlias, String group) {
        return null;
    }

    @Override
    public String getKafkaConsumerGroupTopic(String clusterAlias, String group) {
        return null;
    }

    @Override
    public long getKafkaLogSize(String clusterAlias, String topic, int partitionid) {
        return 0;
    }

    @Override
    public List<MetadataInfo> findKafkaLeader(String clusterAlias, String topic) {
        return null;
    }

    @Override
    public boolean mockMessage(String clusterAlias, String topic, String message) {
        return false;
    }
}
