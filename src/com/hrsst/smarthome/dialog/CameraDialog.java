package com.hrsst.smarthome.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class CameraDialog extends Dialog{
	private Context mContext;

	
	public CameraDialog(Context context,int resource,int action) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        // �����뻻��dialogĬ�ϱ���������dialog�ı�Ե����͸�����Һܿ�
        // ��֮�ﲻ����Ҫ��Ч��
        getWindow().setBackgroundDrawableResource(android.R.color.transparent); 
        View localView = LayoutInflater.from(mContext).inflate(null, null);
        
        setContentView(localView); 
        // ��仰��ȫ��������
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
	}
	
}
