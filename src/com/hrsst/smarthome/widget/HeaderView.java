package com.hrsst.smarthome.widget;

import java.io.File;

import com.hrsst.smarthome.dtsj.R;
import com.hrsst.smarthome.global.NpcCommon;
import com.hrsst.smarthome.util.ImageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;

public class HeaderView extends ImageView {
	Bitmap tempBitmap;
	private Context mContext;
	
	public HeaderView(Context context) {
		super(context);
		this.mContext=context;
	}

	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		// TypedArray array = context.obtainStyledAttributes(attrs,
		// R.styleable.);
		this.mContext = context;
	}

	/**
	 * 根据SD卡中的图片刷新显示的图片。。
	 * @param threeNum
	 * @param isGray
	 */
	public void updateImage(String threeNum, boolean isGray) {
		updateImage(threeNum, isGray, -1);
	}

	public void updateImage(String threeNum, boolean isGray, int oritation) {
//		if(NpcCommon.mThreeNum==null||NpcCommon.mThreeNum.length()<=0){
//			NpcCommon.mThreeNum="0517401";
//		}
		try {
			tempBitmap = ImageUtils.getBitmap(new File(
					"/sdcard/screenshot/tempHead/" + NpcCommon.mThreeNum + "/"
							+ threeNum + ".jpg"), 200, 200);
			if (oritation != -1) {
//				tempBitmap = ImageUtils.roundHalfCorners(mContext, tempBitmap,
//						5, oritation);
			} else {
				tempBitmap = ImageUtils.roundCorners(tempBitmap,
						ImageUtils.getScaleRounded(tempBitmap.getWidth()));
			}
			this.setImageBitmap(tempBitmap);
		} catch (Exception e) {
			tempBitmap = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.header_icon);
			if (oritation != -1) {
				tempBitmap = ImageUtils.roundHalfCorners(mContext, tempBitmap,
						5, oritation);
			} else {
				tempBitmap = ImageUtils.roundCorners(tempBitmap,
						ImageUtils.getScaleRounded(tempBitmap.getWidth()));
			}
			this.setImageBitmap(tempBitmap);
		}
	}
	
	public Bitmap getBitmap(){
		return tempBitmap;
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

}

