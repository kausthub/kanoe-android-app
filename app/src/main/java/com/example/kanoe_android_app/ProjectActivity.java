package com.example.kanoe_android_app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ProjectActivity extends Activity implements DownloadResultReceiver.Receiver , LoaderManager.LoaderCallbacks<Cursor> {

	private DownloadResultReceiver mReceiver;
    ArrayAdapterProject adapter;
    SimpleCursorAdapter ada;
    final String url = "https://kanoe-api-server.herokuapp.com/projects/get";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project);
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
		
		mReceiver = new DownloadResultReceiver(new Handler());
		mReceiver.setReceiver(this);
		Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);

		/* Send optional extras to Download IntentService */
		intent.putExtra("url", url);
		intent.putExtra("receiver", mReceiver);
		intent.putExtra("requestId", 101);

		startService(intent);
	}
	
	@Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
		ArrayAdapterProject adapter;
		switch (resultCode) {
            case DownloadService.STATUS_RUNNING:

                setProgressBarIndeterminateVisibility(true);
                break;
            case DownloadService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                setProgressBarIndeterminateVisibility(false);
                ContentValues values = new ContentValues();

                String[] results = resultData.getStringArray("result");
                final String result=results[0];
                ListView l;
    	        l =(ListView)findViewById(R.id.listview);
    	    	if(result!=null)
    	    	{
    	        //super.onPostExecute(result);
    	        JSONArray ja;
    	        String title[] = null;
    	        
    	        try {
    	        	
    				 ja = new JSONArray(result);
    				title = new String[ja.length()];
    				for (int i=0;i<title.length;i++)
    		        {
    		        try {
    					title[i] = ja.getJSONObject(i).getString("title");
    				} catch (JSONException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    		        }
    				
    				
    				Uri resultUri;
    				Uri contentUri = Uri.withAppendedPath(ProjectsProvider.CONTENT_URI, "projects");
    				int resultUri1 = getApplicationContext().getContentResolver().delete(contentUri, null, null);
    				ContentValues  initialValues;
    				for(int i=0;i<title.length;i++)
    				{
    					initialValues = new ContentValues();
    					initialValues.put("id", ja.getJSONObject(i).getString("id"));
    					initialValues.put("title", ja.getJSONObject(i).getString("title"));
    					initialValues.put("description", ja.getJSONObject(i).getString("description"));
    					initialValues.put("participants", ja.getJSONObject(i).getString("participants"));
    					resultUri = getApplicationContext().getContentResolver().insert(contentUri, initialValues);
    				}
    				
    				
    				
    		         adapter = new ArrayAdapterProject(getBaseContext(), title);
    		        l.setAdapter(adapter);
    			} catch (JSONException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    	        l.setOnItemClickListener(new OnItemClickListener(){
    				@Override
    				public void onItemClick(AdapterView<?> arg0, View arg1,
    						int arg2, long arg3) {
    					//String item = adapter.getItem(arg2);
    					Intent i = new Intent(getApplicationContext(),ProjectDisplay.class);
    					try {
    						i.putExtra("title",new JSONArray(result).getJSONObject(arg2).getString("title"));
    						i.putExtra("participants",new JSONArray(result).getJSONObject(arg2).getString("participants"));
    						i.putExtra("description",new JSONArray(result).getJSONObject(arg2).getString("description"));
    					} catch (JSONException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    					startActivity(i);
    				}
    	        	});
    	            
    	    }
    	    	else
    	    	{
    	    		ProjectsDataBaseHandler db=new ProjectsDataBaseHandler(getApplicationContext());
    	    		List<Projects> contacts = db.getAllEvents();
    	    		if(!(contacts==null))
    	    		{
    	    			final String title[] = new String[5];
    	    			final String description[] = new String[5];
    	    			final String participants[] = new String[5];
    	    			int k=0;
    	    			for (Projects cn : contacts) {
    	    				title[k] = cn.getTitle();
    						description[k] = cn.getDescription();
    						participants[k] = cn.getParticipants();
    						
    						k++;
    	    			}
                        ada = new SimpleCursorAdapter(getBaseContext(), R.layout.activity_project_display,null, new String[] {ProjectsDataBaseHandler.KEY_TITLE,ProjectsDataBaseHandler.KEY_DESCRIPTION},new int[] {R.id.title,R.id.description},0);
    	    			adapter = new ArrayAdapterProject(getBaseContext(), title);
    	    			l.setAdapter(adapter);
    	    			l.setOnItemClickListener(new OnItemClickListener(){
    	    				@Override
    	    				public void onItemClick(AdapterView<?> arg0, View arg1,
    	    						int arg2, long arg3) {
    	    					//String item = adapter.getItem(arg2);
    	    					Intent i = new Intent(getApplicationContext(),ProjectDisplay.class);
    	    					i.putExtra("title",title[arg2]);
    							i.putExtra("description",description[arg2]);
    							i.putExtra("participants",participants[arg2]);
    	    					startActivity(i);
    	    				}
    	    	        	});
    	    		}
    	    	}
    	    	

                /* Update ListView with result */
                

                break;
            case DownloadService.STATUS_ERROR:
                /* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                break;
        }
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.project, menu);
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

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri uri = EventsProvider.CONTENT_URI;
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
        ada.swapCursor(arg1);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        ada.swapCursor(null);
    }


}
