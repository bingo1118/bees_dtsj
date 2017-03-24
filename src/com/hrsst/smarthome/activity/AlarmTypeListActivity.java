package com.hrsst.smarthome.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;
import com.hrsst.smarthome.dtsj.R;
import com.hrsst.smarthome.adapter.DevTypeAdapter;
import com.hrsst.smarthome.global.Constants;
import com.hrsst.smarthome.net.SocketUDP;
import com.hrsst.smarthome.order.SendServerOrder;
import com.hrsst.smarthome.order.UnPackServer;
import com.hrsst.smarthome.pojo.AlarmType;
import com.hrsst.smarthome.pojo.UnPackageFromServer;
import com.hrsst.smarthome.util.BitmapCache;
import com.hrsst.smarthome.volley.JsonArrayPostRequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmTypeListActivity extends Activity{
	private Context mContext;
	private int type;
	private String mac;
	private TextView alarm_type_name_list;
	private ListView alarm_type_list;
	private DevTypeAdapter mDevTypeAdapter;
	private List<AlarmType> li;
	private AlertDialog dialog,modifyDialog;
	private SocketUDP mSocketUDPClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_type_list);
		type = getIntent().getExtras().getInt("type");
		mac = getIntent().getExtras().getString("mac");
		mContext = this;
		init();
		regFilter();
		mSocketUDPClient = SocketUDP.newInstance(Constants.SeverInfo.SERVER,
				Constants.SeverInfo.PORT);
		mSocketUDPClient.startAcceptMessage();
	}
	
	public void regFilter() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("MODIFY_ALARM_DEV_LOCATION_NAME");
		filter.addAction("Constants.Action.unModifyAlarmName");
		mContext.registerReceiver(mReceiver, filter);
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			if(arg1.getAction().equals("Constants.Action.unModifyAlarmName")){
				System.out.println("000");
				byte[] datas = arg1.getExtras().getByteArray("datasByte");
				UnPackageFromServer mUnPackageFromServer = new UnPackServer().unModifyAlarmName(datas);
				String result = mUnPackageFromServer.alarmPos;
				if(null!=result&&result.length()>0){
					if(result.equals("success")){
						getDevice(mac,type,"");
						Toast.makeText(mContext, R.string.change_success, Toast.LENGTH_SHORT).show();
					}else if(result.equals("failed")){
						Toast.makeText(mContext, R.string.change_fail, Toast.LENGTH_SHORT).show();
					}
				}
			}
			
			if(arg1.getAction().equals("MODIFY_ALARM_DEV_LOCATION_NAME")){
				String exLcation = arg1.getExtras().getString("exLcation");
				final String alarmMac = arg1.getExtras().getString("alarmMac");
				View vv = LayoutInflater.from(mContext).inflate(
						R.layout.modify_dev_name_dialog, null);
				TextView cancle_modify = (TextView) vv
						.findViewById(R.id.cancle_modify);
				TextView confire_modify = (TextView) vv
						.findViewById(R.id.confire_modify);
				final EditText modify_named = (EditText) vv
						.findViewById(R.id.modify_named);
				modify_named.setText(exLcation);
				modify_named.setSelection(exLcation.length());
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				modifyDialog = builder.create();
				modifyDialog.show();
				modifyDialog.setContentView(vv);
				modifyDialog.getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
				cancle_modify.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						modifyDialog.dismiss();
					}
				});
				confire_modify.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String modifyName = modify_named.getText().toString()
								.trim();
						if(modifyName.length()>=50){
							Toast.makeText(mContext, R.string.input_string_too_long, Toast.LENGTH_SHORT).show();
						}else{
							byte[] orderSend =SendServerOrder.ModifyAlarmName(alarmMac, modifyName);
							mSocketUDPClient.sendMsg(orderSend);
							modifyDialog.dismiss();
						}
					}
				});
			}
		}
		
	};

	private void init() {
		// TODO Auto-generated method stub
		alarm_type_name_list = (TextView) findViewById(R.id.alarm_type_name_list);
		switch (type) {
		case 1:
			alarm_type_name_list.setText(R.string.list_of_yangan);
			break;
		case 2:
			alarm_type_name_list.setText(R.string.list_of_menci);
			break;
		case 4:
			alarm_type_name_list.setText(R.string.list_of_keranqiti);
			break;
		case 3:
			alarm_type_name_list.setText(R.string.list_of_hongwai);
			break;
		default:
			break;
		}
		
		alarm_type_list = (ListView) findViewById(R.id.alarm_type_list);
		getDevice(mac,type,"");
		alarm_type_list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				final AlarmType mAlarmType = (AlarmType) arg0.getAdapter().getItem(arg2);
				View v = LayoutInflater.from(mContext).inflate(
						R.layout.long_click_dialog, null);
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				dialog = builder.create();
				dialog.show();
				dialog.setContentView(v);
				TextView cancle_delete = (TextView) v.findViewById(R.id.cancle_delete);
				TextView confire_delete = (TextView) v.findViewById(R.id.confire_delete);
				cancle_delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				confire_delete.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						getDevice(mac,type,mAlarmType.getDevMac());
					}
				});
				return true;
			}
		});
	}
	
	private void getDevice(String mac,final int type,String alarmMac){
		RequestQueue mQueue = Volley.newRequestQueue(mContext);
		Map<String,String> map = new HashMap<String,String>();
		map.put("type", type+"");
		map.put("alarmMac", alarmMac);
		map.put("dwMac", mac);
		JsonArrayPostRequest mJsonRequest = new JsonArrayPostRequest(
				Constants.DELETE_433_DEVICE_LIST_URL, 
				new Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						li = new ArrayList<AlarmType>();
						if(response.length()>0){
							for(int i=0;i<response.length();i++){
								try {
									JSONObject obj = (JSONObject) response.get(i);
									AlarmType mAlarmType = new AlarmType();
									mAlarmType.setLocation(obj.getString("position"));
									mAlarmType.setRecordTime(obj.getString("recordeTime"));
									mAlarmType.setDevMac(obj.getString("alarmDevMac"));
									mAlarmType.setMac(obj.getString("mac"));
									li.add(mAlarmType);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						mDevTypeAdapter = new DevTypeAdapter(li, mContext, type);
						alarm_type_list.setAdapter(mDevTypeAdapter);
					}
				}, 
				new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
					}
				}, 
				map);
		mQueue.add(mJsonRequest);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(mReceiver);
		BitmapCache.getInstance().clearCache();
		super.onDestroy();
		
	}
}
