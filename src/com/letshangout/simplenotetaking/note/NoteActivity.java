package com.letshangout.simplenotetaking.note;

import com.letshangout.simplenotetaking.R;
import com.letshangout.simplenotetaking.MainActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NoteActivity extends Activity{
	Button back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		addListenerOnButton();
	}
	
	public void addListenerOnButton(){
		final Context context = this;
		back = (Button) findViewById(R.id.buttonBack);
		
		back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				Intent intent = new Intent(context, MainActivity.class);
				startActivity(intent);
			}
		});
	}
	

}
