package com.hrsst.smarthome.activity;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hrsst.smarthome.dtsj.R;
import com.hrsst.smarthome.SetVideoBuzzerActivity;
import com.hrsst.smarthome.global.AppConfig;
import com.hrsst.smarthome.global.Constants;
import com.hrsst.smarthome.global.NpcCommon;
import com.hrsst.smarthome.net.P2PConnect;
import com.hrsst.smarthome.net.SettingListener;
import com.hrsst.smarthome.net.SocketUDP;
import com.hrsst.smarthome.order.SendServerOrder;
import com.hrsst.smarthome.pojo.Contact;
import com.hrsst.smarthome.util.NetSpeed;
import com.hrsst.smarthome.util.SharedPreferencesManager;
import com.hrsst.smarthome.util.Utils;
import com.hrsst.smarthome.widget.HeaderView;
import com.hrsst.smarthome.widget.MyInputPassDialog;
import com.hrsst.smarthome.widget.MyInputPassDialog.OnCustomDialogListener;
import com.hrsst.smarthome.widget.NormalDialog;
import com.p2p.core.BaseMonitorActivity;
import com.p2p.core.P2PHandler;
import com.p2p.core.P2PValue;
import com.p2p.core.P2PView;


public class ApMonitorActivity extends BaseMonitorActivity implements OnClickListener {
	
	ImageView yinliang;//@@
	boolean isOpenReverse=true;//��Ƶ��ת״̬@@
	public static final String PACKAGE_NAME = "com.hrsst.smarthome.global.";//@@
	public static final String RET_GET_BUZZER = PACKAGE_NAME+"RET_GET_BUZZER";//@@
	
