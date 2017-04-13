package com.hrsst.smarthome.mygridview.lib.internal;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hrsst.smarthome.dtsj.R;
import com.hrsst.smarthome.mygridview.lib.PullToRefreshBase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingLayout extends BaseLoadingLayout {

	static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;

	private final ImageView headerImage, header_pic;
	private final ProgressBar headerProgress;
	private final TextView headerText;
	private final TextView headerText1;
	private TextView loadingText;

	private String pullLabel, pullLabel1;
	private String refreshingLabel;
	private String releaseLabel, releaseLabel1;
	private String currentTime;// 褰撳墠鐨勬洿鏂版椂闂�
	private String oldTime;// 涓婁竴娆℃洿鏂版椂闂�
	private Context mContext;
	private boolean flagShow;// 鏄惁鍑虹幇鍑犱嚎浜洪偅涓浘鐗囷細

	private final Animation rotateAnimation, resetRotateAnimation;

	public LoadingLayout(Context context, final int mode, String releaseLabel, String releaseLabel1, String pullLabel, String pullLabel1, String refreshingLabel, boolean flagShow1) {
		super(context, mode, releaseLabel, releaseLabel1, pullLabel, pullLabel1, refreshingLabel);
		mContext = context;
		ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header, this);
		headerText = (TextView) header.findViewById(R.id.pull_to_refresh_text);// 涓嬫媺鍙互鍒锋柊
		headerText1 = (TextView) header.findViewById(R.id.pull_to_refresh_text1);// 涓嬫媺鍙互鍒锋柊
		loadingText = (TextView) header.findViewById(R.id.pull_to_refresh_loading_text);// 鍒锋柊鍔犺浇
		headerImage = (ImageView) header.findViewById(R.id.pull_to_refresh_image);
		headerProgress = (ProgressBar) header.findViewById(R.id.pull_to_refresh_progress);
		header_pic = (ImageView) header.findViewById(R.id.pull_head_pic);
		flagShow = flagShow1;

		if (flagShow)
			viewVisible();

		final Interpolator interpolator = new LinearInterpolator();
		rotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setInterpolator(interpolator);
		rotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		rotateAnimation.setFillAfter(true);

		resetRotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		resetRotateAnimation.setInterpolator(interpolator);
		resetRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		resetRotateAnimation.setFillAfter(true);

		this.releaseLabel = releaseLabel;// "鏉惧紑鍗冲彲鍒锋柊"
		this.pullLabel = pullLabel;// 鎷栨媺鍙互鍒锋柊
		this.releaseLabel1 = releaseLabel1;
		this.refreshingLabel = refreshingLabel;

		if (pullLabel1.equals("")) {
			this.pullLabel1 = mContext.getResources().getString(R.string.pull_loading_old_geng_ganggang);
		} else {
			this.pullLabel1 = mContext.getResources().getString(R.string.pull_loading_old_geng) + oldTime;
		}

		currentTime = getMyCurrentTime();
		this.releaseLabel1 = mContext.getResources().getString(R.string.pull_loading_new_geng) + currentTime;

		switch (mode) {
		case PullToRefreshBase.MODE_PULL_UP_TO_REFRESH:
			headerImage.setImageResource(R.drawable.pulltorefresh_up_arrow);
			break;
		case PullToRefreshBase.MODE_PULL_DOWN_TO_REFRESH:
		default:
			headerImage.setImageResource(R.drawable.pulltorefresh_down_arrow);
			break;
		}
	}

	/**
	 * 寰楀埌褰撳墠鏃堕棿鏍煎紡锛�
	 *
	 * @return
	 */
	public static String getMyCurrentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 鑾峰彇褰撳墠鏃堕棿
		return formatter.format(curDate);

		/*
		 * 鑾峰彇骞存湀鏃ュ綋鍓嶆椂闂� SimpleDateFormat sDateFormat = new
		 * SimpleDateFormat("yyyy-MM-dd   hh:mm:ss"); String date =
		 * sDateFormat.format(new java.util.Date());
		 */

		/*
		 * 濡傛灉鎯宠幏鍙栧綋鍓嶇殑骞存湀,鍒欏彲浠ヨ繖鏍峰啓(鍙幏鍙栨椂闂存垨绉掔涓�鏍�): SimpleDateFormat sdf=new
		 * SimpleDateFormat("yyyy-MM"); String date=sdf.format(new
		 * java.util.Date());
		 */

		/*
		 * 褰撶劧杩樻湁灏辨槸鍙互鎸囧畾鏃跺尯鐨勬椂闂�(寰�):
		 * df=DateFormat.getDateTimeInstance(DateFormat.FULL,
		 * DateFormat.FULL,Locale.CHINA); System.out.println(df.format(new
		 * Date()));
		 */
	}

	// 鍑犱嚎鍥剧墖鎻愮ず锛�
	private void viewVisible() {
		header_pic.setVisibility(View.VISIBLE);
		headerImage.setVisibility(View.GONE);
		headerProgress.setVisibility(View.GONE);
		headerText.setVisibility(View.GONE);
		headerText1.setVisibility(View.GONE);
		loadingText.setVisibility(View.GONE);
	}

	public void reset() {// 閬垮厤鏈�鍚庡張鍑虹幇涓�涓�
		// headerText.setText(pullLabel);
		// headerImage.setVisibility(View.VISIBLE);
		if (pullLabel1.equals("")) {
			this.pullLabel1 = mContext.getResources().getString(R.string.pull_loading_old_geng_ganggang);
		} else {
			this.pullLabel1 = mContext.getResources().getString(R.string.pull_loading_old_geng) + oldTime;
		}

		headerProgress.setVisibility(View.GONE);
		loadingText.setVisibility(View.GONE);

		headerText.setVisibility(View.VISIBLE);
		headerText1.setVisibility(View.VISIBLE);
		headerText.setText(pullLabel);
		headerText1.setText(pullLabel1);
		headerImage.setVisibility(View.VISIBLE);

		if (flagShow)
			viewVisible();
	}

	public void releaseToRefresh() {
		currentTime = getMyCurrentTime();
		this.releaseLabel1 = mContext.getResources().getString(R.string.pull_loading_new_geng) + currentTime;
		oldTime = currentTime;// 璁颁綇褰撳墠鏇存柊鏃堕棿锛屼笅娆′笅鎷夋樉绀�
		headerText.setVisibility(View.VISIBLE);
		headerText1.setVisibility(View.VISIBLE);
		headerText.setText(releaseLabel);
		headerText1.setText(releaseLabel1);
		headerImage.clearAnimation();
		headerImage.startAnimation(rotateAnimation);

		if (flagShow)
			viewVisible();
	}

	public void setPullLabel(String pullLabel) {
		this.pullLabel = pullLabel;
	}

	public void refreshing() {

		loadingText.setText(refreshingLabel);
		loadingText.setVisibility(View.VISIBLE);

		headerImage.clearAnimation();
		headerText.setVisibility(View.GONE);
		headerText1.setVisibility(View.GONE);
		headerImage.setVisibility(View.GONE);
		headerProgress.setVisibility(View.VISIBLE);

		if (flagShow)
			viewVisible();
	}

	public void setRefreshingLabel(String refreshingLabel) {
		this.refreshingLabel = refreshingLabel;
	}

	public void setReleaseLabel(String releaseLabel) {
		this.releaseLabel = releaseLabel;
	}

	public void pullToRefresh() {
		headerText.setText(pullLabel);
		headerImage.clearAnimation();
		headerImage.startAnimation(resetRotateAnimation);
	}

	public void setTextColor(int color) {
		headerText.setTextColor(color);
	}

}
