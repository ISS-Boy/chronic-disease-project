package com.iss.bigdata.health.elasticsearch.help;

import com.iss.bigdata.health.elasticsearch.entity.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dujijun on 2018/1/4.
 */
public class EventMap extends HashMap<String, Map<String, ?>> {

    public enum EventType{
        ALLERGY, CAREPLAN, CONDITION, ENCOUNTER,
        IMMUNIZATION, MEDICATION, OBSERVATION, USERBASIC
    }

    public static Class getEventClassFromEnum(EventType type) {
        switch (type) {
            case ALLERGY:
                return Allergy.class;
            case CAREPLAN:
                return CarePlan.class;
            case CONDITION:
                return Condition.class;
            case ENCOUNTER:
                return Encounter.class;
            case IMMUNIZATION:
                return Immunization.class;
            case MEDICATION:
                return Medication.class;
            case OBSERVATION:
                return Observation.class;
            case USERBASIC:
                return UserBasic.class;
            default:
                return null;
        }
    }

    /**
     * 通过使用特性的类型参数获取事件列表
     * @param classType 通过调用 getEventClassFromString或getEventClassFromEnum获取Class类型
     * @param key String类型的key
     * @param <T> class的类型
     * @return
     */
    @Deprecated
    public <T> List<Event<T>> getEventList(Class<T> classType, String key){
        return (List<Event<T>>)super.get(key);
    }

    /**
     * 通过使用特性的类型参数获取事件列表
     * @param classType 通过调用 getEventClassFromString或getEventClassFromEnum获取Class类型
     * @param key String类型的key
     * @param <T> class的类型
     * @return 事件Map
     */
    public <T> Map<String, List<T>> getEventMap(Class<T> classType, String key){
        return (Map<String, List<T>>)super.get(key);
    }

    @Override
    public Map<String, ?> get(Object key) {
        throw new UnsupportedOperationException("不允许直接获取EventList");
    }


    /**
     *
     * @param type
     * @return
     */
    public static Class getEventClassFromString(String type){
        switch (type) {
            case "allergies":
                return Allergy.class;
            case "careplans":
                return CarePlan.class;
            case "conditions":
                return Condition.class;
            case "encounters":
                return Encounter.class;
            case "immunizations":
                return Immunization.class;
            case "medications":
                return Medication.class;
            case "observations":
                return Observation.class;
            case "patients":
                return UserBasic.class;
            default:
                return null;
        }
    }
}
