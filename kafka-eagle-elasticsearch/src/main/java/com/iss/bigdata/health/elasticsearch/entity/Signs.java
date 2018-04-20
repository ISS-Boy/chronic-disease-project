package com.iss.bigdata.health.elasticsearch.entity;

public class Signs {

    private int creatinine;
    private double body_mass_index;
    private double body_weight;
    private int systolic_blood_pressure;
    private double calcium;
    private int low_density_lipoprotein_cholesterol;
    private int triglycerides;
    private int glucose;
    private int microalbumin_creatinine_ratio;
    private int urea_uitrogen;
    private int carbon_dioxide;
    private double hemoglobin_A1c_or_hemoglobin_total_in_blood;
    private int chloride;
    private int estimated_glomerular_filtration_rate;
    private int sodium;
    private int high_density_lipoprotein_cholesterol;
    private double body_height;
    private double potassium;
    private int total_cholesterol;



    public Signs() {
    }

    public Signs(int creatinine, double body_mass_index, double body_weight, int systolic_blood_pressure, double calcium, int low_density_lipoprotein_cholesterol, int triglycerides, int glucose, int microalbumin_creatinine_ratio, int urea_uitrogen, int carbon_dioxide, double hemoglobin_A1c_or_hemoglobin_total_in_blood, int chloride, int estimated_glomerular_filtration_rate, int sodium, int high_density_lipoprotein_cholesterol, double body_height, double potassium, int total_cholesterol) {
        this.creatinine = creatinine;
        this.body_mass_index = body_mass_index;
        this.body_weight = body_weight;
        this.systolic_blood_pressure = systolic_blood_pressure;
        this.calcium = calcium;
        this.low_density_lipoprotein_cholesterol = low_density_lipoprotein_cholesterol;
        this.triglycerides = triglycerides;
        this.glucose = glucose;
        this.microalbumin_creatinine_ratio = microalbumin_creatinine_ratio;
        this.urea_uitrogen = urea_uitrogen;
        this.carbon_dioxide = carbon_dioxide;
        this.hemoglobin_A1c_or_hemoglobin_total_in_blood = hemoglobin_A1c_or_hemoglobin_total_in_blood;
        this.chloride = chloride;
        this.estimated_glomerular_filtration_rate = estimated_glomerular_filtration_rate;
        this.sodium = sodium;
        this.high_density_lipoprotein_cholesterol = high_density_lipoprotein_cholesterol;
        this.body_height = body_height;
        this.potassium = potassium;
        this.total_cholesterol = total_cholesterol;
    }

    public int getCreatinine() {
        return creatinine;
    }

    public void setCreatinine(int creatinine) {
        this.creatinine = creatinine;
    }

    public double getBody_mass_index() {
        return body_mass_index;
    }

    public void setBody_mass_index(double body_mass_index) {
        this.body_mass_index = body_mass_index;
    }

    public double getBody_weight() {
        return body_weight;
    }

    public void setBody_weight(double body_weight) {
        this.body_weight = body_weight;
    }

    public int getSystolic_blood_pressure() {
        return systolic_blood_pressure;
    }

    public void setSystolic_blood_pressure(int systolic_blood_pressure) {
        this.systolic_blood_pressure = systolic_blood_pressure;
    }

    public double getCalcium() {
        return calcium;
    }

    public void setCalcium(double calcium) {
        this.calcium = calcium;
    }

    public int getLow_density_lipoprotein_cholesterol() {
        return low_density_lipoprotein_cholesterol;
    }

    public void setLow_density_lipoprotein_cholesterol(int low_density_lipoprotein_cholesterol) {
        this.low_density_lipoprotein_cholesterol = low_density_lipoprotein_cholesterol;
    }

    public int getTriglycerides() {
        return triglycerides;
    }

    public void setTriglycerides(int triglycerides) {
        this.triglycerides = triglycerides;
    }

    public int getGlucose() {
        return glucose;
    }

    public void setGlucose(int glucose) {
        this.glucose = glucose;
    }

    public int getMicroalbumin_creatinine_ratio() {
        return microalbumin_creatinine_ratio;
    }

    public void setMicroalbumin_creatinine_ratio(int microalbumin_creatinine_ratio) {
        this.microalbumin_creatinine_ratio = microalbumin_creatinine_ratio;
    }

    public int getUrea_uitrogen() {
        return urea_uitrogen;
    }

    public void setUrea_uitrogen(int urea_uitrogen) {
        this.urea_uitrogen = urea_uitrogen;
    }

    public int getCarbon_dioxide() {
        return carbon_dioxide;
    }

    public void setCarbon_dioxide(int carbon_dioxide) {
        this.carbon_dioxide = carbon_dioxide;
    }

    public double getHemoglobin_A1c_or_hemoglobin_total_in_blood() {
        return hemoglobin_A1c_or_hemoglobin_total_in_blood;
    }

