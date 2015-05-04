package com.example.mulchat;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONObject;

import com.example.mulchat.Websocket.WebSocketCallback;
import com.example.mulchat.adapter.ChatListAdapater;
import com.example.mulchat.data.ChatEntry;
import com.example.mulchat.data.Sender;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements WebSocketCallback {
	
	public static final String LOG_TAG ="MULCHAT";

	ListView listMain;
	EditText txtInput;
	Button btnSend;
	
	Websocket websocket;
	Sender sender;
	
	
	ChatListAdapater listAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listMain = (ListView) findViewById(R.id.listMainListView);
		txtInput = (EditText) findViewById(R.id.txtInput);
		btnSend = (Button) findViewById(R.id.btnSend);
		
		sender = new Sender(UUID.randomUUID().toString(), "andre");
		websocket = new Websocket(this, "http://mul.pura.at");
		
		listAdapter = new ChatListAdapater(getApplicationContext(), R.layout.listitem);
		listMain.setAdapter(listAdapter);
		
		txtInput.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					switch (keyCode) {
					case KeyEvent.KEYCODE_DPAD_CENTER:
					case KeyEvent.KEYCODE_ENTER:
						emitTextToServer();
						return true;
					default:
						break;
					}
				}
				return false;
			}
		});
		
		btnSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				emitTextToServer();
			}
		});
		
		try {
			websocket.init();
		} catch (Exception e) {
			Log.e(LOG_TAG,"ERROR: "+e.toString());
			makeToast("ERROR CONNECTING TO WEBSOCKET !!!!");
		}
	}
	
	public void emitTextToServer(){
		try{
			JSONObject obj = new JSONObject();
			obj.put("sender", sender.toJSONObj());
			obj.put("msg", txtInput.getText().toString());
			websocket.emitChatMessage(obj);	
			txtInput.setText("");
			txtInput.requestFocus();
		} catch(Exception e){
			Log.e(LOG_TAG,"ERROR: "+e.toString());
		}
	}

	public void addChatEntry(final ChatEntry entry){
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				listAdapter.add(entry);
			}
		});
	}
	
	@Override
	public void onChatEntryReceived(Object chatObj) {
		Log.d(LOG_TAG, "chat msg");
		try{
			addChatEntry(Parser.parseChatEntry(chatObj));
		} catch(Exception e){
			Log.e(LOG_TAG, "ERROR: "+e.toString());
		}
	}

	@Override
	public void onConnect() {
		Log.d(LOG_TAG, "connected");
		makeToast("Connected to server");
	}

	@Override
	public void onDisconnect() {
		Log.d(LOG_TAG, "disconnected");
		makeToast("Disonnected to server");
	}

	@Override
	public void onChatError(Object chatObj) {
		Log.d(LOG_TAG, "chat error");
	}

	@Override
	public void onConnectionError() {
		Log.d(LOG_TAG, "connection error");
	}
	
	public void makeToast(final String txt){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_LONG).show();				
			}
		});

	}
}
