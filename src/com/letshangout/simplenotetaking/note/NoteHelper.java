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
	private String fileNameString;
	private String fileText;
	private EditText ourFileET;
	private File ourFilePath;

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

		try {
			setUpNote(fileNameString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setFileNameString(String s) {
		fileNameString = s;
		setFilePath(s);
	}

	public String getFileNameString() {
		return fileNameString;
	}

	public void setFileText(String s) {
		fileText = s;
	}

	public String getFileText() {
		return fileText;
	}

	public void setFilePath(String s) {
		ourFilePath = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "SimpleNotes" + File.separator + s + ".txt");
	}

	public File getFilePath() {
		return ourFilePath;
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

	public void setUpNote(String s) throws IOException {
		this.getActionBar().setTitle(fileNameString);
		setEditText();
	}

	public void getStuff() throws IOException {
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			setFileNameString(bundle.getString("FILENAME"));
		}
	}

	public void saveFile() {
		BufferedWriter buf;
		setFileText(ourFileET.getText().toString());
		try {
			if (!fileNameString.contains(".txt")) {
				buf = new BufferedWriter(new FileWriter(
						Environment.getExternalStorageDirectory()
								+ File.separator + "SimpleNotes"
								+ File.separator + fileNameString + ".txt"));
			} else {
				buf = new BufferedWriter(new FileWriter(
						Environment.getExternalStorageDirectory()
								+ File.separator + "SimpleNotes"
								+ File.separator + fileNameString));
			}
			buf.write(getFileText());
			buf.close();
			buf.flush();
			Toast.makeText(context, "Note saved", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public StringBuilder getNoteText(File f) throws IOException {
		StringBuilder string;
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

	public void setEditText() throws IOException {
		if (getNoteText(getFilePath()) != null) {
			ourFileET.setText(getNoteText(getFilePath()));
		}
	}

	public void deleteOurFile(String s) {
		File f = new File(s);
		if (f.exists()) {
			f.delete();
		}
	}

}
