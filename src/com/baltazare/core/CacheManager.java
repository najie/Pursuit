package com.baltazare.core;

import java.io.File;

import org.json.JSONArray;
import android.content.*;

public class CacheManager {
	
	Context ctx;
	
	public CacheManager(Context ctx) {
		this.ctx = ctx;
	}
	
	public void JSONSave(JSONArray json) {
		File cacheDir = this.ctx.getExternalCacheDir();
	
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
