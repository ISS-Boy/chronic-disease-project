package examples.interactivequeries.Demo;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import net.seninp.jmotif.sax.NumerosityReductionStrategy;
import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.SAXProcessor;
import net.seninp.jmotif.sax.alphabet.NormalAlphabet;
import net.seninp.jmotif.sax.datastructure.SAXRecords;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.mhealth.open.data.avro.MEvent;

import java.util.*;
import java.util.Map;
import java.util.Properties;

public class PatternMatch1 implements Runnable{
    private Map<String,String> pattern;     //key是measure名字，value是模式字符串
    private String patternid;               //模式有可能是中文，所以改成模式的编号
    private String userid;
    private String order;
    private SAXAnalysisWindow window;

    //alt+insert是构造器和get、set方法的快捷键，点第一个然后按shift点最后一个
    public PatternMatch1(Map<String, String> pattern, String patternid, String userid, String order, SAXAnalysisWindow window) {
        this.pattern = pattern;
        this.patternid = patternid;
        this.userid = userid;
        this.order = order;
        this.window = window;
    }
    public Map<String, String> getPattern() {
        return pattern;
    }
    public void setPattern(Map<String, String> pattern) {
        this.pattern = pattern;
    }
    public String getPatternid() {
        return patternid;
    }
    public void setPatternid(String patternid) {
        this.patternid = patternid;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }
    public SAXAnalysisWindow getWindow() {
        return window;
    }
    public void setWindow(SAXAnalysisWindow window) {
        this.window = window;
    }


    public void run(){
        final Properties streamsConfiguration = new Properties();
        String consumerGroupName = "order_" + order;
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, consumerGroupName);
        streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, ParaConfig.bootstrapServers);
        streamsConfiguration.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, ParaConfig.schemaRegistryUrl);
        streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, SpecificAvroSerde.class);
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10*1000);

        final SpecificAvroSerde<MEvent> mEventSerde = new SpecificAvroSerde<>();
        mEventSerde.configure(ParaConfig.serdeConfig, false);


        KStreamBuilder builder = new KStreamBuilder();

        Map<String, KStream<String, MEvent>> kStreamMap = new HashMap<>();
        for(String measure : pattern.keySet()) {
            kStreamMap.put(measure, builder.stream(Serdes.String(), mEventSerde, measure));
        }

        List<KStream<String, Integer>> list = new ArrayList<>();
        for(String measure : pattern.keySet()) {
             list.add(matchPattern(measure, kStreamMap.get(measure), 66));  //长度跟学长的保持一致，从前台返回来
        }

        //缺join操作，没考虑好是用if else还是循环

        //结果写入到一个topic中
        //key-value的形式，key是user_id，value是avro的格式，avro里有个map，map存放pattern和对应的pattern的0或者1
        //value也可以是string的形式，直接是模式id+0或1，然后opentsdb那边再解析

        final KafkaStreams streams = new KafkaStreams(builder, streamsConfiguration);

        streams.cleanUp();
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    public KStream<String, Integer> matchPattern(String measure, KStream<String,MEvent> kStream, int length){
        List<Float> list = new ArrayList<>();
        KStream<String, Integer>  kStream1 = kStream
                .filter((key,value)-> value.getUserId().equals("the-user-0"))     //用户名也要从前台获取
                .map((key, value) -> {
                    while(list.size() < length-1){
                        list.add(value.getMeasures().get("systolic_blood_pressure").getValue());
                    }
                    if(list.size() >= length) {
                        list.remove(0);
                        list.add(value.getMeasures().get("systolic_blood_pressure").getValue());
                    }
                    else
                        list.add(value.getMeasures().get("systolic_blood_pressure").getValue());
                    double[] tsRed = new double[length];
                    for(int i = 0; i < length; i++)
                        tsRed[i] = list.get(i);

                    //将一维序列通过SAX算法转换，返回SAX记录值
                    SAXAnalysisWindow window = new SAXAnalysisWindow(64,8,4);//保持一致
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
                    String s = pattern.get(measure);
                    boolean match = false;
                    try {
                        match = patternMatch(alphabet,s,alphabetSize);
                    } catch (SAXException e) {
                        e.printStackTrace();
                    }
                    if(match == true) {
                        list.removeAll(list);
                        return KeyValue.pair(value.getUserId(), 1);
                    } else
                        return KeyValue.pair(value.getUserId(), 0);
                    //返回值是0或1
                });
        return kStream1;
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
}
