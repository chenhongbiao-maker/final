package com.atguigu.springboot.bean;

import java.sql.Date;

public class Salary {
    private int emp_id;
    private  int salary_id;
    private Date time;
    private double salary;
    private double resalary;
    private double leave_salary;
    private double overtime_salary;
    private String emp_name;
    private String month;

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getResalary() {
        return resalary;
    }

    public void setResalary(double resalary) {
        this.resalary = resalary;
    }

    public double getLeave_salary() {
        return leave_salary;
    }

    public void setLeave_salary(double leave_salary) {
        this.leave_salary = leave_salary;
    }

    public double getOvertime_salary() {
        return overtime_salary;
    }

    public void setOvertime_salary(double overtime_salary) {
        this.overtime_salary = overtime_salary;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }


    public int getSalary_id() {
        return salary_id;
    }

    public void setSalary_id(int salary_id) {
        this.salary_id = salary_id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "emp_id=" + emp_id +
                ", salary_id=" + salary_id +
                ", time=" + time +
                ", salary=" + salary +
                ", resalary=" + resalary +
                ", leave_salary=" + leave_salary +
                ", overtime_salary=" + overtime_salary +
                ", emp_name='" + emp_name + '\'' +
                ", month='" + month + '\'' +
                '}';
    }
}
