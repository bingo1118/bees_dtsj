package com.hrsst.smarthome.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class NearlyTell implements Serializable, Comparable {
	// id
	public int id;
	// ͨ����ID
	public String tellId;
	// ͨ������
	public int tellType;
	// ͨ��״̬
	public int tellState;
	// ͨ��ʱ��
	public String tellTime;
	// ��ǰ��¼�û�
	public String activeUser;
	// ��ʾ�������������ݿ�
	public int count;

	// δ����
	public static final int TELL_STATE_CALL_IN_REJECT = 0;
	// �����ɹ�
	public static final int TELL_STATE_CALL_IN_ACCEPT = 1;
	// δ��ͨ�򱻹Ҷ�
	public static final int TELL_STATE_CALL_OUT_REJECT = 2;
	// ��ͨ�ɹ�
	public static final int TELL_STATE_CALL_OUT_ACCEPT = 3;

	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		NearlyTell nearlyTell = (NearlyTell) o;
		Timestamp user1Time = new Timestamp(Long.parseLong(this.tellTime));
		Timestamp user2Time = new Timestamp(Long.parseLong(nearlyTell.tellTime));
		if (user1Time.after(user2Time)) {
			return -1;
		} else if (!user1Time.after(user2Time)) {
			return 1;
		} else {
			return 0;
		}
	}
}

