package com.jt.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.vo.Student;

@Controller
public class TestController {
	@RequestMapping(value = "downLoadStuInfoExcel", produces = "text/html;charset=UTF-8")
    public void downLoadStuInfoExcel(HttpServletResponse response, HttpServletRequest request) throws JSONException, UnsupportedEncodingException {
		
		JSONObject rt = new JSONObject();
        //json对象，用来记录下载状态值，写入log中，也可以把状态值返回到前台，这一部分可有可无。
        rt.put("status", "1");
        rt.put("message", "");
        rt.put("result", "");
		
        List<Student> stuList=new ArrayList<>();
	    Student stu = new Student();
	    stu.setId(1);
	    stu.setName("奈美");
	    stu.setSex("女");
	    stu.setTelephone("13812478358");
	    stu.setAddress("杭州");
	    stuList.add(stu);
	    
	    Workbook wb = new XSSFWorkbook();
	  //标题行抽出字段
	    String[] title = {"序号","姓名","性别","住址", "手机号"};
	    //设置sheet名称，并创建新的sheet对象
	    String sheetName = "学生信息一览";
	    Sheet stuSheet = wb.createSheet(sheetName);
	    //获取表头行
	    Row titleRow = stuSheet.createRow(0);
	    //创建单元格，设置style居中，字体，单元格大小等
	    CellStyle style = wb.createCellStyle();
	    Cell cell = null;
	    //把已经写好的标题行写入excel文件中
	    for (int i = 0; i < title.length; i++) {
	        cell = titleRow.createCell(i);
	        cell.setCellValue(title[i]);
	        cell.setCellStyle(style);
	    }
	    Row row = null;
	    for (int i = 0; i < stuList.size(); i++) {
	        //创建list.size()行数据
	        row = stuSheet.createRow(i + 1);
	        //把值一一写进单元格里
	        //设置第一列为自动递增的序号
	        row.createCell(0).setCellValue(i + 1);
	        row.createCell(1).setCellValue(stuList.get(i).getName());
	        row.createCell(2).setCellValue(stuList.get(i).getSex());
	        row.createCell(3).setCellValue(stuList.get(i).getAddress());
	        row.createCell(4).setCellValue(stuList.get(i).getTelephone());
	        ;
	    }
	    //设置单元格宽度自适应，在此基础上把宽度调至1.5倍
	    for (int i = 0; i < title.length; i++) {
	        stuSheet.autoSizeColumn(i, true);
	        stuSheet.setColumnWidth(i, stuSheet.getColumnWidth(i) * 15 / 10);
	    }
        //设置contentType为vnd.ms-excel
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        
        // 对文件名进行处理。防止文件名乱码，这里前台直接定义了模板文件名，所以就不再次定义了
        //String fileName = CharEncodingEdit.processFileName(request, "stuTemplateExcel.xlsx");
        // Content-disposition属性设置成以附件方式进行下载
        String fileName="学生信息一览.xlsx";

         fileName = new String(fileName.getBytes(), "ISO-8859-1");
        response.setHeader("Content-disposition", "attachment;filename="+fileName);
        //调取response对象中的OutputStream对象
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            rt.put("status", "0");
            rt.put("message", "模板文件下载失败");
        }
       
	}
}
