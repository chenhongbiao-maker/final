package com.atguigu.springboot.service;

import com.atguigu.springboot.bean.Department;
import com.atguigu.springboot.mapper.DepartMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

@Service
public class Departservice {
    @Resource
    private DepartMapper departMapper;
    /**
     * 展示部门列表
     */
    public PageInfo<Department> findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Department> depart1=departMapper.findAll();
        PageInfo<Department> depart=new PageInfo<>(depart1,pageSize);
        return depart;
    }

    /**
     *查找部门是否相同
     */
    public Department checkjob(String department_name,String job_name){
        Department department=departMapper.checkjob(department_name,job_name);
        return department;
    }

    /**
     * 增加职位
     */
    public void addjob(String department_name,String job_name,double money){
        Department department=departMapper.addbefore(department_name);
        if(department==null){
            departMapper.adddepartment(department_name);
        }
        int department_id=departMapper.searchdepartmentid(department_name);
        departMapper.addjob(job_name,department_id,money);
    }

    /**
     * 修改职位前
     */
    public Department editbefore(int job_id){
        Department department=departMapper.editbefore(job_id);
        return  department;
    }

    /**
     * 修改职位
     */
    public void edit(String depart_name, String job_name,
                           double money,int job_id) {
        Department department = departMapper.checkjob(depart_name, job_name);

        if (department == null) {
            Department department1 = departMapper.addbefore(depart_name);
            if (department1 == null) {
                departMapper.adddepartment(depart_name);
            }
            int department_id1 = departMapper.selectdepartid(job_id);
            List<Department> departments = departMapper.searchdepat(department_id1);
            if (departments.size() == 1) {
                departMapper.deletedepartment(department_id1);
            }

            int department_id = departMapper.searchdepartmentid(depart_name);
            departMapper.edit(job_id, job_name, department_id, money);
        } else {
            int job = department.getJob_id();
            if (job == job_id) {
                departMapper.editme(job_id, money);
            } else {
                int department_id1= departMapper.selectdepartid(job);
                int department_id = departMapper.selectdepartid(job_id);
                List<Department> departments = departMapper.searchdepat(department_id);
                departMapper.deletejob(job_id);
                if (departments.size() == 1) {
                    departMapper.deletedepartment(department_id);
                }
                departMapper.edit(job, job_name, department_id1, money);

            }

        }
    }


    /**
     * 删除职位
     */
    public void deletejob(int job_id, Date ctime){
        int department_id=departMapper.selectdepartid(job_id);
        String department_name=departMapper.selectdepartname(department_id);
        List<Department> departments=departMapper.searchdepat(department_id);
        String job_name=departMapper.selectjobname(job_id);
        List<Integer> ids=departMapper.selectempid(department_name,job_name);

        departMapper.deletesalary(ids);
        departMapper.deleteattendance(ids);
        departMapper.deleteemp(ids,ctime);


        departMapper.deletejob(job_id);
        if(departments.size()==1){
        departMapper.deletedepartment(department_id);
        }


    }

    /**
     * 搜索框查询部门或者职位
     */
    public PageInfo<Department> search(int pageNum, int pageSize,int type,String search){
        PageHelper.startPage(pageNum,pageSize);
        if(type==1){
            List<Department> departments1=departMapper.search1(search);
            PageInfo<Department> departments=new PageInfo<>(departments1,pageNum);
            return departments;
        }else{
            List<Department> departments1=departMapper.search2(search);
            PageInfo<Department> departments=new PageInfo<>(departments1,pageNum);
            return departments;
        }

    }

    /**
     * 搜索工资范围
     */
    public PageInfo<Department> searchmoney(int pageNum, int pageSize,int search1,int search2){
        PageHelper.startPage(pageNum,pageSize);
       if(search1>search2){
           int type=0;
           type=search1;
           search1=search2;
           search2=type;
       }
        List<Department> departments1=departMapper.searchmoney(search1,search2);
        PageInfo<Department> departments=new PageInfo<>(departments1,pageNum);
        return departments;


    }

}
