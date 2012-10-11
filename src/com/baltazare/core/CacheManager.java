package com.baltazare.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import android.content.*;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.widget.Toast;

public class CacheManager {
	
	private static final String TAG = "Cache Manager";
	
	Context ctx;
	
	public CacheManager(Context ctx) {
		this.ctx = ctx;
	}
	
	public void save(String datas) {
		try {
			FileOutputStream fos = new FileOutputStream(new File(this.ctx.getCacheDir(), "questions.dat"));
			fos.write(datas.getBytes());
			fos.close();
			Toast.makeText(this.ctx, "Cache created", Toast.LENGTH_SHORT).show();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getCache() {
		try {
			FileInputStream fis = new FileInputStream(new File(this.ctx.getCacheDir()+"/questions.dat"));
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
	
	public Boolean isCacheFileExists() {
		File cacheDir = new File(this.ctx.getCacheDir()+"/questions.dat");
		Boolean isExists = cacheDir.exists();
		
		return isExists;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
