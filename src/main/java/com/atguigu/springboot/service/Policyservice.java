package com.atguigu.springboot.service;

import com.atguigu.springboot.bean.Policy;
import com.atguigu.springboot.mapper.PolicyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Policyservice {
    @Resource
    PolicyMapper policyMapper;

    /**
     * 返回政策列表
     */
    public Policy find(){
        Policy policy=policyMapper.find();
        return policy;
    }

    /**
     * 编辑政策
     */
    public void edit(double leave_money,double over_money,double off_money,int policy_id){
        policyMapper.edit(leave_money, over_money, off_money, policy_id);
    }
}
