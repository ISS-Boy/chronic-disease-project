package org.smartloli.kafka.eagle.web.pojo;

import java.util.Date;

/**
 * Created by weidaping on 2018/5/3.
 */
public class LearningConfigure {
    private String configureId;
    private String configureName;
    private String age;
    private String gender;
    private String disease;
    private String metric;
    private String dateBegin;
    private String dateEnd;
    private Integer slidingWindowSize;
    private Integer paaSize;
    private Integer alphabetSize;
    private Integer analysisWindowStartSize;
    private Integer frequencyThreshold;
    private Integer rThreshold;
    private Integer k;
    private String isDone;

    public String getConfigureId() {
        return configureId;
    }

    public LearningConfigure setConfigureId(String configureId) {
        this.configureId = configureId;
        return this;
    }

    public String getConfigureName() {
        return configureName;
    }

    public LearningConfigure setConfigureName(String configureName) {
        this.configureName = configureName;
        return this;
    }

    public String getAge() {
        return age;
    }

    public LearningConfigure setAge(String age) {
        this.age = age;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public LearningConfigure setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getDisease() {
        return disease;
    }

    public LearningConfigure setDisease(String disease) {
        this.disease = disease;
        return this;
    }

    public String getMetric() {
        return metric;
    }

    public LearningConfigure setMetric(String metric) {
        this.metric = metric;
        return this;
    }

    public String getDateBegin() {
        return dateBegin;
    }

    public LearningConfigure setDateBegin(String dateBegin) {
        this.dateBegin = dateBegin;
        return this;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public LearningConfigure setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public Integer getSlidingWindowSize() {
        return slidingWindowSize;
    }

    public LearningConfigure setSlidingWindowSize(Integer slidingWindowSize) {
        this.slidingWindowSize = slidingWindowSize;
        return this;
    }

    public Integer getPaaSize() {
        return paaSize;
    }

    public LearningConfigure setPaaSize(Integer paaSize) {
        this.paaSize = paaSize;
        return this;
    }

    public Integer getAlphabetSize() {
        return alphabetSize;
    }

    public LearningConfigure setAlphabetSize(Integer alphabetSize) {
        this.alphabetSize = alphabetSize;
        return this;
    }

    public Integer getAnalysisWindowStartSize() {
        return analysisWindowStartSize;
    }

    public LearningConfigure setAnalysisWindowStartSize(Integer analysisWindowStartSize) {
        this.analysisWindowStartSize = analysisWindowStartSize;
        return this;
    }

    public Integer getFrequencyThreshold() {
        return frequencyThreshold;
    }

    public LearningConfigure setFrequencyThreshold(Integer frequencyThreshold) {
        this.frequencyThreshold = frequencyThreshold;
        return this;
    }

    public Integer getrThreshold() {
        return rThreshold;
    }

    public LearningConfigure setrThreshold(Integer rThreshold) {
        this.rThreshold = rThreshold;
        return this;
    }

    public Integer getK() {
        return k;
    }

    public LearningConfigure setK(Integer k) {
        this.k = k;
        return this;
    }

    public String getIsDone() {
        return isDone;
    }

    public void setIsDone(String isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LearningConfigure{");
        sb.append("configureId='").append(configureId).append('\'');
        sb.append(", configureName='").append(configureName).append('\'');
        sb.append(", age='").append(age).append('\'');
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", disease='").append(disease).append('\'');
        sb.append(", metric='").append(metric).append('\'');
        sb.append(", dateBegin=").append(dateBegin);
        sb.append(", dateEnd=").append(dateEnd);
        sb.append(", slidingWindowSize=").append(slidingWindowSize);
        sb.append(", paaSize=").append(paaSize);
        sb.append(", alphabetSize=").append(alphabetSize);
        sb.append(", analysisWindowStartSize=").append(analysisWindowStartSize);
        sb.append(", frequencyThreshold=").append(frequencyThreshold);
        sb.append(", rThreshold=").append(rThreshold);
        sb.append(", k=").append(k);
        sb.append(", isDone='").append(isDone).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
