package com.baltazare.core;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * Custom onClickListener to pass goodAnswers Params
 */
public class OnWrongAnsClickListener implements OnClickListener{
	
	protected String[] goodAnswers;
	
	public OnWrongAnsClickListener(String[] goodAnswers) {
		this.goodAnswers = goodAnswers;
	}
	
	public void onClick(View v) {
		
	}

}
