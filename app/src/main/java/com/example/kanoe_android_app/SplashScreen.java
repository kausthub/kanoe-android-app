package com.example.kanoe_android_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

	private static final int SPLASH_TIME = 1 * 1500;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(SplashScreen.this,
						MainActivity.class);
				startActivity(intent);
				SplashScreen.this.finish();
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		}, SPLASH_TIME);
		new Handler().postDelayed(new Runnable() {
			  @Override
			  public void run() {
			         } 
			    }, SPLASH_TIME);
	} 
	@Override
	public void onBackPressed() {
		this.finish();
		super.onBackPressed();
	}
}
