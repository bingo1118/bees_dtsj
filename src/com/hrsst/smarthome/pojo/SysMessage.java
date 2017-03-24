package com.hrsst.smarthome.pojo;

public class SysMessage implements Comparable {
	// id
	public int id;
	// ��Ϣ����(����)
	public String msg;
	// ��Ϣ����(Ӣ��)
	public String msg_en;
	// ����ʱ��
	public String msg_time;
	// ��ǰ��¼�û�
	public String activeUser;
	// ��Ϣ����
	public int msgType;
	// ��Ϣ״̬
	public int msgState;

	// ״̬��δ��
	public static final int MESSAGE_STATE_NO_READ = 0;
	// ״̬�Ѷ�
	public static final int MESSAGE_STATE_READED = 1;
	// ���ͣ�����Ա����
	public static final int MESSAGE_TYPE_ADMIN = 2;

	// ����
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Message msg = (Message) o;
		if (msg.id > this.id) {
			return 1;
		} else if (msg.id < this.id) {
			return -1;
		} else {
			return 0;
		}
	}
}

