package com.atguigu.springboot.service;


import com.atguigu.springboot.bean.Department;
import com.atguigu.springboot.bean.EmpSummary;
import com.atguigu.springboot.bean.Employee;
import com.atguigu.springboot.bean.ImportExcel;
import com.atguigu.springboot.mapper.EmployeeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Employeeservice {
    @Resource
    private EmployeeMapper employeeMapper;

    /**
     * 查询员工表全部
     */
    public PageInfo<Employee> findAll(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Employee> emp1=employeeMapper.findAll();
        PageInfo<Employee> emp=new PageInfo<>(emp1,pageNum);
        return emp;
    }

    /**
     * 搜索框查询员工或者部门
     */
    public PageInfo<Employee> search(int pageNum, int pageSize,int type,String search){
        PageHelper.startPage(pageNum,pageSize);
        if(type==1){
            List<Employee> emp1=employeeMapper.search1(search);
            PageInfo<Employee> emp=new PageInfo<>(emp1,pageNum);
            return emp;
        }else{
            List<Employee> emp1=employeeMapper.search2(search);
            PageInfo<Employee> emp=new PageInfo<>(emp1,pageNum);
            return emp;
        }

    }

    /**
     * 搜索入职时间
     */
    public PageInfo<Employee> searchtime(int pageNum, int pageSize,Date search1,Date search2){
        PageHelper.startPage(pageNum,pageSize);
            if (!search1.before(search2)){
                Date date=search1;
                search1=search2;
                search2=date;
            }
            List<Employee> emp1=employeeMapper.searchtime(search1,search2);
            PageInfo<Employee> emp=new PageInfo<>(emp1,pageNum);
            return emp;


    }

    /**
     * 搜索离职员工或者部门
     */
    public PageInfo<Employee> quitsearch(int pageNum, int pageSize,int type,String search){
        PageHelper.startPage(pageNum,pageSize);
        if(type==1){
            List<Employee> emp1=employeeMapper.quitsearch1(search);
            PageInfo<Employee> emp=new PageInfo<>(emp1,pageNum);
            return emp;
        }else{
            List<Employee> emp1=employeeMapper.quitsearch2(search);
            PageInfo<Employee> emp=new PageInfo<>(emp1,pageNum);
            return emp;
        }

    }

    /**
     * 搜索离职时间
     */
    public PageInfo<Employee> quitsearchtime(int pageNum, int pageSize,Date search1,Date search2){
        PageHelper.startPage(pageNum,pageSize);
        if (!search1.before(search2)){
            Date date=search1;
            search1=search2;
            search2=date;
        }
        List<Employee> emp1=employeeMapper.quitsearchtime(search1,search2);
        PageInfo<Employee> emp=new PageInfo<>(emp1,pageNum);
        return emp;


    }

    /**
     * 批量删除员工信息
     */
    public void deleteAll(List<Integer> ids,Date date){
        employeeMapper.deleteAll(ids,date);
        employeeMapper.deleteAllsalary(ids);
        employeeMapper.deleteAllattendance(ids);
    }

    /**
     * 离职员工批量删除
     */
    public void empdeleteAll(List<Integer> ids){
        employeeMapper.empdeleteAll(ids);
    }

    /**
     * 查找部门表
     */
    public List<Department> searchdepart(){
        List<Department> departments=employeeMapper.searchdepart();
        return departments;
    }

    /**
     * 查找职位
     */
    public List<Department> selectjob(String department_name){
        List<Department> departments=employeeMapper.selectjob(department_name);
        return departments;
    }

    /**
     * 增加员工
     */
    public void add(String emp_name, int age, int sex, String phone, String education, String department_name, String job_name, Date join_date,String address ){
        employeeMapper.add(emp_name,age,sex,phone,education,department_name,job_name,join_date,address);
    }

    /**
     *
     * 返回修改页面
     */
    public  Employee editbefore(int emp_id){
        Employee employee=employeeMapper.editbefore(emp_id);
        return employee;
    }

    /**
     * 修改员工信息
     */
    public void edit(int emp_id,String emp_name, int age, int sex, String phone, String education,  Date join_date,String address){
        employeeMapper.edit(emp_id,emp_name,age,sex,phone,education,join_date,address);
    }

    /**
     * 删除员工信息
     */
    public void delete(int emp_id,Date date){
        employeeMapper.delete(emp_id,date);
        employeeMapper.deletesalary(emp_id);
        employeeMapper.deleteattendance(emp_id);
    }

    /**
     * 员工调岗
     */
    public void changejob(String department_name,String job_name,int emp_id){
        employeeMapper.changejob(department_name, job_name, emp_id);
    }

    /**
     * 添加调岗记录
     */
    public void addchange(int emp_id,Date change_date,String emp_name,String bedepartment_name,String bejob_name,String afdepartment_name,String afjob_name){
        employeeMapper.addchange(emp_id, change_date, emp_name, bedepartment_name, bejob_name, afdepartment_name, afjob_name);
    }

    /**
     * 查看员工离职记录
     */
    public PageInfo<Employee> findQuit(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Employee> emp1=employeeMapper.findQuit();
        PageInfo<Employee> emp=new PageInfo<>(emp1,pageNum);
        return emp;
    }

    /**
     * 离职员工删除
     */
    public void deletequit(int emp_id){
        employeeMapper.deletequit(emp_id);
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
        List<Employee> e=new ArrayList<>();
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
        Workbook wb;
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


            Employee employee=new Employee();
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

            String age="";
            int age1=0;
            if(row.getCell(1)==null){
                messages.put("age"+r+"0","(第"+(r+1)+"行,年龄不能为空)");
                problem=true;
            }else if(row.getCell(1).getCellType() !=0){
                messages.put("age"+r+"1","(第"+(r+1)+"行,年龄请设置为数值)");
                problem =true;
            }else {
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                age= row.getCell(1).getStringCellValue();
                age1=Integer.parseInt(age);
            }

            String sex="";
            int sex1=0;
            if(row.getCell(2)==null){
                messages.put("sex"+r+"0","(第"+(r+1)+"行,性别不能为空)");
                problem=true;
            }else if( row.getCell(2).getCellType() !=0){
                messages.put("sex"+r+"1","(第"+(r+1)+"行,性别请设置为数值)");
                problem =true;
            }else{
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                sex = row.getCell(2).getStringCellValue();
                sex1=Integer.parseInt(sex);
            }

            String phone="";
            String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
            if(row.getCell(3)==null){
                messages.put("phone"+r+"0","(第"+(r+1)+"行,电话不能为空)");
                problem=true;
            }else if(row.getCell(3).getCellType() !=0){
                messages.put("phone"+r+"1","(第"+(r+1)+"行,电话格式不正确)");
                problem=true;
            }else{
                row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                 phone=row.getCell(3).getStringCellValue();
            }

            String education="";
            if(row.getCell(4)==null){
                messages.put("education"+r+"0","(第"+(r+1)+"行,学历不能为空)");
                problem=true;
            }else if(row.getCell(4).getCellType() !=1){
                messages.put("education"+r+"1","(第"+(r+1)+"行,学历不正确)");
                problem=true;
            }else {
                education=row.getCell(4).getStringCellValue();
            }

            String department="";
            if(row.getCell(5)==null){
                messages.put("department"+r+"0","(第"+(r+1)+"行,部门不能为空)");
                problem=true;
            }else if(row.getCell(5).getCellType() !=1){
                messages.put("department"+r+"1","(第"+(r+1)+"行,部门不正确)");
                problem=true;
            }else {
                department=row.getCell(5).getStringCellValue();
            }

            String job="";
            if(row.getCell(6)==null){
                messages.put("job"+r+"0","(第"+(r+1)+"行,职位不能为空)");
                problem=true;
            }else if(row.getCell(6).getCellType() !=1){
                messages.put("job"+r+"1","(第"+(r+1)+"行,职位格式不正确)");
                problem=true;
            }else {
                job=row.getCell(6).getStringCellValue();
            }


            Department department1=employeeMapper.checkjob(department,job);
            if(department1==null){
                messages.put("depatmentjob"+r+"0","(第"+(r+1)+"行,不存在该部门职位)");
                problem=true;
            }


            Date date = null;
            java.util.Date date1;
            if(row.getCell(7)==null){
                messages.put("joindate"+r+"0","(第"+(r+1)+"行,日期不能为空)");
                problem=true;
            }else if(row.getCell(7).getCellType() !=0){
                messages.put("joindate"+r+"1","(第"+(r+1)+"行,日期格式不正确)");
                problem=true;
            }else{
                date1  = row.getCell(7).getDateCellValue();
                date=new java.sql.Date(date1.getTime());
            }

            String address="";
            if(row.getCell(8)==null){
                messages.put("address"+r+"0","(第"+(r+1)+"行,地址不能为空)");
                problem=true;
            }else if(row.getCell(8).getCellType() !=1){
                messages.put("address"+r+"1","(第"+(r+1)+"行,地址格式不正确)");
                problem=true;
            }else {
                address=row.getCell(8).getStringCellValue();
            }




            if(problem==false){
                success++;
                employee.setEmp_name(name);
                employee.setAge(age1);
                employee.setSex(sex1);
                employee.setPhone(phone);
                employee.setEducation(education);
                employee.setDepartment_name(department);
                employee.setJob_name(job);
                employee.setJoin_date(date);
                employee.setAddress(address);
                e.add(employee);
            }

            sum++;

        }

        for(Employee employee:e){
            employeeMapper.add(employee.getEmp_name(),employee.getAge(),employee.getSex(),employee.getPhone(),employee.getEducation(),employee.getDepartment_name(),employee.getJob_name(),employee.getJoin_date(),employee.getAddress());
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
     * 员工性别汇总
     */
    public  List<EmpSummary> empsummarysex(){
        List<Employee> emp=employeeMapper.findAll();
        int boy=0,gril=0;
        for (int i=0;i<emp.size();i++){
            if(emp.get(i).getSex()==0){
                gril++;
            }else{
                boy++;
            }
        }
        List<EmpSummary> empSummaries = new ArrayList<>();
        empSummaries.add(new EmpSummary("男",boy));
        empSummaries.add(new EmpSummary("女",gril));
        return empSummaries;
    }

    /**
     * 员工部门汇总
     */
    public  List<EmpSummary> empsummarydepart(){
        List<EmpSummary> empSummaries = new ArrayList<>();
        List<Employee> emp=employeeMapper.findAll();
        int num=emp.size();
        String[] depart=new String[num];
        for(int i=0;i<num;i++){
            depart[i]=emp.get(i).getDepartment_name();
        }
        ArrayList list=new ArrayList();

        for (int i=0;i<depart.length;i++){
            if(!list.contains(depart[i]))
                list.add(depart[i]);
        }

        for(int i=0;i<list.size();i++){
            int denum=employeeMapper.summaryde(list.get(i).toString());
            empSummaries.add(new EmpSummary(list.get(i).toString(),denum));
        }

        return empSummaries;
    }

    /**
     * 员工学历汇总
     */
    public  List<EmpSummary> empsummaryeducation(){
        List<EmpSummary> empSummaries = new ArrayList<>();
        List<Employee> emp=employeeMapper.findAll();
        int num=emp.size();
        String[] education=new String[num];
        for(int i=0;i<num;i++){
            education[i]=emp.get(i).getEducation();
        }
        ArrayList list=new ArrayList();

        for (int i=0;i<education.length;i++){
            if(!list.contains(education[i]))
                list.add(education[i]);
        }

        for(int i=0;i<list.size();i++){
            int denum=employeeMapper.summaryeducation(list.get(i).toString());
            empSummaries.add(new EmpSummary(list.get(i).toString(),denum));
        }

        return empSummaries;

    }

    /**
     * 员工状态统计
     */
    public  List<EmpSummary> empsummaryquit(){
        int alive=0,quit=0;
        List<Employee> emp=employeeMapper.findAllemp();
        for (int i=0;i<emp.size();i++){
            if(emp.get(i).getActive()==0){
                quit++;
            }else{
                alive++;
            }
        }
        List<EmpSummary> empSummaries = new ArrayList<>();
        empSummaries.add(new EmpSummary("在职",alive));
        empSummaries.add(new EmpSummary("离职",quit));
        return empSummaries;
    }
}
