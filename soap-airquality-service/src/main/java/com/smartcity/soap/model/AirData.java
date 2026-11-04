package com.smartcity.soap.model;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="AirData")
@XmlAccessorType(XmlAccessType.FIELD)
public class AirData {

    private int aqi;
    private double NO2;
    private double CO2;
    private double O3;

    public AirData() {}

    public AirData(int aqi, double nO2, double cO2, double o3) {
        this.aqi = aqi;
        this.NO2 = nO2;
        this.CO2 = cO2;
        this.O3 = o3;
    }

    public int getAqi() { return aqi; }
    public double getNO2() { return NO2; }
    public double getCO2() { return CO2; }
    public double getO3() { return O3; }

    public void setAqi(int aqi) { this.aqi = aqi; }
    public void setNO2(double nO2) { this.NO2 = nO2; }
    public void setCO2(double cO2) { this.CO2 = cO2; }
    public void setO3(double o3) { this.O3 = o3; }
}
