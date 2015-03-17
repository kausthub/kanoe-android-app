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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class SettingActivity extends Activity {
	SharedPreferences.Editor editor;
	ActionBar bar;
	CheckBox x;
	RadioButton r1,r2;
	RadioGroup ItemtypeGroup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE); 
		String orient = prefs.getString("portrait", null);
		String theme = prefs.getString("theme", null);
		Log.e("theme",theme);
		x = (CheckBox)findViewById(R.id.checkBox1);
		if( orient.equals("true"))
		{
			x.setChecked(true);
			SettingActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
		r1 = (RadioButton)findViewById(R.id.radio0);
		r2 = (RadioButton)findViewById(R.id.radio1);
		if(theme.equals("light"))
		{
			r1.setChecked(true);
			r2.setChecked(false);
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff00a1e2")));
		}
		else
		{
			r2.setChecked(true);
			r1.setChecked(false);
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#111111")));
		}
		 ItemtypeGroup = (RadioGroup) findViewById(R.id.radioGroup1);
	    ItemtypeGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

	                @Override
	                public void onCheckedChanged(RadioGroup group, int checkedId) {
	                    if (checkedId == R.id.radio0) {
	                    	ActionBar bar = getActionBar();
	                    	bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff00a1e2")));
	                    	
	                    	editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
	                    	editor.putString("theme", "light");
	                    	//bar = getActionBar();
	                    	//bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff00a1e2")));
	                    	editor.commit();
	                    } else if (checkedId == R.id.radio1) {
	                    	editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
	                    	editor.putString("theme", "dark");
	                    	bar = getActionBar();
	                    	bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#111111")));
	                    	editor.commit();
	                    }
	                }
	            });
	    x = (CheckBox)findViewById(R.id.checkBox1);
	    x.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

	        @Override
	        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
	        	if(isChecked)
	        	{
	        		SettingActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        		editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
                	editor.putString("portrait", "true");
                	editor.commit();
	        	}
	        	else
	        	{
	        		editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
                	editor.putString("portrait", "false");
                	editor.commit();
	        	}
	        }
	    }
	 );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
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
