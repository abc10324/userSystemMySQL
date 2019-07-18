package com.sam.usersystem.model.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.sam.usersystem.model.UsersBean;
import com.sam.usersystem.model.dao.UsersDAO;

@Service
@Transactional
public class UserService {
	@Autowired
	private UsersDAO usersDAO;
	final private Logger logger = Logger.getLogger(UserService.class);
	
	
	@Transactional(isolation=Isolation.READ_UNCOMMITTED)
	public Map<String,String> regist(UsersBean bean) {
		logger.info(bean.getUserID());
		logger.info(bean.getUserName());
		
		Map<String,String> map = new HashMap<>();
		
		// check is this ID exist
		if(usersDAO.selectById(bean.getUserID()) == null) {
			
			// check is this Name exist
			if(usersDAO.selectByName(bean.getUserName()) == null) {
				
				// insert this data
				UsersBean result = usersDAO.insert(bean);
				System.out.println(result);
				
				if(result != null) {
					map.put("userId",bean.getUserID());
					map.put("userName",bean.getUserName());
				} else {
					map.put("errorMsg", "internal error , please try again");
					
					System.out.println("Rollback");
				}
				
				
				
			} else {
				// run change name's method
				String newUserName = getNewUserName(bean.getUserName());
				bean.setUserName(newUserName);
				
				// insert this data
				UsersBean result = usersDAO.insert(bean);
				
				if(result != null) {
					map.put("userId",bean.getUserID());
					map.put("userName",bean.getUserName());
				} else {
					map.put("errorMsg", "internal error , please try again");
					
				}
				
			}
			
		} else {
			map.put("errorMsg", "ID already registed");
			logger.warn("ID already registed");
		}
		
		return map;
	}
	
	
	
	private String getNewUserName(String userName) {
		String newUserName = userName;
		
		
		List<String> list = usersDAO.selectLikeName(userName);

		// substring to get following numbers
		// filter for not a number
		List<Integer> filteredList = new ArrayList();
		for(String resultUserName : list) {
			String processedUserName = resultUserName.substring(userName.length());
			
			if(processedUserName.matches("^[0-9]+$")) {
				filteredList.add(Integer.valueOf(processedUserName));
			}
		}
		
		
		// sorting for next operation
		Integer[] intArr = filteredList.toArray(new Integer[filteredList.size()]);
		Arrays.sort(intArr);
		
		if(filteredList.size() != 0) {
			
			// check first
			if(intArr[0] != 1) {
				newUserName = newUserName + "1";
				
			} else {
				
				// get the unused number that is between the numbers
				int arrLen = intArr.length;
				for(int i=0 ; i<(arrLen - 1) ; i++) {
					
					// if near number's difference greater than one
					// than insert new number between them
					if((intArr[i+1] - intArr[i]) > 1) {
						newUserName = newUserName + String.valueOf(intArr[i] + 1);
						break;
						
					} else if(i == (arrLen - 2)) { // last point case
						newUserName = newUserName + String.valueOf(intArr[i+1] + 1);
					}
				}
				
			}
			
			
		} else {
			newUserName = newUserName + "1";
		}
		
		
		return newUserName;
	}
}
