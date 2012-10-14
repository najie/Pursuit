package com.baltazare.pursuit;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baltazare.core.*;

public class PlayActivity extends Activity {
	
	private static final String LOG_TAG = "Play Activity";
	private JSONArray questions = null;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        
        //get cache
        String cacheString = (new CacheManager(this).getCache("questions"));
        
        try {
        	//setup questions
    		this.questions = new JSONArray(cacheString);
			
    		Question question = this.pickARandomQuestion();
    		
			this.displayQuestion(question);
			
			
		} catch (JSONException e) {
			Log.e(LOG_TAG, e.getMessage());
		}
    }
    
    private void resetView() {
    	this.cleanView();
    	this.displayQuestion(this.pickARandomQuestion());
    }
    
    private void cleanView() {
    	ViewGroup parent = (ViewGroup)findViewById(R.id.answersArea);
    	parent.removeAllViews();
    }
    
    private Question pickARandomQuestion() {
    	
    	JSONArray questions = this.questions;
    	
		int randomeOffset = (int)(Math.random() * ((questions.length())-0)) + 0;
		Question question = null;
		
		try {
			question = new Question((JSONObject)questions.get(randomeOffset), this);
		} catch (JSONException e) {
			Log.e(LOG_TAG, e.getMessage());
		}
    	
    	return question;
    }
    
    private void displayQuestion(Question question) {
    	//setup onCickListeners
		OnClickListener onClickGoodAnswer = new OnClickListener() {
			
			public void onClick(View v) {
				PlayActivity ctx = (PlayActivity)v.getContext();
				Toast.makeText(ctx, "Bonne réponse !", Toast.LENGTH_SHORT).show();
				ctx.resetView();				
			}
		};
		OnClickListener onClickWrongAnswer = new OnClickListener() {
			
			public void onClick(View v) {
				PlayActivity ctx = (PlayActivity)v.getContext();
				Toast.makeText(ctx, "Mauvaise réponse !", Toast.LENGTH_SHORT).show();
				ctx.resetView();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.play_activity, menu);
        return true;
    }
}
