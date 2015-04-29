package com.flickrapi.mp.sdave.shivamdave_miniproject_flickrapi;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


// Class that extends GetData to take the JSON gotten from GetData
// and parse that into the images data type
public class GetFlickrJSONData extends GetData {
    public String LOG_TAG = "GetFlickrJSONData";

    public List<Image> _images;
    private Uri endpointURI;
    private String FLICKR_URL = "https://api.flickr.com/services/feeds/photos_public.gne?format=json&nojsoncallback=1";

    public GetFlickrJSONData() {
        super(null);
        createURI();
        _images = new ArrayList<Image>();
    }

    public void execute() {
        super.setDataURL(endpointURI.toString());
        // TODO: Does the actual execution
    }

    // Makes the FLICKR_URL string a uri
    // if its null, it returns a false,
    // otherwise a true
    public boolean createURI() {
        endpointURI = Uri.parse(FLICKR_URL);
        if (endpointURI == null) {
            Log.v(LOG_TAG, "endPointURI is null");
            return false;
        } else {
            Log.v(LOG_TAG, "endPointURI is: " + FLICKR_URL);
            return true;
        }
    }

    public void createResult() {
        // TODO: Does the actual parsing of the JSON into a list of images
    }

    public List<Image> getImages() {
        return _images;
    }

    public class DownloadJSONData extends GetJSONData {

        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            createResult();
        }

        @Override
        protected String doInBackground(String... params) {
            String[] paramsArray = {endpointURI.toString()};
            return super.doInBackground(paramsArray);
        }
    }
}