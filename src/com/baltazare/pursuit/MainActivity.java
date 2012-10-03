package com.baltazare.pursuit;

import org.json.JSONArray;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.baltazare.core.*;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JSONArray questions = (new QueryManager()).query("getAllQuestions");
        
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
