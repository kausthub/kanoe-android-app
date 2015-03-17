package com.example.kanoe_android_app;

public class Publications {
	int _id;
	String author,coauthors,area,date,description;
	public Publications(int _id, String author, String coauthors, String area, String date, String description)
	{
		this._id = _id;
		this.date = date;
		this.description = description;
		this.date = date;
		this.author = author;
		this.coauthors = coauthors;
	}
	public Publications() 
	{
		
	}
	public int getID(){
	    return this._id;
	}
	public void setID(int id){
	    this._id = id;
	}
	public String getDate(){
	    return this.date;
	}
	public void setDate(String date){
	    this.date = date;
	}
	public String getArea(){
	    return this.area;
	}
	public void setArea(String place){
	    this.area = place;
	}
	public String getDescription(){
	    return this.description;
	}
	public void setDescription(String desc){
	    this.description = desc;
	}
	public String getAuthor(){
	    return this.author;
	}
	public void setAuthor(String r){
	    this.author = r;
	}
	public String getCoauthors(){
	    return this.coauthors;
	}
	public void setCoauthors(String r){
	    this.coauthors = r;
	}
}
