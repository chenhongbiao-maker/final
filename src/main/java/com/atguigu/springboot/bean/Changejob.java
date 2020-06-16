package com.atguigu.springboot.bean;

import java.sql.Date;

public class Changejob {
    private Date change_date;
    private String emp_name;
    private String bedepartment_name;
    private String bejob_name;
    private String afdepartment_name;
    private String afjob_name;
    private int emp_id;
    private int change_id;

    public int getChange_id() {
        return change_id;
    }

    public void setChange_id(int change_id) {
        this.change_id = change_id;
    }

    public Date getChange_date() {
        return change_date;
    }

    public void setChange_date(Date change_date) {
        this.change_date = change_date;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getBedepartment_name() {
        return bedepartment_name;
    }

    public void setBedepartment_name(String bedepartment_name) {
        this.bedepartment_name = bedepartment_name;
    }

    public String getBejob_name() {
        return bejob_name;
    }

    public void setBejob_name(String bejob_name) {
        this.bejob_name = bejob_name;
    }

    public String getAfdepartment_name() {
        return afdepartment_name;
    }

    public void setAfdepartment_name(String afdepartment_name) {
        this.afdepartment_name = afdepartment_name;
    }

    public String getAfjob_name() {
        return afjob_name;
    }

    public void setAfjob_name(String afjob_name) {
        this.afjob_name = afjob_name;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }
}
