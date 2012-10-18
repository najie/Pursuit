package com.baltazare.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import android.annotation.TargetApi;
import android.content.*;
import android.util.Log;
import android.widget.Toast;

public class CacheManager {
	
	private static final String TAG = "Cache Manager";
	
	Context ctx;
	
	String[] fileNames = {"questions", "questionVersion"};
	
	public CacheManager(Context ctx) {
		this.ctx = ctx;
	}
	
	public void save(String datas, String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(new File(this.ctx.getCacheDir(), filename+".dat"));
			fos.write(datas.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getCache(String filename) {
		try {
			FileInputStream fis = new FileInputStream(new File(this.ctx.getCacheDir()+"/"+filename+".dat"));
			StringWriter writer = new StringWriter();
			IOUtils.copy(fis, writer);
			String datas = writer.toString();
			fis.close();
			return datas;
		} catch (FileNotFoundException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		
		return null;
	}
	
	public Boolean isCacheFileExists(String filename) {
		File cacheDir = new File(this.ctx.getCacheDir()+"/"+filename+".dat");
		
		if(cacheDir.exists() && this.getCache(filename) != "") {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Boolean checkCacheFiles() {
		for (String filename : this.fileNames) {
			if(this.isCacheFileExists(filename) == false) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
