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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;

public class EventActivity extends Activity implements DownloadResultReceiver.Receiver,LoaderManager.LoaderCallbacks<Cursor> {
    private DownloadResultReceiver mReceiver;
    ArrayAdapterProject adapter;
    SimpleCursorAdapter ada;
    final String url = "https://kanoe-api-server.herokuapp.com/events/get";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String orient = prefs.getString("portrait", null);
        if (orient.equals("true")) {
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        String theme = prefs.getString("theme", null);
        Log.e("theme", theme);
        if (theme.equals(null) || theme.equals("light")) {
            ActionBar bar = getActionBar();
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff00a1e2")));
        } else {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event, menu);
        return true;
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        ArrayAdapterEvent adapter;
        switch (resultCode) {
            case DownloadService.STATUS_RUNNING:

                setProgressBarIndeterminateVisibility(true);
                break;
            case DownloadService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                setProgressBarIndeterminateVisibility(false);
                ContentValues values = new ContentValues();
                String[] results = resultData.getStringArray("result");
                final String result = results[0];
                ListView l;
                l = (ListView) findViewById(R.id.listview2);
                if (!(result == null)) {


                    JSONArray ja;
                    String title[] = null;

                    try {

                        ja = new JSONArray(result);
                        title = new String[ja.length()];
                        for (int i = 0; i < title.length; i++) {
                            try {
                                title[i] = ja.getJSONObject(i).getString("title");
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        /////
                        Uri resultUri;
                        Uri contentUri = Uri.withAppendedPath(EventsProvider.CONTENT_URI, "events");
                        int resultUri1 = getApplicationContext().getContentResolver().delete(contentUri, null, null);
                        ContentValues initialValues;
                        for (int i = 0; i < title.length; i++) {
                            initialValues = new ContentValues();
                            initialValues.put("id", ja.getJSONObject(i).getString("id"));
                            initialValues.put("title", ja.getJSONObject(i).getString("title"));
                            initialValues.put("date", ja.getJSONObject(i).getString("date"));
                            initialValues.put("place", ja.getJSONObject(i).getString("place"));
                            initialValues.put("description", ja.getJSONObject(i).getString("description"));
                            initialValues.put("remarks", ja.getJSONObject(i).getString("remarks"));
                            resultUri = getApplicationContext().getContentResolver().insert(contentUri, initialValues);
                        }


                        /////
                        adapter = new ArrayAdapterEvent(getBaseContext(), title);
                        l.setAdapter(adapter);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    l.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1,
                                                int arg2, long arg3) {

                            Intent i = new Intent(getApplicationContext(), EventDisplay.class);
                            try {
                                i.putExtra("title", new JSONArray(result).getJSONObject(arg2).getString("title"));
                                i.putExtra("date", new JSONArray(result).getJSONObject(arg2).getString("date"));
                                i.putExtra("place", new JSONArray(result).getJSONObject(arg2).getString("place"));
                                i.putExtra("description", new JSONArray(result).getJSONObject(arg2).getString("description"));
                                i.putExtra("remarks", new JSONArray(result).getJSONObject(arg2).getString("remarks"));
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            startActivity(i);
                        }
                    });


                } else {
                    EventsDataBaseHandler db = new EventsDataBaseHandler(getApplicationContext());
                    List<Events> contacts = db.getAllEvents();
                    if (!(contacts == null)) {
                        final String title[] = new String[5];
                        final String date[] = new String[5];
                        final String place[] = new String[5];
                        final String description[] = new String[5];
                        final String remarks[] = new String[5];
                        int k = 0;
                        for (Events cn : contacts) {
                            title[k] = cn.getTitle();
                            date[k] = cn.getDate();
                            place[k] = cn.getPlace();
                            description[k] = cn.getDescription();
                            remarks[k] = cn.getRemarks();

                            k++;
                        }
                        ada = new SimpleCursorAdapter(getBaseContext(), R.layout.activity_project_display,null, new String[] {EventsDataBaseHandler.KEY_TITLE,EventsDataBaseHandler.KEY_DESCRIPTION},new int[] {R.id.title,R.id.description},0);
                        adapter = new ArrayAdapterEvent(getBaseContext(), title);
                        l.setAdapter(adapter);
                        l.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> arg0, View arg1,
                                                    int arg2, long arg3) {
                                //String item = adapter.getItem(arg2);
                                Intent i = new Intent(getApplicationContext(), EventDisplay.class);
                                i.putExtra("title", title[arg2]);
                                i.putExtra("date", date[arg2]);
                                i.putExtra("place", place[arg2]);
                                i.putExtra("description", description[arg2]);
                                i.putExtra("remarks", remarks[arg2]);
                                startActivity(i);
                            }
                        });
                    }
                }
        }



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