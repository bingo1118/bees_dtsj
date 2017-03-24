package com.hrsst.smarthome.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.net.ConnectivityManager;

public class IsNetworkAvailable {
	
	public static final boolean ping() { 
        String result = null; 
        try { 
                String ip = "www.baidu.com";// ping �ĵ�ַ�����Ի����κ�һ�ֿɿ������� 
                Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping��ַ3�� 
                // ��ȡping�����ݣ����Բ��� 
                InputStream input = p.getInputStream(); 
                BufferedReader in = new BufferedReader(new InputStreamReader(input)); 
                StringBuffer stringBuffer = new StringBuffer(); 
                String content = ""; 
                while ((content = in.readLine()) != null) { 
                        stringBuffer.append(content); 
                }  
                // ping��״̬ 
                int status = p.waitFor(); 
                if (status == 0) { 
                        result = "success"; 
                        return true; 
                } else { 
                        result = "failed";
                        return false;
                } 
        } catch (Exception e) { 
                result = "IOException"; 
                return false;
        } 
        
	}
	
	public static final boolean isNetworkAvailable(Context mContext) {
		// �õ�����������Ϣ
		ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		// ȥ�����ж������Ƿ�����
		if (manager.getActiveNetworkInfo() != null) {
			return manager.getActiveNetworkInfo().isAvailable();
		}else{
			return false;
		}
		
	}
}
