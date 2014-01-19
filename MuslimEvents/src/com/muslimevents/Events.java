package com.muslimevents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Events extends Activity implements OnItemClickListener{
	AsyncHttpClient client = new AsyncHttpClient();
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.events, menu);
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent myIntent = new Intent(Events.this, EventActivity.class);
		myIntent.putExtra("info", hackyObjects[position].toString());
		startActivity(myIntent);
		
	}
	ListView orgListView;
	String hackyEventTitles[];
	JSONObject hackyObjects[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String id = this.getIntent().getExtras().getString("id");
        int masjid_id = Integer.parseInt(id);
        if(masjid_id < 0) return;
        orgListView = (ListView) this.findViewById(R.id.orgList);
        client.get("http://muslimevents.herokuapp.com/events/organization_events?id="+id, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				hackyEventTitles = new String[response.length()];
				hackyObjects = new JSONObject[response.length()];
				for(int i=0; i < response.length(); i++){
					try {
						JSONObject info = response.getJSONObject(i);
						hackyEventTitles[i] = info.getString("title");
						hackyObjects[i] = info;
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
		        orgListView.setAdapter(new ArrayAdapter<String>(Events.this, android.R.layout.simple_list_item_1, hackyEventTitles));
		        orgListView.setOnItemClickListener(Events.this);
			}
        });
        
        
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, AddEventActivity.class);
		i.putExtra("organization", this.getIntent().getExtras().getString("organization"));
		startActivity(i);
		return true;
	}
    
    

}
