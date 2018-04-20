package org.smartloli.kafka.eagle.grafana.JavaBean;

public class List {

    private int builtIn;
    private String datasource;
    private boolean enable;
    private boolean hide;
    private String iconColor;
    private String name;
    private String type;
    public void setBuiltIn(int builtIn) {
         this.builtIn = builtIn;
     }
     public int getBuiltIn() {
         return builtIn;
     }

    public void setDatasource(String datasource) {
         this.datasource = datasource;
     }
     public String getDatasource() {
         return datasource;
     }

    public void setEnable(boolean enable) {
         this.enable = enable;
     }
     public boolean getEnable() {
         return enable;
     }

    public void setHide(boolean hide) {
         this.hide = hide;
     }
     public boolean getHide() {
         return hide;
     }

    public void setIconColor(String iconColor) {
         this.iconColor = iconColor;
     }
     public String getIconColor() {
         return iconColor;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

}
