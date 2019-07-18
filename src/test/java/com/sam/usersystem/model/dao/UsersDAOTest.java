package com.sam.usersystem.model.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sam.usersystem.model.UsersBean;

public class UsersDAOTest {
	private ApplicationContext context;
	private UsersDAO usersDAO;
	private SessionFactory sessionFactory;
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.sam.usersystem.config.SpringJavaConfig.class);
		
		usersDAO = (UsersDAO) context.getBean("usersDAOHibernate");
		
		sessionFactory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	@Test
	public void insert() {
		
		UsersBean bean = new UsersBean();
		String userID = "abc10324";
		String userName = "Sam";
		String pwd = "abc9814016";
		
		bean.setUserID(userID);
		bean.setPwd(pwd);
		bean.setUserName(userName);
		
		UsersBean result = null;
		sessionFactory.getCurrentSession().beginTransaction();
		
		result = usersDAO.insert(bean);
		if(result != null) {
			sessionFactory.getCurrentSession().getTransaction().commit();
		} else {
			sessionFactory.getCurrentSession().getTransaction().rollback();
		}
		
		
		if(result != null) {
			System.out.println(result);
		} else {
			System.out.println("Insert fail");
		}
		
	}
	
	
	@Test
	public void selectById() {
//		String userID = "abc10324";
		String userID = "abc9814016";
		
		UsersBean result = null;
		sessionFactory.getCurrentSession().beginTransaction();
		result = usersDAO.selectById(userID);
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		if(result != null) {
			System.out.println(result);
		} else {
			System.out.println("No Result");
		}
	}
	
	@Test
	public void selectByName() {
		String userName = "Sam";
//		String userName = "Jenny";
		
		UsersBean result = null;
		sessionFactory.getCurrentSession().beginTransaction();
		result = usersDAO.selectByName(userName);
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		if(result != null) {
			System.out.println(result);
		} else {
			System.out.println("No Result");
		}
	}
	
	@Test
	public void selectLikeName() {
		String userName = "Sam";
//		String userName = "Jenny";
		
		List<String> result = null;
		sessionFactory.getCurrentSession().beginTransaction();
		result = usersDAO.selectLikeName(userName);
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		if(result.size() != 0) {
			for(String resultUserName : result) {
				System.out.println(resultUserName);
			}
		} else {
			System.out.println("No Result");
		}
	}
	
	
	@After
	public void destroy() {
		sessionFactory.close();
		((ConfigurableApplicationContext) context).close();
	}
	
}
