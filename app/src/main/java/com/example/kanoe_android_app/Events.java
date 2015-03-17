package com.example.kanoe_android_app;

public class Events {
	int _id;
	String title,date,place,description,remarks;
	public Events(int _id, String title, String date, String place, String description, String remarks)
	{
		this._id = _id;
		this.title = title;
		this.date = date;
		this.description = description;
		this.remarks = remarks;
	}
	public Events()
	{
		
	}
	public int getID(){
	    return this._id;
	}
	public void setID(int id){
	    this._id = id;
	}
	public String getTitle(){
	    return this.title;
	}
	public void setTitle(String title){
	    this.title = title;
	}
	public String getDate(){
	    return this.date;
	}
	public void setDate(String date){
	    this.date = date;
	}
	public String getPlace(){
	    return this.place;
	}
	public void setPlace(String place){
	    this.place = place;
	}
	public String getDescription(){
	    return this.description;
	}
	public void setDescription(String desc){
	    this.description = desc;
	}
	public String getRemarks(){
	    return this.remarks;
	}
	public void setRemarks(String r){
	    this.remarks = r;
	}
}

