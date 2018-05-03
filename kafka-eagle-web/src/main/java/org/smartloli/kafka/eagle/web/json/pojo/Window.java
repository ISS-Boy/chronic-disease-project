package org.smartloli.kafka.eagle.web.json.pojo;

/**
 * Auto-generated: 2018-04-24 20:24:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Window {

    private String windowType;
    private String windowInterval;
    private String windowLength;
    public void setWindowType(String windowType) {
        this.windowType = windowType;
    }
    public String getWindowType() {
        return windowType;
    }

    public void setWindowInterval(String windowInterval) {
        this.windowInterval = windowInterval;
    }
    public String getWindowInterval() {
        return windowInterval;
    }

    public void setWindowLength(String windowLength) {
        this.windowLength = windowLength;
    }
    public String getWindowLength() {
        return windowLength;
    }

    @Override
    public String toString() {
        return "Window{" +
                "windowType='" + windowType + '\'' +
                ", windowInterval='" + windowInterval + '\'' +
                ", windowLength='" + windowLength + '\'' +
                '}';
    }
}