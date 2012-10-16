package com.baltazare.pursuit.play;

import com.baltazare.pursuit.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WrongAnswerActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_answer);
        
        String[] goodAnswers = getIntent().getExtras().getStringArray("goodAnswers");
        String goodAnsStr = "";
        for (String string : goodAnswers) {
			goodAnsStr += string+"\n";
		}
        
        TextView goodAnsArea = (TextView)findViewById(R.id.listGoodAnswers);
        goodAnsArea.setText(goodAnsStr);
        
        RelativeLayout tv = (RelativeLayout)findViewById(R.id.wrongAnsRelativeLayout);
        tv.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				finish();
			}
		});
    }

}
