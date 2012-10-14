package com.baltazare.core;

import java.util.Arrays;
import java.util.Collections;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Question {
	
	private static final String LOG_TAG = "QuestionObject";
	
	protected String question = "";
	protected int nbUsed, nbAnswers = 0;
	protected String gaString = "";
	protected String waString = "";
	protected String[][] answers = new String[8][2];
	protected String gaArray[];
	protected String waArray[];
	protected int gaIndexes[];
	protected Context ctx;
	
	public Question(JSONObject ques, Context context){
		
		try {
			this.ctx = context;
			this.nbAnswers = ques.getInt("nbAnswers");
			this.question = ques.getString("text").replaceAll("\\\\", "");
			this.gaString = ques.getString("listGoodAnswers");
			this.waString = ques.getString("listWrongAnswers");
			this.gaArray = gaString.split("/");
			this.waArray = waString.split("/");
			int waL = waArray.length;
			for (int i = 0; i < waL; i++) {
				this.answers[i][0] = waArray[i].replaceAll("\\\\", "");
				this.answers[i][1] = "wrong";
			}
			for (int i = 0, l=gaArray.length; i < l; i++) {
				this.answers[waL+i][0] = gaArray[i].replaceAll("\\\\", "");
				this.answers[waL+i][1] = "good";
			}
			Collections.shuffle(Arrays.asList(answers));
		} catch (JSONException e) {
			Log.e(LOG_TAG, e.getMessage());
		} catch (NullPointerException e) {
			Log.e(LOG_TAG, e.getMessage());
		}
		
	}
	
	public View[] getCreatedButtonsLayout(OnClickListener onClickGoodAnswer, OnClickListener onClickWrongAnswer){
		try {
			View[] answersLayout = new View[8];
			
			for (int i = 0; i < this.answers.length; i++) {
				if(answers[i][0] != null){
					Button currView = new Button(ctx);
					currView.setText(answers[i][0]);
					currView.setTag(answers[i][1]);
					currView.setTag("answer");
					currView.setWidth(250);
					currView.setGravity(Gravity.CENTER);
					currView.setHeight(LayoutParams.WRAP_CONTENT);
					
					if(answers[i][1].equals("good") == true)
						currView.setOnClickListener(onClickGoodAnswer);
					else
						currView.setOnClickListener(onClickWrongAnswer);
					answersLayout[i] = currView;
				}
				else{
					answersLayout[i] = null;
				}
			}
			return answersLayout;
		} catch (NullPointerException e) {
			Log.e(LOG_TAG, e.getMessage());
		}
		
		return null;
	}
	
	public String getQuestiontext() {
		return this.question;
	}
}
