package com.example.cloudy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

	private String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void viewFolder(View view) {
		Intent intent = new Intent(this, ViewFolderActivity.class);
		startActivity(intent);
	}
	
	public void createFile(View view) {
		Intent intent = new Intent(this, CreateFileActivity.class);
		startActivity(intent);
	}
	
	public void takePic(View view) {
		Intent intent = new Intent(this, TakePicActivity.class);
		startActivity(intent);
	}
	
	public void viewPics(View view) {
		Intent intent = new Intent(this, ViewPicsActivity.class);
		startActivity(intent);
	}

}




