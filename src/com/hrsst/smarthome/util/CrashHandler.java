package com.hrsst.smarthome.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hrsst.smarthome.dtsj.R;
import com.hrsst.smarthome.global.Constants;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * UncaughtException������,��������Uncaught�쳣��ʱ��,�и������ӹܳ���,����¼���ʹ��󱨸�
 * 
 */
@SuppressLint("SimpleDateFormat")
public class CrashHandler implements UncaughtExceptionHandler {

	public static final String TAG = "CrashHandler";
	
	public static File zipFile;
	private static long timetamp = System.currentTimeMillis();
	
	private static final boolean isUploadError = true;
	// ������־��¼λ��
	public static String path = "/sdcard/UploadAppError/log/";
	// ϵͳĬ�ϵ�UncaughtException������
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	// CrashHandlerʵ��
	private static CrashHandler INSTANCE = new CrashHandler();
	// �����Context����
	private Context mContext;
	// �����洢�豸��Ϣ���쳣��Ϣ
	private final Map<String, String> infos = new HashMap<String, String>();

	/** ��ֻ֤��һ��CrashHandlerʵ�� */
	private CrashHandler() {
	}

	/** ��ȡCrashHandlerʵ�� ,����ģʽ */
	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	/**
	 * ��ʼ��
	 * 
	 * @param context
	 */
	public void init(Context context) {
		mContext = context;
		// ��ȡϵͳĬ�ϵ�UncaughtException������
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		// ���ø�CrashHandlerΪ�����Ĭ�ϴ�����
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * ��UncaughtException����ʱ��ת��ú���������
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			// ����û�û�д�������ϵͳĬ�ϵ��쳣������������
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				
			}
			// �˳�����
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	}

	/**
	 * �Զ��������,�ռ�������Ϣ ���ʹ��󱨸�Ȳ������ڴ����.
	 * 
	 * @param ex
	 * @return true:��������˸��쳣��Ϣ;���򷵻�false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}else{
			String a=ex.getMessage();
			String b=ex.getLocalizedMessage();
		}
		// ʹ��Toast����ʾ�쳣��Ϣ
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext, R.string.will_over, 2000).show();
				Looper.loop();
			}
		}.start();
		// �ռ��豸������Ϣ
		collectDeviceInfo(mContext);
		// ������־�ļ�
		saveCrashInfoile(ex);
		return true;
	}

	/**
	 * 1���ռ��豸������Ϣ
	 * 
	 * @param ctx
	 */
	public void collectDeviceInfo(Context ctx) {
		try {
			PackageManager pm = ctx.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null"
						: pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
				
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	/**
	 * 2�����������Ϣ���ļ���
	 * 
	 * @param ex
	 * @return �����ļ�����,���ڽ��ļ����͵�������
	 */
	private String saveCrashInfoile(Throwable ex) {

		StringBuffer sb = new StringBuffer();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat logFt = new SimpleDateFormat("yyyyMMdd");
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (key.equals("TIME")) {
				Date d = new Date(Long.valueOf(value + ""));
				sb.append(key + "=" + format.format(d) + "\n");
			} else {
				sb.append(key + "=" + value + "\n");
			}

		}

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		try {
			long timestamp = System.currentTimeMillis();
			Date d = new Date(timestamp);
			// String time = formatter.format(new Date());
			String time = logFt.format(d);
			// String fileName = "crash-" + time + "-" + timestamp + ".txt";
			String fileName = "crash-" + time + timetamp+ ".log";
			if (!Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				return null;
			}
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File f = new File(path + fileName);
			if (!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream fos = new FileOutputStream(f, true);
			fos.write(sb.toString().getBytes());
			fos.close();
			if (!isUploadError)
				return fileName;
			sendErrorLogToServer(f, Constants.ERROR_URL);
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}
		return null;
	}

	/**
	 * 3����������־��������
	 * 
	 * @param f
	 * @param serverURL
	 */
	public static void sendErrorLogToServer(File logFile, String serverURL) {
		SimpleDateFormat logFt = new SimpleDateFormat("yyyyMMdd");
		List<File> files = new ArrayList<File>();
		FileUtils.list(logFile.getParentFile(), "crash", ".log", "3", files);
		String time = logFt.format(new Date());
		try {
			if (files.isEmpty())
				return;
			System.out.println("send start....");
			zipFile = new File(logFile.getParent() + "/crash_" + time+timetamp
					+ ".rar");
			System.out.println("zipFile="+zipFile);
			ZipUtils.zipFiles(files, zipFile);
			System.out.println("serverURL="+serverURL);
			
			new MyTask().execute(serverURL);
			
		} catch (Exception e) {
			return;
		}
	}
	
	static class MyTask extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			String serverURL = params[0];
			UploadUtil uploadUtil = new UploadUtil();
			uploadUtil.uploadFile(zipFile, serverURL);
			return null;
		}
		
	}
}
