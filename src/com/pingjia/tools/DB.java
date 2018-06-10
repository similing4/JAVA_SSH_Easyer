package com.pingjia.tools;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pingjia.hibernate.HibernateSessionFactory;

public class DB {
	private static final Logger log = LoggerFactory.getLogger(DB.class);

	public static void add(Serializable obj) {
		save(obj);
	}
	public static void save(Serializable obj) {
		log.debug("saving instance");
		try {
			Session session = HibernateSessionFactory.getSession();
			Transaction tx = session.beginTransaction();
			session.save(obj);
			tx.commit();
			session.close();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public static void delete(Serializable obj) {
		log.debug("deleting instance");
		try {
			Session session = HibernateSessionFactory.getSession();
			Transaction tx = session.beginTransaction();
			session.delete(obj);
			tx.commit();
			session.close();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	public static List<?> select(String tablename,DB_Where where,int limitstart,int limitend) {
		List<?> list = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			Transaction tran = session.beginTransaction();
			String whereStr = (where.isEmpty()?"":(" where "+where.getWhereSQL()));
			System.out.println("from " + tablename + whereStr);
			String limitStr = "";
			if(limitstart != -1)
				limitStr = " limit " + limitstart;
			if(limitend != -1)
				limitStr = " , " + limitend + " ";
			Query q = session.createQuery("from " + tablename + whereStr + limitStr);
			list = q.list();
			tran.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
