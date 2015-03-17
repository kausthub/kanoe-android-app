package com.example.kanoe_android_app;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PublicationsDatabaseHandler extends SQLiteOpenHelper{
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "publicationsManager";
 
    // Contacts table name
    private static final String TABLE_CONTACTS = "publications";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_AREA = "area";
     static final String KEY_DESCRIPTION = "description";
     static final String KEY_AUTHOR = "author";
    private static final String KEY_COAUTHORS = "coauthors";
    
    public PublicationsDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
 // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_AUTHOR + " TEXT,"
                + KEY_COAUTHORS + " TEXT,"+ KEY_AREA + " TEXT," +KEY_DATE+ " TEXT," +KEY_DESCRIPTION+ " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
 
        // Create tables again
        onCreate(db);
    }
    
 // Adding new contact
    public void addContact(Publications event) 
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(KEY_AUTHOR, event.getAuthor()); 
        values.put(KEY_COAUTHORS, event.getCoauthors()); 
        values.put(KEY_AREA, event.getArea());
        values.put(KEY_DATE, event.getDate());
        values.put(KEY_DESCRIPTION, event.getDescription());
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }
    
 // Getting All Contacts
    public List<Publications> getAllEvents() 
    {
    	 List<Publications> eventList = new ArrayList<Publications>();
    	    // Select All Query
    	    String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
    	 
    	    SQLiteDatabase db = this.getWritableDatabase();
    	    Cursor cursor = db.rawQuery(selectQuery, null);
    	 
    	    // looping through all rows and adding to list
    	    if (cursor.moveToFirst()) {
    	        do {
    	            Publications contact = new Publications();
    	            contact.setID(Integer.parseInt(cursor.getString(0)));
    	            contact.setAuthor(cursor.getString(1));
    	            contact.setCoauthors(cursor.getString(2));
    	            contact.setArea(cursor.getString(3));
    	            contact.setDate(cursor.getString(4));
    	            contact.setDescription(cursor.getString(5));
    	            // Adding contact to list
    	            eventList.add(contact);
    	        } while (cursor.moveToNext());
    	    }
    	 
    	    // return contact list
    	    return eventList;
    }
    
    public void deleteEvents() 
    {
    	//String selectQuery = "DELETE FROM " + TABLE_CONTACTS;
	    SQLiteDatabase db = this.getWritableDatabase();
	    db.execSQL("delete from "+ TABLE_CONTACTS);
	    //db.rawQuery(selectQuery, null);
	    db.close();
    }
}
