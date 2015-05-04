package com.example.mulchat.adapter;
import java.text.SimpleDateFormat;
import java.util.Comparator;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.mulchat.R;
import com.example.mulchat.data.ChatEntry;

public class ChatListAdapater extends ArrayAdapter<ChatEntry> implements OnItemClickListener{
	private final Context context;
	int resourceEntry;
	
	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	
	public ChatListAdapater(Context context, int resource) {
		super(context, resource);
		this.resourceEntry = resource;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(resourceEntry, parent, false);
		
		ChatEntry e =getItem(position);
		
		TextView txtName = (TextView) rowView.findViewById(R.id.txtItemName);
		TextView txtMsg = (TextView) rowView.findViewById(R.id.txtItemContent);
		
		txtName.setText(e.getSender().getName()+" @ "+df.format(e.getTimestamp()));
		txtMsg.setText(e.getMsg());
		
		return rowView;
	}
	
	@Override
	public void add(ChatEntry object) {
		for(int i = 0 ; i < getCount(); i++){
			if(object.getMid().equals(getItem(i).getMid())){
				return;
			}
		}
		
		super.add(object);
		
		sort(new Comparator<ChatEntry>() {
			@Override
			public int compare(ChatEntry lhs, ChatEntry rhs) {
				return (int) (rhs.getTimestamp().getTime()-lhs.getTimestamp().getTime());
			}
		});
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
}
