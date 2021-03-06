package com.muslimevents;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends Activity implements OnItemClickListener {
	AsyncHttpClient client = new AsyncHttpClient();
	ListView orgListView;
	
	String hackyNames[], hackyIds[];
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        orgListView = (ListView) this.findViewById(R.id.orgList);
        Log.d("Blah", "Test");
        client.get("http://muslimevents.herokuapp.com/events/nearyby?city=Richardson", new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				hackyNames = new String[response.length()];
				hackyIds = new String[response.length()];
				Log.d("Blah", response.length()+"");
				for(int i=0; i < response.length(); i++){
					try {
						JSONObject info = response.getJSONObject(i);
						hackyNames[i] = info.getString("name");
						hackyIds[i] = info.getString("id");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
		        orgListView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, hackyNames));
		        orgListView.setOnItemClickListener(MainActivity.this);
			}

			@Override
			public void onFailure(int statusCode, Throwable e, JSONArray errorResponse) {
				// TODO Auto-generated method stub
				Log.d("Blah",e.toString());
			}
        	
        });

    }

    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    String Orgs[] = {"MAS", "ICNA", "ISNA", "MSA"};
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent myIntent = new Intent(MainActivity.this, Events.class);
		myIntent.putExtra("id", hackyIds[position]);
		myIntent.putExtra("organization", hackyNames[position]);
		startActivity(myIntent);
		
	}
	    
}
