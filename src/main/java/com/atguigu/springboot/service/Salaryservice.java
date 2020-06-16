package com.atguigu.springboot.service;

import com.atguigu.springboot.bean.*;
import com.atguigu.springboot.mapper.SalaryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class Salaryservice {
    @Resource
    private  SalaryMapper salaryMapper;
    /**
     * 展示工资表
     */
    public PageInfo<Salary> findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Salary> salaries1=salaryMapper.findAll();
        PageInfo<Salary> salaries=new PageInfo<>(salaries1,pageNum);
        return salaries;
    }

    /**
     * 搜索框查询员工
     */
    public PageInfo<Salary> search(int pageNum, int pageSize,String search){
        PageHelper.startPage(pageNum,pageSize);

            List<Salary> salaries1=salaryMapper.search1(search);
            PageInfo<Salary> salaries=new PageInfo<>(salaries1,pageNum);
            return salaries;


    }

    /**
     * 搜索入职时间
     */
    public PageInfo<Salary> searchtime(int pageNum, int pageSize,String search1,String search2){
        PageHelper.startPage(pageNum,pageSize);
        String year1,year2;
        year1=search1+"-01";//字符串拼接
        year2=search2+"-01";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date1 = null;
        java.util.Date date2 = null;
        try {
            date1 = format.parse(year1);
            date2 = format.parse(year2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date d1 = new java.sql.Date(date1.getTime());//获取年月日
        java.sql.Date d2 = new java.sql.Date(date2.getTime());//获取年月日
        if (!d1.before(d2)){
            Date date=d1;
            d1=d2;
            d2=date;
        }
        List<Salary> salaries1=salaryMapper.searchtime(d1,d2);
        PageInfo<Salary> salaries=new PageInfo<>(salaries1,pageNum);
        return salaries;


    }

    /**
     * 通过员工名返回基本工资和id
     */
    public Map<Object, String> checksalary(String emp_name){
        Map<Object, String> resultMap = new HashMap<Object, String>();
        Employee employee1=salaryMapper.selectid(emp_name);
        if(employee1==null){
            return resultMap;
        }
        Employee employee=salaryMapper.selectdepartmentname(employee1.getEmp_id());
        int department_id=salaryMapper.selectdepartmentid(employee.getDepartment_name());
        double job_money=salaryMapper.selectmoney(department_id,employee.getJob_name());
        String job_money1=String.valueOf(job_money);
        String emp_id1=String.valueOf(employee1.getEmp_id());
        resultMap.put("job_money",job_money1);
        resultMap.put("emp_id",emp_id1);
        return resultMap;
    }

    /**
     * 通过员工id返回考勤扣款和加班奖金
     */
    public Map<Object, Double> getmoney(int emp_id, String month){
        Map<Object, Double> resultMap = new HashMap<Object, Double>();
       Policy policy=salaryMapper.findpolicy();
       List<Attendance> attendances=salaryMapper.searchmonth(month,emp_id);
       if(attendances==null){
           return resultMap;
       }

       double leave_money = 0;
       double off_money = 0;
       double over_money = 0;
       for(int i=0;i<attendances.size();i++){
           off_money+=attendances.get(i).getOff_time();
           leave_money+=attendances.get(i).getLeave_time();
           over_money+=attendances.get(i).getOver_time();
       }
       leave_money=leave_money*policy.getLeave_money()+off_money*policy.getOff_money();
       over_money=over_money*policy.getOver_money();

        resultMap.put("leave_money",leave_money);
        resultMap.put("over_money",over_money);
        return resultMap;
    }

    /**
     * 添加员工工资
     */
    public void add(String emp_name, int emp_id, double salary,
                      String month, double leave_time,
                    double overtime_salary, double resalary){
        String year;
        year=month+"-01";//字符串拼接
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = format.parse(year);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date d = new java.sql.Date(date.getTime());//获取年月日
        Salary salary1=  salaryMapper.checkedit(emp_id,month);
        if(salary1==null){
            salaryMapper.add(emp_id, salary, emp_name, d, resalary, leave_time, overtime_salary, month);
        }else {
            int salary_id=salary1.getSalary_id();
            salaryMapper.addc(emp_id,salary,emp_name,resalary,leave_time,overtime_salary,salary_id);
        }
    }

    /**
     * 返回修改页面
     */
    public Salary editbefore(int salary_id){
        Salary salary=salaryMapper.editbefore(salary_id);
        return  salary;
    }

    /**
     * 修改工资信息
     */
    public void edit(String emp_name, int emp_id, double salary,
                     String month, double leave_time,
                     double overtime_salary, double resalary,int salary_id){
        String year;
        year=month+"-01";//字符串拼接
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = format.parse(year);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date d = new java.sql.Date(date.getTime());//获取年月日
        Salary salary1=  salaryMapper.checkedit(emp_id,month);
        if(salary1==null){
            salaryMapper.edit(emp_id,salary,emp_name,d,resalary,leave_time,overtime_salary,salary_id,month);
        }else {
            if(salary_id==salary1.getSalary_id()){
                salaryMapper.edit(emp_id,salary,emp_name,d,resalary,leave_time,overtime_salary,salary_id,month);
            }else{
                salaryMapper.delete(salary_id);
                salary_id=salary1.getSalary_id();
                salaryMapper.edit(emp_id,salary,emp_name,d,resalary,leave_time,overtime_salary,salary_id,month);
            }
        }
    }

    /**
     * 删除工资信息
     */
    public void delete(int salary_id){
        salaryMapper.delete(salary_id);
    }

    /**
     * 返回年度汇总
     */
    public Salarysummary summary(String year,String searchname){
        Salarysummary salarysummary=new Salarysummary();
        List<Salary> salary;
        if(searchname==""){
            salary=salaryMapper.searchyear(year);
        }else {
            salary=salaryMapper.searchyearname(year,searchname);
        }

        int num=salary.size();
        String tomonth;
        String[] month=new String[num];
        for(int i=0;i<num;i++){
            month[i]=salary.get(i).getMonth();
        }
        ArrayList list=new ArrayList();

        for (int i=0;i<month.length;i++){
            if(!list.contains(month[i]))
                list.add(month[i]);
        }
        Map<String, List<Salary>> summary = new HashMap<String , List<Salary>>();
        List<Salary> salaries;
        for(int i=0;i<list.size();i++){
            tomonth=list.get(i).toString().substring(5,7);
            if(searchname=="") {
                salaries = salaryMapper.searchtomonth(list.get(i).toString());
            }else {
                salaries = salaryMapper.searchtomonthname(list.get(i).toString(),searchname);
            }
            summary.put(tomonth,salaries);
        }
        salarysummary.setNum(num);
        salarysummary.setSummaryMap(summary);
        return salarysummary;

    }

    /**
     * 批量增加员工工资
     */
    public void addmany(String cnashu2,String cnashu3, String cnashu4,
                          String cnashu5,String cnashu6,String cnashu7,
                          String cnashu8){
        String [] emp_name=cnashu2.split(",");
        String [] emp_id1=cnashu3.split(",");
        String [] salary1=cnashu4.split(",");
        String [] month=cnashu5.split(",");
        String [] leave_salary1=cnashu6.split(",");
        String [] over_salary1=cnashu7.split(",");
        String [] resalary1=cnashu8.split(",");
        int len=emp_id1.length;
        Integer[] emp_id=new Integer[len];
        Double[] salary=new Double[len];
        Double[] leave_salary=new Double[len];
        Double[] over_salary=new Double[len];
        Double[] resalary=new Double[len];
        Date [] year=new Date[len];
        for(int i=0;i<emp_id1.length;i++){
            emp_id[i]=Integer.valueOf(emp_id1[i]);
            salary[i]=Double.valueOf(salary1[i]);
            leave_salary[i]=Double.valueOf(leave_salary1[i]);
            over_salary[i]=Double.valueOf(over_salary1[i]);
            resalary[i]=Double.valueOf(resalary1[i]);

            String d;
            d=month[i]+"-01";//字符串拼接
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = null;
            try {
                date = format.parse(d);
            } catch (Exception e) {
                e.printStackTrace();
            }
            java.sql.Date y = new java.sql.Date(date.getTime());//获取年月日
            year[i]=y;
        }

        for(int i=0;i<len;i++){
            Salary salary2=  salaryMapper.checkedit(emp_id[i],month[i]);
            if(salary2==null){
                salaryMapper.add(emp_id[i], salary[i], emp_name[i], year[i], resalary[i], leave_salary[i], over_salary[i], month[i]);
            }else {
                int salary_id=salary2.getSalary_id();
                salaryMapper.addc(emp_id[i],salary[i],emp_name[i],resalary[i],leave_salary[i],over_salary[i],salary_id);
            }
        }



    }

    /**
     * 批量删除
     */
    public void deleteAll(List<Integer> ids){
        salaryMapper.deleteAll(ids);
    }

    /**
     * 导入Excel
     */
    public ImportExcel batchImport(String fileName, MultipartFile file) throws Exception {
        ImportExcel importExcel = new ImportExcel();
        Map<String, Object> messages = new HashMap<>();

        int sum = 0; ///总条数
        int success = 0;  ///成功数（总数-失败数）
        int fail = 0;  ///失败
        boolean notNull = false;
        List<Salary> salaries=new ArrayList<>();

        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            //throw new MyException("上传文件格式不正确");
            messages.put("failto", "上传文件格式不正确");
            importExcel.setSum(sum);
            importExcel.setFail(fail);
            importExcel.setSuccess((sum-fail));
            importExcel.setMessages(messages);
            return importExcel;
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet!=null){
            notNull = true;
        }


        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null){
                continue;
            }

            Salary salary123=new Salary();
            boolean problem=false;

            String name = "";
            if(row.getCell(0)==null){
                messages.put("name"+r+"0","(第"+(r+1)+"行,姓名不能为空)");
                problem=true;
            }else if(row.getCell(0).getCellType() !=1){
                messages.put("name"+r+"1","(第"+(r+1)+"行,姓名请设为文本格式)");
                problem =true;
            }else {
                name=row.getCell(0).getStringCellValue();
            }

            String id="";
            int id1=0;
            if(row.getCell(1)==null){
                messages.put("id"+r+"1","(第"+(r+1)+"行,id不能为空)");
                problem=true;
            }else if(row.getCell(1).getCellType()!=0){
                messages.put("id"+r+"0","(第"+(r+1)+"行,员工ID格式不正确)");
                problem =true;
            }else {
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                id=row.getCell(1).getStringCellValue();
                id1=Integer.parseInt(id);
            }



            Employee employee=salaryMapper.checkname(name,id1);
            if(employee==null){
                messages.put("nameid"+r+"0","(第"+(r+1)+"行,请正确填写员工名或id)");
                problem=true;
            }

            String d;
            java.util.Date date = null;
            Date year = null;

            String month = "";
            if(row.getCell(2)==null){
                messages.put("month"+r+"1","(第"+(r+1)+"行,工资月份不能为空)");
                problem=true;
            }else if(row.getCell(2).getCellType() !=1){
                messages.put("month"+r+"0","(第"+(r+1)+"行,请正确填写工资年月)");
                problem =true;
            } else {
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                 month = row.getCell(2).getStringCellValue();
                d=month+"-01";//字符串拼接
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                try {
                    date = format.parse(d.toString());
                } catch (Exception e11) {
                    e11.printStackTrace();
                }
                if(date==null){
                    messages.put("month"+r+"3","(第"+(r+1)+"行,工资月份格式不正确)");
                    problem=true;
                }else{
                    year = new java.sql.Date(date.getTime());//获取年月日
                }
            }

            double salary=0;
            if(row.getCell(3)==null){
                messages.put("salary"+r+"1","(第"+(r+1)+"行,基础工资不能为空)");
                problem=true;
            }else if(row.getCell(3).getCellType() !=0){
                messages.put("salary"+r+"0","(第"+(r+1)+"行,基础工资格式不正确)");
                problem=true;
            }else{
                salary=row.getCell(3).getNumericCellValue();
            }

            double leave_money = 0;
            if(row.getCell(4)==null){
                messages.put("leave_money"+r+"1","(第"+(r+1)+"行,考勤扣款不能为空)");
                problem=true;
            }else if(row.getCell(4).getCellType() !=0){
                messages.put("leave_money"+r+"0","(第"+(r+1)+"行,考勤扣款格式不正确)");
                problem=true;
            }else{
                leave_money=row.getCell(4).getNumericCellValue();
            }


            double over_money =0 ;
            if(row.getCell(5)==null){
                messages.put("over_money"+r+"1","(第"+(r+1)+"行,加班奖金不能为空)");
                problem=true;
            }else if(row.getCell(5).getCellType() !=0){
                messages.put("over_money"+r+"0","(第"+(r+1)+"行,加班奖金格式不正确)");
                problem=true;
            }else{
                over_money=row.getCell(5).getNumericCellValue();
            }

            double resalary =0 ;
            if(row.getCell(6)==null){
                messages.put("resalary"+r+"1","(第"+(r+1)+"行,实际工资不能为空)");
                problem=true;
            }else if(row.getCell(6).getCellType() !=0){
                messages.put("resalary"+r+"0","(第"+(r+1)+"行,实际工资格式不正确)");
                problem=true;
            }else{
                resalary=row.getCell(6).getNumericCellValue();
            }






            if(problem==false){
                success++;
                salary123.setEmp_id(id1);
                salary123.setEmp_name(name);
                salary123.setLeave_salary(leave_money);
                salary123.setOvertime_salary(over_money);
                salary123.setSalary(salary);
                salary123.setResalary(resalary);
                salary123.setMonth(month);
                salary123.setTime(year);
                salaries.add(salary123);
            }

            sum++;

        }

        for(Salary salary:salaries){
           Salary salary1=salaryMapper.checkedit(salary.getEmp_id(),salary.getMonth());
           if(salary1==null){
               salaryMapper.add(salary.getEmp_id(),salary.getSalary(),salary.getEmp_name(),salary.getTime(),salary.getResalary(),salary.getLeave_salary(),salary.getOvertime_salary(),salary.getMonth());
           }else{
               int salary_id=salary1.getSalary_id();
               salaryMapper.addc(salary.getEmp_id(),salary.getSalary(),salary.getEmp_name(),salary.getResalary(),salary.getLeave_salary(),salary.getOvertime_salary(),salary_id);
           }
        }
        fail=sum-success;
        if(fail==0){
            messages.put("failto", "暂无错误信息");
        }else{
            messages.put("failto", "导入失败原因如下");
        }

        importExcel.setSum(sum);
        importExcel.setFail(fail);
        importExcel.setSuccess((sum-fail));
        importExcel.setMessages(messages);
        return importExcel;
    }

    /**
     * 返回基础工资柱状图
     */
    public Salarysummary summarysalary(String year){
        Salarysummary salarysummary =new Salarysummary();
        List<Salary> salaries;
        salaries=salaryMapper.searchyear(year);
        int num=salaries.size();
        String[] month=new String[num];
        String[] name=new String[num];
        String search1=null;
        for(int i=0;i<salaries.size();i++){
            month[i]= salaries.get(i).getTime().toString().substring(5,7);
            name[i]=salaries.get(i).getEmp_name();
        }
        ArrayList listmonth=new ArrayList();
        ArrayList listname=new ArrayList();
        List<Salary> salaries1;
        //获取月份
        for (int i=0;i<month.length;i++){
            if(!listmonth.contains(month[i]))
                listmonth.add(month[i]);
        }
        Collections.sort(listmonth);
        //获取名字
        for(int i=0;i<name.length;i++){
            if(!listname.contains(name[i]))
                listname.add(name[i]);
        }
        double salary=0;
        List<List<Double>> listList=new ArrayList<>();
        for(int i=0;i<listname.size();i++){
            List<Double> doubleList=new ArrayList<>();
            for(int j=0;j<listmonth.size();j++){
                search1=year+'-'+listmonth.get(j);
                salaries1=salaryMapper.searchtomonthname(search1,listname.get(i).toString());
                if(salaries1==null){
                    salary=0;
                    doubleList.add(salary);
                }else{
                    for(int k = 0; k <salaries1.size(); k ++){
                        salary+=salaries1.get(k).getSalary();
                    }
                    doubleList.add(salary);
                    salary=0;
                }
            }
            listList.add(doubleList);
        }

        salarysummary.setDoublelist(listList);
        salarysummary.setMonth(listmonth);
        salarysummary.setName(listname);
        return salarysummary;
    }

    /**
     * 返回实际工资柱状图
     */
    public Salarysummary summaryresalary(String year){
        Salarysummary salarysummary =new Salarysummary();
        List<Salary> salaries;
        salaries=salaryMapper.searchyear(year);
        int num=salaries.size();
        String[] month=new String[num];
        String[] name=new String[num];
        String search1=null;
        for(int i=0;i<salaries.size();i++){
            month[i]= salaries.get(i).getTime().toString().substring(5,7);
            name[i]=salaries.get(i).getEmp_name();
        }
        ArrayList listmonth=new ArrayList();
        ArrayList listname=new ArrayList();
        List<Salary> salaries1;
        //获取月份
        for (int i=0;i<month.length;i++){
            if(!listmonth.contains(month[i]))
                listmonth.add(month[i]);
        }
        Collections.sort(listmonth);
        //获取名字
        for(int i=0;i<name.length;i++){
            if(!listname.contains(name[i]))
                listname.add(name[i]);
        }
        double resalary=0;
        List<List<Double>> listList=new ArrayList<>();
        for(int i=0;i<listname.size();i++){
            List<Double> doubleList=new ArrayList<>();
            for(int j=0;j<listmonth.size();j++){
                search1=year+'-'+listmonth.get(j);
                salaries1=salaryMapper.searchtomonthname(search1,listname.get(i).toString());
                if(salaries1==null){
                    resalary=0;
                    doubleList.add(resalary);
                }else{
                    for(int k = 0; k <salaries1.size(); k ++){
                        resalary+=salaries1.get(k).getResalary();
                    }
                    doubleList.add(resalary);
                    resalary=0;
                }
            }
            listList.add(doubleList);
        }

        salarysummary.setDoublelist(listList);
        salarysummary.setMonth(listmonth);
        salarysummary.setName(listname);
        return salarysummary;
    }

}
