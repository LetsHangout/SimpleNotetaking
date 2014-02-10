package com.letshangout.simplenotetaking.note;

import java.io.File;
import java.io.IOException;

import com.letshangout.simplenotetaking.R;
import com.letshangout.simplenotetaking.MainActivity;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NoteActivity extends Activity {
	final Context context = this;
	Button newNote;
	Button editNote;
	Button main;
	File ourDirectory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		createDirectory();
		addListenerOnButton();

	}

	public void addListenerOnButton() {
		main = (Button) findViewById(R.id.buttonMain);
		newNote = (Button) findViewById(R.id.buttonNew);
		editNote = (Button) findViewById(R.id.buttonEdit);

		main.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, MainActivity.class);
				startActivity(intent);
			}
		});

		newNote.setOnClickListener(new OnClickListener() {
			/*
			 * Open dialog window to ask for new file name
			 */

			@Override
			public void onClick(View v) {
				// set up a dialog box to accept input
				

				final EditText alertEditText = new EditText(context);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.MATCH_PARENT);
				alertEditText.setLayoutParams(lp);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				alertDialogBuilder
						.setMessage("Enter new file name")
						.setView(alertEditText)

						.setNegativeButton("Back",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// close window if this is clicked
										dialog.cancel();
									}
								})
						.setPositiveButton("Create",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int id) {
										// Overridden!!
									}
								});

				// create dialog box
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show dialog
				alertDialog.show();

				/*
				 * override positive button behavior
				 * must do this or else dialog will close when user tries to create a file that already exists
				 */
				alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								// Checks to make sure external storage exists
								// and is read/writeable
								if (!Environment.getExternalStorageState()
										.equals(Environment.MEDIA_MOUNTED)) {
									// Handle no sdcard event
									
									
								} else {
									//make sure file name is valid
									if (alertEditText.getText().toString()
											.matches("[a-zA-Z0-9]*")) {
										File file = new File(ourDirectory,
												alertEditText.getText()
														.toString() + ".txt");
										//make sure file doesn't already exist
										if (!file.exists()) {
											try {
												file.createNewFile();												
												// Go to new file??
												
												Intent intent = new Intent(context, NewNote.class);
												intent.putExtra("FILENAME", alertEditText.getText().toString());
												startActivity(intent);
												
												//Dismiss dialog?
											} catch (IOException e) {
												e.printStackTrace();
											}
										} else {
											alertEditText.setHint(alertEditText
													.getText().toString().toLowerCase());
											alertEditText.setText("");
											Toast.makeText(context,
													"File already exists!",
													Toast.LENGTH_SHORT).show();
										}

									} else {
										alertEditText.setText("");
										Toast.makeText(context,
												"Only A-z & 0-9 please",
												Toast.LENGTH_SHORT).show();
									}
								}
							}
						});

				alertDialog
						.setOnShowListener(new DialogInterface.OnShowListener() {
							/*
							 * Needs to be here to be able to focus on EditText
							 * in the dialog box.
							 * 
							 * (non-Javadoc)
							 * 
							 * @see
							 * android.content.DialogInterface.OnShowListener
							 * #onShow(android.content.DialogInterface)
							 */
							@Override
							public void onShow(DialogInterface arg0) {
								// TODO Auto-generated method stub
								alertEditText.requestFocus();
								alertEditText.setImeActionLabel("",
										EditorInfo.IME_ACTION_NEXT);

							}
						});

			}
		});

		editNote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub

			}
		});
	}

	public void createDirectory() {
		File sn = new File("SimpleNotes");
		// Return the primary external storage directory plus the name of our
		// directory and creates it.
		ourDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + sn + File.separator);
		//	Make sure directory doesn't already exist
		if(!ourDirectory.exists()){
			//Create
			ourDirectory.mkdir();
		}
	}
}
