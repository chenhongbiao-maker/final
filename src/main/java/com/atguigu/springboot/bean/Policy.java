package com.atguigu.springboot.bean;

public class Policy {
    private int policy_id;
    private double leave_money;
    private double over_money;
    private double off_money;

    public double getLeave_money() {
        return leave_money;
    }

    public void setLeave_money(double leave_money) {
        this.leave_money = leave_money;
    }

    public double getOver_money() {
        return over_money;
    }

    public void setOver_money(double over_money) {
        this.over_money = over_money;
    }

    public double getOff_money() {
        return off_money;
    }

    public void setOff_money(double off_money) {
        this.off_money = off_money;
    }

    public int getPolicy_id() {
        return policy_id;
    }

    public void setPolicy_id(int policy_id) {
        this.policy_id = policy_id;
    }
}
