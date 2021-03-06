package com.hrsst.smarthome.activity;

import java.util.List;

import com.hrsst.smarthome.dtsj.R;
import com.hrsst.smarthome.adapter.ChoiceDevAdapter;
import com.hrsst.smarthome.pojo.UserDevice;
import com.hrsst.smarthome.util.BitmapCache;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddFireLinkOneActivity extends Activity {
	private Context mContext;
	private Button add_fk_action_one;
	private TextView device_name;
	private List<UserDevice> mUserDeviceList;
	private AlertDialog modifyDialog;
	private ChoiceDevAdapter mChoiceDevAdapter;
	private String devMac;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_firelink_one);
		mContext = this;
		mUserDeviceList = (List<UserDevice>) getIntent().getSerializableExtra(
				"devList");
		init();
	}

	@SuppressLint("NewApi")
	private void init() {
		// TODO Auto-generated method stub
		ImageView yg_1_image = (ImageView) findViewById(R.id.yg_1_image);
		Bitmap mBitmap = BitmapCache.getInstance().getBitmap(R.drawable.yg_1,mContext);
		BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), mBitmap);
		yg_1_image.setBackground(bd);
		add_fk_action_one = (Button) findViewById(R.id.add_fk_action_one);
		device_name = (TextView) findViewById(R.id.device_name);
		add_fk_action_one.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (devMac != null && devMac.length() > 0) {
					Intent i = new Intent(mContext,
							AddFireLinkTwoActivity.class);
					i.putExtra("device", devMac);
					startActivity(i);
//					finish();
				} else {
					Toast.makeText(mContext, R.string.please_choose_smart_socket, Toast.LENGTH_SHORT).show();
				}
			}
		});
		device_name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				View vv = LayoutInflater.from(mContext).inflate(
						R.layout.choice_dev_dialog, null);
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				modifyDialog = builder.create();
				modifyDialog.show();
				modifyDialog.setContentView(vv);
				ListView dev_list = (ListView) vv.findViewById(R.id.dev_list);
				dev_list.setAdapter(mChoiceDevAdapter);
				dev_list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int pos, long arg3) {
						// TODO Auto-generated method stub
						UserDevice mUserDevice = mUserDeviceList.get(pos);
						device_name.setText(mUserDevice.getDevName());
						devMac = mUserDevice.getDevMac();
						modifyDialog.dismiss();
					}
				});
			}
		});
		mChoiceDevAdapter = new ChoiceDevAdapter(mContext, mUserDeviceList);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		BitmapCache.getInstance().clearCache();
	}
}
