package com.iss.bigdata.health.elasticsearch.service;

import com.alibaba.fastjson.JSON;
import com.aliyun.hitsdb.client.HiTSDB;
import com.aliyun.hitsdb.client.HiTSDBClient;
import com.aliyun.hitsdb.client.HiTSDBClientFactory;
import com.aliyun.hitsdb.client.HiTSDBConfig;
import com.aliyun.hitsdb.client.value.request.Query;
import com.aliyun.hitsdb.client.value.request.SubQuery;
import com.aliyun.hitsdb.client.value.response.QueryResult;
import com.aliyun.hitsdb.client.value.type.Aggregator;
import com.iss.bigdata.health.elasticsearch.entity.*;
import com.iss.bigdata.health.elasticsearch.help.EventMap;
import com.iss.bigdata.health.elasticsearch.help.QueryObject;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dujijun on 2018/1/3.
 * <p>
 * 结构
 * -patient
 * -encounter
 * -observation
 * -medications
 * -immunizations
 * -conditions
 * -careplans
 * -allergies
 */
public class ElasticSearchServiceImpl implements ElasticSearchService {
    private RestHighLevelClient client;
    private static final int MAX_QUERY_SIZE = 10000;

    enum Signal {
        北京, 天津, 河北, 山西, 内蒙古, 辽宁, 吉林, 黑龙江, 上海, 江苏, 浙江, 安徽, 福建, 江西, 山东, 河南, 湖北, 湖南, 广东, 广西, 海南, 重庆, 四川, 贵州, 云南, 西藏, 陕西, 甘肃, 青海, 宁夏, 新疆
    }

    public ElasticSearchServiceImpl() {
        RestClient lowLevelRestClient = RestClient.builder(
                new HttpHost("192.168.222.232", 9200, "http")).build();
        this.client = new RestHighLevelClient(lowLevelRestClient);

    }

    HiTSDBConfig hiTSDBConfig = HiTSDBConfig.address("192.168.222.233", 4242).config();

    @Override
    public EventMap getAllTypeEventByUserId(String userId, Date start, Date end) {
        List<QueryObject> startQueries = new ArrayList<>();
        startQueries.add(new QueryObject(userId, "conditions", "synthea", Condition.class, start, end));
        startQueries.add(new QueryObject(userId, "allergies", "synthea", Allergy.class, start, end));
        startQueries.add(new QueryObject(userId, "careplans", "synthea", CarePlan.class, start, end));
        startQueries.add(new QueryObject(userId, "medications", "synthea", Medication.class, start, end));

        List<QueryObject> dateQueries = new ArrayList<>();
        dateQueries.add(new QueryObject(userId, "observation", "synthea", Observation.class, start, end));
        dateQueries.add(new QueryObject(userId, "immunizations", "synthea", Immunization.class, start, end));
        dateQueries.add(new QueryObject(userId, "encounters", "synthea", Encounter.class, start, end));

        EventMap events = new EventMap();
        events.putAll(getMutiTypeEventsByUserIdOrderByDate(dateQueries));
        events.putAll(getMutiTypeEventsByUserIdOrderByStart(startQueries));
        return events;
    }

    /**
     * 组合查询, 查询多个类型的EventList
     *
     * @param queryRequests 查询请求列表
     * @return 多事件映射
     */

    @Override
    public EventMap getSeveralTypeEventsByUserId(List<QueryObject> queryRequests) {
        Map<String, List<QueryObject>> queryGroup = queryRequests
                .stream()
                .collect(Collectors.groupingBy(q -> q.getFilterNameAndOrderKey()));
        EventMap resultMap = new EventMap();
        resultMap.putAll(getMutiTypeEventsByUserIdOrderByDate(queryGroup.get("date")));
        resultMap.putAll(getMutiTypeEventsByUserIdOrderByStart(queryGroup.get("start")));
        return resultMap;
    }

    /**
     * 获取通过事件发生日期排序的类型的事件
     */


    @Override
    public EventMap getMutiTypeEventsByUserIdOrderByDate(List<QueryObject> queryRequests) {
        return getMultiTypeEventsByUserId(queryRequests, "date", SortOrder.DESC);
    }

    /**
     * 获取通过事件起始日期排序的类型的事件
     */


    @Override
    public EventMap getMutiTypeEventsByUserIdOrderByStart(List<QueryObject> queryRequests) {
        return getMultiTypeEventsByUserId(queryRequests, "start", SortOrder.DESC);
    }

