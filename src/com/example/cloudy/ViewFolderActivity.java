package com.example.cloudy;

import android.os.Bundle;
import android.widget.ListView;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi.MetadataBufferResult;
import com.google.android.gms.drive.DriveFolder;

public class ViewFolderActivity extends BaseActivity {

	private String TAG = "ViewFolderActivity";
	private ListView mResultsListView;
	private ResultsAdapter mResultsAdapter;

	@Override
	public void onConnected(Bundle connectionHint) {
		super.onCreate(connectionHint);
		setContentView(R.layout.activity_view_folder);
		mResultsListView = (ListView) findViewById(R.id.listViewResults);
		mResultsAdapter = new ResultsAdapter(this);
		mResultsListView.setAdapter(mResultsAdapter);
		DriveFolder folder = Drive.DriveApi.getRootFolder(getGoogleApiClient());
		folder.listChildren(getGoogleApiClient())
		.setResultCallback(metadataResult);
	}


	final private ResultCallback<MetadataBufferResult> metadataResult = new
			ResultCallback<MetadataBufferResult>() {
		@Override
		public void onResult(MetadataBufferResult result) {
			if (!result.getStatus().isSuccess()) {
				showMessage("Problem while retrieving files");
				return;
			}
			mResultsAdapter.clear();
			mResultsAdapter.append(result.getMetadataBuffer());
			showMessage("Successfully listed files.");
		}
	};
}
