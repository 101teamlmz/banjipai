package com.team.classpai.view;


import com.team.classpai.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.ImageView;

public class WelcomeActivity extends Activity {

	private Handler mHandler = new Handler();
	private ImageView iv_welcome;
	
	private int alpha = 255;
	private int b = 0;
	@SuppressLint("HandlerLeak")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//FullScreen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_welcome);
		
		iv_welcome=(ImageView) findViewById(R.id.image_start1);
		
		iv_welcome.setAlpha(alpha);
		new Thread(new Runnable() {
			public void run() {
				while (b < 2) {
					try {
						if (b == 0) {
							Thread.sleep(650);
							b = 1;
						} else {
							Thread.sleep(200);
						}

						updateApp();

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				iv_welcome.setAlpha(alpha);
				iv_welcome.invalidate();
			}
		};
	}
	
	
	public void updateApp() {
		alpha -= 11;
		//避免出现白屏
		if (alpha <= 30) {
			b = 2;
			Intent intent = new Intent(WelcomeActivity.this,HomeActivity.class);
			startActivity(intent);
			this.finish();
			//查询需要很多内存开销，提前回收一些
			System.gc(); 
		}

		mHandler.sendMessage(mHandler.obtainMessage());
	}
}
