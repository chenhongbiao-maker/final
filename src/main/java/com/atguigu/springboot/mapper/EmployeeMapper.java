package com.atguigu.springboot.mapper;

import com.atguigu.springboot.bean.Department;
import com.atguigu.springboot.bean.Employee;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Select("select * from employee where active=1")
    public List<Employee> findAll();

    @Select("select * from employee ")
    public List<Employee> findAllemp();

    @Select("select * from employee where active=1 and emp_name like CONCAT( '%',#{search},'%') ")
    public List<Employee> search1(String search);

    @Select("select * from employee where active=1 and department_name like CONCAT( '%',#{search},'%') ")
    public List<Employee> search2(String search);

    @Select("select * from employee where active=1 and join_date between #{search1} and #{search2}")
    public List<Employee> searchtime(Date search1,Date search2);

    @Select("select * from employee where active=0 and emp_name like CONCAT( '%',#{search},'%') ")
    public List<Employee> quitsearch1(String search);

    @Select("select * from employee where active=0 and department_name like CONCAT( '%',#{search},'%') ")
    public List<Employee> quitsearch2(String search);

    @Select("select * from employee where active=0 and join_date between #{search1} and #{search2}")
    public List<Employee> quitsearchtime(Date search1,Date search2);

    @Update("<script> update employee set active=0,leave_time=#{leave_time} where emp_id in <foreach collection = 'ids' item='id'  open='('  separator=',' close=')' > #{id} </foreach> </script>  ")
    public void deleteAll(@Param(value="ids")List<Integer> ids,Date leave_time);

    @Delete("<script> delete from employee  where emp_id in <foreach collection = 'ids' item='id'  open='('  separator=',' close=')' > #{id} </foreach> </script>  ")
    public void empdeleteAll(@Param(value="ids")List<Integer> ids);

    @Delete("<script> delete from salary  where emp_id in <foreach collection = 'ids' item='id'  open='('  separator=',' close=')' > #{id} </foreach> </script>  ")
    public void deleteAllsalary(@Param(value="ids")List<Integer> ids);

    @Delete("<script> delete from attendance  where emp_id in <foreach collection = 'ids' item='id'  open='('  separator=',' close=')' > #{id} </foreach> </script>  ")
    public void deleteAllattendance(@Param(value="ids")List<Integer> ids);

    @Select("select * from department")
    public List<Department> searchdepart();

    @Select("select * from job where department_id = (select department_id from department where department_name =#{department_name})")
    public List<Department> selectjob(String department_name);

    @Insert("insert into employee(emp_name,age,sex,phone,education,department_name,job_name,join_date,active,address) values(#{emp_name},#{age},#{sex},#{phone},#{education},#{department_name},#{job_name},#{join_date},1,#{address})")
    public void add(String emp_name, int age, int sex, String phone, String education, String department_name, String job_name, Date join_date,String address);

    @Select("select * from employee where emp_id=#{emp_id}")
    public Employee editbefore(int emp_id);

    @Update("update employee set emp_name=#{emp_name},age=#{age},sex=#{sex},education=#{education},phone=#{phone},join_date=#{join_date},address=#{address} where emp_id=#{emp_id}")
    public void edit(int emp_id,String emp_name, int age, int sex, String phone, String education,  Date join_date,String address);

    @Update("update employee set active=0,leave_time=#{leave_time} where emp_id=#{emp_id}")
    public void delete(int emp_id,Date leave_time);

    @Delete("delete from employee where emp_id=#{emp_id}")
    public void deletequit(int emp_id);

    @Delete("delete from salary where emp_id=#{emp_id}")
    public void deletesalary(int emp_id);

    @Delete("delete from attendance where emp_id=#{emp_id}")
    public void deleteattendance(int emp_id);

    @Update("update employee set department_name=#{department_name},job_name=#{job_name} where emp_id=#{emp_id} ")
    public void changejob(String department_name,String job_name,int emp_id);

    @Insert("insert into changejob(emp_id,change_date,emp_name,bedepartment_name,bejob_name,afdepartment_name,afjob_name) values(#{emp_id},#{change_date},#{emp_name},#{bedepartment_name},#{bejob_name},#{afdepartment_name},#{afjob_name}) ")
    public void addchange(int emp_id,Date change_date,String emp_name,String bedepartment_name,String bejob_name,String afdepartment_name,String afjob_name);

    @Select("select * from employee where active=0")
    public List<Employee> findQuit();

    @Select("select * from (select * from  department join  job using(department_id))a where department_name=#{department_name} and job_name=#{job_name}  ")
    public  Department checkjob(String department_name,String job_name);

    @Select("select count(*) from employee where active=1 and department_name=#{department_name}")
    public int summaryde(String department_name);

    @Select("select count(*) from employee where active=1 and education=#{education}")
    public int summaryeducation(String education);
}
