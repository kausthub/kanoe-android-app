package com.example.kanoe_android_app;

public class Projects {
	int _id;
	String title,participants,description;
	public Projects(int _id, String title, String description, String participants)
	{
		this._id = _id;
		this.title = title;
		this.description = description;
		this.participants = participants;
	}
	public Projects()
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
	public String getDescription(){
	    return this.description;
	}
	public void setDescription(String desc){
	    this.description = desc;
	}
	public String getParticipants(){
	    return this.participants;
	}
	public void setParticipants(String r){
	    this.participants = r;
	}
}
