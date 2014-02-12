package com.letshangout.simplenotetaking.note;

import java.io.File;
import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Environment;

public class FileHelper extends ListActivity{
	
	final String ourDirectoryString = Environment.getExternalStorageDirectory() + File.separator + "SimpleNotes" + File.separator;
	final File ourDirectory =new File(Environment.getExternalStorageDirectory() + File.separator + "SimpleNotes" + File.separator);
	
	public ArrayList<String> getFileList(File[] file){
		ArrayList<String> arrayFiles = new ArrayList<String>();
		if(file.length == 0){
			return null;
		}else{
			for(int i=0; i<file.length;i++){
				arrayFiles.add(file[i].getName());
			}
		}
		return arrayFiles;
	}
	
	public File[] getFiles(String DirectoryPath){
		File f = new File(DirectoryPath);
		f.mkdirs();
		File[] file = f.listFiles();
		return file;
	}
	
	public void createDirectory() {
		//	Make sure directory doesn't already exist
		if(!ourDirectory.exists()){
			//Create
			ourDirectory.mkdir();
		}
	}
}
