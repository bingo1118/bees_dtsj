package com.hrsst.smarthome.global;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Constants {
	public static final String SERVER_URL = "http://119.29.155.148/SmartHome";
	public static final String SERVER_URL2 = "http://119.29.155.148/smartHome2";
	public static final String TEST_SERVER_URL = "http://192.168.56.1:8080/SmartHome";
	public static final String USER_IFEXIT_URL = SERVER_URL+"/ifExitUserRegister.action";
	public static final String ADDENVIRONMENTDEVICE = SERVER_URL2+"/servlet/AddEnvironmentDevice";//��ӻ���̽����@@
	public static final String HTTPGETDEV = SERVER_URL2+"/servlet/GetDeviceStateAction?userNum=";//��ȡ�豸��Ϣ@@
	public static final String GETHISTORYINFO= SERVER_URL2+"/servlet/GetHistoryInfomation";//��ȡ����̽������ʷ����@@
	
	public static final String UPDATE_USER_PWD_URL = SERVER_URL+"/updateUserPwd.action";
	public static final String USER_LOGIN_URL = SERVER_URL+"/loginUser.action";
	public static final String USER_REGISTER_URL = SERVER_URL+"/saveUserRegister.action";
	public static final String BIND_ALIAS_URL = "http://119.29.155.148/GeTuiPush/getCid.action";
	public static final String ERROR_URL = "http://119.29.155.148/UploadError/UploadServlet";
	
	//��ʱ�ӿ�
	public static final String GET_TIMER_LIST_URL = SERVER_URL+"/findAllDwTimer.action";
	public static final String DELETE_TIMER_URL = SERVER_URL+"/deleteDwTimer.action";
	//�豸�б�ӿ�
	public static final String GET_DEVICE_LIST_URL = SERVER_URL+"/findAllUserDevice.action";
	public static final String DELETE_DEVICE_URL = SERVER_URL+"/deleteDevice.action";
	public static final String SAVE_DEVICE_URL = SERVER_URL+"/saveUserDevice.action";
	public static final String UPDATE_DEVICE_NAME_URL = SERVER_URL+"/updateDeviceName.action";
	//��ȡ��ɾ��433�豸�б�ӿ�
	public static final String GET_433_DEVICE_LIST_URL = SERVER_URL+"/findAllAlarmDevList.action";
	public static final String DELETE_433_DEVICE_LIST_URL = SERVER_URL+"/deleteAlarmDev.action";
	//��ȡ����ɾ��������Ϣ�б�findOrDelMessage
	public static final String FIND_OR_DELETE_MESSAGES_URL = SERVER_URL+"/findOrDelMessage.action";
	//��ȡϵͳ��Ϣ�б�
	public static final String GET_SYSTEM_MSG_URL = SERVER_URL+"/findShareMessagesList.action";
	//���½ӿ�
	public static final String UPDATE_URL="http://119.29.155.148/download/update_bees.xml";
	
	public static final String[] WEEKEN_STRING = {"������","����һ","���ڶ�","������","������","������","������"};
	public static final String EVERY_DAY = "������ ����һ ���ڶ� ������ ������ ������ ������ ";
	public static final String WORKING_DAY = "����һ ���ڶ� ������ ������ ������ ";
	public static final String WEEKEN_DAY = "������ ������ ";
	
	public static class RegisterType{
		public static final int PHONE=0;
		public static final int EMALL=1;
	}
	
	public static class WeekType{
		public static final String EVERY_DAY_TYPE="ÿ��";
		public static final String WORKING_DAY_TYPE="������";
		public static final String ONCE_TYPE="ִ��һ��";
		public static final String TODAY="����";
		public static final String TOMORROW="����";
		public static final String WEEKEN_DAY_TYPE="��ĩ";
	}
	
	public static class UserInfo{
		public static final String USER_ID="user_id";
		public static final String USER_NUMBER="user_number";
		public static final String PWD="pwd";
		public static final String SESSION_ID="session_id";
		public static final String SETTAG = "set_tag";
	}
	
	public static class WatchAction{
		public static final String IF_WATCH="if_watching";
		public static final String CAMERA_ID="camera_id";
	}
	
	public static class Action{
		public static final String IF_USER_LOGIN_YES="IF_USER_LOGIN_YES";
		public static final String IF_USER_LOGIN_NO="IF_USER_LOGIN_NO";
		public static final String MAIN_ACTION = "MAIN_ACTION";
		public static final String KILL_MAIN_ACTION = "KILL_MAIN_ACTION";
		public static final String CLOSE_SLIDE_MENU = "CLOSE_SLIDE_MENU";
		public static final String OPEN_SLIDE_MENU = "OPEN_SLIDE_MENU";
		public static final String DELETE_OR_FIND_SYSTEM_MSG = "DELETE_OR_FIND_SYSTEM_MSG";
		public static final String AGREE_SYSTEM_MSG = "AGREE_SYSTEM_MSG";
		
		/*
		 * globel
		 */
		public final static String ACTION_NETWORK_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
		public static final String CLOSE_INPUT_DIALOG = PACKAGE_NAME+"CLOSE_INPUT_DIALOG";
		public static final String NET_WORK_TYPE_CHANGE = PACKAGE_NAME+"NET_WORK_TYPE_CHANGE";

		/*
		 * mainactivity
		 */
		public final static String ACTION_SWITCH_USER = PACKAGE_NAME+"ACTION_SWITCH_USER";
		public final static String ACTION_EXIT = PACKAGE_NAME+"ACTION_EXIT";
		public static final String RECEIVE_MSG = PACKAGE_NAME+"RECEIVE_MSG";
		public final static String ACTION_UPDATE = PACKAGE_NAME+"ACTION_UPDATE";
		public static final String RECEIVE_SYS_MSG = PACKAGE_NAME+"RECEIVE_SYS_MSG";
		public static final String SESSION_ID_ERROR = PACKAGE_NAME+"SESSION_ID_ERROR";
		
		/* contact
		 */
		public final static String REFRESH_CONTANTS = PACKAGE_NAME+"refresh.contants";
		public final static String ACTION_REFRESH_NEARLY_TELL = PACKAGE_NAME+"ACTION_REFRESH_NEARLY_TELL";
		public final static String GET_FRIENDS_STATE = PACKAGE_NAME+"GET_FRIENDS_STATE";
		public final static String ACTION_COUNTRY_CHOOSE = PACKAGE_NAME+"ACTION_COUNTRY_CHOOSE";
		public final static String ADD_CONTACT_SUCCESS = PACKAGE_NAME+"ADD_CONTACT_SUCCESS";
		public final static String LOCAL_DEVICE_SEARCH_END = PACKAGE_NAME+"LOCAL_DEVICE_SEARCH_END";
		public final static String IS_OPEN_THREAD_CONTACTFRAG=PACKAGE_NAME+"IS_OPEN_THREAD_CONTACTFRAG";
		/* MainControl
		 */
		public static final String CHECK_DEVICE_PWD = PACKAGE_NAME+"CHECK_DEVICE_PWD";
		
		/* sysset
		 */
		public static final String ADD_ALARM_MASK_ID_SUCCESS = PACKAGE_NAME+"ADD_ALARM_MASK_ID_SUCCESS";
		public static final String REFRESH_ALARM_RECORD = PACKAGE_NAME+"REFRESH_ALARM_RECORD";
		public static final String CHANGE_ALARM_MESSAGE=PACKAGE_NAME+"CHANGE_ALARM_MESSAGE";
		
		/* shake
		 */
		public static final String UPDATE_DEVICE_FALG = PACKAGE_NAME+"UPDATE_DEVICE_FALG";
		
		/* MainLogin
		 */
		public static final String REPLACE_EMAIL_LOGIN = PACKAGE_NAME+"REPLACE_EMAIL_LOGIN";
		public static final String REPLACE_PHONE_LOGIN = PACKAGE_NAME+"REPLACE_PHONE_LOGIN";
		public static final String EMAIL_LOGIN = PACKAGE_NAME+"EMAIL_LOGIN";
		public static final String PHONE_LOGIN = PACKAGE_NAME+"PHONE_LOGIN";
		
		/* Control
		 */
		public static final String CONTROL_SETTING_PWD_ERROR = PACKAGE_NAME+"CONTROL_SETTING_PWD_ERROR";
		
		public static final String REPLACE_REMOTE_CONTROL = PACKAGE_NAME+"REPLACE_REMOTE_CONTROL";
		public static final String REPLACE_MAIN_CONTROL = PACKAGE_NAME+"REPLACE_MAIN_CONTROL";
		public static final String REPLACE_SETTING_TIME = PACKAGE_NAME+"REPLACE_SETTING_TIME";
		public static final String REPLACE_ALARM_CONTROL = PACKAGE_NAME+"REPLACE_ALARM_CONTROL";
		public static final String REPLACE_VIDEO_CONTROL = PACKAGE_NAME+"REPLACE_VIDEO_CONTROL";
		public static final String REPLACE_RECORD_CONTROL = PACKAGE_NAME+"REPLACE_RECORD_CONTROL";
		public static final String REPLACE_SECURITY_CONTROL = PACKAGE_NAME+"REPLACE_SECURITY_CONTROL";
		public static final String REPLACE_NET_CONTROL = PACKAGE_NAME+"REPLACE_NET_CONTROL";
		public static final String REPLACE_DEFENCE_AREA_CONTROL = PACKAGE_NAME+"REPLACE_DEFENCE_AREA_CONTROL";
		public static final String REPLACE_SD_CARD_CONTROL=PACKAGE_NAME+"REPLACE_SD_CARD_CONTROL";
		public static final String CONTROL_BACK=PACKAGE_NAME+"CONTROL_BACK";
//		����Կ������
		public static final String CURRENT_WIFI_NAME=PACKAGE_NAME+"CURRENT_WIFI_NAME";
		public static final String HEARED=PACKAGE_NAME+"HEARED";
		public static final String DELETE_DEVICE_ALL=PACKAGE_NAME+"DELETE_DEVICE_ALL";
		public static final String INSERT_INFRARED_BACK=PACKAGE_NAME+"INSERT_INFRARED_BACK";
		public static final String ADD_SUCESS=PACKAGE_NAME+"ADD_SUCESS";
		public static final String SETTING_WIFI_SUCCESS=PACKAGE_NAME+"SETTING_WIFI_SUCCESS";
		public static final String ACTIVITY_FINISH=PACKAGE_NAME+"ACTIVITY_FINISH";
		public static final String REPEAT_LOADING_DATA=PACKAGE_NAME+"REPEAT_LOADING_DATA";
// add 
		public static final String DIAPPEAR_ADD=PACKAGE_NAME+"DIAPPEAR_ADD";
	    public static final String RADAR_SET_WIFI_SUCCESS=PACKAGE_NAME+"RADAR_SET_WIFI_SUCCESS";
	    public static final String RADAR_SET_WIFI_FAILED=PACKAGE_NAME+"RADAR_SET_WIFI_FAILED";
	    public static final String MONITOR_NEWDEVICEALARMING=PACKAGE_NAME+"MONITOR_NEWDEVICEALARMING";
	}
	
	public static class WifiInfo{
		public static final String SSID="HRSST_18fe34d6899d";
		public static final String WIFI_PASSWORD="0123456789";
		public static final String WIFI_IP="192.168.4.1";
		public static final int WIFI_PORT=50000;
	}
	
	public static class SeverInfo{
		private static final String SEVER_IP="119.29.155.148";//119.29.155.148
		public static final int PORT=51020;
		public static InetAddress SERVER=null;
		static{
			try {
				SERVER = InetAddress.getByName(SEVER_IP);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static final String CACHE_FOLDER_NAME = "bees";
	public static class Update{
		public static final String SAVE_PATH = Constants.CACHE_FOLDER_NAME+"/apk";
		public static final String FILE_NAME = Constants.CACHE_FOLDER_NAME+".apk";
		public static final String INSTALL_APK = "application/vnd.android.package-archive";
		public static final int CHECK_UPDATE_HANDLE_FALSE = 0x11;
		public static final int CHECK_UPDATE_HANDLE_TRUE = 0x12;
	}
	
	//sst
	//URL
		public static final String URL_HEAD = "http://119.29.155.148/NotificationToPush/";
		public static final String ADD_CAMERA_URL = URL_HEAD+"saveCamera.action";
		public static final String DEL_CAMERA_URL = URL_HEAD+"deleteCamera.action";
		public static final String NEW_USERINFO_URL = URL_HEAD+"newDeviceOnline.action";
		public static final String OLD_USERINFO_URL = URL_HEAD+"oldDeviceOutLine.action";
		public static final String INSERT_CAMERA_URL = URL_HEAD+"findUserCamera.action";
		public static final String UPDATE_CAMERA_INFO_URL = URL_HEAD+"updateCameraInfo.action";
		public static final String UPDATE_CAMERA_PWD_URL = URL_HEAD+"updateCameraPassword.action";
		public static final String UPDATE_DEFENCE_ALARMTYPE_URL = URL_HEAD+"updateDefenceAlarm.action";
		public static final String UPDATE_DEFENCE_NAME_URL = URL_HEAD+"updateDefenceName.action";
		public static final String FIND_ALL_ALARM_TYPE = URL_HEAD+"findAllAlarmType.action";
		public static final String FIND_DEFENCE_ALARMTYPE_URL = URL_HEAD+"findAlarmType.action";
		public static final String DEFENCE_FINDALL_URL = URL_HEAD+"findAll.action";
		public static final String FINDALARMMESSAGE_BY_PAGE = URL_HEAD+"findAlarmByUserIDPage.action";
		public static final String FINDALARMMESSAGE_BY_TIME = URL_HEAD+"findAlarmByUserID.action";
		public static final String GET_ALARM_TYPE_URL = URL_HEAD+"findDeviceAlarmType.action";
		public static final String GET_ALARM_TYPE_LIST_URL = URL_HEAD+"findDeviceAlarmTypeList.action";
		public static final String DOWNLOADIMAGES_URL = "http://119.29.155.148/images/";
		
		public static final String[] STATUS= {"1","2","3","4","5"};
		
		public static final int KEYBOARD_SHOW_FILTER_USER = 0x11;
		public static final String PACKAGE_NAME = "com.hrsst.smarthome.global.";
		public static final String FORGET_PASSWORD_URL = "http://cloudlinks.cn/pw/";
		
		public static class P2P {
			// �豸��֧��
			public static final String RET_DEVICE_NOT_SUPPORT = PACKAGE_NAME
					+ "RET_DEVICE_NOT_SUPPORT";

			// �������
			public static final String ACK_RET_CHECK_PASSWORD = PACKAGE_NAME
					+ "ACK_RET_CHECK_PASSWORD";

			// ��ȡ�豸��������
			public static final String ACK_RET_GET_NPC_SETTINGS = PACKAGE_NAME
					+ "ACK_RET_GET_NPC_SETTINGS";

			// ��ȡ����״̬
			public static final String ACK_RET_GET_DEFENCE_STATES = PACKAGE_NAME
					+ "ACK_RET_GET_DEFENCE_STATES";

			// �豸ʱ�����
			public static final String ACK_RET_SET_TIME = PACKAGE_NAME
					+ "ACK_RET_SET_TIME";
			public static final String ACK_RET_GET_TIME = PACKAGE_NAME
					+ "ACK_RET_GET_TIME";
			public static final String RET_SET_TIME = PACKAGE_NAME + "RET_SET_TIME";
			public static final String RET_GET_TIME = PACKAGE_NAME + "RET_GET_TIME";

			// ��Ƶ��ʽ���
			public static final String ACK_RET_SET_VIDEO_FORMAT = PACKAGE_NAME
					+ "ACK_RET_SET_VIDEO_FORMAT";
			public static final String RET_SET_VIDEO_FORMAT = PACKAGE_NAME
					+ "RET_SET_VIDEO_FORMAT";
			public static final String RET_GET_VIDEO_FORMAT = PACKAGE_NAME
					+ "RET_GET_VIDEO_FORMAT";

			// ������С���
			public static final String ACK_RET_SET_VIDEO_VOLUME = PACKAGE_NAME
					+ "ACK_RET_SET_VIDEO_VOLUME";
			public static final String RET_SET_VIDEO_VOLUME = PACKAGE_NAME
					+ "RET_SET_VIDEO_VOLUME";
			public static final String RET_GET_VIDEO_VOLUME = PACKAGE_NAME
					+ "RET_GET_VIDEO_VOLUME";

			// �޸��豸�������
			public static final String ACK_RET_SET_DEVICE_PASSWORD = PACKAGE_NAME
					+ "ACK_RET_SET_DEVICE_PASSWORD";
			public static final String RET_SET_DEVICE_PASSWORD = PACKAGE_NAME
					+ "RET_SET_DEVICE_PASSWORD";

			// ���������������
			public static final String ACK_RET_SET_NET_TYPE = PACKAGE_NAME
					+ "ACK_RET_SET_NET_TYPE";
			public static final String RET_SET_NET_TYPE = PACKAGE_NAME
					+ "RET_SET_NET_TYPE";
			public static final String RET_GET_NET_TYPE = PACKAGE_NAME
					+ "RET_GET_NET_TYPE";

			// ����WIFI���
			public static final String ACK_RET_SET_WIFI = PACKAGE_NAME
					+ "ACK_RET_SET_WIFI";
			public static final String ACK_RET_GET_WIFI = PACKAGE_NAME
					+ "ACK_GET_SET_WIFI";
			public static final String RET_SET_WIFI = PACKAGE_NAME + "RET_SET_WIFI";
			public static final String RET_GET_WIFI = PACKAGE_NAME + "RET_GET_WIFI";

			// ���ð󶨱���ID
			public static final String ACK_RET_SET_BIND_ALARM_ID = PACKAGE_NAME
					+ "ACK_RET_SET_BIND_ALARM_ID";
			public static final String ACK_RET_GET_BIND_ALARM_ID = PACKAGE_NAME
					+ "ACK_RET_GET_BIND_ALARM_ID";
			public static final String RET_SET_BIND_ALARM_ID = PACKAGE_NAME
					+ "RET_SET_BIND_ALARM_ID";
			public static final String RET_GET_BIND_ALARM_ID = PACKAGE_NAME
					+ "RET_GET_BIND_ALARM_ID";
			public static final String DELETE_BINDALARM_ID = PACKAGE_NAME
					+ "DELETE_BINDALARM_ID";

			// ���ñ�������
			public static final String ACK_RET_SET_ALARM_EMAIL = PACKAGE_NAME
					+ "ACK_RET_SET_ALARM_EMAIL";
			public static final String ACK_RET_GET_ALARM_EMAIL = PACKAGE_NAME
					+ "ACK_RET_GET_ALARM_EMAIL";
			public static final String RET_SET_ALARM_EMAIL = PACKAGE_NAME
					+ "RET_SET_ALARM_EMAIL";
			public static final String RET_GET_ALARM_EMAIL = PACKAGE_NAME
					+ "RET_GET_ALARM_EMAIL";
			public static final String RET_GET_ALARM_EMAIL_WITHSMTP = PACKAGE_NAME
					+ "RET_GET_ALARM_EMAIL_WITHSMTP";
			public static final String ACK_RET_GET_ALARM_EMAIL_WITHSMTP = PACKAGE_NAME
					+ "ACK_RET_GET_ALARM_EMAIL_WITHSMTP";

			// �����ƶ����
			public static final String ACK_RET_SET_MOTION = PACKAGE_NAME
					+ "ACK_RET_SET_MOTION";
			public static final String RET_SET_MOTION = PACKAGE_NAME
					+ "RET_SET_MOTION";
			public static final String RET_GET_MOTION = PACKAGE_NAME
					+ "RET_GET_MOTION";

			// ���÷�����
			public static final String ACK_RET_SET_BUZZER = PACKAGE_NAME
					+ "RET_SET_BUZZER";
			public static final String RET_SET_BUZZER = PACKAGE_NAME
					+ "RET_SET_BUZZER";
			public static final String RET_GET_BUZZER = PACKAGE_NAME
					+ "RET_GET_BUZZER";

			// ����¼��ģʽ
			public static final String ACK_RET_SET_RECORD_TYPE = PACKAGE_NAME
					+ "ACK_RET_SET_RECORD_TYPE";
			public static final String RET_SET_RECORD_TYPE = PACKAGE_NAME
					+ "RET_SET_RECORD_TYPE";
			public static final String RET_GET_RECORD_TYPE = PACKAGE_NAME
					+ "RET_GET_RECORD_TYPE";

			// ����¼��ʱ��
			public static final String ACK_RET_SET_RECORD_TIME = PACKAGE_NAME
					+ "ACK_RET_SET_RECORD_TIME";
			public static final String RET_SET_RECORD_TIME = PACKAGE_NAME
					+ "RET_SET_RECORD_TIME";
			public static final String RET_GET_RECORD_TIME = PACKAGE_NAME
					+ "RET_GET_RECORD_TIME";

			// ����¼��ƻ�ʱ��
			public static final String ACK_RET_SET_RECORD_PLAN_TIME = PACKAGE_NAME
					+ "ACK_RET_SET_RECORD_PLAN_TIME";
			public static final String RET_SET_RECORD_PLAN_TIME = PACKAGE_NAME
					+ "RET_SET_RECORD_PLAN_TIME";
			public static final String RET_GET_RECORD_PLAN_TIME = PACKAGE_NAME
					+ "RET_GET_RECORD_PLAN_TIME";

			// ��������
			public static final String ACK_RET_SET_DEFENCE_AREA = PACKAGE_NAME
					+ "ACK_RET_SET_DEFENCE_AREA";
			public static final String ACK_RET_GET_DEFENCE_AREA = PACKAGE_NAME
					+ "ACK_RET_GET_DEFENCE_AREA";
			public static final String ACK_RET_CLEAR_DEFENCE_AREA = PACKAGE_NAME
					+ "ACK_RET_CLEAR_DEFENCE_AREA";
			public static final String RET_CLEAR_DEFENCE_AREA = PACKAGE_NAME
					+ "RET_CLEAR_DEFENCE_AREA";
			public static final String RET_SET_DEFENCE_AREA = PACKAGE_NAME
					+ "RET_SET_DEFENCE_AREA";
			public static final String RET_GET_DEFENCE_AREA = PACKAGE_NAME
					+ "RET_GET_DEFENCE_AREA";
			//Ԥ��λ���ú󷵻�
			public static final String MESG_TYPE_RET_ALARM_TYPE_MOTOR_PRESET_POS = PACKAGE_NAME
					+ "MESG_TYPE_RET_ALARM_TYPE_MOTOR_PRESET_POS";

			// Զ������
			public static final String ACK_RET_SET_REMOTE_DEFENCE = PACKAGE_NAME
					+ "ACK_RET_SET_REMOTE_DEFENCE";
			public static final String RET_SET_REMOTE_DEFENCE = PACKAGE_NAME
					+ "RET_SET_REMOTE_DEFENCE";
			public static final String RET_GET_REMOTE_DEFENCE = PACKAGE_NAME
					+ "RET_GET_REMOTE_DEFENCE";

			public static final String ACK_RET_SET_REMOTE_RECORD = PACKAGE_NAME
					+ "ACK_RET_SET_REMOTE_RECORD";
			public static final String RET_SET_REMOTE_RECORD = PACKAGE_NAME
					+ "RET_SET_REMOTE_RECORD";
			public static final String RET_GET_REMOTE_RECORD = PACKAGE_NAME
					+ "RET_GET_REMOTE_RECORD";

			// ���ó�ʼ����
			public static final String ACK_RET_SET_INIT_PASSWORD = PACKAGE_NAME
					+ "ACK_RET_SET_INIT_PASSWORD";
			public static final String RET_SET_INIT_PASSWORD = PACKAGE_NAME
					+ "RET_SET_INIT_PASSWORD";
			public static final String RET_GET_VISTOR_PASSWORD = PACKAGE_NAME
					+ "RET_GET_VISTOR_PASSWORD";

			// ��ѯ¼���ļ�
			public static final String ACK_RET_GET_PLAYBACK_FILES = PACKAGE_NAME
					+ "ACK_RET_GET_PLAYBACK_FILES";
			public static final String RET_GET_PLAYBACK_FILES = PACKAGE_NAME
					+ "RET_GET_PLAYBACK_FILES";

			// �طŸı������
			public static final String PLAYBACK_CHANGE_SEEK = PACKAGE_NAME
					+ "PLAYBACK_CHANGE_SEEK";

			// �ط�״̬�ı�
			public static final String PLAYBACK_CHANGE_STATE = PACKAGE_NAME
					+ "PLAYBACK_CHANGE_STATE";

			// ����״̬�л�
			public static final String P2P_CHANGE_IMAGE_TRANSFER = PACKAGE_NAME
					+ "P2P_CHANGE_IMAGE_TRANSFER";

			// �豸������
			public static final String ACK_RET_GET_DEVICE_INFO = PACKAGE_NAME
					+ "ACK_RET_GET_DEVICE_INFO";
			public static final String RET_GET_DEVICE_INFO = PACKAGE_NAME
					+ "RET_GET_DEVICE_INFO";

			public static final String ACK_RET_CHECK_DEVICE_UPDATE = PACKAGE_NAME
					+ "ACK_RET_CHECK_DEVICE_UPDATE";
			public static final String RET_CHECK_DEVICE_UPDATE = PACKAGE_NAME
					+ "RET_CHECK_DEVICE_UPDATE";

			public static final String ACK_RET_DO_DEVICE_UPDATE = PACKAGE_NAME
					+ "ACK_RET_DO_DEVICE_UPDATE";
			public static final String RET_DO_DEVICE_UPDATE = PACKAGE_NAME
					+ "RET_DO_DEVICE_UPDATE";

			public static final String ACK_RET_CANCEL_DEVICE_UPDATE = PACKAGE_NAME
					+ "ACK_RET_CANCEL_DEVICE_UPDATE";
			public static final String RET_CANCEL_DEVICE_UPDATE = PACKAGE_NAME
					+ "RET_CANCEL_DEVICE_UPDATE";
			/*
			 * p2p Connect
			 */
			public static final String P2P_REJECT = PACKAGE_NAME + "P2P_REJECT";
			public static final String P2P_ACCEPT = PACKAGE_NAME + "P2P_ACCEPT";
			public static final String P2P_READY = PACKAGE_NAME + "P2P_READY";

			// p2p��Ƶ�ֱ��ʸı�
			public static final String P2P_RESOLUTION_CHANGE = PACKAGE_NAME
					+ "P2P_RESOLUTION_CHANGE";

			// P2P��������ı�
			public static final String P2P_MONITOR_NUMBER_CHANGE = PACKAGE_NAME
					+ "P2P_MONITOR_NUMBER_CHANGE";
			// ����ͼ��ת
			public static final String RET_GET_IMAGE_REVERSE = PACKAGE_NAME
					+ "RET_GET_IMAGE_REVERSE";
			public static final String ACK_VRET_SET_IMAGEREVERSE = PACKAGE_NAME
					+ "ACK_VRET_SET_IMAGEREVERSE";
			public static final String RET_SET_IMGEREVERSE = PACKAGE_NAME
					+ "RET_SET_IMGEREVERSE";
			// ������⿪��
			public static final String ACK_RET_SET_INFRARED_SWITCH = PACKAGE_NAME
					+ "ACK_RET_SET_INFRARED_SWITCH";
			public static final String RET_GET_INFRARED_SWITCH = PACKAGE_NAME
					+ "RET_GET_INFRARED_SWITCH";
			// ���߱������뿪��
			public static final String ACK_RET_SET_WIRED_ALARM_INPUT = PACKAGE_NAME
					+ "ACK_RET_GET_WIRED_ALARM_INPUT";
			public static final String RET_GET_WIRED_ALARM_INPUT = PACKAGE_NAME
					+ "RET_GET_WIRED_ALARM_INPUT";
			// ���߱������뿪��
			public static final String ACK_RET_SET_WIRED_ALARM_OUT = PACKAGE_NAME
					+ "ACK_RET_GET_WIRED_ALARM_OUT";
			public static final String RET_GET_WIRED_ALARM_OUT = PACKAGE_NAME
					+ "RET_GET_WIRED_ALARM_OUT";
			// �Զ�����
			public static final String ACK_RET_SET_AUTOMATIC_UPGRADE = PACKAGE_NAME
					+ "ACK_RET_GET_AUTOMATIC_UPGRADE";
			public static final String RET_GET_AUTOMATIC_UPGRAD = PACKAGE_NAME
					+ "RET_GET_AUTOMATIC_UPGRAD";
			// ���÷ÿ�����
			public static final String ACK_RET_SET_VISITOR_DEVICE_PASSWORD = PACKAGE_NAME
					+ "ACK_RET_SET_VISITOR_DEVICE_PASSWORD";
			public static final String RET_SET_VISITOR_DEVICE_PASSWORD = PACKAGE_NAME
					+ "RET_SET_VISITOR_DEVICE_PASSWORD";
			// ����ʱ��
			public static final String ACK_RET_SET_TIME_ZONE = PACKAGE_NAME
					+ "ACK_RET_SET_TIME_ZONE";
			public static final String RET_GET_TIME_ZONE = PACKAGE_NAME
					+ "RET_GET_TIME_ZONE";
			// SD��
			public static final String RET_GET_SD_CARD_CAPACITY = PACKAGE_NAME
					+ "RET_GET_SD_CARD_CAPACITY";
			public static final String RET_GET_SD_CARD_FORMAT = PACKAGE_NAME
					+ "RET_GET_SD_CARD_FORMAT";
			public static final String ACK_GET_SD_CARD_CAPACITY = PACKAGE_NAME
					+ "ACK_GET_SD_CARD_CAPACITY";
			public static final String ACK_GET_SD_CARD_FORMAT = PACKAGE_NAME
					+ "ACK_GET_SD_CARD_FORMAT";
			public static final String RET_GET_USB_CAPACITY = PACKAGE_NAME
					+ "RET_GET_USB_CAPACITY";
			// Ԥ¼��
			public static final String RET_GET_PRE_RECORD = PACKAGE_NAME
					+ "RET_GET_PRE_RECORD";
			public static final String ACK_RET_SET_PRE_RECORD = PACKAGE_NAME
					+ "ACK_RET_SET_PRE_RECORD";
			public static final String RET_SET_PRE_RECORD = PACKAGE_NAME
					+ "RET_SET_PRE_RECORD";
			// ����������
			public static final String ACK_RET_GET_SENSOR_SWITCH = PACKAGE_NAME
					+ "ACK_RET_GET_SENSOR_SWITCH";
			public static final String ACK_RET_SET_SENSOR_SWITCH = PACKAGE_NAME
					+ "ACK_RET_SET_SENSOR_SWITCH";
			public static final String RET_GET_SENSOR_SWITCH = PACKAGE_NAME
					+ "RET_GET_SENSOR_SWITCH";
			public static final String RET_SET_SENSOR_SWITCH = PACKAGE_NAME
					+ "RET_SET_SENSOR_SWITCH";
			// ����
			public static final String RET_CUSTOM_CMD_DISCONNECT = PACKAGE_NAME
					+ "RET_CUSTOM_CMD_DISCONNECT";
			// GPIO
			public static final String ACK_RET_SET_GPIO = PACKAGE_NAME
					+ "ACK_RET_SET_GPIO";
			public static final String ACK_RET_SET_GPIO1_0 = PACKAGE_NAME
					+ "ACK_RET_SET_GPIO1_0";
			public static final String RET_SET_GPIO = PACKAGE_NAME + "RET_SET_GPIO";
			// ��ȡ��Ƶ�豸�ͺ�
			public static final String RET_GET_AUDIO_DEVICE_TYPE = PACKAGE_NAME
					+ "RET_GET_AUDIO_DEVICE_TYPE";
			// SetLamp����
			public static final String SET_LAMP_STATUS = PACKAGE_NAME
					+ "SET_LAMP_STATUS";
			// SetLamp����
			public static final String ACK_SET_LAMP_STATUS = PACKAGE_NAME
					+ "ACK_SET_LAMP_STATUS";
			// GetLamp����
			public static final String GET_LAMP_STATUS = PACKAGE_NAME
					+ "GET_LAMP_STATUS";
			// ��ȡ����������
			public static final String RET_GET_LANGUEGE = PACKAGE_NAME
					+ "RET_GET_LANGUEGE";
			public static final String RET_SET_LANGUEGE = PACKAGE_NAME
					+ "RET_SET_LANGUEGE";
			//��ȡnvr���豸�б�
			public static final String ACK_GET_NVR_IPC_LIST=PACKAGE_NAME
					+"ACK_GET_NVR_IPC_LIST";
			public static final String RET_GET_NVR_IPC_LIST=PACKAGE_NAME
					+"RET_GET_NVR_IPC_LIST";
			 //�鿴/����Ԥ��λ����
		    public static final String RET_PRESET_MOTORPOS_STATUS=PACKAGE_NAME+"RET_PRESET_MOTORPOS_STATUS";
		    //��ȡ�佹�䱶
		    public static final String RET_GET_FOCUS_ZOOM=PACKAGE_NAME+"RET_GET_FOCUS_ZOOM";
//			  ���۹������
			  		public static final String ACK_FISHEYE=PACKAGE_NAME+"ACK_FISHEYE";
			  		public static final String RET_SET_IPC_WORKMODE=PACKAGE_NAME+"RET_SET_IPC_WORKMODE";
			  		public static final String RET_SET_SENSER_WORKMODE=PACKAGE_NAME+"RET_SET_SENSER_WORKMODE";
			  		public static final String RET_SET_SCHEDULE_WORKMODE=PACKAGE_NAME+"RET_SET_SCHEDULE_WORKMODE";
			  		public static final String RET_DELETE_SCHEDULE=PACKAGE_NAME+"RET_DELETE_SCHEDULE";
			  		public static final String RET_GET_CURRENT_WORKMODE=PACKAGE_NAME+"RET_GET_CURRENT_WORKMODE";
			  		public static final String RET_GET_SENSOR_WORKMODE=PACKAGE_NAME+"RET_GET_SENSOR_WORKMODE";
			  		public static final String RET_GET_SCHEDULE_WORKMODE=PACKAGE_NAME+"RET_GET_SCHEDULE_WORKMODE";
			  		public static final String RET_SET_ALLSENSER_SWITCH=PACKAGE_NAME+"RET_SET_ALLSENSER_SWITCH";
			  		public static final String RET_GET_ALLSENSER_SWITCH=PACKAGE_NAME+"RET_GET_ALLSENSER_SWITCH";
			  		public static final String RET_SET_LOWVOL_TIMEINTERVAL=PACKAGE_NAME+"RET_SET_LOWVOL_TIMEINTERVAL";
			  		public static final String RET_GET_LOWVOL_TIMEINTERVAL=PACKAGE_NAME+"RET_GET_LOWVOL_TIMEINTERVAL";
			  	//�ڶ������
					public static final String RET_DELETE_ONE_CONTROLER=PACKAGE_NAME+"RET_DELETE_ONE_CONTROLER";
					public static final String RET_DELETE_ONE_SENSOR=PACKAGE_NAME+"RET_DELETE_ONE_SENSOR";
					public static final String RET_CHANGE_CONTROLER_NAME=PACKAGE_NAME+"RET_CHANGE_CONTROLER_NAME";
					public static final String RET_CHANGE_SENSOR_NAME=PACKAGE_NAME+"RET_CHANGE_SENSOR_NAME";
					public static final String RET_INTO_LEARN_STATE=PACKAGE_NAME+"RET_INTO_LEARN_STATE";
					public static final String RET_TURN_SENSOR=PACKAGE_NAME+"RET_TURN_SENSOR";
					public static final String RET_SHARE_TO_MEMBER=PACKAGE_NAME+"RET_SHARE_TO_MEMBER";
					public static final String RET_GOT_SHARE=PACKAGE_NAME+"RET_GOT_SHARE";
					public static final String RET_DEV_RECV_MEMBER_FEEDBACK=PACKAGE_NAME+"RET_DEV_RECV_MEMBER_FEEDBACK";
					public static final String RET_ADMIN_DELETE_ONE_MEMBER=PACKAGE_NAME+"RET_ADMIN_DELETE_ONE_MEMBER";
					public static final String RET_DELETE_DEV=PACKAGE_NAME+"RET_DELETE_DEV";
					public static final String RET_GET_MEMBER_LIST=PACKAGE_NAME+"RET_GET_MEMBER_LIST";
					public static final String RET_SET_ONE_SPECIAL_ALARM=PACKAGE_NAME+"RET_SET_ONE_SPECIAL_ALARM";
					public static final String RET_GET_ALL_SPECIAL_ALARM=PACKAGE_NAME+"RET_GET_ALL_SPECIAL_ALARM";
					public static final String RET_GET_LAMPSTATE=PACKAGE_NAME+"RET_GET_LAMPSTATE";
					public static final String RET_KEEP_CLIENT=PACKAGE_NAME+"RET_KEEP_CLIENT";
		  //��ȡ����ͼ��
				    public static final String RET_GET_ALLARMIMAGE=PACKAGE_NAME+"RET_GET_ALLARMIMAGE";
				    public static final String RET_GET_ALLARMIMAGE_PROGRESS=PACKAGE_NAME+"RET_GET_ALLARMIMAGE_PROGRESS";
				    //�л�APģʽ
				    public static final String RET_SET_AP_MODE=PACKAGE_NAME+"RET_SET_AP_MODE";
				    //�Ƿ�֧��APģʽ
				    public static final String RET_AP_MODESURPPORT=PACKAGE_NAME+"RET_AP_MODESURPPORT";
				    //P2Pͼ��ʼ��ʾ
				    public static final String RET_P2PDISPLAY=PACKAGE_NAME+"RET_P2PDISPLAY";
				    
				    public static final String ACK_GET_REMOTE_DEFENCE=PACKAGE_NAME+"ACK_GET_REMOTE_DEFENCE";
		}
		
		public static class P2P_TYPE{
			public static final int P2P_TYPE_CALL = 0;
			public static final int P2P_TYPE_MONITOR = 1;
			public static final int P2P_TYPE_PLAYBACK = 2;
		}
		
		public static class P2P_CALL{
			public static class CALL_RESULT{
				public static final int CALL_SUCCESS = 0;
				public static final int CALL_PHONE_FORMAT_ERROR = 1;
			}
		}
		
		public static class P2P_SET{
			
			public static class ACK_RESULT{
				public static final int ACK_PWD_ERROR = 9999;
				public static final int ACK_NET_ERROR = 9998;
				public static final int ACK_SUCCESS = 9997;
				public static final int ACK_INSUFFICIENT_PERMISSIONS=9996;
			}
			
			public static class DEVICE_TIME_SET{
				public static final int SETTING_SUCCESS = 0;
			}
			
			public static class VIDEO_FORMAT_SET{
				public static final int SETTING_SUCCESS = 0;
				public static final int VIDEO_FORMAT_NTSC = 0;
				public static final int VIDEO_FORMAT_PAL = 1;
			}
			
			public static class VIDEO_VOLUME_SET{
				public static final int SETTING_SUCCESS = 0;
			}
			
			public static class DEVICE_PASSWORD_SET{
				public static final int SETTING_SUCCESS = 0;
			}
			
			public static class NET_TYPE_SET{
				public static final int SETTING_SUCCESS = 0;
				public static final int NET_TYPE_WIRED = 0;
				public static final int NET_TYPE_WIFI = 1;
			}
			
			public static class WIFI_SET{
				public static final int SETTING_SUCCESS = 0;
			}
			
			public static class BIND_ALARM_ID_SET{
				public static final int SETTING_SUCCESS = 0;
			}
			
			public static class ALARM_EMAIL_SET{
				public static final int SETTING_SUCCESS = 0;
			}
			
			public static class MOTION_SET{
				public static final int SETTING_SUCCESS = 0;
				public static final int MOTION_DECT_ON = 1;
				public static final int MOTION_DECT_OFF = 0; 
			}
			
			public static class BUZZER_SET{
				public static final int SETTING_SUCCESS = 0;
				public static final int BUZZER_SWITCH_ON_ONE_MINUTE = 1;
				public static final int BUZZER_SWITCH_ON_TWO_MINUTE = 2;
				public static final int BUZZER_SWITCH_ON_THREE_MINUTE = 3;
				public static final int BUZZER_SWITCH_OFF = 0; 
			}
			
			public static class RECORD_TYPE_SET{
				public static final int SETTING_SUCCESS = 0;
				public static final int RECORD_TYPE_MANUAL = 0;
				public static final int RECORD_TYPE_ALARM = 1;
				public static final int RECORD_TYPE_TIMER = 2;
			}
			
			public static class RECORD_TIME_SET{
				public static final int SETTING_SUCCESS = 0;
				public static final int RECORD_TIME_ONE_MINUTE = 0;
				public static final int RECORD_TIME_TWO_MINUTE = 1;
				public static final int RECORD_TIME_THREE_MINUTE = 2;
			}
			
			public static class RECORD_PLAN_TIME_SET{
				public static final int SETTING_SUCCESS = 0;
				
			}
			
			public static class DEFENCE_AREA_SET{
				public static final int SETTING_SUCCESS = 0;
				public static final int DEFENCE_AREA_TYPE_LEARN = 0;
				public static final int DEFENCE_AREA_TYPE_CLEAR = 1;
				public static final int DEFENCE_AREA_TYPE_CLEAR_GROUP = 2;
				
			}
			
			public static class REMOTE_DEFENCE_SET{
				public static final int SETTING_SUCCESS = 0;
				public static final int ALARM_SWITCH_ON = 1;
				public static final int ALARM_SWITCH_OFF = 0;
			}
			
			public static class REMOTE_RECORD_SET{
				public static final int SETTING_SUCCESS = 0;
				public static final int RECORD_SWITCH_ON = 1;
				public static final int RECORD_SWITCH_OFF = 0; 
			}
			public static class PRE_RECORD_SET{
				public static final int SETTING_SUCCESS = 0;
				public static final int PRE_RECORD_SWITCH_ON = 1;
				public static final int PRE_RECORD_SWITCH_OFF = 0; 
			}
			public static class INIT_PASSWORD_SET{
				public static final int SETTING_SUCCESS = 0;
				public static final int ALREADY_EXIST_PASSWORD = 43;
			}
			
			public static class DEVICE_UPDATE {

				public static final int HAVE_NEW_VERSION = 1;
				public static final int IS_LATEST_VERSION = 54;
				public static final int OTHER_WAS_CHECKING = 58;
				public static final int HAVE_NEW_IN_SD = 72;
				public static final int DO_UPDATE_SUCCESS = 0;
				public static final int UNKNOWN=-1;
			}
			public static class INFRARED_SWITCH{
				public static final int INFRARED_SWITCH_ON=1;
				public static final int INFRARED_SWITCH_OFF=0;
			}
			public static class WIRED_ALARM_INPUT{
				public static final int ALARM_INPUT_ON=1;
				public static final int ALARM_INPUT_OFF=0;
			}
			public static class WIRED_ALARM_OUT{
				public static final int ALARM_OUT_ON=1;
				public static final int ALARM_OUT_OFF=0;
			}
			public static class AUTOMATIC_UPGRADE{
				public static final int AUTOMATIC_UPGRADE_ON=1;
				public static final int AUTOMATIC_UPGRADE_OFF=0;
			}
			public static class DEVICE_VISITOR_PASSWORD_SET{
				public static final int SETTING_SUCCESS = 0;
			}
			public static class SD_FORMAT{
				public static final int SD_CARD_SUCCESS=80;
				public static final int SD_CARD_FAIL=81;
				public static final int SD_NO_EXIST=82;
			}
		}
		
		public static class	DeviceState{
			public static final int ONLINE = 1;
			public static final int OFFLINE = 0;
		}
		
		public static class DefenceState{
			public static final int DEFENCE_STATE_OFF = 0;
			public static final int DEFENCE_STATE_ON = 1;
			public static final int DEFENCE_STATE_LOADING = 2;
			public static final int DEFENCE_STATE_WARNING_PWD = 3;
			public static final int DEFENCE_STATE_WARNING_NET = 4;
			public static final int DEFENCE_NO_PERMISSION=5;
		}
		public static class DeviceFlag {
			public static final int UNSET_PASSWORD = 0;
			public static final int ALREADY_SET_PASSWORD = 1;
			public static final int AP_MODE = 2;
			public static final int UNKNOW = 3;
		}
		
		public static class ReplaceStyle{
			public static final int SLIDE = 0;
			public static final int FADE = 1;
		}
		
		public static class LoginType{
			public static final int PHONE = 0;
			public static final int EMAIL = 1;
		}

		public static class ModifyAccountType{
			public static final int MODIFY_EMAIL = 0;
		}
		
		
		public static class SettingConfig{
			
			public static final int SETTING_CLICK_TIME_DELAY = 0;
			
		}
		
		public static class MessageType{
			public static final int SEND_SUCCESS = 0;
			public static final int SENDING = 1;
			public static final int SEND_FAULT = 2;
			public static final int UNREAD = 3;
			public static final int READED = 4;
		}
		
		public static class ActivityInfo{
			public static final int ACTIVITY_LOGOACTIVITY = 0;
			public static final int ACTIVITY_MAINACTIVITY = 1;
			public static final int ACTIVITY_LOGINACTIVITY = 2;
			public static final int ACTIVITY_IMAGEBROWSER = 3;
			public static final int ACTIVITY_ABOUTACTIVITY = 4;
			public static final int ACTIVITY_ACCOUNTINFOACTIVITY = 5;
			public static final int ACTIVITY_ADDALARMMASKIDACTIVITY = 6;
			public static final int ACTIVITY_ADDCONTACTACTIVITY = 7;
			public static final int ACTIVITY_ADDCONTACTNEXTACTIVITY = 8;
			public static final int ACTIVITY_ALARMRECORDACTIVITY = 9;
			public static final int ACTIVITY_ALARMSETACTIVITY = 10;
			public static final int ACTIVITY_FORWARDACTIVITY = 11;
			public static final int ACTIVITY_MAINCONTROLACTIVITY = 12;
			public static final int ACTIVITY_MESSAGEACTIVITY = 13;
			public static final int ACTIVITY_MODIFYACCOUNTEMAILACTIVITY = 14;
			public static final int ACTIVITY_MODIFYACCOUNTPHONEACTIVITY = 15;
			public static final int ACTIVITY_MODIFYACCOUNTPHONEACTIVITY2 = 16;
			public static final int ACTIVITY_MODIFYALARMIDACTIVITY = 17;
			public static final int ACTIVITY_MODIFYBOUNDEMAILACTIVITY = 18;
			public static final int ACTIVITY_MODIFYCONTACTACTIVITY = 19;
			public static final int ACTIVITY_MODIFNPCPASSWORDACTIVITY = 20;
			public static final int ACTIVITY_REGISTERACTIVITY = 21;
			public static final int ACTIVITY_REGISTERACTIVITY2 = 22;
			public static final int ACTIVITY_SEARCHLISTACTIVITY = 23;
			public static final int ACTIVITY_SETTINGBELLRINGACTIVITY = 24;
			public static final int ACTIVITY_SETTINGSDBELLACTIVITY = 25;
			public static final int ACTIVITY_SETTINGSYSTEMACTIVITY = 26;
			public static final int ACTIVITY_SYSMSGACTIVITY = 27;
			public static final int ACTIVITY_SYSNOTIFYACTIVITY = 28;
			public static final int ACTIVITY_TELLDETAILACTIVITY = 29;
			public static final int ACTIVITY_UNBINDPHONEACTIVITY = 30;
			public static final int ACTIVITY_VERIFYPHONEACTIVITY = 31;
			public static final int ACTIVITY_PLAYBACKLISTACTIVITY = 32;
			public static final int ACTIVITY_PLAYBACKACTIVITY = 33;
			public static final int ACTIVITY_VIDEOACTIVITY = 34;
			public static final int ACTIVITY_MONITORACTIVITY = 35;
			public static final int ACTIVITY_CALLACTIVITY = 36;
			public static final int ACTIVITY_MODIFYLOGINPASSWORDACTIVITY = 37;
			public static final int ACTIVITY_CUTIMAGEACTIVITY = 38;
			public static final int ACTIVITY_FORWARDDOWNACTIVITY = 39;
			public static final int ACTIVITY_DEVICE_UPDATE = 40;
			public static final int ACTIVITY_SHAKE = 41;
			public static final int ACTIVITY_INFRARED_REMOTE = 42;
			public static final int ACTIVITY_INFRARED_SET_WIFI = 43;
			public static final int ACTIVITY_LOCAL_DEVICE_LIST = 44;
			public static final int ACTIVITY_ALTOGETHERREGISTERACTIVITY = 45;
			public static final int ACTIVITY_QRCODE = 46;
			public static final int ACTIVITY_MODIFY_NPC_VISITOR_PASSWORD_ACTIVITY=47;
			public static final int ACTIVITY_THREEADDCONTACTACTIVITY=48;
			public static final int SMARTKEYSETWIFIACTIVITY=49;
			public static final int WIFILISTACTIVITY=50;
			public static final int ACTIVITY_QRCODEACTIVITY=51;
			public static final int ACTIVITY_CREATEQRCODEACTIVITY=52;
			public static final int ACTIVITY_ALARMPUSHACCOUNTACTIVITY=53;
			public static final int ACTIVITY_IMAGESEE=54;
			public static final int ACTIVITY_RADARADDFIRSTACTIVITY=55;
			public static final int ACTIVITY_RARDARADDACTIVITY=56;
			public static final int ACTIVITY_WAITDEVICENETWORKACTIVITY=57;
			public static final int ACTIVITY_ADDWAITACTIVITY=58;
			public static final int ACTIVITY_APMONITORACTIVITY=64;
			public static final int ACTIVITY_ALRAM_PICTRUE=65;
		}
		
		public static class ActivityStatus{
			public static final int STATUS_START = 0;
			public static final int STATUS_STOP = 1;
		}

		public static class Image{
			public static final String USER_HEADER_PATH = "/sdcard/"+CACHE_FOLDER_NAME+"/";
			public static final String USER_HEADER_TEMP_FILE_NAME = "temp";
			public static final float USER_HEADER_ROUND_SCALE = 1f/32f;
			public static final int USER_HEADER_MIN_SIZE = 32;
			public static final String USER_HEADER_FILE_NAME = "header";
			public static final String USER_GRAY_HEADER_FILE_NAME = "header_gray";
		}

		public static final int USER_HEADER_WIDTH_HEIGHT = 500;
		
		public static class ConnectType{
			public final static int P2PCONNECT=0;
			public final static int RTSPCONNECT=1;
		}
		public static class APmodeState{
			public static final int LINK = 0;
			public static final int UNLINK = 1;
			public static final int NO_SEARCH=2;
		}
}
