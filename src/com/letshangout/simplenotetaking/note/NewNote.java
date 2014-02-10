package com.letshangout.simplenotetaking.note;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.letshangout.simplenotetaking.R;

public class NewNote extends Activity{

	final Context context = this;
	String fileNameText;
	String fileText;
	EditText ourFileET;
	BufferedWriter buf;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_file);
		ourFileET = (EditText) findViewById(R.id.noteET);
		getStuff();
		setUpNote();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.note, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.save:
	        	saveFile();
	            return true;
	        case R.id.action_settings:
	            //openSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void setUpNote(){
		this.getActionBar().setTitle(fileNameText);
		
	}
	
	public void getStuff(){
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			fileNameText = bundle.getString("FILENAME");
		}
	}
	
	public void saveFile(){
		File sn = new File("SimpleNotes");
		try{
			buf = new BufferedWriter(new FileWriter(Environment.getExternalStorageDirectory() + File.separator + sn + File.separator + fileNameText + ".txt"));
			buf.write(ourFileET.getText().toString());
			buf.close();
			buf.flush();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
