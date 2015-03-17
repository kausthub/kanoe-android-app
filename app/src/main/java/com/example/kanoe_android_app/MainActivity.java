package com.example.kanoe_android_app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	SharedPreferences.Editor editor;
ImageButton ib1;
ImageButton ib2;
ImageButton ib3;
ImageButton ib4;
Button b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		ib1 = (ImageButton)findViewById(R.id.imageButton1);
		ib2 = (ImageButton)findViewById(R.id.imageButton2);
		ib3 = (ImageButton)findViewById(R.id.imageButton3);
		ib4 = (ImageButton)findViewById(R.id.imageButton4);
		try{
			SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
			String theme = prefs.getString("theme", null);
			Log.e("theme",theme);
			if(theme.equals(null) || theme.equals("light"))
			{
				ActionBar bar = getActionBar();
				bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff00a1e2")));
			}
			else
			{
				ActionBar bar = getActionBar();
				bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#111111")));
			}
		}
		catch(Exception e)
		{
			editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        	editor.putString("theme", "light");
        	editor.putString("portrait", "false");
        	editor.commit();
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff00a1e2")));
		}

	}
	public void gotoEvents(View v)
	{
		Intent i = new Intent(this,EventActivity.class);
		startActivity(i);
	}
	public void gotoProjects(View v)
	{
		Intent i = new Intent(this,ProjectActivity.class);
		startActivity(i);
	}
	public void gotoPublications(View v)
	{
		Intent i = new Intent(this,PublicationActivity.class);
		startActivity(i);
	}
	public void gotoAbout(View v)
	{
		Intent i = new Intent(this,AboutActivity.class);
		startActivity(i);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent in = new Intent(this, SettingActivity.class);
			startActivity(in);
		}
		return super.onOptionsItemSelected(item);
	}
}