    public void setHemoglobin_A1c_or_hemoglobin_total_in_blood(double hemoglobin_A1c_or_hemoglobin_total_in_blood) {
        this.hemoglobin_A1c_or_hemoglobin_total_in_blood = hemoglobin_A1c_or_hemoglobin_total_in_blood;
    }

    public int getChloride() {
        return chloride;
    }

    public void setChloride(int chloride) {
        this.chloride = chloride;
    }

    public int getEstimated_glomerular_filtration_rate() {
        return estimated_glomerular_filtration_rate;
    }

    public void setEstimated_glomerular_filtration_rate(int estimated_glomerular_filtration_rate) {
        this.estimated_glomerular_filtration_rate = estimated_glomerular_filtration_rate;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getHigh_density_lipoprotein_cholesterol() {
        return high_density_lipoprotein_cholesterol;
    }

    public void setHigh_density_lipoprotein_cholesterol(int high_density_lipoprotein_cholesterol) {
        this.high_density_lipoprotein_cholesterol = high_density_lipoprotein_cholesterol;
    }

    public double getBody_height() {
        return body_height;
    }

    public void setBody_height(double body_height) {
        this.body_height = body_height;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public int getTotal_cholesterol() {
        return total_cholesterol;
    }

    public void setTotal_cholesterol(int total_cholesterol) {
        this.total_cholesterol = total_cholesterol;
    }

    @Override
    public String toString() {
        return "Signs{" +
                "creatinine=" + creatinine +
                ", body_mass_index=" + body_mass_index +
                ", body_weight=" + body_weight +
                ", systolic_blood_pressure=" + systolic_blood_pressure +
                ", calcium=" + calcium +
                ", low_density_lipoprotein_cholesterol=" + low_density_lipoprotein_cholesterol +
                ", triglycerides=" + triglycerides +
                ", glucose=" + glucose +
                ", microalbumin_creatinine_ratio=" + microalbumin_creatinine_ratio +
                ", urea_uitrogen=" + urea_uitrogen +
                ", carbon_dioxide=" + carbon_dioxide +
                ", hemoglobin_A1c_or_hemoglobin_total_in_blood=" + hemoglobin_A1c_or_hemoglobin_total_in_blood +
                ", chloride=" + chloride +
                ", estimated_glomerular_filtration_rate=" + estimated_glomerular_filtration_rate +
                ", sodium=" + sodium +
                ", high_density_lipoprotein_cholesterol=" + high_density_lipoprotein_cholesterol +
                ", body_height=" + body_height +
                ", potassium=" + potassium +
                ", total_cholesterol=" + total_cholesterol +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Signs signs = (Signs) o;

        if (creatinine != signs.creatinine) return false;
        if (Double.compare(signs.body_mass_index, body_mass_index) != 0) return false;
        if (Double.compare(signs.body_weight, body_weight) != 0) return false;
        if (systolic_blood_pressure != signs.systolic_blood_pressure) return false;
        if (Double.compare(signs.calcium, calcium) != 0) return false;
        if (low_density_lipoprotein_cholesterol != signs.low_density_lipoprotein_cholesterol) return false;
        if (triglycerides != signs.triglycerides) return false;
        if (glucose != signs.glucose) return false;
        if (microalbumin_creatinine_ratio != signs.microalbumin_creatinine_ratio) return false;
        if (urea_uitrogen != signs.urea_uitrogen) return false;
        if (carbon_dioxide != signs.carbon_dioxide) return false;
        if (Double.compare(signs.hemoglobin_A1c_or_hemoglobin_total_in_blood, hemoglobin_A1c_or_hemoglobin_total_in_blood) != 0)
            return false;
        if (chloride != signs.chloride) return false;
        if (estimated_glomerular_filtration_rate != signs.estimated_glomerular_filtration_rate) return false;
        if (sodium != signs.sodium) return false;
        if (high_density_lipoprotein_cholesterol != signs.high_density_lipoprotein_cholesterol) return false;
        if (Double.compare(signs.body_height, body_height) != 0) return false;
        if (Double.compare(signs.potassium, potassium) != 0) return false;
        return total_cholesterol == signs.total_cholesterol;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = creatinine;
        temp = Double.doubleToLongBits(body_mass_index);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(body_weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + systolic_blood_pressure;
        temp = Double.doubleToLongBits(calcium);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + low_density_lipoprotein_cholesterol;
        result = 31 * result + triglycerides;
        result = 31 * result + glucose;
        result = 31 * result + microalbumin_creatinine_ratio;
        result = 31 * result + urea_uitrogen;
        result = 31 * result + carbon_dioxide;
        temp = Double.doubleToLongBits(hemoglobin_A1c_or_hemoglobin_total_in_blood);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + chloride;
        result = 31 * result + estimated_glomerular_filtration_rate;
        result = 31 * result + sodium;
        result = 31 * result + high_density_lipoprotein_cholesterol;
        temp = Double.doubleToLongBits(body_height);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(potassium);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + total_cholesterol;
        return result;
    }
}
