package com.atguigu.springboot.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Attendancesummary {
    private Integer Num;
    private Map<String, List<Attendance>> summaryMap;
    private List<List<Double>> doublelist;
    private ArrayList month;
    private ArrayList name;

    public Integer getNum() {
        return Num;
    }

    public void setNum(Integer num) {
        Num = num;
    }

    public Map<String, List<Attendance>> getSummaryMap() {
        return summaryMap;
    }

    public void setSummaryMap(Map<String, List<Attendance>> summaryMap) {
        this.summaryMap = summaryMap;
    }

    public List<List<Double>> getDoublelist() {
        return doublelist;
    }

    public void setDoublelist(List<List<Double>> doublelist) {
        this.doublelist = doublelist;
    }


    public ArrayList getMonth() {
        return month;
    }

    public void setMonth(ArrayList month) {
        this.month = month;
    }

    public ArrayList getName() {
        return name;
    }

    public void setName(ArrayList name) {
        this.name = name;
    }
}
