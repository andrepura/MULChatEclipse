package com.example.mulchat.data;

import org.json.JSONException;
import org.json.JSONObject;


public class Sender {
	String sid;
	String name;
	String color;
	
	
	public Sender(String sid, String name) {
		this(sid,name,"");
		color = '#'+Integer.toHexString((int) (Math.random()*0xFFFFFF));
	}
	public Sender(String sid, String name, String color) {
		super();
		this.sid = sid;
		this.name = name;
		this.color = color;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public JSONObject toJSONObj() throws JSONException{
		JSONObject obj = new JSONObject();
		obj.put("sid", getSid());
		obj.put("name", getName());
		if(getColor() != null){
			obj.put("color", getColor());
		}
		return obj;
	}
	
}
