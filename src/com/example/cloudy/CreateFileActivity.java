package com.example.cloudy;

import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi.ContentsResult;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.OpenFileActivityBuilder;


public class CreateFileActivity extends BaseActivity {

	private String TAG = "CreateFileActivity";

	protected static final int REQUEST_CODE_CREATOR = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_file);

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		super.onConnected(connectionHint);
		login();
	}

	public void login() {
		Drive.DriveApi.newContents(getGoogleApiClient())
		.setResultCallback(contentsCallback);
	}

	final private ResultCallback<ContentsResult> contentsCallback = new
			ResultCallback<ContentsResult>() {
		@Override
		public void onResult(ContentsResult result) {
			if (!result.getStatus().isSuccess()) {
				// Handle error
				return;
			}

			MetadataChangeSet metadataChangeSet = new MetadataChangeSet.Builder()
			.setMimeType("text/html").build();
			IntentSender intentSender = Drive.DriveApi
					.newCreateFileActivityBuilder()
					.setInitialMetadata(metadataChangeSet)
					.setInitialContents(result.getContents())
					.build(getGoogleApiClient());
			try {
				startIntentSenderForResult(intentSender, 1, null, 0, 0, 0);
			} catch (SendIntentException e) {
				// Handle the exception
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE_CREATOR:
			if (resultCode == RESULT_OK) {
				data.getParcelableExtra(
						OpenFileActivityBuilder.EXTRA_RESPONSE_DRIVE_ID);
				showMessage("File successfully created");
			}
			finish();
			break;
		default:
			super.onActivityResult(requestCode, resultCode, data);
			break;
		}
	}
}




