package com.atguigu.springboot.service;

import com.atguigu.springboot.bean.*;
import com.atguigu.springboot.mapper.AttendanceMapper;
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
public class Attendanceservice {
    @Resource
     AttendanceMapper attendanceMapper;

    /**
     * 考勤列表
     */
    public PageInfo<Attendance> findAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Attendance> attendances1 = attendanceMapper.findAll();
        PageInfo<Attendance> attendances = new PageInfo<>(attendances1, pageSize);
        return attendances;
    }

    /**
     * 搜索框查询员工或者部门
     */
    public PageInfo<Attendance> search(int pageNum, int pageSize,String search){
        PageHelper.startPage(pageNum,pageSize);

            List<Attendance> attendances1=attendanceMapper.search1(search);
            PageInfo<Attendance> attendances=new PageInfo<>(attendances1,pageNum);
            return attendances;


    }

    /**
     * 搜索考勤时间
     */
    public PageInfo<Attendance> searchtime(int pageNum, int pageSize,Date search1,Date search2){
        PageHelper.startPage(pageNum,pageSize);
        if (!search1.before(search2)){
            Date date=search1;
            search1=search2;
            search2=date;
        }
        List<Attendance> attendances1=attendanceMapper.searchtime(search1,search2);
        PageInfo<Attendance> attendances=new PageInfo<>(attendances1,pageNum);
        return attendances;


    }

    /**
     * 返回员工id
     */
    public Map<Object, String> getid(String emp_name) {
        Map<Object, String> resultMap = new HashMap<Object, String>();
        Employee employee1 = attendanceMapper.selectid(emp_name);
        if (employee1 == null) {
            return resultMap;
        }
        String emp_id1=String.valueOf(employee1.getEmp_id());
        resultMap.put("emp_id",emp_id1);
        return resultMap;
    }

    /**
     * 查看是否存在这个员工
     */
    public Map<Object, String> checkname(String emp_name,int emp_id){
        Map<Object, String> resultMap = new HashMap<Object, String>();
        Employee employee1 = attendanceMapper.checkname(emp_name,emp_id);
        if (employee1 == null) {
            return resultMap;
        }
        resultMap.put("emp_id","存在");
        return resultMap;
    }

    /**
     * 增加考勤记录
     */
    public void add(String emp_name, int emp_id, double leave_time, double over_time, double off_time, Date time){
        Attendance attendance=attendanceMapper.checkadd(emp_id,time);
        if(attendance==null) {
            attendanceMapper.add(emp_name, emp_id, leave_time, over_time, off_time, time);
        }else{
            int attendance_id=attendance.getAttendance_id();
            attendanceMapper.edit(emp_name, emp_id, leave_time, over_time, off_time, time,attendance_id);
        }
    }

    /**
     * 删除考勤记录
     */
    public void delete(int attendance_id){
        attendanceMapper.delete(attendance_id);
    }

    /**
     * 编辑考勤记录前
     */
    public Attendance editbefore(int attendance_id){
        Attendance attendance=attendanceMapper.editbefore(attendance_id);
        return attendance;
    }

    /**
     * 编辑考勤记录
     */
    public void edit(String emp_name, int emp_id, double leave_time, double over_time, double off_time, Date time,int attendance_id){
        Attendance attendance=attendanceMapper.checkadd(emp_id,time);
        if(attendance==null){
            attendanceMapper.edit(emp_name, emp_id, leave_time, over_time, off_time, time,attendance_id);
        }else{

            if(attendance_id==attendance.getAttendance_id()){
                attendanceMapper.edit(emp_name, emp_id, leave_time, over_time, off_time, time,attendance_id);
            }else {
                attendanceMapper.delete(attendance_id);
                attendance_id = attendance.getAttendance_id();
                attendanceMapper.edit(emp_name, emp_id, leave_time, over_time, off_time, time, attendance_id);
            }
        }

    }

    /**
     * 批量删除
     */
    public void deleteAll(List<Integer> ids){
        attendanceMapper.deleteAll(ids);
    }

    /**
     *返回年度汇总
     */
    public Attendancesummary summary(String search,String searchname ){
        List<Attendance> attendances;
        if(searchname==""){
             attendances=attendanceMapper.searchyear(search);
        }else{
             attendances=attendanceMapper.searchyearname(search,searchname);
        }
        Attendancesummary  attendancesummary=new Attendancesummary();

        if(attendances==null){
            attendancesummary.setNum(0);
            return attendancesummary;
        }
        int num=attendances.size();
        String[] month=new String[attendances.size()];
        String search1=null;
        for(int i=0;i<attendances.size();i++){
            month[i]= attendances.get(i).getTime().toString().substring(5,7);
        }
        ArrayList list=new ArrayList();
        List<Attendance> attendances1;
        Object o;
        String tomonth;
        for (int i=0;i<month.length;i++){
            if(!list.contains(month[i]))
                list.add(month[i]);
        }
        Map<String, List<Attendance>> summary = new HashMap<String , List<Attendance>>();

        double over_time=0;
        double off_time=0;
        double leave_time=0;

        for(int i=0;i<list.size();i++){
            search1=search+'-'+list.get(i);
            o=list.get(i);
            tomonth=o.toString();
            if(searchname==""){
            attendances1=attendanceMapper.searchmonth(search1);
            }else{
                attendances1=attendanceMapper.searchmonthname(search1,searchname);
            }

            for(int k = 0; k <attendances1.size() - 1;k ++){

                for(int l = attendances1.size() - 1;l > k; l--){

                    if(attendances1.get(k).getEmp_id()==attendances1.get(l).getEmp_id()){

                        over_time=attendances1.get(k).getOver_time()+attendances1.get(l).getOver_time();
                        off_time=attendances1.get(k).getOff_time()+attendances1.get(l).getOff_time();
                        leave_time=attendances1.get(k).getLeave_time()+attendances1.get(l).getLeave_time();
                        attendances1.get(k).setLeave_time(leave_time);
                        attendances1.get(k).setOff_time(off_time);
                        attendances1.get(k).setOver_time(over_time);
                        attendances1.remove(l);
                    }

                }
                over_time=0;
                off_time=0;
                leave_time=0;
            }


            summary.put(tomonth,attendances1);
        }


        attendancesummary.setSummaryMap(summary);
        attendancesummary.setNum(num);


        return attendancesummary;


    }

    /**
     * 批量导入考勤
     */
    public void addmany(String cnashu2,String cnashu3, String cnashu4,
                        String cnashu5,String cnashu6,String cnashu7){
        String [] emp_name=cnashu2.split(",");
        String [] emp_id1=cnashu3.split(",");
        String [] leave_time1=cnashu4.split(",");
        String [] over_time1=cnashu5.split(",");
        String [] off_time1=cnashu6.split(",");
        String [] time1=cnashu7.split(",");
        int len=emp_id1.length;
        Integer[] emp_id=new Integer[len];
        Double[] leave_time=new Double[len];
        Double[] over_time=new Double[len];
        Double[] off_time=new Double[len];
        Date [] time=new Date[len];
        for(int i=0;i<emp_id1.length;i++){
            emp_id[i]=Integer.valueOf(emp_id1[i]);
            leave_time[i]=Double.valueOf(leave_time1[i]);
            over_time[i]=Double.valueOf(over_time1[i]);
            off_time[i]=Double.valueOf(off_time1[i]);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = null;
            try {
                date = format.parse(time1[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            java.sql.Date y = new java.sql.Date(date.getTime());//获取年月日
            time[i]=y;
        }

        for(int i=0;i<len;i++){
            Attendance attendance=attendanceMapper.checkadd(emp_id[i],time[i]);
            if(attendance==null) {
                attendanceMapper.add(emp_name[i], emp_id[i], leave_time[i], over_time[i], off_time[i], time[i]);
            }else{
                int attendance_id=attendance.getAttendance_id();
                attendanceMapper.edit(emp_name[i], emp_id[i], leave_time[i], over_time[i], off_time[i], time[i],attendance_id);
            }
        }



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
        List<Attendance> attendances=new ArrayList<>();

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

            Attendance attendance123=new Attendance();
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

            Employee employee=attendanceMapper.checkname(name,id1);
            if(employee==null){
                messages.put("nameid"+r+"0","(第"+(r+1)+"行,请正确填写员工名或id)");
                problem=true;
            }

            Date date = null;
            java.util.Date date1;
            if(row.getCell(2)==null){
                messages.put("time"+r+"0","(第"+(r+1)+"行,考勤日期不能为空)");
                problem=true;
            }else if(row.getCell(2).getCellType() !=0){
                messages.put("time"+r+"1","(第"+(r+1)+"行,考勤日期格式不正确)");
                problem=true;
            }else{
                date1  = row.getCell(2).getDateCellValue();
                date=new java.sql.Date(date1.getTime());
            }

            double leave_time=0;
            if(row.getCell(3)==null){
                messages.put("leave_time"+r+"1","(第"+(r+1)+"行,缺勤时间不能为空)");
                problem=true;
            }else if(row.getCell(3).getCellType() !=0){
                messages.put("leave_time"+r+"0","(第"+(r+1)+"行,缺勤时间格式不正确)");
                problem=true;
            }else{
                leave_time=row.getCell(3).getNumericCellValue();
            }

            double over_time=0;
            if(row.getCell(4)==null){
                messages.put("over_time"+r+"1","(第"+(r+1)+"行,加班时间不能为空)");
                problem=true;
            }else if(row.getCell(4).getCellType() !=0){
                messages.put("over_time"+r+"0","(第"+(r+1)+"行,加班时间格式不正确)");
                problem=true;
            }else{
                over_time=row.getCell(4).getNumericCellValue();
            }

            double off_time=0;
            if(row.getCell(5)==null){
                messages.put("off_time"+r+"1","(第"+(r+1)+"行,请假时间不能为空)");
                problem=true;
            }else if(row.getCell(5).getCellType() !=0){
                messages.put("off_time"+r+"0","(第"+(r+1)+"行,请假时间格式不正确)");
                problem=true;
            }else{
                off_time=row.getCell(5).getNumericCellValue();
            }





            if(problem==false){
                success++;
                attendance123.setEmp_name(name);
                attendance123.setEmp_id(id1);
                attendance123.setTime(date);
                attendance123.setOver_time(over_time);
                attendance123.setOff_time(off_time);
                attendance123.setLeave_time(leave_time);
                attendances.add(attendance123);
            }

            sum++;

        }

        for(Attendance attendance:attendances){
            Attendance attendance1=attendanceMapper.checkadd(attendance.getEmp_id(),attendance.getTime());
            if(attendance1==null){
                attendanceMapper.add(attendance.getEmp_name(),attendance.getEmp_id(),attendance.getLeave_time(),attendance.getOver_time(),attendance.getOff_time(),attendance.getTime());
            }else{
                int attendance_id=attendance1.getAttendance_id();
                attendanceMapper.edit(attendance.getEmp_name(),attendance.getEmp_id(),attendance.getLeave_time(),attendance.getOver_time(),attendance.getOff_time(),attendance.getTime(),attendance_id);
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
     * 返回加班柱状图
     */
    public Attendancesummary summaryovertime(String year){
        Attendancesummary attendancesummary =new Attendancesummary();
        List<Attendance> attendances;
        attendances=attendanceMapper.searchyear(year);
        int num=attendances.size();
        String[] month=new String[num];
        String[] name=new String[num];
        String search1=null;
        for(int i=0;i<attendances.size();i++){
            month[i]= attendances.get(i).getTime().toString().substring(5,7);
            name[i]=attendances.get(i).getEmp_name();
        }
        ArrayList listmonth=new ArrayList();
        ArrayList listname=new ArrayList();
        List<Attendance> attendances1;
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
        double over_time=0;
        List<List<Double>> listList=new ArrayList<>();
        for(int i=0;i<listname.size();i++){
            List<Double> doubleList=new ArrayList<>();
            for(int j=0;j<listmonth.size();j++){
                search1=year+'-'+listmonth.get(j);
                attendances1=attendanceMapper.searchmonthname(search1,listname.get(i).toString());
                if(attendances1==null){
                    over_time=0;
                    doubleList.add(over_time);
                }else{
                for(int k = 0; k <attendances1.size(); k ++){
                    over_time+=attendances1.get(k).getOver_time();
                }
                doubleList.add(over_time);
                over_time=0;
            }
            }
        listList.add(doubleList);
        }

        attendancesummary.setDoublelist(listList);
        attendancesummary.setMonth(listmonth);
        attendancesummary.setName(listname);

        return attendancesummary;
    }

    /**
     * 返回缺勤柱状图
     */
    public Attendancesummary summaryleavetime(String year){
        Attendancesummary attendancesummary =new Attendancesummary();
        List<Attendance> attendances;
        attendances=attendanceMapper.searchyear(year);
        int num=attendances.size();
        String[] month=new String[num];
        String[] name=new String[num];
        String search1=null;
        for(int i=0;i<attendances.size();i++){
            month[i]= attendances.get(i).getTime().toString().substring(5,7);
            name[i]=attendances.get(i).getEmp_name();
        }
        ArrayList listmonth=new ArrayList();
        ArrayList listname=new ArrayList();
        List<Attendance> attendances1;
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
        double leave_time=0;
        List<List<Double>> listList=new ArrayList<>();
        for(int i=0;i<listname.size();i++){
            List<Double> doubleList=new ArrayList<>();
            for(int j=0;j<listmonth.size();j++){
                search1=year+'-'+listmonth.get(j);
                attendances1=attendanceMapper.searchmonthname(search1,listname.get(i).toString());
                if(attendances1==null){
                    leave_time=0;
                    doubleList.add(leave_time);
                }else{
                    for(int k = 0; k <attendances1.size(); k ++){
                        leave_time+=attendances1.get(k).getLeave_time();
                    }
                    doubleList.add(leave_time);
                    leave_time=0;
                }
            }
            listList.add(doubleList);
        }

        attendancesummary.setDoublelist(listList);
        attendancesummary.setMonth(listmonth);
        attendancesummary.setName(listname);

        return attendancesummary;
    }

    /**
     * 返回请假柱状图
     */
    public Attendancesummary summaryofftime(String year){
        Attendancesummary attendancesummary =new Attendancesummary();
        List<Attendance> attendances;
        attendances=attendanceMapper.searchyear(year);
        int num=attendances.size();
        String[] month=new String[num];
        String[] name=new String[num];
        String search1=null;
        for(int i=0;i<attendances.size();i++){
            month[i]= attendances.get(i).getTime().toString().substring(5,7);
            name[i]=attendances.get(i).getEmp_name();
        }
        ArrayList listmonth=new ArrayList();
        ArrayList listname=new ArrayList();
        List<Attendance> attendances1;
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
        double off_time=0;
        List<List<Double>> listList=new ArrayList<>();
        for(int i=0;i<listname.size();i++){
            List<Double> doubleList=new ArrayList<>();
            for(int j=0;j<listmonth.size();j++){
                search1=year+'-'+listmonth.get(j);
                attendances1=attendanceMapper.searchmonthname(search1,listname.get(i).toString());
                if(attendances1==null){
                    off_time=0;
                    doubleList.add(off_time);
                }else{
                    for(int k = 0; k <attendances1.size(); k ++){
                        off_time+=attendances1.get(k).getOff_time();
                    }
                    doubleList.add(off_time);
                    off_time=0;
                }
            }
            listList.add(doubleList);
        }

        attendancesummary.setDoublelist(listList);
        attendancesummary.setMonth(listmonth);
        attendancesummary.setName(listname);

        return attendancesummary;
    }

}
