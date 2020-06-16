package com.atguigu.springboot.mapper;

import com.atguigu.springboot.bean.Policy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

@Mapper
public interface PolicyMapper {
    @Select("select * from policy")
    public Policy find();
    @Update("update policy set leave_money=#{leave_money},over_money=#{over_money},off_money=#{off_money} where policy_id=#{policy_id}")
    public void edit(double leave_money,double over_money,double off_money,int policy_id);
}
