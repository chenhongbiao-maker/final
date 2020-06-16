package com.atguigu.springboot.controller;

import com.atguigu.springboot.bean.Changejob;
import com.atguigu.springboot.service.Changejobservice;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChangejobController
{
    @Autowired
    Changejobservice changejobservice;
    /**
     * 展示调职记录表
     */
    @RequestMapping("/changejobtable/list")
    public  String changejobtable(Integer pageNum, Model model){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Changejob> changejob=changejobservice.findAll(pageNum,pageSize);
        model.addAttribute("changejob",changejob);
        return "changejobtablelist";
    }

    /*
     *展示搜索内容
     */
    @RequestMapping("/changejobtable/search")
    public String search(Integer pageNum, Model model, @RequestParam("type") int type, @RequestParam("search") String search){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Changejob> changejob=changejobservice.search(pageNum,pageSize,type,search);
        model.addAttribute("changejob",changejob);
        model.addAttribute("type",type);
        model.addAttribute("search",search);
        return "changejobtablesearch";
    }

    /**
     *调职时间搜索
     */
    @RequestMapping("/changejobtable/searchtime")
    public String searchtime(Integer pageNum, Model model, @RequestParam("search1") Date search1, @RequestParam("search2") Date search2){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Changejob> changejob=changejobservice.searchtime(pageNum,pageSize,search1,search2);
        model.addAttribute("changejob",changejob);
        model.addAttribute("search2",search2);
        model.addAttribute("search1",search1);
        return "changejobtablesearchtime";
    }

    /**
     * 批量删除
     */
    @RequestMapping("/changejobtabledeleteAll")
    @ResponseBody
    public String deleteAll(String checkList){
        String[] strs = checkList.split(",");
        List<Integer> ids = new ArrayList<>();
        for(String str:strs){
            ids.add(Integer.parseInt(str));
        }
        changejobservice.deleteAll(ids);
        return "success";
    }

    /**
     * 删除调职记录
     */
    @RequestMapping("/changejobtable/delete")
    public String changejobtabledelete(@RequestParam("change_id") int change_id,
                                     Integer pageNum,Model model){
        changejobservice.deletechangejob(change_id);
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Changejob> changejob=changejobservice.findAll(pageNum,pageSize);
        model.addAttribute("changejob",changejob);
        return "changejobtablelist";

    }

}
