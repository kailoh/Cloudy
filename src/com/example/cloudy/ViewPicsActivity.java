package com.example.cloudy;

import java.util.Iterator;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi.MetadataBufferResult;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;

public class ViewPicsActivity extends BaseActivity {

	private String TAG = "ViewPicsActivity";
	private ListView mResultsListView;
	private ResultsAdapter mResultsAdapter;
	
	@Override
	public void onConnected(Bundle connectionHint) {
		super.onCreate(connectionHint);
		setContentView(R.layout.activity_view_pics);
		mResultsListView = (ListView) findViewById(R.id.picsListViewResults);
		mResultsAdapter = new ResultsAdapter(this);
		mResultsListView.setAdapter(mResultsAdapter);
		Log.e(TAG, "google API Client: " + getGoogleApiClient());
		DriveFolder folder = Drive.DriveApi.getRootFolder(getGoogleApiClient());
		Query query = new Query.Builder().addFilter(Filters.eq(SearchableField.MIME_TYPE, "image/png")).build();
		folder.queryChildren(getGoogleApiClient(), query).setResultCallback(metadataResult);
		
		//get the metadata of the file - it contains URL
		//https://developer.android.com/reference/com/google/android/gms/drive/Metadata.html
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
			MetadataBuffer buffer = result.getMetadataBuffer();
			Log.e(TAG, "size: " + buffer.getCount());
			Iterator<Metadata> iterator = buffer.iterator();
			while (iterator.hasNext()) {
				Metadata m = iterator.next();
				Log.e(TAG, "Web Content Link: " + m.getWebContentLink());
			}
			
			
			mResultsAdapter.append(result.getMetadataBuffer());
			showMessage("Successfully listed files.");
		}
	};

}
