package com.baltazare.pursuit.menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baltazare.core.CacheManager;
import com.baltazare.pursuit.R;
import com.baltazare.pursuit.R.layout;
import com.baltazare.pursuit.R.menu;
import com.baltazare.pursuit.play.PlayActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreatePlayerActivity extends Activity {

	private static final String LOG_TAG = "CreatePlayer";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    	setContentView(R.layout.activity_create_player);
    	Button letsBegin = (Button)findViewById(R.id.cp_lets_begin);
        letsBegin.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				//get input value
				EditText nameInput = (EditText)findViewById(R.id.cp_name_input);
				String name = nameInput.getText().toString();
				//create Player
				if(name == null || name.equals("") == false) {
					CreatePlayerActivity cp = (CreatePlayerActivity)v.getContext();
					cp.createPlayer(name);
				}
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_player, menu);
        return true;
    }
    
    private void createPlayer(String name) {
    	CacheManager cm = new CacheManager(this);
		JSONObject playerDatas = new JSONObject();
		JSONArray datas =  new JSONArray();
		try {
			playerDatas.put("playerName", name);
			playerDatas.put("score", 0);
			
			datas.put(0, playerDatas);
			
			cm.save(datas.toString(), "players");
			Toast.makeText(this, "joueur créé", Toast.LENGTH_SHORT).show();
		} catch (JSONException e) {
			Log.e(LOG_TAG, e.getMessage());
		}
    }
    
    private Boolean addPlayer(String name) {
    	CacheManager cm = new CacheManager(this);
    	
    	if(cm.isCacheFileExists("players") == false) {
    		return false;
    	}
    	
    	JSONObject player = new JSONObject();
    	
    	try {
    		player.put("name", name);
        	player.put("score", 0);
			JSONArray players = new JSONArray(cm.getCache("players"));
			players.put(players.length(), player);
			
			cm.save(players.toString(), "players");
			Toast.makeText(this, "joueur ajouté", Toast.LENGTH_SHORT).show();
		} catch (JSONException e) {
			Log.e(LOG_TAG, e.getMessage());
		}
    		
    	
    	return true;
    }
}
