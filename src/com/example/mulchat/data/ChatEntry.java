package com.example.mulchat.data;

import java.util.Date;

import org.json.JSONObject;

public class ChatEntry {

	String mid;
	Date timestamp;
	Sender sender;
	String msg;
	Location location;
		
	public ChatEntry(Sender sender, String mid, Date timestamp) {
		super();
		this.sender = sender;
		this.mid = mid;
		this.timestamp = timestamp;
	}
	
	public ChatEntry(Sender sender,  String mid, Date timestamp, String msg) {
		this(sender,mid,timestamp);
		this.msg=msg;
	}
	
	
	public ChatEntry(Sender sender,  String mid, Date timestamp, String msg, Location location) {
		this(sender,mid,timestamp,msg);
		this.location = location;
	}
	
	public Sender getSender() {
		return sender;
	}
	public void setSender(Sender sender) {
		this.sender = sender;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public JSONObject toJSONObj() throws Exception{
		if(this.sender == null) throw new Exception("Sender is null");
		JSONObject obj = new JSONObject();
		
		//add sender
		obj.put("sender", sender.toJSONObj());
		//add msg
		if(msg !=null){
			obj.put("msg", msg);
		}
		//add location
		if(location != null){
			obj.put("location", location.toJSONObj());
		}
		
		return obj;
	}
	
}
