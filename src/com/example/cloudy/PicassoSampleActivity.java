package com.example.cloudy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ToggleButton;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

abstract class PicassoSampleActivity extends FragmentActivity {
  private FrameLayout sampleContent;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    super.setContentView(R.layout.picasso_sample_activity);
    sampleContent = (FrameLayout) findViewById(R.id.sample_content);

    final ListView activityList = (ListView) findViewById(R.id.activity_list);
    final PicassoSampleAdapter adapter = new PicassoSampleAdapter(this);
    activityList.setAdapter(adapter);
    activityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        adapter.getItem(position).launch(PicassoSampleActivity.this);
      }
    });
  }


  @Override public void setContentView(int layoutResID) {
    getLayoutInflater().inflate(layoutResID, sampleContent);
  }

  @Override public void setContentView(View view) {
    sampleContent.addView(view);
  }

  @Override public void setContentView(View view, ViewGroup.LayoutParams params) {
    sampleContent.addView(view, params);
  }
}
