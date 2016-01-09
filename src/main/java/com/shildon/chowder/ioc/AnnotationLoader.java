package com.shildon.chowder.ioc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 加载扫描目录下的所有Class。
 * @author shildon<shildondu@gmail.com>
 * @date Jan 2, 2016 4:02:25 PM
 *
 */
public class AnnotationLoader {
	
	private static final String DEFAULT_PROPERTIES = "scan.properties";
	private static final String PACKAGE_NAME = "packageName";
	private static final String PROPERTIES_SUFFIX = ".properties";
	private static final String CLASS_SUFFIX = ".class";
	
	private static final String PROJECT_ROOT;
	static {
		PROJECT_ROOT = Thread.currentThread().getContextClassLoader().getResource("./").getPath();
	}
	
	private static final Log log = LogFactory.getLog(AnnotationLoader.class);
	
	/**
	 * properties文件的默认位置位于项目根目录，默认文件名为scan.properties
	 * @return
	 * @throws FileNotFoundException
	 */
	public List<Class<?>> loadResource() throws FileNotFoundException {
		return loadResource(DEFAULT_PROPERTIES);
	}
	
	/**
	 * properties文件的默认位置位于项目根目录。
	 * @param fileName 
	 * @return
	 * @throws FileNotFoundException
	 */
	public List<Class<?>> loadResource(String fileName) throws FileNotFoundException {
		Properties properties = new Properties();
		InputStream is = null;
		List<Class<?>> clazzs = new LinkedList<Class<?>>();
		
		// 构造绝对路径
		String path = PROJECT_ROOT + fileName;
		
		if (log.isDebugEnabled()) {
			log.debug(path);
		}

		if (!Files.exists(Paths.get(path))) {
			throw new FileNotFoundException("The properties file is not existing!");
		} else {
			if (null != fileName && fileName.endsWith(PROPERTIES_SUFFIX)) {
				is = Thread.currentThread().getContextClassLoader().
						getResourceAsStream(fileName);
				if (null != is) {
					try {
						properties.load(is);
						String packageName = (String) properties.get(PACKAGE_NAME);
						
						if (log.isDebugEnabled()) {
							log.debug(packageName);
						}

						loadClass(packageName, clazzs);
					} catch (IOException e) {
						log.error("Can not load the path!", e);
					}
				}
				
			}
		}
		return clazzs;
	}
	
	private List<Class<?>> loadClass(String packageName, List<Class<?>> clazzs) {
		// 将包名中的.替代成/
		String packagePath = packageName.replace(".", "/");
		if (packagePath.endsWith("/class")) {
			int packageLength = packagePath.lastIndexOf("/class");
			packagePath = packagePath.substring(0, packageLength) + 
					CLASS_SUFFIX;
		}
		String fullPath = PROJECT_ROOT + packagePath;
		
		if (log.isDebugEnabled()) {
			log.debug(fullPath);
		}
		
		File directory = new File(fullPath);
		
		if (directory.exists()) {
			// 如果是目录，扫描目录下的class文件
			if (directory.isDirectory()) {
				File[] files = directory.listFiles();
				
				for (File file : files) {
					// 递归调用
					loadClass(packageName + "." + file.getName(), clazzs);
				}
			} else {
				try {
					if (packageName.endsWith(CLASS_SUFFIX)) {
						int fileNameLength = packageName.lastIndexOf(CLASS_SUFFIX);
						packageName = packageName.substring(0, fileNameLength);
						Class<?> clazz = Class.forName(packageName);
						clazzs.add(clazz);
					}
				} catch (ClassNotFoundException e) {
					log.error("Class: " + packageName + " is not found!", e);
				}
			}
		}
		return clazzs;
	}

}