	RelativeLayout layout_title,image_im,play_back_im,share_dev_im,sd_card_im,setting_im,preset_pos_im;
	ImageView iv_full_screen,iv_voice,iv_speak,iv_screenshot,open_door,yuzhiwei_im,preset_pos_binder_im;
	ImageView yinliang_im;//@@
	LinearLayout l_control;
	RelativeLayout rl_control,control_bottom;
	LinearLayout control_top;
	Button choose_video_format,szyzw,ckyzw;
	View line;
	TextView video_mode_hd, video_mode_sd, video_mode_ld,tv_name;
	ImageView close_voice,send_voice,iv_half_screen,hungup,screenshot,defence_state;
	LinearLayout layout_voice_state;
	RelativeLayout r_p2pview,yuzhiwei;
    ImageView voice_state,baidu1,baidu2,baidu3,baidu4,baidu5;
    private RelativeLayout record_im ;//¼�����ð�ť@@
    private RelativeLayout time_set_im;//ʱ������@@
    LinearLayout l_device_list;
	private Contact mContact;
	int callType=3;
	private Context mContext;
	boolean isReject = false;
	boolean isRegFilter=false;
	private int ScrrenOrientation;
	int window_width, window_height;
	String callId,password;
	int connectType;
	private int defenceState = -1;
	boolean mIsCloseVoice = false;
	int mCurrentVolume, mMaxVolume;
	AudioManager mAudioManager;
	boolean isSurpportOpenDoor=false;
	boolean isShowVideo = false;
	boolean isSpeak = false;
	int current_video_mode;
	int screenWidth;
	int screenHeigh;
	// ˢ�¼�ز���
	private RelativeLayout rlPrgTxError;
	private TextView txError, tx_wait_for_connect;
	private Button btnRefrash;
	private ProgressBar progressBar;
	private HeaderView ivHeader;
	private String [] ipcList;
	int number;
	int currentNumber=0;
	boolean isShowDeviceList=false;
	List<TextView> devicelist=new ArrayList<TextView>();
//  ҡ�ֻ��л�ipc
	Vibrator vibrator;
	SensorManager sensorManager;
	Sensor sensor;
	SensorEventListener sensorListener;
	boolean isShake=true;
	private long lastUpdateTime;
	private float lastX;
	private float lastY;
	private float lastZ;
	private static final int UPTATE_INTERVAL_TIME = 70;
	private static final int SPEED_SHRESHOLD = 2000;
	private boolean isReceveHeader=false;
	boolean isPermission=true;
	private View vLineHD;
	private boolean connectSenconde=false;
	int pushAlarmType;
	boolean isCustomCmdAlarm=false;
	private Handler mhandler = new Handler();
	private AlertDialog modifyDialog,mDialog;
	private SocketUDP mSocketUDPClient;
	private String fromUserNum;
	private boolean yuzhiweiFlag=false;
	NetSpeed speed;
	private TextView net_speed_tv;
	private int ifshare;//@@
	/*
	 * ƽ������
	����1280*720 �� ƽ��170KB/S
	����640*360 ��ƽ��110KB/S
	����320*192��ƽ��70KB/S*/
	private ImageView iv_fanzhuan;//��ת��ť@@
	private View view1, view2;  
    private ViewPager viewPager;  //��Ӧ��viewPager  
    private List<View> viewList;//view����  
    private TextView pager1;
    private TextView pager2;
	protected int position=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apmonitor);
		P2PConnect.setPlaying(true);
		Window win = getWindow();
	        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
	                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
	        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
	    mContext=this;
		mContact=(Contact) getIntent().getSerializableExtra("contact");
		ifshare=getIntent().getExtras().getInt("ifshare");//@@
		P2PHandler.getInstance().getNpcSettings(mContact.contactId,
				mContact.contactPassword);//@@
		P2PHandler.getInstance().getBindAlarmId(mContact.contactId,
				mContact.contactPassword);//@@
		if(mContact.contactType==P2PValue.DeviceType.IPC){
			setIsLand(false);
		}else{
			setIsLand(true);
		}
		SharedPreferencesManager.getInstance()
				.putData(mContext,
				Constants.WatchAction.IF_WATCH,
				"yes");
		SharedPreferencesManager.getInstance()
				.putData(mContext,
				Constants.WatchAction.CAMERA_ID,
				mContact.contactId);
		ipcList=getIntent().getStringArrayExtra("ipcList");
		number=getIntent().getIntExtra("number", -1);
		connectType=getIntent().getIntExtra("connectType", Constants.ConnectType.P2PCONNECT);
		isSurpportOpenDoor=getIntent().getBooleanExtra("isSurpportOpenDoor", false);
		isCustomCmdAlarm=getIntent().getBooleanExtra("isCustomCmdAlarm", false);
		callId=mContact.contactId;
		if(number>0){
			callId=ipcList[0];
		}
		password=mContact.contactPassword;
		P2PConnect.setMonitorId(callId);// �����ڼ�ص�ID
		SettingListener.setMonitorID(callId);// �����ڼ�ص�ID
		getScreenWithHeigh();
		regFilter();
		callDevice();
		initcComponent();
		fromUserNum = SharedPreferencesManager.getInstance().getData(mContext,
				Constants.UserInfo.USER_NUMBER);
		if (mAudioManager == null) {
			mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		}
		mCurrentVolume = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		mMaxVolume = mAudioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		ScrrenOrientation = Configuration.ORIENTATION_PORTRAIT;
		vibrator=(Vibrator) mContext.getSystemService(mContext.VIBRATOR_SERVICE);
		 
	}
	//@@
	private void initviewset() {
				viewPager = (ViewPager) findViewById(R.id.viewpager);  
				pager1=(TextView)findViewById(R.id.pager1);
				pager2=(TextView)findViewById(R.id.pager2);
		        LayoutInflater inflater=getLayoutInflater();  
		        view1 = inflater.inflate(R.layout.apview_set1, null);  
		        view2 = inflater.inflate(R.layout.apview_set2,null);  
		          
		        viewList = new ArrayList<View>();// ��Ҫ��ҳ��ʾ��Viewװ��������  
		        viewList.add(view1);  
		        viewList.add(view2);  
		        play_back_im = (RelativeLayout)view1.findViewById(R.id.play_back_im);
		        image_im = (RelativeLayout)view1.findViewById(R.id.image_im);
		        preset_pos_im = (RelativeLayout)view1.findViewById(R.id.preset_pos_im);
		        share_dev_im = (RelativeLayout)view1.findViewById(R.id.share_dev_im);
		        setting_im = (RelativeLayout)view1.findViewById(R.id.setting_im);
		        sd_card_im = (RelativeLayout)view2.findViewById(R.id.sd_card_im);
		        record_im= (RelativeLayout)view2.findViewById(R.id.record_im);
		        time_set_im=(RelativeLayout)view2.findViewById(R.id.time_set_im);
		        PagerAdapter pagerAdapter = new PagerAdapter() {  
		            
		        	@Override
		        	public void setPrimaryItem(ViewGroup container,
		        			int position, Object object) {
		        		// TODO �Զ����ɵķ������
		        		super.setPrimaryItem(container, position, object);
		        	}
		            @Override  
		            public boolean isViewFromObject(View arg0, Object arg1) {  
		                // TODO Auto-generated method stub  
		                return arg0 == arg1;  
		            }  
		              
		            @Override  
		            public int getCount() {  
		                // TODO Auto-generated method stub  
		                return viewList.size();  
		            }  
		              
		            @Override  
		            public void destroyItem(ViewGroup container, int position,  
		                    Object object) {  
		                // TODO Auto-generated method stub  
		                container.removeView(viewList.get(position));  
		            }  
		              
		            @Override  
		            public Object instantiateItem(ViewGroup container, int position) {  
		                // TODO Auto-generated method stub  
		                container.addView(viewList.get(position)); 
		                return viewList.get(position);  
		            }  
		        };  
		          
		          
		        viewPager.setAdapter(pagerAdapter);
		        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
					
					@Override
					public void onPageSelected(int arg0) {
						if(position==1){
		                	  pager1.setText("��");
		                	  pager2.setText("��");
		                	  position=0;
		                  }else{
		                	  pager1.setText("��");
		                	  pager2.setText("��");
		                	  position=1;
		                  }
					}
					
					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO �Զ����ɵķ������
						
					}
					
					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO �Զ����ɵķ������
						
					}
				});
	}
	public void initcComponent(){
		yinliang=(ImageView)findViewById(R.id.yinliang_im);
		pView=(P2PView)findViewById(R.id.p2pview);
		P2PView.type=0;
		
		initviewset();//@@
		
		yinliang_im=(ImageView)findViewById(R.id.yinliang_im);
		yinliang_im.setOnClickListener(this);
		
		preset_pos_binder_im = (ImageView) findViewById(R.id.preset_pos_binder_im);
		baidu1 = (ImageView) findViewById(R.id.baidu1);
		baidu2 = (ImageView) findViewById(R.id.baidu2);
		baidu3 = (ImageView) findViewById(R.id.baidu3);
		baidu4 = (ImageView) findViewById(R.id.baidu4);
		baidu5 = (ImageView) findViewById(R.id.baidu5);
		
		yuzhiwei = (RelativeLayout)findViewById(R.id.yuzhiwei);
		yuzhiwei_im = (ImageView)findViewById(R.id.yuzhiwei_im);
//		preset_pos_im = (RelativeLayout)findViewById(R.id.preset_pos_im);
//		setting_im = (RelativeLayout)findViewById(R.id.setting_im);
//		image_setting_im = (RelativeLayout)findViewById(R.id.image_setting_im);
//		sd_card_im = (RelativeLayout)findViewById(R.id.sd_card_im);
//		share_dev_im = (RelativeLayout)findViewById(R.id.share_dev_im);
//		image_im = (RelativeLayout)findViewById(R.id.image_im);
//		play_back_im = (RelativeLayout)findViewById(R.id.play_back_im);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_name.setText(mContact.contactName);
		layout_title=(RelativeLayout)findViewById(R.id.layout_title);
		iv_full_screen=(ImageView)findViewById(R.id.iv_full_screen);
		l_control=(LinearLayout)findViewById(R.id.l_control);
		line=(View)findViewById(R.id.line);
		rl_control=(RelativeLayout)findViewById(R.id.rl_control);
		iv_voice=(ImageView)findViewById(R.id.iv_voice);
		iv_speak=(ImageView)findViewById(R.id.iv_speak);
		iv_fanzhuan=(ImageView)findViewById(R.id.iv_fanzhuan);//@@
		iv_screenshot=(ImageView)findViewById(R.id.iv_screenshot);
		control_bottom=(RelativeLayout)findViewById(R.id.control_bottom);
		control_top=(LinearLayout)findViewById(R.id.control_top);
		video_mode_hd = (TextView) findViewById(R.id.video_mode_hd);
		video_mode_sd = (TextView) findViewById(R.id.video_mode_sd);
		video_mode_ld = (TextView) findViewById(R.id.video_mode_ld);
		vLineHD=findViewById(R.id.v_line_hd);
		choose_video_format=(Button)findViewById(R.id.choose_video_format);
		close_voice=(ImageView)findViewById(R.id.close_voice);
		send_voice=(ImageView)findViewById(R.id.send_voice);
		layout_voice_state=(LinearLayout)findViewById(R.id.layout_voice_state);
		iv_half_screen=(ImageView)findViewById(R.id.iv_half_screen);
		hungup=(ImageView)findViewById(R.id.hungup);
		screenshot=(ImageView)findViewById(R.id.screenshot);
		defence_state=(ImageView)findViewById(R.id.defence_state);
		open_door=(ImageView)findViewById(R.id.open_door);
		r_p2pview=(RelativeLayout)findViewById(R.id.r_p2pview);
	    voice_state=(ImageView)findViewById(R.id.voice_state);
	    l_device_list=(LinearLayout)findViewById(R.id.l_device_list);
	    setControlButtomHeight(0);
//	    initIpcDeviceList();
	    frushLayout(mContact.contactType);
		if(number>1){
//			tv_choosee_device.setVisibility(View.VISIBLE);
			sensorManager=(SensorManager) mContext.getSystemService(mContext.SENSOR_SERVICE);
			if(sensorManager!=null){
				sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
				sensorListener=new SensorEventListener() {
					
					@Override
					public void onSensorChanged(SensorEvent event) {
						// TODO Auto-generated method stub
						if(!isShake){
							// ���ڼ��ʱ��
							long currentUpdateTime = System.currentTimeMillis();
							// ���μ���ʱ����
							long timeInterval = currentUpdateTime - lastUpdateTime;
							if (timeInterval < UPTATE_INTERVAL_TIME)
								return;
							// ���ڵ�ʱ����lastʱ��
							lastUpdateTime = currentUpdateTime;
							
							// ���x,y,z����
							float x = event.values[0];
							float y = event.values[1];
							float z = event.values[2];

							// ���x,y,z�ı仯ֵ
							float deltaX = x - lastX;
							float deltaY = y - lastY;
							float deltaZ = z - lastZ;

							// �����ڵ�������last����
							lastX = x;
							lastY = y;
							lastZ = z;
							double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
									* deltaZ)
									/ timeInterval * 10000;
							// �ﵽ�ٶȷ�ֵ��������ʾ
							if (speed >= SPEED_SHRESHOLD) {
								isShake = true;
								vibrator.vibrate( new long[]{500,200,500,200}, -1);
								switchNext();
							}
						}
					}

					@Override
					public void onAccuracyChanged(Sensor sensor, int accuracy) {
						// TODO Auto-generated method stub
						
					}
					
				};
			}
			sensorManager.registerListener(sensorListener , sensor,SensorManager.SENSOR_DELAY_GAME);
		}
		// ˢ�¼��
		rlPrgTxError = (RelativeLayout) findViewById(R.id.rl_prgError);
		txError = (TextView) findViewById(R.id.tx_monitor_error);
		btnRefrash = (Button) findViewById(R.id.btn_refrash);
		progressBar = (ProgressBar) findViewById(R.id.prg_monitor);
		tx_wait_for_connect = (TextView) findViewById(R.id.tx_wait_for_connect);
		ivHeader = (HeaderView) findViewById(R.id.hv_header);
		rlPrgTxError.setOnClickListener(this);
		btnRefrash.setOnClickListener(this);
		// ����ͷ��
		setHeaderImage();
		
		preset_pos_binder_im.setOnClickListener(this);
		baidu1.setOnClickListener(this);
		baidu2.setOnClickListener(this);
		baidu3.setOnClickListener(this);
		baidu4.setOnClickListener(this);
		baidu5.setOnClickListener(this);
		yuzhiwei_im.setOnClickListener(this);
		preset_pos_im.setOnClickListener(this);
		setting_im.setOnClickListener(this);
		record_im.setOnClickListener(this);
		time_set_im.setOnClickListener(this);
		sd_card_im.setOnClickListener(this);
		share_dev_im.setOnClickListener(this);
		play_back_im.setOnClickListener(this);
		image_im.setOnClickListener(this);
		iv_full_screen.setOnClickListener(this);
		iv_voice.setOnClickListener(this);
		iv_screenshot.setOnClickListener(this);
		iv_fanzhuan.setOnClickListener(this);//@@
		choose_video_format.setOnClickListener(this);
		close_voice.setOnClickListener(this);
		send_voice.setOnClickListener(this);
		iv_half_screen.setOnClickListener(this);
		hungup.setOnClickListener(this);
		screenshot.setOnClickListener(this);
		video_mode_hd.setOnClickListener(this);
		video_mode_sd.setOnClickListener(this);
		video_mode_ld.setOnClickListener(this);
		defence_state.setOnClickListener(this);
		open_door.setOnClickListener(this);
		final AnimationDrawable anim = (AnimationDrawable) voice_state
				.getDrawable();
		OnPreDrawListener opdl = new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				anim.start();
				return true;
			}

		};
		voice_state.getViewTreeObserver().addOnPreDrawListener(opdl);
		if (mContact.contactType == P2PValue.DeviceType.NPC) {
			current_video_mode = P2PValue.VideoMode.VIDEO_MODE_LD;
		} else {
			current_video_mode = P2PConnect.getMode();
		}
		if (isSurpportOpenDoor == true) {
			open_door.setVisibility(ImageView.VISIBLE);
		} else {
			open_door.setVisibility(ImageView.GONE);
		}
		updateVideoModeText(current_video_mode);
		if (mContact.contactType != P2PValue.DeviceType.DOORBELL && !isSurpportOpenDoor) {
			iv_speak.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent event) {
					// TODO Auto-generated method stub
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							hideVideoFormat();
							layout_voice_state.setVisibility(RelativeLayout.VISIBLE);
						    iv_speak.setBackgroundResource(R.drawable.portrait_speak_p);
//						    T.showShort(mContext, R.string.hold_talk);
							setMute(false);
							return true;
						case MotionEvent.ACTION_UP:
							layout_voice_state
									.setVisibility(RelativeLayout.GONE);
							iv_speak
									.setBackgroundResource(R.drawable.portrait_speak);
							setMute(true);
							return true;
						}
						return false;
				}
			});
			send_voice.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View arg0, MotionEvent event) {
					// TODO Auto-generated method stub
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						hideVideoFormat();
						layout_voice_state
								.setVisibility(RelativeLayout.VISIBLE);
						
						send_voice
						.setBackgroundResource(R.drawable.ic_send_audio_p);
						setMute(false);
						return true;
					case MotionEvent.ACTION_UP:
						layout_voice_state
								.setVisibility(RelativeLayout.GONE);
						send_voice
						.setBackgroundResource(R.drawable.ic_send_audio);
						setMute(true);
						return true;
					}
					return false;
				}
			});
		} else if (mContact.contactType == P2PValue.DeviceType.DOORBELL
				&& !isSurpportOpenDoor) {
			isFirstMute=false;
			iv_speak.setOnClickListener(this);
			send_voice.setOnClickListener(this);
		} else if (isSurpportOpenDoor) {
			Log.e("leleTest", "isSurpportOpenDoor="+isSurpportOpenDoor);
			iv_speak.setOnClickListener(this);
			// ��ʼ���ʱû����������ʱ����
			send_voice.setOnClickListener(this);
			iv_speak.performClick();
			iv_speak.performClick();
//			speak();
//			speak();
//			send_voice.performClick();
			// speak();
		}
		initIpcDeviceList();
		mSocketUDPClient = SocketUDP.newInstance(Constants.SeverInfo.SERVER,
				Constants.SeverInfo.PORT);
		mSocketUDPClient.startAcceptMessage();
		try {
			net_speed_tv = (TextView) findViewById(R.id.net_speed_tv);
			Handler mHandler = new Handler() {
				@SuppressLint("HandlerLeak")
				@Override
				public void handleMessage(Message msg) {
					if (msg.what == 1) {
						String speedStr = msg.arg1 + "k/s";
						net_speed_tv.setText(speedStr);
					}
				}

			};
			speed = NetSpeed.getInstant(this, mHandler);
			speed.startCalculateNetSpeed();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void initSpeark(int deviceType,boolean isOpenDor){
		if(isOpenDor==true){
			open_door.setVisibility(View.VISIBLE);
		}else{
			open_door.setVisibility(View.GONE);
		}
		if (deviceType != P2PValue.DeviceType.DOORBELL && !isOpenDor) {
			iv_speak.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent event) {
					// TODO Auto-generated method stub
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							hideVideoFormat();
							layout_voice_state
									.setVisibility(RelativeLayout.VISIBLE);
						    hideVideoFormat();
							setMute(false);
							return true;
						case MotionEvent.ACTION_UP:
							layout_voice_state
									.setVisibility(RelativeLayout.GONE);
							iv_speak
									.setBackgroundResource(R.drawable.portrait_speak);
							setMute(true);
							return true;
						}
						return false;
				}
			});
			send_voice.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View arg0, MotionEvent event) {
					// TODO Auto-generated method stub
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						hideVideoFormat();
						layout_voice_state
								.setVisibility(RelativeLayout.VISIBLE);
						
						send_voice
						.setBackgroundResource(R.drawable.ic_send_audio_p);
						setMute(false);
						return true;
					case MotionEvent.ACTION_UP:
						layout_voice_state
								.setVisibility(RelativeLayout.GONE);
						send_voice
						.setBackgroundResource(R.drawable.ic_send_audio);
						setMute(true);
						return true;
					}
					return false;
				}
			});
		} else if (deviceType == P2PValue.DeviceType.DOORBELL
				&& !isOpenDor) {
			isFirstMute=false;
			iv_speak.setOnTouchListener(null);
			send_voice.setOnTouchListener(null);
			iv_speak.setOnClickListener(this);
			send_voice.setOnClickListener(this);
		} else if (isOpenDor) {
			iv_speak.setOnTouchListener(null);
			send_voice.setOnTouchListener(null);
			control_bottom.setVisibility(View.VISIBLE);
			iv_speak.setOnClickListener(this);
			// ��ʼ���ʱû����������ʱ����
			send_voice.setOnClickListener(this);
			isFirstMute=true;
			iv_speak.performClick();
			iv_speak.performClick();
			// speak();
		}
	}

	private void setHeaderImage() {
		ivHeader.updateImage(callId, true, 1);
	}
	/**
	 * ˢ��IPC��NPC������ͬ
	 */
	private void frushLayout(int contactType){
		if(contactType==P2PValue.DeviceType.IPC){
			video_mode_hd.setVisibility(View.VISIBLE);
			vLineHD.setVisibility(View.VISIBLE);
		}else if(contactType==P2PValue.DeviceType.NPC){
			video_mode_hd.setVisibility(View.GONE);
			vLineHD.setVisibility(View.GONE);
		}
	}

	private void regFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constants.P2P.P2P_ACCEPT);
		filter.addAction(Constants.P2P.P2P_READY);
		filter.addAction(Constants.P2P.P2P_REJECT);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		filter.addAction(Constants.P2P.ACK_RET_CHECK_PASSWORD);
		filter.addAction(Constants.P2P.RET_GET_REMOTE_DEFENCE);
		filter.addAction(Constants.P2P.RET_SET_REMOTE_DEFENCE);
		filter.addAction(Constants.P2P.P2P_RESOLUTION_CHANGE);
		filter.addAction(Constants.P2P.DELETE_BINDALARM_ID);
		filter.addAction(Constants.Action.MONITOR_NEWDEVICEALARMING);
		filter.addAction(Constants.P2P.RET_P2PDISPLAY);
		filter.addAction(Constants.P2P.ACK_GET_REMOTE_DEFENCE);
		filter.addAction(Constants.P2P.RET_PRESET_MOTORPOS_STATUS);
		filter.addAction(RET_GET_BUZZER);//@@
		filter.addAction(Constants.P2P.RET_GET_IMAGE_REVERSE);//@@
		mContext.registerReceiver(mReceiver, filter);
		isRegFilter = true;
	}
	BroadcastReceiver mReceiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context arg0, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals(Constants.P2P.RET_PRESET_MOTORPOS_STATUS)){
				int resultStr = intent.getExtras().getInt("result");
				switch (resultStr) {
				case 0:
					Toast.makeText(mContext, R.string.successful_operation, 1).show();
					break;
				case 84:
					Toast.makeText(mContext,R.string.have_not_setting_item, 1).show();				
					break;
				case 255:
					Toast.makeText(mContext, R.string.this_device_dont_support_preset_position, 1).show();
					break;
				default:
					break;
				}
			}
			if(intent.getAction().equals(Constants.P2P.P2P_READY)){
				 Log.e("monitor", "P2P_READY"+"callId="+callId);
			     P2PHandler.getInstance().getDefenceStates(callId, password);
				 isReceveHeader=false;
				 isShake=false;
				 P2PConnect.setMonitorId(callId);
				 SettingListener.setMonitorID(callId);
			}else if(intent.getAction().equals(Constants.P2P.P2P_ACCEPT)){
				int[] type = intent.getIntArrayExtra("type");
				P2PView.type = type[0];
				P2PView.scale = type[1];
				int Heigh = 0;
				if(P2PView.type==1&&P2PView.scale==0){
					Heigh=screenWidth*3/4;
					setIsLand(true);
				}else{
					Heigh=screenWidth*9/16;
					setIsLand(false);
				}
				if(ScrrenOrientation ==Configuration.ORIENTATION_PORTRAIT){
					LinearLayout.LayoutParams parames=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
					parames.height=Heigh;
					r_p2pview.setLayoutParams(parames);
				}
			}else if(intent.getAction().equals(Constants.P2P.ACK_RET_CHECK_PASSWORD)){
				finish();
			} else if (intent.getAction().equals(Constants.P2P.P2P_REJECT)) {
				String error = intent.getStringExtra("error");
				int code=intent.getIntExtra("code", 9);
				showError(error,code);
				 isShake=false;
			} else if (intent.getAction().equals(
					Constants.P2P.RET_GET_REMOTE_DEFENCE)) {
				String ids = intent.getStringExtra("contactId");
				if (!ids.equals("") && ids.equals(callId)) {
					defenceState = intent.getIntExtra("state", -1);
					changeDefence(defenceState);
				}
				//defence_state.setVisibility(ImageView.VISIBLE);
			}else if(intent.getAction().equals(Constants.P2P.RET_SET_REMOTE_DEFENCE)){
				int result = intent.getIntExtra("state", -1);
				if (result == 0) {
					if (defenceState == Constants.DefenceState.DEFENCE_STATE_ON) {
						defenceState = Constants.DefenceState.DEFENCE_STATE_OFF;
					} else {
						defenceState = Constants.DefenceState.DEFENCE_STATE_ON;
					}
					changeDefence(defenceState);
				}
			} else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				String error = intent.getStringExtra("error");
				showError(error,0);
			}else if (intent.getAction().equals(
					Constants.Action.MONITOR_NEWDEVICEALARMING)) {
				
			}else if(intent.getAction().equals(Constants.P2P.RET_P2PDISPLAY)){
				Log.e("monitor", "RET_P2PDISPLAY");
				connectSenconde=true;
				if(!isReceveHeader){
					hindRlProTxError();
					 pView.updateScreenOrientation();
//					 iv_full_screen.setVisibility(View.VISIBLE);
					 isReceveHeader=true;
				}
			} else if (intent.getAction().equals(
					Constants.P2P.DELETE_BINDALARM_ID)) {
				int result = intent.getIntExtra("deleteResult", 1);
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
				if (result == 0) {
					// ɾ���ɹ�
					Toast.makeText(mContext, R.string.change_success, Toast.LENGTH_SHORT).show();
				} else if (result == -1) {
					// ��֧��
					Toast.makeText(mContext, R.string.the_device_dont_support_the_operation, Toast.LENGTH_SHORT).show();
				} else {
					// ʧ��
				}
			}else if(intent.getAction().equals(Constants.P2P.ACK_GET_REMOTE_DEFENCE)){
				String contactId=intent.getStringExtra("contactId");
				int result=intent.getIntExtra("result", -1);
				if(contactId.equals(callId)){
					if(result == Constants.P2P_SET.ACK_RESULT.ACK_INSUFFICIENT_PERMISSIONS){
						isPermission=false;
					}
				}
				
			}else if (intent.getAction().equals(RET_GET_BUZZER)) {
				int state = intent.getIntExtra("buzzerState", -1);
				if(state==1||state==2||state==3){
					yinliang.setBackground(getResources().getDrawable(R.drawable.yinliang_kai));
				}else{
					yinliang.setBackground(getResources().getDrawable(R.drawable.yinliang_jingyin));
				}//@@
			}else if(intent.getAction().equals(Constants.P2P.RET_GET_IMAGE_REVERSE)){
				 int type=intent.getIntExtra("type",-1);
				 if(type==0){
					 
					 isOpenReverse=true;
				 }else if(type==1){
					 
					 isOpenReverse=false;
				 }
			}//@@
		}
	};
	public void changeDefence(int defencestate){
		if (defencestate == Constants.DefenceState.DEFENCE_STATE_ON) {
			defence_state.setImageResource(R.drawable.deployment);
		} else {
			defence_state.setImageResource(R.drawable.disarm);
		}
	}

	/**
	 * ���ع���ҳ
	 */
	private void hindRlProTxError() {
		rlPrgTxError.setVisibility(View.GONE);
	}
	@SuppressLint("NewApi")
	private void showRlProTxError() {
		ObjectAnimator anima = ObjectAnimator.ofFloat(rlPrgTxError, "alpha",
				0f, 1.0f);
		rlPrgTxError.setVisibility(View.VISIBLE);
		anima.setDuration(500).start();
		anima.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationEnd(Animator animation) {

			}

			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
	}

	// ���ò���
    public void setDefence() {
    	if(!isPermission){
    		Toast.makeText(mContext, R.string.insufficient_permissions, Toast.LENGTH_SHORT).show();
    		return;
    	}
        if (defenceState == Constants.DefenceState.DEFENCE_STATE_ON) {
                P2PHandler.getInstance().setRemoteDefence(mContact.getContactId(), password,
                 Constants.P2P_SET.REMOTE_DEFENCE_SET.ALARM_SWITCH_OFF);

        } else if (defenceState == Constants.DefenceState.DEFENCE_STATE_OFF) {
                P2PHandler.getInstance().setRemoteDefence(mContact.getContactId(), password,
                Constants.P2P_SET.REMOTE_DEFENCE_SET.ALARM_SWITCH_ON);
        }

    }
	public void callDevice(){
		P2PConnect.setCurrent_state(P2PConnect.P2P_STATE_CALLING);
	    P2PConnect.setCurrent_call_id(callId);   
		String push_mesg = NpcCommon.mThreeNum
				+ ":"
				+ mContext.getResources().getString(
						R.string.p2p_call_push_mesg);
		Log.e("dxsTest","NpcCommon.mThreeNum-->"+NpcCommon.mThreeNum+"mContact.contactId-->"+mContact.contactId+"connectType-->"+connectType+"AppConfig.VideoMode-->"+AppConfig.VideoMode);
		if(connectType==Constants.ConnectType.RTSPCONNECT){
			callType=3;
			String ipAddress="";
			String ipFlag="";
			if( mContact.ipadressAddress!=null){
				ipAddress= mContact.ipadressAddress.getHostAddress();		
				ipFlag=ipAddress.substring(ipAddress.lastIndexOf(".") + 1, ipAddress.length());
			}else{
				
			}
			P2PHandler.getInstance().call(NpcCommon.mThreeNum, "0", true,Constants.P2P_TYPE.P2P_TYPE_MONITOR,"1", "1", push_mesg,AppConfig.VideoMode,mContact.contactId);
//			P2PHandler.getInstance().RTSPConnect(NpcCommon.mThreeNum, mContact.contactPassword, true, callType, mContact.contactId, ipFlag, push_mesg, ipAddress,AppConfig.VideoMode,rtspHandler);	
		}else if(connectType==Constants.ConnectType.P2PCONNECT){
			 callType=1;
	         P2PHandler.getInstance().call(NpcCommon.mThreeNum, password, true,Constants.P2P_TYPE.P2P_TYPE_MONITOR,callId, null, push_mesg,AppConfig.VideoMode,mContact.contactId);
		}
	}
  Handler rtspHandler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Log.e("dxswifi", "rtspʧ��");
				showError("connect error",0);
				P2PHandler.getInstance().reject();
				break;
			case 1:
				Log.e("dxswifi", "rtsp�ɹ�");
				rlPrgTxError.setVisibility(View.GONE);
				P2PConnect.setCurrent_state(2);
			    playReady();
				mContact.apModeState=Constants.APmodeState.LINK;
				break;
			}
		}
	};
	private void playReady() {
//		P2PHandler.getInstance().openAudioAndStartPlaying(callType);
		Intent ready = new Intent();
		ready.setAction(Constants.P2P.P2P_READY);
		this.sendBroadcast(ready);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
			ScrrenOrientation = Configuration.ORIENTATION_LANDSCAPE;
			layout_title.setVisibility(View.GONE);
			l_control.setVisibility(View.GONE);
			line.setVisibility(View.GONE);
			rl_control.setVisibility(View.GONE);
			iv_full_screen.setVisibility(View.GONE);
			iv_voice.setVisibility(View.GONE);
			iv_screenshot.setVisibility(View.GONE);
			iv_fanzhuan.setVisibility(View.GONE);//@@
//			����control_bottom�ĸ߶�
			int height=(int) getResources().getDimension(R.dimen.p2p_monitor_bar_height);
			setControlButtomHeight(height);
			control_bottom.setVisibility(View.VISIBLE);
			yuzhiwei.setVisibility(View.GONE);
			yuzhiweiFlag = false;
//			setIsLand(true);
			pView.fullScreen();
			isFullScreen=true;
			LinearLayout.LayoutParams parames=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			r_p2pview.setLayoutParams(parames);
		}else{
			ScrrenOrientation = Configuration.ORIENTATION_PORTRAIT;
			layout_title.setVisibility(View.VISIBLE);
			l_control.setVisibility(View.VISIBLE);
			line.setVisibility(View.VISIBLE);
			rl_control.setVisibility(View.VISIBLE);
			iv_full_screen.setVisibility(View.VISIBLE);
			iv_voice.setVisibility(View.VISIBLE);
			iv_screenshot.setVisibility(View.VISIBLE);
			iv_fanzhuan.setVisibility(View.VISIBLE);//@@
//			����control_bottom�ĸ߶ȵ���0
			setControlButtomHeight(0);
			control_bottom.setVisibility(View.INVISIBLE);
			yuzhiwei.setVisibility(View.GONE);
			yuzhiweiFlag = false;
			control_top.setVisibility(View.GONE);
//			setIsLand(false);
			if (isFullScreen) {
	             isFullScreen = false;
	             pView.halfScreen();
	             Log.e("half", "half screen--");
	        }
			if(P2PView.type==1){
				if(P2PView.scale==0){
					 int Heigh=screenWidth*3/4;
					 LinearLayout.LayoutParams parames=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
					 parames.height=Heigh;
					 r_p2pview.setLayoutParams(parames);	
				}else{
					int Heigh=screenWidth*9/16;
					 LinearLayout.LayoutParams parames=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
					 parames.height=Heigh;
					 r_p2pview.setLayoutParams(parames);	
				}
			}else{
				if(mContact.contactType==P2PValue.DeviceType.NPC){
					 int Heigh=screenWidth*3/4;
					 LinearLayout.LayoutParams parames=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
					 parames.height=Heigh;
					 r_p2pview.setLayoutParams(parames);	
				}else{
					int Heigh=screenWidth*9/16;
					 LinearLayout.LayoutParams parames=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
					 parames.height=Heigh;
					 r_p2pview.setLayoutParams(parames);	
				}
			}
		}
	}
	public void updateVideoModeText(int mode) {
		if (mode == P2PValue.VideoMode.VIDEO_MODE_HD) {
			video_mode_hd.setTextColor(mContext.getResources().getColor(
					R.color.text_color_blue));
			video_mode_sd.setTextColor(mContext.getResources().getColor(
					R.color.text_color_white));
			video_mode_ld.setTextColor(mContext.getResources().getColor(
					R.color.text_color_white));
			choose_video_format.setText(R.string.video_mode_hd);
		} else if (mode == P2PValue.VideoMode.VIDEO_MODE_SD) {
			video_mode_hd.setTextColor(mContext.getResources().getColor(
					R.color.text_color_white));
			video_mode_sd.setTextColor(mContext.getResources().getColor(
					R.color.text_color_blue));
			video_mode_ld.setTextColor(mContext.getResources().getColor(
					R.color.text_color_white));
			choose_video_format.setText(R.string.video_mode_sd);
		} else if (mode == P2PValue.VideoMode.VIDEO_MODE_LD) {
			video_mode_hd.setTextColor(mContext.getResources().getColor(
					R.color.text_color_white));
			video_mode_sd.setTextColor(mContext.getResources().getColor(
					R.color.text_color_white));
			video_mode_ld.setTextColor(mContext.getResources().getColor(
					R.color.text_color_blue));
			choose_video_format.setText(R.string.video_mode_ld);
		}
	}
	@Override
	protected void onP2PViewSingleTap() {
		// TODO Auto-generated method stub
		changeControl();
	}

	@Override
	protected void onCaptureScreenResult(boolean isSuccess, int prePoint) {
		// TODO Auto-generated method stub
		if (isSuccess) {
			// Capture success
			Toast.makeText(mContext,R.string.screenshot_success, Toast.LENGTH_SHORT).show();
			List<String> pictrues=Utils.getScreenShotImagePath(callId, 1);
			if(pictrues.size()<=0){
				return;
			}
            Utils.saveImgToGallery(pictrues.get(0));
		} else {
			Toast.makeText(mContext, R.string.screenshot_failed, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public int getActivityInfo() {
		// TODO Auto-generated method stub
		return Constants.ActivityInfo.ACTIVITY_APMONITORACTIVITY;
	}

	@Override
	protected void onGoBack() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onGoFront() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onExit() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBackPressed() {
		reject();
		super.onBackPressed();
	}
	@Override
	public void onDestroy() {
		if (mAudioManager != null) {
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
					mCurrentVolume, 0);
		}
		if (isRegFilter) {
	         mContext.unregisterReceiver(mReceiver);
	            isRegFilter = false;
	    }
		P2PConnect.setPlaying(false);
		P2PConnect.setMonitorId("");// �����ڼ�ص�IDΪ��
		SettingListener.setMonitorID("");
		if (sensorListener != null) {
			sensorManager.unregisterListener(sensorListener);
		}
		if (!activity_stack
				.containsKey(Constants.ActivityInfo.ACTIVITY_MAINACTIVITY)) {
		}
		Intent refreshContans = new Intent();
		refreshContans.setAction(Constants.Action.REFRESH_CONTANTS);
		mContext.sendBroadcast(refreshContans);
		speed.stopCalculateNetSpeed();
		SharedPreferencesManager.getInstance()
				.putData(mContext,
				Constants.WatchAction.IF_WATCH,
				"no");
		super.onDestroy();
	}
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP) {
			mCurrentVolume++;
			if (mCurrentVolume > mMaxVolume) {
				mCurrentVolume = mMaxVolume;
			}

			if (mCurrentVolume != 0) {
				mIsCloseVoice = false;
				iv_voice.setImageResource(R.drawable.selector_half_screen_voice_open);
				close_voice.setBackgroundResource(R.drawable.half_screen_voice_open);
			}
			return false;
		} else if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
			mCurrentVolume--;
			if (mCurrentVolume < 0) {
				mCurrentVolume = 0;
			}

			if (mCurrentVolume == 0) {
				mIsCloseVoice = true;
				iv_voice.setImageResource(R.drawable.selector_half_screen_voice_close);
				close_voice.setBackgroundResource(R.drawable.half_screen_voice_close);
			}

			return false;
		}

		return super.dispatchKeyEvent(event);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.yinliang_im://���÷�������ť����¼�@@
			Intent setBuzzer = new Intent(mContext,SetVideoBuzzerActivity.class);
			setBuzzer.putExtra("contact", mContact);
			startActivity(setBuzzer);
			reject();//@@
			break;
		case R.id.preset_pos_binder_im:
			Intent i14 = new Intent(mContext,BinderPresetPosActivity.class);
			i14.putExtra("contact", mContact);
			startActivity(i14);
			reject();
			break;
		case R.id.yuzhiwei_im://��������Ԥ��λ
			if(yuzhiweiFlag==false){
				yuzhiweiFlag=true;
				yuzhiwei.setVisibility(View.VISIBLE);
				
			}else{
				yuzhiwei.setVisibility(View.GONE);
				yuzhiweiFlag=false;
			}
			break;
		case R.id.baidu1:
			byte bPresetNum = 0;
			yzwDialog(bPresetNum);
			break;
		case R.id.baidu2:
			byte bPresetNum1 = 1;
			yzwDialog(bPresetNum1);
			break;
		case R.id.baidu3:
			byte bPresetNum2 = 2;
			yzwDialog(bPresetNum2);
			break;
		case R.id.baidu4:
			byte bPresetNum3 = 3;
			yzwDialog(bPresetNum3);
			break;
		case R.id.baidu5:
			byte bPresetNum4 = 4;
			yzwDialog(bPresetNum4);
			break;
		case R.id.preset_pos_im://��������Ԥ��λ
			if(yuzhiweiFlag==false){
				iv_full_screen.setVisibility(View.GONE);
				iv_voice.setVisibility(View.GONE);
				iv_screenshot.setVisibility(View.GONE);
				iv_fanzhuan.setVisibility(View.GONE);//@@
				yuzhiweiFlag=true;
				yuzhiwei.setVisibility(View.VISIBLE);
			}else{
				iv_full_screen.setVisibility(View.VISIBLE);
				iv_voice.setVisibility(View.VISIBLE);
				iv_screenshot.setVisibility(View.VISIBLE);
				iv_fanzhuan.setVisibility(View.VISIBLE);//@@
				yuzhiwei.setVisibility(View.GONE);
				yuzhiweiFlag=false;
			}
			break;
		case R.id.setting_im://�޸����ƺ�����
			Intent i13 = new Intent(mContext,ModifyCameraInfoActivity.class);
			i13.putExtra("contact", mContact);
			startActivity(i13);
			reject();
			break;
		case R.id.record_im://��Ƶ¼��
			Intent i11 = new Intent(mContext,RecordProjectActivity.class);
			i11.putExtra("contact", mContact);
			startActivity(i11);
			reject();
			break;
		case R.id.time_set_im://ʱ������@@
