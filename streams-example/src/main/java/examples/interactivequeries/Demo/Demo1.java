package examples.interactivequeries.Demo;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import kafka.admin.AdminUtils;
import kafka.utils.ZkUtils;
import net.seninp.jmotif.sax.NumerosityReductionStrategy;
import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.SAXProcessor;
import net.seninp.jmotif.sax.alphabet.NormalAlphabet;
import net.seninp.jmotif.sax.datastructure.SAXRecords;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.security.JaasUtils;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.*;
import org.mhealth.open.data.avro.Demo;
import org.mhealth.open.data.avro.MEvent;
import org.mhealth.open.data.avro.Measure;
import org.mhealth.open.data.avro.SPatient;
import org.rocksdb.Options;

import java.io.*;
import java.nio.file.Files;
import java.io.Serializable;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;


public class Demo1 {

    static final String TOPIC1 = "blood-pressure";
    static final String TOPIC2 = "heart-rate";
    static final String TOPIC3 = "body-temperature";
    static final String TOPIC4 = "step-count";
    static final String TOPIC5 = "sleep-duration";
    static final String TOPIC6 = "body-fat-percentage";
    static final String TOPIC7 = "patient";
    static final String TOPIC8 = "careplans";

    public static final String PROFILE_STORE_NAME = "profile.store";
    public static final String SEARCH_STORE_NAME = "search.store";


    public static class CustomRocksDBConfig implements RocksDBConfigSetter {

        @Override
        public void setConfig(final String storeName, final Options options, final Map<String, Object> configs) {
            // Workaround: We must ensure that the parallelism is set to >= 2.  There seems to be a known
            // issue with RocksDB where explicitly setting the parallelism to 1 causes issues (even though
            // 1 seems to be RocksDB's default for this configuration).
            int compactionParallelism = Math.max(Runtime.getRuntime().availableProcessors(), 2);
            // Set number of compaction threads (but not flush threads).
            options.setIncreaseParallelism(compactionParallelism);
        }
    }

