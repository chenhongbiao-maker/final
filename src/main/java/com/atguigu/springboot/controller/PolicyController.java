package com.atguigu.springboot.controller;

import com.atguigu.springboot.bean.Policy;
import com.atguigu.springboot.service.Policyservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PolicyController {
    @Autowired
    private Policyservice policyservice;

    /**
     * 返回政策列表
     */
    @RequestMapping("/policy/list")
    public String find(Model model){
        Policy policy=policyservice.find();
        model.addAttribute("policy",policy);
        return "policylist";

    }

    /**
     *政策修改前
     */
    @RequestMapping("/policy/editbefore")
    public String editbefore(@RequestParam("policy_id") int policy_id, Model model){
        Policy policy=policyservice.find();
        model.addAttribute("policy_id",policy_id);
        model.addAttribute("policy",policy);
        return "policyedit";
    }

    /**
     * 政策修改
     */
    @RequestMapping("/policy/edit")
    public String edit(@RequestParam("policy_id")int policy_id,
                       @RequestParam("leave_money") double leave_money,
                       @RequestParam("over_money") double over_money,
                       @RequestParam("off_money") double off_money,Model model){
        policyservice.edit(leave_money, over_money, off_money, policy_id);
        Policy policy=policyservice.find();
        model.addAttribute("policy",policy);
        return "policylist";

    }
}
