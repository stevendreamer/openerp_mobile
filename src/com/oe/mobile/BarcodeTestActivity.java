package com.oe.mobile;

import com.zijunlin.Zxing.Demo.CaptureActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.graphics.Bitmap;

public class BarcodeTestActivity extends Activity {

	ImageButton imgBtn;
	EditText barcodeTxt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_barcode_test);

		imgBtn = (ImageButton) findViewById(R.id.scanButton);
		barcodeTxt = (EditText) findViewById(R.id.barcodeText);
		imgBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(BarcodeTestActivity.this,CaptureActivity.class);  
	            //关键点来了，使用startActivityForResult来启动  
	            startActivityForResult(intent, 100);  

			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_barcode_test, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 可以根据多个请求代码来作相应的操作
		if (20 == resultCode) {

			String format = data.getExtras().getString("format");
			String barcode = data.getExtras().getString("barcode");
			// BitMap image = data.getExtras().get("image");
			barcodeTxt.setText(barcode);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
