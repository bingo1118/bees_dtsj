package com.hrsst.smarthome.pojo;

import java.io.Serializable;

public class UserDevice implements Serializable {
	private static final long serialVersionUID = -4517546542852512395L;
	private String userNum;
	private String devMac;
	private String devName;
	private String recordTime;
	private int lightStates;//��/��״̬socket
	private int socketStates;
	private String newTime; 
	private int lightOnOrOutLine;
	private int devType;//�豸���ͣ�1���Ų�/2������ͷ
	private String cameraPwd;
	private int isShare;
	private int defence;
	private int id;
	
	public UserDevice() {
		super();
	}

	public String getUserNum() {
		return userNum;
	}
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	public String getDevMac() {
		return devMac;
	}
	public void setDevMac(String devMac) {
		this.devMac = devMac;
	}
	public String getDevName() {
		return devName;
	}
	public void setDevName(String devName) {
		this.devName = devName;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public int getLightStates() {
		return lightStates;
	}

	public void setLightStates(int lightStates) {
		this.lightStates = lightStates;
	}

	public int getSocketStates() {
		return socketStates;
	}

	public void setSocketStates(int socketStates) {
		this.socketStates = socketStates;
	}

	public String getNewTime() {
		return newTime;
	}

	public void setNewTime(String newTime) {
		this.newTime = newTime;
	}
	public int getLightOnOrOutLine() {
		return lightOnOrOutLine;
	}
	public void setLightOnOrOutLine(int lightOnOrOutLine) {
		this.lightOnOrOutLine = lightOnOrOutLine;
	}

	public int getDevType() {
		return devType;
	}

	public void setDevType(int devType) {
		this.devType = devType;
	}

	public String getCameraPwd() {
		return cameraPwd;
	}

	public void setCameraPwd(String cameraPwd) {
		this.cameraPwd = cameraPwd;
	}

	public int getIsShare() {
		return isShare;
	}

	public void setIsShare(int isShare) {
		this.isShare = isShare;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
