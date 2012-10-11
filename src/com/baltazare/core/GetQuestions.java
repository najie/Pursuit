package com.baltazare.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class GetQuestions extends AsyncTask<Void, Integer, JSONArray>{

	private static final String LOG_TAG = "Query Manager";
	private static final String NOM_HOTE_BALTAZARE = "http://baltazarestudio.fr";
	private static final String PATH_RPC = "/private/android/pursuit/rpc.php?";
	private static final String PARAMS_JSON = "query=android&methodName=getAllQuestions&output=json";
	
	@Override
	protected JSONArray doInBackground(Void... params) {
		HttpClient httpClient = new DefaultHttpClient();
		String URI = NOM_HOTE_BALTAZARE+PATH_RPC+PARAMS_JSON;
		HttpGet httpGet = new HttpGet(URI);
		JSONArray result = null;
		
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			
			HttpEntity httpEntity = httpResponse.getEntity();
    		
    		if( httpEntity != null ) {
    			InputStream inputStream = httpEntity.getContent();
    			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    			StringBuilder stringBuilder = new StringBuilder();
    			
    			String ligneLue = bufferedReader.readLine();
    			while(ligneLue != null){
    				stringBuilder.append(ligneLue+"\n");
    				ligneLue = bufferedReader.readLine();
    			}
    			bufferedReader.close();
    			
    			String resJSON = stringBuilder.toString();
    			result = new JSONObject(resJSON).getJSONArray("res");
    		}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.e(LOG_TAG, e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(LOG_TAG, e.getMessage());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(LOG_TAG, e.getMessage());
		}
		
		return result;
	}
}
