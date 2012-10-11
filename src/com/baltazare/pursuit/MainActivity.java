package com.baltazare.pursuit;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import com.baltazare.core.*;

public class MainActivity extends Activity {

	private static final String LOG_TAG = "Main Activity";  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
			JSONArray questions = (new GetQuestions()).execute().get();
			CacheManager cm = new CacheManager(this);
			if(cm.isCacheFileExists() == false) {
				cm.save(questions.toString());
			}
			//Starting the menu activity
	        Intent intent = new Intent(this, MenuActivity.class);
	        startActivity(intent);
	        
		} catch (InterruptedException e) {
			Log.e(LOG_TAG, e.getMessage());
		} catch (ExecutionException e) {
			Log.e(LOG_TAG, e.getMessage());
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
