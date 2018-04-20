package org.smartloli.kafka.eagle.grafana.Parameter;

import java.util.List;

public class PARMOfPanel {
    private int panelId;

    private String title;

    private String type;

    private String displaytype;
    
    private List<PARMOfTarget> targetslist;

    public PARMOfPanel(int panelId, String title, String type, String displaytype,List<PARMOfTarget> targets) {
        this.panelId = panelId;
        this.title = title;
        this.type = type;
        this.displaytype=displaytype;
        this.targetslist = targets;
    }

    public PARMOfPanel() {
    }

    @Override
    public String toString() {
        return "Panel{" +
                "panelId='" + panelId + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", targets=" + targetslist +
                '}';
    }

    public int getPanelId() {
        return panelId;
    }

    public void setPanelId(int panelId) {
        this.panelId = panelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PARMOfTarget> getTargets() {
        return targetslist;
    }

    public void setTargets(List<PARMOfTarget> targetslist) {
        this.targetslist = targetslist;
    }
    
    public void setDisplaytype(String displaytype) {
        this.displaytype = displaytype;
    }

    public String getDisplaytype() {
        return displaytype;
    }
   
}