    /**
     * 多类型查询, 通过查询对象传入需要的查询与排序键, 得到所有的查询结果
     * 注意：传入的List中的查询对象的排序键和参数中的排序键必须相同，否则会抛出异常
     * 推荐使用
     * {@link ElasticSearchServiceImpl#getMutiTypeEventsByUserIdOrderByDate(List)}
     * {@link ElasticSearchServiceImpl#getMutiTypeEventsByUserIdOrderByStart(List)}
     * {@link ElasticSearchServiceImpl#getMultiTypeEventsByUserId(List, String, SortOrder)}
     *
     * @param queryRequests 查询请求对象
     * @param orderKey      排序键
     * @param order         排序方式
     * @return 事件映射列表
     * @throws IOException
     */


    @Override
    public EventMap getMultiTypeEventsByUserId(List<QueryObject> queryRequests, String orderKey, SortOrder order) {
        EventMap eventMap = new EventMap();
        SearchRequest searchRequest = new SearchRequest();
        List<String> indice = new ArrayList<>();

        // 构建EventMap
        buildEventMap(eventMap, queryRequests);

        // 构建搜索语句
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder filterQueryRoot = QueryBuilders.boolQuery();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        // 构建每一个Query的查询
        for (QueryObject queryRequest : queryRequests) {
            // 当排序键不一致时，抛出异常
            if (!queryRequest.getFilterNameAndOrderKey().equals(orderKey))
                throw new RuntimeException("使用错误，请保证参数中的所有排序键和查询对象中的排序键一直");

            indice.add(queryRequest.getIndexName());
            searchRequest.types(queryRequest.getType());

            boolQueryBuilder.should(QueryBuilders
                    .boolQuery()
                    .must(QueryBuilders.termQuery("_index", queryRequest.getIndexName()))
                    .must(QueryBuilders.termQuery("user_id.keyword", queryRequest.getUserId()))
                    .must(QueryBuilders.rangeQuery(queryRequest.getFilterNameAndOrderKey())
                            .from(queryRequest.getStart())
                            .to(queryRequest.getEnd())));

        }
        // 设置查询索引
        String[] indiceArr = new String[indice.size()];
        indice.toArray(indiceArr);
        searchRequest.indices(indiceArr);

        // 设置结果集排序
        sourceBuilder.sort(orderKey, order);
        sourceBuilder.size(MAX_QUERY_SIZE);
        sourceBuilder.query(filterQueryRoot.filter(boolQueryBuilder));

        // 打印查询语句
//        System.out.println(sourceBuilder.query());

        searchRequest.source(sourceBuilder);


        // 获取输出结果
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("连接或查询有误, 请检查您的网络连接是否畅通，并检查查询项是否有误!");
        }
        for (SearchHit hit : searchResponse.getHits()) {
            putEachEventIntoMap(eventMap, hit);
        }

