package com.example.kanoe_android_app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class EventDisplay extends Activity {
	TextView t1;
	TextView t2;
	TextView t3;
	TextView t4;
	TextView t5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_display);
		SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE); 
		String orient = prefs.getString("portrait", null);
		if(orient.equals("true"))
		{
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
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
		String title,date,place,description,remarks;
		title = getIntent().getExtras().getString("title");
		description = getIntent().getExtras().getString("description");
		date = getIntent().getExtras().getString("date");
		remarks = getIntent().getExtras().getString("remarks");
		place = getIntent().getExtras().getString("place");
		t1 = (TextView)findViewById(R.id.title);
		t2 = (TextView)findViewById(R.id.place);
		t3 = (TextView)findViewById(R.id.date);
		t4 = (TextView)findViewById(R.id.description);
		t5 = (TextView)findViewById(R.id.remarks);
		t2.setText(place);
		t4.setText(description);
		t1.setText(title);
		t3.setText(date);
		t5.setText(remarks);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_display, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
