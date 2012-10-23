package com.baltazare.core;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class PlayerManager {
	
	Context ctx = null;
	private static final String LOG_TAG = "Player Manager";
	
	
	public Boolean createPlayer(String name) {
		
		if(this.isPlayerExists(name) == false) {
			try {
	    		CacheManager cm =  new CacheManager(this.ctx);
	        	String playersStr = cm.getCache("players");
	        	
	        	JSONArray players = null;
	        	JSONObject player = new JSONObject();
	        	
				player.put("name", name);
				player.put("score", 0);
	        	
	        	if(playersStr.equals("null")) {
	        		players = new JSONArray();
	    			players.put(0, player);
	        	}
	        	else {
	        		players =  new JSONArray(playersStr);
	        		players.put(players.length(), player);
	        	}
	        	
	        	Log.i(LOG_TAG, players.toString());
	        	
				cm.save(players.toString(), "players");
			} catch (JSONException e) {
				Log.e(LOG_TAG, e.getMessage());
			}
			
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public void addFriend(String name) {
		this.createPlayer(name);
	}
	
	public void deletePlayer(String name) {
		CacheManager cm = new CacheManager(this.ctx);
		String playersStr = cm.getCache("players");
		
		try {
			JSONArray players = new JSONArray(playersStr);
			int l = players.length();
			
			for (int i = 0; i < l; i++) {
				String currName = players.getJSONObject(i).getString("name");
				//TODO suppress the player
			}
			
		} catch (JSONException e) {
			Log.e(LOG_TAG, e.getMessage());
		}
		
	}
	
	public int getNumberOfPlayers() {
		
		return 0;
	}
	
	private Boolean isPlayerExists(String name) {
		
		return false;
	}
	
	public PlayerManager(Context ctx) {
		this.ctx = ctx;
	}
	
}
