package com.atguigu.springboot.controller;

import com.atguigu.springboot.bean.Admin;
import com.atguigu.springboot.service.Adminservice;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    Adminservice adminservice;
    /**
     * 返回首页
     */
    @RequestMapping("/system/list")
    public String systemlist(){
        return "list1";
    }

    /**
     * 返回首页
     */
    @RequestMapping("/system/list1")
    public String systemlist1(){
        return "list2";
    }


    /**
     *展示管理员表
     */
    @RequestMapping("/admin/list")
    public String list(Integer pageNum,Model model){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Admin> admin=adminservice.findAll(pageNum,pageSize);
        model.addAttribute("admin",admin);
        return "list";
        //List<Admin> admin = adminservice.findAll(pageNum,pageSize);
    }

    @RequestMapping("/admin/addbefore")
    public String addbefore(){
        return "add";
    }

    /**
     *添加管理员
     */
    @PostMapping(value="/admin/add")
    public String add(@RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam("role") String role,
                      Integer pageNum,Model model){

            adminservice.add(username, password,role);

        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Admin> admin=adminservice.findAll(pageNum,pageSize);

            model.addAttribute("admin", admin);
            return "list";

    }

    /**
     *修改管理员前
     */
    @RequestMapping("/admin/editbefore")
    public String editbefore(Model model,int admin_id){
        Admin admin=adminservice.editbefore(admin_id);
        model.addAttribute("admin",admin);
        return "edit";
    }

    /**
     *修改管理员前
     */
    @RequestMapping("/admin/editbefore1")
    public String editbefore1(Model model,String admin_name){
        Admin admin=adminservice.editbefore1(admin_name);
        model.addAttribute("admin",admin);
        return "edit";
    }

    /**
     *修改管理员信息
     */
    @RequestMapping("/admin/edit")
    public String edit(@RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam("admin_id") int admin_id,
                       @RequestParam("role") String role,
                       Integer pageNum,Model model){
        adminservice.edit(username, password, admin_id,role);

        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Admin> admin=adminservice.findAll(pageNum,pageSize);
        model.addAttribute("admin", admin);
        return "list";

    }

    /**
     * 判断是否存在该管理员名
     */
    @RequestMapping("/admin/checkadminname")
    @ResponseBody
    public Map<Object, String> admincheckadminname(String username){
        Map<Object, String> resultMap = adminservice.checkadminname(username);
        if(resultMap.isEmpty()){
            return null;
        }
        return resultMap;
    }

    /**
     * 判断是否存在该管理员名
     */
    @RequestMapping("/admin/checkadminname1")
    @ResponseBody
    public Map<Object, String> admincheckadminname1(String username,int admin_id){
        Map<Object, String> resultMap = adminservice.checkadminname1(username,admin_id);
        if(resultMap.isEmpty()){
            return null;
        }
        return resultMap;
    }

    /**
     *删除管理员信息
     */
    @RequestMapping("/admin/delete")
    public String delete(@RequestParam("admin_id") int admin_id,
                         Integer pageNum,Model model){
        adminservice.delete(admin_id);


        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Admin> admin=adminservice.findAll(pageNum,pageSize);

        model.addAttribute("admin", admin);
        return "list";
    }

    /**
     * 管理员搜索
     */

    @RequestMapping("/admin/search")
    public String search(Integer pageNum, Model model, @RequestParam("type") int type, @RequestParam("search") String search){
        int pageSize=5;
        if(pageNum==null){
            pageNum=1;
        }
        PageInfo<Admin> admin=adminservice.search(pageNum,pageSize,type,search);
        model.addAttribute("admin",admin);
        model.addAttribute("type",type);
        model.addAttribute("search",search);
        return "listsearch";
    }



}
