package org.smartloli.kafka.eagle.grafana.Parameter;

import java.util.List;

public class PARAMOfDashboard {
    private String dashboardName;

    private String from;

    private String to;

    private List<PARMOfPanel> panels;

    public PARAMOfDashboard(String dashboardName, String from, String to, List<PARMOfPanel> panels) {
        this.dashboardName = dashboardName;
        this.from = from;
        this.to = to;
        this.panels = panels;
    }

    @Override
    public String toString() {
        return "PARAMOfDashboard{" +
                "dashboardName='" + dashboardName + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", panels=" + panels +
                '}';
    }

    public String getDashboardName() {
        return dashboardName;
    }

    public void setDashboardName(String dashboardName) {
        this.dashboardName = dashboardName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<PARMOfPanel> getPanels() {
        return panels;
    }

    public void setPanels(List<PARMOfPanel> panels) {
        this.panels = panels;
    }

    public PARAMOfDashboard() {
    }
}
