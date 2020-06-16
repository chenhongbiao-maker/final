package com.atguigu.springboot.service;

import com.atguigu.springboot.bean.Changejob;
import com.atguigu.springboot.mapper.ChangejobMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

@Service
public class Changejobservice {
    @Resource
    ChangejobMapper changejobMapper;
    public PageInfo<Changejob> findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Changejob> changejobs1=changejobMapper.findAll();
        PageInfo<Changejob> changejobs=new PageInfo<>(changejobs1,pageSize);
        return changejobs;
    }

    /**
     * 搜索框查询员工或者部门
     */
    public PageInfo<Changejob> search(int pageNum, int pageSize,int type,String search){
        PageHelper.startPage(pageNum,pageSize);
        if(type==1){
            List<Changejob> changejobs1=changejobMapper.search1(search);
            PageInfo<Changejob> changejobs=new PageInfo<>(changejobs1,pageNum);
            return changejobs;
        }else{
            List<Changejob> changejobs1=changejobMapper.search2(search);
            PageInfo<Changejob> changejobs=new PageInfo<>(changejobs1,pageNum);
            return changejobs;
        }

    }

    /**
     * 搜索调职时间
     */
    public PageInfo<Changejob> searchtime(int pageNum, int pageSize, Date search1, Date search2){
        PageHelper.startPage(pageNum,pageSize);
        if (!search1.before(search2)){
            Date date=search1;
            search1=search2;
            search2=date;
        }
        List<Changejob> changejobs1=changejobMapper.searchtime(search1,search2);
        PageInfo<Changejob> changejobs=new PageInfo<>(changejobs1,pageNum);
        return changejobs;


    }

    /**
     * 批量删除
     */
    public void deleteAll(List<Integer> ids){
        changejobMapper.deleteAll(ids);
    }

    /**
     * 删除调职记录
     */
    public void deletechangejob(int changejob_id){
        changejobMapper.deletechangejob(changejob_id);
    }

}
