package com.atguigu.springboot.controller;

import com.atguigu.springboot.bean.*;
import com.atguigu.springboot.service.Salaryservice;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class SalaryController {
    @Autowired
    Salaryservice salaryservice;

    /**
     *返回工资列表
     */
    @RequestMapping("/salary/list")
    public String list(Integer pageNum, Model model){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Salary> sar=salaryservice.findAll(pageNum,pageSize);
        model.addAttribute("sar",sar);
        return "salarylist";
    }

    /**
     *返回工资列表
     */
    @RequestMapping("/salary/list1")
    public String list1(Integer pageNum, Model model){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Salary> sar=salaryservice.findAll(pageNum,pageSize);
        model.addAttribute("sar",sar);
        return "emp/salarylist1";
    }

    /**
     *工资添加前
     */
    @RequestMapping("/salary/addbefore")
    public  String addbefore(){
        return "salaryadd";
    }

    /**
     *工资添加前
     */
    @RequestMapping("/salary/addbefore1")
    public  String addbefore1(){
        return "emp/salaryadd1";
    }
    /**
     *获得员工id和基本工资
     */

    @RequestMapping("/checksalary")
    @ResponseBody
    public Map<Object, String> checksalary(String emp_name){

        Map<Object, String> resultMap = salaryservice.checksalary(emp_name);
        if(resultMap.isEmpty()){
            return null;
        }
        return resultMap;
    }

    /**
     *获得员工考勤扣款和加班奖金
     */
    @RequestMapping("/getmoney")
    @ResponseBody
    public Map<Object, Double> getmoney(int emp_id,String month){
        Map<Object,Double> resultMap=salaryservice.getmoney(emp_id, month);
        if(resultMap.isEmpty()){
            return null;
        }

        return resultMap;
    }

    /**
     *添加员工工资
     */
    @RequestMapping("/salary/add")
    public String add(@RequestParam("emp_name") String emp_name,
                      @RequestParam("emp_id") int emp_id,
                      @RequestParam("salary") double salary,
                      @RequestParam("month") String month,
                      @RequestParam("leave_salary") double leave_time,
                      @RequestParam("overtime_salary") double overtime_salary,
                      @RequestParam("resalary") double resalary,
                      Integer pageNum, Model model
                       ){

        salaryservice.add(emp_name,emp_id,salary,month,leave_time,overtime_salary,resalary);

        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Salary> sar=salaryservice.findAll(pageNum,pageSize);
        model.addAttribute("sar",sar);
        return "salarylist";
    }

    /**
     *添加员工工资
     */
    @RequestMapping("/salary/add1")
    public String add1(@RequestParam("emp_name") String emp_name,
                      @RequestParam("emp_id") int emp_id,
                      @RequestParam("salary") double salary,
                      @RequestParam("month") String month,
                      @RequestParam("leave_salary") double leave_time,
                      @RequestParam("overtime_salary") double overtime_salary,
                      @RequestParam("resalary") double resalary,
                      Integer pageNum, Model model
    ){

        salaryservice.add(emp_name,emp_id,salary,month,leave_time,overtime_salary,resalary);

        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Salary> sar=salaryservice.findAll(pageNum,pageSize);
        model.addAttribute("sar",sar);
        return "emp/salarylist1";
    }

    /**
     * 返回修改页面
     */
    @RequestMapping("/salary/editbefore")
    public String editbefore(@RequestParam("salary_id") int salary_id,Model model ){
        Salary salary= salaryservice.editbefore(salary_id);
        model.addAttribute("salary",salary);
        return "salaryedit";
    }

    /**
     * 返回修改页面
     */
    @RequestMapping("/salary/editbefore1")
    public String editbefore1(@RequestParam("salary_id") int salary_id,Model model ){
        Salary salary= salaryservice.editbefore(salary_id);
        model.addAttribute("salary",salary);
        return "emp/salaryedit1";
    }

    /**
     * 修改工资信息
     */
    @RequestMapping("/salary/edit")
    public String edit(@RequestParam("emp_name") String emp_name,
                       @RequestParam("salary_id") int salary_id,
                       @RequestParam("emp_id") int emp_id,
                       @RequestParam("salary") double salary,
                       @RequestParam("month") String month,
                       @RequestParam("leave_salary") double leave_time,
                       @RequestParam("overtime_salary") double overtime_salary,
                       @RequestParam("resalary") double resalary,
                       Integer pageNum, Model model){


        salaryservice.edit(emp_name,emp_id,salary,month,leave_time,overtime_salary,resalary,salary_id);
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Salary> sar=salaryservice.findAll(pageNum,pageSize);
        model.addAttribute("sar",sar);
        return "salarylist";
    }

    /**
     * 修改工资信息
     */
    @RequestMapping("/salary/edit1")
    public String edit1(@RequestParam("emp_name") String emp_name,
                       @RequestParam("salary_id") int salary_id,
                       @RequestParam("emp_id") int emp_id,
                       @RequestParam("salary") double salary,
                       @RequestParam("month") String month,
                       @RequestParam("leave_salary") double leave_time,
                       @RequestParam("overtime_salary") double overtime_salary,
                       @RequestParam("resalary") double resalary,
                       Integer pageNum, Model model){


        salaryservice.edit(emp_name,emp_id,salary,month,leave_time,overtime_salary,resalary,salary_id);
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Salary> sar=salaryservice.findAll(pageNum,pageSize);
        model.addAttribute("sar",sar);
        return "emp/salarylist1";
    }

    /**
     * 删除工资信息
     */
    @RequestMapping("/salary/delete")
    public String delete(@RequestParam("salary_id") int salary_id,
                         Integer pageNum,Model model){
        salaryservice.delete(salary_id);
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Salary> sar=salaryservice.findAll(pageNum,pageSize);
        model.addAttribute("sar",sar);
        return "salarylist";
    }

    /**
     * 删除工资信息
     */
    @RequestMapping("/salary/delete1")
    public String delete1(@RequestParam("salary_id") int salary_id,
                         Integer pageNum,Model model){
        salaryservice.delete(salary_id);
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Salary> sar=salaryservice.findAll(pageNum,pageSize);
        model.addAttribute("sar",sar);
        return "emp/salarylist1";
    }

    /*
     *展示搜索内容
     */
    @RequestMapping("/salary/search")
    public String search(Integer pageNum, Model model, @RequestParam("search") String search){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Salary> sar=salaryservice.search(pageNum,pageSize,search);
        model.addAttribute("sar",sar);
        model.addAttribute("search",search);
        return "salarysearch";
    }

    /*
     *展示搜索内容
     */
    @RequestMapping("/salary/search1")
    public String search1(Integer pageNum, Model model, @RequestParam("search") String search){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Salary> sar=salaryservice.search(pageNum,pageSize,search);
        model.addAttribute("sar",sar);
        model.addAttribute("search",search);
        return "emp/salarysearch1";
    }

    /**
     *入职时间搜索
     */
    @RequestMapping("/salary/searchtime")
    public String searchtime(Integer pageNum, Model model, @RequestParam("search1") String search1, @RequestParam("search2") String search2){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Salary> sar=salaryservice.searchtime(pageNum,pageSize,search1,search2);
        model.addAttribute("sar",sar);
        model.addAttribute("search2",search2);
        model.addAttribute("search1",search1);
        return "salarysearchtime";
    }

    /**
     *入职时间搜索
     */
    @RequestMapping("/salary/searchtime1")
    public String searchtime1(Integer pageNum, Model model, @RequestParam("search1") String search1, @RequestParam("search2") String search2){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Salary> sar=salaryservice.searchtime(pageNum,pageSize,search1,search2);
        model.addAttribute("sar",sar);
        model.addAttribute("search2",search2);
        model.addAttribute("search1",search1);
        return "emp/salarysearchtime1";
    }

    /**
     * 返回年度汇总前
     */
    @RequestMapping("/salary/summarybefore")
    public String summarybefore(){
        return "salarysummary1";
    }

    /**
     * 返回年度汇总
     */
    @RequestMapping("/salary/summary")
    public String summary( String search1,String search2,Model model){

          Salarysummary salarysummary=salaryservice.summary(search1,search2);
        model.addAttribute("summary",salarysummary);
        model.addAttribute("year",search1);
        return "salarysummary";
    }

    /**
     * 工资批量增加前
     */
    @RequestMapping("/salary/addmanybefore")
    public  String addmanybefore(){
        return "salaryaddmany";
    }

    /**
     * 工资批量增加前
     */
    @RequestMapping("/salary/addmanybefore1")
    public  String addmanybefore1(){
        return "emp/salaryaddmany1";
    }

    /**
     * 批量增加工资
     */
    @RequestMapping("/salary/addmany")
    public String addmany(String cnashu2,String cnashu3, String cnashu4,
                          String cnashu5,String cnashu6,String cnashu7,
                          String cnashu8,Integer pageNum,Model model){
        salaryservice.addmany(cnashu2, cnashu3, cnashu4, cnashu5, cnashu6, cnashu7, cnashu8);
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Salary> sar=salaryservice.findAll(pageNum,pageSize);
        model.addAttribute("sar",sar);
        return "salarylist";
    }

    /**
     * 批量增加工资
     */
    @RequestMapping("/salary/addmany1")
    public String addmany1(String cnashu2,String cnashu3, String cnashu4,
                          String cnashu5,String cnashu6,String cnashu7,
                          String cnashu8,Integer pageNum,Model model){
        salaryservice.addmany(cnashu2, cnashu3, cnashu4, cnashu5, cnashu6, cnashu7, cnashu8);
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Salary> sar=salaryservice.findAll(pageNum,pageSize);
        model.addAttribute("sar",sar);
        return "emp/salarylist1";
    }

    /**
     * 批量删除
     */
    @RequestMapping("/salarydeleteAll")
    @ResponseBody
    public String deleteAll(String checkList){
        String[] strs = checkList.split(",");
        List<Integer> ids = new ArrayList<>();
        for(String str:strs){
            ids.add(Integer.parseInt(str));
        }
        salaryservice.deleteAll(ids);
        return "success";
    }

    /**
     * Excel导入
     */
    @RequestMapping("/salary/import")
    public String addUser(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        ImportExcel importExcel=new ImportExcel();
        String fileName = file.getOriginalFilename();
        importExcel=salaryservice.batchImport(fileName, file);
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
    @RequestMapping("/salary/import1")
    public String addUser1(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        ImportExcel importExcel=new ImportExcel();
        String fileName = file.getOriginalFilename();
        importExcel=salaryservice.batchImport(fileName, file);
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
     * 返回员工基础工资柱状图
     */
    @RequestMapping("/salary/summarysalary")
    @ResponseBody
    public Salarysummary summarysalary(String year){
        Salarysummary salarysummary=new Salarysummary();
        salarysummary=salaryservice.summarysalary(year);
        return salarysummary;

    }

    /**
     * 返回员工实际工资柱状图
     */
    @RequestMapping("/salary/summaryresalary")
    @ResponseBody
    public Salarysummary summaryresalary(String year){
        Salarysummary salarysummary=new Salarysummary();
        salarysummary=salaryservice.summaryresalary(year);
        return salarysummary;

    }

}
