package com.hrsst.smarthome.pojo;

import java.io.Serializable;
import java.net.InetAddress;

import com.hrsst.smarthome.global.AppConfig;
import com.hrsst.smarthome.global.Constants;
import com.hrsst.smarthome.util.WifiUtils;
import com.p2p.core.P2PValue;


public class Contact implements Serializable, Comparable {

		// id
		public int id;
		// ��ϵ������
		public String contactName="";
		// ��ϵ��ID
		public String contactId;
		// ��ϵ�˼������ ע�⣺���ǵ�½���룬ֻ�е���ϵ������Ϊ�豸����
		public String contactPassword="0";
		// ��ϵ������
		public int contactType;
		// ����ϵ�˷���������δ����Ϣ
		public int messageCount;
		// ��ǰ��¼���û�
		public String activeUser="";
		// ����״̬ ���������ݿ�
		public int onLineState = Constants.DeviceState.OFFLINE;
		// ����״̬���������ݿ�
		public int defenceState = Constants.DefenceState.DEFENCE_STATE_LOADING;
		// ��¼�Ƿ��ǵ����ȡ����״̬ ���������ݿ�
		public boolean isClickGetDefenceState = false;
		// ��ϵ�˱�� ���������ݿ�
		public int contactFlag;
		// ip��ַ
		public InetAddress ipadressAddress;
	    // �û����������
		public String userPassword="";
	    //�Ƿ��豸�и���
		public int Update=Constants.P2P_SET.DEVICE_UPDATE.UNKNOWN;
	    //��ǰ�汾
		public String cur_version="";
		//�ɸ��µ��İ汾
		public String up_version="";
	    //��ľ��rtsp���
		public int rtspflag=0;
		// ������״̬����
		public String wifiPassword="12345678";
	    //APģʽ�µ�wifi����
		public int mode=P2PValue.DeviceMode.GERNERY_MODE;
		public int apModeState=Constants.APmodeState.UNLINK;
		public boolean isConnectApWifi=false;
		@Override
		public int compareTo(Object arg0) {
			// TODO Auto-generated method stub
			Contact o = (Contact) arg0;
			if (o.onLineState > this.onLineState) {
				return 1;
			} else if (o.onLineState < this.onLineState) {
				return -1;
			} else {
				return 0;
			}
		}
		/**
		 * ��ȡ�豸IP���һ��
		 * @return �շ���""
		 */
		public String getIpMark(){
			if(ipadressAddress!=null){
				String mark=ipadressAddress.getHostAddress();
				return mark.substring(mark.lastIndexOf(".")+1,mark.length());
			}
			return "";
		}
		
		public String getContactId(){
			String ip=getIpMark();
			if(ip.equals("")){
				return contactId;
			}
			return ip;
		}
		
		public void setApModeState(int state){
			this.apModeState=state;
		}
		
		public int getApModeState(){
			if(contactType==P2PValue.DeviceType.IPC&&mode==P2PValue.DeviceMode.AP_MODE){
				 String wifiName=AppConfig.Relese.APTAG+contactId;
				if(WifiUtils.getInstance().isConnectWifi(AppConfig.Relese.APTAG+contactId)){
					apModeState=Constants.APmodeState.LINK;
				}else{
					if(WifiUtils.getInstance().isScanExist(wifiName)){
						//����APģʽ
						apModeState=Constants.APmodeState.UNLINK;
					}else{
						return apModeState;
					}
				}
			}
				return apModeState;
		}
		
		public String getAPName(){
			return AppConfig.Relese.APTAG+contactId;
		}
		/**
		 * ��ȡ��ʾ����
		 * @return 
		 */
		public String getContactName(){
			if(contactName!=null&&contactName.length()>0){
				return contactName;
			}else{
				return contactId;
			}
		}
	
	public int onLineStatues;//0���ߣ�1����
}