        return eventMap;
    }

    private void putEachEventIntoMap(EventMap eventMap, SearchHit hit) {
        String id = JSON.parseObject(hit.getSourceAsString()).getString("encounter");
        switch (hit.getIndex()) {
            case "allergies":
                Map<String, List<Allergy>> allergiesMap = eventMap.getEventMap(Allergy.class, "allergies");
                if (!allergiesMap.containsKey(id))
                    allergiesMap.put(id, new ArrayList<>());
                allergiesMap.get(id).add(JSON.parseObject(hit.getSourceAsString(), Allergy.class));
                break;
            case "encounters":
                Map<String, List<Encounter>> encountersMap = eventMap.getEventMap(Encounter.class, "encounters");
                if (!encountersMap.containsKey(id))
                    encountersMap.put(id, new ArrayList<>());
                encountersMap.get(id).add(JSON.parseObject(hit.getSourceAsString(), Encounter.class));
                break;
            case "conditions":
                Map<String, List<Condition>> conditionsMap = eventMap.getEventMap(Condition.class, "conditions");
                if (!conditionsMap.containsKey(id))
                    conditionsMap.put(id, new ArrayList<>());
                conditionsMap.get(id).add(JSON.parseObject(hit.getSourceAsString(), Condition.class));
                break;
            case "medications":
                Map<String, List<Medication>> medicationsMap = eventMap.getEventMap(Medication.class, "medications");
                if (!medicationsMap.containsKey(id))
                    medicationsMap.put(id, new ArrayList<>());
                medicationsMap.get(id).add(JSON.parseObject(hit.getSourceAsString(), Medication.class));
                break;
            case "immunizations":
                Map<String, List<Immunization>> immunizationsMap = eventMap.getEventMap(Immunization.class, "immunizations");
                if (!immunizationsMap.containsKey(id))
                    immunizationsMap.put(id, new ArrayList<>());
                immunizationsMap.get(id).add(JSON.parseObject(hit.getSourceAsString(), Immunization.class));
                break;
            case "observation":
                Map<String, List<Observation>> observationsMap = eventMap.getEventMap(Observation.class, "observation");
                if (!observationsMap.containsKey(id))
                    observationsMap.put(id, new ArrayList<>());
                observationsMap.get(id).add(JSON.parseObject(hit.getSourceAsString(), Observation.class));
                break;
            case "careplans":
                Map<String, List<CarePlan>> careplansMap = eventMap.getEventMap(CarePlan.class, "careplans");
                if (!careplansMap.containsKey(id))
                    careplansMap.put(id, new ArrayList<>());
                careplansMap.get(id).add(JSON.parseObject(hit.getSourceAsString(), CarePlan.class));
                break;
        }
    }

    // 构建对应事件信息列表
    @Deprecated
    private void putEachEventIntoList(EventMap eventMap, SearchHit hit, String orderKey) {
        Date date = JSON.parseObject(hit.getSourceAsString()).getDate(orderKey);
        switch (hit.getIndex()) {
            case "allergies":
                eventMap.getEventList(Allergy.class, "allergies")
                        .add(new Event<>(hit.getId(),
                                JSON.parseObject(hit.getSourceAsString(),
                                        Allergy.class), date));
                break;
            case "encounters":
                eventMap.getEventList(Encounter.class, "encounters")
                        .add(new Event<>(hit.getId(),
                                JSON.parseObject(hit.getSourceAsString(),
                                        Encounter.class), date));
                break;
            case "conditions":
                eventMap.getEventList(Condition.class, "conditions")
                        .add(new Event<>(hit.getId(),
                                JSON.parseObject(hit.getSourceAsString(),
                                        Condition.class), date));
                break;
            case "medications":
                eventMap.getEventList(Medication.class, "medications")
                        .add(new Event<>(hit.getId(),
                                JSON.parseObject(hit.getSourceAsString(),
                                        Medication.class), date));
                break;
            case "immunizations":
                eventMap.getEventList(Immunization.class, "immunizations")
                        .add(new Event<>(hit.getId(),
                                JSON.parseObject(hit.getSourceAsString(),
                                        Immunization.class), date));
                break;
            case "observation":
                eventMap.getEventList(Observation.class, "observation")
                        .add(new Event<>(hit.getId(),
                                JSON.parseObject(hit.getSourceAsString(),
                                        Observation.class), date));
                break;
            case "careplans":
                eventMap.getEventList(CarePlan.class, "careplans")
                        .add(new Event<>(hit.getId(),
                                JSON.parseObject(hit.getSourceAsString(),
                                        CarePlan.class), date));
                break;

        }
    }

    private void buildEventMap(EventMap map, List<QueryObject> queryRequests) {
        for (QueryObject q : queryRequests)
            map.put(q.getIndexName(), createResultMap(q.getClassType()));
    }

    private <T> Map<String, List<T>> createResultMap(Class<T> classType) {
        return new LinkedHashMap<>();
    }


    /**
     * 不排序的EventList
     *
     * @param userId    用户的id
     * @param indexName 索引名——即事件名
     * @param type      索引类型，一般统一为synthea
     * @param classType 需要获取事件实体的Class的类型
     * @return 事件列表
     * @throws IOException
     */


    @Override
    public <T> Map<String, List<T>> getSpecificEventsByUserId(String userId, String indexName, String type, Class<T> classType) {
        return getSpecificEventsByUserId(userId,
                indexName,
                type,
                classType,
                new HashMap<>(),
                "timestamp",
                null,
                null
        );
    }

    /**
     * 单类型查询, 通过UserId来获取相应的单类型事件列表
     *
     * @param userId      用户的id
     * @param indexName   索引名——即事件名
     * @param type        索引类型，一般统一为synthea
     * @param classType   需要获取事件实体的Class的类型
     * @param orderFields Map<String, SortOrder> (排序列名, 顺序)
     * @param filterName  过滤域名称
     * @param startDate   开始时间
     * @param endDate     结束时间
     * @return 事件列表
     */


    @Override
    public <T> Map<String, List<T>> getSpecificEventsByUserId(String userId,
                                                              String indexName,
                                                              String type,
                                                              Class<T> classType,
                                                              Map<String, SortOrder> orderFields,
                                                              String filterName,
                                                              Date startDate,
                                                              Date endDate) {
        Map<String, List<T>> events = new LinkedHashMap<>();

        SearchRequest searchRequest = new SearchRequest(indexName);
        searchRequest.types(type);

        // 构建搜索语句
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        // 使用bool组合查询
        sourceBuilder.query(QueryBuilders.boolQuery().filter(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("user_id.keyword", userId))
                        .must(QueryBuilders.rangeQuery(filterName).from(startDate).to(endDate))
        ));
        // 按照所选字段和顺序进行排序
        orderFields.forEach(sourceBuilder::sort);
        sourceBuilder.size(MAX_QUERY_SIZE);

        // 发送搜索请求并分析获取结果
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("连接或查询有误, 请检查您的网络连接是否畅通，并检查查询项是否有误!");
        }
        SearchHits hits = result.getHits();
        for (SearchHit hit : hits.getHits()) {
            T entity = JSON.parseObject(hit.getSourceAsString(), classType);
            String id = JSON.parseObject(hit.getSourceAsString()).getString("encounter");

            id = id == null ? userId : id;
            if (!events.containsKey(id))
                events.put(id, new ArrayList<>());
            events.get(id).add(entity);
        }
        return events;
    }

    /**
     * 获取encounter事件，按照date来进行排序
     *
     * @return 事件列表
     * @throws IOException
     */


    @Override
    public Map<String, List<Encounter>> getEncounterEventsByUserId(String userId, Date startDate, Date endDate) {
        HashMap<String, SortOrder> fieldOrder = new HashMap<>();
        fieldOrder.put("date", SortOrder.DESC);
        return getSpecificEventsByUserId(userId,
                "encounters",
                "synthea",
                Encounter.class,
                fieldOrder,
                "date",
                startDate, endDate
        );
    }

    /**
     * immunizations，按照date来进行排序
     *
     * @return 事件列表
     * @throws IOException
     */


    @Override
    public Map<String, List<Immunization>> getImmunizationEventsByUserId(String userId, Date startDate, Date endDate) {
        HashMap<String, SortOrder> fieldOrder = new HashMap<>();
        fieldOrder.put("date", SortOrder.DESC);
        return getSpecificEventsByUserId(userId,
                "immunizations",
                "synthea",
                Immunization.class,
                fieldOrder,
                "date",
                startDate, endDate
        );
    }

    /**
     * 获取observations事件，按照date来进行排序
     *
     * @return 事件列表
     * @throws IOException
     */


    @Override
    public Map<String, List<Observation>> getObservationEventsByUserId(String userId, Date startDate, Date endDate) {
        HashMap<String, SortOrder> fieldOrder = new HashMap<>();
        fieldOrder.put("date", SortOrder.DESC);
        return getSpecificEventsByUserId(userId,
                "observation",
                "synthea",
                Observation.class,
                fieldOrder,
                "date",
                startDate, endDate

        );
    }

    /**
     * 获取medication事件，按照start(事件开始事件)来进行排序
     *
     * @return 事件列表
     * @throws IOException
     */


    @Override
    public Map<String, List<Medication>> getMedicationEventsByUserId(String userId, Date startDate, Date endDate) {
        HashMap<String, SortOrder> fieldOrder = new HashMap<>();
        fieldOrder.put("start", SortOrder.DESC);
        return getSpecificEventsByUserId(userId,
                "medications",
                "synthea",
                Medication.class,
                fieldOrder,
                "start",
                startDate, endDate
        );
    }

    /**
     * 获取careplans事件，按照start来进行排序
     *
     * @return 事件列表
     * @throws IOException
     */


    @Override
    public Map<String, List<CarePlan>> getCarePlanEventsByUserId(String userId, Date startDate, Date endDate) {
        HashMap<String, SortOrder> fieldOrder = new HashMap<>();
        fieldOrder.put("start", SortOrder.DESC);
        return getSpecificEventsByUserId(userId,
                "careplans",
                "synthea",
                CarePlan.class,
                fieldOrder,
                "start",
                startDate, endDate
        );
    }

    /**
     * 获取allergies事件，按照start(事件开始事件)来进行排序
     *
     * @return 事件列表
     * @throws IOException
     */


    @Override
    public Map<String, List<Allergy>> getAllergyEventsByUserId(String userId, Date startDate, Date endDate) {
        HashMap<String, SortOrder> fieldOrder = new HashMap<>();
        fieldOrder.put("start", SortOrder.DESC);
        return getSpecificEventsByUserId(userId,
                "allergies",
                "synthea",
                Allergy.class,
                fieldOrder,
                "start",
                startDate, endDate
        );
    }

    /**
     * 获取conditions事件，按照start(事件开始事件)来进行排序
     *
     * @return 事件列表
     * @throws IOException
     */


    @Override
    public Map<String, List<Condition>> getConditionEventsByUserId(String userId, Date startDate, Date endDate) {
        HashMap<String, SortOrder> fieldOrder = new HashMap<>();
        fieldOrder.put("start", SortOrder.DESC);
        return getSpecificEventsByUserId(userId,
                "conditions",
                "synthea",
                Condition.class,
                fieldOrder,
                "start",
                startDate, endDate
        );
    }

    /**
     * 通过UserId来获取用户数据，由于对于每个User来说这个Event只会有唯一的一个，故获取其第一个元素即可
     *
     * @param userId
     * @return 事件列表
     * @throws IOException
     */


    @Override
    public UserBasic getUserBasicByUserId(String userId) {
        Map<String, List<UserBasic>> userMap = getSpecificEventsByUserId(userId,
                "patient",
                "synthea",
                UserBasic.class
        );
        List<UserBasic> userList = userMap.getOrDefault(userId, new ArrayList<>());

        return userList.size() == 0 ? null : userList.get(0);
    }

    /**
     * 获取DiseaseUser某年某地区数量
     */
    @Override
    public ArrayList<String> getDiseaseUserNum_area(String disease, String year) {


        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest("conditions");
        searchRequest.types("synthea");

        sourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("description", disease))
                .must(QueryBuilders.rangeQuery("start").gt(year + "-01-01T00:00:00Z").lt(year + "-12-31T23:59:59Z")));

        sourceBuilder.size(MAX_QUERY_SIZE);
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest);

        } catch (IOException e) {

            e.printStackTrace();
            throw new RuntimeException("连接或查询有误, 请检查您的网络连接是否畅通，并检查查询项是否有误!");
        }

        SearchHits hits = result.getHits();

        ArrayList<Diseaseuser> diseaseusers = new ArrayList<Diseaseuser>();
        for (SearchHit hit : hits.getHits()) {
            Diseaseuser dis = JSON.parseObject(hit.getSourceAsString(), Diseaseuser.class);
            //UserBasic user = getUserBasicByUserId(dis.getUser_id());
            diseaseusers.add(dis);
        }
        ArrayList<String> arrayListNum = new ArrayList<String>();
        ArrayList<String> arrayListMF = new ArrayList<String>();
        for (Diseaseuser user : diseaseusers) {
            String[] array = getUserBasicByUserId(user.getUser_id()).getAddress().split(" ");
            String gender = getUserBasicByUserId(user.getUser_id()).getGender();
            if (array[1] == null || array[1].isEmpty()) {
                array[1] = array[2];
            }
            arrayListNum.add(array[1] + ":" + gender);
            arrayListMF.add(array[1]);
        }
        Set set = new HashSet(arrayListNum);
        ArrayList arrNum = new ArrayList();
        for (Object addr : set) {
            arrNum.add(addr + ":" + Collections.frequency(arrayListNum, addr));
        }
        Collections.sort(arrNum);

        ArrayList<String> arrFM = new ArrayList<String>();
        String addr1;
        String addr2;
        String numberF;
        String numberM;
        for (int i = 0; i < arrNum.size() - 1; i++) {
            addr1 = arrNum.get(i).toString().split(":")[0];
            addr2 = arrNum.get(i + 1).toString().split(":")[0];
            if (addr1.equals(addr2)) {
                numberF = arrNum.get(i).toString().split(":")[2];
                numberM = arrNum.get(i + 1).toString().split(":")[2];
                i = i + 1;
            } else {
                if (arrNum.get(i).toString().split(":")[1].equals("F")) {

                    numberF = arrNum.get(i).toString().split(":")[2];
                    numberM = "0";
                } else {
                    numberF = "0";
                    numberM = arrNum.get(i).toString().split(":")[2];
                }
            }
            arrFM.add(addr1 + ":" + numberF + ":" + numberM);
        }

        if (!arrNum.get(arrNum.size() - 2).toString().split(":")[0].equals(arrNum.get(arrNum.size() - 1).toString().split(":")[0])) {
            if (arrNum.get(arrNum.size() - 1).toString().split(":")[0].equals("F")) {
                numberF = arrNum.get(arrNum.size() - 1).toString().split(":")[2];
                numberM = "0";
            } else {
                numberM = arrNum.get(arrNum.size() - 1).toString().split(":")[2];
                numberF = "0";
            }
            arrFM.add(arrNum.get(arrNum.size() - 1).toString().split(":")[0] + ":" + numberF + ":" + numberM);
        }
        return arrFM;
    }


    /**
     * 获取DiseaseUser某年月数量
     */
    @Override
    public ArrayList<String> getDiseaseUserNum_month(String diseases, String years) {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest("conditions");
        searchRequest.types("synthea");
        sourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("description", diseases))
                .must(QueryBuilders.rangeQuery("start").gt(years + "-01-01T00:00:00Z").lt(years + "-12-31T23:59:59Z")));
        sourceBuilder.size(MAX_QUERY_SIZE);
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("连接或查询有误, 请检查您的网络连接是否畅通，并检查查询项是否有误!");
        }
        SearchHits hits = result.getHits();
        ArrayList<Diseaseuser> diseaseusers = new ArrayList<Diseaseuser>();
        for (SearchHit hit : hits.getHits()) {
            Diseaseuser dis = JSON.parseObject(hit.getSourceAsString(), Diseaseuser.class);
            diseaseusers.add(dis);
        }
        ArrayList<String> arrayListNum = new ArrayList<String>();

        for (Diseaseuser user : diseaseusers) {
            String gender = getUserBasicByUserId(user.getUser_id()).getGender();
            String time = user.getStart().toString().split(" ")[1];
            arrayListNum.add(time + ":" + gender);
        }
        Set set = new HashSet(arrayListNum);
        ArrayList arrNum = new ArrayList();
        for (Object addr : set) {
            arrNum.add(addr + ":" + Collections.frequency(arrayListNum, addr));
        }
        Collections.sort(arrNum);
        Map<String, ArrayList> mapNum = new HashMap<String, ArrayList>();

        for (int i = 0; i < arrNum.size() - 1; i++) {
            String month = arrNum.get(i).toString().split(":")[0];
            switch (month) {
                case "Jan":
                    month = "1";
                    break;
                case "Feb":
                    month = "2";
                    break;
                case "Mar":
                    month = "3";
                    break;
                case "Apr":
                    month = "4";
                    break;
                case "May":
                    month = "5";
                    break;
                case "Jun":
                    month = "6";
                    break;
                case "Jul":
                    month = "7";
                    break;
                case "Aug":
                    month = "8";
                    break;
                case "Sep":
                    month = "9";
                    break;
                case "Oct":
                    month = "10";
                    break;
                case "Nov":
                    month = "11";
                    break;
                case "Dec":
                    month = "12";
                    break;
            }
            String diseaseusernum = arrNum.get(i).toString().split(":")[1] + ":" + arrNum.get(i).toString().split(":")[2];
            if (!mapNum.containsKey(month)) {
                ArrayList array = new ArrayList();
                array.add(diseaseusernum);
                mapNum.put(month, array);
            } else {
                mapNum.get(month).add(diseaseusernum);
            }

        }

        ArrayList returnArraylist = new ArrayList();
        for (String key : mapNum.keySet()) {
            if (mapNum.get(key).size() == 1) {
                if (mapNum.get(key).get(0).toString().split(":")[1].equals("F")) {
                    returnArraylist.add(key + ":" + mapNum.get(key).get(0).toString().split(":")[1] + ":" + "0");
                } else {
                    returnArraylist.add(key + ":" + "0" + ":" + mapNum.get(key).get(0).toString().split(":")[1]);
                }
            } else {
                returnArraylist.add(key + ":" + mapNum.get(key).get(0).toString().split(":")[1] + ":" + mapNum.get(key).get(1).toString().split(":")[1]);
            }
        }
        returnArraylist.sort(new Comparator<String>() {
            public int compare(String o1, String o2) {
                int i1 = Integer.parseInt(o1.split(":")[0]);
                int i2 = Integer.parseInt(o2.split(":")[0]);
                return (i1 - i2);

            }
        });
        return returnArraylist;

    }

    @Override
    public int getDiseaseUserNum_per(String years, String disease) {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest("conditions");
        searchRequest.types("synthea");
        sourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("description", disease))
                .must(QueryBuilders.rangeQuery("start").gt(years + "-01-01T00:00:00Z").lt(years + "-12-31T23:59:59Z")));
        sourceBuilder.size(MAX_QUERY_SIZE);
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("连接或查询有误, 请检查您的网络连接是否畅通，并检查查询项是否有误!");
        }
        SearchHits hits = result.getHits();
        ArrayList<Diseaseuser> diseaseusers = new ArrayList<Diseaseuser>();
        for (SearchHit hit : hits.getHits()) {
            Diseaseuser dis = JSON.parseObject(hit.getSourceAsString(), Diseaseuser.class);
            diseaseusers.add(dis);
        }
        ArrayList<String> arrayListNum = new ArrayList<String>();
        int diseasenum = 0;
        for (Diseaseuser user : diseaseusers) {
            String gender = getUserBasicByUserId(user.getUser_id()).getGender();
            diseasenum++;
        }
        return diseasenum;

    }

    @Override
    public ArrayList<String> getDiseaseUserNum_timeline(String diseases, String years) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest("conditions");
        searchRequest.types("synthea");
        sourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("description", diseases))
                .must(QueryBuilders.rangeQuery("start").gt(years + "-01-01T00:00:00Z").lt(years + "-12-31T23:59:59Z")));
        sourceBuilder.size(MAX_QUERY_SIZE);
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest);

        } catch (IOException e) {

            e.printStackTrace();
            throw new RuntimeException("连接或查询有误, 请检查您的网络连接是否畅通，并检查查询项是否有误!");
        }
        SearchHits hits = result.getHits();
        ArrayList<Diseaseuser> diseaseusers = new ArrayList<Diseaseuser>();
        for (SearchHit hit : hits.getHits()) {
            Diseaseuser dis = JSON.parseObject(hit.getSourceAsString(), Diseaseuser.class);
            diseaseusers.add(dis);
        }
        ArrayList<String> arrayListNum = new ArrayList<String>();

        for (Diseaseuser user : diseaseusers) {
            String[] array = getUserBasicByUserId(user.getUser_id()).getAddress().split(" ");

            if (array[1] == null || array[1].isEmpty()) {
                array[1] = array[2];
            }
            arrayListNum.add(array[1]);

        }
        Set set = new HashSet(arrayListNum);
        ArrayList arrNum = new ArrayList();
        for (Object addr : set) {
            arrNum.add(addr + ":" + Collections.frequency(arrayListNum, addr));
        }
        ArrayList returnarr = new ArrayList();
        for (Signal e : Signal.values()) {
            returnarr.add(e.toString() + ":" + 0);
        }

        for (int i = 0; i < arrNum.size(); i++) {
            for (int j = 0; j < returnarr.size(); j++) {
                String addr1 = arrNum.get(i).toString().split(":")[0];
                String value = arrNum.get(i).toString().split(":")[1];
                String addr2 = returnarr.get(j).toString().split(":")[0];
                if (addr1.equals(addr2)) {
                    returnarr.set(j, addr1 + ":" + value);
                    break;
                }
            }
        }

        return returnarr;
    }


    @Test

    public void getDiseaseUserNum_area_test() {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest("conditions");
        searchRequest.types("synthea");
        sourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("description", "Hypertension"))
                .must(QueryBuilders.rangeQuery("start").gt("2018-01-01T00:00:00Z").lt("2018-12-31T23:59:59Z")));
        sourceBuilder.size(MAX_QUERY_SIZE);
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest);

        } catch (IOException e) {

            e.printStackTrace();
            throw new RuntimeException("连接或查询有误, 请检查您的网络连接是否畅通，并检查查询项是否有误!");
        }
        SearchHits hits = result.getHits();
        ArrayList<Diseaseuser> diseaseusers = new ArrayList<Diseaseuser>();
        for (SearchHit hit : hits.getHits()) {
            Diseaseuser dis = JSON.parseObject(hit.getSourceAsString(), Diseaseuser.class);
            diseaseusers.add(dis);
        }
        ArrayList<String> arrayListNum = new ArrayList<String>();

        for (Diseaseuser user : diseaseusers) {
            String[] array = getUserBasicByUserId(user.getUser_id()).getAddress().split(" ");

            if (array[1] == null || array[1].isEmpty()) {
                array[1] = array[2];
            }
            if (array[1].length() != 2) {
                array[1] = array[1].substring(0, 2);
            }
            arrayListNum.add(array[1]);

        }
        Set set = new HashSet(arrayListNum);
        ArrayList arrNum = new ArrayList();
        for (Object addr : set) {
            arrNum.add(addr + ":" + Collections.frequency(arrayListNum, addr));
        }

        ArrayList returnarr = new ArrayList();
        for (Signal e : Signal.values()) {
            returnarr.add(e.toString() + ":" + 0);
        }

        for (int i = 0; i < arrNum.size(); i++) {
            for (int j = 0; j < returnarr.size(); j++) {
                String addr1 = arrNum.get(i).toString().split(":")[0];
                String value = arrNum.get(i).toString().split(":")[1];
                String addr2 = returnarr.get(j).toString().split(":")[0];
                if (addr1.equals(addr2)) {
                    returnarr.set(j, addr1 + ":" + value);
                    break;
                }
            }
        }
        System.out.println(arrNum);
        System.out.println(returnarr);

    }


    @Test
    public void getDiseaseUserNumTest1() {

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest("conditions");
        searchRequest.types("synthea");
        sourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("description", "Hypertension"))
                .must(QueryBuilders.rangeQuery("start").gt("2018-01-01T00:00:00Z").lt("2018-12-31T23:59:59Z")));
        sourceBuilder.size(MAX_QUERY_SIZE);
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("连接或查询有误, 请检查您的网络连接是否畅通，并检查查询项是否有误!");
        }
        SearchHits hits = result.getHits();
        ArrayList<Diseaseuser> diseaseusers = new ArrayList<Diseaseuser>();
        for (SearchHit hit : hits.getHits()) {
            Diseaseuser dis = JSON.parseObject(hit.getSourceAsString(), Diseaseuser.class);
            diseaseusers.add(dis);
        }
        ArrayList<String> arrayListNum = new ArrayList<String>();
        int i = 0;
        for (Diseaseuser user : diseaseusers) {
            String gender = getUserBasicByUserId(user.getUser_id()).getGender();
            i++;
        }
        System.out.print(i);

    }


    @Override
    public ArrayList<UserBasic> searchUserByConditions(String ageStart, String ageEnd, String gender) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest("patient");
        searchRequest.types("synthea");

        Objects.requireNonNull(gender);

        if (!"all".equals(gender)) {
            //如果传入起始时间，就加入起始时间作为筛选条件
            // start < 生日 < end - 死期[错误]     start < 生日 - 死期 < end
            sourceBuilder.query(QueryBuilders.boolQuery()
                    .filter(QueryBuilders.boolQuery())
                    .must(QueryBuilders.matchQuery("gender", gender)));
        } else {
            sourceBuilder.query(QueryBuilders.matchAllQuery());
        }

        sourceBuilder.size(MAX_QUERY_SIZE);
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("连接或查询有误, 请检查您的网络连接是否畅通，并检查查询项是否有误!");
        }
        SearchHits hits = result.getHits();
        ArrayList<UserBasic> userBasics = new ArrayList<>();
        for (SearchHit hit : hits.getHits()) {
            UserBasic dis = JSON.parseObject(hit.getSourceAsString(), UserBasic.class);
            Date endDate = Optional.ofNullable(dis.getDeathdate()).orElse(Date.from(Instant.now()));

            int year = endDate.getYear() - dis.getBirthdate().getYear();
            if (year < Integer.parseInt(ageEnd) && year > Integer.parseInt(ageStart))
                userBasics.add(dis);
        }
        return userBasics;
    }


    @Override
    public ArrayList<Condition> searchCondition(List<String> conditions) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest("conditions");
        searchRequest.types("synthea");
        BoolQueryBuilder filterRootQuery = QueryBuilders.boolQuery();
        BoolQueryBuilder queryBuilder = new BoolQueryBuilder();
        if (conditions == null ||
            conditions.size() == 0 ||
            (conditions.size() == 1 && "all".equals(conditions.get(0)))) {
            queryBuilder.must(QueryBuilders.matchAllQuery());
        } else {
            for (String condition : conditions) {
                queryBuilder.should(QueryBuilders.termQuery("description.keyword", condition));
            }
        }

        sourceBuilder.query(filterRootQuery.filter(queryBuilder));
        sourceBuilder.size(MAX_QUERY_SIZE);
        searchRequest.source(sourceBuilder);
        SearchResponse result = null;
        try {
            result = client.search(searchRequest);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("连接或查询有误, 请检查您的网络连接是否畅通，并检查查询项是否有误!");
        }
        SearchHits hits = result.getHits();
        ArrayList<Condition> diseaseusers = new ArrayList<Condition>();
        for (SearchHit hit : hits.getHits()) {
            Condition dis = JSON.parseObject(hit.getSourceAsString(), Condition.class);
            diseaseusers.add(dis);
        }
        return diseaseusers;
    }


    @Override
    public List<QueryResult> searchMetric(Long start, Long end, List<String> metrics, String userId) {
        HiTSDB hiTSDB = HiTSDBClientFactory.connect(hiTSDBConfig);
        Query query = new Query();//Query.timeRange(start, end)
//                .sub(SubQuery.metric("heart_rate").aggregator(Aggregator.FIRST).tag("userId", "the-user-0").build()).build();
        Query.Builder builder = query.timeRange(start, end);
//                .sub(SubQuery.metric("heart_rate").aggregator(Aggregator.FIRST).tag("userId", "the-user-0").build());
        if (metrics != null && metrics.size() > 0) {
            for (String metric : metrics) {
                builder = builder.sub(SubQuery.metric(metric).aggregator(Aggregator.FIRST).tag("userId", userId).build());
            }
        }
        query = builder.build();
        List<QueryResult> result = hiTSDB.query(query);
        System.out.println("==============" + result);
        System.out.println("==============" + result.size());
        return result;
    }
}