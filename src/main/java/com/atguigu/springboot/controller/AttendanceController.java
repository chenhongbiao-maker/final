package com.atguigu.springboot.controller;

import com.atguigu.springboot.bean.Attendance;
import com.atguigu.springboot.bean.Attendancesummary;
import com.atguigu.springboot.bean.ImportExcel;
import com.atguigu.springboot.service.Attendanceservice;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AttendanceController {
    @Autowired
    Attendanceservice attendanceservice;
    /**
     * 展示考勤表
     */
    @RequestMapping("/attdance/list")
    public  String attdancelist(Integer pageNum, Model model){
        int pageSize=10;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Attendance> attendance=attendanceservice.findAll(pageNum,pageSize);
        model.addAttribute("attendance",attendance);
        return "attendancelist";
    }

    /**
     * 展示考勤表
     */
    @RequestMapping("/attdance/list1")
    public  String attdancelist1(Integer pageNum, Model model){
        int pageSize=10;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Attendance> attendance=attendanceservice.findAll(pageNum,pageSize);
        model.addAttribute("attendance",attendance);
        return "emp/attendancelist1";
    }

    /*
     *展示搜索内容
     */
    @RequestMapping("/attendance/search")
    public String search(Integer pageNum, Model model, @RequestParam("search") String search){
        int pageSize=10;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Attendance> attendance=attendanceservice.search(pageNum,pageSize,search);
        model.addAttribute("attendance",attendance);
        model.addAttribute("search",search);
        return "attendancesearch";
    }

    /*
     *展示搜索内容
     */
    @RequestMapping("/attendance/search1")
    public String search1(Integer pageNum, Model model, @RequestParam("search") String search){
        int pageSize=10;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Attendance> attendance=attendanceservice.search(pageNum,pageSize,search);
        model.addAttribute("attendance",attendance);
        model.addAttribute("search",search);
        return "emp/attendancesearch1";
    }

    /**
     *考勤时间搜索
     */
    @RequestMapping("/attendance/searchtime")
    public String searchtime(Integer pageNum, Model model, @RequestParam("search1") Date search1, @RequestParam("search2") Date search2){
        int pageSize=10;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Attendance> attendance=attendanceservice.searchtime(pageNum,pageSize,search1,search2);
        model.addAttribute("attendance",attendance);
        model.addAttribute("search2",search2);
        model.addAttribute("search1",search1);
        return "attendancesearchtime";
    }

    /**
     *考勤时间搜索
     */
    @RequestMapping("/attendance/searchtime1")
    public String searchtime1(Integer pageNum, Model model, @RequestParam("search1") Date search1, @RequestParam("search2") Date search2){
        int pageSize=10;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Attendance> attendance=attendanceservice.searchtime(pageNum,pageSize,search1,search2);
        model.addAttribute("attendance",attendance);
        model.addAttribute("search2",search2);
        model.addAttribute("search1",search1);
        return "emp/attendancesearchtime1";
    }

    /**
     * 添加考勤记录前
     */
    @RequestMapping("/attendance/addbefore")
    public String addbefore(Model model){
        return "attendanceadd";
    }

    /**
     * 添加考勤记录前
     */
    @RequestMapping("/attendance/addbefore1")
    public String addbefore1(Model model){
        return "emp/attendanceadd1";
    }

    /**
     * 根据姓名返回id
     */
    @RequestMapping("/attendancegetid")
    @ResponseBody
    public Map<Object, String> getid(String emp_name){
        Map<Object, String> resultMap = attendanceservice.getid(emp_name);
        if(resultMap.isEmpty()){
            return null;
        }
        return resultMap;
    }

    /**
     * 查看是否存在员工
     */
    @RequestMapping("/attendance/checkname")
    @ResponseBody
    public Map<Object, String> checkname(String emp_name,int emp_id){
        Map<Object, String> resultMap = attendanceservice.checkname(emp_name,emp_id);
        if(resultMap.isEmpty()){
            return null;
        }
        return resultMap;
    }

    /**
     * 增加考勤记录
     */
    @RequestMapping("/attendance/add")
    public String add(@RequestParam("emp_name") String emp_name,
                      @RequestParam("emp_id") int emp_id,
                      @RequestParam("leave_time") double leave_time,
                      @RequestParam("over_time") double over_time,
                      @RequestParam("off_time") double off_time,
                      @RequestParam("time") Date time,
                      Integer pageNum,Model model){
            attendanceservice.add(emp_name, emp_id, leave_time, over_time, off_time, time);
        int pageSize=10;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Attendance> attendance=attendanceservice.findAll(pageNum,pageSize);
        model.addAttribute("attendance",attendance);
        return "attendancelist";
    }

    /**
     * 增加考勤记录
     */
    @RequestMapping("/attendance/add1")
    public String add1(@RequestParam("emp_name") String emp_name,
                      @RequestParam("emp_id") int emp_id,
                      @RequestParam("leave_time") double leave_time,
                      @RequestParam("over_time") double over_time,
                      @RequestParam("off_time") double off_time,
                      @RequestParam("time") Date time,
                      Integer pageNum,Model model){
        attendanceservice.add(emp_name, emp_id, leave_time, over_time, off_time, time);
        int pageSize=10;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Attendance> attendance=attendanceservice.findAll(pageNum,pageSize);
        model.addAttribute("attendance",attendance);
        return "emp/attendancelist1";
    }

    /**
     * 删除考勤记录
     */
    @RequestMapping("/attendance/delete")
    public String delete(@RequestParam("attendance_id") int attendance_id,
                         Integer pageNum,Model model){
        attendanceservice.delete(attendance_id);
        int pageSize=10;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Attendance> attendance=attendanceservice.findAll(pageNum,pageSize);
        model.addAttribute("attendance",attendance);
        return "attendancelist";
    }

    /**
     * 删除考勤记录
     */
    @RequestMapping("/attendance/delete1")
    public String delete1(@RequestParam("attendance_id") int attendance_id,
                         Integer pageNum,Model model){
        attendanceservice.delete(attendance_id);
        int pageSize=10;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Attendance> attendance=attendanceservice.findAll(pageNum,pageSize);
        model.addAttribute("attendance",attendance);
        return "emp/attendancelist1";
    }

    /**
     * 编辑考勤记录
     */
    @RequestMapping("/attendance/editbefore")
    public String editbefore(@RequestParam("attendance_id") int attendance_id, Model model){
        Attendance attendance =attendanceservice.editbefore(attendance_id);
        model.addAttribute("attendance",attendance);
        return "attendanceedit";
    }

    /**
     * 编辑考勤记录
     */
    @RequestMapping("/attendance/editbefore1")
    public String editbefore1(@RequestParam("attendance_id") int attendance_id, Model model){
        Attendance attendance =attendanceservice.editbefore(attendance_id);
        model.addAttribute("attendance",attendance);
        return "emp/attendanceedit1";
    }

    /**
     * 修改考勤信息
     */
    @RequestMapping("/attendance/edit")
    public String edit(@RequestParam("emp_name") String emp_name,
                       @RequestParam("emp_id") int emp_id,
                       @RequestParam("leave_time") double leave_time,
                       @RequestParam("over_time") double over_time,
                       @RequestParam("off_time") double off_time,
                       @RequestParam("time") Date time,
                       @RequestParam("attendance_id") int attendance_id,
                       Integer pageNum,Model model){
        attendanceservice.edit(emp_name, emp_id, leave_time, over_time, off_time, time, attendance_id);
        int pageSize=10;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Attendance> attendance=attendanceservice.findAll(pageNum,pageSize);
        model.addAttribute("attendance",attendance);
        return "attendancelist";
    }

    /**
     * 修改考勤信息
     */
    @RequestMapping("/attendance/edit1")
    public String edit1(@RequestParam("emp_name") String emp_name,
                       @RequestParam("emp_id") int emp_id,
                       @RequestParam("leave_time") double leave_time,
                       @RequestParam("over_time") double over_time,
                       @RequestParam("off_time") double off_time,
                       @RequestParam("time") Date time,
                       @RequestParam("attendance_id") int attendance_id,
                       Integer pageNum,Model model){
        attendanceservice.edit(emp_name, emp_id, leave_time, over_time, off_time, time, attendance_id);
        int pageSize=10;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Attendance> attendance=attendanceservice.findAll(pageNum,pageSize);
        model.addAttribute("attendance",attendance);
        return "emp/attendancelist1";
    }

    /**
     * 批量删除
     */
    @RequestMapping("/attendancedeleteAll")
    @ResponseBody
    public String deleteAll(String checkList){
        String[] strs = checkList.split(",");
        List<Integer> ids = new ArrayList<>();
        for(String str:strs){
            ids.add(Integer.parseInt(str));
        }
        attendanceservice.deleteAll(ids);
        return "success";
    }

    /**
     * 返回考勤汇总页
     */
    @RequestMapping("/attendance/summarybefore")
    public String summarybefore(){
        return "attendancesummary1";
    }

    /**
     * 返回年度汇总
     */
    @RequestMapping("/attendance/summary")
    public String summary(String search1,
                          String search2,Model model ){


        Attendancesummary summary=attendanceservice.summary(search1,search2);
        model.addAttribute("summary",summary);
        model.addAttribute("year",search1);
        return "attendancesummary";
    }

    /**
     * Excel导入
     */
    @RequestMapping("/attendance/import")
    public String addUser(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        ImportExcel importExcel=new ImportExcel();
        String fileName = file.getOriginalFilename();
        importExcel=attendanceservice.batchImport(fileName, file);
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
    @RequestMapping("/attendance/import1")
    public String addUser1(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        ImportExcel importExcel=new ImportExcel();
        String fileName = file.getOriginalFilename();
        importExcel=attendanceservice.batchImport(fileName, file);
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
     * 批量增加考勤前
     */
    @RequestMapping("/attdance/addmanybefore")
    public String attdanceaddmanybefore(){
        return  "attendanceaddmany";
    }

    /**
     * 批量增加考勤前
     */
    @RequestMapping("/attdance/addmanybefore1")
    public String attdanceaddmanybefore1(){
        return  "emp/attendanceaddmany1";
    }

    /**
     * 批量添加考勤
     */
    @RequestMapping("/attendance/addmany")
    public String attendanceaddmany(String cnashu2,String cnashu3, String cnashu4,
                                    String cnashu5,String cnashu6,String cnashu7,
                                    Integer pageNum,Model model){
        attendanceservice.addmany(cnashu2, cnashu3, cnashu4, cnashu5, cnashu6, cnashu7);
        int pageSize=10;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Attendance> attendance=attendanceservice.findAll(pageNum,pageSize);
        model.addAttribute("attendance",attendance);
        return "attendancelist";
    }

    /**
     * 批量添加考勤
     */
    @RequestMapping("/attendance/addmany1")
    public String attendanceaddmany1(String cnashu2,String cnashu3, String cnashu4,
                                    String cnashu5,String cnashu6,String cnashu7,
                                    Integer pageNum,Model model){
        attendanceservice.addmany(cnashu2, cnashu3, cnashu4, cnashu5, cnashu6, cnashu7);
        int pageSize=10;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Attendance> attendance=attendanceservice.findAll(pageNum,pageSize);
        model.addAttribute("attendance",attendance);
        return "emp/attendancelist1";
    }

    /**
     * 返回员工加班柱状图
     */
    @RequestMapping("/attendance/summaryovertime")
    @ResponseBody
    public Attendancesummary summaryleavetime(String year){
        Attendancesummary attendancesummary=new Attendancesummary();
        attendancesummary=attendanceservice.summaryovertime(year);
        return attendancesummary;
    }

    /**
     * 返回员工缺勤柱状图
     */
    @RequestMapping("/attendance/summaryleavetime")
    @ResponseBody
    public Attendancesummary summaryovertime(String year){
        Attendancesummary attendancesummary=new Attendancesummary();
        attendancesummary=attendanceservice.summaryleavetime(year);
        return attendancesummary;
    }

    /**
     * 返回员工请假柱状图
     */
    @RequestMapping("/attendance/summaryofftime")
    @ResponseBody
    public Attendancesummary summaryofftime(String year){
        Attendancesummary attendancesummary=new Attendancesummary();
        attendancesummary=attendanceservice.summaryofftime(year);
        return attendancesummary;
    }



}
