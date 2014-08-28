package com.example.cloudy;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi.DriveIdResult;
import com.google.android.gms.drive.DriveApi.MetadataBufferResult;
import com.google.android.gms.drive.DriveFolder;


public class MainActivity extends BaseActivity {
	
	private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.activity_main);
    }
    
    @Override
    public void onConnected(Bundle connectionHint) {
    	super.onConnected(connectionHint);
    	login();
    	
    }
    
    public void login() {
        Drive.DriveApi.fetchDriveId(getGoogleApiClient(), EXISTING_FOLDER_ID)
                .setResultCallback(idCallback);
    }

    final private ResultCallback<DriveIdResult> idCallback = new ResultCallback<DriveIdResult>() {
        @Override
        public void onResult(DriveIdResult result) {
            if (!result.getStatus().isSuccess()) {
                showMessage("Cannot find DriveId. Are you authorized to view this file?");
                return;
            }
            DriveFolder folder = Drive.DriveApi.getFolder(getGoogleApiClient(), result.getDriveId());
            folder.listChildren(getGoogleApiClient())
                    .setResultCallback(metadataResult);
        }
    };

    final private ResultCallback<MetadataBufferResult> metadataResult = new
            ResultCallback<MetadataBufferResult>() {
        @Override
        public void onResult(MetadataBufferResult result) {
            if (!result.getStatus().isSuccess()) {
                showMessage("Problem while retrieving files");
                return;
            }
            Log.e(TAG, "Result: " + result.getMetadataBuffer());
            showMessage("Successfully listed files.");
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
