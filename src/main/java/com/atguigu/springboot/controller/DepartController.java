package com.atguigu.springboot.controller;

import com.atguigu.springboot.bean.Department;
import com.atguigu.springboot.service.Departservice;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DepartController {
    @Autowired
    Departservice departservice;
    /**
     * 展示部门列表
     */
    @RequestMapping("/depart/list")
    public String list(Integer pageNum,Model model){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Department> depart=departservice.findAll(pageNum,pageSize);
        model.addAttribute("depart",depart);
        return "departlist";
    }

    @RequestMapping("/depart/addbefore")
    public String addbefore(){
        return "departadd";
    }

    @RequestMapping("/depart/checkJob")
    @ResponseBody
    public Map<Object, String> checkjob(String depart_name,String job_name){
        Map<Object, String> resultMap = new HashMap<Object, String>();
        Department department=departservice.checkjob(depart_name,job_name);
        if(department!=null){
            resultMap.put("msg", "已存在");
            return resultMap;
        }
        resultMap.put("msg", "不存在");
        return resultMap;
    }

    /**
     *增加职位
     */
    @RequestMapping("/depart/add")
    public String add(@RequestParam String depart_name,
                      @RequestParam String job_name,
                      @RequestParam double money,
                      Integer pageNum,Model model){
        departservice.addjob(depart_name,job_name,money);

        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Department> depart=departservice.findAll(pageNum,pageSize);
        model.addAttribute("depart",depart);
        return "departlist";
    }

    /**
     * 修改职位前
     */
    @RequestMapping("/depart/editbefore")
    public String editbefore(@RequestParam("job_id") int job_id,Model model){
        Department department=departservice.editbefore(job_id);
        model.addAttribute("department",department);
        return "departedit";
    }

    /**
     * 修改职位
     */
    @RequestMapping("/depart/edit")
    public String edit(@RequestParam String depart_name,
                       @RequestParam String job_name,
                       @RequestParam double money,
                       @RequestParam("job_id") int job_id,
                       Integer pageNum,Model model){
        departservice.edit(depart_name,job_name,money,job_id);

        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Department> depart=departservice.findAll(pageNum,pageSize);
        model.addAttribute("depart",depart);
        return "departlist";
    }

    /**
     * 删除职位
     */
    @RequestMapping("/depart/delete")
    public String delete(@RequestParam int job_id,Integer pageNum,Model model){
        java.sql.Date ctime = new java.sql.Date(new java.util.Date().getTime());
        departservice.deletejob(job_id,ctime);
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Department> depart=departservice.findAll(pageNum,pageSize);
        model.addAttribute("depart",depart);
        return "departlist";
    }

    /**
     * 部门表搜索
     */
    @RequestMapping("/depart/search")
    public String search(Integer pageNum, Model model, @RequestParam("type") int type, @RequestParam("search") String search){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Department> depart=departservice.search(pageNum,pageSize,type,search);
        model.addAttribute("depart",depart);
        model.addAttribute("type",type);
        model.addAttribute("search",search);
        return "departlistsearch";
    }

    /**
     *入职时间搜索
     */
    @RequestMapping("/depart/searchmoney")
    public String searchmoney(Integer pageNum, Model model, @RequestParam("search1") int search1, @RequestParam("search2") int search2){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Department> depart=departservice.searchmoney(pageNum,pageSize,search1,search2);
        model.addAttribute("depart",depart);
        model.addAttribute("search2",search2);
        model.addAttribute("search1",search1);
        return "departlistsearchmoney";
    }




}
