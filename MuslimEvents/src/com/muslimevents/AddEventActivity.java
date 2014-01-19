package com.muslimevents;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AddEventActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		
		Button b = (Button) this.findViewById(R.id.submitButton);
		b.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				getInputs();
				finish();
			}
			
		});
		
	}
	
	public void getInputs(){
		EditText title, description, location, startTime, startDate, endTime, endDate;
		title = (EditText)this.findViewById(R.id.titleInput);
		description = (EditText)this.findViewById(R.id.descriptionInput);
		location = (EditText)this.findViewById(R.id.locationInput);
		startTime = (EditText)this.findViewById(R.id.startTimeInput);
		startDate = (EditText)this.findViewById(R.id.startDateInput);
		endTime = (EditText)this.findViewById(R.id.endTimeInput);
		endDate = (EditText)this.findViewById(R.id.endDateInput);
		
		String theTitle = title.getText().toString();
		String theDescription = description.getText().toString();
		String theStartDate = startDate.getText().toString();
		String theStartTime = startTime.getText().toString();
		String theEndDate = startTime.getText().toString();
		String theEndTime = endTime.getText().toString();
		String theLocation = location.getText().toString();
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
		
		String startDateSend = dateFormatter.format(theStartDate);
		String startTimeSend = timeFormatter.format(theStartTime);
		String finalStart = startDateSend + " " + startTimeSend;
		
		String endDateSend = dateFormatter.format(theEndDate);
		String endTimeSend = timeFormatter.format(theEndTime);
		String finalEnd = endDateSend + " " + endTimeSend;
		
		RequestParams rp = new RequestParams();
		rp.add("title", theTitle);
		rp.add("description", theDescription);
		rp.add("start_time", finalStart);
		rp.add("end_time", finalEnd);
		rp.add("location", theLocation);
		rp.add("org_name", this.getIntent().getExtras().getString("organization"));
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.put(
			"http://muslimevents.heroku.com/events/submit", rp, 
			new AsyncHttpResponseHandler(){

				@Override
				public void onSuccess(String content) {
					// TODO Auto-generated method stub
					Log.d("Blah","Like a boss");
				}
				
			}
		);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_event, menu);
		return true;
	}
}
	

