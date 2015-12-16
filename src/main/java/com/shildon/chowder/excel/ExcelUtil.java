package com.shildon.chowder.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel文件读写工具。
 * @author shildon<shildondu@gmail.com>
 * @date Aug 15, 2015 3:52:53 PM
 *
 */
public class ExcelUtil {
	
	private HSSFWorkbook hssfWorkbook;
	private HSSFSheet hssfSheet;
	private int index = 0;
	
	/**
	 * 导出Excel文件到输出流。
	 * @param outputStream
	 * @param sheetName
	 * @param instruction 如果你需要额外的说明，会占用第二行第一列
	 * @param objects
	 * @param headerNames key是Excel文件列头名称，value是对应类中属性名称
	 */
	public <T> void export(OutputStream outputStream, String sheetName, String[] instruction,
			List<T> objects, Map<String, String> headerNames) {
		hssfWorkbook = new HSSFWorkbook();
		hssfSheet = hssfWorkbook.createSheet(sheetName);
		createHeader(instruction, headerNames);
		setContent(objects, headerNames);
		write(outputStream);
	}
	
	/**
	 * 得到写有Excel文件的输入流。
	 * @param sheetName
	 * @param instruction 如果你需要额外的说明，会占用第二行第一列
	 * @param objects
	 * @param headerNames key是Excel文件列头名称，value是对应类中属性名称
	 * @return
	 */
	public <T> InputStream getInputStream(String sheetName, String[] instruction,
			List<T> objects, Map<String, String> headerNames) {
		hssfWorkbook = new HSSFWorkbook();
		hssfSheet = hssfWorkbook.createSheet(sheetName);
		createHeader(instruction, headerNames);
		setContent(objects, headerNames);

		ByteArrayInputStream inputStream = null;
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {

			hssfWorkbook.write(outputStream);
			inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	
	/**
	 * 通过流导入excel文件。
	 * @param inputStream
	 * @param sheetName
	 * @param headerNames key是Excel文件列头名称，value是对应类中属性名称
	 * @param target
	 * @return
	 */
	public <T> List<T> importExcel(InputStream inputStream, String sheetName,
			Map<String, String> headerNames, Class<T> target) {
		List<T> objects = new LinkedList<T>();
		try {
			hssfWorkbook = new HSSFWorkbook(inputStream);
			hssfSheet = hssfWorkbook.getSheet(sheetName);
			
			String[] setMethodName = getSetMethodName(headerNames);
			
			int rowCount = hssfSheet.getLastRowNum();
			for (int i = 1; i <= rowCount; i++) {
				HSSFRow result = hssfSheet.getRow(i);
				T object = target.newInstance();
				int cellCount = result.getLastCellNum();
				
				for (int j = 0; j <= cellCount; j++) {
					HSSFCell cell = result.getCell(j);
					Method method = null;
					try {
						method = target.getMethod(setMethodName[j], new Class[] {});
						method.invoke(object, cell.getStringCellValue());
					} catch (NoSuchMethodException | SecurityException |
							IllegalAccessException | IllegalArgumentException |
							InvocationTargetException e) {
						e.printStackTrace();
						continue;
					}
				}
				objects.add(object);
			}
		} catch (IOException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return objects;
	}
	
	private void createHeader(String[] instruction, Map<String, String> headerNames) {
		HSSFRow header = hssfSheet.createRow(index++);

		if (null != instruction && 0 != instruction.length) {
			for(int i = 0; i < instruction.length; i++) {
				HSSFCell hssfCell = header.createCell(i);
				hssfCell.setCellValue(instruction[i]);
			}
			header = hssfSheet.createRow(index++);
		}
		
		int i = 0;
		for (String head : headerNames.values()) {
			HSSFCell hssfCell = header.createCell(i++);
			hssfCell.setCellValue(head);
		}
	}
	
	private String[] getSetMethodName(Map<String, String> headerNames) {
		HSSFRow header = hssfSheet.getRow(0);
		int cellCount = header.getLastCellNum();
		String[] setMethodName = new String[headerNames.size()];
		
		for (int i = 0; i <= cellCount; i++) {
			HSSFCell cell = header.getCell(i);
			String value = cell.getStringCellValue();
			
			String methodName = headerNames.get(value);
			setMethodName[i] = null != methodName ? "set" + methodName.substring(0, 1).toUpperCase() + 
					methodName.substring(1) : null;
		}
		return setMethodName;
	}
	
	private String[] getMethodNames(Map<String, String> headerNames) {
		String[] methodNames = null;
		if (null != headerNames) {
			methodNames = new String[headerNames.size()];
			int i = 0;
			for (String head : headerNames.keySet()) {
				String methodName = "get" + head.substring(0, 1).toUpperCase() + 
						head.substring(1);
				methodNames[i++] = methodName;
			}
		}
		return methodNames;
	}
	
	private <T> void setContent(List<T> objects, Map<String, String> headerNames) {
		String[] methodNames = getMethodNames(headerNames);
		for(int i = 0; i < objects.size(); i++) {
			HSSFRow hssfRow = hssfSheet.createRow(index++);


			for(int j = 0; j < headerNames.size(); j++) {
				HSSFCell hssfCell = hssfRow.createCell(j);
				
				try {
					
					Method method = objects.get(i).getClass().getMethod(methodNames[j], new Class[] {});
					String value = null == method.invoke(objects.get(i), new Object[] { } ) ? null :
						method.invoke(objects.get(i), new Object[] { } ).toString();
					hssfCell.setCellValue(value);
					
				} catch(NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void write(OutputStream outputStream) {
		try {
			hssfWorkbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.flush();
				outputStream.close();
				hssfWorkbook.close();
				index = 0;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}