package com.hrsst.smarthome.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.hrsst.smarthome.dtsj.R;
import com.hrsst.smarthome.global.Constants;
import com.hrsst.smarthome.net.SocketUDP;
import com.hrsst.smarthome.order.SendServerOrder;
import com.hrsst.smarthome.order.UnPackServer;
import com.hrsst.smarthome.pojo.Contact;
import com.hrsst.smarthome.pojo.UnPackageFromServer;
import com.hrsst.smarthome.util.MusicManger;
import com.hrsst.smarthome.util.SharedPreferencesManager;
import com.hrsst.smarthome.util.Utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AlarmActivity extends Activity {
	Context mContext;
	private int TIME_OUT = 20 * 1000;
	ImageView alarm_fk_img, alarm_back,watch_video_image;
	boolean isAlarm;
	int max_alarm_count;
	private byte[] payload;
	private String macStr;
	private String timeStr;
	private String positionStr;
	private TextView alarm_msg_tv, alarm_msg_time_tv;
	private Contact mContact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int mCurrentOri = getResources().getConfiguration().orientation;
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		Window win = getWindow();
		win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		mContext = this;
		setContentView(R.layout.activity_firelink_alarm);
		alarm_msg_tv = (TextView) findViewById(R.id.alarm_msg_tv);
		alarm_msg_time_tv = (TextView) findViewById(R.id.alarm_msg_time_tv);
		payload = getIntent().getByteArrayExtra("payload");
		mContact = (Contact) getIntent().getSerializableExtra("mContact");
		alarm_fk_img = (ImageView) findViewById(R.id.alarm_fk_img);
		alarm_back = (ImageView) findViewById(R.id.alarm_back);
		watch_video_image= (ImageView) findViewById(R.id.watch_video_image);
		alarm_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		watch_video_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(null!=mContact){
					mContact.contactType = 0;
					mContact.apModeState = 1;
					
					Intent monitor = new Intent();
					monitor.setClass(mContext, ApMonitorActivity.class);
					monitor.putExtra("contact", mContact);
					monitor.putExtra("connectType",Constants.ConnectType.P2PCONNECT);
					monitor.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mContext.startActivity(monitor);
					finish();
				}else{
					watch_video_image.setVisibility(View.GONE);
				}
			}
		});
		getAlarmMessages(payload);
		alarmInit();
		excuteTimeOutTimer();
		if(null!=mContact){
			//��ʾ���Բ鿴��Ƶ��ť
			watch_video_image.setVisibility(View.VISIBLE);
		}else{
			//���ؿ��Բ鿴��Ƶ��ť
			watch_video_image.setVisibility(View.GONE);
		}	
	}
	/**
	 * ����������������
	 */
	private void alarmInit() {
		final AnimationDrawable anim = (AnimationDrawable) alarm_fk_img
				.getBackground();
		OnPreDrawListener opdl = new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				anim.start();
				return true;
			}

		};
		alarm_fk_img.getViewTreeObserver().addOnPreDrawListener(opdl);
	}

	boolean viewed = false;
	Timer timeOutTimer;
	public static final int USER_HASNOTVIEWED = 3;

	private Handler mHandler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case USER_HASNOTVIEWED:
				finish();
				break;
			default:
				break;
			}
			return false;
		}
	});

	// ��ʱ��ʱ��
	public void excuteTimeOutTimer() {
		timeOutTimer = new Timer();
		TimerTask mTask = new TimerTask() {
			@Override
			public void run() {
				// ����һ���Ի���
				if (!viewed) {
					Message message = new Message();
					message.what = USER_HASNOTVIEWED;
					mHandler.sendMessage(message);
				}
			}
		};
		timeOutTimer.schedule(mTask, TIME_OUT);
	}

	// ��������
	public void loadMusicAndVibrate() {
		isAlarm = true;
		MusicManger.getInstance().playAlarmMusic(getApplicationContext());
		new Thread() {
			public void run() {
				while (isAlarm) {
					MusicManger.getInstance().Vibrate();
					Utils.sleepThread(100);
				}
				MusicManger.getInstance().stopVibrate();
			}
		}.start();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		isAlarm = false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadMusicAndVibrate();
	}

	@Override
	protected void onPause() {
		super.onPause();
		MusicManger.getInstance().stop();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onDestroy() {
//		unregisterReceiver(mReceiver);
		super.onDestroy();
		if (timeOutTimer != null) {
			timeOutTimer.cancel();
			timeOutTimer=null;
		}

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	private void getAlarmMessages(byte[] payload) {
		byte type = payload[1];
		byte[] mac = new byte[12];
		for (int i = 0; i < 12; i++) {
			mac[i] = payload[i + 2];
		}
		byte[] time = new byte[19];
		for (int i = 0; i < 19; i++) {
			time[i] = payload[i + 14];
		}
		int p = payload.length - 33;
		byte[] position = new byte[p];
		for (int i = 0; i < p; i++) {
			position[i] = payload[33 + i];
		}
		macStr = new String(mac).trim();
		timeStr = new String(time).trim();
		positionStr = new String(position).trim();
		String s = null;
		switch (type) {
		case 1:
			s = getResources().getString(R.string.alarmactivity_yangan);
			break;
		case 2:
			s = getResources().getString(R.string.alarmactivity_menci);
			break;
		case 3:
			s = getResources().getString(R.string.alarmactivity_hongwai);
			break;
		case 4:
			s = getResources().getString(R.string.alarmactivity_keranqiti);
			break;
		case 5:
			s = getResources().getString(R.string.water_dev);
			break;
		case 6:
			s = getResources().getString(R.string.controler_dev);
			break;
		default:
			break;
		}
		alarm_msg_tv.setText(positionStr + getResources().getString(R.string.alarmactivity_de) + s + getResources().getString(R.string.alarmactivity_issue_an_alert));
		alarm_msg_time_tv.setText(getResources().getString(R.string.alarmactivity_alarm_time) + timeStr);
	}
}
