package com.atguigu.springboot.mapper;

import com.atguigu.springboot.bean.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("select * from admin where admin_name=#{admin_name} and password=#{password} and role=#{role} ")
    public Admin login(String admin_name,String password,String role);

    @Select("select * from admin ")
     public List<Admin> findAll();

    @Insert("insert into admin(admin_name,password,role) values(#{admin_name},#{password},#{role}) ")
    public void add(String admin_name,String password,String role);

    @Select("select * from admin where admin_id=#{admin_id}")
    public Admin editbefore(int admin_id);

    @Select("select * from admin where admin_name=#{admin_name}")
    public Admin editbefore1(String admin_name);

    @Select("select * from admin where admin_name=#{admin_name}")
    public Admin checkadminname(String admin_name);


    @Update("update admin set admin_name=#{admin_name} , password=#{password},role=#{role} where admin_id=#{admin_id} ")
    public void edit(String admin_name,String password,int admin_id,String role);

    @Delete("delete from admin where admin_id=#{admin_id}")
    public void delete(int admin_id);

    @Select("select * from admin where admin_name like CONCAT( '%',#{search},'%') ")
    public List<Admin> search1(String search);

    @Select("select * from admin where role like CONCAT( '%',#{search},'%') ")
    public List<Admin> search2(String search);
}
