package com.atguigu.springboot.mapper;

import com.atguigu.springboot.bean.Attendance;
import com.atguigu.springboot.bean.Employee;
import com.atguigu.springboot.bean.Policy;
import com.atguigu.springboot.bean.Salary;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface SalaryMapper {
    @Select("select * from salary order by time desc")
    public List<Salary> findAll();

    @Select("select * from salary where emp_name like CONCAT( '%',#{search},'%') order by time desc ")
    public List<Salary> search1(String search);

    @Select("select * from salary where time between #{search1} and #{search2} order by time desc" )
    public List<Salary> searchtime(Date search1,Date search2);

    @Select("select * from employee where emp_name=#{emp_name} and active=1 limit 0,1 ")
    public Employee selectid(String emp_name);

    @Select("select department_name,job_name from employee where emp_id=#{emp_id}")
    public Employee selectdepartmentname(int emp_id);
    @Select("select department_id from department where department_name=#{department_name} ")
    public int selectdepartmentid(String department_name);
    @Select("select job_money from job where department_id=#{department_id} and job_name=#{job_name}")
    public double selectmoney(int department_id,String job_name);
    @Select("select * from policy")
    public Policy findpolicy();
    @Select("select * from attendance where  date_format(time,'%Y-%m')=#{month} and emp_id=#{emp_id}")
    public List<Attendance> searchmonth(String month,int emp_id);
    @Insert("insert into salary(emp_id,salary,emp_name,time,resalary,leave_salary,overtime_salary,month) values(#{emp_id},#{salary},#{emp_name},#{time},#{resalary},#{leave_salary},#{overtime_salary},#{month})")
    public void add(int emp_id, double salary, String emp_name, Date time,double resalary,double leave_salary,double overtime_salary,String month);
    @Select("select * from salary where emp_id=#{emp_id} and date_format(time,'%Y-%m')=#{month}")
    public Salary checkedit(int emp_id,String month);
    @Update("update  salary set emp_id=#{emp_id},salary=#{salary},emp_name=#{emp_name},resalary=#{resalary},leave_salary=#{leave_salary},overtime_salary=#{overtime_salary} where salary_id=#{salary_id}")
    public void addc(int emp_id, double salary, String emp_name,double resalary,double leave_salary,double overtime_salary,int salary_id);
    @Select("select * from salary where salary_id=#{salary_id}")
    public Salary editbefore(int salary_id);
    @Update("update  salary set emp_id=#{emp_id},salary=#{salary},emp_name=#{emp_name},resalary=#{resalary},leave_salary=#{leave_salary},overtime_salary=#{overtime_salary} where salary_id=#{salary_id}")
    public void edit(int emp_id, double salary, String emp_name,Date time,double resalary,double leave_salary,double overtime_salary,int salary_id,String month);
    @Delete("delete from salary where salary_id=#{salary_id}")
    public void delete(int salary_id);
    @Select("select * from salary where  date_format(time,'%Y')=#{search} ")
    public List<Salary> searchyear(String search);

    @Select("select * from salary where  date_format(time,'%Y')=#{search1} and emp_name=#{search2} ")
    public List<Salary> searchyearname(String search1,String search2);

    @Select("select * from salary where  date_format(time,'%Y-%m')=#{search} ")
    public List<Salary> searchtomonth(String search);

    @Select("select * from salary where  date_format(time,'%Y-%m')=#{search1} and emp_name=#{search2} ")
    public List<Salary> searchtomonthname(String search1,String search2);

    @Delete("<script> delete from salary  where salary_id in <foreach collection = 'ids' item='id'  open='('  separator=',' close=')' > #{id} </foreach> </script>  ")
    public void deleteAll(@Param(value="ids")List<Integer> ids);

    @Select("select * from employee where emp_name=#{emp_name} and emp_id=#{emp_id}")
    public Employee checkname(String emp_name,int emp_id);

}
