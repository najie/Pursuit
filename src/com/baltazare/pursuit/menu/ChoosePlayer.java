package com.baltazare.pursuit.menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baltazare.core.CacheManager;
import com.baltazare.pursuit.R;
import com.baltazare.pursuit.R.layout;
import com.baltazare.pursuit.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.RelativeLayout;

public class ChoosePlayer extends Activity {

	private static final String LOG_TAG = "ChoosPlayer";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player);
        
        CacheManager cm = new CacheManager(this);
        String playersJson = cm.getCache("players");
        try {
			JSONArray players = new JSONArray(playersJson);
			
			
		} catch (JSONException e) {
			Log.e(LOG_TAG, e.getMessage());
		}
        
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.chp_players_name_area);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_choose_player, menu);
        return true;
    }
}
