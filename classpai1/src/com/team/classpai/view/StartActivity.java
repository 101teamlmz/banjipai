package com.team.classpai.view;


import com.team.classpai.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

public class StartActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(StartActivity.this, HomeActivity.class);
				startActivity(intent);
				StartActivity.this.finish();
			}
		}, 3500);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Builder builder = new Builder(StartActivity.this);
			builder.setTitle("提示");
			builder.setMessage("你确定要退出吗？");
			builder.setIcon(R.drawable.ic_launcher);
			
			DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					if (arg1 == DialogInterface.BUTTON_POSITIVE) {
						StartActivity.this.finish();
						
					} else if (arg1 == DialogInterface.BUTTON_NEGATIVE) {
						arg0.cancel();
					}
				}
			};
			builder.setPositiveButton("确定", dialog);
			builder.setNegativeButton("取消", dialog);
			AlertDialog alertDialog = builder.create();
			alertDialog.show();

		}
		return super.onKeyDown(keyCode, event);
}
}