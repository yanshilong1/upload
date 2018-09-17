package cn.cloud.core.entity;

import java.io.Serializable;

public class JsonBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int code;
	private String userId;
	private String userName;
	private String msg;
	private String data;
	
	public JsonBean(){}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
