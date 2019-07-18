package com.sam.usersystem.model.dao;

import java.util.List;

import com.sam.usersystem.model.UsersBean;

public interface UsersDAO {
	public UsersBean insert(UsersBean bean);
	public UsersBean selectById(String userID);
	public UsersBean selectByName(String userName);
	public List<String> selectLikeName(String userName);
}
