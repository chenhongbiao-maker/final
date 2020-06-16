package com.atguigu.springboot.mapper;

import com.atguigu.springboot.bean.Attendance;
import com.atguigu.springboot.bean.Employee;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.List;

@Mapper
public interface AttendanceMapper {
    @Select("select * from attendance order by time desc")
    public List<Attendance> findAll();

    @Select("select * from attendance where emp_name like CONCAT( '%',#{search},'%') order by time desc ")
    public List<Attendance> search1(String search);

    @Select("select * from attendance where time between #{search1} and #{search2} order by time desc")
    public List<Attendance> searchtime(Date search1,Date search2);

    @Select("select * from employee where emp_name=#{emp_name} and active=1 limit 0,1 ")
    public Employee selectid(String emp_name);

    @Select("select * from employee where emp_name=#{emp_name} and emp_id=#{emp_id}")
    public Employee checkname(String emp_name,int emp_id);
    @Insert("insert into attendance(emp_name,emp_id,leave_time,over_time,off_time,time) values(#{emp_name},#{emp_id},#{leave_time},#{over_time},#{off_time},#{time})")
    public void add(String emp_name, int emp_id, double leave_time, double over_time, double off_time, Date time);
    @Delete("delete from attendance where attendance_id=#{attendance_id}")
    public void delete(int attendance_id);
    @Select("select * from attendance where attendance_id=#{attendance_id}")
    public Attendance editbefore(int attendance_id);
    @Select("select * from attendance where emp_id=#{emp_id} and date_format(time,'%Y-%m-%d')=#{time}")
    public Attendance checkadd(int emp_id,Date time);
    @Update("update attendance set emp_name=#{emp_name},emp_id=#{emp_id},leave_time=#{leave_time},over_time=#{over_time},off_time=#{off_time},time=#{time} where attendance_id=#{attendance_id}")
    public void edit(String emp_name, int emp_id, double leave_time, double over_time, double off_time, Date time,int attendance_id);
    @Delete("<script> delete from attendance  where attendance_id in <foreach collection = 'ids' item='id'  open='('  separator=',' close=')' > #{id} </foreach> </script>  ")
    public void deleteAll(@Param(value="ids")List<Integer> ids);
    @Select("select * from attendance where  date_format(time,'%Y')=#{search} ")
    public List<Attendance> searchyear(String search);

    @Select("select * from attendance where  date_format(time,'%Y')=#{search1} and emp_name=#{search2} ")
    public List<Attendance> searchyearname(String search1,String search2);

    @Select("select * from attendance where  date_format(time,'%Y-%m')=#{search} ")
    public List<Attendance> searchmonth(String search);

    @Select("select * from attendance where  date_format(time,'%Y-%m')=#{search1} and emp_name=#{search2} ")
    public List<Attendance> searchmonthname(String search1,String search2);
}
