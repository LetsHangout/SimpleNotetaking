package com.letshangout.simplenotetaking.note;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.letshangout.simplenotetaking.R;

public class NoteHelper extends Activity {

	final Context context = this;
	String fileNameText;
	String fileText;
	EditText ourFileET;
	BufferedWriter buf;
	StringBuilder string;
	File ourFile =new File(Environment.getExternalStorageDirectory() + File.separator + "SimpleNotes" + File.separator);
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_file);
		ourFileET = (EditText) findViewById(R.id.noteET);
		
		try {
			getStuff();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			// openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void setUpNote() {
		this.getActionBar().setTitle(fileNameText);

	}

	public void getStuff() throws IOException{
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			fileNameText = bundle.getString("FILENAME");
			setEditText(fileNameText);
		}
	}

	public void saveFile() {
		try {
			if(!fileNameText.contains(".txt")){
				buf = new BufferedWriter(new FileWriter(
						Environment.getExternalStorageDirectory() + File.separator
							+ "SimpleNotes" + File.separator + fileNameText
							+ ".txt"));
			}else{
				buf = new BufferedWriter(new FileWriter(
						Environment.getExternalStorageDirectory() + File.separator
						+ "SimpleNotes" + File.separator + fileNameText));
			}
			buf.write(ourFileET.getText().toString());
			Toast.makeText(context, "Note saved", Toast.LENGTH_SHORT).show();
			buf.close();
			buf.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public StringBuilder getNoteText(File f) throws IOException {
		string = new StringBuilder();

		BufferedReader br = new BufferedReader(new FileReader(f));
		String line;

		while ((line = br.readLine()) != null) {
			string.append(line);
			string.append('\n');
		}
		br.close();
		return string;
	}
	
	
	public void setEditText(String s) throws IOException{
		File file = new File(Environment.getExternalStorageDirectory() + File.separator + "SimpleNotes" + File.separator + s);
		if (getNoteText(file) != null){
			ourFileET.setText(getNoteText(file));
		}
	}

}
