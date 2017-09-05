package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {

	private Workbook workbook;
	private Sheet sheet;
	
	// 初始化
	public ReadExcel(String filePath, String sheetName) {
		String[] s = filePath.split("\\.");// 根据"."分离路径来获取文件后缀名
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(filePath));
			// 根据后缀名判断创建哪种workbook
			if (s[1].equals("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (s[1].equals("xls")) {
				workbook = new HSSFWorkbook(fis);
			}
			sheet = workbook.getSheet(sheetName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// 获取行数
	public int getrowNum() {
		return sheet.getLastRowNum() + 1;
	}
	
	//获取列数
	public int getColumnNum(){
		return sheet.getRow(0).getLastCellNum();
	}
	
	// 关闭流
	public void close() {
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//获取全部数据
	public Object[][] getData(){
		ArrayList<String[]> list = new ArrayList<String[]>();
		a:for (int i = 1; i < getrowNum(); i++) {
			String[] line = new String[getColumnNum()];
			for (int j = 0; j < getColumnNum(); j++) {
				try {
					line[j] = sheet.getRow(i).getCell(j).getStringCellValue();
				} catch (Exception e) {
					line[j] = String.valueOf((int)sheet.getRow(i).getCell(j).getNumericCellValue());
				}
				if(line[0].equals("")){
					break a;
				}
			}
			list.add(line);
		}
		Object[][] object = new Object[list.size()][getColumnNum()];
		for (int i = 0; i < list.size(); i++) {
			object[i] = list.get(i);
		}
		
		return object;
	}


}
