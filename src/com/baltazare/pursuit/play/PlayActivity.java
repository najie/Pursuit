package com.baltazare.pursuit.play;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
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
import com.baltazare.pursuit.R;
import com.baltazare.pursuit.R.id;
import com.baltazare.pursuit.R.layout;
import com.baltazare.pursuit.R.menu;
import com.baltazare.pursuit.R.string;
import com.baltazare.pursuit.menu.MenuActivity;

public class PlayActivity extends Activity {
	
	private static final String LOG_TAG = "Play Activity";
	static final int ANSWER_REQUEST = 0;
	private JSONArray questions = null;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        
        //TODO get players pick a random one then display this name
        
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
    
    public void onResume() {
    	super.onResume();
    	
    	this.resetView();
    }
    
    private void displayQuestion(Question question) {
    	//setup onCickListeners
		OnClickListener onClickGoodAnswer = new OnClickListener() {
			
			public void onClick(View v) {
				PlayActivity ctx = (PlayActivity)v.getContext();
				Intent goodAnsActivity = new Intent(ctx, GoodAnswerActivity.class);
				startActivityForResult(goodAnsActivity, ANSWER_REQUEST);
			}
		};
		OnClickListener onClickWrongAnswer = new OnWrongAnsClickListener(question.getGoodAnswers()) {
			
			public void onClick(View v) {
				PlayActivity ctx = (PlayActivity)v.getContext();
				
				Bundle bundle = new Bundle();
				bundle.putStringArray("goodAnswers", this.goodAnswers);
				
				Intent wrongAnsActivity = new Intent(ctx, WrongAnswerActivity.class);
				wrongAnsActivity.putExtras(bundle);
				
				startActivityForResult(wrongAnsActivity, ANSWER_REQUEST);
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
}
