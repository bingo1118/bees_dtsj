package com.hrsst.smarthome.adapter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.hrsst.smarthome.dtsj.R;
import com.hrsst.smarthome.global.Constants;
import com.hrsst.smarthome.pojo.UserDevice;
import com.hrsst.smarthome.util.BitmapCache;
import com.p2p.core.P2PHandler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class PullToRefreshGridViewAdapter extends BaseAdapter {
	private GridView mGridView;
	private Set<Integer> socketPos;
	private Set<Integer> cameraPos;
	private Context mContext;
	private List<UserDevice> list;
	private ViewHolder holder;
	private Map<String, Integer> m;
	private String cameraId;
	private String cameraPwd;

	public PullToRefreshGridViewAdapter(Context mContext,List<UserDevice> list) {
		this.mContext = mContext;
		this.list = list;
		socketPos =new TreeSet<Integer>();
		cameraPos =new TreeSet<Integer>();
		m = new TreeMap<String, Integer>();
	}

	@Override
	public int getCount() {
		return list.size()+1;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_uselayout, null);
			convertView.setPadding(35, 15, 0, 10);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.mImageView);
			holder.defence_image = (ImageView) convertView.findViewById(R.id.defence_image);
			holder.text = (TextView) convertView.findViewById(R.id.mTextView);
			holder.open_or_close_tv = (TextView) convertView.findViewById(R.id.open_or_close_tv);
			holder.device_list_rela = (RelativeLayout) convertView.findViewById(R.id.device_list_rela);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(list.size()>0&&position<list.size()){
			UserDevice mUserDevice =list.get(position);
			//Ìí¼ÓÉãÏñÍ·ÆôÓÃ
			int type = mUserDevice.getDevType();
			int onOrOutLine = mUserDevice.getLightOnOrOutLine();
			int openOrColse = mUserDevice.getSocketStates();
			switch (type) {
			case 1:
				socketPos.add(position);
				int defenceType = mUserDevice.getDefence();
				if(defenceType==0){
					holder.defence_image.setBackgroundResource(R.drawable.defence_on);
				}else{
					holder.defence_image.setBackgroundResource(R.drawable.defence_off);
				}
				
				if(openOrColse==1){
//					Bitmap mBitmap = BitmapCache.getInstance().getBitmap(R.drawable.zhuangtai_on, mContext);
//					BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), mBitmap);
					holder.image.setImageResource(R.drawable.zhuangtai_on);
					holder.open_or_close_tv.setText(R.string.on);
				}else{
//					Bitmap mBitmap = BitmapCache.getInstance().getBitmap(R.drawable.zhuangtai_off, mContext);
//					BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), mBitmap);
					holder.image.setImageResource(R.drawable.zhuangtai_off);
					holder.open_or_close_tv.setText(R.string.off);
				}
				if(onOrOutLine==1){
					holder.defence_image.setEnabled(true);
//					Bitmap mBitmap = BitmapCache.getInstance().getBitmap(R.drawable.chazuo_on, mContext);
//					BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), mBitmap);
					holder.device_list_rela.setBackgroundResource(R.drawable.chazuo_on);
				}else{
					holder.defence_image.setEnabled(false);
//					Bitmap mBitmap = BitmapCache.getInstance().getBitmap(R.drawable.chazuo_off, mContext);
//					BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), mBitmap);
					holder.device_list_rela.setBackgroundResource(R.drawable.chazuo_off);
				}
				break;
			case 2:
				cameraPos.add(position);
				holder.defence_image.setBackgroundResource(R.drawable.defence_anxia);
				holder.image.setVisibility(View.GONE);
				holder.open_or_close_tv.setVisibility(View.GONE);
				String cameraId = mUserDevice.getDevMac().trim();
				String cameraPwd = mUserDevice.getCameraPwd().trim();
				P2PHandler.getInstance().getDefenceStates(
						cameraId, cameraPwd);
				m.put(cameraId, position);
				if(onOrOutLine==1){
					holder.defence_image.setEnabled(true);
//					Bitmap mBitmap = BitmapCache.getInstance().getBitmap(R.drawable.shouye_sxt_on, mContext);
//					BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), mBitmap);
					holder.device_list_rela.setBackgroundResource(R.drawable.shouye_sxt_on);
				}else{
					holder.defence_image.setEnabled(false);
//					Bitmap mBitmap = BitmapCache.getInstance().getBitmap(R.drawable.shouye_sxt_off, mContext);
//					BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), mBitmap);
					holder.device_list_rela.setBackgroundResource(R.drawable.shouye_sxt_off);
				}
				break;
			default:
				break;
			}
			
			holder.text.setText(mUserDevice.getDevName());
		}
		if(position==list.size()){
			holder.device_list_rela.setBackgroundResource(R.drawable.add_device);
			holder.defence_image.setVisibility(View.GONE);
			holder.image.setVisibility(View.GONE);
			holder.text.setVisibility(View.GONE);
			holder.open_or_close_tv.setVisibility(View.GONE);
		}
		holder.defence_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				UserDevice mUserDevice = list.get(position);
				int type = mUserDevice.getDevType();
				int defence = mUserDevice.getDefence();
				switch (type) {
				case 1:
					if(defence==0){
						defence=1;
					}else{
						defence=0;
					}
					Intent i = new Intent();
					i.putExtra("defencePos", position);
					i.putExtra("defenceType", defence);
					i.putExtra("devMac", list.get(position).getDevMac());
					i.setAction("DEFENCE_ACTION");
					mContext.sendBroadcast(i);
					break;
				case 2:
					System.out.println("defence="+defence);
					if(defence==0){
						P2PHandler.getInstance().setRemoteDefence(
								mUserDevice.getDevMac().trim(),
								mUserDevice.getCameraPwd().trim(),
								Constants.P2P_SET.REMOTE_DEFENCE_SET.ALARM_SWITCH_ON);
						defence=1;
						cameraId = mUserDevice.getDevMac().trim();
						cameraPwd = mUserDevice.getCameraPwd().trim();
						holder.defence_image.setBackgroundResource(R.drawable.defence_on);
					}else{
						P2PHandler.getInstance().setRemoteDefence(
								mUserDevice.getDevMac().trim(),
								mUserDevice.getCameraPwd().trim(),
								Constants.P2P_SET.REMOTE_DEFENCE_SET.ALARM_SWITCH_OFF);
						defence=0;
						cameraId = mUserDevice.getDevMac().trim();
						cameraPwd = mUserDevice.getCameraPwd().trim();
						holder.defence_image.setBackgroundResource(R.drawable.defence_off);
					}
					break;
				default:
					break;
				}
				
			}
		});
		return convertView;
	}
	
	public String getCameraId(){
		if(null!=cameraId&&cameraId.length()>0){
			return cameraId;
		}else{
			return null;
		}
	}
	
	public String getCameraPwd(){
		if(null!=cameraPwd&&cameraPwd.length()>0){
			return cameraPwd;
		}else{
			return null;
		}
	}
	
	public void setGridView(GridView mGridView){
		this.mGridView = mGridView;
	}
	
    public void updateItemData(List<UserDevice> item){
    	if(null==item||item.size()<=0){
    		return;
    	}
    	for(UserDevice u:item){
    		list.set(u.getId(), u);
    		Message msg = Message.obtain();
        	msg.arg1 = u.getId();
        	msg.what=1;
        	han.sendMessage(msg);
    	}
    }
    
    public void updateCameraData(List<UserDevice> item){
    	for(UserDevice u:item){
    		list.set(u.getId(), u);
    		Message msg = Message.obtain();
        	msg.obj = u;
        	msg.what=3;
        	han.sendMessage(msg);
    	}
    }
    
    public void defence(int pos,int type){
    	Message msg = Message.obtain();
    	msg.arg1 = pos;
    	msg.arg2 = type;
    	msg.what=2;
    	han.sendMessage(msg);
    }
    
    public void cameraDefence(int pos,int type){
    	Message msg = Message.obtain();
    	msg.arg1 = pos;
    	msg.arg2 = type;
    	msg.what=4;
    	han.sendMessage(msg);
    }
    
    private void updateDefence(int pos,int type){
    	if (mGridView == null){
            return;
        }
    	View view = mGridView.getChildAt(pos);
    	if(view==null){
    		return;
    	}
    	UserDevice mUserDevice = list.get(pos);
    	mUserDevice.setDefence(type);
    	list.set(pos, mUserDevice);
    	ImageView defence_image = (ImageView) view.findViewById(R.id.defence_image);
    	if(type==0){
    		defence_image.setBackgroundResource(R.drawable.defence_on);
		}else{
			defence_image.setBackgroundResource(R.drawable.defence_off);
		}
    }
    
    private void updateCameraDefence(int pos,int type){
    	if (mGridView == null){
            return;
        }
    	View view = mGridView.getChildAt(pos);
    	if(view==null){
    		 return;
    	}
    	UserDevice mUserDevice = list.get(pos);
    	mUserDevice.setDefence(type);
    	list.set(pos, mUserDevice);
    	ImageView defence_image = (ImageView) view.findViewById(R.id.defence_image);
    	if(type==1){
    		defence_image.setBackgroundResource(R.drawable.defence_on);
		}else{
			defence_image.setBackgroundResource(R.drawable.defence_off);
		}
    }
    
	@SuppressLint("HandlerLeak")
	private Handler han = new Handler(){
        public void handleMessage(android.os.Message msg){
        	switch (msg.what) {
			case 1:
				updateItem(msg.arg1);
				break;
			case 2:
				updateDefence(msg.arg1,msg.arg2);
				break;
			case 3:
				updateCameraStatus(msg.obj);
				break;
			case 4:
				updateCameraDefence(msg.arg1,msg.arg2);
				break;
			default:
				break;
			}
        };
    };
    
    @SuppressLint("NewApi")
	private void updateCameraStatus(Object obj){
    	if (mGridView == null){
            return;
        }
    	View view = mGridView.getChildAt(((UserDevice) obj).getId());
    	if(view==null){
    		return;
    	}
    	ImageView defence_image = (ImageView) view.findViewById(R.id.defence_image);
    	ImageView image = (ImageView) view.findViewById(R.id.mImageView);
    	TextView open_or_close_tv = (TextView) view.findViewById(R.id.open_or_close_tv);
    	TextView text = (TextView) view.findViewById(R.id.mTextView);
    	RelativeLayout device_list_rela = (RelativeLayout) view.findViewById(R.id.device_list_rela);
    	switch (((UserDevice) obj).getDefence()) {
		case 1:
			defence_image.setBackgroundResource(R.drawable.defence_on);
			break;
		case 0:
			defence_image.setBackgroundResource(R.drawable.defence_off);
			break;
		default:
			break;
		}
    	
		image.setVisibility(View.GONE);
		open_or_close_tv.setVisibility(View.GONE);
		if(((UserDevice) obj).getLightOnOrOutLine()==1){
			defence_image.setEnabled(true);
//			Bitmap mBitmap = BitmapCache.getInstance().getBitmap(R.drawable.shouye_sxt_on, mContext);
//			BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), mBitmap);
			device_list_rela.setBackgroundResource(R.drawable.shouye_sxt_on);
		}else{
			defence_image.setEnabled(false);
//			Bitmap mBitmap = BitmapCache.getInstance().getBitmap(R.drawable.shouye_sxt_off, mContext);
//			BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), mBitmap);
			device_list_rela.setBackgroundResource(R.drawable.shouye_sxt_off);
		}
		text.setText(((UserDevice) obj).getDevName());
    }
	
	@SuppressLint("NewApi")
	private void updateItem(int index){
		if (mGridView == null){
            return;
        }
		View view = mGridView.getChildAt(index);
		if(view==null){
			return;
		}
		ImageView im = (ImageView) view.findViewById(R.id.mImageView);
		TextView tv = (TextView) view.findViewById(R.id.mTextView);
		TextView tv_open = (TextView) view.findViewById(R.id.open_or_close_tv);
		RelativeLayout rela = (RelativeLayout) view.findViewById(R.id.device_list_rela);
		ImageView defence_image = (ImageView) view.findViewById(R.id.defence_image);
		UserDevice mUserDevice =list.get(index);
		int onOrOutLine = mUserDevice.getLightOnOrOutLine();
		int openOrColse = mUserDevice.getSocketStates();
		if(openOrColse==1){
//			Bitmap mBitmap = BitmapCache.getInstance().getBitmap(R.drawable.zhuangtai_on, mContext);
//			BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), mBitmap);
			im.setImageResource(R.drawable.zhuangtai_on);
			tv_open.setText(R.string.on);
		}else{
//			Bitmap mBitmap = BitmapCache.getInstance().getBitmap(R.drawable.zhuangtai_off, mContext);
//			BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), mBitmap);
			im.setImageResource(R.drawable.zhuangtai_off);
			tv_open.setText(R.string.off);
		}
		if(onOrOutLine==1){
//			Bitmap mBitmap = BitmapCache.getInstance().getBitmap(R.drawable.chazuo_on, mContext);
//			BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), mBitmap);
			defence_image.setEnabled(true);
			rela.setBackgroundResource(R.drawable.chazuo_on);
		}else{
//			Bitmap mBitmap = BitmapCache.getInstance().getBitmap(R.drawable.chazuo_off, mContext);
//			BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), mBitmap);
			defence_image.setEnabled(false);
			rela.setBackgroundResource(R.drawable.chazuo_off);
		}
		tv.setText(mUserDevice.getDevName());
    }
	
	public Integer[] getSocketPos(){
		Integer[] arr2=socketPos.toArray(new Integer[0]);
		return arr2;
	}
	public Integer[] getCameraPos(){
		Integer[] arr1=cameraPos.toArray(new Integer[0]);
		return arr1;
	}
	
	public Map<String, Integer> getPos(){
		return m;
	}

	static class ViewHolder {
		public ImageView image;
		public TextView text,open_or_close_tv;
		public RelativeLayout device_list_rela;
		public ImageView defence_image;
	}

}
