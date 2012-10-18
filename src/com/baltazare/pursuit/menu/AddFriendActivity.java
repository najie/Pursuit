package com.baltazare.pursuit.menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baltazare.core.CacheManager;
import com.baltazare.pursuit.R;

import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFriendActivity extends Activity {
	
	private static final String LOG_TAG = "AddFriend";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        
        Button addFriendButton = (Button)findViewById(R.id.af_add);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				EditText it = (EditText)findViewById(R.id.af_name_input);
				String name = it.getText().toString();
				if(name == null || name.equals("") == false) {
					AddFriendActivity activity = (AddFriendActivity)v.getContext();
					activity.addFriend(name);
					finish();
				}
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_friend, menu);
        return true;
    }
    
    private void addFriend(String name) {
    	CacheManager cm =  new CacheManager(this);
    	String playersStr = cm.getCache("players");
    	try {
			JSONArray players =  new JSONArray(playersStr);
			int length = players.length();
			JSONObject player = new JSONObject();
			player.put("name", name);
			player.put("score", 0);
			players.put(length, player);
			
			cm.save(players.toString(), "players");
			Toast.makeText(this, "ami ajouté", Toast.LENGTH_SHORT).show();
		} catch (JSONException e) {
			Log.e(LOG_TAG, e.getMessage());
		}
    }
}
