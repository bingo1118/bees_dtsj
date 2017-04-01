package com.hrsst.smarthome.thread;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.hrsst.smarthome.global.Constants;
import com.hrsst.smarthome.global.MyApp;
import com.hrsst.smarthome.net.SocketUDP;
import com.hrsst.smarthome.pojo.UpdateInfo;
import com.hrsst.smarthome.util.SharedPreferencesManager;
import com.hrsst.smarthome.util.Utils;
import com.p2p.core.P2PHandler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Xml;

public class MainThread {
	static MainThread manager;
	boolean isRun;
	long lastSysmsgTime;
	private Main main;
	Context mContext;
	private static byte[] orderSend;
	private static boolean isOpenThread;
	private static SocketUDP mSocketUDPClient;
	private static int count;
	private WifiReceiver receiverWifi;
	private WifiManager wifim;
	private static String[] contactIds;// = new String[];

	public MainThread(Context mContext) {
		this.mContext = mContext;
	}


	public static MainThread getInstance() {
		return manager;
	}

	class Main extends Thread {
		@Override
		public void run() {
			if(null==mSocketUDPClient){
				mSocketUDPClient = SocketUDP.newInstance(Constants.SeverInfo.SERVER
						, Constants.SeverInfo.PORT);
				mSocketUDPClient.startAcceptMessage();
			}
			isRun = true;
			while (isRun) {
				if (isOpenThread == true) {
					if(null!=orderSend&&orderSend.length>0){
						long last_check_update_time = SharedPreferencesManager
								.getInstance().getLastAutoCheckUpdateTime(MyApp.app);
						checkUpdate(last_check_update_time);
						Utils.sleepThread(10* 1000);
						mSocketUDPClient.sendMsg(orderSend);
						
					}
					if(null!=contactIds&&contactIds.length>0){
						P2PHandler.getInstance().getFriendStatus(contactIds);
					}
				}else{
					Utils.sleepThread(10 * 1000);
				}
				getWifiSSID();
				Utils.sleepThread(30* 1000);
			}
		}
	};
	
	public static void refreash(){
		if(null!=orderSend&&orderSend.length>0){
			mSocketUDPClient.sendMsg(orderSend);
		}
		if(null!=contactIds&&contactIds.length>0){
			P2PHandler.getInstance().getFriendStatus(contactIds);//��ȡ����ͷ״̬��
		}
	}
	
	public void go() {
		if (null == main || !main.isAlive()) {
			main = new Main();
			main.start();
		}
		receiverWifi = new WifiReceiver();
		MyApp.app.registerReceiver(receiverWifi, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));// ע��㲥
	}

	public void kill() {
		isRun = false;
		main = null;
		mContext.unregisterReceiver(receiverWifi);
	}
	
	public void getWifiSSID() {
		wifim = (WifiManager)MyApp.app.getSystemService(MyApp.app.WIFI_SERVICE);
		if(wifim.isWifiEnabled()){
			wifim.startScan();
		}
	}
	
	class WifiReceiver extends BroadcastReceiver
	{
		public void onReceive(Context context, Intent intent)
		{
			if (intent.getAction().equals(
					WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)){
				if(null!=wifim){
					if(wifim.isWifiEnabled()){
						List<ScanResult> li = wifim.getScanResults();
						List<String> wifi = new ArrayList<String>();
						if (li.size() > 0) {
							for (int i = 0; i < li.size(); i++) {
								String str = li.get(i).SSID;
								boolean b = str.startsWith("HRSST_");
								if(b&&str.length()==18){
									wifi.add(str);
								}
							}
						} 
						Intent i = new Intent("FIND_NEW_DEVICE_NUMBER");
						i.putStringArrayListExtra("count", (ArrayList<String>) wifi);
						MyApp.app.sendBroadcast(i);
					}
				}
			}
		}
	}
	
	public static void setByte(byte[] orderSend,int count,String[] contactIds){
		MainThread.orderSend = orderSend;
		MainThread.count = count;
		MainThread.contactIds = contactIds;
	}
	public static void setByte(int count,String[] contactIds){
		MainThread.count = count;
		MainThread.contactIds = contactIds;
	}//@@
	
	public static void setOpenThread(boolean isOpenThread) {
		MainThread.isOpenThread = isOpenThread;
	}

	private UpdateInfo mUpdateInfo = new UpdateInfo();
	public void checkUpdate(long last_check_update_time) {
		try {
			long now_time = System.currentTimeMillis();
			//1000 * 60 * 60 * 12
			if ((now_time - last_check_update_time) > 1000 * 60 * 60 * 12) {
				SharedPreferencesManager.getInstance()
						.putLastAutoCheckUpdateTime(now_time, MyApp.app);
				// ������ַ����
				URL url = new URL(Constants.UPDATE_URL);
				// ������
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				// ����
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5000);
				if (conn.getResponseCode() == 200) {
					InputStream input = conn.getInputStream();
					// ���� xml pull
					//������PullParser����
					XmlPullParser parser = Xml.newPullParser();
					//��������
					parser.setInput(input, "GBK");
					int eventType = XmlPullParser.START_DOCUMENT;
					// ����
					while (eventType != XmlPullParser.END_DOCUMENT) {
						switch (eventType) {
						case XmlPullParser.START_TAG:// ��ǩ��ʼ
							if ("versionCode".equals(parser.getName()//��ȡ��ǩ��
									)) {
								mUpdateInfo.versionCode = parser.nextText();//��ȡ�ı�ֵ
							} else if ("versionName".equals(parser.getName())) {
								mUpdateInfo.versionName = parser.nextText();
							} else if ("message".equals(parser.getName())) {
								mUpdateInfo.message = parser.nextText();
							} else if ("url".equals(parser.getName())) {
								mUpdateInfo.url = parser.nextText();
							}
							break;
						}
						// ִ����һ��
						eventType = parser.next();
					}
					// ����
					int serverCode = Integer.parseInt(mUpdateInfo.versionCode);
	
					if (serverCode > getlocalVersion()) {
						Intent i = new Intent("Constants.Action.ACTION_UPDATE");
						i.putExtra("url", mUpdateInfo.url);
						i.putExtra("message", mUpdateInfo.message);
						MyApp.app.sendBroadcast(i);
					}
					if(last_check_update_time==-1&&serverCode<=getlocalVersion()){
						Intent i = new Intent("Constants.Action.ACTION_UPDATE_NO");
						i.putExtra("message", "�������°汾");
						MyApp.app.sendBroadcast(i);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			Intent i = new Intent("Constants.Action.ACTION_UPDATE_NO");
//			MyApp.app.sendBroadcast(i);
		} 
	}
	
	public int getlocalVersion(){
 		int localversion = 0;
 		try {
 			PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
 			localversion = info.versionCode;
 		} catch (NameNotFoundException e) {
 			e.printStackTrace();
 		}
 		return localversion;
 	}
}
