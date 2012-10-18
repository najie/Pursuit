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
        
        CacheManager cm = new CacheManager(this);
        if(cm.isCacheFileExists("players")) {
        	
        }
        else {
        	setContentView(R.layout.activity_create_player);
        	Button letsBegin = (Button)findViewById(R.id.cp_lets_begin);
            letsBegin.setOnClickListener(new View.OnClickListener() {
    			
    			public void onClick(View v) {
    				//get input value
    				EditText nameInput = (EditText)findViewById(R.id.cp_name_input);
    				String name = nameInput.getText().toString();
    				//create Player
    				if(name == null || name.equals("") == false) {
    					(new CreatePlayer(v.getContext())).execute(name);
    				}
    			}
    		});
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_create_player, menu);
        return true;
    }
    
    class CreatePlayer extends AsyncTask<String, Integer, Boolean> {
    	
    	Context ctx = null;
    	
    	public CreatePlayer(Context ctx) {
    		this.ctx = ctx;
    	}
    	
		@Override
		protected Boolean doInBackground(String... params) {
			CacheManager cm = new CacheManager(this.ctx);
			JSONObject datas = new JSONObject();
			try {
				datas.put("playerName", params);
				datas.put("score", 0);
				
				cm.save(datas.toString(), "players");
				
			} catch (JSONException e) {
				Log.e(LOG_TAG, e.getMessage());
			}
			return true;
		}
		
		protected void onPostExecute(Boolean result) {
			Toast.makeText(this.ctx, "Joueur créé", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this.ctx, PlayActivity.class);
			startActivity(intent);
		}
    	
    }
}
