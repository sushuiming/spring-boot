package com.boot.system.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;


/*import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;*/

/**
 * 
 * @author
 * @version
 */
public class ExcelUtil {

	/**
	 * 根据数据导出excel表
	 * 
	 * @param title   表格标题名
	 * @param headerList 表格属性列名数组
	 * @param dataList 需要显示的数据集合,
	 * @param path     excel表保存路径
	 * @throws IOException 
	 */
	public static void exportExcel(String title, List<String> headerList, 
			List<Map<String, Object>> dataList, String path) throws IOException {				
		exportExcel(title, headerList, dataList, new File(path));
	}
	
	/**
	 * 根据数据导出excel表
	 * 
	 * @param title   表格标题名
	 * @param headerList 表格属性列名数组
	 * @param dataList 需要显示的数据集合,
	 * @param file     excel文件流
	 * @throws IOException 
	 */
	public static void exportExcel(String title, List<String> headerList, 
			List<Map<String, Object>> dataList, File file) throws IOException {				
		exportExcel(title, headerList, dataList, new FileOutputStream(file));
	}

	/**
	 * 根据数据导出excel表
	 * 
	 * @param title   表格标题名
	 * @param headerList 表格属性列名数组
	 * @param dataList 需要显示的数据集合,
	 * @param out     excel文件输出流流
	 * @throws IOException 
	 */
	public static void exportExcel(String title, List<String> headerList, 
			List<Map<String, Object>> dataList, OutputStream out) throws IOException {				
		/*HSSFWorkbook workbook = new HSSFWorkbook(); // 声明一个工作薄		
		HSSFSheet sheet = workbook.createSheet(title); // 生成一个表格
		//sheet.setDefaultColumnWidth((short) 15); // 设置表格默认列宽度为15个字节
		HSSFCellStyle style = workbook.createCellStyle(); // 生成一个样式
		// 设置这些样式
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFFont font = workbook.createFont(); // 生成一个字体
		// font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);		
		style.setFont(font); // 把字体应用到当前的样式
		
		// 产生表格标题行		
		if (headerList != null && headerList.size() > 0) {
			HSSFRow row = sheet.createRow(0);
			HSSFCell cell = null;
			HSSFRichTextString text = null;
			for (int i = 0, size = headerList.size(); i < size; i++) {				
				cell = row.createCell(i);
				cell.setCellStyle(style);				
				text = new HSSFRichTextString(headerList.get(i));
				cell.setCellValue(text);
			}
			
		}
		
		// 产生表格数据行		
		if (dataList != null && dataList.size() > 0) {
			HSSFRow row = null;
			HSSFCell cell = null;
			HSSFRichTextString text = null;
			Map<String, Object> map = null;
			for (int i = 0, size = dataList.size(); i < size; i++) {
				row = sheet.createRow(i + 1);
				map = dataList.get(i);
				int index = 0;     // 每行数据，单元格索引
				for (Object obj : map.values()) {
					cell = row.createCell(index);
					cell.setCellStyle(style);
					text = new HSSFRichTextString(String.valueOf(obj));
					cell.setCellValue(text);
					index++;
				}				
			}			
		}
		
		workbook.write(out);	*/	
	}

}
