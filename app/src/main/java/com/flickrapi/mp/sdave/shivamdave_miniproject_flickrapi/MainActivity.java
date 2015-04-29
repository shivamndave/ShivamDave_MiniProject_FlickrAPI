package com.flickrapi.mp.sdave.shivamdave_miniproject_flickrapi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.List;


public class MainActivity extends Activity {
    FlickrGridAdapter _adapter;
    ProgressDialog _progressSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _progressSpinner = new ProgressDialog(this);
        _progressSpinner.setTitle("Loading Flickr Public Feed");
        _progressSpinner.show();

        ProcessImages processImageList = new ProcessImages();
        processImageList.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Class that extends the GetFlickrJSONData class
    // calls those functions (that create the parsed JSON image list)
    // in addition to passing them into the FlickrGridAdapter
    public class ProcessImages extends GetFlickrJSONData {
        public ProcessImages() {
            super();
        }

        public void execute() {
            super.execute();
            MakeResult makeResult = new MakeResult();
            makeResult.execute();
        }

        public class MakeResult extends DownloadJSONData {
            List<Image> _currentImages;

            protected void onPostExecute(String dataWebStr) {
                _currentImages = getImages();
                GridView _flickrGridView = (GridView) findViewById(R.id.flickrGridView);
                _adapter = new FlickrGridAdapter(MainActivity.this, _currentImages);
                _flickrGridView.setAdapter(_adapter);
                _progressSpinner.dismiss();
            }
        }
    }
}
