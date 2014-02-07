package com.letshangout.simplenotetaking;

import com.letshangout.simplenotetaking.note.NoteActivity;
import com.letshangout.simplenotetaking.slyvr.SlyvrActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button button;
	Button noteButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void addListenerOnButton() {
		final Context context = this;
		button = (Button) findViewById(R.id.buttonBack);
		noteButton = (Button) findViewById(R.id.noteButton);
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, SlyvrActivity.class);
				startActivity(intent);
			}
		});
		
		noteButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				Intent intent = new Intent(context, NoteActivity.class);
				startActivity(intent);
			}
		});
	}

}
