package org.smartloli.kafka.eagle.web.json.pojo;
import java.util.List;

/**
 * Auto-generated: 2018-04-24 20:24:26
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class BlockGroup {

    private String blockGroupName;
    private List<BlockValues> blockValues;
    public void setBlockGroupName(String blockGroupName) {
        this.blockGroupName = blockGroupName;
    }
    public String getBlockGroupName() {
        return blockGroupName;
    }

    public void setBlockValues(List<BlockValues> blockValues) {
        this.blockValues = blockValues;
    }
    public List<BlockValues> getBlockValues() {
        return blockValues;
    }

    @Override
    public String toString() {
        return "BlockGroup{" +
                "blockGroupName='" + blockGroupName + '\'' +
                ", blockValues=" + blockValues +
                '}';
    }
}