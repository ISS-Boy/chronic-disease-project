package org.smartloli.kafka.eagle.grafana.JavaBean;

import java.util.List;

public class Rows {
    private Rows rows;
    private boolean collapse;
    private String height;
    private List<Panels> panels;
    private String repeat;
    private String repeatIteration;
    private String repeatRowId;
    private boolean showTitle;
    private String title;
    private String titleSize;
    public void setCollapse(boolean collapse) {
         this.collapse = collapse;
     }
     public boolean getCollapse() {
         return collapse;
     }

    public void setHeight(String height) {
         this.height = height;
     }
     public String getHeight() {
         return height;
     }

    public void setPanels(List<Panels> panels) {
         this.panels = panels;
     }
     public List<Panels> getPanels() {
         return panels;
     }

    public void setRepeat(String repeat) {
         this.repeat = repeat;
     }
     public String getRepeat() {
         return repeat;
     }

    public void setRepeatIteration(String repeatIteration) {
         this.repeatIteration = repeatIteration;
     }
     public String getRepeatIteration() {
         return repeatIteration;
     }

    public void setRepeatRowId(String repeatRowId) {
         this.repeatRowId = repeatRowId;
     }
     public String getRepeatRowId() {
         return repeatRowId;
     }

    public void setShowTitle(boolean showTitle) {
         this.showTitle = showTitle;
     }
     public boolean getShowTitle() {
         return showTitle;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setTitleSize(String titleSize) {
         this.titleSize = titleSize;
     }
     public String getTitleSize() {
         return titleSize;
     }
     public Rows returnRows(Rows r) {
    	 this.rows=r;
		return rows;
		
	}
}