//			Intent intent_time = new Intent(mContext,RecordProjectActivity.class);
//			intent_time.putExtra("contact", mContact);
//			startActivity(intent_time);
//			reject();
			break;
		case R.id.sd_card_im://sd����Ϣ
			Intent i12 = new Intent(mContext,SDCardActivity.class);
			i12.putExtra("contact", mContact);
			startActivity(i12);
			reject();
			break;
		case R.id.play_back_im://�ط�
			Intent i1 = new Intent(mContext,PlayBackListActivity.class);
			i1.putExtra("contact", mContact);
			startActivity(i1);
			reject();
			break;
		case R.id.share_dev_im://����
			if(ifshare==1){
				Toast.makeText(mContext,R.string.shared_dev_can_not_share, Toast.LENGTH_LONG).show();
				break;
			}//@@
			View shareView = LayoutInflater.from(mContext).inflate(
					R.layout.modify_dev_name_dialog, null);
			TextView shareView_cancle_modify = (TextView) shareView
					.findViewById(R.id.cancle_modify);
			TextView shareView_confire_modify = (TextView) shareView
					.findViewById(R.id.confire_modify);
			final EditText shareView_modify_named = (EditText) shareView
					.findViewById(R.id.modify_named);
			shareView_modify_named.setHint(R.string.input_other_side_account);
			AlertDialog.Builder shareView_builder = new AlertDialog.Builder(
					mContext);
			modifyDialog = shareView_builder.create();
			modifyDialog.show();
			modifyDialog.setContentView(shareView);
			modifyDialog.getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
			shareView_cancle_modify.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					modifyDialog.dismiss();
				}
			});
			shareView_confire_modify.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String userPhone= SharedPreferencesManager.getInstance().getData(
							mContext,
							"userPhone");
					String userEmail= SharedPreferencesManager.getInstance().getData(
							mContext,
							"userEmail");
					String userNum = SharedPreferencesManager.getInstance().getData(
							mContext,
							Constants.UserInfo.USER_NUMBER);
					modifyDialog.dismiss();
					String toUserNum = shareView_modify_named.getText()
							.toString().trim();
					if (toUserNum != null && toUserNum.length() > 0&&!userNum.equals(toUserNum)&&!toUserNum.equals(userPhone)&&!toUserNum.equals(userEmail)) {
						mSocketUDPClient.sendMsg(SendServerOrder.ShareDev(mContact.getContactId(),
								fromUserNum, toUserNum));
					} else {
						Toast.makeText(mContext,R.string.input_account_fail_input_again, Toast.LENGTH_SHORT).show();
					}
				}
			});
			shareView_modify_named.setFocusable(true);
			break;
		case R.id.image_im:
			Intent i = new Intent(mContext,PrintScreenActivity.class);
			i.putExtra("contact", mContact);
			startActivity(i);
			reject();
			break;
		case R.id.iv_full_screen:
			ScrrenOrientation = Configuration.ORIENTATION_LANDSCAPE;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			break;
		case R.id.defence_state:
			setDefence();
			break;
		case R.id.close_voice:
		case R.id.iv_voice:
			if (mIsCloseVoice) {
				mIsCloseVoice = false;
				iv_voice.setImageResource(R.drawable.selector_half_screen_voice_open);
				close_voice.setBackgroundResource(R.drawable.half_screen_voice_open);
				if (mCurrentVolume == 0) {
					mCurrentVolume = 1;
				}
				if (mAudioManager != null) {
					mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
							mCurrentVolume, 0);
				}
			} else {
				mIsCloseVoice = true;
				iv_voice.setImageResource(R.drawable.selector_half_screen_voice_close);
				close_voice.setBackgroundResource(R.drawable.half_screen_voice_close);
				if (mAudioManager != null) {
					mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0,
							0);
				}
			}
			break;
		case R.id.screenshot:
		case R.id.iv_screenshot:
			this.captureScreen(-1);
			break;
		case R.id.iv_fanzhuan://��Ƶ��ת@@
			if(isOpenReverse==true){
				P2PHandler.getInstance().setImageReverse(mContact.contactId, mContact.contactPassword, 1);
			}else{
				P2PHandler.getInstance().setImageReverse(mContact.contactId, mContact.contactPassword, 0);
			}
			P2PHandler.getInstance().getNpcSettings(mContact.contactId, mContact.contactPassword);
			break;
		case R.id.hungup:
			reject();
			break;
		case R.id.choose_video_format:
			changevideoformat();
			break;
		case R.id.iv_half_screen:
			control_bottom.setVisibility(View.INVISIBLE);
			 ScrrenOrientation = Configuration.ORIENTATION_PORTRAIT;
              setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			break;
		case R.id.video_mode_hd:
			if (current_video_mode != P2PValue.VideoMode.VIDEO_MODE_HD) {
				current_video_mode = P2PValue.VideoMode.VIDEO_MODE_HD;
				P2PHandler.getInstance().setVideoMode(
						P2PValue.VideoMode.VIDEO_MODE_HD);
				updateVideoModeText(current_video_mode);
			}
			hideVideoFormat();
			break;
		case R.id.video_mode_sd:
			if (current_video_mode != P2PValue.VideoMode.VIDEO_MODE_SD) {
				current_video_mode = P2PValue.VideoMode.VIDEO_MODE_SD;
				P2PHandler.getInstance().setVideoMode(
						P2PValue.VideoMode.VIDEO_MODE_SD);
				updateVideoModeText(current_video_mode);
			}
			hideVideoFormat();
			break;
		case R.id.video_mode_ld:
			if (current_video_mode != P2PValue.VideoMode.VIDEO_MODE_LD) {
				current_video_mode = P2PValue.VideoMode.VIDEO_MODE_LD;
				P2PHandler.getInstance().setVideoMode(
						P2PValue.VideoMode.VIDEO_MODE_LD);
				updateVideoModeText(current_video_mode);
			}
			hideVideoFormat();
			break;
		case R.id.rl_prgError:
		case R.id.btn_refrash:
			if (btnRefrash.getVisibility() == View.VISIBLE) {
				hideError();
				callDevice();
			}
			break;
		case R.id.open_door:
			openDor();
			break;
		case R.id.iv_speak:
		case R.id.send_voice:
			if (!isSpeak) {
				speak();
			} else {
				noSpeak();
			}
		    break;
		default:
			break;
		}
	}
	
	// ����/�鿴Ԥ��λλ�÷�Χ�� 0 ~ 4
		private void yzwDialog(final byte bPresetNum){
			LayoutInflater layoutInflater = LayoutInflater.from(mContext);   
	        View myLoginView = layoutInflater.inflate(R.layout.dialog_yuzhiwei, null);  
	        szyzw = (Button) myLoginView.findViewById(R.id.szyzw_btn);
	        ckyzw = (Button) myLoginView.findViewById(R.id.ckyzw_btn);
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
	        mDialog = alertDialog.create();
	        mDialog.show();
	        mDialog.setContentView(myLoginView);
	        mDialog.getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
	        //����Ԥ��λ
	        szyzw.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					byte[] data = new byte[20];
					data[0] = 87;
					data[1] = 0;
					data[2] = 1;
					data[3] = bPresetNum;
					P2PHandler.getInstance().sMesgPresetMotorPos(callId, password, data);
					mDialog.dismiss();
				}
			});
	        //�鿴Ԥ��λ
	        ckyzw.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					byte[] data = new byte[20];
					data[0] = 87;
					data[1] = 0;
					data[2] = 0;
					data[3] = bPresetNum;
					P2PHandler.getInstance().sMesgPresetMotorPos(callId, password, data);
					mDialog.dismiss();
				}
			});
	       
		}
	
	// ���óɶԻ�״̬
		private void speak() {
			hideVideoFormat();
			layout_voice_state.setVisibility(RelativeLayout.VISIBLE);
			send_voice.setBackgroundResource(R.drawable.ic_send_audio_p);
			iv_speak.setBackgroundResource(R.drawable.portrait_speak_p);
			setMute(false);
			isSpeak = true;
			Log.e("leleSpeak", "speak--"+isSpeak);
		}
		private void noSpeak(){
			send_voice.setBackgroundResource(R.drawable.ic_send_audio);
			iv_speak.setBackgroundResource(R.drawable.portrait_speak);
			layout_voice_state.setVisibility(RelativeLayout.GONE);
			setMute(true);
			isSpeak = false;
			mhandler.postDelayed(mrunnable, 500);
			Log.e("leleSpeak", "no speak--"+isSpeak);
		}
		private boolean isFirstMute = true;
		Runnable mrunnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (isFirstMute) {
					Log.e("leleSpeak", "mrunnable--");
					send_voice.performClick();
					isFirstMute = false;
				}
			}
		};
		public void stopSpeak(){
			send_voice.setBackgroundResource(R.drawable.ic_send_audio);
			iv_speak.setBackgroundResource(R.drawable.portrait_speak);
			layout_voice_state.setVisibility(RelativeLayout.GONE);
			setMute(true);
			isSpeak = false;
		}
	/**
	 * ����
	 */
	private void openDor() {
		NormalDialog dialog = new NormalDialog(mContext, mContext
				.getResources().getString(R.string.open_door), mContext
				.getResources().getString(R.string.confirm_open_door), mContext
				.getResources().getString(R.string.yes), mContext
				.getResources().getString(R.string.no));
		dialog.setOnButtonOkListener(new NormalDialog.OnButtonOkListener() {

			@Override
			public void onClick() {
				if(isCustomCmdAlarm==true){
					String cmd = "IPC1anerfa:unlock";
					P2PHandler.getInstance().sendCustomCmd(callId, password, cmd);
				}else{
					P2PHandler.getInstance().setGPIO1_0(callId, password);
				}
			}
		});
		dialog.showDialog();
	}
	Handler sHandler=new Handler(){
		public void handleMessage(Message msg) {
			switchConnect();
		};
	};

	/**
	 * չʾ���Ӵ���
	 * 
	 * @param error
	 */
	public void showError(String error,int code) {
		if(!connectSenconde&&code!=9){
			callDevice();
			connectSenconde=true;
			return;
		}
		progressBar.setVisibility(View.GONE);
		tx_wait_for_connect.setVisibility(View.GONE);
		txError.setVisibility(View.VISIBLE);
		btnRefrash.setVisibility(View.VISIBLE);
		txError.setText(error);
	}

	/**
	 * �������Ӵ���
	 */
	private void hideError() {
		progressBar.setVisibility(View.VISIBLE);
		tx_wait_for_connect.setText(getResources().getString(R.string.waite_for_linke));
		tx_wait_for_connect.setVisibility(View.VISIBLE);
		txError.setVisibility(View.GONE);
		btnRefrash.setVisibility(View.GONE);
	}
	/**
	 * �л�����
	 */
	private void switchConnect(){
		progressBar.setVisibility(View.VISIBLE);
		tx_wait_for_connect.setText(getResources().getString(R.string.switch_connect));
		tx_wait_for_connect.setVisibility(View.VISIBLE);
		txError.setVisibility(View.GONE);
		btnRefrash.setVisibility(View.GONE);
//		iv_full_screen.setVisibility(View.INVISIBLE);
		showRlProTxError();
		Log.e("switchConnect", "switchConnect");
	}

	public void reject() {
		if (!isReject) {
			isReject = true;
			P2PHandler.getInstance().reject();
			disconnectDooranerfa();
			finish();
		}
	} 
	public void readyCallDevice() {
        if(connectType == Constants.ConnectType.P2PCONNECT){
            P2PHandler.getInstance().openAudioAndStartPlaying(1);
            P2PHandler.getInstance().getDefenceStates(callId, password);
        }else{
            P2PHandler.getInstance().openAudioAndStartPlaying(1);
            callId="1";
            password="0";
            P2PHandler.getInstance().getDefenceStates(callId, password);
        }
        
    }
	private long exitTime = 0;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(mContext, R.string.press_again_exit_monitor, Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				reject();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	public void changevideoformat() {
		if (control_top.getVisibility() == RelativeLayout.VISIBLE) {
			Animation anim2 = AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out);
			anim2.setDuration(100);
			control_top.startAnimation(anim2);
			control_top.setVisibility(RelativeLayout.GONE);
			isShowVideo = false;
		} else {
			Animation anim2 = AnimationUtils.loadAnimation(this,
					android.R.anim.fade_in);
			anim2.setDuration(100);
			control_top.setVisibility(RelativeLayout.VISIBLE);
			control_top.startAnimation(anim2);
			isShowVideo = true;
		}
	}
	public void hideVideoFormat() {
		if (control_top.getVisibility() == RelativeLayout.VISIBLE) {
			Animation anim2 = AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out);
			anim2.setDuration(100);
			control_top.startAnimation(anim2);
			control_top.setVisibility(RelativeLayout.GONE);
			isShowVideo = false;
		}
	}
	public void changeControl() {
		if (isSpeak) {// �Խ������в�����ʧ
			return;
		}
		if(ScrrenOrientation ==Configuration.ORIENTATION_PORTRAIT){
			return;
		}
		Log.e("changeControl", "changeControl");
		if (control_bottom.getVisibility() == RelativeLayout.VISIBLE) {
			Log.e("changeControl", "changeControl--VISIBLE");
			Animation anim2 = AnimationUtils.loadAnimation(this,
					android.R.anim.fade_out);
			anim2.setDuration(100);
			control_bottom.startAnimation(anim2);
			anim2.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation arg0) {
					// TODO Auto-generated method stub
					hideVideoFormat();
					choose_video_format.setClickable(false);
				}

				@Override
				public void onAnimationRepeat(Animation arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation arg0) {
					// TODO Auto-generated method stub
					hideVideoFormat();
					control_bottom.setVisibility(RelativeLayout.GONE);
					yuzhiwei.setVisibility(View.GONE);
					yuzhiweiFlag = false;
					choose_video_format
							.setBackgroundResource(R.drawable.sd_backgroud);
					choose_video_format.setClickable(true);
				}
			});

		} else {
			Log.e("changeControl", "changeControl--INVISIBLE");
			control_bottom.setVisibility(RelativeLayout.VISIBLE);
			control_bottom.bringToFront();
			Animation anim2 = AnimationUtils.loadAnimation(this,
					android.R.anim.fade_in);
			anim2.setDuration(100);
			control_bottom.startAnimation(anim2);
			anim2.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation arg0) {
					// TODO Auto-generated method stub
					hideVideoFormat();
					choose_video_format.setClickable(false);
				}

				@Override
				public void onAnimationRepeat(Animation arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation arg0) {
					// TODO Auto-generated method stub
					hideVideoFormat();
					choose_video_format.setClickable(true);
				}
			});
		}
	}
	/**
	 * �±�����Ϣ
	 */
	NormalDialog dialog;
	String contactidTemp = "";
	
	private Dialog passworddialog;
	void createPassDialog(String id) {
		passworddialog = new MyInputPassDialog(mContext,Utils.getStringByResouceID(R.string.check), id, listener);
		passworddialog.show();
	}
	private OnCustomDialogListener listener = new OnCustomDialogListener() {

		@Override
		public void check(final String password, final String id) {
			if (password.trim().equals("")) {
				Toast.makeText(mContext, R.string.please_input_monitor_psw, Toast.LENGTH_SHORT).show();
				return;
			}

			if (password.length() > 30||password.charAt(0)=='0') {
				Toast.makeText(mContext, R.string.device_psw_invalid, Toast.LENGTH_SHORT).show();
				return;
			}

			P2PConnect.vReject(9,"");
			new Thread() {
				@Override
				public void run() {
					while (true) {
						if (P2PConnect.getCurrent_state() == P2PConnect.P2P_STATE_NONE) {
							Message msg = new Message();
							String pwd=P2PHandler.getInstance().EntryPassword(password);
							String[] data = new String[] { id,pwd,
									String.valueOf(pushAlarmType) };
							msg.what=1;
							msg.obj = data;
							handler.sendMessage(msg);
							break;
						}
						Utils.sleepThread(500);
					}
				}
			}.start();

		}
	};
	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if(msg.what==0){
				Contact contact=(Contact) msg.obj;
				Intent monitor=new Intent(mContext,ApMonitorActivity.class);
				monitor.putExtra("contact", contact);
				monitor.putExtra("connectType", Constants.ConnectType.P2PCONNECT);
				startActivity(monitor);

			}else if(msg.what==1){
				if (passworddialog != null && passworddialog.isShowing()) {
					passworddialog.dismiss();
				}
				String[] data = (String[]) msg.obj;
				P2PHandler.getInstance().reject();
				switchConnect();
				callId=data[0];
				password=data[1];
				if(isSpeak){
				    stopSpeak();
				}
				setHeaderImage();
				if(pushAlarmType==P2PValue.AlarmType.ALARM_TYPE_DOORBELL_PUSH){
					initSpeark(P2PValue.DeviceType.DOORBELL, true);
					Log.e("leleMonitor", "switch doorbell push");
				}else{
					initSpeark(P2PValue.DeviceType.IPC, false);
					Log.e("leleMonitor", "switch---");
				}
				connectDooranerfa();
				callDevice();
				frushLayout(P2PValue.DeviceType.IPC);

			}
			return false;
		}
	});
	@Override
	public void onHomePressed() {
		// TODO Auto-generated method stub
		super.onHomePressed();
		reject();
	}
	public void getScreenWithHeigh(){
	   DisplayMetrics dm = new DisplayMetrics();
		 //��ȡ��Ļ��Ϣ
	   getWindowManager().getDefaultDisplay().getMetrics(dm);
       screenWidth = dm.widthPixels;
       screenHeigh = dm.heightPixels;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		readyCallDevice();
		initp2pView();
	}
	 /*
     * ��ʼ��P2pview
     */
    public void initp2pView() {
        this.initP2PView(mContact.contactType);
//        DisplayMetrics dm = new DisplayMetrics();
//	    getWindowManager().getDefaultDisplay().getMetrics(dm);
//	    window_width = dm.widthPixels;
//	    window_height = dm.heightPixels;
        WindowManager manager = getWindowManager();
        window_width = manager.getDefaultDisplay().getWidth();
        window_height = manager.getDefaultDisplay().getHeight();
        this.initScaleView(this, window_width, window_height);
        setMute(true);
    }
    public void initIpcDeviceList(){
//    	if(number<0){
//    		number=0;
//    	}
    	LinearLayout.LayoutParams p=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    	p.height=dip2px(mContext, 40*number);
		l_device_list.setLayoutParams(p);
		for(int i=0;i<number;i++){
			View view = LayoutInflater.from(mContext).inflate(
					R.layout.item_device, null);
			final TextView tv_deviceId=(TextView)view.findViewById(R.id.tv_deviceId);
			tv_deviceId.setText(ipcList[i]);
			if(i==0){
				tv_deviceId.setTextColor(getResources().getColor(R.color.blue));
			}else{
				tv_deviceId.setTextColor(getResources().getColor(R.color.white));
			}
			devicelist.add(tv_deviceId);
			l_device_list.addView(view);
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				   Message msg=new Message();
				   msg.what=Integer.parseInt(tv_deviceId.getText().toString());
				   chandler.sendMessage(msg);
				}
			});
		}
    }
   Handler chandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			for(int i=0;i<ipcList.length;i++){
				if(ipcList[i].equals(String.valueOf(msg.what))){
					currentNumber=i;
					P2PHandler.getInstance().reject();
					callId=ipcList[currentNumber];
					callDevice();
				}
			}	
		}
	};
	
    public static int dip2px(Context context, float dpValue) { 
    	final float scale = context.getResources().getDisplayMetrics().density; 
    	return (int) (dpValue * scale + 0.5f); 
    } 
    
    public void switchNext(){
    	if(currentNumber<number-1){
			currentNumber=currentNumber+1;
		}else{
			currentNumber=0;
		}
		P2PHandler.getInstance().reject();
		switchConnect();
		callId=ipcList[currentNumber];
		setHeaderImage();
    }
    public void switchLast(){
    	if(currentNumber>0){
			currentNumber=currentNumber-1;
		}else{
			currentNumber=number-1;
		}
		P2PHandler.getInstance().reject();
		switchConnect();
		callId=ipcList[currentNumber];
		setHeaderImage();
		callDevice();
    }
    public void connectDooranerfa(){
    	if(isCustomCmdAlarm==true){
			String cmd_connect = "IPC1anerfa:connect";
			P2PHandler.getInstance().sendCustomCmd(callId, password,
					cmd_connect);
		}
    }
    public void disconnectDooranerfa(){
    	if (isCustomCmdAlarm == true) {
			String cmd_disconnect = "IPC1anerfa:disconnect";
			P2PHandler.getInstance().sendCustomCmd(callId, password,
					cmd_disconnect);
		}
    }
    public void setControlButtomHeight(int height){
    	LinearLayout.LayoutParams control_bottom_parames=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		control_bottom_parames.height=height;
		control_bottom.setLayoutParams(control_bottom_parames);
    }
    
    @Override
    public void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	reject();
    	System.out.println("onStop...");
    }
    
}
