package com.shildon.chowder.template;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * 封装hibernate基本操作。
 * @author shildon<shildondu@gmail.com>
 * @date Dec 5, 2015 8:40:09 PM
 *
 */
public class HibernateTemplate {
	
	// 需要用Spring注入或手动初始化
	private static SessionFactory sessionFactory;
	private static Session session;
	private static Transaction transaction;
	
	private static final Logger log = Logger.getLogger(HibernateTemplate.class);
	
	/**
	 * 开始事务
	 */
	private static void beginTransaction() {
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}
	
	/**
	 * 结束事务
	 */
	private static void endTransaction() {
		transaction.commit();
		session.close();
	}
	
	/**
	 * 存储对象
	 * @param t 目标对象
	 * @return
	 */
	public static <T> boolean save(T t) {
		boolean flag = false;
		beginTransaction();
		try {
			flag = session.save(t) == null;
		} catch (Exception e) {
			log.error(t.getClass().getName() + ": 存储错误！", e);
		} finally {
			endTransaction();
		}
		return flag;
	}
	
	/**
	 * 删除对象
	 * @param t 目标对象
	 * @return
	 */
	public static <T> boolean delete(T t) {
		boolean flag = false;
		beginTransaction();
		try {
			session.delete(t);
			flag = true;
		} catch (Exception e) {
			log.error(t.getClass().getName() + ": 删除错误！", e);
		} finally {
			endTransaction();
		}
		return flag;
	}
	
	/**
	 * 通过sql语句建立查询
	 * @param sql sql语句
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> findBySql(String hql) {
		Query query = null;
		beginTransaction();
		try {
			query = session.createQuery(hql);
		} catch (Exception e) {
			log.error("查询hql语句出错！", e);
		} finally {
			endTransaction();
		}
		return query.list();
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	public static <T> List<T> findAll(Class<T> clazz) {
		String name = clazz.getName();
		String hql = "from " + name;
		return findBySql(hql);
	}
	
	/**
	 * 更新对象
	 * @param t 目标对象
	 * @return
	 */
	public static <T> boolean update(T t) {
		boolean flag = false;
		beginTransaction();
		try {
			session.update(t);
			flag = true;
		} catch (Exception e) {
			log.error(t.getClass().getName() + ": 更新错误！", e);
		} finally {
			endTransaction();
		}
		return flag;
	}

}
