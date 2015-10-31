package com.shildon.chowder.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * The Excel processing util.
 * @author shildon<shildondu@gmail.com>
 * @date Aug 15, 2015 3:52:53 PM
 *
 */
public class ExcelUtil {
	
	private HSSFWorkbook hssfWorkbook;
	private HSSFSheet hssfSheet;
	private int index = 0;
	
	/**
	 * Export the excel file to the outputstream.
	 * @param outputStream
	 * @param sheetName
	 * @param instruction if you need extra instruction
	 * @param objects
	 * @param headerNames key is the name you want to show in excel file, value is the property name.
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
	 * get the excel file inputstream.
	 * @param sheetName
	 * @param instruction if you need extra instruction
	 * @param objects
	 * @param headerNames key is the name you want to show in excel file, value is the property name.
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
					String value = method.invoke(objects.get(i), new Object[] {}).toString();
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