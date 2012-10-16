package com.baltazare.pursuit.play;

import com.baltazare.pursuit.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.RelativeLayout;

public class GoodAnswerActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_answer);
        RelativeLayout tv = (RelativeLayout)findViewById(R.id.goodAnsRelativeLayout);
        tv.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				finish();
			}
		});
    }

}
