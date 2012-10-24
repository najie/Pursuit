package com.baltazare.pursuit.menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baltazare.core.listener.OnClickListenerStringParam;
import com.baltazare.core.manager.CacheManager;
import com.baltazare.core.manager.PlayerManager;
import com.baltazare.pursuit.R;
import com.baltazare.pursuit.R.layout;
import com.baltazare.pursuit.R.menu;
import com.baltazare.pursuit.play.PlayActivity;

import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CreatePlayerActivity extends Activity {

	private static final String LOG_TAG = "CreatePlayer";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	setContentView(R.layout.activity_create_player);
    	
    	/* START BUTTON */
    	Button letsBegin = (Button)findViewById(R.id.cp_lets_begin);
        letsBegin.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//get input value
				EditText nameInput = (EditText)findViewById(R.id.cp_name_input);
				String name = nameInput.getText().toString();
				//create Player
				if(name == null || name.equals("") == false) {
					CreatePlayerActivity cp = (CreatePlayerActivity)v.getContext();
					
					cp.addPlayer(name);
					
					/* start play activity */
					Intent playActivity = new Intent(v.getContext(), PlayActivity.class);
					startActivity(playActivity);
					
					finish();
				}
			}
		});
        
        Button cancel = (Button)findViewById(R.id.cp_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
        	
			public void onClick(View v) {
				finish();
			}
		});
        
        Button addFriend = (Button)findViewById(R.id.cp_add_player);
        addFriend.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent addFriendActivity = new Intent(v.getContext(), AddFriendActivity.class);
				startActivity(addFriendActivity);
			}
		});
    }

	public void onResume() {
    	super.onResume();
    	
    	//display friends
    	CacheManager cm = new CacheManager(this);
    	String playersStr = cm.getCache("players");
    	PlayerManager pm = new PlayerManager(this);
    	
    	if(pm.getNumberOfPlayers() != 0) {
    		
    		LinearLayout listFriends = (LinearLayout)findViewById(R.id.cp_list_friends);
    		listFriends.removeAllViews();
    		
    		try {
				JSONArray players = new JSONArray(playersStr);
				int length = players.length();
				
				for (int i = 0; i < length; i++) {
					
					TextView txt = new TextView(this);
					String name = players.getJSONObject(i).getString("name");
					
					txt.setText(name);
					txt.setGravity(Gravity.CENTER);
					txt.setWidth(10);
					txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.delete_28x28, 0);
					
					/* delete player on click */
					txt.setOnClickListener(new OnClickListenerStringParam(name){
						
						public void onClick(View v) {
							
							v.setVisibility(View.INVISIBLE);
							
							PlayerManager pm = new PlayerManager(v.getContext());
							pm.deletePlayer(this.string);
						}
					});
					
					listFriends.addView(txt);
				}
				
			} catch (JSONException e) {
				Log.e(LOG_TAG, e.getMessage());
			}
    	}
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_player, menu);
        return true;
    }
    
    private void addPlayer(String name) {
    	PlayerManager pm = new PlayerManager(this);
    	pm.createPlayer(name);
    }
}
