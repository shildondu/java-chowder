package com.shildon.orm;

import java.util.LinkedList;
import java.util.List;

/**
 * 关系映射后，用来保存信息。
 * @author shildon<shildondu@gmail.com>
 * @date Jan 6, 2016 3:47:46 PM
 *
 */
public class Entity {
	
	private Class<?> clazz;
	private String tablename;
	private List<Property> properties = new LinkedList<Property>();
	
	static class Property {
		private String name;
		private String value;

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public Entity addProperty(String name, String value) {
		Property property = new Property();
		property.setName(name);
		property.setValue(value);
		properties.add(property);
		return this;
	}

}
