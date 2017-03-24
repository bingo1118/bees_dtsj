package com.hrsst.smarthome.pojo;

public class User {
	private String userNum;
	private String sessionId;
	private String cameraUser;
	private String cameraEmail;
	private int ifLogin;//1��ʾ��¼�ɹ���2��ʾ��¼ʧ�ܣ�3��ʾ���շ����������ݳ��Ȳ���
	public User(String userNum, String sessionId, String cameraUser,
			String cameraEmail,int ifLogin) {
		super();
		this.userNum = userNum;
		this.sessionId = sessionId;
		this.cameraUser = cameraUser;
		this.cameraEmail = cameraEmail;
		this.ifLogin = ifLogin;
	}
	public User() {
		super();
	}
	public String getUserNum() {
		return userNum;
	}
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getCameraUser() {
		return cameraUser;
	}
	public void setCameraUser(String cameraUser) {
		this.cameraUser = cameraUser;
	}
	public String getCameraEmail() {
		return cameraEmail;
	}
	public void setCameraEmail(String cameraEmail) {
		this.cameraEmail = cameraEmail;
	}
	public int getIfLogin() {
		return ifLogin;
	}
	public void setIfLogin(int ifLogin) {
		this.ifLogin = ifLogin;
	}
	
	
	
}
