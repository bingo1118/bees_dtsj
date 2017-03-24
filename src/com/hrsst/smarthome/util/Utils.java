package com.hrsst.smarthome.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.provider.MediaStore;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.hrsst.smarthome.dtsj.R;
import com.hrsst.smarthome.global.AppConfig;
import com.hrsst.smarthome.global.Constants;
import com.hrsst.smarthome.global.MyApp;
import com.hrsst.smarthome.global.NpcCommon;
import com.hrsst.smarthome.pojo.SysMessage;
import com.hrsst.smarthome.widget.NormalDialog;
import com.p2p.core.P2PHandler;
import com.p2p.core.P2PValue;
import com.p2p.core.update.UpdateManager;


public class Utils {
	public static String[] getMsgInfo(SysMessage msg, Context context) {

		if (msg.msgType == SysMessage.MESSAGE_TYPE_ADMIN) {

			String title = context.getResources().getString(
					R.string.system_administrator);
			String content = msg.msg;
			return new String[] { title, content };
		} else {
			return null;
		}

	}

	public static boolean hasDigit(String content) {
		boolean flag = false;
		Pattern p = Pattern.compile(".*\\d+.*");
		Matcher m = p.matcher(content);
		if (m.matches())
			flag = true;
		return flag;
	}

	// yyyy-MM-dd HH:mm:ss ->>>>>>> yyyy-MM-dd HH:mm
	public static String ConvertTimeByString(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(time);
			sdf.applyPattern("yyyy-MM-dd HH:mm");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sdf.format(date);
	}

	public static String ConvertTimeByLong(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(time);
		return sdf.format(date);
	}

	public static String getDefenceAreaByGroup(Context context, int group) {
		switch (group) {
		case 0:
			return context.getResources().getString(R.string.remote);
		case 1:
			return context.getResources().getString(R.string.hall);
		case 2:
			return context.getResources().getString(R.string.window);
		case 3:
			return context.getResources().getString(R.string.balcony);
		case 4:
			return context.getResources().getString(R.string.bedroom);
		case 5:
			return context.getResources().getString(R.string.kitchen);
		case 6:
			return context.getResources().getString(R.string.courtyard);
		case 7:
			return context.getResources().getString(R.string.door_lock);
		case 8:
			return context.getResources().getString(R.string.other);
		default:
			return "";
		}
	}

	public static Bitmap montageBitmap(Bitmap frame, Bitmap src, int x, int y) {
		int w = src.getWidth();
		int h = src.getHeight();
		Bitmap sizeFrame = Bitmap.createScaledBitmap(frame, w, h, true);

		Bitmap newBM = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(newBM);
		canvas.drawBitmap(src, 0, 0, null);
		canvas.drawBitmap(sizeFrame, 0, 0, null);
		return newBM;
	}

	public static boolean isZh(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		if (language.endsWith("zh"))
			return true;
		else
			return false;
	}

