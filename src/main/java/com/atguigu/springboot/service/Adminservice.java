package com.atguigu.springboot.service;

import com.atguigu.springboot.bean.Admin;
import com.atguigu.springboot.mapper.AdminMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Adminservice {
    @Resource
    private  AdminMapper adminMapper;

    /**
     * 管理员登陆
     */
    public Admin login(String username,String password,String role){
        Admin admin=adminMapper.login(username, password,role);

        return admin;

    }

    /**
     * 查询管理员表全部
     */
    public PageInfo<Admin> findAll(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Admin> admin1=adminMapper.findAll();
        PageInfo<Admin> admin=new PageInfo<>(admin1,pageNum);
        return admin;
    }

    /**
     * 管理员表的添加
     */
    public void add(String username,String password,String role){
        adminMapper.add(username,password,role);
    }

    /**
     * 按指定id查询管理员
     */
    public Admin editbefore(int id){
        Admin admin=adminMapper.editbefore(id);
        return admin;
    }

    /**
     * 按指定id查询管理员
     */
    public Admin editbefore1(String name){
        Admin admin=adminMapper.editbefore1(name);
        return admin;
    }

    /**
     * 判断管理员是否存在
     */
    public Map<Object, String> checkadminname(String name){
        Map<Object, String> resultMap = new HashMap<Object, String>();
        Admin admin = adminMapper.checkadminname(name);
        if (admin == null) {
            return resultMap;
        }
        resultMap.put("name","存在");
        return resultMap;
    }

    /**
     * 判断管理员是否存在
     */
    public Map<Object, String> checkadminname1(String name,int admin_id){
        Map<Object, String> resultMap = new HashMap<Object, String>();
        Admin admin = adminMapper.checkadminname(name);
        if (admin == null) {
            return resultMap;
        }

        if (admin.getAdmin_id() == admin_id) {
            return resultMap;
        }

        if(admin.getAdmin_id() != admin_id){
            resultMap.put("name","存在");
            return resultMap;
        }

        resultMap.put("name","存在");
        return resultMap;
    }

    /**
     * 修改管理员信息
     */
    public void edit(String username, String password, int admin_id,String role){
        adminMapper.edit(username, password, admin_id,role);
    }

    /**
     * 删除管理员信息
     */
    public void delete(int admin_id){
        adminMapper.delete(admin_id);
    }

    /**
     * 管理员搜索
     */
    public PageInfo<Admin> search(int pageNum, int pageSize,int type,String search){
        PageHelper.startPage(pageNum,pageSize);
        if(type==1){
            List<Admin> emp1=adminMapper.search1(search);
            PageInfo<Admin> emp=new PageInfo<>(emp1,pageNum);
            return emp;
        }else{
            List<Admin> emp1=adminMapper.search2(search);
            PageInfo<Admin> emp=new PageInfo<>(emp1,pageNum);
            return emp;
        }

    }

}
