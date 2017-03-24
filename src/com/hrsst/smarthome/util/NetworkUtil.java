package com.hrsst.smarthome.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
 
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
 
public class NetworkUtil {
     
    private NetworkUtil() {
        throw new UnsupportedOperationException("NetworkUtil cannot be instantiated");
    }
 
    /**
     * �ж������Ƿ�����
     *
     */
    public static boolean isConnected(Context context)  {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()){
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
 
    /**
     * �ж��Ƿ���wifi����
     */
    public static boolean isWifi(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) return false;
        return connectivity.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
 
    }
 
    /**
     * ���������ý���
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName componentName = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
        intent.setComponent(componentName);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }
 
    /**
     * ʹ��SSL�����ε�֤��
     */
    public static  void useUntrustedCertificate() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
