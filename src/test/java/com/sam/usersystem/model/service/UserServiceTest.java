package com.sam.usersystem.model.service;

import java.util.Map;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sam.usersystem.model.UsersBean;

public class UserServiceTest {
	private ApplicationContext context;
	private UserService userService;
	private SessionFactory sessionFactory;
	
	@Before
	public void init() {
		context = new AnnotationConfigApplicationContext(com.sam.usersystem.config.SpringJavaConfig.class);
		
		userService = (UserService) context.getBean("userService");
		
		sessionFactory = (SessionFactory) context.getBean("sessionFactory");
	}
	
	
	@Test
	public void regist() {
		UsersBean bean = new UsersBean();
		String userID = "abc103252";
		String userName = "Sam2";
		String pwd = "abc9814016";
		
		bean.setUserID(userID);
		bean.setPwd(pwd);
		bean.setUserName(userName);
		
		Map<String,String> map = null;
		sessionFactory.getCurrentSession().beginTransaction();
		map = userService.regist(bean);
		sessionFactory.getCurrentSession().getTransaction().commit();
		
		if(map.containsKey("errorMsg")) {
			System.out.println(map.get("errorMsg"));
		} else {
			System.out.println(map.get("userId"));
			System.out.println(map.get("userName"));
		}
		
	}
	
	
	@After
	public void destroy() {
		sessionFactory.close();
		((ConfigurableApplicationContext) context).close();
	}
}
