package com.hrsst.smarthome.pojo;

public class Message implements Comparable {
	// id
	public int id;
	// ������ID
	public String fromId;
	// ������ID
	public String toId;
	// ��Ϣ����
	public String msg;
	// ���ͻ����ʱ��
	public String msgTime;
	// ��ǰ��¼�û�
	public String activeUser;
	// ��Ϣ״̬
	public String msgState;
	// ��Ϣ��ʱ���
	public String msgFlag;

	// ����
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Message msg = (Message) o;
		if (msg.id > this.id) {
			return -1;
		} else if (msg.id < this.id) {
			return 1;
		} else {
			return 0;
		}
	}
}

