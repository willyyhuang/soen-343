package com.project.SmartHomeSimulator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.jackson.JsonComponent;

import java.util.List;

@JsonComponent
@JsonIgnoreProperties
public class Zone {
    private String name;
    private List<String> roomsInZone;
    private int nonEmptyTemp;
    private int emptyTemp;
    private String period1;
    private int period1Temp;
    private String period2;
    private int period2Temp;
    private String period3;
    private int period3Temp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getRoomsInZone() {
        return roomsInZone;
    }

    public int getNonEmptyTemp() {
        return nonEmptyTemp;
    }

    public void setNonEmptyTemp(int nonEmptyTemp) {
        this.nonEmptyTemp = nonEmptyTemp;
    }

    public int getEmptyTemp() {
        return emptyTemp;
    }

    public void setEmptyTemp(int emptyTemp) {
        this.emptyTemp = emptyTemp;
    }

    public void setRoomsInZone(List<String> roomsInZone) {
        this.roomsInZone = roomsInZone;
    }

    public String getPeriod1() {
        return period1;
    }

    public void setPeriod1(String period1) {
        this.period1 = period1;
    }

    public int getPeriod1Temp() {
        return period1Temp;
    }

    public void setPeriod1Temp(int period1Temp) {
        this.period1Temp = period1Temp;
    }

    public String getPeriod2() {
        return period2;
    }

    public void setPeriod2(String period2) {
        this.period2 = period2;
    }

    public int getPeriod2Temp() {
        return period2Temp;
    }

    public void setPeriod2Temp(int period2Temp) {
        this.period2Temp = period2Temp;
    }

    public String getPeriod3() {
        return period3;
    }

    public void setPeriod3(String period3) {
        this.period3 = period3;
    }

    public int getPeriod3Temp() {
        return period3Temp;
    }

    public void setPeriod3Temp(int period3Temp) {
        this.period3Temp = period3Temp;
    }
}
