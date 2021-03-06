package com.hrsst.smarthome.activity;

import java.util.Calendar;

import com.hrsst.smarthome.adapter.DateNumericAdapter;
import com.hrsst.smarthome.dtsj.R;
import com.hrsst.smarthome.global.Constants;
import com.hrsst.smarthome.pojo.Contact;
import com.hrsst.smarthome.util.Utils;
import com.hrsst.smarthome.widget.OnWheelScrollListener;
import com.hrsst.smarthome.widget.WheelView;
import com.p2p.core.P2PHandler;
import com.p2p.core.utils.DelayThread;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SetTimeActivity extends Activity implements OnClickListener{
	
	private Context mContext;
	private Contact contact;
	private boolean isRegFilter = false;
	WheelView date_year,date_month,date_day,date_hour,date_minute,w_urban;
	
	RelativeLayout setting_urban_title;
	TextView time_text;
	ProgressBar progressBar;
	
	String cur_modify_time;
	int current_urban;
	Button bt_set_timezone,setting_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_time);
		
		mContext = this;
		contact = (Contact) getIntent().getSerializableExtra("contact");
		initComponent();
		regFilter();
		P2PHandler.getInstance().getDeviceTime(contact.contactId, contact.contactPassword);
		P2PHandler.getInstance().getNpcSettings(contact.contactId, contact.contactPassword);
	}
	
	public void initComponent(){
		Calendar calendar = Calendar.getInstance();
		setting_time = (Button)findViewById(R.id.setting_time);
		time_text = (TextView)findViewById(R.id.time_text);
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		setting_time.setEnabled(false);
		setting_time.setOnClickListener(this);
		
		// year
        int curYear = calendar.get(Calendar.YEAR);
		date_year = (WheelView)findViewById(R.id.date_year);
		date_year.setViewAdapter(new DateNumericAdapter(mContext, 2010, 2036));
		date_year.setCurrentItem(curYear-2010);
		date_year.addScrollingListener(scrolledListener);
		date_year.setCyclic(true);
		
		int curMonth = calendar.get(Calendar.MONTH)+1;
		date_month = (WheelView)findViewById(R.id.date_month);
		date_month.setViewAdapter(new DateNumericAdapter(mContext, 1, 12));
		date_month.setCurrentItem(curMonth-1);
		date_month.addScrollingListener(scrolledListener);
		date_month.setCyclic(true);
		
		int curDay = calendar.get(Calendar.DAY_OF_MONTH);
		date_day = (WheelView)findViewById(R.id.date_day);
		date_day.setViewAdapter(new DateNumericAdapter(mContext, 1, 31));
		date_day.setCurrentItem(curDay-1);
		date_day.addScrollingListener(scrolledListener);
		date_day.setCyclic(true);
		
		int curHour = calendar.get(Calendar.HOUR_OF_DAY);
		date_hour = (WheelView)findViewById(R.id.date_hour);
		date_hour.setViewAdapter(new DateNumericAdapter(mContext, 0, 23));
		date_hour.setCurrentItem(curHour);
		date_hour.setCyclic(true);
		
		int curMinute = calendar.get(Calendar.MINUTE);
		date_minute = (WheelView)findViewById(R.id.date_minute);
		date_minute.setViewAdapter(new DateNumericAdapter(mContext, 0, 59));
		date_minute.setCurrentItem(curMinute);
		date_minute.setCyclic(true);
		
		w_urban=(WheelView)findViewById(R.id.w_urban);
		w_urban.setViewAdapter(new DateNumericAdapter(mContext, -11, 12));
		w_urban.setCyclic(true);
		bt_set_timezone=(Button)findViewById(R.id.bt_set_timezone);
		bt_set_timezone.setOnClickListener(this);
		setting_urban_title=(RelativeLayout)findViewById(R.id.setting_urban_title);
		
	}
	
	
	private boolean wheelScrolled = false;
	  
	OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
	      public void onScrollingStarted(WheelView wheel) {
	          wheelScrolled = true;
	          updateStatus();
	      }
	      public void onScrollingFinished(WheelView wheel) {
	          wheelScrolled = false;
	          updateStatus();
	      }
	  };

	
	public void updateStatus(){
		int year = date_year.getCurrentItem()+2010;
		int month = date_month.getCurrentItem()+1;
		
		if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
			date_day.setViewAdapter(new DateNumericAdapter(mContext, 1, 31));
		}else if(month==2){
			
			boolean isLeapYear = false;
			if(year%100==0){
				if(year%400==0){
					isLeapYear = true;
				}else{
					isLeapYear = false;
				}
			}else{
				if(year%4==0){
					isLeapYear = true;
				}else{
					isLeapYear = false;
				}
			}
			if(isLeapYear){
				if(date_day.getCurrentItem()>28){
					date_day.scroll(30, 2000);
	    		}
				date_day.setViewAdapter(new DateNumericAdapter(mContext, 1, 29));
			}else{
				if(date_day.getCurrentItem()>27){
					date_day.scroll(30, 2000);
	    		}
				date_day.setViewAdapter(new DateNumericAdapter(mContext, 1, 28));
			}
			
		}else{
			if(date_day.getCurrentItem()>29){
				date_day.scroll(30, 2000);
			}
			date_day.setViewAdapter(new DateNumericAdapter(mContext, 1, 30));
		}
		
	}
	
	public void regFilter(){
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.P2P.ACK_RET_SET_TIME);
		filter.addAction(Constants.P2P.ACK_RET_GET_TIME);
		filter.addAction(Constants.P2P.RET_SET_TIME);
		filter.addAction(Constants.P2P.RET_GET_TIME);
		filter.addAction(Constants.P2P.RET_GET_TIME_ZONE);
		filter.addAction(Constants.P2P.ACK_RET_SET_TIME_ZONE);
		mContext.registerReceiver(mReceiver, filter);
		isRegFilter = true;
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context arg0, Intent intent) {
			if(intent.getAction().equals(Constants.P2P.RET_GET_TIME)){
				String time = intent.getStringExtra("time");
				time_text.setText(time);
				progressBar.setVisibility(RelativeLayout.GONE);
				time_text.setVisibility(RelativeLayout.VISIBLE);
				setting_time.setEnabled(true);
			}else if(intent.getAction().equals(Constants.P2P.RET_SET_TIME)){
				int result = intent.getIntExtra("result", -1);
				if(result==Constants.P2P_SET.DEVICE_TIME_SET.SETTING_SUCCESS){
					time_text.setText(cur_modify_time);
					progressBar.setVisibility(RelativeLayout.GONE);
					time_text.setVisibility(RelativeLayout.VISIBLE);
					setting_time.setEnabled(true);
					Toast.makeText(mContext, R.string.modify_success, Toast.LENGTH_SHORT).show();
				}else{
					progressBar.setVisibility(RelativeLayout.GONE);
					time_text.setVisibility(RelativeLayout.VISIBLE);
					setting_time.setEnabled(true);
					Toast.makeText(mContext, R.string.operator_error, Toast.LENGTH_SHORT).show();
				}
			}else if(intent.getAction().equals(Constants.P2P.ACK_RET_GET_TIME)){
				int result = intent.getIntExtra("result", -1);
				if(result==Constants.P2P_SET.ACK_RESULT.ACK_PWD_ERROR){
					
					Intent i = new Intent();
					i.setAction(Constants.Action.CONTROL_SETTING_PWD_ERROR);
					mContext.sendBroadcast(i);
					
				}else if(result==Constants.P2P_SET.ACK_RESULT.ACK_NET_ERROR){
					Log.e("my","net error resend:get npc time");
					P2PHandler.getInstance().getDeviceTime(contact.contactId, contact.contactPassword);
				}
			}else if(intent.getAction().equals(Constants.P2P.ACK_RET_SET_TIME)){
				
				int result = intent.getIntExtra("result", -1);
				if(result==Constants.P2P_SET.ACK_RESULT.ACK_PWD_ERROR){
					Intent i = new Intent();
					i.setAction(Constants.Action.CONTROL_SETTING_PWD_ERROR);
					mContext.sendBroadcast(i);
				}else if(result==Constants.P2P_SET.ACK_RESULT.ACK_NET_ERROR){
					Log.e("my","net error resend:set npc time");
					P2PHandler.getInstance().setDeviceTime(contact.contactId, contact.contactPassword, cur_modify_time);
				}
				
			}else if(intent.getAction().equals(Constants.P2P.RET_GET_TIME_ZONE)){
				 int timezone=intent.getIntExtra("state", -1);
				 if(timezone!=-1){
					 setting_urban_title.setVisibility(RelativeLayout.VISIBLE);
				 }
				 w_urban.setCurrentItem(timezone);
			}else if(intent.getAction().equals(Constants.P2P.ACK_RET_SET_TIME_ZONE)){
				int state=intent.getIntExtra("state",-1);
				if(state==Constants.P2P_SET.ACK_RESULT.ACK_SUCCESS){
					Toast.makeText(mContext, R.string.timezone_success, Toast.LENGTH_SHORT).show();
					P2PHandler.getInstance().getDeviceTime(contact.contactId, contact.contactPassword);
				}else if(state==Constants.P2P_SET.ACK_RESULT.ACK_NET_ERROR){
					P2PHandler.getInstance().setTimeZone(contact.contactId,contact.contactPassword,current_urban);
				}
			}
		}
	};
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()){
		case R.id.setting_time:
			progressBar.setVisibility(RelativeLayout.VISIBLE);
			time_text.setVisibility(RelativeLayout.GONE);
			setting_time.setEnabled(false);
			new DelayThread(Constants.SettingConfig.SETTING_CLICK_TIME_DELAY,new DelayThread.OnRunListener() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					cur_modify_time = Utils.convertDeviceTime(
							date_year.getCurrentItem()+10,
							date_month.getCurrentItem()+1,
							date_day.getCurrentItem()+1,
							date_hour.getCurrentItem(), 
							date_minute.getCurrentItem());
					P2PHandler.getInstance().setDeviceTime(contact.contactId, contact.contactPassword, cur_modify_time);
				}
			}).start();
			break;
		case R.id.bt_set_timezone:
			current_urban=w_urban.getCurrentItem();
			P2PHandler.getInstance().setTimeZone(contact.contactId, contact.contactPassword, current_urban);	
		    break;
		}
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(isRegFilter){
			mContext.unregisterReceiver(mReceiver);
			isRegFilter = false;
		}
		Intent it=new Intent();
		it.setAction(Constants.Action.CONTROL_BACK);
		mContext.sendBroadcast(it);
	}
	//@@返回上一界面
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if (keyCode == KeyEvent.KEYCODE_BACK){
				Intent i = new Intent(mContext,ApMonitorActivity.class);
				i.putExtra("contact",contact);
				startActivity(i);
				finish();
			}
			return super.onKeyDown(keyCode, event);
		}
}