    public static void main(String[] args) throws Exception {
        final int restEndpointPort = 8080;
        final String bootstrapServers = "192.168.222.226:9092";
        final String schemaRegistryUrl = "http://192.168.222.226:8081";
        //final String restEndpointHostname = "localhost";      做交互式查询的
        final String zk = "192.168.222.229:2181";

        System.out.println("Connecting to Kafka cluster via bootstrap servers " + bootstrapServers);
        System.out.println("Connecting to Confluent schema registry at " + schemaRegistryUrl);
        //System.out.println("REST endpoint at http://" + restEndpointHostname + ":" + restEndpointPort);


        //删除topic
//        ZkUtils zkUtils = ZkUtils.apply(zk, 30000, 30000, JaasUtils.isZkSecurityEnabled());
//        AdminUtils.deleteTopic(zkUtils, "interactive-queries-example_1-search.store-changelog");
//        if (zkUtils != null) {
//            zkUtils.close();
//        }

//        //删除state store所在的文件夹
//        delAllFiles(new File("/tmp/streams-example"));

        final KafkaStreams streams = createStreams(bootstrapServers,
                schemaRegistryUrl
//                restEndpointPort,
//                "/tmp/streams-example"
        );

        streams.cleanUp();

        streams.start();


        // Start the Restful proxy for servicing remote access to state stores
        // final Demo1RestService restService = startRestProxy(streams, restEndpointPort);

        // Add shutdown hook to respond to SIGTERM and gracefully close Kafka Streams
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                //restService.stop();
                streams.close();
            } catch (Exception e) {
                // ignored
            }
        }));
    }


    static KafkaStreams createStreams(final String bootstrapServers,
                                      final String schemaRegistryUrl
                                      //final int applicationServerPort
                                      //final String stateDir
                                    ) throws IOException, InterruptedException{

        final Properties streamsConfiguration = new Properties();

        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, "interactive-queries-example_10");//相当于consumer的group.id
        streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, "interactive-queries-example-client");
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        streamsConfiguration.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
       // streamsConfiguration.put(StreamsConfig.APPLICATION_SERVER_CONFIG, "localhost:" + applicationServerPort);
       // streamsConfiguration.put(StreamsConfig.STATE_DIR_CONFIG, stateDir);
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        //streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");//指定消费offset是从头开始还是从最新的开始
        //数据处理的提交间隔，存储处理器当前位置的间隔毫秒数（和时间窗口作用一样吗？）
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10*1000);

        // Allow the user to fine-tune the `metadata.max.age.ms` via Java system properties from the CLI.
        // Lowering this parameter from its default of 5 minutes to a few seconds is helpful in
        // situations where the input topic was not pre-created before running the application because
        // the application will discover a newly created topic faster.  In production, you would
        // typically not change this parameter from its default.
        String metadataMaxAgeMs = System.getProperty(ConsumerConfig.METADATA_MAX_AGE_CONFIG);
        if (metadataMaxAgeMs != null) {
            try {
                int value = Integer.parseInt(metadataMaxAgeMs);
                streamsConfiguration.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, value);
                System.out.println("Set consumer configuration " + ConsumerConfig.METADATA_MAX_AGE_CONFIG +
                        " to " + value);
            } catch (NumberFormatException ignored) {
            }
        }

        final Map<String, String> serdeConfig = Collections.singletonMap(
                AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

        final SpecificAvroSerde<MEvent> mEventSerde = new SpecificAvroSerde<>();
        mEventSerde.configure(serdeConfig, false);

        final SpecificAvroSerde<SPatient> sPatientSerde = new SpecificAvroSerde<>();
        sPatientSerde.configure(serdeConfig, false);

        final Serde<Windowed<String>> windowedStringSerde = new WindowedSerde<>(Serdes.String());



        KStreamBuilder builder = new KStreamBuilder();

//        int length = 66;
//
//        KStream<String, MEvent> mEventKStream1 = builder.stream(Serdes.String(),mEventSerde,TOPIC1);
//        KStream<String, MEvent> mEventKStream2 = builder.stream(Serdes.String(),mEventSerde,TOPIC2);
//        KStream<String, MEvent> mEventKStream3 = builder.stream(Serdes.String(),mEventSerde,TOPIC3);
//        KStream<String, MEvent> mEventKStream4 = builder.stream(Serdes.String(),mEventSerde,TOPIC4);
//        mEventKStream4.print();

/*        List<Float> List1 = new ArrayList<>();
        List<Float> List2 = new ArrayList<>();

        List<String> users = new ArrayList<>();
        KStream<String, String>  matchPattern1 = mEventKStream1
                .filter((key,value)->{
                    boolean flag = false;
                    for (String user : users) {
                        flag |= key.equals(user);
                    }
                    return flag;
                })
                .map((key, value) -> {
                    while(List1.size()<length-1){
                        List1.add(value.getMeasures().get("systolic_blood_pressure").getValue());
                    }
                    if(List1.size() >= length) {
                        List1.remove(0);
                        List1.add(value.getMeasures().get("systolic_blood_pressure").getValue());
                    }
                    else
                        List1.add(value.getMeasures().get("systolic_blood_pressure").getValue());
                    double[] tsRed = new double[length];
                    for(int i=0;i<length;i++)
                        tsRed[i] = List1.get(i);

                    //将一维序列通过SAX算法转换，返回SAX记录值
                    SAXAnalysisWindow window = new SAXAnalysisWindow(64,8,4);
                    int slidingWindowSize = window.getnLength();
                    int paaSize = window.getwSegment();
                    int alphabetSize = window.getaAlphabet();
                    //NONE 是所有，没有省略
                    NumerosityReductionStrategy nrStrategy = NumerosityReductionStrategy.NONE;
                    int nThreshold = 1;

                    NormalAlphabet na = new NormalAlphabet();
                    SAXProcessor sp = new SAXProcessor();

                    SAXRecords res = null;
                    try {
                        res = sp.ts2saxViaWindow(tsRed, slidingWindowSize, paaSize,
                                na.getCuts(alphabetSize), nrStrategy, nThreshold);
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }

                    String alphabet = new String();
                    Set<Integer> index = res.getIndexes();
                    for (Integer idx : index)
                        alphabet += String.valueOf(res.getByIndex(idx).getPayload());
                    String s = "bbbbccccbbbbccccbbbbcccc";
                    boolean match = false;
                    try {
                        match = patternMatch(alphabet,s,alphabetSize);
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }
                    if(match == true) {
                        List1.removeAll(List1);
                        return KeyValue.pair(value.getUserId(), "1");
                    } else
                        return KeyValue.pair(value.getUserId(), "0");
                    //返回值是0或1，不能用int型，因为特殊格式转int会报错
                });

        KStream<String, String>  matchPattern2 = mEventKStream2
                        .filter((key,value)-> value.getUserId().equals("the-user-0"))
                        .map((key, value) -> {
                            while(List2.size()<length-1){
                                List2.add(value.getMeasures().get("heart_rate").getValue());
                            }
                            if(List2.size() >= length) {
                                List2.remove(0);
                                List2.add(value.getMeasures().get("heart_rate").getValue());
                            }
                            else
                                List2.add(value.getMeasures().get("heart_rate").getValue());
                            double[] tsRed = new double[length];
                            for(int i=0;i<length;i++)
                                tsRed[i] = List2.get(i);

                            //将一维序列通过SAX算法转换，返回SAX记录值
                            SAXAnalysisWindow window = new SAXAnalysisWindow(64,8,4);
                            int slidingWindowSize = window.getnLength();
                            int paaSize = window.getwSegment();
                            int alphabetSize = window.getaAlphabet();
                            //NONE 是所有，没有省略
                            NumerosityReductionStrategy nrStrategy = NumerosityReductionStrategy.NONE;
                            int nThreshold = 1;

                            NormalAlphabet na = new NormalAlphabet();
                            SAXProcessor sp = new SAXProcessor();

                            SAXRecords res = null;
                            try {
                                res = sp.ts2saxViaWindow(tsRed, slidingWindowSize, paaSize,
                                        na.getCuts(alphabetSize), nrStrategy, nThreshold);
                            } catch (SAXException e) {
                                e.printStackTrace();
                            }

                            String alphabet = new String();
                            Set<Integer> index = res.getIndexes();
                            for (Integer idx : index)
                                alphabet += String.valueOf(res.getByIndex(idx).getPayload());
                            String s = "bbbbccccbbbbccccbbbbcccc";
                            boolean match = false;
                            try {
                                match = patternMatch(alphabet,s,alphabetSize);
                            } catch (SAXException e) {
                                e.printStackTrace();
                            }
                            if(match == true) {
                                List2.removeAll(List2);
                                return KeyValue.pair(value.getUserId(), "1");
                            } else
                                return KeyValue.pair(value.getUserId(), "0");
                            //返回值是0或1
                        });
        KStream<String,String> k = matchPattern1.join(matchPattern2,
                (leftValue,rightValue) -> leftValue + "/" + rightValue,
                JoinWindows.of(TimeUnit.SECONDS.toMillis(60)),
                Serdes.String(),
                Serdes.String(),
                Serdes.String()
        );
        k.print();*/

//        matchPattern1
//                .join(mEventKStream2,
//                    (leftValue,rightValue) -> "0",
//                    JoinWindows.of(TimeUnit.MINUTES.toMillis(66))
//                );


/*        kStream1.join(kStream2,
                        (leftValue , rightValue) -> {
                            double[][] data = {
                                    {Double.parseDouble(leftValue.get(0).toString()), Double.parseDouble(rightValue.get(0).toString())},
                                    {Double.parseDouble(leftValue.get(1).toString()), Double.parseDouble(rightValue.get(1).toString())},
                                    {Double.parseDouble(leftValue.get(2).toString()), Double.parseDouble(rightValue.get(2).toString())},
                                    {Double.parseDouble(leftValue.get(3).toString()), Double.parseDouble(rightValue.get(3).toString())},
                                    {Double.parseDouble(leftValue.get(4).toString()), Double.parseDouble(rightValue.get(4).toString())}
                            };

                            //PCA降维，得到一维数组tsRed，现在不用pca降维了
//                            Matrix X = new DenseMatrix(data).transpose();
//                            int r = 1;
//                            Matrix R = PCA.run(X, r);
//                            DenseVector temp = (DenseVector) R.getColumnVector(0);
                            double[] tsRed = new double[67];//temp.getPr();
                            NormalAlphabet nn = new NormalAlphabet();
                            //double[][] nm = nn.getDistanceMatrix();

                            //将降维后的序列通过SAX算法转换，返回SAX记录值
                            SAXAnalysisWindow window = new SAXAnalysisWindow(1,8,64);
                            int slidingWindowSize = window.getnLength();
                            int paaSize = window.getwSegment();
                            int alphabetSize = window.getaAlphabet();
                            //NONE 是所有，没有省略
                            NumerosityReductionStrategy nrStrategy = NumerosityReductionStrategy.NONE;
                            int nThreshold = 1;

                            NormalAlphabet na = new NormalAlphabet();
                            SAXProcessor sp = new SAXProcessor();

                            SAXRecords res = null;
                            try {
                                res = sp.ts2saxViaWindow(tsRed, slidingWindowSize, paaSize,
                                        na.getCuts(alphabetSize), nrStrategy, nThreshold);
                            } catch (SAXException e) {
                                e.printStackTrace();
                            }

                            String alphabet = new String();
                            Set<Integer> index = res.getIndexes();
                            for (Integer idx : index)
                                alphabet += String.valueOf(res.getByIndex(idx).getPayload());
                            return alphabet;

                        },
                        JoinWindows.of(TimeUnit.MINUTES.toMillis(1))
                ).print();*/


//        //①：单类体征数据数值上的条件定义，比如血压超过某个指定阈值触发警告
//        KStream<String, MEvent> mEventKStream = builder.stream(Serdes.String(),mEventSerde,TOPIC1);
//        KStream<String, MEvent> mEventStream = mEventKStream
//                .filter((key , value) -> value.getMeasures().get("systolic_blood_pressure").getValue() > 130
//                        || value.getMeasures().get("diastolic_blood_pressure").getValue() > 80);
//        mEventStream.print();
//        System.out.println("running");

        KStream<String, MEvent> mEventKStream2 = builder.stream(Serdes.String(),mEventSerde,TOPIC3);
        KStream<String, MEvent> mEventKStream3 = builder.stream(Serdes.String(),mEventSerde,TOPIC4);
        mEventKStream2.print();
        mEventKStream3.print();

        //②：多类体征数据数值上的条件定义，比如血压和心率同时超过各自的阈值触发警告
/*        KStream<String, MEvent> mEventKStream1 = builder.stream(Serdes.String(),mEventSerde,TOPIC1);
        KStream<String, MEvent> mEventKStream2 = builder.stream(Serdes.String(),mEventSerde,TOPIC2);
        KStream<String, MEvent> mEventKStream3 = builder.stream(Serdes.String(),mEventSerde,TOPIC3);
        KStream<String, String> joined = mEventKStream1
                .filter((key , value) ->
                        value.getMeasures().get("systolic_blood_pressure").getValue() > 110
                                || value.getMeasures().get("diastolic_blood_pressure").getValue() > 80)
                .join(mEventKStream2
                        .filter((key, value) -> value.getMeasures().get("heart_rate").getValue()> 90),
                        (leftValue , rightValue) ->
                                "user_id = " + leftValue.getUserId()
                                        + ", systolic_blood_pressure = " + leftValue.getMeasures().get("systolic_blood_pressure").getValue()
                                        + ", diastolic_blood_pressure = " + leftValue.getMeasures().get("diastolic_blood_pressure").getValue()
                                        + ", heart_rate = " + rightValue.getMeasures().get("heart_rate").getValue(),
                        JoinWindows.of(TimeUnit.MINUTES.toMillis(1))
                        )
//                .join(mEventKStream3
//                                .filter((key, value) -> value.getMeasures().get("body_temperature").getValue()>37),
//                        (leftValue , rightValue) ->
//                                leftValue
//                                        + ", body_temperature = " + rightValue.getMeasures().get("body_temperature").getValue(),
//                        JoinWindows.of(TimeUnit.MINUTES.toMillis(1)))
                ;
        joined.print();*/


/*        //③：体征数据变化方式或幅度上的条件定义，比如心率或血压增幅过大而触发警告
        KStream<String, MEvent>  mEventKStream = builder.stream(Serdes.String(),mEventSerde,TOPIC2);
        mEventKStream
                .filter((key,value)-> value.getUserId().equals("the-user-1000"))
                .groupByKey()
                .reduce((v1, v2) -> {
                            Measure m1 = new Measure();
                            m1.put("unit", "beats/min");
                            m1.put("value", v2.getMeasures().get("heart_rate").getValue()-v1.getMeasures().get("heart_rate").getValue());
                            Measure m2 = new Measure();
                            m2.put("unit", "beats/min");
                            m2.put("value", v2.getMeasures().get("heart_rate").getValue());
                            Map map = new HashMap();
                            map.put("heart_rate_increase",m1);
                            map.put("heart_rate",m2);
                            MEvent m3 = new MEvent();
                            m3.put("user_id",v2.getUserId());
                            m3.put("timestamp", v2.getTimestamp());
                            m3.put("measures", map);
                            return m3;
                        }
//                        ,
//                        TimeWindows.of(100L),
//                        "SEARCH_STORE_NAME"
                ).toStream().print();*/


/*        //④：体征数据在时间段内满足阈值条件次数上的条件定义，比如几分钟之内多次出现血压超标
        KStream<String, MEvent> mEventKStream = builder.stream(Serdes.String(),mEventSerde,TOPIC1);
        KTable<Windowed<String>, Long> mEventTable = mEventKStream
                .filter((key, value) -> value.getUserId().equals("the-user-1000"))
                .filter((key , value) ->
                        value.getMeasures().get("systolic_blood_pressure").getValue() > 100
                    || value.getMeasures().get("diastolic_blood_pressure").getValue() > 60)
//                .map((key, value) -> new KeyValue<>(value.getUserId(),value))
//                .groupByKey()
                .groupBy((key, value) -> value.getUserId(),Serdes.String(),mEventSerde)
                .count(TimeWindows.of(5 * 1000L).advanceBy(1*1000L),SEARCH_STORE_NAME);   //Hopping Window
        mEventTable.filter((key, value) -> value>=2).print();*/

       //⑤：体征数据超过阈值持续时间上的条件定义，比如血压值超标持续几分钟（即判断一个时间窗口内的数据最小值是不是大于给定阈值）
/*        final Comparator<MEvent> comparator =
                (o1, o2) -> (int) (o1.getMeasures().get("heart_rate").getValue() - o2.getMeasures().get("heart_rate").getValue());

        KStream<String, MEvent> heartrateRanking= builder.stream(Serdes.String(),mEventSerde,TOPIC2);
        KTable<Windowed<String>,PriorityQueue<MEvent>> hrRank =
                heartrateRanking
                .filter((key,value)-> value.getUserId().equals("the-user-1000"))
                .groupByKey(Serdes.String(),
                        mEventSerde)
                .aggregate(() -> new PriorityQueue<>(comparator),
                        (aggKey, value, aggregate) -> {
                            aggregate.add(value);
                            return aggregate;
                        },
                        TimeWindows.of(120*1000L),
                        new PriorityQueueSerde<>(comparator, mEventSerde)
                );  //.toStream().print();
         final int minN = 1;
       KTable<Windowed<String>, String> minCounts = hrRank
                .mapValues(queue -> {
                    final StringBuilder sb = new StringBuilder();
//                    for (int i = 0; i < minN; i++) {
//                        final MEvent me = queue.poll();
//                        if (me == null) {
//                            break;
//                        }
//                        sb.append(me.getUserId()+","+me.getTimestamp());
//                        sb.append("\n");
//                    }
                    final MEvent me = queue.poll();
                    sb.append(me.getMeasures().get("heart_rate").getValue());
                    return sb.toString();
                });
        minCounts.filter((key, value) -> Double.parseDouble(value)>60).print();*/
        //要解决的问题，①：在这个例子中加入时间窗口，要比设置的提交间隔短
        // ②：跑程序的时候把上一次程序留下的changelog topic删掉，确保集群里只有一个changelog topic


        /**
         *  (1) 计算心率增幅
         *  (2) 筛选出心率增幅大于10，同时高压大于120、低压小于90的数据
         *  (3) 筛选出5分钟内(2)出现次数大于3次的数据
         *  (4) 把结果打印出来（心率值、血压值、心率增幅）
         */
/*
        KStream<String, MEvent> mEventKStream1 = builder.stream(Serdes.String(),mEventSerde,TOPIC1);
        KStream<String, MEvent> mEventKStream2 = builder.stream(Serdes.String(),mEventSerde,TOPIC2);

        KTable<String, MEvent> kTable = mEventKStream2
                .filter((key,value)-> value.getUserId().equals("the-user-1000"))
                .groupByKey()
                .reduce((v1, v2) -> {
                            Measure m1 = new Measure();
                            m1.put("unit", "beats/min");
                            m1.put("value", v2.getMeasures().get("heart_rate").getValue()-v1.getMeasures().get("heart_rate").getValue());
                            Measure m2 = new Measure();
                            m2.put("unit", "beats/min");
                            m2.put("value", v2.getMeasures().get("heart_rate").getValue());
                            Map map = new HashMap();
                            map.put("heart_rate_increase",m1);
                            map.put("heart_rate",m2);
                            MEvent m3 = new MEvent();
                            m3.put("user_id",v2.getUserId());
                            m3.put("timestamp", v2.getTimestamp());
                            m3.put("measures", map);
                            return m3;
                        },PROFILE_STORE_NAME)
                .filter((key,value)-> value.getMeasures().get("heart_rate_increase").getValue() > 10);

        KStream<String, MEvent> kStream = mEventKStream1
                .filter((key,value)-> value.getMeasures().get("systolic_blood_pressure").getValue() > 120
                        && value.getMeasures().get("diastolic_blood_pressure").getValue() < 90);

        kStream.join(kTable.filter((key,value) -> value.getUserId().equals("the-user-1000"),SEARCH_STORE_NAME),
                (leftValue , rightValue) ->
                        "user_id = " + leftValue.getUserId()
                                + ", heart_rate = " + leftValue.getMeasures().get("heart_rate_increase").getValue()
                                + ", heart_rate = " + leftValue.getMeasures().get("heart_rate").getValue()
                                + ", systolic_blood_pressure = " + rightValue.getMeasures().get("systolic_blood_pressure").getValue()
                                + ", diastolic_blood_pressure = " + rightValue.getMeasures().get("diastolic_blood_pressure").getValue()
        )
        .groupByKey()
        .reduce((v1,v2) -> {
            List<String> list = new ArrayList<>();
            list.add(v1);
            list.add(v2);
            if (list.size() > 3) {
                StringBuffer s = null;
                for (int i = 0; i < list.size(); i++) {
                    s.append("第"+ i+1 +"次" + list.get(i));
                    s.append("\n");
                }
                return s.toString();
            }
            else
                return null;
        },
        TimeWindows.of(5 * 60 * 1000))
        .print();
*/



        /**
         *  (1) 计算5分钟内心率的 min 和 max
         *  (2) 把结果打印出来
         */
/*        final Comparator<MEvent> comparator =
                (o1, o2) -> (int) (o1.getMeasures().get("heart_rate").getValue() - o2.getMeasures().get("heart_rate").getValue());

        KStream<String, MEvent> heartrateRanking= builder.stream(Serdes.String(),mEventSerde,TOPIC2);
        KTable<Windowed<String>,PriorityQueue<MEvent>> hrRank = heartrateRanking
                .filter((key,value)-> value.getUserId().equals("the-user-1000"))
                .groupByKey(Serdes.String(),
                        mEventSerde)
                .aggregate(() -> new PriorityQueue<>(comparator),
                        (aggKey, value, aggregate) -> {
                            aggregate.add(value);
                            return aggregate;
                        },
                        TimeWindows.of(5 * 60 * 1000L),
                        new PriorityQueueSerde<>(comparator, mEventSerde),
                        SEARCH_STORE_NAME
                );

        KTable<Windowed<String>, String> minCounts = hrRank
                .mapValues(queue -> {
                    List<Float> list = new ArrayList<>();
                    while(true){
                        final MEvent me = queue.poll();
                        if (me == null) {
                            break;
                        }
                        list.add(me.getMeasures().get("heart_rate").getValue());
                    }
                    return "min:" + list.get(0) + "  max:" + list.get(list.size()-1);
                });
        minCounts.print();*/


        //静态数据和动态数据结合，筛选出10秒的时间段内西班牙女性的高血压患者病例
/*        KStream<String, MEvent> mEventKStream = builder.stream(Serdes.String(),mEventSerde,TOPIC1);
        //GlobalKTable<String, SPatient> sPatientGlobalKTable = builder.globalTable(Serdes.String(),sPatientSerde,TOPIC7,"sPatient_store1");
        KTable<String, SPatient> sPatientKTable = builder.table(Serdes.String(),sPatientSerde,TOPIC7,SEARCH_STORE_NAME);
        KStream<String, String> joined = mEventKStream
                .filter((key , value) -> Integer.valueOf(key.substring(key.lastIndexOf("-") + 1)) < 190
                        && (value.getMeasures().get("systolic_blood_pressure").getValue() > 100
                        || value.getMeasures().get("diastolic_blood_pressure").getValue() > 60))
                .join(sPatientKTable.filter((key ,value) -> value.getGender()=="M"
                                        ,PROFILE_STORE_NAME)
                        ,
                        (leftValue , rightValue) -> {
                            if (rightValue != null)
                                return "user_id = " + leftValue.getUserId()
                                        + ", systolic_blood_pressure = " + leftValue.getMeasures().get("systolic_blood_pressure").getValue()
                                        + ", diastolic_blood_pressure = " + leftValue.getMeasures().get("diastolic_blood_pressure").getValue()
                                        + ", gender = " + rightValue.getGender();
                            else
                                return null;
                        },
                Serdes.String(),
                mEventSerde
                );
        builder.connectProcessorAndStateStores("KSTREAM-JOIN-0000000005",SEARCH_STORE_NAME);
        joined.print();
        System.out.println("running");*/


        //对高血压数据进行排名取Top10
/*        final Comparator<SPatient> comparator =
                (o1, o2) -> (int) (o2.getTimestamp() - o1.getTimestamp());
                //(o1, o2) -> (int) (o2.getMeasures().get("systolic_blood_pressure").getValue() - o1.getMeasures().get("systolic_blood_pressure").getValue());

        //KTable<String, MEvent> bloodPressureRanking= builder.table(Serdes.String(),mEventSerde,TOPIC1,"bpRanking_store");
        KTable<String, SPatient> bloodPressureRanking1= builder.table(Serdes.String(),sPatientSerde,TOPIC7,"bpRanking_store1");
        KTable<String,PriorityQueue<SPatient>> bpRank = bloodPressureRanking1
                //.filter((key,value)-> value.getUserId()=="the-user-55")
                .groupBy((key, value) -> KeyValue.pair(key, value),
                Serdes.String(),
                sPatientSerde)
                .aggregate(() -> new PriorityQueue<>(comparator),
                        (aggKey, value, aggregate) -> {
                            aggregate.add(value);
                            return aggregate;
                        },
                        (aggKey, value, aggregate) -> {
                            aggregate.remove(value);
                            return aggregate;
                        },
                        new PriorityQueueSerde<>(comparator, sPatientSerde),
                        "demo4"
                );
    //    .toStream().print();
        final int topN = 40;
       KTable<String, String> topViewCounts = bpRank
                .mapValues(queue -> {
                    final StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < topN; i++) {
                        final SPatient sp = queue.poll();
                        if (sp == null) {
                            break;
                        }
                        sb.append(sp.getUserId()+","+sp.getTimestamp());
                        sb.append("\n");
                    }
                    return sb.toString();
                });
        topViewCounts.toStream().print();*/


        return new KafkaStreams(builder, streamsConfiguration);
    }

    static Demo1RestService startRestProxy(final KafkaStreams streams, final int port) throws Exception {
        final HostInfo hostInfo = new HostInfo("localhost", port);
        final Demo1RestService demo1RestService = new Demo1RestService(streams, hostInfo, PROFILE_STORE_NAME, SEARCH_STORE_NAME);
        demo1RestService.start(port);
        return demo1RestService;
    }

    static boolean patternMatch(String alphabet1,String alphabet2,int length) throws SAXException{
        NormalAlphabet nal = new NormalAlphabet();
        double[][] distance = nal.getDistanceMatrix(length);
        for(int i=0;i<alphabet1.length();i++){
            if(distance[alphabet1.charAt(i)-'a'][alphabet2.charAt(i)-'a'] != 0)
                return false;
        }
        return true;
    }

    static void delAllFiles(File dir) {
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                if (!f.isDirectory())
                    f.delete();
                else
                    delAllFiles(f);
            }

        }
        dir.delete();
    }
}



