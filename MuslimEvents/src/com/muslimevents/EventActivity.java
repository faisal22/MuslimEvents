package com.muslimevents;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class EventActivity extends Activity {
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
		
		TextView title, description, location, startTime, endTime;
		title = (TextView)this.findViewById(R.id.title);
		description = (TextView)this.findViewById(R.id.theDescription);
		location = (TextView)this.findViewById(R.id.theLocation);
		startTime = (TextView)this.findViewById(R.id.theStartTime);
		endTime = (TextView)this.findViewById(R.id.theEndTime);
		
		String stuffString = this.getIntent().getExtras().getString("info");
		try{
			JSONObject stuff = new JSONObject(stuffString);
			title.setText(stuff.getString("title"));
			description.setText(stuff.getString("description"));
			startTime.setText(stuff.getString("starts"));
			endTime.setText(stuff.getString("ends"));
			location.setText(stuff.getString("location"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event, menu);
		return true;
	}

}
