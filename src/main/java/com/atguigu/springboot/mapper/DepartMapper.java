package com.atguigu.springboot.mapper;

import com.atguigu.springboot.bean.Department;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface DepartMapper {
    @Select("select * from  department join  job using(department_id) order by  department_name")
    public List<Department> findAll();

    @Select("select * from (select * from  department join  job using(department_id))a where department_name=#{department_name} and job_name=#{job_name}  ")
    public  Department checkjob(String department_name,String job_name);

    @Select("select * from department where department_name=#{department_name}")
    public Department addbefore(String department_name);

    @Insert("insert into department(department_name) values(#{department_name})")
    public void adddepartment(String department_name);

    @Select("select department_id from  department where department_name=#{department_name}")
    public int searchdepartmentid(String department_name);

    @Insert("insert into job(job_name,department_id,job_money) values(#{job_name},#{department_id},#{job_money})")
    public void addjob(String job_name,int department_id,double job_money);

    @Select("select department_id from job where job_id=#{job_id} ")
    public int selectdepartid(int job_id);

    @Select("select department_name from department where department_id=#{department_id} ")
    public String selectdepartname(int department_id);

    @Select("select job_name from job where job_id=#{job_id} ")
    public String selectjobname(int job_id);

    @Select("select emp_id from employee where department_name=#{department_name} and job_name=#{job_name}")
    public List<Integer> selectempid(String department_name,String job_name);

    @Update("<script> update employee set active=0,leave_time=#{leave_time} where emp_id in <foreach collection = 'ids' item='id'  open='('  separator=',' close=')' > #{id} </foreach> </script>  ")
    public void deleteemp(@Param(value="ids")List<Integer> ids, Date leave_time);

    @Delete("<script> delete from salary  where emp_id in <foreach collection = 'ids' item='id'  open='('  separator=',' close=')' > #{id} </foreach> </script>  ")
    public void deletesalary(@Param(value="ids")List<Integer> ids);

    @Delete("<script> delete from attendance  where emp_id in <foreach collection = 'ids' item='id'  open='('  separator=',' close=')' > #{id} </foreach> </script>  ")
    public void deleteattendance(@Param(value="ids")List<Integer> ids);

    @Select("select * from job where department_id=#{department_id} ")
    public List<Department> searchdepat(int department_id);

    @Select("select * from (select * from  department join  job using(department_id))a where job_id=#{job_id} ")
    public Department editbefore(int job_id);

    @Update("update job set job_name=#{job_name},department_id=#{department_id},job_money=#{money} where job_id=#{job_id}")
    public void edit(int job_id,String job_name,int department_id,double money);

    @Update("update job set job_money=#{money} where job_id=#{job_id}")
    public void editme(int job_id,double money);


    @Delete("delete from job where job_id=#{job_id}")
    public void deletejob(int job_id);

    @Delete("delete from department where department_id=#{department_id}")
    public void deletedepartment(int department_id);

    @Select(" select * from  department join  job using(department_id)  where department_name like CONCAT( '%',#{search},'%') order by  department_name ")
    public List<Department> search1(String search);

    @Select(" select * from  department join  job using(department_id)  where job_name like CONCAT( '%',#{search},'%') order by  department_name ")
    public List<Department> search2(String search);

    @Select(" select * from  department join  job using(department_id)  where job_money between #{search1} and #{search2} order by  department_name ")
    public List<Department> searchmoney(int search1,int search2);

}