	public static void upDate(final Context context) {
		new Thread() {
			@Override
			public void run() {

				boolean isUpdate;
				boolean isOk = false;
				try {
					Timestamp time = new Timestamp(System.currentTimeMillis());
					String recent_checkTime = SharedPreferencesManager
							.getInstance()
							.getData(
									context,
									SharedPreferencesManager.SP_FILE_GWELL,
									SharedPreferencesManager.KEY_UPDATE_CHECKTIME);
					if (recent_checkTime.equals("")) {
						isOk = true;
					} else {
						Timestamp recentTime = Timestamp
								.valueOf(recent_checkTime);
						long now = time.getTime();
						long last = recentTime.getTime();
						if ((now - last) > (1000 * 60 * 60 * 24)) {
							isOk = true;
						}
					}
					if (!isOk) {
						return;
					}
					isUpdate = UpdateManager.getInstance().checkUpdate(NpcCommon.mThreeNum);
					if (isUpdate && isOk) {
						SharedPreferencesManager.getInstance().putData(context,
								SharedPreferencesManager.SP_FILE_GWELL,
								SharedPreferencesManager.KEY_UPDATE_CHECKTIME,
								time.toString());
						Intent i = new Intent(Constants.Action.ACTION_UPDATE);
						String data = "";
						if (Utils.isZh(MyApp.app)) {
							data = UpdateManager.getInstance()
									.getUpdateDescription();
						} else {
							data = UpdateManager.getInstance()
									.getUpdateDescription_en();
						}
						i.putExtra("updateDescription", data);
						context.sendBroadcast(i);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static void showPromptDialog(Context context, int title, int content) {
		NormalDialog dialog = new NormalDialog(context, context.getResources()
				.getString(title), context.getResources().getString(content),
				"", "");
		dialog.setStyle(NormalDialog.DIALOG_STYLE_PROMPT);
		dialog.showDialog();
	}

	public static String intToIp(int ip) {
		return (ip & 0xFF) + "." + ((ip >> 8) & 0xff) + "."
				+ ((ip >> 16) & 0xff) + "." + ((ip >> 24) & 0xff);
	}

	public static HashMap getHash(String string) {
		try {
			HashMap map = new HashMap<String, String>();
			String[] item = string.split(",");
			for (int i = 0, len = item.length; i < len; i++) {
				String[] info = item[i].split(":");
				map.put("" + info[0], info[1]);
			}
			return map;
		} catch (Exception e) {
			return null;
		}

	}

	public static String getFormatTellDate(Context context, String time) {

		String year = context.getString(R.string.year_format);
		String month = context.getString(R.string.month_format);
		String day = context.getString(R.string.day_format);

		SimpleDateFormat sd = new SimpleDateFormat("yyyy" + year + "MM" + month
				+ "dd" + day + " HH:mm");
		Date dt = null;
		try {
			dt = new Date(Long.parseLong(time));
		} catch (Exception e) {
		}

		String s = "";
		if (dt != null) {
			s = sd.format(dt);
		}

		return s;
	}

	public static void sleepThread(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String[] getDeleteAlarmIdArray(String[] data, int position) {
		if (data.length == 1) {
			return new String[] { "0" };
		}

		String[] array = new String[data.length - 1];
		int count = 0;
		for (int i = 0; i < data.length; i++) {
			if (position != i) {
				array[count] = data[i];
				count++;
			}
		}

		return array;
	}

	public static String convertDeviceTime(int iYear, int iMonth, int iDay,
			int iHour, int iMinute) {
		int year = (2000 + iYear);
		int month = iMonth;
		int day = iDay;
		int hour = iHour;
		int minute = iMinute;

		StringBuilder sb = new StringBuilder();
		sb.append(year + "-");

		if (month < 10) {
			sb.append("0" + month + "-");
		} else {
			sb.append(month + "-");
		}

		if (day < 10) {
			sb.append("0" + day + " ");
		} else {
			sb.append(day + " ");
		}

		if (hour < 10) {
			sb.append("0" + hour + ":");
		} else {
			sb.append(hour + ":");
		}

		if (minute < 10) {
			sb.append("0" + minute);
		} else {
			sb.append("" + minute);
		}
		return sb.toString();
	}

	public static String convertPlanTime(int hour_from, int minute_from,
			int hour_to, int minute_to) {

		StringBuilder sb = new StringBuilder();

		if (hour_from < 10) {
			sb.append("0" + hour_from + ":");
		} else {
			sb.append(hour_from + ":");
		}

		if (minute_from < 10) {
			sb.append("0" + minute_from + "-");
		} else {
			sb.append(minute_from + "-");
		}

		if (hour_to < 10) {
			sb.append("0" + hour_to + ":");
		} else {
			sb.append(hour_to + ":");
		}

		if (minute_to < 10) {
			sb.append("0" + minute_to);
		} else {
			sb.append("" + minute_to);
		}

		return sb.toString();
	}

	public static boolean isNumeric(String str) {
		if (null == str || "".equals(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	public static void deleteFile(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}
		if (file.isDirectory()) {
			File[] childFile = file.listFiles();
			if (childFile == null || childFile.length == 0) {
				file.delete();
				return;
			}
			for (File f : childFile) {
				deleteFile(f);
			}
			file.delete();

		}
	}

	public static int dip2px(Context context, int dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int bytesToInt(byte[] src, int offset) {
		int value;
		value = (int) ((src[offset] & 0xFF) | ((src[offset + 1] & 0xFF) << 8)
				| ((src[offset + 2] & 0xFF) << 16) | ((src[offset + 3] & 0xFF) << 24));
		return value;
	}

	public static int getColorByResouse(int resouse) {
		return MyApp.app.getResources().getColor(resouse);
	}


	public static String getStringByResouceID(int R) {
		return MyApp.app.getString(R);
	}

	public static String getAlarmType(int type, boolean isSupport, int group,
			int item) {
		switch (type) {
		case P2PValue.AlarmType.EXTERNAL_ALARM:
			StringBuffer buffer = new StringBuffer();
			if (isSupport) {
				buffer.append(getStringByResouceID(R.string.allarm_type1))
						.append("   ");
				buffer.append("\n");
				buffer.append(getStringByResouceID(R.string.area)).append("��")
						.append(getDefenceAreaByGroup(MyApp.app, group))
						.append("  ")
						.append(getStringByResouceID(R.string.channel))
						.append("��").append(item + 1);
			}
			return buffer.toString();
		case P2PValue.AlarmType.MOTION_DECT_ALARM:

			return getStringByResouceID(R.string.allarm_type2);
		case P2PValue.AlarmType.EMERGENCY_ALARM:

			return getStringByResouceID(R.string.allarm_type3);

		case P2PValue.AlarmType.LOW_VOL_ALARM:
			StringBuffer buffer2 = new StringBuffer();
			if (isSupport) {
				buffer2.append(getStringByResouceID(R.string.low_voltage_alarm))
						.append("   ");
				buffer2.append("\n");
				buffer2.append(getStringByResouceID(R.string.area)).append("��")
						.append(getDefenceAreaByGroup(MyApp.app, group))
						.append("  ")
						.append(getStringByResouceID(R.string.channel))
						.append("��").append(item + 1);
			}
			return buffer2.toString();
		case P2PValue.AlarmType.PIR_ALARM:

			return getStringByResouceID(R.string.allarm_type4);
		case P2PValue.AlarmType.EXT_LINE_ALARM:

			return getStringByResouceID(R.string.allarm_type5);
		case P2PValue.AlarmType.DEFENCE:

			return getStringByResouceID(R.string.defence);
		case P2PValue.AlarmType.NO_DEFENCE:

			return getStringByResouceID(R.string.no_defence);
		case P2PValue.AlarmType.BATTERY_LOW_ALARM:

			return getStringByResouceID(R.string.battery_low_alarm);
		case P2PValue.AlarmType.ALARM_TYPE_DOORBELL_PUSH:

			return getStringByResouceID(R.string.alarm_type);
		case P2PValue.AlarmType.RECORD_FAILED_ALARM:

			return getStringByResouceID(R.string.record_failed);
		case P2PValue.AlarmType.ALARM_TYPE_DOOR_MAGNET:
			
			return getStringByResouceID(R.string.door_alarm);
		default:

			return getStringByResouceID(R.string.not_know);
		}
	}

	public static boolean isWeakPassword(String passWord) {
		if (getPassWordStatus(passWord) < 2) {
			return true;
		}
		return false;
	}

	/**
	 * ��ȡ����ǿ�����
	 * 
	 * @param password
	 * @return
	 */
	private static String WEAK = "^[1-9]\\d*$|^[A-Z]+$|^[a-z]+$|^(.)\\1+$";
	private static String MID = "^(?![A-Z]+$)(?![a-z]+$)(?!\\d+$)(?![\\W_]+$)\\S+$";
	private static String STRONG = "^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{6,100}$";

	public static int getPassWordStatus(String password) {
		if (password.length() == 0) {
			return 0;
		} else if (password.length() < 6 || isRuo(password)) {
			return 1;
		}
		if (password.matches(MID)) {
			if (password.matches(STRONG)) {
				return 3;
			}
			return 2;
		}
		return -1;
	}

	/**
	 * �Ƿ�������
	 * 
	 * @param password
	 * @return
	 */
	private static boolean isRuo(String password) {
		if(password.matches(WEAK)){
            return true;
        }
        return false;
	}

	public static InetAddress getIntentAddress(Context mContext)
			throws IOException {
		WifiManager wifiManager = (WifiManager) mContext
				.getSystemService(Context.WIFI_SERVICE);
		DhcpInfo dhcp = wifiManager.getDhcpInfo();
		int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
		byte[] quads = new byte[4];
		for (int k = 0; k < 4; k++)
			quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
		return InetAddress.getByAddress(quads);
	}
	public static String getIntentAddress(Context mContext,String deviceID)
			throws IOException {
		 WifiManager wifiManager =(WifiManager)mContext.getSystemService(Context.WIFI_SERVICE);  
		 //�ж�wifi�Ƿ���  
		 if (!wifiManager.isWifiEnabled()) {  
		  wifiManager.setWifiEnabled(true);    
		}  
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();       
		int address = wifiInfo.getIpAddress();   
	     String ip = intToIp(address);  
	     String ipAddress=ip.substring(0, ip.lastIndexOf(".")+1)+deviceID;
        Log.e("dxsipaddress", "ip="+ip+"--"+"ipAddress="+ipAddress);
		return ipAddress;
	}

	/**
	 * ���������()
	 */
	public static void hindKeyBoard(View btnKey) {
		InputMethodManager imm = (InputMethodManager) MyApp.app
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		imm.hideSoftInputFromWindow(btnKey.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);

	}

	/**
	 * byte[]��16�����ַ������
	 * 
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		stringBuilder.append("[");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
			if (i != src.length - 1) {
				stringBuilder.append(", ");
			}
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	/**
	 * ��������ʽ
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmial(String email) {
		// String str=
		// "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		String str =
		// "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		"^[A-Za-z0-9][\\w\\._]*[a-zA-Z0-9]+@[A-Za-z0-9-_]+\\.([A-Za-z]{2,4})";
		Pattern pattern = Pattern.compile(str, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		// Matcher matcher = Pattern.compile(regEx).matcher(email);
		return matcher.matches();
	}

	/**
	 * WiFi�Ƿ����
	 * 
	 * @param result
	 * @return
	 */
	public static boolean isWifiOpen(ScanResult result) {
		if (result.capabilities.toLowerCase().indexOf("wep") != -1
				|| result.capabilities.toLowerCase().indexOf("wpa") != -1) {
			return false;
		}
		return true;
	}

	/**
	 * ����¼���Ŀ¼ ~/videorecode
	 */
	public static File createRecodeFile() {
		if (!isSD()) {
			return null;
		}
		File sd = Environment.getExternalStorageDirectory();
		String path = sd.getPath() + "/videorecode";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		if (file == null) {
			Log.e("Utils", "create Recoding file failed");
		}
		return file;
	}

	/**
	 * �ж��ֻ�SD���Ƿ����
	 * 
	 * @return
	 */
	public static boolean isSD() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ����¼���ļ�����~/videorecoder/callId/callId_yyyy_MM_dd_HH_mm_ss.MP4��
	 * 
	 * @param callId
	 * @return
	 */
	public static String getVideoRecodeName(String callId) {
		if (!isSD()) {
			return "noSD";
		}
		long time = System.currentTimeMillis();
		File sd = Environment.getExternalStorageDirectory();
		String path = sd.getPath() + "/videorecode/" + callId;
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");// �ƶ����ڵ���ʾ��ʽ
		String name = callId + "_" + sdf.format(new Date(time));
		String filename = file.getPath() + "/" + name + ".MP4";
		return filename;
	}

	/**
	 * ��int��ֵת��Ϊռ�ĸ��ֽڵ�byte���飬������������(��λ��ǰ����λ�ں�)��˳�� ��bytesToInt2��������ʹ��
	 */
	public static int[] intToBytes2(int value) {
		int[] src = new int[4];
		src[3] = value & 0xFF;
		src[2] = (value >> 8) & 0xFF;
		src[1] = (value >> 16) & 0xFF;
		src[0] = (value >> 24) & 0xFF;

		return src;
	}

	/**
	 * �ַ�����Դ��ʽ��
	 * 
	 * @param formatSRC
	 * @param formats
	 * @return
	 */
	public static String getFormatString(int formatSRC, String... formats) {
		return MyApp.app.getResources().getString(formatSRC, formats);
	}

	/**
	 * DES����
	 * 
	 * @param datasource
	 * @param password
	 * @return
	 */
	public static byte[] desCrypto(byte[] datasource, String password) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			// ����һ���ܳ׹�����Ȼ��������DESKeySpecת����
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher����ʵ����ɼ��ܲ���
			Cipher cipher = Cipher.getInstance("DES");
			// ���ܳ׳�ʼ��Cipher����
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// ���ڣ���ȡ���ݲ�����
			// ��ʽִ�м��ܲ���
			return cipher.doFinal(datasource);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * DES����
	 * 
	 * @param src
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(byte[] src, String password) throws Exception {
		// DES�㷨Ҫ����һ�������ε������Դ
		SecureRandom random = new SecureRandom();
		// ����һ��DESKeySpec����
		DESKeySpec desKey = new DESKeySpec(password.getBytes());
		// ����һ���ܳ׹���
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// ��DESKeySpec����ת����SecretKey����
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher����ʵ����ɽ��ܲ���
		Cipher cipher = Cipher.getInstance("DES");
		// ���ܳ׳�ʼ��Cipher����
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// ������ʼ���ܲ���
		return new String(cipher.doFinal(src));
	}

	public static byte[] gainWifiMode() {
		byte[] data = new byte[20];
		for (int i = 0; i < data.length; i++) {
			data[i] = 0;
		}
		return data;
	}

	public static byte[] setDeviceApWifiPwd(String pwd) {
		byte[] data = new byte[272];
		for (int i = 0; i < data.length; i++) {
			data[0] = 0;
		}
		data[0] = 2;
		data[8] = 1;
		byte[] password = pwd.getBytes();
		for (int j = 0; j < password.length; j++) {
			data[j + 144] = password[j];
		}
		return data;

	}

	/**
	 * ��ͼ����µ����
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean saveImgToGallery(String fileName) {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
		if (!sdCardExist)
			return false;
		if (fileName == null || fileName.length() <= 0)
			return false;
		try {
			ContentValues values = new ContentValues();
			values.put("datetaken", new Date().toString());
			values.put("mime_type", "image/png");
			values.put("_data", fileName);
			ContentResolver cr = MyApp.app.getContentResolver();
			cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
		MediaScannerConnection.scanFile(MyApp.app,
				new String[] { AppConfig.Relese.SCREENSHORT }, null, null);
		return true;

	}

	/**
	 * callId��ѯ��Id ����Ϊ����ʱ��ѯ���н�ͼ,type 1Ϊʱ�併�� 2Ϊʱ������
	 */
	public static List<String> getScreenShotImagePath(final String callId,
			int type) {
		File[] data = new File[0];

		List<String> tempPathList = new ArrayList<String>();
		ArrayList<FileInfo> fileList = new ArrayList<FileInfo>();// ����Ҫ�����ļ���Ϣ���뵽FileInfo����
		File file = new File(AppConfig.Relese.SCREENSHORT);
		FileFilter filter = new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				if (null == callId || "".equals(callId)) {
					if (pathname.getName().endsWith(".jpg")) {
						return true;
					} else {
						return false;
					}
				} else {
					// final String
					// ipflag=FList.getInstance().getIpFlag(callId);
					if (pathname.getName().startsWith(callId)) {
						return true;
					} else {
						return false;
					}
				}
			}
		};
		data = file.listFiles(filter);
		if (null != data) {
			for (int i = 0, count = data.length; i < count; i++) {
				FileInfo info = new FileInfo();
				File f = data[i];
				info.path = f.getPath();
				info.LastModified = f.lastModified();
				fileList.add(info);
			}
			if (type == 1) {
				Collections.sort(fileList, new FileComparatorUp());// ͨ����дComparator��ʵ����
			} else {
				Collections.sort(fileList, new FileComparatorDown());// ͨ����дComparator��ʵ����
			}
			for (FileInfo info : fileList) {
				tempPathList.add(info.path);
			}

		}
		return tempPathList;
	}

	public static class FileInfo {
		long LastModified;
		String path;
	}

	public static class FileComparatorDown implements Comparator<FileInfo> {
		@Override
		public int compare(FileInfo file1, FileInfo file2) {
			if (file1.LastModified < file2.LastModified) {
				return -1;
			} else {
				return 1;
			}
		}
	}

	public static class FileComparatorUp implements Comparator<FileInfo> {
		@Override
		public int compare(FileInfo file1, FileInfo file2) {
			if (file1.LastModified < file2.LastModified) {
				return 1;
			} else {
				return -1;
			}
		}
	}
	public static byte[] btop = { (byte) 130, 1, 7, 0, (byte) 0xff, 0x01, 0x00,
			0x08, 0x00, (byte) 0xff, 0x08 };// ��
	public static byte[] bbottom = { (byte) 130, 1, 7, 0, (byte) 0xff, 0x01,
			0x00, 0x10, 0x00, (byte) 0xff, 0x10 };// ��
	public static byte[] bleft = { (byte) 130, 1, 7, 0, (byte) 0xff, 0x01,
			0x00, 0x04, (byte) 0xff, 0x00, 0x04 };// ��
	public static byte[] bright = { (byte) 130, 1, 7, 0, (byte) 0xff, 0x01,
			0x00, 0x02, (byte) 0xff, 0x00, 0x02, };// ��
	public static byte[] zoom_small = { (byte) 130, 1, 7, 0, (byte) 0xff, 0x01,
			0x00, 0x20, 0x00, 0x00, 0x21 };// �䱶��
	public static byte[] zoom_big = { (byte) 130, 1, 7, 0, (byte) 0xff, 0x01,
			0x00, 0x40, 0x00, 0x00, 0x41 };// �䱶��
	public static byte[] focus_small = { (byte) 130, 1, 7, 0, (byte) 0xff,
			0x01, 0x00, (byte) 0x80, 0x00, 0x00, (byte) 0x81 };// �۽���
	public static byte[] focus_big = { (byte) 130, 1, 7, 0, (byte) 0xff, 0x01,
			0x01, 0x00, 0x00, 0x00, 0x02 };// �۽�Զ
	public static byte[] aperture_smal = { (byte) 130, 1, 7, 0, (byte) 0xff,
			0x01, 0x02, 0x00, 0x00, 0x00, 0x03 };// ��ȦС
	public static byte[] aperture_big = { (byte) 130, 1, 7, 0, (byte) 0xff,
			0x01, 0x04, 0x00, 0x00, 0x00, 0x05 };// ��Ȧ��

	public static int getColorByResouce(int R) {
		return MyApp.app.getResources().getColor(R);
	}
     public static boolean checkPassword(String password){
    	 if(password.length()<10&&Utils.isNumeric(password)&&password.charAt(0) == '0'){
    		 return false;
    	 }else{
    		 return true; 
    	 }
     }
     public static String getWifiName(String wifiname){
    	if(wifiname!=null&&wifiname.length()>2){
    		int first = wifiname.charAt(0);
    		if (first == 34) {
    			wifiname = wifiname.substring(1, wifiname.length() - 1);
    		}
    	}
    	return wifiname;
     }
     /**
      * byte��λ���
      *
      * @param info
      * @param isOpposite �Ƿ����ȡ
      * @return
      */
     public static int[] getByteBinnery(byte info, boolean isOpposite) {
         int i;
         int j = 0;
         int[] gpiostatus = new int[8];
         if (isOpposite) {
             for (i = 0; i <= 7; ++i) {
                 gpiostatus[j] = (byte) ((info >> i) & 0x01);
                 j++;
             }
         } else {
             for (i = 7; i >= 0; --i) {
                 gpiostatus[j] = (byte) ((info >> i) & 0x01);
                 j++;
             }
         }
         return gpiostatus;
     }
     /**ָ��ĳλ������Ϊ1
      * @param src
      * @param position
      * @return
      */
     public static byte ChangeBitTrue(byte src,int position){
         return (byte) (src|(1<<position));
     }
     /**ָ��ĳλ������0������λ���ֲ���
      * @param src
      * @param position
      * @return
      */
     public static byte ChangeByteFalse(byte src, int position){
         int t= Integer.MAX_VALUE^(1<<position);
         return (byte) (src&t);
     }
     /**
      * ���ۿ���
      * @param value
      * @return
      */
     public static byte[] intToBytes(int value) {
         byte[] src = new byte[4];
         src[0] = (byte) (value & 0xFF);
         src[1] = (byte) ((value >> 8) & 0xFF);
         src[2] = (byte) ((value >> 16) & 0xFF);
         src[3] = (byte) ((value >> 24) & 0xFF);

         return src;
     }
     public static String getStringForId(int StringId) {
         return MyApp.app.getResources().getString(StringId);

     }
    
     
     public static String getPhoneIpdress(){
    	 WifiManager wifiManager = (WifiManager) MyApp.app.getSystemService(Context.WIFI_SERVICE);  
    	 //�ж�wifi�Ƿ���  
         if (!wifiManager.isWifiEnabled()) {  
    	     wifiManager.setWifiEnabled(true);    
    	 }  
    	 WifiInfo wifiInfo = wifiManager.getConnectionInfo();       
    	 int ipAddress = wifiInfo.getIpAddress();   
    	 String ip = intToIp(ipAddress);
		return ip;   

     }
     

    
}
