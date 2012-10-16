package com.baltazare.pursuit.menu;

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


import com.baltazare.core.CacheManager;
import com.baltazare.pursuit.MainActivity;
import com.baltazare.pursuit.R;
import com.baltazare.pursuit.R.id;
import com.baltazare.pursuit.R.layout;
import com.baltazare.pursuit.R.menu;
import com.baltazare.pursuit.R.string;
import com.baltazare.pursuit.play.PlayActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends Activity {
	
	@Override
	public void onResume() {
		super.onResume();
		//check cache
        if(!(new CacheManager(this)).checkCacheFiles()) {
        	Intent mainActivity = new Intent(this, MainActivity.class);
        	startActivity(mainActivity);
        }
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        
        //Binds
        ImageView refreshImg = (ImageView) findViewById(R.id.refreshUpdateQuestion);
        refreshImg.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				(new QuestionUpdate()).execute();
			}
		});
        final Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent playActivity = new Intent(v.getContext(), PlayActivity.class);
				v.getContext().startActivity(playActivity);
			}
		});
        
        //check question version
        (new QuestionUpdate()).execute();
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }
    
    class QuestionUpdate extends AsyncTask<Void, Integer, Boolean> {

    	private static final String LOG_TAG = "Question Updater";
    	private static final String NOM_HOTE_BALTAZARE = "http://baltazarestudio.fr";
    	private static final String PATH_RPC = "/private/android/pursuit/rpc.php?";
    	private static final String PARAMS_JSON = "query=android&methodName=getQuestionVersion&output=json";
    	
    	@Override
    	protected Boolean doInBackground(Void... params) {
    		HttpClient httpClient = new DefaultHttpClient();
    		String URI = NOM_HOTE_BALTAZARE+PATH_RPC+PARAMS_JSON;
    		HttpGet httpGet = new HttpGet(URI);
    		Boolean result = false;
    		
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
        			
        			String version = new JSONObject(stringBuilder.toString()).getJSONArray("res").get(0).toString();
        			String currVersion = (new CacheManager(getApplicationContext())).getCache("questionVersion");
        			
        			Log.i(LOG_TAG, version+"//"+currVersion);
        			
        			if(!version.equals(currVersion)) {
        				return true;
        			}
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
    	
		protected void onPostExecute(Boolean result) {
			ProgressBar pb = (ProgressBar)findViewById(R.id.menuBottomBarProgress);
			TextView tv = (TextView)findViewById(R.id.menuBottomBarText);
			ImageView refreshImg = (ImageView) findViewById(R.id.refreshUpdateQuestion);
			
			if(result == false) {
				tv.setText(R.string.no_question_update);
			}
			else {
				tv.setText(R.string.question_update_available);
			}
			
			refreshImg.setVisibility(View.VISIBLE);
			pb.setVisibility(View.INVISIBLE);
		}
		
		protected void onPreExecute() {
			ImageView refreshImg = (ImageView) findViewById(R.id.refreshUpdateQuestion);
			ProgressBar pb = (ProgressBar)findViewById(R.id.menuBottomBarProgress);
			TextView tv = (TextView)findViewById(R.id.menuBottomBarText);
			tv.setText(R.string.new_question);
			refreshImg.setVisibility(View.INVISIBLE);
			pb.setVisibility(View.VISIBLE);
			tv.setVisibility(View.VISIBLE);
			
		}
		
    }
}