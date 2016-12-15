package com.hlkj.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.hlkj.util.ConnectionDB;

public class ImportDataDd {

	public static void main(String[] args) throws Exception {
		System.out.println("司机号码开始插入...");
		POIFSFileSystem fs=new POIFSFileSystem(new FileInputStream("d:/didi.xls"));//文件路径//new FileInputStream(fileFullName)
		 HSSFWorkbook wbHssfWorkbook=new HSSFWorkbook(fs);//打开工作薄
	     HSSFSheet sheet=wbHssfWorkbook.getSheetAt(0);//打开工作表
	     HSSFRow row=null;
	     String data=null;
	     List<String>  list_baidu2 = new ArrayList<String>();
	     ConnectionDB db = new ConnectionDB();
		     for ( int i = 0; i <=sheet.getLastRowNum(); i++)
		     {   
		            row =sheet.getRow(i);
		            String no="";
		            String status="";
		            for (int j = 0; j <= 0; j++)
		            {//循环读取每一列
		                switch (row.getCell((short)j)!=null?row.getCell((short)j).getCellType():HSSFCell.CELL_TYPE_BLANK) 
		                {//判断单元格的数据类型
			                case HSSFCell.CELL_TYPE_BLANK:
			                	  break;
			                case HSSFCell.CELL_TYPE_NUMERIC:
			                    break;
			                default:
			                    data=row.getCell((short)j).getStringCellValue().trim();
		                }  
//		               if(j==0){
//		            	   no=data;
//		               }else{
//		            	   status=data;
//		               }
		            }

 		            db.executeUpdate("insert into ah_yldd_sj values(?,?) ",new Object[]{(i+1),data});
 		            System.out.println("正在插入数据..."+(i+1)+",----"+data+",");
		            
		     }
		     
		       
	} 
}
