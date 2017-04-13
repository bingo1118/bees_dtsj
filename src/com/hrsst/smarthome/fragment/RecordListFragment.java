package com.hrsst.smarthome.fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hrsst.smarthome.dtsj.R;
import com.hrsst.smarthome.activity.PlayBackListActivity;
import com.hrsst.smarthome.adapter.RecordAdapter;
import com.hrsst.smarthome.global.AppConfig;
import com.hrsst.smarthome.global.Constants;
import com.hrsst.smarthome.net.P2PConnect;
import com.hrsst.smarthome.pojo.Contact;
import com.hrsst.smarthome.util.Utils;
import com.p2p.core.MediaPlayer;
import com.p2p.core.P2PHandler;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecordListFragment extends Fragment implements OnScrollListener {
	Context mContext;
	ListView list_record;
	Contact contact;
	String[] names;
	AlertDialog load_record;
	View load_view;
	LayoutInflater inflater;
	RecordAdapter adapter;
	boolean isDialogShowing = false;
	private int visibleLastIndex = 0; // 鏈�鍚庣殑鍙椤圭储寮�
	private int visibleItemCount; // 褰撳墠绐楀彛鍙椤规�绘暟
	RelativeLayout layout_loading;
	boolean isRegFilter = false;
	private List<String> list;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		list = getArguments().getStringArrayList("list");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.inflater = inflater;
		mContext = getActivity();
		View view = inflater
				.inflate(R.layout.fragment_record, container, false);
		initComponent(view);
		return view;
	}

	public void regFilter() {
		isRegFilter = true;
		IntentFilter filter = new IntentFilter();
		// filter.addAction(Constants.P2P.RET_GET_PLAYBACK_FILES);
		// filter.addAction(Constants.P2P.ACK_RET_GET_PLAYBACK_FILES);
		filter.addAction(Constants.Action.REPEAT_LOADING_DATA);
		mContext.registerReceiver(br, filter);
	}

	BroadcastReceiver br = new BroadcastReceiver() {

		@Override
		public void onReceive(Context arg0, Intent intent) {
			if (intent.getAction().equals(Constants.P2P.RET_GET_PLAYBACK_FILES)) {
				// String[] names = (String[])
				// intent.getCharSequenceArrayExtra("recordList");
				// if(names.length<=0){
				// layout_loading.setVisibility(RelativeLayout.GONE);
				// T.showShort(mContext, R.string.loading_end);
				// return;
				// }
				// list=new ArrayList<String>();
				// for(String str:names){
				// list.add(str);
				// Log.e("adddata", str.substring(6,str.length()));
				// }
				// Log.e("adddata", "list_size"+list.size());
				// adapter.upLoadData(list);
				// layout_loading.setVisibility(RelativeLayout.GONE);

				// }else
				// if(intent.getAction().equals(Constants.P2P.ACK_RET_GET_PLAYBACK_FILES)){
				// int result = intent.getIntExtra("result", -1);
				// if(result==Constants.P2P_SET.ACK_RESULT.ACK_PWD_ERROR){
				// T.showShort(mContext, R.string.password_error);
				// }else if(result==Constants.P2P_SET.ACK_RESULT.ACK_NET_ERROR){
				// Log.e("my","net error resend:set npc time");
				// T.showShort(mContext, R.string.net_error);
				// }else
				// if(result==Constants.P2P_SET.ACK_RESULT.ACK_INSUFFICIENT_PERMISSIONS){
				// T.showShort(mContext, R.string.insufficient_permissions);
				// }
				// }
				//
			} else if (intent.getAction().equals(
					Constants.Action.REPEAT_LOADING_DATA)) {
				layout_loading.setVisibility(View.GONE);
			}
		}
	};

	public void initComponent(View view) {
		list_record = (ListView) view.findViewById(R.id.list_record);
		adapter = new RecordAdapter(mContext, list);
		TextView emptyView = new TextView(mContext);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		emptyView.setGravity(Gravity.CENTER_HORIZONTAL);
		emptyView.setPadding(0, 200, 0, 0);
		emptyView.setTextColor(Utils.getColorByResouce(R.color.black));
		emptyView.setLayoutParams(params);
		emptyView.setText(R.string.no_video);
		emptyView.setVisibility(View.GONE);
		((ViewGroup) list_record.getParent()).addView(emptyView);
		list_record.setEmptyView(emptyView);
		list_record.setAdapter(adapter);
		// View view1=inflater.inflate(R.layout.list_record_load, null);
		// list_record.addFooterView(view1);
		layout_loading = (RelativeLayout) view
				.findViewById(R.id.layout_loading);
		list_record.setOnScrollListener(this);
		list_record.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String filename = adapter.getList().get(arg2);
				load_view = inflater.inflate(R.layout.dialog_load_record, null);
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());

				load_record = builder.create();
				// load_record.setCancelable(false);
				load_record.show();
				isDialogShowing = true;
				load_record.setContentView(load_view);
				load_record.setOnKeyListener(new OnKeyListener() {

					@Override
					public boolean onKey(DialogInterface arg0, int arg1,
							KeyEvent event) {
						// TODO Auto-generated method stub
						if (event.getAction() == KeyEvent.ACTION_DOWN
								&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
							// createExitDialog(this);
							if (isDialogShowing) {

								load_record.cancel();
								isDialogShowing = false;
								P2PHandler.getInstance().reject();
							}
							return true;
						}
						return false;
					}

				});

				FrameLayout.LayoutParams layout = new FrameLayout.LayoutParams(
						Utils.dip2px(getActivity(), 222), Utils.dip2px(
								getActivity(), 130));
				load_view.setLayoutParams(layout);
				final AnimationDrawable anim;
				ImageView img = (ImageView) load_view
						.findViewById(R.id.load_record_img);

				anim = (AnimationDrawable) img.getDrawable();
				OnPreDrawListener opdl = new OnPreDrawListener() {
					@Override
					public boolean onPreDraw() {
						anim.start();
						return true;
					}
				};
				img.getViewTreeObserver().addOnPreDrawListener(opdl);
				PlayBackListActivity.currentFile = arg2;
				P2PConnect.setCurrent_state(P2PConnect.P2P_STATE_CALLING);
				P2PConnect.setCurrent_call_id(contact.contactId);
				P2PHandler.getInstance().playbackConnect(contact.getContactId(),
						contact.contactPassword, filename, arg2,AppConfig.VideoMode);
			}

		});
	}

	public void cancelDialog() {
		load_record.cancel();
		isDialogShowing = false;
		MediaPlayer.getInstance().native_p2p_hungup();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (isRegFilter == true) {
			mContext.unregisterReceiver(br);
			isRegFilter = false;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		regFilter();
		if (adapter == null) {
			adapter = new RecordAdapter();
		} else {
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public void updateList(List<String> list) {
		if (adapter != null) {
			adapter.upLoadData(list);
		}
	}

	public void setUser(Contact contact) {
		this.contact = contact;

	}

	public void closeDialog() {
		if (null != load_record) {
			load_record.cancel();
			isDialogShowing = false;
		}
	}

	public void scrollOn() {
		list_record.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				return false;
			}

		});
	}

	public void scrollOff() {
		list_record.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				return true;
			}

		});
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.visibleItemCount = visibleItemCount;
		visibleLastIndex = firstVisibleItem + visibleItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		int itemsLastIndex = adapter.getCount(); // 鏁版嵁闆嗘渶鍚庝竴椤圭殑绱㈠紩
		int lastIndex = itemsLastIndex + 1; // 鍔犱笂搴曢儴鐨刲oadMoreView椤�
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& visibleLastIndex == lastIndex) {
			// 濡傛灉鏄嚜鍔ㄥ姞杞�,鍙互鍦ㄨ繖閲屾斁缃紓姝ュ姞杞芥暟鎹殑浠ｇ爜

		}
		Log.e("length", itemsLastIndex + "itemsLastIndex");
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& itemsLastIndex == visibleLastIndex) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			try {
				Date nextStartTime = RecordAdapter.startTime;
				String lasttime = adapter.getLastItem();
				if (lasttime == null || nextStartTime == null) {
					return;
				}
				Date nextEndTime = sdf.parse(adapter.getLastItem());
				if (nextEndTime == null || nextEndTime.equals("")
						|| nextStartTime == null || nextStartTime.equals("")) {
					return;
				}
				layout_loading.setVisibility(View.VISIBLE);

				Log.i("waitload", "璇锋眰涓�娆�");
				setIsWatie(true);
				P2PHandler.getInstance().getRecordFiles(contact.getContactId(),
						contact.contactPassword, nextStartTime, nextEndTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void setIsWatie(boolean isWatie) {
		((PlayBackListActivity) mContext).setIsWaiteList(isWatie);
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
