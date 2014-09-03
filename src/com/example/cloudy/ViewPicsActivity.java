package com.example.cloudy;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi.ContentsResult;
import com.google.android.gms.drive.DriveApi.MetadataBufferResult;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;

public class ViewPicsActivity extends BaseActivity {

	private String TAG = "ViewPicsActivity";
	private List<ImageView> mIvs = new ArrayList<ImageView>();
	private List<ResultCallback<ContentsResult>> mCallbacks = new ArrayList<ResultCallback<ContentsResult>>();

	@Override
	public void onConnected(Bundle connectionHint) {
		super.onCreate(connectionHint);
		setContentView(R.layout.activity_view_pics);
		mIvs.add((ImageView) findViewById(R.id.imageView1));
		mIvs.add((ImageView) findViewById(R.id.imageView2));
		mIvs.add((ImageView) findViewById(R.id.imageView3));
		Log.e(TAG, "google API Client: " + getGoogleApiClient());
		DriveFolder folder = Drive.DriveApi.getRootFolder(getGoogleApiClient());
		Query query = new Query.Builder().addFilter(Filters.eq(SearchableField.MIME_TYPE, "image/png")).build();
		folder.queryChildren(getGoogleApiClient(), query).setResultCallback(metadataResult);

		mCallbacks.add(new ResultCallback<ContentsResult>() {
			@Override
			public void onResult(ContentsResult result) {
				if (!result.getStatus().isSuccess()) {
					Log.e(TAG, "File can't be opened");
					// display an error saying file can't be opened
					return;
				}
				// Contents object contains pointers
				// to the actual byte stream
				Contents contents = result.getContents();
				InputStream is = contents.getInputStream();
				Bitmap bm = BitmapFactory.decodeStream(is);
				mIvs.get(0).setImageBitmap(bm);
			}
		});

		mCallbacks.add(new ResultCallback<ContentsResult>() {
			@Override
			public void onResult(ContentsResult result) {
				if (!result.getStatus().isSuccess()) {
					Log.e(TAG, "File can't be opened");
					// display an error saying file can't be opened
					return;
				}
				// Contents object contains pointers
				// to the actual byte stream
				Contents contents = result.getContents();
				InputStream is = contents.getInputStream();
				Bitmap bm = BitmapFactory.decodeStream(is);
				mIvs.get(1).setImageBitmap(bm);
			}
		});

		mCallbacks.add(new ResultCallback<ContentsResult>() {
			@Override
			public void onResult(ContentsResult result) {
				if (!result.getStatus().isSuccess()) {
					Log.e(TAG, "File can't be opened");
					// display an error saying file can't be opened
					return;
				}
				// Contents object contains pointers
				// to the actual byte stream
				Contents contents = result.getContents();
				InputStream is = contents.getInputStream();
				Bitmap bm = BitmapFactory.decodeStream(is);
				mIvs.get(2).setImageBitmap(bm);
			}
		});
	}


	final private ResultCallback<MetadataBufferResult> metadataResult = new
			ResultCallback<MetadataBufferResult>() {
		@Override
		public void onResult(MetadataBufferResult result) {
			if (!result.getStatus().isSuccess()) {
				showMessage("Problem while retrieving files");
				return;
			}
			MetadataBuffer buffer = result.getMetadataBuffer();
			Log.e(TAG, "size: " + buffer.getCount());
			Iterator<Metadata> iterator = buffer.iterator();
			int count = 0;
			while (iterator.hasNext()) {
				Metadata m = iterator.next();
				DriveFile file = Drive.DriveApi.getFile(getGoogleApiClient(), m.getDriveId());
				Log.e(TAG, "file: " + file);
				file.openContents(getGoogleApiClient(), DriveFile.MODE_READ_ONLY, null).setResultCallback(mCallbacks.get(count));
				count++;
			}
		}
	};


}
