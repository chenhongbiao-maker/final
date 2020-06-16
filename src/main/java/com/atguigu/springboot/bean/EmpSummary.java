package com.atguigu.springboot.bean;

public class EmpSummary {
    private String sexname;
    private int sexnum;


    public EmpSummary(String sexname, int sexnum) {
        this.sexname = sexname;
        this.sexnum = sexnum;
    }


    public String getSexname() {
        return sexname;
    }

    public void setSexname(String sexname) {
        this.sexname = sexname;
    }

    public int getSexnum() {
        return sexnum;
    }

    public void setSexnum(int sexnum) {
        this.sexnum = sexnum;
    }

}
