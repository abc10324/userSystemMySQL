package com.sam.usersystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Users")
public class UsersBean {
	private Integer no;
	private String	userID;
	private Byte[]	pwd;
	private String	userName;
	
	// test master branch No.2
	@Override
	public String toString() {
		return "UsersBean [no=" + no + ", userID=" + userID + ", userName=" + userName + "]";
	}
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	
	@Column(name="uID",unique=true)
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public Byte[] getPwd() {
		return pwd;
	}
	public void setPwd(Byte[] pwd) {
		this.pwd = pwd;
	}
	
	public void setPwd(String pwd) {
		byte[] bytePwd = pwd.getBytes();
		int length = bytePwd.length;
		
		Byte[] objBytePwd = new Byte[length];
		
		for(int i=0 ; i<length ; i++ ) {
			objBytePwd[i] = bytePwd[i];
		}
		
		this.pwd = objBytePwd;
	}
	
	@Column(name="uName",unique=true)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
