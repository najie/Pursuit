package com.baltazare.core;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;

public class Util {

	public List<View> findViewsWithTag(ViewGroup root, String tag) {
		List<View> views = new ArrayList<View>();
		
		final int nbOfChild = root.getChildCount();
		for(int i=0; i<nbOfChild; i++){
	        final View childView = root.getChildAt(i);
	        
            final Object tagView = childView.getTag();
            
            if(tagView != null && tagView.equals(tag)) {
            	views.add(childView);
            }
	    }
		return views;
	}
}
