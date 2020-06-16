package com.atguigu.springboot.bean;

import java.sql.Date;

public class Attendance {
    private int attendance_id;
    private int emp_id;
    private String emp_name;
    private double leave_time;//缺勤
    private Date time;
    private double over_time;//加班
    private double off_time;//请假

    public int getAttendance_id() {
        return attendance_id;
    }

    public void setAttendance_id(int attendance_id) {
        this.attendance_id = attendance_id;
    }

    public int getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }



    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    public double getLeave_time() {
        return leave_time;
    }

    public void setLeave_time(double leave_time) {
        this.leave_time = leave_time;
    }

    public double getOver_time() {
        return over_time;
    }

    public void setOver_time(double over_time) {
        this.over_time = over_time;
    }

    public double getOff_time() {
        return off_time;
    }

    public void setOff_time(double off_time) {
        this.off_time = off_time;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "attendance_id=" + attendance_id +
                ", emp_id=" + emp_id +
                ", emp_name='" + emp_name + '\'' +
                ", leave_time=" + leave_time +
                ", time=" + time +
                ", over_time=" + over_time +
                ", off_time=" + off_time +
                '}';
    }
}
