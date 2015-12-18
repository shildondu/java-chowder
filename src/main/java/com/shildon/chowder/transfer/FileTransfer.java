package com.shildon.chowder.transfer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 文件传输工具。
 * @author shildon<shildondu@gmail.com>
 * @date Dec 16, 2015 10:39:01 PM
 *
 */
public class FileTransfer {
	
	public final Log log = LogFactory.getLog(FileTransfer.class);
	
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
			download(response.getOutputStream(), prefix + fileName);
		} catch (IOException e) {
			log.error("Can not get the outputstream of response.", e);
		}
	}
	
	/**
	 * 将文件写入流。
	 * @param outputStream
	 * @param fullPath
	 */
	public void download(OutputStream outputStream, String fullPath) {
		// Path类似与java.io.File，但封装了更多的对路径的操作。
		Path path = Paths.get(fullPath);
		// Files封装了许多对Path的操作。
		if (Files.exists(path)) {
			try {
				Files.copy(path, outputStream);
			} catch (IOException e) {
				log.error("Download error.", e);
			}
		} else {
			log.info("The file is not existing.");
		}
	}
	
	/**
	 * 将文件写入目标路径。
	 * @param targetPath
	 * @param fullPath
	 */
	public void download(String targetPath, String fullPath) {
		Path target = Paths.get(targetPath);
		Path source = Paths.get(fullPath);
		
		if (Files.exists(source)) {
			if (Files.exists(target)) {
				try {
					Files.createFile(target);
				} catch (IOException e) {
					log.error("Can not create target file.", e);
				}
			}
			try {
				Files.copy(source, target);
			} catch (IOException e) {
				log.error("Download error.", e);
			}
		} else {
			log.info("The file is not existing");
		}
	}

}
