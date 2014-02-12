package com.letshangout.simplenotetaking.note;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.letshangout.simplenotetaking.R;
import com.letshangout.simplenotetaking.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NoteActivity extends Activity {
	final Context context = this;
	final String ourDirectoryString = Environment.getExternalStorageDirectory() + File.separator + "SimpleNotes" + File.separator;
	final File ourDirectory =new File(Environment.getExternalStorageDirectory() + File.separator + "SimpleNotes" + File.separator);
	Button main = null;
	FileHelper helper = new FileHelper();
	ListView listView;
	TextView headerTV;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		helper.createDirectory();
		addListenerOnButton();
		setupListview();

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.note_main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_new:
			final EditText alertEditText = new EditText(context);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			alertEditText.setLayoutParams(lp);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

			alertDialogBuilder
					.setMessage("Enter new file name")
					.setView(alertEditText)

					.setNegativeButton("Back",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									// close dialog window if this is clicked
									dialog.cancel();
								}
							})
					.setPositiveButton("Create",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									
								}
							});

			// create dialog box
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show dialog
			alertDialog.show();

			/*
			 * override positive button behavior must do this or else dialog
			 * will close when user tries to create a file that already exists
			 */
			alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							// Checks to make sure external storage exists
							// and is read/writeable
							if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
								// Handle no sdcard event

							} else {
								// make sure file name is valid
								if (alertEditText.getText().toString().matches("[a-zA-Z0-9]*")) {
									File file = new File(ourDirectory, alertEditText.getText().toString() + ".txt");
									// make sure file doesn't already exist
									if (!file.exists()) {
										try {
											file.createNewFile();
											// Go to new file??

											Intent intent = new Intent(context, NoteHelper.class);
											intent.putExtra("FILENAME",
													alertEditText.getText().toString());
											startActivity(intent);
											

											// Dismiss dialog?
										} catch (IOException e) {
											e.printStackTrace();
										}
									} else {
										alertEditText.setHint(alertEditText.getText().toString().toLowerCase());
										alertEditText.setText("");
										Toast.makeText(context, "File already exists!", Toast.LENGTH_SHORT).show();
									}

								} else {
									alertEditText.setText("");
									Toast.makeText(context, "Only A-z & 0-9 please", Toast.LENGTH_SHORT).show();
								}
							}
						}
					});

			alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
				/*
				 * Needs to be here to be able to focus on EditText in the
				 * dialog box.
				 * 
				 * (non-Javadoc)
				 * 
				 * @see android.content.DialogInterface.OnShowListener
				 * #onShow(android.content.DialogInterface)
				 */
				@Override
				public void onShow(DialogInterface arg0) {
					// TODO Auto-generated method stub
					alertEditText.requestFocus();
					alertEditText.setImeActionLabel("",EditorInfo.IME_ACTION_NEXT);
				}
			});
			return true;
		case R.id.action_settings:
			// openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void addListenerOnButton() {
		
		main = (Button) findViewById(R.id.buttonMain);
		main.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, MainActivity.class);
				startActivity(intent);
			}
		});
	}
	
	public void setupListview(){
		listView = (ListView) findViewById(R.id.listView1);
		headerTV = (TextView) findViewById(R.id.textView1);
		ArrayAdapter<String> arrayAdapter;
		
		
		ArrayList<String> f = helper.getFileList(helper.getFiles(ourDirectoryString));
		
		if(f != null){
			arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, helper.getFileList(helper.getFiles(ourDirectoryString)));
			listView.setAdapter(arrayAdapter);
			headerTV.setText(R.string.current_notes);
		}else{
			headerTV.setText(R.string.no_files);
		}

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				final String fileString = ((TextView) v).getText().toString();
				
				AlertDialog.Builder options = new AlertDialog.Builder(context);
				options
					.setMessage("What do you want to do?")
					.setNegativeButton("Back", new DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int arg1) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					
				})
					.setPositiveButton("Open", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(context, NoteHelper.class);
							intent.putExtra("FILENAME", fileString);
							startActivity(intent);
						}
					})
					.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							//Delete file
							
						}
					});
				AlertDialog ad = options.create();
				ad.show();
			}
		});
	}
}
