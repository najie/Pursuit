package com.baltazare.pursuit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baltazare.core.*;

public class PlayActivity extends Activity {
	
	private static final String LOG_TAG = "Play Activity";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        
        //get cache
        String cacheString = (new CacheManager(this).getCache());
        
        try {
        	//setup questions
			JSONArray questions = new JSONArray(cacheString);
			
			//pick a random question and initialize it
			int randomeOffset = (int)(Math.random() * ((questions.length())-0)) + 0;
			Question question =  new Question((JSONObject)questions.get(randomeOffset), this);
			
			//setup onCickListeners
			OnClickListener onClickGoodAnswer = new OnClickListener() {
				
				public void onClick(View v) {
					Toast.makeText(v.getContext(), "Bonne réponse !", Toast.LENGTH_SHORT).show();
					
				}
			};
			
			OnClickListener onClickWrongAnswer = new OnClickListener() {
				
				public void onClick(View v) {
					Toast.makeText(v.getContext(), "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
					
				}
			};
			
			//get buttons view
			View[] buttonViews = question.getCreatedButtonsLayout(onClickGoodAnswer, onClickWrongAnswer);
			
			//get answers area view and the button views in it
			LinearLayout answersArea = (LinearLayout) findViewById(R.id.answersArea);
			for (View view : buttonViews) {
				if(view != null)
					answersArea.addView(view);
			}
			//set the question text
			((TextView)findViewById(R.id.questionText)).setText(question.getQuestiontext());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(LOG_TAG, e.getMessage());
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.play_activity, menu);
        return true;
    }
}
