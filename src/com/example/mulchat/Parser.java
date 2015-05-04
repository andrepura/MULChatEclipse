package com.example.mulchat;

import java.util.Date;

import org.json.JSONObject;

import com.example.mulchat.data.ChatEntry;
import com.example.mulchat.data.Sender;

public class Parser {

	public static ChatEntry parseChatEntry(Object input) throws Exception{
		if(input == null) throw new Exception("input is null");
		JSONObject obj = (JSONObject) input;
		String mid = getStringRecursive(obj, new String[] { "mid"});
		String timestamp = getStringRecursive(obj, new String[] { "timestamp"});
		Date dateTimestamp = new Date(Long.parseLong(timestamp) * 1000);
		
		String sid = getStringRecursive(obj, new String[] { "sender","sid"});
		String name = getStringRecursive(obj, new String[] { "sender","name"});
		
		String msg = getStringRecursive(obj, new String[] { "msg"});
		
		ChatEntry entry = new ChatEntry(new Sender(sid, name), mid, dateTimestamp);
		if(msg != null)
			entry.setMsg(msg);
		
		return entry;
	}
	
	/**
	 * get a string recursively from a json object identified by an array of
	 * identifiers
	 * 
	 * @param org.json.JSONObject
	 * @param identifiers
	 *            (PATH TO STRING in json object)
	 * @return string or null
	 * @throws Exception
	 */
	public static String getStringRecursive(JSONObject obj, String[] identifiers)
			throws Exception {
		if (identifiers.length == 1) {
			if (obj.has(identifiers[0])) {
				return obj.getString(identifiers[0]);
			} else {
				return null;
			}
		} else if (identifiers.length > 1) {
 
			if (obj.has(identifiers[0])) {
 
				String[] ids = new String[identifiers.length - 1];
				for (int i = 1; i < identifiers.length; i++) {
					ids[i - 1] = identifiers[i];
				}
				JSONObject objNew = obj.getJSONObject(identifiers[0]);
				return getStringRecursive(objNew, ids);
			} else {
				return null;
			}
 
		} else {
			return null;
		}
 
	}
}
