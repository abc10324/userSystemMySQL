package com.sam.usersystem.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sam.usersystem.model.UsersBean;
import com.sam.usersystem.model.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping(path="/api/user",produces="application/json")
	@ResponseBody
	public Map<String,String> regist(@RequestBody String body){
		Map<String,String> map = new HashMap<>();
		
		JSONObject jsonObj = new JSONObject(body);
		
		// data check
		if(jsonObj.getString("userId") == null || jsonObj.getString("userId").matches("^.*[ ]+.*$")) {
			map.put("errorMsg", "invalid ID input");
			
			return map;
		}
		
		if(jsonObj.getString("userName") == null || jsonObj.getString("userName").matches("^.*[ ]+.*$")) {
			map.put("errorMsg", "invalid Name input");
			
			return map;
		}
		
		if(jsonObj.getString("pwd") == null || jsonObj.getString("pwd").matches("^.*[ ]+.*$")) {
			map.put("errorMsg", "invalid password input");
			
			return map;
		}
		
		UsersBean bean = new UsersBean();
		
		bean.setUserID(jsonObj.getString("userId"));
		bean.setUserName(jsonObj.getString("userName"));
		bean.setPwd(jsonObj.getString("pwd"));
		
		return userService.regist(bean);
	}
	
	
	@RequestMapping(path="/NotFound",produces="application/json")
	@ResponseBody
	public Map<String,String> pageNotFoundReply(){
		Map<String,String> map = new HashMap<>();
		
		map.put("errorMsg", "wrong url , please try /api/user with POST request");
	
		return map;
	}
	
	@RequestMapping(path="/Error",produces="application/json")
	@ResponseBody
	public Map<String,String> errorReply(){
		Map<String,String> map = new HashMap<>();
		
		map.put("errorMsg", "internal error occured , please try again later");
	
		return map;
	}
	
}
