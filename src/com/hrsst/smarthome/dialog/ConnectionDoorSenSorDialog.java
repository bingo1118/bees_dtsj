package com.hrsst.smarthome.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.hrsst.smarthome.dtsj.R;
import com.hrsst.smarthome.util.BitmapCache;

public class ConnectionDoorSenSorDialog extends Dialog{
	private Context mContext;
	private AnimationDrawable mAnimationDrawable;
	private int resource;

	public ConnectionDoorSenSorDialog(Context mContext,int resource) {
		super(mContext);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.resource = resource;
	}
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        // �����뻻��dialogĬ�ϱ���������dialog�ı�Ե����͸�����Һܿ�
        // ��֮�ﲻ����Ҫ��Ч��
        getWindow().setBackgroundDrawableResource(android.R.color.transparent); 
        View localView = LayoutInflater.from(mContext).inflate(R.layout.dialog_connecting_doorsensor, null);
        Bitmap mBitmap = BitmapCache.getInstance().getBitmap(R.drawable.liuchengtu_4,mContext);
		BitmapDrawable bd = new BitmapDrawable(mContext.getResources(), mBitmap);
		ImageView liuchengtu_4_rela = (ImageView) localView.findViewById(R.id.liuchengtudoor_image);
        liuchengtu_4_rela.setBackground(bd);
        ImageView im  = (ImageView) localView.findViewById(R.id.connect_doorsensor_image);
        im.setBackgroundResource(resource);
        mAnimationDrawable = (AnimationDrawable) im.getBackground();
        setContentView(localView); 
        // ��仰��ȫ��������
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
	}
	
	public void startConnect(){
		mAnimationDrawable.start();
	}
	
	public void stopConnect(){
		mAnimationDrawable.stop();
	}
}
