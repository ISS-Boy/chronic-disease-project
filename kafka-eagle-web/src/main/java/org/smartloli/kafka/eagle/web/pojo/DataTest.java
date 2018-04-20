package org.smartloli.kafka.eagle.web.pojo;

/**
 * The DataTest entity corresponds to the datatest table in the database.
 *@author LH
 * **/
import com.google.gson.Gson;

public class DataTest {
    private int id;
    private int dNumb;
    private String name;
    private String description;
    private String value;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getdNumb() {
        return dNumb;
    }

    public void setdNumb(int dNumb) {
        this.dNumb = dNumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString()  {
        return new Gson().toJson(this);
    }


}
