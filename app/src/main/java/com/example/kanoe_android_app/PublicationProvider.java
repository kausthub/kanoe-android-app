package com.example.kanoe_android_app;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class PublicationProvider extends ContentProvider {

private PublicationsDatabaseHandler edb;
	
	private static final String AUTHORITY = "com.example.kanoe.PublicationsProvider1";
	public static final int TUTORIALS = 100;
	public static final int TUTORIAL_ID = 110;
	 
	private static final String TUTORIALS_BASE_PATH = "";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
	        + "/" + TUTORIALS_BASE_PATH);
	 @Override
	 public boolean onCreate() {
	  edb = new PublicationsDatabaseHandler(getContext());
	  return true;
	 }
	 @Override
	 public int delete(Uri uri, String where, String[] args) {
	  String table = getTableName(uri);
	     SQLiteDatabase dataBase=edb.getWritableDatabase();
	     return dataBase.delete(table, where, args);
	 }
	 public static String getTableName(Uri uri){
		  String value = uri.getPath();
		  value = value.replace("/", "");//we need to remove '/'
		  return value;
	 }
	 @Override
	 public String getType(Uri arg0) {
	  // TODO Auto-generated method stub
	  return null;
	 }

	 
	 @Override
	 public Uri insert(Uri uri, ContentValues initialValues) {
	  String table = getTableName(uri);
	  SQLiteDatabase database = edb.getWritableDatabase();
	  long value = database.insert(table, null, initialValues);
	  return Uri.withAppendedPath(CONTENT_URI, String.valueOf(value));
	 }

	 @Override
	 public Cursor query(Uri uri, String[] projection, String selection,
	      String[] selectionArgs, String sortOrder) {
	  String table =getTableName(uri);
	  SQLiteDatabase database = edb.getReadableDatabase();
	  Cursor cursor =database.query(table,  projection, selection, selectionArgs, null, null, sortOrder);
	     return cursor;
	 }  

	 @Override
	 public int update(Uri uri, ContentValues values, String whereClause,
	      String[] whereArgs) {
	  String table = getTableName(uri);
	  SQLiteDatabase database = edb.getWritableDatabase();  
	  return database.update(table, values, whereClause, whereArgs);
	 }

}
