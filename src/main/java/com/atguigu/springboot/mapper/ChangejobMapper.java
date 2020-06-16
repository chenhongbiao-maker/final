package com.atguigu.springboot.mapper;

import com.atguigu.springboot.bean.Changejob;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;
import java.util.List;

@Mapper
public interface ChangejobMapper {
    @Select("select * from changejob order by change_date desc")
    public List<Changejob> findAll();
    @Select("select * from changejob where emp_name like CONCAT( '%',#{search},'%') ")
    public List<Changejob> search1(String search);

    @Select("select * from changejob where bedepartment_name like CONCAT( '%',#{search},'%') ")
    public List<Changejob> search2(String search);

    @Select("select * from changejob where change_date between #{search1} and #{search2}")
    public List<Changejob> searchtime(Date search1, Date search2);

    @Delete("<script> delete from changejob  where change_id in <foreach collection = 'ids' item='id'  open='('  separator=',' close=')' > #{id} </foreach> </script>  ")
    public void deleteAll(@Param(value="ids")List<Integer> ids);

    @Delete("delete from changejob  where change_id=#{change_id} ")
    public void deletechangejob(int change_id);
}
