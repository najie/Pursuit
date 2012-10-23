package com.baltazare.pursuit.menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baltazare.core.CacheManager;
import com.baltazare.core.OnClickListenerStringParam;
import com.baltazare.core.PlayerManager;
import com.baltazare.pursuit.R;
import com.baltazare.pursuit.R.layout;
import com.baltazare.pursuit.R.menu;
import com.baltazare.pursuit.play.PlayActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ChoosePlayerActivity extends Activity {

	private static final String LOG_TAG = "ChoosPlayer";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_player);
        
        CacheManager cm = new CacheManager(this);
        if(cm.isCacheFileExists("players") == false)
        	finish();
    }
	
	public void onResume() {
		super.onResume();
		
		LinearLayout ll = (LinearLayout)findViewById(R.id.chp_players_name_area);
        
		//remove childs
		ll.removeAllViews();
		
        CacheManager cm = new CacheManager(this);
		
		//display players
        String playersJson = cm.getCache("players");
        try {
			JSONArray players = new JSONArray(playersJson);
			int length = players.length();
			for (int i = 0; i < length; i++) {
				LinearLayout currLl = new LinearLayout(this);
				Button button = new Button(this);
				Button deleteButton = new Button(this);
				String name = players.getJSONObject(i).getString("name");
				
				currLl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				
				deleteButton.setText("X");
				deleteButton.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0));
				
				button.setText(name);
				button.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
				button.setGravity(Gravity.CENTER);
				
				button.setOnClickListener(new OnClickListenerStringParam(name) {
					
					public void onClick(View v) {
						Intent intent = new Intent(v.getContext(), PlayActivity.class);
						startActivity(intent);
					}
				});
				
				deleteButton.setOnClickListener(new OnClickListenerStringParam(name) {
					
					public void onClick(View v) {
						PlayerManager pm = new PlayerManager(v.getContext());
						pm.deletePlayer(this.string);
						
						LinearLayout parent = (LinearLayout)v.getParent();
						parent.setVisibility(View.INVISIBLE);
						//TODO ANIMATE
						
						if(pm.getNumberOfPlayers() == 0) {
							finish();
						}
					}
				});
				
				currLl.addView(button);
				currLl.addView(deleteButton);

				ll.addView(currLl);
			}
			
		} catch (JSONException e) {
			Log.e(LOG_TAG, e.getMessage());
		}
        
      //bind add friend
        Button addFriendButton = (Button)findViewById(R.id.chp_add);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), AddFriendActivity.class);
				startActivity(intent);
			}
		});
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_choose_player, menu);
        return true;
    }
}
