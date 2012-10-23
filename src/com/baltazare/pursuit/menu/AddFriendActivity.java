package com.baltazare.pursuit.menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baltazare.core.CacheManager;
import com.baltazare.core.PlayerManager;
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
        
        //Binds
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
        
        Button cancel = (Button)findViewById(R.id.af_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				finish();
				
			}
		});
    }
    
    public void onPause() {
    	super.onPause();
    	finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_friend, menu);
        return true;
    }
    
    private void addFriend(String name) {
    	PlayerManager pm = new PlayerManager(this);
    	pm.addFriend(name);
    }
}
