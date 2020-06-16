package com.atguigu.springboot.controller;

import com.atguigu.springboot.bean.Department;
import com.atguigu.springboot.bean.EmpSummary;
import com.atguigu.springboot.bean.Employee;
import com.atguigu.springboot.bean.ImportExcel;
import com.atguigu.springboot.service.Employeeservice;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmpController {
    @Autowired
    Employeeservice employeeservice;
    /**
     *展示员工表
     */
    @RequestMapping("/emp/list")
    public String list(Integer pageNum, Model model){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.findAll(pageNum,pageSize);
        model.addAttribute("emp",emp);
        return "emplist";
        //List<Admin> admin = adminservice.findAll(pageNum,pageSize);
    }

    /**
     *展示员工表
     */
    @RequestMapping("/emp/list1")
    public String list1(Integer pageNum, Model model){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.findAll(pageNum,pageSize);
        model.addAttribute("emp",emp);
        return "emp/emplist1";
        //List<Admin> admin = adminservice.findAll(pageNum,pageSize);
    }

    /*
    *展示搜索内容
     */
    @RequestMapping("/emp/search")
    public String search(Integer pageNum, Model model, @RequestParam("type") int type, @RequestParam("search") String search){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.search(pageNum,pageSize,type,search);
        model.addAttribute("emp",emp);
        model.addAttribute("type",type);
        model.addAttribute("search",search);
        return "empsearch";
    }

    /*
     *展示搜索内容
     */
    @RequestMapping("/emp/search1")
    public String search1(Integer pageNum, Model model, @RequestParam("type") int type, @RequestParam("search") String search){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.search(pageNum,pageSize,type,search);
        model.addAttribute("emp",emp);
        model.addAttribute("type",type);
        model.addAttribute("search",search);
        return "emp/empsearch1";
    }

    /**
     *入职时间搜索
     */
    @RequestMapping("/emp/searchtime")
    public String searchtime(Integer pageNum, Model model, @RequestParam("search1") Date search1, @RequestParam("search2") Date search2){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.searchtime(pageNum,pageSize,search1,search2);
        model.addAttribute("emp",emp);
        model.addAttribute("search2",search2);
        model.addAttribute("search1",search1);
        return "empsearchtime";
    }

    /**
     *入职时间搜索
     */
    @RequestMapping("/emp/searchtime1")
    public String searchtime1(Integer pageNum, Model model, @RequestParam("search1") Date search1, @RequestParam("search2") Date search2){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.searchtime(pageNum,pageSize,search1,search2);
        model.addAttribute("emp",emp);
        model.addAttribute("search2",search2);
        model.addAttribute("search1",search1);
        return "emp/empsearchtime1";
    }

    /*
     *展示离职搜索内容
     */
    @RequestMapping("/empquit/search")
    public String quitsearch(Integer pageNum, Model model, @RequestParam("type") int type, @RequestParam("search") String search){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.quitsearch(pageNum,pageSize,type,search);
        model.addAttribute("emp",emp);
        model.addAttribute("type",type);
        model.addAttribute("search",search);
        return "empquitsearch";
    }

    /**
     *离职时间搜索
     */
    @RequestMapping("/empquit/searchtime")
    public String quitsearchtime(Integer pageNum, Model model, @RequestParam("search1") Date search1, @RequestParam("search2") Date search2){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.quitsearchtime(pageNum,pageSize,search1,search2);
        model.addAttribute("emp",emp);
        model.addAttribute("search2",search2);
        model.addAttribute("search1",search1);
        return "empquitsearchtime";
    }


    /*
    *批量删除员工表信息
     */
    @RequestMapping("/deleteAll")
    @ResponseBody
    public String deleteAll(String checkList){
        String[] strs = checkList.split(",");
        List<Integer> ids = new ArrayList<>();
        java.sql.Date ctime = new java.sql.Date(new java.util.Date().getTime());
        for(String str:strs){
            ids.add(Integer.parseInt(str));
        }

        employeeservice.deleteAll(ids,ctime);

        return "success";
    }

    /*
    批量删除离职员工
     */
    @RequestMapping("/empdeleteAll")
    @ResponseBody
    public String empdeleteAll(String checkList){
        String[] strs = checkList.split(",");
        List<Integer> ids = new ArrayList<>();
        for(String str:strs){
            ids.add(Integer.parseInt(str));
        }

        employeeservice.empdeleteAll(ids);

        return "success";
    }

    /**
     * 返回到增加页面
     */
    @RequestMapping("/emp/addbefore")
    public String addbefore(Model model){
        List<Department> departments=employeeservice.searchdepart();
        model.addAttribute("departments",departments);
        return "empadd";

    }

    /**
     * 返回到增加页面
     */
    @RequestMapping("/emp/addbefore1")
    public String addbefore1(Model model){
        List<Department> departments=employeeservice.searchdepart();
        model.addAttribute("departments",departments);
        return "emp/empadd1";

    }

    /**
     * 添加员工
     */
    @PostMapping(value="/emp/add")
    public String add(@RequestParam("name") String name,
                      @RequestParam("age") int age,
                      @RequestParam("sex") int sex,
                      @RequestParam("education") String education,
                      @RequestParam("department") String department,
                      @RequestParam("job") String job,
                      @RequestParam("phone") String phone,
                      @RequestParam("address") String address,
                      @RequestParam("time") Date time,
                      Integer pageNum,Model model){
        employeeservice.add(name,age,sex,phone,education,department,job,time,address);

        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.findAll(pageNum,pageSize);
        model.addAttribute("emp",emp);

        return "emplist";
    }

    /**
     * 添加员工
     */
    @PostMapping(value="/emp/add1")
    public String add1(@RequestParam("name") String name,
                      @RequestParam("age") int age,
                      @RequestParam("sex") int sex,
                      @RequestParam("education") String education,
                      @RequestParam("department") String department,
                      @RequestParam("job") String job,
                      @RequestParam("phone") String phone,
                      @RequestParam("address") String address,
                      @RequestParam("time") Date time,
                      Integer pageNum,Model model){
        employeeservice.add(name,age,sex,phone,education,department,job,time,address);

        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.findAll(pageNum,pageSize);
        model.addAttribute("emp",emp);

        return "emp/emplist1";
    }

    /**
     * 下拉框联动
     */
    @RequestMapping("/selectJob")
    @ResponseBody
    public List<Department> selectjob( String index){
        List<Department> departments=employeeservice.selectjob(index);
        return departments;
    }



    /**
     * 返回到修改页面
     */
    @RequestMapping("/emp/editbefore")
    public String editbefore(Model model,int emp_id){
        Employee emp=employeeservice.editbefore(emp_id);
        model.addAttribute("emp",emp);
        return "empedit";

    }

    /**
     * 返回到修改页面
     */
    @RequestMapping("/emp/editbefore1")
    public String editbefore1(Model model,int emp_id){
        Employee emp=employeeservice.editbefore(emp_id);
        model.addAttribute("emp",emp);
        return "emp/empedit1";

    }

    /**
     * 修改员工信息
     */
    @RequestMapping("/emp/edit")
    public String edit(@RequestParam("emp_id") int emp_id,
                       @RequestParam("name") String name,
                       @RequestParam("age") int age,
                       @RequestParam("sex") int sex,
                       @RequestParam("education") String education,
                       @RequestParam("phone") String phone,
                       @RequestParam("time") Date time,
                       @RequestParam("address") String address,
                       Integer pageNum,Model model
    ){
        employeeservice.edit(emp_id,name,age,sex,phone,education,time,address);
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.findAll(pageNum,pageSize);
        model.addAttribute("emp",emp);
        return "emplist";
    }

    /**
     * 修改员工信息
     */
    @RequestMapping("/emp/edit1")
    public String edit1(@RequestParam("emp_id") int emp_id,
                       @RequestParam("name") String name,
                       @RequestParam("age") int age,
                       @RequestParam("sex") int sex,
                       @RequestParam("education") String education,
                       @RequestParam("phone") String phone,
                       @RequestParam("time") Date time,
                       @RequestParam("address") String address,
                       Integer pageNum,Model model
    ){
        employeeservice.edit(emp_id,name,age,sex,phone,education,time,address);
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.findAll(pageNum,pageSize);
        model.addAttribute("emp",emp);
        return "emp/emplist1";
    }

    /**
     * 删除员工
     */
    @RequestMapping("/emp/delete")
    public String delete(@RequestParam("emp_id") int emp_id,
                         Integer pageNum,Model model){

        java.sql.Date ctime = new java.sql.Date(new java.util.Date().getTime());
        employeeservice.delete(emp_id,ctime);
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.findAll(pageNum,pageSize);
        model.addAttribute("emp",emp);
        return "emplist";
    }

    /**
     * 删除员工
     */
    @RequestMapping("/emp/delete1")
    public String delete1(@RequestParam("emp_id") int emp_id,
                         Integer pageNum,Model model){

        java.sql.Date ctime = new java.sql.Date(new java.util.Date().getTime());
        employeeservice.delete(emp_id,ctime);
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.findAll(pageNum,pageSize);
        model.addAttribute("emp",emp);
        return "emp/emplist1";
    }

    /**
     * 删除离职员工
     */
    @RequestMapping("/emp/deletequit")
    public String deletequit(@RequestParam("emp_id") int emp_id,
                          Integer pageNum,Model model){

        employeeservice.deletequit(emp_id);
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.findQuit(pageNum,pageSize);
        model.addAttribute("emp",emp);
        return "empquit";
    }

    /**
     * 员工调职
     */
    @RequestMapping("/changejob/list")
    public String changejoblist(Integer pageNum, Model model){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.findAll(pageNum,pageSize);
        model.addAttribute("emp",emp);
        return "changejoblist";
    }

    /**
     * 员工调职
     */
    @RequestMapping("/changejob/list1")
    public String changejoblist1(Integer pageNum, Model model){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.findAll(pageNum,pageSize);
        model.addAttribute("emp",emp);
        return "emp/changejoblist1";
    }

    /*
     *展示员工调职搜索内容
     */
    @RequestMapping("/changejob/search")
    public String searchjob(Integer pageNum, Model model, @RequestParam("type") int type, @RequestParam("search") String search){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.search(pageNum,pageSize,type,search);
        model.addAttribute("emp",emp);
        model.addAttribute("type",type);
        model.addAttribute("search",search);
        return "changejoblistsearch";
    }

    /*
     *展示员工调职搜索内容
     */
    @RequestMapping("/changejob/search1")
    public String searchjob1(Integer pageNum, Model model, @RequestParam("type") int type, @RequestParam("search") String search){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.search(pageNum,pageSize,type,search);
        model.addAttribute("emp",emp);
        model.addAttribute("type",type);
        model.addAttribute("search",search);
        return "emp/changejoblistsearch1";
    }

    /**
     * 员工调职前
     */
    @RequestMapping("/changejob/before")
    public String changejobbefore(@RequestParam("job_name") String job_name,
                                  @RequestParam("department_name") String department_name,
                                  @RequestParam("emp_id") String emp_id,
                                  @RequestParam("emp_name") String emp_name, Model model){
        model.addAttribute("emp_id",emp_id);
        model.addAttribute("emp_name",emp_name);
        model.addAttribute("job_name",job_name);
        model.addAttribute("department_name",department_name);
        List<Department> departments=employeeservice.searchdepart();
        model.addAttribute("departments",departments);
        return "changejob";

    }

    /**
     * 员工调职前
     */
    @RequestMapping("/changejob/before1")
    public String changejobbefore1(@RequestParam("job_name") String job_name,
                                  @RequestParam("department_name") String department_name,
                                  @RequestParam("emp_id") String emp_id,
                                  @RequestParam("emp_name") String emp_name, Model model){
        model.addAttribute("emp_id",emp_id);
        model.addAttribute("emp_name",emp_name);
        model.addAttribute("job_name",job_name);
        model.addAttribute("department_name",department_name);
        List<Department> departments=employeeservice.searchdepart();
        model.addAttribute("departments",departments);
        return "emp/changejob1";

    }

    /**
     * 员工调职
     */
    @RequestMapping("/changejob/after")
    public String changeafter(@RequestParam("emp_name") String emp_name,
                              @RequestParam("emp_id") int emp_id,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("department") String department,
                              @RequestParam("job") String job,
                              @RequestParam("time") Date time,
                              Integer pageNum,Model model){
        employeeservice.changejob(department,job,emp_id);
        employeeservice.addchange(emp_id,time,emp_name,username,password,department,job);

        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.findAll(pageNum,pageSize);
        model.addAttribute("emp",emp);
        return "changejoblist";
    }

    /**
     * 员工调职
     */
    @RequestMapping("/changejob/after1")
    public String changeafter1(@RequestParam("emp_name") String emp_name,
                              @RequestParam("emp_id") int emp_id,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("department") String department,
                              @RequestParam("job") String job,
                              @RequestParam("time") Date time,
                              Integer pageNum,Model model){
        employeeservice.changejob(department,job,emp_id);
        employeeservice.addchange(emp_id,time,emp_name,username,password,department,job);

        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.findAll(pageNum,pageSize);
        model.addAttribute("emp",emp);
        return "emp/changejoblist1";
    }



    /**
     * 返回员工离职记录
     */
    @RequestMapping("/emp/quitlist")
    public String quitlist(Integer pageNum, Model model){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Employee> emp=employeeservice.findQuit(pageNum,pageSize);
        model.addAttribute("emp",emp);
        return "empquit";
        //List<Admin> admin = adminservice.findAll(pageNum,pageSize);
    }

    /**
     * Excel导入
     */
    @RequestMapping("/emp/import")
    public String addUser(@RequestParam("file") MultipartFile file,Model model) throws Exception {
        ImportExcel importExcel=new ImportExcel();
        String fileName = file.getOriginalFilename();
        importExcel=employeeservice.batchImport(fileName, file);
        Map<String, Object> messages = new HashMap<>();
        messages=importExcel.getMessages();
        Object messages1=messages.get("failto");
        messages.remove("failto");
        importExcel.setMessages(messages);
        model.addAttribute("messages1",messages1);
        model.addAttribute("importExcel",importExcel);
        return "excellist";

    }

    /**
     * Excel导入
     */
    @RequestMapping("/emp/import1")
    public String addUser1(@RequestParam("file") MultipartFile file,Model model) throws Exception {
        ImportExcel importExcel=new ImportExcel();
        String fileName = file.getOriginalFilename();
        importExcel=employeeservice.batchImport(fileName, file);
        Map<String, Object> messages = new HashMap<>();
        messages=importExcel.getMessages();
        Object messages1=messages.get("failto");
        messages.remove("failto");
        importExcel.setMessages(messages);
        model.addAttribute("messages1",messages1);
        model.addAttribute("importExcel",importExcel);
        return "emp/excellist1";

    }

    /**
     * 返回员工汇总
     */
    @RequestMapping("/emp/summary1")
    public String empsummary1(){
        return "empsummary";
    }

    /**
     * 返回员工性别汇总
     */
    @RequestMapping("/emp/summarysex")
    @ResponseBody
    public List<EmpSummary> empsummarysex(){
        List<EmpSummary> empSummaries=employeeservice.empsummarysex();


        return empSummaries;
    }

    /**
     * 返回员工部门汇总
     */
    @RequestMapping("/emp/summarydepart")
    @ResponseBody
    public List<EmpSummary> empsummarydepart(){
        List<EmpSummary> empSummaries=employeeservice.empsummarydepart();
        return empSummaries;
    }

    /**
     * 返回员工学历汇总
     */
    @RequestMapping("/emp/summaryeducation")
    @ResponseBody
    public List<EmpSummary> empsummaryeducation(){
        List<EmpSummary> empSummaries=employeeservice.empsummaryeducation();
        return empSummaries;
    }

    /**
     * 返回员工学历汇总
     */
    @RequestMapping("/emp/summaryquit")
    @ResponseBody
    public List<EmpSummary> empsummaryquit(){
        List<EmpSummary> empSummaries=employeeservice.empsummaryquit();
        return empSummaries;
    }

}
