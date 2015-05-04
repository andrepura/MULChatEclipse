package com.example.mulchat;


import org.json.JSONObject;



import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.socketio.client.IO.Options;


public class Websocket {

	public String host="";
	public final static String LOG_TAG = "WEBSOCKET";
	
	Socket socket;
	WebSocketCallback callback;

	public Websocket(WebSocketCallback callback, String host) {
		this.callback = callback;
		this.host = host;
	}
	
	public void init() throws Exception{
		Log.d(LOG_TAG,"init websocket connection");
		Options opt = new Options();
		opt.forceNew = true;
		socket = IO.socket(host,opt);
		socket.off();
		
		socket.on("charerror", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				callback.onChatError(args[0]);
			}
		});
		socket.on("chat", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				callback.onChatEntryReceived(args[0]);
			}
		});
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				callback.onConnect();
			}
		});
		socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				callback.onDisconnect();
			}
		});
		socket.on(Socket.EVENT_ERROR, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				callback.onConnectionError();
			}
		});
		socket.connect();
	}
	
	public void emitChatMessage(JSONObject obj){
		socket.emit("chat",obj);
	}
	
	public static interface WebSocketCallback {
		void onChatEntryReceived(Object chatObj);
		void onConnect();
		void onDisconnect();
		void onChatError(Object chatObj);
		void onConnectionError();
	}
}
