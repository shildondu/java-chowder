package com.shildon.file.transfer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 文件传输工具。
 * @author shildon<shildondu@gmail.com>
 * @date Dec 16, 2015 10:39:01 PM
 *
 */
public final class FileTransfer {
	
	public static final Log log = LogFactory.getLog(FileTransfer.class);
	
	/**
	 * 设置http下载相应头。
	 * @param response
	 */
	private void setHttpDownloadHead(HttpServletResponse response, String fileName) {
		try {
			//转换成IOS8859-1字符以供浏览器显示
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			log.error("Unsupported encode.", e);
		}
		response.setContentType("application/octet-stream");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
	}
	
	/**
	 * 将文件写入到response的流。
	 * @param response
	 * @param prefix 路径前缀，以/结尾
	 * @param fileName 文件名
	 */
	public void download(HttpServletResponse response, String prefix, String fileName) {
		setHttpDownloadHead(response, fileName);
		try {
			download(prefix + fileName, response.getOutputStream());
		} catch (IOException e) {
			log.error("Can not get the outputstream of response.", e);
		}
	}
	
	private void copy(Path source, OutputStream outputStream) {
		// Files封装了许多对Path的操作。
		if (Files.exists(source)) {
			try {
				// 每次复制8K
				Files.copy(source, outputStream);
			} catch (IOException e) {
				log.error("Download error.", e);
			}
		} else {
			log.info("The source file is not existing.");
		}
	}
	
	/**
	 * 将文件写入流。
	 * @param outputStream
	 * @param sourcePath 完整的源文件路径
	 */
	public void download(String sourcePath, OutputStream outputStream) {
		// Path类似与java.io.File，但封装了更多的对路径的操作。
		Path source = Paths.get(sourcePath);
		copy(source, outputStream);
	}
	
	/**
	 * 将文件写入目标路径。
	 * @param sourcePath
	 * @param targetPath
	 */
	public void download(String sourcePath, String targetPath) {
		Path source = Paths.get(sourcePath);
		Path target = Paths.get(targetPath);
		
		if (!Files.exists(target)) {
			try {
				Files.createFile(target);
			} catch (IOException e) {
				log.error("The target file is not existing and can not be created.", e);
			}
		}
		try {
			copy(source, Files.newOutputStream(target));
		} catch (IOException e) {
			log.error("Error in createing outputstream to targetPath.", e);
		}
	}
	
	/**
	 * 利用commons-fileupload实现文件上传
	 * @param request
	 * @param directoryPath 目标目录，以"/"结尾
	 */
	public void upload(HttpServletRequest request, String directoryPath) {
		// 判断httpRequest是否包含文件上传
		if (ServletFileUpload.isMultipartContent(request)) {
			Path directory = Paths.get(directoryPath);
			
			// 如果没有目录则创建
			if (!Files.exists(directory)) {
				try {
					Files.createDirectory(directory);
				} catch (IOException e) {
					log.error("Error in creating directory.", e);
				}
			}
		
			// 下面的代码开始使用Commons-UploadFile组件处理上传的文件数据
			FileItemFactory fileItemFactory = new DiskFileItemFactory();
			ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
			List<FileItem> fileItems = null;
			try {
				fileItems = servletFileUpload.parseRequest(request);
			} catch (FileUploadException e) {
				log.error("Error in parsing request.", e);
			}
		
			for (FileItem fileItem : fileItems) {
				if (!fileItem.isFormField()) {
					// 得到文件名
					String fileName = fileItem.getName();
					String fullPath = directoryPath + fileName;
					Path target = Paths.get(fullPath);

					// 有相同名称的文件,增加后缀
					for (int i = 0; Files.exists(target); i++) {
						directoryPath = directoryPath + "(" + i + ")";
						target = Paths.get(directoryPath);
					}
					// 创建新文件
					try {
						Files.createFile(target);
					} catch (IOException e) {
						log.error("Can not create target file.", e);
					}
					
					try {
						InputStream inputStream = fileItem.getInputStream();
						Files.copy(inputStream, target);
					} catch (IOException e) {
						log.error("Error in copying file.", e);
					}
				}
			}
		} else {
			log.info("The request is not a file upload request.");
		}
	}
}
