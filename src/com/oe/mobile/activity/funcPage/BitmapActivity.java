package com.oe.mobile.activity.funcPage;

import java.io.IOException;

import com.oe.mobile.R;
import com.oe.mobile.R.id;
import com.oe.mobile.R.layout;
import com.oe.mobile.R.menu;
import com.oe.mobile.retired.ImageThread;
import com.oe.mobile.retired.ModelView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BitmapActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bitmap);

		final ImageView iv = (ImageView) findViewById(R.id.testBitmap);

		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 0x113) {
					// get the search result from the msg
					String s = (String) msg.obj;
					iv.setImageBitmap(getBitmapByString(s));
				}
			}
		};
		
		try {
			new Thread(new ImageThread(handler)).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_bitmap, menu);
		return true;
	}

	public static Bitmap getBitmapByString(String s) {
		// 将字符串转换成Bitmap类型
		Bitmap bitmap = null;
		try {
			byte[] bitmapArray;
			bitmapArray = Base64.decode(s, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
					bitmapArray.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}

/*
 * //将字符串转换成Bitmap类型 Bitmap bitmap=null; try { byte[]bitmapArray;
 * bitmapArray=Base64.decode(string, Base64.DEFAULT);
 * bitmap=BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length); }
 * catch (Exception e) { e.printStackTrace(); }
 * 
 * return bitmap; }
 * 
 * 
 * 
 * public String bitmaptoString(Bitmap bitmap){ //将Bitmap转换成字符串 String
 * string=null; ByteArrayOutputStream bStream=new ByteArrayOutputStream();
 * bitmap.compress(CompressFormat.PNG,100,bStream);
 * byte[]bytes=bStream.toByteArray();
 * string=Base64.encodeToString(bytes,Base64.DEFAULT); return string; }
 * 
 * ----将数据传递给服务端的时候报错，这时候就得转化一下编码格式，将获取回来的String进行转码，转成UTF-8这样解决了非法字符的问题 String
 * data1 = Base64.encodeToString(data, 0, data.length,Base64.DEFAULT);
 */