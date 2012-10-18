package com.baltazare.core;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * Custom onClickListener to pass goodAnswers Params
 */
public class OnClickListenerStringParam implements OnClickListener{
	
	protected String string;
	
	public OnClickListenerStringParam(String string) {
		this.string = string;
	}
	
	public void onClick(View v) {
		
	}

}
