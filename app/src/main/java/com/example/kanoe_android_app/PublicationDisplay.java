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

public class PublicationDisplay extends Activity {
	TextView t1;
	TextView t2;
	TextView t3;
	TextView t4;
	TextView t5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publication_display);
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
		String author,coauthors,area,description,date;
		author = getIntent().getExtras().getString("author");
		description = getIntent().getExtras().getString("description");
		coauthors = getIntent().getExtras().getString("coauthors");
		area = getIntent().getExtras().getString("area");
		date = getIntent().getExtras().getString("date");
		t1 = (TextView)findViewById(R.id.area);
		t2 = (TextView)findViewById(R.id.author);
		t3 = (TextView)findViewById(R.id.coauthors);
		t4 = (TextView)findViewById(R.id.description);
		t5 = (TextView)findViewById(R.id.date);
		try{
			t1.setText(area);
			t2.setText(author);
			t3.setText(coauthors);
			t4.setText(description);
			t5.setText(date);
		}
		catch(Exception e)
		{
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.publication_display, menu);
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
