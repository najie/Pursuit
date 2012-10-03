package com.baltazare.core;

import java.util.Arrays;
import java.util.Collections;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Question {
	
	String question = "";
	int nbUsed, nbAnswers = 0;
	String gaString = "";
	String waString = "";
	String[][] answers = new String[8][2];
	private static final String LOG_TAG = "QuestionObject";
	String gaArray[];
	String waArray[];
	int gaIndexes[];
	View[] answersLayout = new View[8];
	Context ctx = null;
	OnClickListener onClickGoodAnswer;
	OnClickListener onClickWrongAnswer;
	
	Question(JSONObject ques, Context context){
		
		try {
			ctx = context;
			nbAnswers = ques.getInt("nbAnswers");
			question = ques.getString("text").replaceAll("\\\\", "");
			gaString = ques.getString("listGoodAnswers");
			waString = ques.getString("listWrongAnswers");
			gaArray = gaString.split("/");
			waArray = waString.split("/");
			int waL = waArray.length;
			for (int i = 0; i < waL; i++) {
				answers[i][0] = waArray[i].replaceAll("\\\\", "");
				answers[i][1] = "wrong";
			}
			for (int i = 0, l=gaArray.length; i < l; i++) {
				answers[waL+i][0] = gaArray[i].replaceAll("\\\\", "");
				answers[waL+i][1] = "good";
			}
			Collections.shuffle(Arrays.asList(answers));
			createArrayLayout();
			
		} catch (JSONException e) {
			Log.e(LOG_TAG, e.getMessage());
		} catch (NullPointerException e) {
			Log.e(LOG_TAG, e.getMessage());
		}
		
	}
	
	public void createArrayLayout(){
		try {
			for (int i = 0; i < answers.length; i++) {
				if(answers[i][0] != null){
					Button currView = new Button(ctx);
					currView.setText(answers[i][0]);
					currView.setWidth(200);
					currView.setGravity(Gravity.CENTER);
					currView.setTag(answers[i][1]);
					if(answers[i][1].equals("good") == true)
						currView.setOnClickListener(onClickGoodAnswer);
					else
						currView.setOnClickListener(onClickWrongAnswer);
					answersLayout[i] = currView;
				}
			}
		} catch (NullPointerException e) {
			Log.e(LOG_TAG, e.getMessage());
		}
	}
	
}
