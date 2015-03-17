package com.example.kanoe_android_app;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProjectsDataBaseHandler extends SQLiteOpenHelper {
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "projectsManager";
 
    // Contacts table name
    private static final String TABLE_CONTACTS = "projects";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
     static final String KEY_TITLE = "title";
     static final String KEY_DESCRIPTION = "description";
    private static final String KEY_PARTICIPANTS = "participants";
    
    public ProjectsDataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
 // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                 + KEY_DESCRIPTION + " TEXT," +KEY_PARTICIPANTS+ " TEXT)";
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
    public void addContact(Projects event) 
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, event.getTitle());
        values.put(KEY_DESCRIPTION, event.getDescription());
        values.put(KEY_PARTICIPANTS, event.getParticipants());
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }
    
 // Getting All Contacts
    public List<Projects> getAllEvents() 
    {
    	 List<Projects> eventList = new ArrayList<Projects>();
    	    // Select All Query
    	    String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
    	 
    	    SQLiteDatabase db = this.getWritableDatabase();
    	    Cursor cursor = db.rawQuery(selectQuery, null);
    	 
    	    // looping through all rows and adding to list
    	    if (cursor.moveToFirst()) {
    	        do {
    	            Projects contact = new Projects();
    	            contact.setID(Integer.parseInt(cursor.getString(0)));
    	            contact.setTitle(cursor.getString(1));
    	            contact.setDescription(cursor.getString(2));
    	            contact.setParticipants(cursor.getString(3));
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
