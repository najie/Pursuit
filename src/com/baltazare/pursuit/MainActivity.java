package com.baltazare.pursuit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.net.ConnectivityManagerCompat;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.baltazare.core.*;
import com.baltazare.core.manager.CacheManager;
import com.baltazare.pursuit.menu.MenuActivity;

public class MainActivity extends Activity {

	private static final String LOG_TAG = "Main Activity"; 
	
	protected CacheManager cm;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        this.cm = new CacheManager(this);
        
        if(this.cm.checkCacheFiles() == false) {
        	//check internet status
        	ConnectivityManager connManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = connManager.getActiveNetworkInfo();
        	if(netInfo != null && netInfo.isConnected()) {
        		(new InitCache()).execute();
            }
        	else {
        		displayNoConnectionMsg();
        	}
        }
        else {
        	this.startMenuActivity();
        }
    }
    
    private void displayNoConnectionMsg() {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage(R.string.ma_no_internet);
    	builder.setTitle(R.string.ma_no_internet_title);
    	
    	builder.setPositiveButton(R.string.ma_i_fill_fix_it, new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
			}
		});
    	builder.setNegativeButton("Plus tard !", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
    	//block the back button
    	builder.setCancelable(false);
    	
    	builder.create().show();
    }
    
    public void saveQuestionsInCache(String questions) {
    	this.cm.save(questions, "questions");
    }
    
    public void saveQuestionVersionInCache(String questionVersion) {
    	this.cm.save(questionVersion, "questionVersion");
    }
    
    public void createPlayersCacheFile() {
    	this.cm.save((new JSONArray()).toString(), "players");
    }
    
    public void startMenuActivity() {
    	//Starting the menu activity
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
    
    
    /**
     * AsyncTask Class to get the questions and init the cache.
     */
    class InitCache extends AsyncTask<Void, Integer, Map<String, String>>{

    	private static final String LOG_TAG = "Query Manager";
    	private static final String NOM_HOTE_BALTAZARE = "http://baltazarestudio.fr";
    	private static final String PATH_RPC = "/private/android/pursuit/rpc.php?";
    	private static final String PARAMS_JSON = "query=android&methodName=getAllQuestions&output=json";
    	
    	@Override
    	protected Map<String, String> doInBackground(Void... params) {
    		HttpClient httpClient = new DefaultHttpClient();
    		String URI = NOM_HOTE_BALTAZARE+PATH_RPC+PARAMS_JSON;
    		HttpGet httpGet = new HttpGet(URI);
    		JSONArray questions = null;
    		String questionVersion = null;
    		Map<String, String> result = new HashMap<String, String>();
    		
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
        			JSONObject res = new JSONObject(resJSON).getJSONObject("res");
        			questions = res.getJSONArray("questions");
        			questionVersion = res.getString("questionVersion");
        			result.put("questions", questions.toString());
        			result.put("questionVersion", questionVersion);
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
    	
    	protected void onPostExecute(Map<String, String> result) {
    		MainActivity.this.saveQuestionsInCache(result.get("questions"));
    		MainActivity.this.saveQuestionVersionInCache(result.get("questionVersion"));
    		MainActivity.this.createPlayersCacheFile();
			MainActivity.this.startMenuActivity();
    	}
    }
}


